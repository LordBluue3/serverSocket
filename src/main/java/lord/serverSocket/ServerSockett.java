package lord.serverSocket;

import lord.serverSocket.database.SqlProvedor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerSockett  {

    public static void main(String[] args) throws IOException {

           ServerSocket server = new ServerSocket(12345);
           SqlProvedor executor = new SqlProvedor();
           executor.criandoBd();

           while(true){

               Socket cliente = server.accept();
               System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
               ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
               saida.flush();
               saida.close();
               cliente.close();

           }

    }
}
