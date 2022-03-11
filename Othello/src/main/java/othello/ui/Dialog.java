/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.ui;

import java.awt.Color;
import othello.global.Layer;
import othello.global.State;
import othello.manager.Handler;

/**
 *
 * @author Admin
 */
public class Dialog extends TextArea {

    public Dialog(float x, float y) {
        super(x, y, Layer.UI);
        states.add(State.Game);
    }

    @Override
    public void create() {
        setFont(Handler.fnt_b_aguda.deriveFont(20f));
        setColor(new Color(255, 192, 0));
    }

    @Override
    public void update() {
        
    }
    
}
