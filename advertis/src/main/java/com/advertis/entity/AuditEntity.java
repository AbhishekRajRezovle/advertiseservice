package com.advertis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
@Data
public abstract class AuditEntity implements Serializable {

	@Column(name = "created_at", updatable = false)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")  
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime updatedAt;

}
