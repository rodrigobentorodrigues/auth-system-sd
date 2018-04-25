package com.ifpb.sd.infra;

/**
 *
 * @author rodrigobento
 */
public interface Marshaller {
    
    String jsonSuccess(String appId, String token);
    String jsonAuth(String appId, boolean result);
    String jsonChecked(String appId, boolean response);
    String jsonError();
    
}
