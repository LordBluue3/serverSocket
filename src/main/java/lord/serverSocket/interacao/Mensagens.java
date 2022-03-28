package lord.serverSocket.interacao;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import lord.serverSocket.ServerSockett;
import lord.serverSocket.database.SqlProvedor;
import lord.serverSocket.database.Usuario;
import lord.serverSocket.seguranca.Consulta;
import lord.serverSocket.seguranca.Validar;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Mensagens {
    private final Socket socket;
    private List<String> listOfMessages = null;


    public Mensagens(Socket socket,List<String> listOfMessages) {
        this.socket = socket;
        this.listOfMessages = listOfMessages;
    }
    public void lerMensagens(){

        try {
            //obter o fluxo de entrada do soquete conectado
            InputStream inputStream = socket.getInputStream();
            //crie um DataInputStream para que possamos ler os dados dele.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            // Leia a lista de mensagens do socket

            try {
                listOfMessages = (List<String>) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Revisto [" + listOfMessages.size() + "] mensagem ao: " + socket);
        }catch (Exception e){
            System.out.println(e);

        }
    }

    public void imprimirMensagem(SQLExecutor executor, ServerSocket ss) {
        for (String msg : listOfMessages) {
            try {
                JSONObject parsed = (JSONObject) new JSONParser().parse(msg);
                System.out.println("Mensagem Recebida: ");
                String login = (String) parsed.get("login");
                String senha = (String) parsed.get("senha");

                //consulta no banco de dados
                Consulta consulta = new Consulta(ss);
                Usuario usuario = consulta.consultar(executor, login, senha);

                System.out.println(login);
                System.out.println(senha);

                //validando e enviando mensagem para o cliente
                Validar val = new Validar(socket, usuario);
                val.validar();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
