/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.entity;

import java.util.ArrayList;
import static othello.global.General.*;
import othello.global.Layer;
import othello.global.State;
import othello.manager.GameObject;

/**
 *
 * @author Admin
 */
public class Bot extends GameObject{
    
    private final Board board;
    
    private final int [][] matrix = new int[board_row][board_col];
    
    private int control_color;
    private final int maxDepth = 5;

    public Bot(float x, float y, Board board) {
        super(x, y, Layer.Piece);
        states.add(State.Game);
        this.board = board;
    }
    
    private void encodeBoard() {
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                if (board.pieces[i][j] == null) matrix[i][j] = 0;
                else matrix[i][j] = board.pieces[i][j].piece_color;
            }
        }
    }
    
    private boolean isPossible(int I, int J, int pcolor) {
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                if (d1 == d2 && d2 == 0) continue;
                int i = I + d1, j = J + d2, cnt = 0;
                while (isValid(i, j)) {
                    if (matrix[i][j] == 0) {
                        break;
                    }
                    if (matrix[i][j] == pcolor) {
                        if (cnt > 0) return true;
                        break;
                    }
                    cnt++;
                    i += d1; j += d2;
                }
            }
        }
        return false;
    }
    
    private ArrayList<Integer> generateMoves(int pcolor) {
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                if (isPossible(i, j, pcolor) && matrix[i][j] == 0) {
                    moves.add(i*board_col + j);
                }
            }
        }
        return moves;
    }
    
    private ArrayList<Integer> makeMove(int move, int piece_color) {
        ArrayList<Integer> flippedPieces = new ArrayList<>();
        int I = move / board_col, J = move % board_col;
        matrix[I][J] = piece_color;
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                if (d1 == d2 && d2 == 0) continue;
                int i = I + d1, j = J + d2;
                ArrayList<Integer> flippingPieces = new ArrayList<>();
                while (isValid(i, j)) {
                    if (matrix[i][j] == 0) {
                        break;
                    }
                    if (matrix[i][j] != piece_color){
                        flippingPieces.add(i*board_col + j);
                    }
                    else {
                        for (int p : flippingPieces) {
                            matrix[p / board_col][p % board_col] *= -1;
                            flippedPieces.add(p);
                        }
                        break;
                    }
                    i += d1; j += d2;
                }
            }
        }
        return flippedPieces;
    }
    
    private void undoMove(int move, ArrayList<Integer> flippedPieces) {
        matrix[move / board_col][move % board_col] = 0;
        flippedPieces.forEach(p -> {
            matrix[p / board_col][p % board_col] *= -1;
        });
    }
    
    private final int [][] square_val = {
        {10, -1, 5, 2, 2, 5, -1, 10},
        {-1, -5, 1, 1, 1, 1, -5, -1},
        {5, 1, 1, 1, 1, 1, 1, 5},
        {2, -1, 1, 0, 0, 1, 1, 2},
        {2, -1, 1, 0, 0, 1, 1, 2},
        {5, -1, 1, 1, 1, 1, 1, 5},
        {-1, -5, 1, 1, 1, 1, -5, -1},
        {10, -1, 5, 2, 2, 5, -1, 10}
    };
    
    private int boardValue() {
        int val = 0;
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                val += matrix[i][j]*square_val[i][j];
            }
        }
        return val;
    }
    
    private int bestMove, positions = 0;
    
    private int MiniMaxSearch (int depth, int piece_color, int alpha, int beta) {
        positions ++;
        if (depth == maxDepth) {
            return boardValue();
        }
        ArrayList<Integer> moves = generateMoves(piece_color);
        if (moves.isEmpty()) {
            return MiniMaxSearch(depth + 1, -piece_color, alpha, beta);
        }
        int bestValue;
        if (piece_color == 1) {
            bestValue = Integer.MIN_VALUE;
            for (int move: moves) {
                ArrayList<Integer> flippedPieces = makeMove(move, piece_color);
                int value = MiniMaxSearch(depth + 1, -piece_color, alpha, beta);
                if (bestValue < value) {
                    bestValue = value;
                    if (depth == 0) { 
                        bestMove = move;
                    }
                }
                alpha = Math.max(alpha, bestValue);
                undoMove(move, flippedPieces);
                if (bestValue >= beta) break;
            }
            
        } else {
            bestValue = Integer.MAX_VALUE;
            for (int move: moves) {
                ArrayList<Integer> flippedPieces = makeMove(move, piece_color);
                int value = MiniMaxSearch(depth + 1, -piece_color, alpha, beta);
                if (bestValue > value) {
                    bestValue = value;
                    if (depth == 0) { 
                        bestMove = move;
                    }
                }
                beta = Math.min(beta, bestValue);
                undoMove(move, flippedPieces);
                if (bestValue <= alpha) break;
            }
        }
        if (depth == 0) System.out.println("Board value: " + bestValue);
        return bestValue;
    }

    @Override
    public void create() {
        control_color = -board.control_color;
    }
    
    private int t = -1;

    @Override
    public void update() {
        if (board.isBotTurn) {
            board.isBotTurn = false;
            positions = 0;
            encodeBoard();
            MiniMaxSearch(0, control_color, Integer.MIN_VALUE, Integer.MAX_VALUE);
            System.out.println("Positions: " + positions);
            t = 60;
        }
        t = Math.max(t - 1, -1);
        if (t == 0) {
            board.addPiece(bestMove);
        }
    }
    
}
