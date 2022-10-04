package com.advertis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.advertis.entity.AdvertisGeoMapping;

@Repository
public interface AdvertisGeoMappingRepository extends JpaRepository<AdvertisGeoMapping, Long> {

}
