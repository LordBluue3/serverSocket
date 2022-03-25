package lord.serverSocket.database;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;

public class UsuarioAdapter implements SQLResultAdapter<Usuario> {
    @Override
    public Usuario adaptResult(SimpleResultSet simpleResultSet) {
        return new Usuario(simpleResultSet.get("email"),simpleResultSet.get("senha"));
    }
}
