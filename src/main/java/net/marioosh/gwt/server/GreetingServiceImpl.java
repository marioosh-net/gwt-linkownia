package net.marioosh.gwt.server;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.marioosh.gwt.client.GreetingService;
import net.marioosh.gwt.shared.RPCException;
import net.marioosh.gwt.shared.model.dao.LinkDAO;
import net.marioosh.gwt.shared.model.dao.UserDAO;
import net.marioosh.gwt.shared.model.entities.Link;
import net.marioosh.gwt.shared.model.entities.User;
import net.marioosh.gwt.shared.model.helper.Criteria;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends AutoinjectingRemoteServiceServlet implements
		GreetingService {

	Logger log = Logger.getLogger(getClass());

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private LinkDAO linkDAO;

	@Override
	public String greetServer(String input) throws IllegalArgumentException {
		return "Hello " + input;
	}

	@Override
	public String findUser(String login) {
		return userDAO.getByLoginLike(login) + "";
	}

	@Override
	public String addUser(String login) throws RPCException {
		if(!isUserExist(login)) {
			Long id = (Long) userDAO.add(login);
			return userDAO.get(id)+"";
		} else {
			throw new RPCException("Login exist!");
		}
	}
	
	@Override
	public String addRandomUsers(int count) {
		try {
			while (count-- > 0)
				userDAO.addRandom();
			return "OK";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@Override
	public boolean isUserExist(String login) {
		if(userDAO.getByLogin(login) != null){
			return true;
		}
		return false;
	}
	
	@Override
	public void deleteAllUsers() throws RPCException {
		userDAO.deleteAll();
	}
	
	@Override
	public List<User> allUsers() {
		List<User> l = userDAO.findAll();
		for(User u: l) {
			log.info(u);
		}
		return l;
	}
	
	public User randomUser() {
		return userDAO.getRandomUser();
	}
	
	@Override
	public List<Link> allLinks(Criteria c) {
		return linkDAO.find(c); 
	}
	
	@Override
	public void addLink(Link link) throws RPCException {
		try {
			Map<String,String> m = WebUtils.pageInfo(link.getAddress());
			if(m.get("title") != null && (link.getName() == null || link.getName().equals(""))) {
				link.setName(m.get("title"));
			}
			if(m.get("description") != null && (link.getDescription() == null || link.getDescription().equals(""))) {
				link.setDescription(m.get("description"));
			}
			if(link.getName() == null) {
				link.setName(link.getAddress());
			}
			linkDAO.add(link);
		} catch(Exception e) {
			throw new RPCException(e.getMessage());
		}
	}
}
