package othello.manager;

import java.awt.event.MouseEvent;
import static othello.global.General.*;
import java.awt.event.MouseAdapter;

/**
 *
 * @author ADMIN
 */
public class MouseInput extends MouseAdapter {
    
    @Override
    public void mouseClicked(MouseEvent e) {
        mouse_down = true;
        mouse_drag = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse_up = true;
        mouse_drag = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse_x = e.getX();
        mouse_y = e.getY();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        mouse_x = e.getX();
        mouse_y = e.getY();
        mouse_drag = true;
    }
    
}
