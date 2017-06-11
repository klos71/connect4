package server;

import java.io.*;
import java.util.Scanner;

public class Analyzer {

    private int tot,red,yellow,redPrcent,YellowPrecent;

    public Analyzer() throws IOException{
        readFile();
        tot = red + yellow;
        redPrcent = (tot/redPrcent)*100;
        YellowPrecent = (tot/YellowPrecent)*100;
    }

    public void saveWinner(int player){
        try{
            PrintWriter writer = new PrintWriter("winner.txt", "UTF-8");
            writer.println("Red|Yellow");
            if(player == 1){
                writer.println(red+1 + " " + yellow);
            }else if(player == 2){
                writer.println(red + " " + yellow+1);
            }
            writer.println("Red%:" + redPrcent + "Yellow%: " + YellowPrecent);

            writer.close();
        } catch (IOException e) {
            // do something
        }

    }
    public void readFile() throws IOException{
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(new File("winner.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc2.nextLine();
        red = Integer.parseInt(sc2.next());
        yellow = Integer.parseInt(sc2.next());


    }
}
