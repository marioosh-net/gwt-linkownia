package net.marioosh.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface GreetingServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void greetServer( java.lang.String p0, AsyncCallback<java.lang.String> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void findUser( java.lang.String p0, AsyncCallback<java.lang.String> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void addRandomUsers( int p0, AsyncCallback<java.lang.String> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void addUser( java.lang.String p0, AsyncCallback<java.lang.String> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void isUserExist( java.lang.String p0, AsyncCallback<java.lang.Boolean> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void deleteAllUsers( AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void allUsers( AsyncCallback<java.util.List> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void randomUser( AsyncCallback<net.marioosh.gwt.shared.model.entities.User> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void addLink( net.marioosh.gwt.shared.model.entities.Link p0, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void allLinks( net.marioosh.gwt.shared.model.helper.Criteria p0, AsyncCallback<java.util.List> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see net.marioosh.gwt.client.GreetingService
     */
    void deleteLink( java.lang.Long p0, AsyncCallback<Void> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static GreetingServiceAsync instance;

        public static final GreetingServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (GreetingServiceAsync) GWT.create( GreetingService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "GreetingService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
