package com.ifpb.sd.manager;

import com.ifpb.sd.datastore.RepoSingleton;
import com.ifpb.sd.domain.Token;
import com.ifpb.sd.infra.Translate;
import com.ifpb.sd.remotes.AuthService;
import java.rmi.RemoteException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe responsavel por verificar se os tokens existentes no banco ja passaram
 * da hora para ser inspirado (24 horas/1 dia)
 *
 * @author rodrigobento
 */
public class InutilizeWorker implements Runnable {

    private RepoSingleton repo;
    private AuthService service;
    private Translate trans;

    public InutilizeWorker(RepoSingleton repo, AuthService service, Translate trans) {
        this.repo = repo;
        this.service = service;
        this.trans = trans;
    }

    @Override
    public void run() {
        System.out.println("Serviço de inutilização em execução");
        while (true) {
            try {
                // Busca todos os tokens que ainda estão ativos
                List<Token> listValid = repo.listValid();
                for (Token token : listValid) {
                    // Verifica se a diferença entre a data/hora do token é maior que a data atual (diferença de 1 dia)
                    LocalDateTime atual = LocalDateTime.now();
                    Duration duration = Duration.between(token.getDateHour(), atual);
                    long durationDays = duration.toDays();
                    if (durationDays >= 1) {
                        // Inutiliza o token, realizando uma requisição ao auth-manager
                        service.inutilize(trans.toJSONCheck(token.getProvider(),
                                token.getValue()));
                    }
                }
                // Aguarda 10 seg para verificar novamente
//                System.out.println("Aguardando 10 seg");
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

}
