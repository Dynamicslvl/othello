/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.ui;

import java.awt.Color;
import othello.global.General;
import othello.global.Layer;
import othello.global.State;
import othello.manager.GameObject;
import othello.manager.Handler;

/**
 *
 * @author Admin
 */
public class PossibleMove extends GameObject {

    public PossibleMove(float x, float y) {
        super(x, y, Layer.UI);
        states.add(State.Game);
    }

    @Override
    public void create() {
        image = General.colorImage(Handler.spr_possible_move, new Color(0, 92, 66));
        set_centered_offset();
    }
    
    public void setImageColor(Color color) {
        image = General.colorImage(image, color);
    }

    @Override
    public void update() {
    }
    
}
