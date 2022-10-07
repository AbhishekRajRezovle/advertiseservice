package com.advertis.service;

import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.advertis.AppConstantTest;
import com.advertis.dto.GeoDto;
import com.advertis.dto.ResponseDto;
import com.advertis.entity.AdvertisEntity;
import com.advertis.entity.AdvertisGeoMapping;
import com.advertis.repository.AdvertisGeoMappingRepository;
import com.advertis.repository.AdvertisRepository;
import com.advertis.utils.HaversineDistanceCalculator;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

	@InjectMocks
	AdvertisService advertisService;
	@Mock
	AdvertisRepository advertisRepository;
	@Mock
	AdvertisGeoMappingRepository advertisGeoMappingRepository;
	AdvertisEntity advertisEntity = new AdvertisEntity();
	@Mock
	RestTemplate restTemplate;
	@Mock
	HaversineDistanceCalculator haversineDistanceCalculator;
	List<AdvertisEntity>  AdvertisEntities=new ArrayList<>();
	List<AdvertisGeoMapping> AdvertisGeoMappings= new ArrayList<>();
	
	@BeforeEach
    public void setup() {
		AdvertisEntity advertisEntity = new AdvertisEntity();
		advertisEntity.setAdId(102L);
		advertisEntity.setHref("https://www.google.com");
		advertisEntity.setLongitude(12.0);
		advertisEntity.setLatitude(11.0);
		advertisEntity.setAdvertisName("Test");
		advertisEntity.setUpdatedAt(new Date());
		advertisEntity.setCreatedAt(new Date());
    }
	
	@Test
    void addAdvertis() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        when(restTemplate.exchange(advertisEntity.getHref(), HttpMethod.GET, entity, String.class)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        given(advertisRepository.save(Mockito.any())).willReturn(advertisEntity);
        GeoDto geoDto = mapper.readValue(AppConstantTest.GEO_RESPONSE, GeoDto.class);
        when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(ResponseEntity.ok(geoDto));
        given(haversineDistanceCalculator.checkInside(Mockito.any(),Mockito.any(),Mockito.any())).willReturn(true);
        ResponseDto advertisingModelResponse = advertisService.addAdvertis(advertisEntity);
        Assertions.assertEquals(200, advertisingModelResponse.getCode());
    }
	
	@Test
    void createAdvertis_invalidUrl() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        when(restTemplate.exchange(advertisEntity.getHref(), HttpMethod.GET, entity, String.class)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        ResponseDto advertisingModelResponse = advertisService.addAdvertis(advertisEntity);
        Assertions.assertEquals(424, advertisingModelResponse.getCode());
    }
	
	@Test
    void updateAdvertis() {
        advertisEntity.setLongitude(11.23);
        given(advertisRepository.findById(Mockito.any())).willReturn(Optional.of(advertisEntity));
        given(advertisRepository.save(Mockito.any())).willReturn(advertisEntity);
        ResponseDto advertisingModelResponse = advertisService.updateAdvertis(advertisEntity);
        Assertions.assertEquals(200, advertisingModelResponse.getCode());
    }
	
	@Test
    void updateAdvertis_not_found() {
		advertisEntity.setAdId(201L);
		advertisEntity.setLongitude(11.23);
        given(advertisRepository.findById(Mockito.any())).willReturn(Optional.empty());
        ResponseDto advertisingModelResponse = advertisService.updateAdvertis(advertisEntity);
        Assertions.assertEquals(404, advertisingModelResponse.getCode());
    }
	
	@Test
    void deleteAdvertis() {
        given(advertisRepository.findById(Mockito.any())).willReturn(Optional.of(advertisEntity));
        ResponseDto advertisingModelResponse = advertisService.deleteAdvertis(advertisEntity.getAdId());
        Assertions.assertEquals(200, advertisingModelResponse.getCode());
    }

    @Test
    void deleteAdvertis_not_found() {
        given(advertisRepository.findById(Mockito.any())).willReturn(Optional.empty());
        ResponseDto advertisingModelResponse = advertisService.deleteAdvertis(advertisEntity.getAdId());
        Assertions.assertEquals(404, advertisingModelResponse.getCode());
    }

}
