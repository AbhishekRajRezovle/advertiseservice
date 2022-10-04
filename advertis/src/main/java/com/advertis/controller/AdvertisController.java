package com.advertis.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advertis.dto.ResponseDto;
import com.advertis.entity.AdvertisEntity;
import com.advertis.service.AdvertisService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/advertis/api")
public class AdvertisController {

	@Autowired
	AdvertisService advertisService;

	@PostMapping("/createAdvertis")
	public ResponseDto addAdvertis(@Valid @RequestBody AdvertisEntity request) {
		log.info("creating advertising for request {}", request);
		return advertisService.addAdvertis(request);
	}

	@DeleteMapping("deleteAdvertis" + "/{advertisingId}")
	public ResponseDto deleteAdvertising(@PathVariable final Long advertisingId) {
		log.info("deleting advertising for request {}", advertisingId);
		return advertisService.deleteAdvertis(advertisingId);
	}

	@PutMapping("updateAdvertis")
	public ResponseDto updateAdvertis(@Valid @RequestBody AdvertisEntity request) {
		log.info("updating advertising for request {}", request);
		return advertisService.updateAdvertis(request);
	}

}
