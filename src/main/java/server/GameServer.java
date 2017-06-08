package server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import packages.GamePacket;

import java.io.IOException;

/**
 * Created by klos71 on 08/06/2017.
 */
public class GameServer {

    public void run(){
        try{
            Server server = new Server();
            server.start();
            server.bind(8080);

            Kryo kryo = server.getKryo();
            kryo.register(GamePacket.class);


            server.addListener(new Listener() {
                public void received (Connection connection, Object object) {
                    if (object instanceof GamePacket) {
                        GamePacket request = (GamePacket) object;
                        System.out.println(request.GameRoomName);

                        request.GameRoomName = "test";
                        connection.sendTCP(request);
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
