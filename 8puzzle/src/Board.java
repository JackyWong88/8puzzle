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
    
    
// construct a board from an N-by-N array of blocks
// (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = blocks;
    }

    public int dimension() {                 // board dimension N
        return 0;
    }

    public int hamming() {                   // number of blocks out of place
        return 0;
    }

    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        return 0;
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
        return this;
    }

    public boolean equals(Object y) {       // does this board equal y?
        return false;
    }

    public Iterable<Board> neighbors() {    // all neighboring boards

    }

    public String toString() {              // string representation of this board (in the output format specified below)
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(this.blocks[i][j] != i*N+(j+1) && i != N-1 && j != N-1)
                    return "none";
            }
        }
        return "none";
    }

    public static void main(String[] args) {// unit tests (not graded)

    }
}