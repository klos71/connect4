package server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.BlowfishSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import packages.*;


import javax.crypto.KeyGenerator;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by klos71 on 08/06/2017.
 */
public class GameServer{

    private List<Connection> players = new ArrayList<>();
    private List<GameRoom> GameRooms = new ArrayList<>();
    private String[] names = {"room1", "room2", "room3", "room4"};
    private int index = 0;

    public void run() {
        try {
            Server server = new Server();
            byte[] key = null;
            key = KeyGenerator.getInstance("Blowfish").generateKey().getEncoded();
            Kryo kryo = server.getKryo();
            kryo.register(ClientPackage.class);
            kryo.register(ServerString.class);
            kryo.register(GamePacket.class);
            kryo.register(GameBoard.class);
            kryo.register(byte[][].class);
            kryo.register(byte[].class);
            kryo.register(byte.class);
            kryo.register(WinPacket.class);
            kryo.register(String.class, new BlowfishSerializer(new DefaultSerializers.StringSerializer(), key));
            server.start();
            server.bind(8080, 8081);
            server.addListener(new Listener() {
                public void connected(Connection c){
                    players.add(c);
                    System.out.println(players.size());
                    if (players.size() >= 2) {
                        System.out.println("creating gameroom");
                        GameRoom newGame = new GameRoom(players.get(0), players.get(1), names[index], new GameBoard());
                        ServerString string = new ServerString();
                        string.text = names[index];
                        server.sendToTCP(players.get(0).getID(),string);
                        server.sendToTCP(players.get(1).getID(),string);

                        players.clear();

                        GameRooms.add(newGame);
                        newGame.start();
                        index++;
                    }
                }

                public void received(Connection connection, Object object) {
                    if (object instanceof ClientPackage) {
                        ClientPackage request = (ClientPackage) object;
                        System.out.println("client roomname: " + request.gameRoomName);
                        System.out.println("client row " + request.row);
                        System.out.println("Gamerooms amount "+GameRooms.size());
                        for (int i = 0; i < GameRooms.size(); i++) {
                            System.out.println(GameRooms.get(i).name);
                            if (GameRooms.get(i).name.equals(request.gameRoomName)) {
                                if (connection.getID() == GameRooms.get(i).playerOne.getID()) {
                                    System.out.println("player 1 drops disc");
                                    //drop disc for player
                                    GameRooms.get(i).dropDisc(request.row,(byte)(GameRooms.get(i).playerOne.getID()%2+1));
                                    if(GameRooms.get(i).win = GameRooms.get(i).getGameBoard().checkWin()){
                                        WinPacket win = new WinPacket();
                                        win.ID = GameRooms.get(i).playerOne.getID();
                                        server.sendToTCP(GameRooms.get(i).playerOne.getID(),win);
                                        server.sendToTCP(GameRooms.get(i).playerTwo.getID(),win);
                                        try{
                                            new Analyzer().saveWinner(1);
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }


                                    }
                                } else if (connection.getID() == GameRooms.get(i).playerTwo.getID()) {
                                    System.out.println("player 2 drops disc");
                                    //drop disc for player 2
                                    GameRooms.get(i).dropDisc(request.row,(byte)(GameRooms.get(i).playerTwo.getID()%2+1));
                                    if(GameRooms.get(i).win = GameRooms.get(i).getGameBoard().checkWin()){
                                        WinPacket win = new WinPacket();
                                        win.ID = GameRooms.get(i).playerTwo.getID();
                                        server.sendToTCP(GameRooms.get(i).playerOne.getID(),win);
                                        server.sendToTCP(GameRooms.get(i).playerTwo.getID(),win);
                                        try{
                                            new Analyzer().saveWinner(2);
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                //send boardstate to players
                                GameBoard state = GameRooms.get(i).getGameBoard();


                                System.out.println("Sending board state");
                                server.sendToTCP(GameRooms.get(i).playerOne.getID(), state);
                                server.sendToTCP(GameRooms.get(i).playerTwo.getID(), state);

                            }
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException n){
            n.printStackTrace();
        }
    }

    public static void main(String[] args){
        GameServer server = new GameServer();
        server.run();

    }

}
