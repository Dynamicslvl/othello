/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.ui;

import othello.global.Layer;
import othello.global.State;
import othello.manager.GameObject;
import othello.manager.Handler;

/**
 *
 * @author Admin
 */
public class PossibleMove extends GameObject{

    public PossibleMove(float x, float y) {
        super(x, y, Layer.Tile);
        states.add(State.Game);
    }

    @Override
    public void create() {
        image = Handler.spr_possible_move;
        set_centered_offset();
    }

    @Override
    public void update() {
    }
    
}
