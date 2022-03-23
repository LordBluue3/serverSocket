package lord.serverSocket.database;

import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import com.henryfabio.sqlprovider.executor.SQLExecutor;

import java.io.File;

public class SqlProvedor {


    public SQLExecutor criandoBd() {
        var connector = SQLiteDatabaseType.builder()
                .file(new File("database", "database/LDB.db"))
                .build().connect();
        var executor = new SQLExecutor(connector);

        executor.updateQuery("CREATE TABLE IF NOT EXISTS tbUsuarios  (\n" +
                "    id_usuarios INTEGER      PRIMARY KEY AUTOINCREMENT,\n" +
                "    email       VARCHAR (30) UNIQUE\n" +
                "                             NOT NULL,\n" +
                "    senha       VARCHAR (16) NOT NULL\n" +
                ")");

        return executor;
    }
}