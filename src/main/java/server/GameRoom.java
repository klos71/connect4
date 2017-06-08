package server;

import com.esotericsoftware.kryonet.Connection;
import packages.GameBoard;

/**
 * Created by klos71 on 08/06/2017.
 */
public class GameRoom implements Runnable{

    private boolean isRunning = true;
    Thread t;
    public Connection playerOne;
    public Connection playerTwo;
    String name;


    public GameRoom(Connection playerOne, Connection playerTwo,String name) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.name = name;

    }

    @Override
    public void run(){
        while(isRunning == true){

        }
    }

    public void dropDisc(int row){

    }

    public GameBoard getGameBoard(){
        return null;
    }

    public void start() {
        t = new Thread(this);
        t.start();
    }
}
