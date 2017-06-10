package server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import packages.ClientPackage;
import packages.ServerString;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by klos71 on 08/06/2017.
 */
public class GameServer{

    private List<Connection> players = new ArrayList<>();
    private List<GameRoom> GameRooms = new ArrayList<>();
    private String[] names = {"room1", "room2", "room3", "room4"};

    public void run() {

        try{
            //Log.set(Log.LEVEL_TRACE);
            Server server = new Server();
            server.start();
            server.bind(8080, 8081);

            Kryo kryo = server.getKryo();
            kryo.register(ClientPackage.class);
            kryo.register(ServerString.class);

            server.addListener(new Listener() {
                public void received (Connection connection, Object object) {
                    if (object instanceof ClientPackage) {
                        ClientPackage request = (ClientPackage)object;
                        System.out.println(request.gameRoomName);

                        ServerString response = new ServerString();
                        response.text = "Thanks";
                        connection.sendTCP(response);

                    }
                }
            });
        }catch (IOException e){

        }

        /*try {
            Server server = new Server();
            server.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    //connection.setKeepAliveTCP(8000);
                    players.add(connection);
                    if (players.size() >= 2) {
                        int index = 0;
                        GameRoom newGame = new GameRoom(players.get(0), players.get(1), names[index], new GameBoard());
                        for (int i = 0; i > players.size(); i++) {
                            players.remove(i);
                        }
                        GameRooms.add(newGame);
                        newGame.start();
                    }

                    if (object instanceof ClientPackage) {
                        ClientPackage request = (ClientPackage) object;
                        System.out.println(request.row);
                        for (int i = 0; i > GameRooms.size(); i++) {
                            if (GameRooms.get(i).name.equals(request.gameRoomName)) {
                                if (connection.getID() == GameRooms.get(i).playerOne.getID()) {
                                    //drop disc for player
                                    //GameRooms.get(i).dropDisc(request.row,player);
                                } else if (connection.getID() == GameRooms.get(i).playerTwo.getID()) {
                                    //drop disc for player 2
                                    //GameRooms.get(i).dropDisc(request.row,player);
                                }

                                //send boardstate to players
                                GameBoard state = (GameBoard) object;

                                server.sendToTCP(GameRooms.get(i).playerTwo.getID(), state);
                                server.sendToTCP(GameRooms.get(i).playerTwo.getID(), state);
                            }
                        }
                    }
                }
            });*/
    }

    public static void main(String[] args){
        GameServer server = new GameServer();
        server.run();
    }

}
