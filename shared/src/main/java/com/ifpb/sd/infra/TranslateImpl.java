package com.ifpb.sd.infra;

import com.ifpb.sd.domain.Provider;
import com.ifpb.sd.domain.User;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe responsavel por realizar todas as conversões referente aos JSON e as
 * classes de modelo
 *
 * @author rodrigobento
 */
public class TranslateImpl implements Translate {

    @Override
    public String toJSONRequestClient(User user) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", user.getId());
            json.put("email", user.getEmail());
            json.put("senha", user.getSenha());
            json.put("appId", user.getAppId());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public String toJSONRequestProvider(Provider provider) {
        JSONObject jsonToken = new JSONObject();
        JSONObject aggregate = new JSONObject();
        try {
            jsonToken.put("host", provider.getHost());
            jsonToken.put("name", provider.getName());
            jsonToken.put("port", provider.getPort());
            aggregate.put("uri", provider.getAppId());
            aggregate.put("token", jsonToken);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return aggregate.toString();
    }

    @Override
    public String toJSONCheck(String appId, String token) {
        JSONObject jsonCheck = new JSONObject();
        try {
            jsonCheck.put("token", token);
            jsonCheck.put("appId", appId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return jsonCheck.toString();
    }

    @Override
    public User fromJSONUser(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            User user = new User();
            user.setId(obj.getInt("id"));
            user.setEmail(obj.getString("email"));
            user.setSenha(obj.getString("senha"));
            user.setAppId(obj.getString("appId"));
            return user;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Provider fromJSONProvider(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject token = obj.getJSONObject("token");
            Provider provider = new Provider();
            provider.setAppId(obj.getString("uri"));
            provider.setHost(token.getString("host"));
            provider.setName(token.getString("name"));
            provider.setPort(token.getInt("port"));
            return provider;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
