package net.marioosh.gwt.shared.model.impl;

import net.marioosh.gwt.shared.model.dao.LinkDAO;
import net.marioosh.gwt.shared.model.entities.Link;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("linkDAO")
@Transactional
public class LinkDAOImpl extends GenericDAOImpl<Link> implements LinkDAO {

	private Logger log = Logger.getLogger(getClass());
	
	public LinkDAOImpl() {
		super(Link.class);
	}

	@Autowired
	public void init(SessionFactory factory) {
		setSessionFactory(factory);
	}

}