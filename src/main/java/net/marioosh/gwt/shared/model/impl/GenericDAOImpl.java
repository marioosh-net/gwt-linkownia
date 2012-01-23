package net.marioosh.gwt.shared.model.impl;

import java.io.Serializable;
import net.marioosh.gwt.shared.model.dao.GenericDAO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GenericDAOImpl<T> extends HibernateDaoSupport implements GenericDAO<T> {

	protected final Class<T> persistentClass;
	
	GenericDAOImpl(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Override
	public Serializable add(T obj) {
		return getHibernateTemplate().save(obj);
	}

	@Override
	public void update(T obj) {
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Long id) {
		getHibernateTemplate().delete(get(id));
	}

	@Override
	public T get(Long id) {
		return (T) getHibernateTemplate().get(persistentClass, id);
	}

}
