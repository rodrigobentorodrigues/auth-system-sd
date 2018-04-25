package br.com.ifpb.sd.provider;

import com.ifpb.sd.domain.Provider;
import com.ifpb.sd.domain.User;
import com.ifpb.sd.infra.Translate;
import com.ifpb.sd.infra.TranslateImpl;
import com.ifpb.sd.remotes.AuthService;
import com.ifpb.sd.repository.RepoSingleton;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author rodrigobento
 */
public class Main {

    private static Translate trans = new TranslateImpl();
    private static RepoSingleton repo = RepoSingleton.getInstance();

    public static void main(String[] args) throws JSONException {
        try {
            // Dados do Provider
            String appId = "sd-provider";
//            Para executar localmente
//            String host = "localhost";
            String host = "provider";
            String name = "rmi:/provider";
            int port = 1095;

            // Executando Provider 
            Registry reg = LocateRegistry.createRegistry(port);
            reg.bind(name, new ProviderServiceImpl());
            System.out.println("Provider pronto para receber requisições");
            
//            // Cadastrando provedor
//            Provider insert = new Provider(appId, host, name, port);
//            String request = trans.toJSONRequestProvider(insert);
//            
////            Registry registry = LocateRegistry.getRegistry("localhost", 1090);
//            Registry registry = LocateRegistry.getRegistry("manager", 1090);
//            AuthService service = (AuthService) registry.lookup("rmi:/auth-manager");
//            String response = service.register(request);
//            
//            // Recebendo resposta e adicionando ao bd de provider
//            System.out.println(response);
//            JSONObject jsonResponse = new JSONObject(response);
//            // Verifica o codigo de resposta do auth-manager
//            if (jsonResponse.getBoolean("success")) {
//                Provider remote = repo.getRemote(appId);
//                if (remote == null) {
//                    repo.insertRemote(insert);
//                    // Inserindo usuario para teste
//                    repo.insertUser(new User(appId, "rodrigo@gmail.com", "123"));
//                } 
//            } else {
//                // Codigo de erro
//                System.out.println(response);
//            }
//            
//            // Checando a validade de um User
//            
//            String token = "";
//            String appKey = "";
//            String toJSONCheck = trans.toJSONCheck(appKey, token);
//            String check = service.check(toJSONCheck);
//            System.out.println(check);
            
//        } catch (RemoteException | NotBoundException | AlreadyBoundException ex) {
//            ex.printStackTrace();
//        }
        } catch (RemoteException | AlreadyBoundException ex) {
            ex.printStackTrace();
        } 
    }
    
}
