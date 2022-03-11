package othello.manager;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ADMIN
 */
public class BufferedImageLoader {
    
    private BufferedImage image;
    
    public BufferedImage loadImage(String path) throws IOException{
        //Load Image
        image = ImageIO.read(getClass().getResource(path));
        //Create an ARBG Image
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(image, 0, 0, null);
        return bi;
    }
}
