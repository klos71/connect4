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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.SocketException;

/**
 * Created by klos71 on 08/06/2017.
 */
public class GameClient implements InputListener{


   private String roomName;
   private Client client;

    //public static Client client;
   private InputEvent ie;
   private MainFrame gui;





   public void inputReceived(InputEvent ie) {
       System.out.println(ie.getColumn());
       ClientPackage request = new ClientPackage();
       request.row = ie.getColumn();
       request.gameRoomName = roomName;
       client.sendTCP(request);
   }

   public void start(){
       //SwingUtilities.invokeLater(new Runnable() {

               MainFrame g = new MainFrame();
               gui = g;
               gui.setInputListener(this);
               //client.run();

       try {
           //Log.set(Log.LEVEL_TRACE);
           client = new Client();


           Kryo kryo = client.getKryo();
           kryo.register(ClientPackage.class);
           kryo.register(ServerString.class);
           kryo.register(GamePacket.class);
           kryo.register(GameBoard.class);
           kryo.register(byte[][].class);
           kryo.register(byte[].class);
           kryo.register(byte.class);

           client.start();
           client.connect(5000, "127.0.0.1", 8080, 8081);


           client.addListener(new Listener() {
               public void received (Connection connection, Object object) {
                   //connection.setKeepAliveTCP(8000);
                   if(object instanceof GameBoard){
                       System.out.println("updateing gameboard gui");
                       GameBoard board = (GameBoard)object;
                       byte[][] boardState = board.getBoardState();

                       for (int x = 0; x < 7; x++) {
                           for (int y = 0; y < 6; y++) {
                               if (boardState[x][y] == (byte) 1) {
                                   gui.getButton(x, y).setForeground(Color.RED);
                               } else if (boardState[x][y] == (byte) 2) {
                                   gui.getButton(x, y).setForeground(Color.YELLOW);
                               }
                           }
                       }

                   }
                   if(object instanceof ServerString){

                       ServerString name = (ServerString)object;
                       System.out.println("roomname: " + name.text);
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
