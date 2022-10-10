package com.advertis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.advertis.entity.AdvertisGeoMapping;

@Repository
public interface AdvertisGeoMappingRepository extends JpaRepository<AdvertisGeoMapping, Long> {
	
    @Query(value = "SELECT * FROM advertis_geo_mapping m WHERE m.geo_id IN :geoId",nativeQuery = true)
    List<AdvertisGeoMapping> findByGeoIds(@Param("geoId") List<Long> geoId);

}
