import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    private ArrayList<ClientHandler> clientHandlers;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clientHandlers) {

        try {
            this.socket = socket;
            this.clientHandlers = clientHandlers;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try {
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                if (msg.equalsIgnoreCase("exit")) {
                    break;
                }
                for (ClientHandler cl : clientHandlers) {
                    cl.printWriter.println(msg);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        finally {
            try {
                bufferedReader.close();
                printWriter.close();
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
