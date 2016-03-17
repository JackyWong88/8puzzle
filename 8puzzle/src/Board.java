import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
/**
 *
 * @author Jacky
 */
public class Board {
    private int[][] tiles;
    private int N, blankcol, blankrow, parentc, parentr;
    
// construct a board from an N-by-N array of blocks
// (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        parentc = -1;
        parentr = -1;
        N = blocks[0].length;
        this.tiles = new int[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    this.blankrow = i;
                    this.blankcol = j;
                }
                this.tiles[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {                 // board dimension N
        return N*N;
    }

    public int hamming() {                   // number of blocks out of place
        int count = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (this.tiles[i][j] == 0) continue;
                if (this.tiles[i][j] != i*N+(j+1)) count++;
            }
        }
        return count;
    }

    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int count = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (this.tiles[i][j] == 0) continue;
                if (this.tiles[i][j] != i*N+(j+1)) count += Math.abs(i*N+(j+1)-this.tiles[i][j]);
            }
        }
        return count;
    }

    public boolean isGoal() {                // is this board the goal board?
        StdOut.print("Checking ");
        StdOut.println(this.toString());
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                StdOut.print("comparing ");
                StdOut.print(this.tiles[i][j]);
                StdOut.print(" and ");
                StdOut.println(i*N+(j+1));
                if(i == N-1 && j == N-1)
                    break;
                else if(this.tiles[i][j] != i*N+(j+1))
                    return false;
            }
        }
        return true;
    }

    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
        Board twin = new Board(this.tiles);
        int temp1 = twin.tiles[0][0];
        int temp2 = twin.tiles[N-1][N-1];
        if (temp1 == 0) {
            temp1 = twin.tiles[0][1];
            twin.tiles[0][1] = temp2;
            twin.tiles[N-1][N-1] = temp1;
        } else if (temp2 == 0) {
            temp2 = twin.tiles[N-1][N-2];
            twin.tiles[0][0] = temp2;
            twin.tiles[N-1][N-2] = temp1;
        } else {
            twin.tiles[0][0] = temp2;
            twin.tiles[N-1][N-1] = temp1;
        }
        return twin;
    }

    public boolean equals(Object y) {       // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {    // all neighboring boards
        Stack neighbors = new Stack();
        StdOut.print("this board's blank spot is at ");
        StdOut.print(blankrow);
        StdOut.print(",");
        StdOut.println(blankcol);
        StdOut.print("this board's parent spot is at ");
        StdOut.print(parentr);
        StdOut.print(",");
        StdOut.println(parentc);
        if(this.blankrow != N-1 && this.parentr != this.blankrow+1 && this.parentc != this.blankcol)
            neighbors.push(swap(blankrow+1,blankcol));
        if(this.blankcol != N-1 && this.parentr != this.blankrow && this.parentc != this.blankcol+1)
            neighbors.push(swap(blankrow,blankcol+1));
        if(this.blankrow != 0 && this.parentr != this.blankrow-1 && this.parentc != this.blankcol)
            neighbors.push(swap(blankrow-1,blankcol));
        if(this.blankcol != 0 && this.parentr != this.blankrow && this.parentc != this.blankcol-1)
            neighbors.push(swap(blankrow,blankcol-1));
        return neighbors;
    }
    
    //swaps the blank space with the given (r, c)
    private Board swap(int r, int c) {
        StdOut.print("Swapping :");
        StdOut.print(r);
        StdOut.print(",");
        StdOut.println(c);
        Board neighbor = new Board(this.tiles);
        neighbor.parentc = this.blankcol;
        neighbor.parentr = this.blankrow;
        neighbor.blankcol = c;
        neighbor.blankrow = r;
        neighbor.tiles[this.blankrow][this.blankcol] = neighbor.tiles[r][c];
        neighbor.tiles[r][c] = 0;
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

    }
}
