package server;

public class GameBoard {

    private byte[][] boardState;

    public GameBoard(){
        reset();
    }

    public GameBoard(byte[][] initialState) {
        System.arraycopy(initialState, 0, boardState, 0, initialState.length);
    }

    public int topSlot(int column) {
        int y = 5;
        while (y > 0 && boardState[column][y - 1] == 0) {
            y--;
        }
        return y;
    }

    public boolean dropDisc(int column, byte player) {
        if (columnFull(column)) return false;

        int y = topSlot(column);
        boardState[column][y] = player;

        return true;
    }

    public boolean columnFull(int column) {
        return boardState[column][5] != 0;
    }

    public boolean isFull() {
        for (int i = 0; i < 7; i++) {
            if (boardState[i][5] == 0) {
                return false;
            }
        }
        return true;
    }

    public byte[][] getBoardState() {
        return boardState;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 5; y >= 0; y--) {
            for (int x = 0; x < 7; x++) {
                if (x != 6) {
                    sb.append(boardState[x][y] + " ");
                } else {
                    sb.append(boardState[x][y]);
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public boolean checkWin() {
        return false;
    }

    public void reset() {
        boardState = new byte[][] {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };
    }
}