/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.manager;

import java.awt.Font;
import java.awt.FontFormatException;
import static othello.global.General.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Handler {

    public static LinkedList<LinkedList<GameObject>> OBJECT;
    public static BufferedImage spr_possible_move, spr_square, spr_shadow, spr_board_bg;
    public static BufferedImage[] piece_flip = new BufferedImage[19];

    public void loadImages() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            for (int i = 0; i < piece_flip.length; i++) {
                piece_flip[i] = pixelImage(loader.loadImage("/piece_flip/" + i + ".png"), grid_size*0.85f, grid_size*0.85f);
            }
            spr_possible_move = pixelImage(loader.loadImage("/spr_possible_move.png"), grid_size*0.3f, grid_size*0.3f);
            spr_square = pixelImage(loader.loadImage("/spr_square.png"), grid_size, grid_size);
            spr_shadow = pixelImage(loader.loadImage("/spr_shadow.png"), grid_size*0.92f, grid_size*0.92f);
            spr_board_bg = pixelImage(loader.loadImage("/spr_board_bg.png"), grid_size*board_col, grid_size*board_row);
        } catch (IOException e) {
        }
    }
    
    public static Font fnt_r_aguda, fnt_b_aguda;

    public void loadFonts() {
        try {
            fnt_r_aguda = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/othello/font/SVN-Aguda Regular.ttf"));
            fnt_b_aguda = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/othello/font/SVN-Aguda Black.ttf"));
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initSortingLayer() {
        OBJECT = new LinkedList<>();
        for (int i = 0; i < max_layer; i++) {
            OBJECT.add(new LinkedList<>());
        }
    }

    public void tick() {
        for (int i = 0; i < max_layer; i++) {
            for (int j = 0; j < OBJECT.get(i).size(); j++) {
                OBJECT.get(i).get(j).tick();
            }
        }

        mouse_up = false;
        mouse_down = false;
    }

    public int numberOfObject = 0;

    public void render(Graphics2D g) {
        numberOfObject = 0;
        for (int i = 0; i < max_layer; i++) {
            numberOfObject += OBJECT.get(i).size();
            for (int j = 0; j < OBJECT.get(i).size(); j++) {
                OBJECT.get(i).get(j).render(g);
            }
        }
    }

    public static void addObject(GameObject object) {
        OBJECT.get(object.layer.getId()).add(object);
    }

    public static void removeObject(GameObject object) {
        OBJECT.get(object.layer.getId()).remove(object);
    }
}
