import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SolverTest {

    @Test
    void isSolvedTest() {
        Board b = new Board();
        Assertions.assertTrue(b.isSolved());
        b = new Board(
                new int[][] {
                        { 1, 2, 3, 4 },
                        { 5, 6, 7, 8 },
                        { 9, 10, 11, 12 },
                        { 13, 15, 14, 0 }
                }
        );
        Assertions.assertFalse(b.isSolved());
        b = new Board(
                new int[][] {
                        { 1, 2, 3, 4 },
                        { 5, 6, 7, 8 },
                        { 9, 10, 11, 12 },
                        { 13, 14, 0, 15 }
                }
        );
        Assertions.assertFalse(b.isSolved());
        b = new Board(
                new int[][] {
                        { 0, 1, 2, 3 },
                        { 4, 5, 6, 7 },
                        { 8, 9, 10, 11 },
                        { 12, 13, 14, 15 }
                }
        );
        Assertions.assertFalse(b.isSolved());
    }

    @Test
    void isSolvable() {
        Board b = new Board();
        Assertions.assertTrue(b.canBeSolved());
        b = new Board(
                new int[][] {
                        { 1, 2, 3, 4 },
                        { 5, 6, 7, 8 },
                        { 9, 10, 11, 12 },
                        { 13, 15, 14, 0 }
                }
        );
        Assertions.assertFalse(b.canBeSolved());
        b = new Board(
                new int[][] {
                        { 1, 2, 3, 4 },
                        { 5, 6, 7, 8 },
                        { 9, 10, 11, 12 },
                        { 15, 13, 14, 0 }
                }
        );
        Assertions.assertTrue(b.canBeSolved());
        b = new Board(
                new int[][] {
                        { 1, 2, 3, 4 },
                        { 5, 6, 7, 8 },
                        { 9, 10, 11, 15 },
                        { 12, 13, 14, 0 }
                }
        );
        Assertions.assertFalse(b.canBeSolved());
        b = new Board(
                new int[][] {
                        { 0, 1, 2, 4 },
                        { 3, 5, 6, 7 },
                        { 8, 9, 10, 11 },
                        { 12, 13, 14, 15 }
                }
        );
        Assertions.assertTrue(b.canBeSolved());
        b = new Board(
                new int[][] {
                        { 0, 5, 6, 8 },
                        { 11, 14, 3, 9 },
                        { 1, 13, 12, 4 },
                        { 7, 10, 2, 15 }
                }
        );
        Assertions.assertTrue(b.canBeSolved());
    }

    @Test
    void solverTest() {
        Board b = new Board(
                new int[][] {
                        { 1, 2, 3, 4 },
                        { 5, 6, 7, 8 },
                        { 9, 10, 11, 12 },
                        { 13, 14, 0, 15 }
                }
        );
        Solver s = new Solver(b);
        Move[] res = s.idaStar();
        Assertions.assertArrayEquals(new Move[] { Move.R }, res);
        b = new Board(
                new int[][] {
                        { 1, 2, 3, 4 },
                        { 5, 6, 7, 8 },
                        { 9, 10, 0, 12 },
                        { 13, 14, 11, 15 }
                }
        );
        s = new Solver(b);
        res = s.idaStar();
        Assertions.assertArrayEquals(new Move[] { Move.D, Move.R }, res);
        b = new Board(
                new int[][] {
                        { 5, 2, 4, 12 },
                        { 0, 6, 3, 14 },
                        { 10, 1, 15, 8 },
                        { 9, 13, 7, 11 }
                }
        );
        s = new Solver(b);
        res = s.idaStar();
        for (Move t: res) {
            b.takeMoveBack();
        }
        for (Move t : res) {
            b.move(t);
            System.out.println(b);
        }
        b = new Board(
                new int[][] {
                        { 5, 2, 4, 12 },
                        { 10, 6, 14, 0 },
                        { 1, 15, 3, 7 },
                        { 9, 13, 11, 8 }
                }
        );
        s = new Solver(b);
        res = s.idaStar();
        for (Move t: res) {
            b.takeMoveBack();
        }
        for (Move t : res) {
            b.move(t);
            System.out.println(b);
        }
        System.out.println(res.length);
        b = new Board(
                new int[][] {
                        { 5, 7, 15, 9 },
                        { 4, 12, 3, 13 },
                        { 6, 2, 0, 10 },
                        { 8, 11, 14, 1 }
                }
        );
        s = new Solver(b);
        Move[] f = s.idaStar();
        for (Move t: f) {
            b.takeMoveBack();
        }
        for (Move t : f) {
            b.move(t);
            System.out.println(b);
        }
        System.out.println(f.length);
    }
}