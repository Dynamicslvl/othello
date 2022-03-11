/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.entity;

import java.awt.image.BufferedImage;
import static othello.global.General.*;
import othello.global.Layer;
import othello.global.State;
import othello.manager.GameObject;
import static othello.manager.Handler.piece_flip;

/**
 *
 * @author Admin
 */
public class Piece extends GameObject {
    
    public int piece_color = 1;
    private boolean flipping = false;
    private float delay = 0, t = 0;
    private BufferedImage[] images;
    private int image_id = 0, image_count = 0;

    public Piece(float x, float y, int piece_color) {
        super(x, y, Layer.Piece);
        this.piece_color = piece_color;
        states.add(State.Game);
    }

    @Override
    public void create() {
        setDelay(0.02f);
        setImages(piece_flip);
        if (piece_color == 1) {
            image = images[0];
            image_id = 0;
        } else {
            image = images[18];
            image_id = 18;
        }
        set_centered_offset();
    }

    @Override
    public void update() {
        FlipAnimation();
    }
    
    public void FlipAnimation() {
        if (!flipping) return;
        t += deltaTime;
        if(t >= delay){
            t -= delay;
            if (piece_color == 1) {
                if (image_id > 0)
                    image_id--;
                else flipping = false;
            } 
            if (piece_color == -1) {
                if (image_id < image_count - 1)
                    image_id++;
                else flipping = false;
            } 
            image = images[image_id];
        }
        set_centered_offset();
    }
    
    public void setDelay(float delay) {
        this.delay = Math.max(deltaTime, delay);
    }
    
    public void setImages(BufferedImage[] images){
        this.images = images;
        image_count = images.length;
    }
    
    public void setFlipping() {
        flipping = true;
        piece_color = -piece_color;
    }
    
}
