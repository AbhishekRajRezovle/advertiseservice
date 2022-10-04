package com.advertis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Geo {
	
    private Long geoId;
    private Double latitude;
    private Double longitude;
    private Double radius;

}
