package client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import packages.GameBoard;
import packages.GamePacket;


import javax.swing.*;
import java.io.IOException;

/**
 * Created by klos71 on 08/06/2017.
 */
public class GameClient {

   public void run(){
      try{
         Client client = new Client();
         client.start();
         client.connect(5000, "127.0.0.1", 8080);

         Kryo kryo = client.getKryo();
         kryo.register(GamePacket.class);

         if(client.isConnected()){
            System.out.println("connected");
         }

         GamePacket packet = new GamePacket();
         packet.GameRoomName = "test123";
         client.sendTCP(packet);



         client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
               connection.setKeepAliveTCP(5000);


               if (object instanceof GameBoard) {
                  GameBoard response = (GameBoard) object;
                  response.getBoardState();
               }
            }
         });
      }catch (IOException e){

      }

   }


   public static void main(String[] args){

       SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               new MainFrame();
           }
       });

       GameClient client = new GameClient();
       client.run();
   }

}
