package client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import packages.ClientPackage;
import packages.GameBoard;
import packages.GamePacket;
import packages.ServerString;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.SocketException;

/**
 * Created by klos71 on 08/06/2017.
 */
public class GameClient implements InputListener{

   private String roomName;
   private Client client;


   public void inputReceived(InputEvent ie) {
       System.out.println(ie.getColumn());
       ClientPackage request = new ClientPackage();
       request.row = ie.getColumn();
       request.gameRoomName = roomName;
       client.sendTCP(request);
   }
   public void start(){
       SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               GameClient client = new GameClient();
               MainFrame gui = new MainFrame();
               gui.setInputListener(client);
               //client.run();
           }
       });
       try {
           //Log.set(Log.LEVEL_TRACE);
           client = new Client();


           Kryo kryo = client.getKryo();
           kryo.register(ClientPackage.class);
           kryo.register(ServerString.class);
           kryo.register(GamePacket.class);
           kryo.register(GameBoard.class);

           client.start();
           client.connect(5000, "127.0.0.1", 8080, 8081);


           client.addListener(new Listener() {
               public void received (Connection connection, Object object) {
                   //connection.setKeepAliveTCP(8000);
                   if (object instanceof ServerString) {
                       ServerString response = (ServerString)object;
                       System.out.println(response.text);
                   }
                   if(object instanceof GameBoard){
                       GameBoard state = (GameBoard)object;
                       state.getBoardState();
                   }
                   if(object instanceof ServerString){
                       ServerString name = (ServerString)object;
                        roomName = name.text;
                   }
               }
           });

       }catch (IOException e){
            e.printStackTrace();
            System.out.println(e);
       }
   }



   public static void main(String[] args){
       new GameClient().start();

   }

}
