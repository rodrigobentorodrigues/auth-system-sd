package com.ifpb.sd.manager;

import com.ifpb.sd.infra.MarshallerJSON;
import com.ifpb.sd.datastore.RepoSingleton;
import com.ifpb.sd.domain.Provider;
import com.ifpb.sd.domain.Token;
import com.ifpb.sd.domain.User;
import com.ifpb.sd.infra.Translate;
import com.ifpb.sd.infra.TranslateImpl;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;
import com.ifpb.sd.remotes.AuthService;
import com.ifpb.sd.remotes.ProviderService;
import java.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe responsavel por realizar as operaçoes de: - autenticação de clientes,
 * -checagem de token, - inutilização de token, - registro de provedores
 *
 * @author rodrigobento
 */
public class AuthServiceImpl extends UnicastRemoteObject implements AuthService {

    private static final long serialVersionUID = 1L;

    private RepoSingleton repo;
    private MarshallerJSON transform;
    private Translate translate;
    private int cont = 0;

    public AuthServiceImpl() throws RemoteException {
        super();
        this.repo = RepoSingleton.getInstance();
        this.translate = new TranslateImpl();
        this.transform = new MarshallerJSON();
    }

    /**
     * Metodo responsavel por autenticar o usuario e retornar o token caso ele
     * exista no banco
     *
     * @param json: Contendo o appId, email e senha do usuario que deseja se
     * autenticar (buscar token)
     * @return json: Contendo a resposta do usuario, podendo ela ser o token ou
     * erro caso não consiga enviar requisição para o provider
     * @throws (Remote, NotBound, JSON) caso nao consiga se conectar com o
     * provider ou não consiga criar/gerar o json
     */
    @Override
    public String auth(String json) {
        // Constroi o objeto User com os dados da requisição
        User req = translate.fromJSONUser(json);
        // Busca os dados do provider a partir do appId informado
        Provider provider = repo.getRemote(req.getAppId());
        // Verifica se o mesmo existe, caso não exista ja é retornado um JSON contendo o codigo e descrição do erro
        if (provider != null) {
            try {                
                // Constroi um registro a partir do provider do banco de dados, para a verificação dos dados do usuario
                Registry registry = LocateRegistry.getRegistry(provider.getHost(),
                        provider.getPort());
                ProviderService service = (ProviderService) registry.
                        lookup(provider.getName());
                // JSON utilizado para guardar o token gerado e que sera enviado ao provider, para que seja adicionado ao bd
                JSONObject jsonAuth = new JSONObject(json);
                // Cria o token a partir do UUID
                String uniqueID = UUID.randomUUID().toString();
                jsonAuth.put("token", uniqueID);
                // Realiza a requisiçao ao Provider para verificar se o User está ou não cadastrado no provider
                String auth = service.auth(jsonAuth.toString());
                // Verificando a resposta advinda do Provider
                JSONObject objResponse = new JSONObject(auth);
                if (objResponse.getBoolean("is_valid")) {
                    // Para o teste de 200 req/s
                    User user = repo.getUser(req.getEmail(), req.getSenha());
                    repo.insertToken(new Token(uniqueID, true, LocalDateTime.now(), provider.getAppId(), user.getId()));
                    return transform.jsonSuccess(req.getAppId(), uniqueID);
//                    
                }
            }  catch (JSONException ex) {
            } catch (NotBoundException | RemoteException ex) {
                // Utilizado para garantir as tres tentativas referente a tolerancia a falha
                if (cont < 3) {
                    System.out.println("Invocando novamente o provedor, tentativa: " + cont);
                    cont++;
                    return auth(json);
                } else {
                    // Caso não consiga é retornado um JSON para o user contendo a descrição do erro e seu codigo
                    return transform.jsonError();
                }
            }
        }
        return transform.jsonAuth(req.getAppId(), false);
    }

    /**
     * Metodo responsavel por checar se o token do usuario existe e se ele esta
     * valido ou não
     *
     * @param json: contendo o appId e o token do usuario ou provider.
     * @return json: representando a resposta, sendo ela is_valid true ou false
     * @throws (JSONException): caso não consiga construir o objeto JSON
     */
    @Override
    public String check(String json) throws RemoteException {
        String appId = "";
        try {
            // Busca os valores da requisiçao
            JSONObject obj = new JSONObject(json);
            appId = obj.getString("appId");
            String valueToken = obj.getString("token");
            // Verifica se o token esta cadastrado no banco de dados
            Token token = repo.getToken(valueToken);
            if (token != null) {
                // Verifica se o mesmo é valido, retornando um json contendo is_valid como true
                if (token.isValid()) {
                    return transform.jsonChecked(appId, true);
                // retorna um json contendo is_valid como false
                } else {
                    // 
                    return transform.jsonChecked(appId, false);
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        // retorna o JSON contendo a descrição do erro, referente a criação do JSON
        return transform.jsonAuth(appId, false);
    }

    /**
     * Metodo responsavel por registrar o provider no sistema
     *
     * @param json: contendo os dados referente ao provider (appId, host, name e
     * port)
     * @return json: representando a resposta, sendo ela um success ou contendo
     * os erros referentes
     * @throws (RemoteException, NotBoundException, JSONException) caso nao
     * consiga se conectar com o provider ou não consiga criar/gerar o json
     */
    @Override
    public String register(String json) throws RemoteException {
        // Provider da requisição
        Provider req = translate.fromJSONProvider(json);
        // Verifica se ja existe o provider no banco de dados, caso sim so retorna o JSON
        Provider auth = repo.getRemote(req.getAppId());
        if (auth == null) {
            Registry reg = LocateRegistry.getRegistry(req.getHost(), req.getPort());
            try {
                // Realiza uma especie de "ping" com o remoto e caso seja 
                // concluido adiciona o provider no banco e retorna o JSON de sucesso
                ProviderService service = (ProviderService) reg.lookup(req.getName());
            } catch (NotBoundException | AccessException ex) {
                return transform.jsonAuth(req.getAppId(), false);
            }
            repo.insertRemote(req);
            // Cadastrando usuario para teste
            repo.insertUser(new User("sd-provider", "rodrigo@gmail.com", "123"));
        }
        return transform.jsonAuth(req.getAppId(), true);
    }

    /**
     * Metodo responsavel por inutilizar determinado token
     *
     * @param json: contendo os dados referente ao token e seu appId
     * @return json: representando a resposta, sendo ela um success ou contendo
     * os erros referentes
     * @throws (JSONException) caso nao consiga criar/gerar o json
     */
    @Override
    public String inutilize(String json) throws RemoteException {
        String appId = "";
        try {
            JSONObject obj = new JSONObject(json);
            appId = obj.getString("appId");
            String valueToken = obj.getString("token");
            // Invalida o token e verifica a resposta
            boolean invalidateToken = repo.invalidateToken(valueToken);
            if (invalidateToken) {
                System.out.println("Inutilizado");
                // Retorna success de inutilização
                return transform.jsonSuccess(appId, valueToken);
            } else {
                // Retorna json contendo o codigo de erro, pois não foi possivel sua inutilização
                return transform.jsonAuth(appId, false);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        // Retorna json contendo o codigo de erro, em consequencia da criaçao do JSON
        return transform.jsonAuth(appId, false);
    }

}
