
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;

/**
 *
 * @author Jacky
 */
public class Solver {

    private int moves = -2;
    private SearchNode solution;

    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
        SearchNode first = new SearchNode(initial, null, false);
        Board twin = initial.twin();
        //StdOut.println(twin.toString());
        SearchNode firstTwin = new SearchNode(twin, null, true);
        MinPQ<SearchNode> solver = new MinPQ<SearchNode>();
//        MinPQ<SearchNode> twinsolver = new MinPQ<SearchNode>();
        solver.insert(first);
        solver.insert(firstTwin);
        while (true) {
            SearchNode current = solver.delMin();
//            SearchNode currentTwin = twinsolver.delMin();
            if (current.board.isGoal()) {
                if (current.twin) {
                    solution = null;
                    moves = -1;
                    break;
                } else {
                    solution = current;
                    //StdOut.print("Solution: ");
                    //StdOut.println(current.board.toString());
                    moves = solution.moves;
                    break;
                }
            } else {
                Iterable<Board> neighbors = current.board.neighbors();
                Iterator<Board> iterator = neighbors.iterator();
                while (iterator.hasNext()) {
                    Board neighbor = iterator.next();
                    //StdOut.print("Inserting ");
                    //StdOut.println(neighbor.toString());
                    solver.insert(new SearchNode(neighbor, current, current.twin));
                }
            }
        }
        //StdOut.println(solution.board.toString());
    }

    public boolean isSolvable() {           // is the initial board solvable?
        return moves >= 0;
    }

    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
        return moves;
    }

    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (solution == null) return null;
        Stack<Board> trace = new Stack<Board>();
        SearchNode current = solution;
        while (current != null) {
            trace.push(current.board);
            current = current.parent;
        }
        return trace;
    }

    private class SearchNode implements Comparable<SearchNode> {

        private int moves;
        private Board board;
        private SearchNode parent;
        private boolean twin;

        public SearchNode(Board board, SearchNode parent, boolean twin) {
            if (parent == null) moves = 0;
            else moves = parent.moves + 1;
            this.board = board;
            this.parent = parent;
            this.twin = twin;
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
