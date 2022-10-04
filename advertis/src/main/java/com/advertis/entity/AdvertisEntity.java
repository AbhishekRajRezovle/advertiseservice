package com.advertis.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
@Entity
@Table(name="advertis_master")
public class AdvertisEntity extends AuditEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adId;
	
    @NotNull
    @Digits(integer = 6, fraction = 7, message = "at max 7 precision allowed")
    private Double latitude;
    @NotNull
    @Digits(integer = 6, fraction = 7, message = "at max 7 precision allowed")
    private Double longitude;
    @NotBlank
    //@URL(message = "href is invalid")
    private String href;
    @NotBlank
    private String advertisName;

}
