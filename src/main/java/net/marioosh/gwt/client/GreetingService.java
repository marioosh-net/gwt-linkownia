package net.marioosh.gwt.client;

import java.util.List;
import net.marioosh.gwt.shared.RPCException;
import net.marioosh.gwt.shared.model.entities.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greetingService")
public interface GreetingService extends RemoteService {
  String greetServer(String name) throws IllegalArgumentException;
  String findUser(String login);
  String addRandomUsers(int count);
  String addUser(String login) throws RPCException;
  boolean isUserExist(String login);
  void deleteAllUsers() throws RPCException;
  public List<User> allUsers();
  public User randomUser();
}
