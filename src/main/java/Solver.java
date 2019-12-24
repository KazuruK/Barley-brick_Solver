import java.util.*;

public class Solver {
    private Board board;
    private int bound;
    private final Move[] moveValues = Move.values();
    private boolean isMinimalMode = true;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Board b = Board.randomBoard();
        Solver s = new Solver(b);
        s.setInMinimalMode(true);
        System.out.println(b);
        Move[] res = s.idaStar();
        for (Move ignored : res) {
            b.takeMoveBack();
        }
        for (Move m : res) {
            b.move(m);
            System.out.println(b);
        }
        System.out.println(res.length);
        System.out.println(System.currentTimeMillis() - start);
    }

    Solver(Board board) {
        this.board = board;
    }

    void setInMinimalMode(boolean b) {
        isMinimalMode = b;
    }

    Move[] idaStar() {
        bound = minimalBound();
        while (true) {
            bound = search(0);
            if (bound == -1) {
                return board.getPath();
            }
        }
    }

    int minimalBound() {
        if (isMinimalMode) {
            return h();
        } else {
            return 200;
        }
    }

    int h() {
        return board.getManhattan();
    }

    int search(int g) {
        int f = g + h();
        if (f > bound) return f;
        if (board.isSolved()) return -1;

        int min = Integer.MAX_VALUE;
        List<Move> badMoves = new LinkedList<>();
        for (Move move: moveValues) {
            if (board.isMoveLegal(move)) {
                if (board.countManhattan(move) == -1) {
                    board.move(move, -1);
                    int t = search(g + 1);
                    if (t == -1) return -1;
                    if (t < min) min = t;
                    board.takeMoveBack();
                } else {
                    badMoves.add(move);
                }
            }
        }

        for (Move move: badMoves) {
            board.move(move, 1);
            int t = search(g + 1);
            if (t == -1) return -1;
            if (t < min) min = t;
            board.takeMoveBack();
        }
        return min;
    }
}