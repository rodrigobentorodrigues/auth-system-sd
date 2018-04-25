package com.ifpb.sd.repository;

import com.ifpb.sd.domain.Provider;
import com.ifpb.sd.domain.Token;
import com.ifpb.sd.domain.User;
import java.util.List;

/**
 *
 * @author rodrigobento
 */
public class RepoSingleton {
    
    private static RepoSingleton instance;
    private UserCRUD userCrud;
    private RemoteRefCRUD remoteCrud;
    private TokenCRUD tokenCrud;
    
    private RepoSingleton(){
        this.userCrud = new UserCRUD();
        this.remoteCrud = new RemoteRefCRUD();
        this.tokenCrud = new TokenCRUD();
    }
    
    public static RepoSingleton getInstance(){
        if(instance == null){
            instance = new RepoSingleton();
            return instance;
        } else {
            return instance;
        }
    }
    
    public int insertUser(User user){
        return userCrud.create(user);
    }
    
    public boolean insertRemote(Provider provider){
        return remoteCrud.create(provider);
    }
    
    public boolean removeUser(User user){
        return userCrud.delete(user);
    }
    
    public boolean removeRef(Provider provider){
        return remoteCrud.delete(provider);
    }
    
    public boolean updateUser(User user){
        return userCrud.update(user);
    }
    
    public boolean updateRemote(Provider provider){
        return remoteCrud.update(provider);
    }
    
    public User getUser(String email, String senha){
        return userCrud.getUser(email, senha);
    }
    
    public Provider getRemote(String appId){
        return remoteCrud.readRemote(appId);
    }
    
    public boolean insertToken(Token token){
        return tokenCrud.insert(token);
    }    
    
    public boolean invalidateToken(String token){
        return tokenCrud.invalidateToken(token);
    }
    
    public List<Token> listValid(){
        return tokenCrud.listValid();
    }
    
    public Token getToken(int idUser){
        return tokenCrud.getToken(idUser);
    }
    
}
