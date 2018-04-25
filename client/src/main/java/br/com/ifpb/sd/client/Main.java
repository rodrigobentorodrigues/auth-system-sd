package br.com.ifpb.sd.client;

import com.ifpb.sd.domain.User;
import com.ifpb.sd.infra.Translate;
import com.ifpb.sd.infra.TranslateImpl;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import com.ifpb.sd.remotes.AuthService;

/**
 *
 * @author rodrigobento
 */
public class Main {

    private static Translate translate = new TranslateImpl();

    // Pensando em buscar de inicio no banco local para so assim enviar msg para auth-manager
    public static void main(String[] args) {
        try {
            // Alterar localhost quando utilizar docker
            Registry registry = LocateRegistry.getRegistry("manager", 1090);
//            Registry registry = LocateRegistry.getRegistry("localhost", 1090);
            AuthService service = (AuthService) registry.lookup("rmi:/auth-manager");
            User u = new User("sd-provider", "rodrigo@gmail.com", "123");
            String jsonRequest = translate.toJSONRequestClient(u);
            long init = System.currentTimeMillis();
            for (int i = 0; i < 200; i++) {
                System.out.println(service.auth(jsonRequest));
            }
            long fim = System.currentTimeMillis();
            System.out.println(fim - init + " milisegundos");
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
    }

}
