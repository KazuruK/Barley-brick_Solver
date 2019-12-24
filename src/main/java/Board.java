import java.util.*;

public class Board implements Cloneable {
    private int row = 3;
    private int column = 3;

    private int[][] numbers = {
            { 1, 2, 3, 4 },
            { 5, 6, 7, 8 },
            { 9, 10, 11, 12 },
            { 13, 14, 15, 0 }
    };

    private Deque<Move> movesStack = new ArrayDeque<>();
    private Deque<Integer> manhattanStack = new ArrayDeque<>();

    public Board() {
        manhattanStack.addLast(firstCount());
    }

    public Board(int[][] numbers) {
        this.numbers = numbers;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (numbers[i][j] == 0) {
                    row = i;
                    column = j;
                }
            }
        }
        manhattanStack.addLast(firstCount());
    }

    static Board randomBoard() {
        Board b = new Board();
        b.manhattanStack.pollLast();
        b.mix();
        b.manhattanStack.addLast(b.firstCount());
        return b;
    }

    void move(Move move) {
        move(move, countManhattan(move));
    }

    void move(Move move, int h) {
        checkMove(move);
        movesStack.addLast(move);
        manhattanStack.addLast(manhattanStack.getLast() + h);
        switch (move) {
            case L:
                swapColumns(column, column - 1);
                column--;
                break;
            case R:
                swapColumns(column, column + 1);
                column++;
                break;
            case U:
                swapRows(row, row - 1);
                row--;
                break;
            case D:
                swapRows(row, row + 1);
                row++;
        }
    }

    void checkMove(Move move) {
        if (!isMoveLegal(move)) throw new IllegalArgumentException("Illegal move");
    }

    void swapRows(int r1, int r2) {
        int temp = numbers[r1][column];
        numbers[r1][column] = numbers[r2][column];
        numbers[r2][column] = temp;
    }

    void swapColumns(int c1, int c2) {
        int temp = numbers[row][c1];
        numbers[row][c1] = numbers[row][c2];
        numbers[row][c2] = temp;
    }

    boolean isMoveLegal(Move move) {
        if (move.opposite() == movesStack.peekLast()) return false;
        switch (move) {
            case L:
                if (column == 0) return false;
                break;
            case R:
                if (column == 3) return false;
                break;
            case U:
                if (row == 0) return false;
                break;
            case D:
                if (row == 3) return false;
                break;
        }
        return true;
    }

    public int getManhattan() {
        return manhattanStack.getLast();
    }

    public int countManhattan(Move move) {
        switch (move) {
            case L:
                return countManhattan(row, column - 1);
            case R:
                return countManhattan(row, column + 1);
            case U:
                return countManhattan(row - 1, column);
            case D:
                return countManhattan(row + 1, column);
        }
        throw new IllegalStateException();
    }

    private int countManhattan(int rowOfCell, int columnOfCell) {
        int sum = 0;
        int num = numbers[rowOfCell][columnOfCell] - 1;
        int numMod = num % 4;
        int numDel = num >> 2;
        sum -= Math.abs((numDel) - rowOfCell) + Math.abs(numMod - columnOfCell)
                - Math.abs((numDel) - row) - Math.abs(numMod - column);
        return sum;
    }

    void takeMoveBack() {
        Move move = movesStack.pollLast();
        manhattanStack.pollLast();
        if (move == null) throw new IllegalStateException("Taking back move when no moves was");
        switch (move) {
            case L:
                swapColumns(column, ++column);
                break;
            case R:
                swapColumns(column, --column);
                break;
            case U:
                swapRows(row, ++row);
                break;
            case D:
                swapRows(row, --row);
        }
    }

    boolean canBeSolved() {
        return canBeSolved(numbers);
    }

    boolean canBeSolved(int[][] ar) {
        int sum = 0;
        for (int i = 0; i < 16; i++) {
            int iDel = i >> 2;
            int iMod = i % 4;
            if (ar[iDel][iMod] == 0) {
                sum += iDel + 1;
            } else {
                for (int j = i + 1; j < 16; j++) {
                    int jDel = j >> 2;
                    int jMod = j % 4;
                    if (ar[iDel][iMod] > ar[jDel][jMod]
                            && ar[jDel][jMod] != 0)
                    {
                        sum++;
                    }
                }
            }
        }
        return sum % 2 == 0;
    }

    boolean isSolved() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != 3 || j != 2) {
                    if (numbers[i][j] > numbers[(i + (j / 3)) % 4][(j + 1) % 4]) return false;
                }
            }
        }
        return true;
    }

    Move[] getPath() {
        return movesStack.toArray(new Move[0]);
    }

    private int firstCount() {
        int sum = 0;
        for (int i = 0; i < 16; i++) {
            if (numbers[row][column] != 0) {
                int a = numbers[row][column] - 1;
                sum += Math.abs((a >> 2) - row) + Math.abs(a % 4 - column);
            }
        }
        return sum;
    }

    private void mix() {
        int[][] res = new int[4][4];
        do {
            List<Integer> l = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                l.add(i);
            }
            Collections.shuffle(l);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    res[i][j] = l.get((i << 2) + j);
                    if (res[i][j] == 0) {
                        row = i;
                        column = j;
                    }
                }
            }
        } while (!canBeSolved(res));
        numbers = res;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(numbers);
    }
}