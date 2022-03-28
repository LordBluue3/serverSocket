package lord.serverSocket.seguranca;



import com.henryfabio.sqlprovider.executor.SQLExecutor;
import lord.serverSocket.database.Usuario;
import lord.serverSocket.database.UsuarioAdapter;

import java.net.ServerSocket;

public class Consulta {
    private final ServerSocket socket;

    public Consulta(ServerSocket socket) {
        this.socket = socket;
    }
    public Usuario consultar(SQLExecutor executor,String login, String senha){
        Usuario usuario = executor.resultOneQuery("SELECT * FROM tbUsuarios where email =? and senha =?", s ->{
            s.set(1,login);
            s.set(2,senha);
        }, UsuarioAdapter.class);

        return usuario;
    }

}
