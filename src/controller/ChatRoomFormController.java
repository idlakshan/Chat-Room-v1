package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatRoomFormController extends Thread{

    public TextField txtMessage;
    public JFXTextArea txtAreaMessage;
    public Label lblName;
    public AnchorPane chatRoomContext;


    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public void initialize(){
        lblName.setText(LoginFormController.username);
        try {
            socket = new Socket("localhost", 3000);
            System.out.println("Client is Connected");
            bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter=new PrintWriter(socket.getOutputStream(),true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        try {
            while (true) {

                String msg = bufferedReader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]);
                }
                System.out.println(fullMsg);

                System.out.println("cmd="+cmd+"-----"+"UserName "+lblName.getText());
                if(!cmd.equalsIgnoreCase(lblName.getText()+":")){
                    txtAreaMessage.appendText(msg + "\n");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void send() {
        String msg = txtMessage.getText();
        printWriter.println(lblName.getText() + ": " + txtMessage.getText());
        txtMessage.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        txtAreaMessage.appendText("Me: " + msg + "\n");
        txtMessage.clear();
        if (msg.equalsIgnoreCase("Bye") || (msg.equalsIgnoreCase("Logout"))) {
            System.exit(0);
        }
    }


    public void btnSendMsgOnAction(ActionEvent event) {
       send();


    }
}
