package com.advertis.service;

import java.util.List;

import com.advertis.dto.Geo;
import com.advertis.dto.ResponseDto;
import com.advertis.entity.AdvertisEntity;

public interface AdvertisService {
	
	public ResponseDto addAdvertis(AdvertisEntity advertisEntity);
	
	public ResponseDto getAdvertisInsideGeo(Double latitude, Double longitude);
	
	public ResponseDto deleteAdvertis(Long advertisingId);
	
	public ResponseDto updateAdvertis(AdvertisEntity request);
	
	public ResponseDto getAdvertis(Double latitude, Double longitude);
	
	public List<Geo> checkInsideGeoFences(List<Geo> geoFencesList, Double latitude, Double longitude);
	
	public List<Geo> getGeoList();
	
	public int validateHrefUrl(String href);

}
