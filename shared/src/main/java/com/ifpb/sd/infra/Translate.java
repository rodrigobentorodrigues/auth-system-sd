package com.ifpb.sd.infra;

import com.ifpb.sd.domain.Provider;
import com.ifpb.sd.domain.User;

/**
 *
 * @author rodrigobento
 */
public interface Translate {
    
    String toJSONRequestClient(User user);
    String toJSONRequestProvider(Provider provider);
    String toJSONCheck(String appId, String token);
    User fromJSONUser(String json);
    Provider fromJSONProvider(String json);
    
}
