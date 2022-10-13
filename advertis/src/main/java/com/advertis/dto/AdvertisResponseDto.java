package com.advertis.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdvertisResponseDto extends Geo {
	private List<AdvertisDto> advertisEntityList = new ArrayList<>();

}
