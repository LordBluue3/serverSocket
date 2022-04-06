package lord.serverSocket;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import lord.serverSocket.database.SqlProvedor;
import lord.serverSocket.database.Usuario;
import lord.serverSocket.interacao.Mensagens;
import lord.serverSocket.seguranca.Consulta;
import lord.serverSocket.seguranca.Validar;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ServerSockett  {

    public static int contador = 1;

    public static void main(String[] args) throws IOException {

           ServerSocket ss = new ServerSocket(12345);
           SQLExecutor executor = new SqlProvedor().criandoBd();
           List<String> listOfMessages = null;
           Socket socket;


           while(true) {
               System.out.println("ServerSocket está conectando...");
               System.out.println("Server conectado, aguardando requisições!!!");

               socket = ss.accept(); //faz com que o while não continue a menos que uma mensagem seja requisitada
               System.out.println("Conectado ao " + socket + "!");

               //ler mensagem do cliente
               List listOfmessagen = new ArrayList<String>();
               Mensagens mensagem = new Mensagens(socket, listOfMessages);
               mensagem.lerMensagens();

               // imprimir o texto de cada mensagem
               mensagem.imprimirMensagem(executor,ss,contador);
               socket.close();

               }

           }
    }
