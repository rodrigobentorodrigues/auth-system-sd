package com.ifpb.sd.repository;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rodrigobento
 */
public class ConFactory {

    public static Connection getConnection() throws SQLException {
        File repository = new File("repo.mv.db");
        if (!repository.exists()) {
            return DriverManager.
                    getConnection("jdbc:h2:file:./repo;DB_CLOSE_DELAY=-1;"
                            + "INIT=RUNSCRIPT FROM 'classpath:script.sql'\\;", 
//                            + "INIT=RUNSCRIPT FROM './src/main/resources/script.sql'\\;", 
                            "sa", "");
        } else {
            return DriverManager.
                    getConnection("jdbc:h2:file:./repo;DB_CLOSE_DELAY=-1;",
                            "sa", "");
        }
    }

}
