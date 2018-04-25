package com.ifpb.sd.remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author rodrigobento
 */
public interface AuthService extends Remote {
    
    String auth(String json) throws RemoteException;
    String check(String json) throws RemoteException;
    String register(String json) throws RemoteException;
    String inutilize(String json) throws RemoteException;
    
}
