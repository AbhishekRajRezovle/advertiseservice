package com.advertis.utils;

import org.apache.lucene.util.SloppyMath;
import org.springframework.stereotype.Component;

import com.advertis.dto.Geo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HaversineDistanceCalculator {
	
	/*
	 This function checks whether the given lat and long is inside the radius or not
	 */
	    public boolean checkInside(Geo circle, Double longitude, Double latitude) {
	        return haversineDistance(
	                circle.getLongitude(), circle.getLatitude(), longitude, latitude
	        ) < circle.getRadius();
	    }

	    /*
	    Below function is the formula to return the distance between 2 geolocation lat and long.
	    This formula is based on harvestine distance calculator.
	     */
	    public double haversineDistance(Double lon1, Double lat1, Double lon2, Double lat2)
	    {
	        log.info("finding distance of {} {} , {} {}",lat1,lon1,lat2,lon2);
	        double result =  SloppyMath.haversinMeters(lat1,lon1,lat2,lon2);
	        log.info("Distance b/w 2 geo is {}",result);
	        return result;
	    }

}
