
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author Jacky
 */
public class Board {

    private final int[][] tiles;
    private final int N; 
    private int blank, parent;

// construct a board from an N-by-N array of blocks
// (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        parent = -1;
        N = blocks[0].length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    blank = rctoi(i,j);
                }
                this.tiles[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {                 // board dimension N
        return N;
    }

    public int hamming() {                   // number of blocks out of place
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] == 0) continue;
                if (this.tiles[i][j] != i * N + (j + 1)) count++;
            }
        }
        return count;
    }

    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = this.tiles[i][j];
                if (value == 0) continue;
                if (value != i * N + (j + 1))
                    count += (Math.abs((value - 1)/ N - i) + Math.abs((value + N - 1) % N - j));
            }
        }
        return count;
    }

    public boolean isGoal() {                // is this board the goal board?
//        StdOut.print("Checking ");
//        StdOut.println(this.toString());
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
//                StdOut.print("comparing ");
//                StdOut.print(this.tiles[i][j]);
//                StdOut.print(" and ");
//                StdOut.println(i*N+(j+1));
                if (i == N - 1 && j == N - 1)
                    break;
                else if (this.tiles[i][j] != i * N + (j + 1))
                    return false;
            }
        }
        return true;
    }

    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
        Board twin = new Board(this.tiles);
        int temp1 = twin.tiles[0][0];
        int temp2 = twin.tiles[N - 1][N - 1];
        if (temp1 == 0) {
            temp1 = twin.tiles[0][1];
            twin.tiles[0][1] = temp2;
            twin.tiles[N - 1][N - 1] = temp1;
        } else if (temp2 == 0) {
            temp2 = twin.tiles[N - 1][N - 2];
            twin.tiles[0][0] = temp2;
            twin.tiles[N - 1][N - 2] = temp1;
        } else {
            twin.tiles[0][0] = temp2;
            twin.tiles[N - 1][N - 1] = temp1;
        }
        return twin;
    }

    public boolean equals(Object y) {       // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {    // all neighboring boards
        Stack<Board> neighbors = new Stack<Board>();
//        StdOut.print("this board's blank spot is at ");
//        StdOut.print(blankrow);
//        StdOut.print(",");
//        StdOut.println(blankcol);
//        StdOut.print("this board's parent spot is at ");
//        StdOut.print(parentr);
//        StdOut.print(",");
//        StdOut.println(parentc);
        //if the zero is on the far right
        if (this.blank % N != 0 && this.blank + 1 != this.parent)
            neighbors.push(swap(this.blank + 1));
        if ((this.blank - 1) % N != 0 && this.blank - 1 != this.parent)
            neighbors.push(swap(this.blank - 1));
        if (this.blank > N && this.blank - N != this.parent)
            neighbors.push(swap(this.blank - N));
        if ((this.blank - 1) / N != N - 1 && this.blank + N != this.parent)
            neighbors.push(swap(this.blank + N));
        
        return neighbors;
    }
    
    private boolean match(int r1, int c1, int r2, int c2) {
        return (r1 == r2 && c1 == c2);
    }
    
    private int rctoi(int r, int c) {
        return r * this.N + (c + 1);
    }

    //swaps the blank space with the given (r, c)
    private Board swap(int idx) {
//        StdOut.print("Swapping :");
//        StdOut.print(r);
//        StdOut.print(",");
//        StdOut.println(c);
        Board neighbor = new Board(this.tiles);
        neighbor.parent = this.blank;
        neighbor.blank = idx;
        neighbor.tiles[(this.blank - 1)/N][(this.blank + N - 1) % N] = neighbor.tiles[(idx - 1)/N][(idx + N - 1) % N];
        neighbor.tiles[(idx - 1)/N][(idx + N - 1) % N] = 0;
        return neighbor;
    }

    public String toString() {              // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {// unit tests (not graded)
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        StdOut.println(initial);
        for (Board board : initial.neighbors()) {
            StdOut.println(board);
            for (Board board2 : board.neighbors()) {
                StdOut.println(board2);
            }
        }
    }
}
