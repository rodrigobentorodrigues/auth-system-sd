package com.ifpb.sd.manager;

import com.ifpb.sd.datastore.RepoSingleton;
import com.ifpb.sd.infra.Translate;
import com.ifpb.sd.infra.TranslateImpl;
import com.ifpb.sd.remotes.AuthService;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author rodrigobento
 */
public class Main {

    private static RepoSingleton repo = RepoSingleton.getInstance();
    private static Translate trans = new TranslateImpl();

    public static void main(String[] args) {
        try {
            AuthService service = new AuthServiceImpl();
            // Iniciando o Auth-Manager
            Registry registry = LocateRegistry.createRegistry(1090);
            registry.bind("rmi:/auth-manager", service);
            System.out.println("Auth Manager pronto para receber requisições");
            Thread thread = new Thread(new InutilizeWorker(repo, service, trans));
            thread.start();
        } catch (RemoteException | AlreadyBoundException ex) {
            ex.printStackTrace();
        }
    }

}
