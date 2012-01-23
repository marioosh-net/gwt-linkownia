package net.marioosh.gwt.shared.model.dao;

import java.io.Serializable;

public interface GenericDAO<T> {

	public Serializable add(T obj);
	public void update(T obj);
	public void delete(Long id);
	public T get(Long id);

}
