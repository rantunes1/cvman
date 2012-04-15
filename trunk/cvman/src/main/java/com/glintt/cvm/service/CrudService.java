package com.glintt.cvm.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.glintt.cvm.model.Entity;

/**
 * Generic Create, Read, Update and Delete interface descriptor
 * 
 * @param <E>
 *            The entity type being managed by this service
 */
@Service
public interface CrudService<E extends Entity> {

	E getById(Long id);

	Collection<E> getAll();

	void create(final E entity);

	void update(final E entity);

	void delete(final E entity);

	void deleteById(Long id);
}
