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
            socket=new Socket("localhost",3000);
            System.out.println("Client is Connected the Server");
            bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter=new PrintWriter(socket.getOutputStream(),true);
        }catch (IOException e){
            e.printStackTrace();

        }
    }
    @Override
    public void run(){
        try {
            while (true){
                String msg=bufferedReader.readLine();
                String[] token=msg.split(" ");
                String cmd=token[0];
                System.out.println(cmd);
                StringBuilder stringBuilder=new StringBuilder();
                for (int i=0;i<token.length;i++){
                    stringBuilder.append(token[i]);

                }
                System.out.println(stringBuilder);
                System.out.println("cmd "+cmd+" --- "+"Username "+lblName.getText());
                if (!cmd.equalsIgnoreCase(lblName.getText()+" : "));{
                    txtAreaMessage.appendText(msg+"\n");
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void btnSendMsgOnAction(ActionEvent event) {
        String msg=txtMessage.getText();
        printWriter.print(lblName.getText()+": "+txtMessage.getText());
        txtMessage.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        txtAreaMessage.appendText("Me: "+msg+"\n");


    }
}
