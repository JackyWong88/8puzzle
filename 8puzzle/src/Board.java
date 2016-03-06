/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacky
 */
public class Board {
    private int N;
    private int[][] blocks;
    private int moves;
    
// construct a board from an N-by-N array of blocks
// (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {                 // board dimension N
        return N*N;
    }

    public int hamming() {                   // number of blocks out of place
        int count = moves;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (this.blocks[i][j] == 0) continue;
                if (this.blocks[i][j] != i*N+(j+1)) count++;
            }
        }
        return count;
    }

    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int count = moves;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (this.blocks[i][j] == 0) continue;
                if (this.blocks[i][j] != i*N+(j+1)) count += Math.abs(i*N+(j+1)-this.blocks[i][j]);
            }
        }
        return count;
    }

    public boolean isGoal() {                // is this board the goal board?
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(this.blocks[i][j] != i*N+(j+1) && i != N-1 && j != N-1)
                    return false;
            }
        }
        return true;
    }

    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
        int temp1 = this.blocks[0][0];
        int temp2 = this.blocks[0][1];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
        return this;
    }

    public boolean equals(Object y) {       // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {    // all neighboring boards

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
