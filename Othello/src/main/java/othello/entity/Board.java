/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.entity;

import java.awt.Color;
import java.util.ArrayList;
import othello.global.BoxCollider;
import static othello.global.General.*;
import othello.global.Layer;
import othello.global.State;
import othello.manager.GameObject;
import othello.manager.Handler;
import othello.ui.Dialog;
import othello.ui.PossibleMove;

/**
 *
 * @author Admin
 */
public class Board extends GameObject {
    
    public Piece [][] pieces = new Piece[board_row][board_col];
    public PossibleMove [][] moves = new PossibleMove[board_row][board_col];
    public int piece_color = 1;
    public int control_color = 1;
    public boolean isBotTurn = false;
    private int move_count = 0;
    private Dialog dialog;

    public Board(float x, float y) {
        super(x, y, Layer.Tile);
        states.add(State.Game);
    }

    @Override
    public void create() {
        //Dialog
        dialog = new Dialog(15, 25);
        
        //Board area
        boxCollider = new BoxCollider(x, y, grid_size*board_col, grid_size*board_row);
        
        //Draw board
        image = Handler.spr_board_bg;
        
        //Draw line
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                new Square(x + j*grid_size, y + i*grid_size);
                moves[i][j] = new PossibleMove(grid2Real(j) + grid_size/2, grid2Real(i) + grid_size/2);
                moves[i][j].setActive(false);
            }
        }
        
        //Init first 4 pieces
        pieces[board_row/2-1][board_col/2-1] = new Piece(grid2Real(board_col/2-1) + grid_size/2, grid2Real(board_row/2-1) + grid_size/2, -1);
        pieces[board_row/2-1][board_col/2] = new Piece(grid2Real(board_col/2) + grid_size/2, grid2Real(board_row/2-1) + grid_size/2, 1);
        pieces[board_row/2][board_col/2-1] = new Piece(grid2Real(board_col/2-1) + grid_size/2, grid2Real(board_row/2) + grid_size/2, 1);
        pieces[board_row/2][board_col/2] = new Piece(grid2Real(board_col/2) + grid_size/2, grid2Real(board_row/2) + grid_size/2, -1);
        showPossibleMove(false);
    }

    @Override
    public void update() {
        addPiece();
        if (piece_color == 1) {
            dialog.setText("Black's turn");
        } else {
            dialog.setText("White's turn");
        }
        if (move_count == 0) t = Math.max(t - 1, -1);
        if (t == 0) showGameResult();
    }
    
    private boolean isPossible(int I, int J) {
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                if (d1 == d2 && d2 == 0) continue;
                int i = I + d1, j = J + d2, cnt = 0;
                while (isValid(i, j)) {
                    if (pieces[i][j] == null) {
                        break;
                    }
                    if (pieces[i][j].piece_color == piece_color) {
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
    
    public void showPossibleMove(boolean isOtherChecked) {
        move_count = 0;
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                moves[i][j].setActive(false);
                if (pieces[i][j] != null) continue;
                if (isPossible(i, j)) {
                    moves[i][j].setActive(true);
                    move_count++;
                }
            }
        }
        if (move_count == 0 && !isOtherChecked) {
            piece_color = -piece_color;
            showPossibleMove(true);
            if (control_color != piece_color) {
                isBotTurn = true;
            }
            return;
        }
        if (control_color != piece_color) {
            isBotTurn = true;
        }
    }
    
    private int t = 60;
    
    public void showGameResult() {
        int b = 0, w = 0;
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                if (pieces[i][j] == null) continue;
                if (pieces[i][j].piece_color == 1) b++;
                else w++;
            }
        }
        String text = "Black: " + b + " - White: " + w + "            ";
        if (b > w){ 
            text += "BLACK WON!";
        }
        if (b == w) { 
            text += "DRAW!";
        }
        if (b < w) {
            text += "WHITE WON!";
        }
        dialog.setText(text);
        dialog.setColor(Color.red);
        pause = true;
    }
    
    public void flipPieces(int I, int J) {
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                if (d1 == d2 && d2 == 0) continue;
                int i = I + d1, j = J + d2;
                ArrayList<Piece> flipPieces = new ArrayList<>();
                while (isValid(i, j)) {
                    if (pieces[i][j] == null) {
                        break;
                    }
                    if (pieces[i][j].piece_color != piece_color)
                        flipPieces.add(pieces[i][j]);
                    else {
                        flipPieces.forEach(piece -> {
                            piece.setFlipping();
                        });
                        break;
                    }
                    i += d1; j += d2;
                }
            }
        }
    }
    
    public void addPiece() {
        if (piece_color != control_color) return;
        if (!mouse_up) return;
        if (!inBoxCollider(mouse_x, mouse_y, boxCollider)) return;
        int i = real2Grid(mouse_y);
        int j = real2Grid(mouse_x);
        if (!moves[i][j].isActive()) return;
        pieces[i][j] = new Piece(grid2Real(j) + grid_size/2, grid2Real(i) + grid_size/2, piece_color);
        flipPieces(i, j);
        piece_color = -piece_color;
        showPossibleMove(false);
    }
    
    public void addPiece(int position) {
        int i = position / board_col;
        int j = position % board_col;
        pieces[i][j] = new Piece(grid2Real(j) + grid_size/2, grid2Real(i) + grid_size/2, piece_color);
        flipPieces(i, j);
        piece_color = -piece_color;
        showPossibleMove(false);
    }
    
}
