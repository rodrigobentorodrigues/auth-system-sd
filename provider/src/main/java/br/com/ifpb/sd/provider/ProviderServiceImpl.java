package br.com.ifpb.sd.provider;

import com.ifpb.sd.domain.Token;
import com.ifpb.sd.domain.User;
import com.ifpb.sd.infra.Marshaller;
import com.ifpb.sd.infra.MarshallerJSON;
import com.ifpb.sd.infra.Translate;
import com.ifpb.sd.infra.TranslateImpl;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import com.ifpb.sd.remotes.ProviderService;
import com.ifpb.sd.repository.RepoSingleton;
import java.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe responsavel por verificar se o user esta ou nao cadastrado no provider
 *
 * @author rodrigobento
 */
public class ProviderServiceImpl extends UnicastRemoteObject implements ProviderService {

    private static final long serialVersionUID = 1L;

    private RepoSingleton repo;
    private Marshaller transform;
    private Translate trans;

    public ProviderServiceImpl() throws RemoteException {
        super();
        this.repo = RepoSingleton.getInstance();
        this.transform = new MarshallerJSON();
        this.trans = new TranslateImpl();
    }

    /**
     * Metodo responsavel por verificar se o user esta cadastrado no banco de
     * dados do provider
     *
     * @param json: Contendo o appId, email e senha do usuario que deseja se
     * autenticar (buscar token)
     * @return json: Contendo a resposta do usuario, podendo ela ser o token ou
     * erro caso não consiga enviar requisição para o provider
     * @throws (JSONException) caso não consiga criar/gerar o json
     */
    @Override
    public String auth(String json) throws RemoteException {
        // Constroi o user a partir dos dados advindos da requisição
        User user = trans.fromJSONUser(json);
        try {
            // Verifica se o mesmo esta cadastrado no bd
            User aux = repo.getUser(user.getEmail(), user.getSenha());
            if (aux != null) {
                // Caso exista, insere o token no banco de dados do provider (para caso ele deseje verificar os existentes e tentar inutilizar algum)
                JSONObject jsonToken = new JSONObject(json);
                String token = jsonToken.getString("token");
                repo.insertToken(new Token(token, true,
                        LocalDateTime.now(), aux.getAppId(), aux.getId()));
                // Retorna o JSON referente ao sucesso da autenticação
                return transform.jsonChecked(user.getAppId(), true);
            } else {
                // Retorna o JSON referente ao erro da autenticação
                return transform.jsonChecked(user.getAppId(), false);
            }
        } catch (JSONException ex) {
            // Retorna o JSON referente a um erro na construção JSON a partir dos dados do requisitante
            return transform.jsonChecked(user.getAppId(), false);
        }
    }

}
