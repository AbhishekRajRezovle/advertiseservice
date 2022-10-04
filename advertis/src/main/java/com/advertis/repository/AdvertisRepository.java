package com.advertis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.advertis.entity.AdvertisEntity;

@Repository
public interface AdvertisRepository extends JpaRepository<AdvertisEntity, Long> {

}
