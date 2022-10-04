package com.advertis.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.advertis.constant.ApplicationConstants;
import com.advertis.dto.ErrorResponseDto;
import com.advertis.dto.Geo;
import com.advertis.dto.GeoDto;
import com.advertis.dto.ResponseDto;
import com.advertis.dto.SuccessResponseDto;
import com.advertis.entity.AdvertisEntity;
import com.advertis.entity.AdvertisGeoMapping;
import com.advertis.repository.AdvertisGeoMappingRepository;
import com.advertis.repository.AdvertisRepository;
import com.advertis.utils.HaversineDistanceCalculator;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdvertisService {

	@Autowired
	private AdvertisRepository advertisRepository;
	@Autowired
	RestTemplate restTemplate;
	@Value("${client.geo.baseUrl}")
	private String geoClientUrl;
	@Autowired
	HaversineDistanceCalculator haversineDistanceCalculator;

	@Autowired
	AdvertisGeoMappingRepository advertisGeoMappingRepository;

	@Transactional
	public ResponseDto addAdvertis(AdvertisEntity advertisEntity) {
		int isValidUrl = validateHrefUrl(advertisEntity.getHref());
		System.out.println("isValidUrl::"+isValidUrl);
		if (isValidUrl != 200) {
			return new ErrorResponseDto(ApplicationConstants.HTTP_RESPONSE_ERROR_CODE_HREF,
					"received unsuccessful status code " + isValidUrl + " from " + advertisEntity.getHref());
		}
		AdvertisEntity responseDto = advertisRepository.save(advertisEntity);
		List<Geo> geo = (List<Geo>) getAdvertis(advertisEntity.getLatitude(), advertisEntity.getLongitude()).getData();
		Set<AdvertisGeoMapping> advertisGeoMappingSet = new HashSet<>();
		AdvertisGeoMapping advertisGeoMapping;
		if (geo != null && !geo.isEmpty()) {
			for (Geo loopGeoFence : geo) {
				advertisGeoMapping = new AdvertisGeoMapping(loopGeoFence.getGeoId(), responseDto.getAdId());
				advertisGeoMappingSet.add(advertisGeoMapping);
			}
			advertisGeoMappingRepository.saveAll(advertisGeoMappingSet);
		}
		return new SuccessResponseDto(responseDto);
	}

	public ResponseDto deleteAdvertis(Long advertisingId) {
		if (advertisRepository.findById(advertisingId).isPresent()) {
			advertisRepository.deleteById(advertisingId);
			return new SuccessResponseDto(ApplicationConstants.HTTP_RESPONSE_SUCCESS_CODE);
		}
		return new ErrorResponseDto(ApplicationConstants.NOT_FOUND, ApplicationConstants.NOT_FOUND_MSG);
	}

	/*
	 * This function update the advertising data based on unique advertising id if
	 * found in system
	 */
	public ResponseDto updateAdvertis(AdvertisEntity request) {
		return advertisRepository.findById(request.getAdId()).isPresent()
				? new SuccessResponseDto(advertisRepository.save(request))
				: new ErrorResponseDto(ApplicationConstants.NOT_FOUND, ApplicationConstants.NOT_FOUND_MSG);
	}

	private List<Geo> checkInsideGeoFences(List<Geo> geoFencesList, Double latitude, Double longitude) {
		List<Geo> insideGeoList = new ArrayList<>();
		geoFencesList.stream().forEach((loop) -> {
			log.info("checking lat {} long {} inside Geo {}", latitude, longitude, loop);
			if (haversineDistanceCalculator.checkInside(loop, longitude, latitude)) {
				insideGeoList.add(loop);
			}

		});
		return insideGeoList;
	}

	/*
	 * This function return the list of all geofence available at client system and
	 * then checks the unique geolocation which are inside the radius
	 */
	public ResponseDto getAdvertis(Double latitude, Double longitude) {
		List<Geo> geoFencesList = checkInsideGeoFences(getGeoList(), latitude, longitude);
		return new SuccessResponseDto(geoFencesList);
	}

	/*
	 * This function returns the list of all client geofence by using http call
	 */
	public List<Geo> getGeoList() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		ResponseEntity<Object> response = restTemplate.getForEntity(geoClientUrl + "/getGeos", Object.class);
		log.info("list of geofence is {}", response);
		Object obj = response.getBody();
		ObjectMapper mapper = new ObjectMapper();
		GeoDto geoDto = mapper.convertValue(obj, GeoDto.class);
		return geoDto.getData();

	}

	public int validateHrefUrl(String href) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange(href, HttpMethod.GET, entity, String.class).getStatusCodeValue();
	}

}
