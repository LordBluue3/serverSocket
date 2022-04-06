package lord.serverSocket.seguranca;

import lord.serverSocket.ServerSockett;
import lord.serverSocket.database.Usuario;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class Validar {
    private final Socket socket;
    private final Usuario usuario;





    public Validar(Socket socket, Usuario usuario) {
        this.socket = socket;
        this.usuario = usuario;

    }

    public void validar(){


        //Enviando mensagem para o cliente
        DataOutputStream dos = null;
        System.out.print("Enviando a resposta: ");

        try {
            dos = new DataOutputStream(socket.getOutputStream());

            if(usuario == null){
                System.out.println("Login inválido");
                dos.writeUTF("Login inválido");
                    dos.writeUTF(String.valueOf(ServerSockett.contador));
                    System.out.println("numero da tentativa: "+ServerSockett.contador);

                    if(ServerSockett.contador == 3){
                        ServerSockett.contador = 1;
                    }else {
                         ServerSockett.contador =  ServerSockett.contador+1;
                    }

            }else {
                System.out.println("Logado com sucesso!!");
                dos.writeUTF("Logado com sucesso");
            }
            System.out.println("Fechando sockets.");
            System.out.println("=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

