package net.marioosh.gwt.shared;

import java.io.Serializable;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Exception during RPC.
 * IsSerializable - Marker interface indicating that 
 * 	a type is intended to be used with a RemoteService.
 * 
 * @author marioosh
 */
@SuppressWarnings("serial")
public class RPCException extends Exception implements IsSerializable {
	
	public RPCException() {	}
	
	public RPCException(String message) {
		super(message);
	}
}
