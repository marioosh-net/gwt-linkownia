package net.marioosh.gwt.shared.model.impl;

import java.io.Serializable;
import java.util.List;
import net.marioosh.gwt.shared.model.dao.GenericDAO;
import net.marioosh.gwt.shared.model.helper.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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

	@Override
	public List<T> find(Criteria c) {
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		criteria.addOrder(Order.desc("id"));
		if(c != null) {
			return getHibernateTemplate().findByCriteria(criteria, c.getStart(), c.getCount());
		} else {
			return getHibernateTemplate().findByCriteria(criteria);
		}
	}

}
