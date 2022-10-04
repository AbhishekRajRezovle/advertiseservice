package com.advertis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GeoDto {
	private Integer code;
	private String message;
	private Integer status;
	private String requestId;
	private List<Geo> data;

}
