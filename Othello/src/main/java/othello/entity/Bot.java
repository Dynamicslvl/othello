/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.entity;

import java.util.ArrayList;
import static othello.global.General.*;
import othello.manager.Game;

/**
 *
 * @author Admin
 */
public class Bot implements Runnable {

    private final Board board;
    private final Evaluator evaluator = new Evaluator();

    private final int[][] matrix = new int[board_row][board_col];

    private int control_color;
    private final int maxDepth = 6;

    public Bot(Board board) {
        Game.botThread = new Thread(this);
        Game.botThread.start();
        this.board = board;
    }
    
    private int piece_count = 0;

    private void translate() {
        piece_count = 0;
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                if (board.pieces[i][j] == null) {
                    matrix[i][j] = 0;
                } else {
                    piece_count++;
                    matrix[i][j] = board.pieces[i][j].piece_color;
                }
            }
        }
    }

    private boolean isPossible(int I, int J, int piece_color) {
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                if (d1 == d2 && d2 == 0) {
                    continue;
                }
                int i = I + d1, j = J + d2, cnt = 0;
                while (isValid(i, j)) {
                    if (matrix[i][j] == 0) {
                        break;
                    }
                    if (matrix[i][j] == piece_color) {
                        if (cnt > 0) {
                            return true;
                        }
                        break;
                    }
                    cnt++;
                    i += d1;
                    j += d2;
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
                    moves.add(i * board_col + j);
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
                if (d1 == d2 && d2 == 0) {
                    continue;
                }
                int i = I + d1, j = J + d2;
                ArrayList<Integer> flippingPieces = new ArrayList<>();
                while (isValid(i, j)) {
                    if (matrix[i][j] == 0) {
                        break;
                    }
                    if (matrix[i][j] != piece_color) {
                        flippingPieces.add(i * board_col + j);
                    } else {
                        for (int p : flippingPieces) {
                            matrix[p / board_col][p % board_col] *= -1;
                            flippedPieces.add(p);
                        }
                        break;
                    }
                    i += d1;
                    j += d2;
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

    private int bestMove, positions = 0;

    private int MiniMax(int depth, int piece_color, int alpha, int beta) {
        positions++;
        if (depth == maxDepth) {
            return evaluator.evaluate(matrix);
        }
        ArrayList<Integer> moves = generateMoves(piece_color);
        if (moves.isEmpty()) {
            return MiniMax(depth + 1, -piece_color, alpha, beta);
        }
        int bestValue;
        if (piece_color == 1) {
            bestValue = Integer.MIN_VALUE;
            for (int move : moves) {
                ArrayList<Integer> flippedPieces = makeMove(move, piece_color);
                int value = MiniMax(depth + 1, -piece_color, alpha, beta);
                if (bestValue < value) {
                    bestValue = value;
                    if (depth == 0) {
                        bestMove = move;
                    }
                }
                alpha = Math.max(alpha, bestValue);
                undoMove(move, flippedPieces);
                if (alpha >= beta) {
                    break;
                }
            }
        } else {
            bestValue = Integer.MAX_VALUE;
            for (int move : moves) {
                ArrayList<Integer> flippedPieces = makeMove(move, piece_color);
                int value = MiniMax(depth + 1, -piece_color, alpha, beta);
                if (bestValue > value) {
                    bestValue = value;
                    if (depth == 0) {
                        bestMove = move;
                    }
                }
                beta = Math.min(beta, bestValue);
                undoMove(move, flippedPieces);
                if (alpha >= beta) {
                    break;
                }
            }
        }
        if (depth == 0) {
            System.out.println("Board value: " + bestValue);
        }
        return bestValue;
    }

    private int t = -1;
    private boolean wait = false; //wait flip animation end

    public void tick() {
        control_color = -board.control_color;
        if (board.isBotTurn) {
            if (!wait) {
                wait = true;
                t = (int) Math.ceil(Piece.delay*18/deltaTime);
            }
        }
        t = Math.max(t - 1, -1);
        if (t == 0) {
            wait = false;
            positions = 0;
            translate();
            board.isBotTurn = false;
            if (piece_count == 64) return;
            
            //Start thinking
            botThinkingTime = 0;
            isBotThinking = true;
            
            MiniMax(0, control_color, Integer.MIN_VALUE, Integer.MAX_VALUE);
            System.out.println("Positions: " + positions);
            board.addPiece(bestMove);
            
            //End thinking
            isBotThinking = false;
            System.out.println("Thinking time: " + String.format("%.2f", botThinkingTime) + "s");
            System.out.println("");
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        while (Game.running) {
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
        }
    }

}
