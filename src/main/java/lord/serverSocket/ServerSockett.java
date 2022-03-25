package lord.serverSocket;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import lord.serverSocket.database.SqlProvedor;
import lord.serverSocket.database.Usuario;
import lord.serverSocket.database.UsuarioAdapter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class ServerSockett  {

    public static void main(String[] args) throws IOException {

           ServerSocket ss = new ServerSocket(12345);
           SQLExecutor executor = new SqlProvedor().criandoBd();


           while(true) {

               // don't need to specify a hostname, it will be the current machine
               System.out.println("ServerSocket awaiting connections...");
               Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
               System.out.println("Connection from " + socket + "!");

               // get the input stream from the connected socket
               InputStream inputStream = socket.getInputStream();
               // create a DataInputStream so we can read data from it.
               ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

               // read the list of messages from the socket
               List<String> listOfMessages = null;
               try {
                   listOfMessages = (List<String>) objectInputStream.readObject();
               } catch (ClassNotFoundException e) {
                   e.printStackTrace();
               }
               System.out.println("Received [" + listOfMessages.size() + "] messages from: " + socket);
               // print out the text of every message
               System.out.println("All messages:");
               for (String msg : listOfMessages) {
                   try {
                       JSONObject parsed = (JSONObject) new JSONParser().parse(msg);
                       System.out.println("Mensagem: ");
                       String login = (String) parsed.get("login");
                       String senha = (String) parsed.get("senha");

                       Usuario usuario = executor.resultOneQuery("SELECT * FROM tbUsuarios where email =? and senha =?", s ->{
                           s.set(1,login);
                           s.set(2,senha);
                       }, UsuarioAdapter.class);
                       if(usuario == null){
                           System.out.println("Valor null");
                       }else {
                           System.out.println("Acesso permitido");
                       }
                       System.out.println("Closing sockets.");
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
                   socket.close();
               }
           }
    }
}
