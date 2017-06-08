package server;

import com.esotericsoftware.kryonet.Connection;

/**
 * Created by klos71 on 08/06/2017.
 */
public class GameRoom implements Runnable{

    private boolean isRunning = true;
    Thread t;
    Connection playerOne;
    Connection playerTwo;


    public GameRoom(Connection playerOne, Connection playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

    }

    @Override
    public void run(){
        while(isRunning = true){
            //game logic
        }
    }

    public void start() {
        t = new Thread(this);
        t.start();
    }
}
