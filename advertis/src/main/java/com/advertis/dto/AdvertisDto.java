package com.advertis.dto;

import com.advertis.entity.AdvertisEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@NoArgsConstructor
public class AdvertisDto {
	
    private Long adId;
    private Double latitude;
    private Double longitude;
    private String href;
    private String advertisName;
    private Double distance;

    public AdvertisDto(AdvertisEntity advertisEntity) {
        this.adId = advertisEntity.getAdId();
        this.latitude = advertisEntity.getLatitude();
        this.longitude = advertisEntity.getLongitude();
        this.href = advertisEntity.getHref();
        this.advertisName = advertisEntity.getAdvertisName();
        this.distance = 0.0;
    }

}
