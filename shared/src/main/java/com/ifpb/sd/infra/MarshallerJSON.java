package com.ifpb.sd.infra;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe responsável por converter todos os dados que serão enviados e
 * recebidos pelo client, provider e auth-manager
 *
 * @author rodrigobento
 */
public class MarshallerJSON implements Marshaller {

    @Override
    public String jsonSuccess(String appId, String token) {
        JSONObject json = new JSONObject();
        try {
            json.put("appkey", appId);
            json.put("token", token);
            json.put("success", true);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public String jsonAuth(String appId, boolean result) {
        JSONObject json = new JSONObject();
        try {
            json.put("appkey", appId);
            if (result) {
                json.put("success", result);
            } else {
                json.put("success", result);
                json.put("error_code", 999);
                json.put("error_msg", "Auth Fail");
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public String jsonChecked(String appId, boolean response) {
        JSONObject json = new JSONObject();
        try {
            json.put("appkey", appId);
            json.put("is_valid", response);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public String jsonError() {
        JSONObject json = new JSONObject();
        try {
            json.put("message", "Não foi possível realizar autenticação devido a falha no servidor.");
            json.put("codigo", 99999);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return json.toString();
    }

}
