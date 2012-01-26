package net.marioosh.gwt.shared.model.dao;

import java.io.Serializable;
import java.util.List;
import net.marioosh.gwt.shared.model.helper.Criteria;

public interface GenericDAO<T> {

	public Serializable add(T obj);
	public void update(T obj);
	public void delete(Long id);
	public T get(Long id);
	public List<T> find(Criteria c);

}
