
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

/**
 *
 * @author Jacky
 */
public class Solver {
    SearchNode solution;
    int moves = 0;
    

    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
        MinPQ solver = new MinPQ();
        MinPQ twinsolver = new MinPQ();
        Board twin = initial.twin();
        solver.insert(initial);
        twinsolver.insert(twin);
    }

    public boolean isSolvable() {           // is the initial board solvable?
        if (moves > 0) return true;
        else return false;
    }

    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
        return moves;
    }

    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        Stack trace = new Stack();
        SearchNode current = solution;
        while (current != null) {
            trace.push(current.board);
            current = current.parent;
        }
        return trace;
    }
    
    private class SearchNode implements Comparable<SearchNode>{
        int moves;
        Board board;
        SearchNode parent;

        public SearchNode(Board board, SearchNode parent) {
            if (parent == null) moves = 0;
            else moves = parent.moves + 1;
            this.board = board;
            this.parent = parent;
        }

        @Override
        public int compareTo(SearchNode node) {
            int manhattan1 = this.board.manhattan();
            int manhattan2 = node.board.manhattan();
            if (this.moves + manhattan1 > node.moves + manhattan2) return 1;
            else if (this.moves + manhattan1 < node.moves + manhattan2) return -1;
            else if (manhattan1 > manhattan2) return 1;
            else if (manhattan1 < manhattan2) return -1;
            else {
                int hamming1 = this.board.hamming();
                int hamming2 = node.board.hamming();
                if (hamming1 < hamming2) return 1;
                else if (hamming1 > hamming2) return -1;
                else return 0;
            }
        }
        
}   

    public static void main(String[] args) {
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

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
