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
    public GameBoard board;


    public GameRoom(Connection playerOne, Connection playerTwo,String name,GameBoard board) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.name = name;
        this.board = board;

    }

    @Override
    public void run(){
        while(isRunning == true){

        }
    }

    public void dropDisc(int row, byte player){
        board.dropDisc(row,player);
    }

    public GameBoard getGameBoard(){
        return board;
    }

    public void start() {
        t = new Thread(this);
        t.start();
    }
}
