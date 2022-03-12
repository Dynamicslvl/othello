/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.effect;

import othello.global.Layer;
import othello.global.State;
import othello.manager.GameObject;
import othello.manager.Handler;

/**
 *
 * @author Admin
 */
public class Shadow extends GameObject {

    public Shadow(float x, float y) {
        super(x, y, Layer.Tile);
        states.add(State.Game);
    }

    @Override
    public void create() {
        image = Handler.spr_shadow;
        set_centered_offset();
    }

    @Override
    public void update() {
    }
    
}
