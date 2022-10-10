package com.advertis.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.advertis.AppConstantTest;
import com.advertis.entity.AdvertisEntity;
import com.advertis.service.AdvertisService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
	
	@Autowired
    MockMvc mockMvc;
    @InjectMocks
    AdvertisController advertisController;
    @Mock
    AdvertisService advertisingService;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(advertisController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    void addAdvertis() throws Exception{
        AdvertisEntity advertisEntity  = new AdvertisEntity();
        advertisEntity.setAdId(101L);
        advertisEntity.setHref("http://www.google.com");
        advertisEntity.setLongitude(2.0);
        advertisEntity.setLatitude(1.0);
        advertisEntity.setAdvertisName("Test");
        advertisEntity.setUpdatedAt(new Date());
        advertisEntity.setCreatedAt(new Date());
        String json = mapper.writeValueAsString(advertisEntity);
        RequestBuilder mvCRequest = MockMvcRequestBuilders.post(AppConstantTest.CONTROLLER_BASE_URL + "/createAdvertis")
                .contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mvCRequest).andExpect(status().isOk());
    }


    @Test
    void deleteAdvertising() throws Exception{
        RequestBuilder mvCRequest = MockMvcRequestBuilders.delete(AppConstantTest.CONTROLLER_BASE_URL + "/deleteAdvertis/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mvCRequest).andExpect(status().isOk());
    }

    @Test
    void updateAdvertis() throws Exception{
    	AdvertisEntity advertisEntity  = new AdvertisEntity();
    	advertisEntity.setAdId(101L);
    	advertisEntity.setHref("https://www.google.com");
    	advertisEntity.setLongitude(12.0);
    	advertisEntity.setLatitude(11.0);
    	advertisEntity.setAdvertisName("Test");
    	advertisEntity.setUpdatedAt(new Date());
    	advertisEntity.setCreatedAt(new Date());
        String json = mapper.writeValueAsString(advertisEntity);
        RequestBuilder mvCRequest = MockMvcRequestBuilders.put(AppConstantTest.CONTROLLER_BASE_URL + "/updateAdvertis")
                .contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mvCRequest).andExpect(status().isOk());
    }
    
    @Test
    void getAdvertising() throws Exception{
        RequestBuilder mvCRequest = MockMvcRequestBuilders.get(AppConstantTest.CONTROLLER_BASE_URL + "/getAdvertis?latitude=77.9&longitude=88.9")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mvCRequest).andExpect(status().isOk());
    }

}
