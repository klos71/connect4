package client;

/**
 * Created by Axel Engberg on 6/9/2017.
 */
public class InputEvent {

    final private int column;

    public InputEvent(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
}
