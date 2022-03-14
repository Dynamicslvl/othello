/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.entity;

import static othello.global.General.*;

/**
 *
 * @author Admin
 */
public class Evaluator {
    
    private final int middle = 20;
    private final int ending = 58;
    
    private final int [][] matrix = new int[board_row][board_col];
    
    private final int[][] square_value = {
        {100, -10,  8,  6,  6,  8, -10,  100},
        {-10, -25, -4, -4, -4, -4, -25, -10 },
        {  8,  -4,  6,  4,  4,  6,  -4,  8  },
        {  6,  -4,  4,  0,  0,  4,  -4,  6  },
        {  6,  -4,  4,  0,  0,  4,  -4,  6  },
        {  8,  -4,  6,  4,  4,  6,  -4,  8  },
        {-10, -25, -4, -4, -4, -4, -25, -10 },
        {100, -10,  8,  6,  6,  8, -10,  100}
    };
    
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
    
    private int getMoveCount(int piece_color) {
        int count = 0;
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                if (matrix[i][j] == 0) {
                    if (isPossible(i, j, piece_color))
                        count++;
                }
            }
        }
        return count;
    }
    
    int pieces_count = 0;
    
    private void copyMatrix(int [][] matrix) {
        pieces_count = 0;
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                this.matrix[i][j] = matrix[i][j];
                if (matrix[i][j] != 0)
                    pieces_count++;
            }
        }
    }
    
    public int evaluate(int [][] matrix) {
        copyMatrix(matrix);
        if (pieces_count >= ending) {
            return 100*pieces_advantage();
        } else if (pieces_count >= middle) {
            return 60*frontier() + 20*mobility() + 20*placement();
        }
        return 85*mobility() + 15*placement();
    }
    
    private int mobility() {
        float mob1 = getMoveCount( 1);
        float mob2 = getMoveCount(-1);
        return (int) ((mob1 + mob2 != 0) ? 100f * (mob1 - mob2) / (mob1 + mob2) : 0);
    }
    
    private int placement() {
        int diff = 0;
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                diff += matrix[i][j]*square_value[i][j];
            }
        }
        return diff;
    }
    
    private int pieces_advantage() {
        int diff = 0;
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                diff += matrix[i][j];
            }
        }
        return (int) ((pieces_count != 0) ? 100f * diff/pieces_count : 0);
    }
    
    private boolean isNeighbor(int I, int J, int piece_color) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == j && j == 0) continue;
                if (!isValid(I+i, J+j)) continue;
                if (matrix[I+i][J+j] == piece_color)
                    return true;
            }
        }
        return false;
    }
    
    private int frontier() {
        int fr1 = 0, fr2 = 0;
        for (int i = 0; i < board_row; i++) {
            for (int j = 0; j < board_col; j++) {
                if (isNeighbor(i, j, +1)) fr1++;
                if (isNeighbor(i, j, -1)) fr2++;
            }
        }
        return (int) ((fr1 + fr2 == 0) ? 100f* (fr2 - fr1) / (fr1 + fr2) : 0);
    }
}
