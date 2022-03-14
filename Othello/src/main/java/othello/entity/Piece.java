/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.entity;

import java.awt.image.BufferedImage;
import othello.effect.Shadow;
import static othello.global.General.*;
import othello.global.Layer;
import othello.global.State;
import othello.manager.GameObject;
import static othello.manager.Handler.piece_flip;
import static othello.manager.Handler.spr_shadow;

/**
 *
 * @author Admin
 */
public class Piece extends GameObject {
    
    public static float delay = 0.03f;
    public int piece_color = 1;
    private boolean flipping = false;
    private float t = 0;
    private BufferedImage[] images;
    private int image_id = 0, image_count = 0;
    private Shadow shadow;

    public Piece(float x, float y, int piece_color) {
        super(x, y, Layer.Piece);
        this.piece_color = piece_color;
        states.add(State.Game);
    }

    @Override
    public void create() {
        setImages(piece_flip);
        if (piece_color == 1) {
            image = images[0];
            image_id = 0;
        } else {
            image = images[18];
            image_id = 18;
        }
        set_centered_offset();
        shadow = new Shadow(x + grid_size*0.1f + 1, y + grid_size*0.1f + 1);
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
        float scale = 1.4f - 0.4f*Math.abs(9 - image_id)/9;
        image_xscale = scale;
        image_yscale = scale;
        shadow.setImage(addAlphaImage(spr_shadow, (int) (150*(Math.abs(9 - image_id)/9f - 1))));
        shadow.setImage_xscale(scale);
        shadow.setImage_yscale(scale);
        shadow.set_centered_offset();
        set_centered_offset();
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
