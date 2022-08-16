import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static ArrayList<ClientHandler> clientHandlers=new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(3000);
        Socket accept;

        while (true){
            System.out.println("Server is started");
            accept=serverSocket.accept();
            System.out.println("Client is Connected");
            ClientHandler thread=new ClientHandler(accept, clientHandlers);
            clientHandlers.add(thread);
            thread.start();

        }
    }
}
