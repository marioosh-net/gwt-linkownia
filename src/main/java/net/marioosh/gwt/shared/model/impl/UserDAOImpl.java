package net.marioosh.gwt.shared.model.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import net.marioosh.gwt.shared.model.dao.UserDAO;
import net.marioosh.gwt.shared.model.entities.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDAO")
@Transactional
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO {

	private Logger log = Logger.getLogger(getClass());
	
	private SecureRandom random = new SecureRandom();
	
	public UserDAOImpl() {
		super(User.class);
	}

	@Autowired
	public void init(SessionFactory factory) {
		setSessionFactory(factory);
	}

	@Override
	public User getByLogin(String login) {
		String sql = "from User where login = ?";
		Query query = getSession().createQuery(sql);
		query.setString(0, login);
		return (User) query.uniqueResult();
	}
	
	@Override
	public User getByLoginLike(String login) {
		String sql = "from User where login like '%"+login+"%'";
		Query query = getSession().createQuery(sql);
		return (User) query.uniqueResult();
	}

	@Override
	public List<User> findAll() {
		String sql = "from User";
		Query query = getSession().createQuery(sql);
		return query.list();
	}
	
	@Override
	public List<User> findAll(int page) {
		int offset = page * 5;
		String sql = "from User";
		Query query = getSession().createQuery(sql);
		query.setMaxResults(5);
		query.setFirstResult(offset);
		return query.list();
	}
	
	@Override
	public int countPages(int rowPerPage) {
		String sql = "select count(*) from User";
		Query query = getSession().createQuery(sql);
		return (((Long)query.uniqueResult()).intValue() / rowPerPage) + 1;
	}
	
	@Override
	public Serializable add(User obj) {
		obj.setPassword(DigestUtils.md5Hex(obj.getPassword()));
		return super.add(obj);
	}

	@Override
	public Serializable addRandom() {
		User u = new User();
		u.setLogin(nextRandom());
		u.setEmail(nextRandom());
		u.setFirstname(nextRandom());
		u.setLastname(nextRandom());
		u.setPassword(nextRandom());
		u.setDate(new Date());
		u.setTelephone(nextRandom());		
		return add(u);
	}
	
	@Override
	public Serializable add(String login) {
		User u = new User();
		u.setLogin(login);
		u.setEmail(nextRandom());
		u.setFirstname(nextRandom());
		u.setLastname(nextRandom());
		u.setPassword(nextRandom());
		u.setDate(new Date());
		u.setTelephone(nextRandom());
		return add(u);
	}
	
	@Override
	public void deleteAll() {
		getSession().createQuery("delete from User").executeUpdate();
	}
	
	private String nextRandom() {
		return new BigInteger(130, random).toString(32);
	}
	
	@Override
	public User getRandomUser() {
		String sql = "from User where 1 = 1";
		Query query = getSession().createQuery(sql);
		query.setMaxResults(1);
		return (User) query.uniqueResult();
	}
}