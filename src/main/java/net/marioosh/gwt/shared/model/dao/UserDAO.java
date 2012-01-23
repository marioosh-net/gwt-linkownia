package net.marioosh.gwt.shared.model.dao;

import java.io.Serializable;
import java.util.List;
import net.marioosh.gwt.shared.model.entities.User;

public interface UserDAO extends GenericDAO<User> {

	public User getByLogin(String login);
	public User getByLoginLike(String login);
	public Serializable add(String login);
	public List<User> findAll();
	public List<User> findAll(int page);
	public int countPages(int rowPerPage);
	public Serializable addRandom();
	public void deleteAll();
	public User getRandomUser();
}
