package com.glintt.cvm.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface Entity extends Serializable {
	/**
	 * Retrieves the primary key for this entity
	 * 
	 * @return the primary key
	 */
	Long getId();

	void setId(Long id);

	/**
	 * Retrieves the concurrency version for this entity. The concurrency
	 * version is used for optimistic locking on the database
	 * 
	 * @return
	 */
	Long getVersion();

	void setVersion(Long version);
}
