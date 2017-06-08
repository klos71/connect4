package server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import packages.GamePacket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by klos71 on 08/06/2017.
 */
public class GameServer {

    private List<Connection> players = new ArrayList<>();
    private List<GameRoom> GameRooms = new ArrayList<>();

    public void run(){
        try{
            Server server = new Server();
            server.start();
            server.bind(8080);

            Kryo kryo = server.getKryo();
            kryo.register(GamePacket.class);


            server.addListener(new Listener() {
                public void received (Connection connection, Object object) {
                    players.add(connection);
                    if(players.size() >= 2){
                        GameRoom newGame = new GameRoom(players.get(0),players.get(1));
                        for(int i = 0; i > players.size();i++){
                            players.remove(i);
                        }
                        GameRooms.add(newGame);
                        newGame.start();
                    }

                    if (object instanceof GamePacket) {
                        
                    }
                }
            });
        }catch (IOException e){

        }



    }

    public static void main(String[] args){
        GameServer server = new GameServer();
        server.run();
    }
}
