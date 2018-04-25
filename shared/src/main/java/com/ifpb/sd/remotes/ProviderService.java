package com.ifpb.sd.remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author rodrigobento
 */
public interface ProviderService extends Remote {
    
    String auth(String json) throws RemoteException;
    
}
