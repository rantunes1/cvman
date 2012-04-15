package com.glintt.cvm.service;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glintt.cvm.model.Entity;

/**
 * Abstract base Service/Repository
 * 
 * @param <E>
 *            the Entity managed by the service
 */
@Service
@Repository
public abstract class AbstractService<E extends Entity> implements CrudService<E> {

	@PersistenceContext
	EntityManager em;

	protected E getById(Long id, Class<E> clazz) {
		return this.em.find(clazz, id);
	}

	protected Collection<E> getAll(String query, Class<E> clazz) {
		return this.em.createQuery(query, clazz).getResultList();
	}

	@Transactional
	protected void deleteById(Long id, Class<E> clazz) {
		if (id == null || clazz == null) {
			return;
		}

		this.em.remove(this.getById(id, clazz));
	}

	@Override
	@Transactional
	public void create(E entity) {
		this.em.persist(entity);
	}

	@Override
	@Transactional
	public void update(E entity) {
		this.em.merge(entity);
	}

	@Override
	@Transactional
	public void delete(E entity) {
		this.em.remove(entity);
	}
}
