package com.advertis.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advertis.dto.ResponseDto;
import com.advertis.entity.AdvertisEntity;
import com.advertis.service.AdvertisService;
import com.advertis.service.AdvertisServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/advertis/api")
public class AdvertisController {

	@Autowired
	AdvertisService advertisService;

	@PostMapping("/advertis")
	public ResponseDto addAdvertis(@Valid @RequestBody AdvertisEntity request) {
		log.info("creating advertising for request {}", request);
		return advertisService.addAdvertis(request);
	}

	@DeleteMapping("advertis" + "/{advertisId}")
	public ResponseDto deleteAdvertising(@PathVariable final Long advertisId) {
		log.info("deleting advertising for request {}", advertisId);
		return advertisService.deleteAdvertis(advertisId);
	}
	
	@GetMapping("/advertis")
    public ResponseDto getAdvertis(@RequestParam final Double latitude,@RequestParam final Double longitude){
        log.info("getting advertising for request lat {} long {}",latitude,longitude);
        return advertisService.getAdvertisInsideGeo(latitude,longitude);
    }

	@PutMapping("/advertis")
	public ResponseDto updateAdvertis(@Valid @RequestBody AdvertisEntity request) {
		log.info("updating advertising for request {}", request);
		return advertisService.updateAdvertis(request);
	}

}
