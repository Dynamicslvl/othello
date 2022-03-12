package othello.manager;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author ADMIN
 */
public class Window extends Canvas{
    public Window(int width, int height, String title, Game game){
        JFrame frame = new JFrame(title);
        //frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.getContentPane().setPreferredSize(new Dimension(width - 10, height - 10));
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }
}
