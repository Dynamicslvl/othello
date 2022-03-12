/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.global;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class General {
    
    public static boolean pause = false;
    public static final int max_layer = 10;
    public static Random random = new Random();
    public static float timeScale = 1;
    public static float deltaTime = 1f / 60.f;
    public static float mouse_x = 0, mouse_y = 0;
    public static boolean mouse_drag = false;
    public static boolean mouse_down = false;
    public static boolean mouse_up = false;
    
    public static int grid_size = 70;
    public static int board_row = 8, board_col = 8;

    public static int[] dirI = {0, 1,  0, -1};
    public static int[] dirJ = {1, 0, -1,  0};
                              //R, D,  L, U//
    
    public static class GridPosition {

        public int i, j;

        public GridPosition(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static boolean isValid(int i, int j) {
        return !(i < 0 || j < 0 || i >= 16 || j >= 20);
    }

    public static int numberOfDigits(int n) {
        if (n == 0) {
            return 1;
        }
        return (int) Math.floor(Math.log((double) n) / Math.log(10)) + 1;
    }

    public static boolean inCircleCollider(float x, float y, CircleCollider cc) {
        return cc.r > point_distance(x, y, cc.x, cc.y);
    }

    public static boolean inBoxCollider(float x, float y, BoxCollider bc) {
        return (bc.getX() <= x) && (x <= bc.getX() + bc.getW()) && (bc.getY() <= y) && (y <= bc.getY() + bc.getH());
    }

    public static boolean boxCollision(BoxCollider tmp1, BoxCollider tmp2) {
        //((x1+w1 >= x2) && (x2+w2 >= x1) && (y1+h1 >= y2) && (y2+h2 >= y1))
        return (tmp1.getX() + tmp1.getW() >= tmp2.getX())
                && (tmp2.getX() + tmp2.getW() >= tmp1.getX())
                && (tmp1.getY() + tmp1.getH() >= tmp2.getY())
                && (tmp2.getY() + tmp2.getH() >= tmp1.getY());
    }

    public static boolean circleCollision(CircleCollider tmp1, CircleCollider tmp2) {
        return tmp1.r + tmp2.r > point_distance(tmp1.x, tmp1.y, tmp2.x, tmp2.y);
    }

    public static float angle_different(float angle1, float angle2) {
        angle1 -= Math.floor(angle1 / 360) * 360;
        angle2 -= Math.floor(angle2 / 360) * 360;
        float sub_angle = Math.abs(angle1 - angle2);
        if (sub_angle > 360 - sub_angle) {
            sub_angle = 360 - sub_angle;
        }
        return sub_angle;
    }

    public static float point_distance(float x1, float y1, float x2, float y2) {
        float diff1 = x2 - x1, diff2 = y2 - y1;
        return (float) Math.sqrt(diff1 * diff1 + diff2 * diff2);
    }

    public static float point_direction(float x1, float y1, float x2, float y2) {
        return (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
    }

    public static boolean chanceOf(int x, int y) {
        int i = randomRange(1, y);
        return i <= x;
    }

    public static int randomRange(int l, int r) {
        if (l > r) {
            return 0;
        }
        return l + random.nextInt(r - l + 1);
    }

    public static int sign(float x) {
        if (x < 0) {
            return -1;
        }
        if (x == 0) {
            return 0;
        }
        return +1;
    }

    public static float char2Angle(char x) {
        if (x == 'D') {
            return 90;
        }
        if (x == 'L') {
            return 180;
        }
        if (x == 'U') {
            return 270;
        }
        return 0;
    }

    public static float lengthdirX(float length, float direction) {
        return (float) ((float) length * Math.cos(Math.toRadians(direction)));
    }

    public static float lengthdirY(float length, float direction) {
        return (float) ((float) length * Math.sin(Math.toRadians(direction)));
    }

    public static int real2Grid(float pos) {
        int tmp = Math.round(pos);
        return tmp / grid_size;
    }

    public static float grid2Real(int pos) {
        return pos * grid_size;
    }

    public static int clamp(int value, int minValue, int maxValue) {
        if (value < minValue) {
            return minValue;
        }
        if (value > maxValue) {
            return maxValue;
        }
        return value;
    }

    public static float clamp(float value, float minValue, float maxValue) {
        if (value < minValue) {
            return minValue;
        }
        if (value > maxValue) {
            return maxValue;
        }
        return value;
    }

    public static float clamp(float value, int minValue, int maxValue) {
        if (value < minValue) {
            return minValue;
        }
        if (value > maxValue) {
            return maxValue;
        }
        return value;
    }

    public static double clamp(double value, double minValue, double maxValue) {
        if (value < minValue) {
            return minValue;
        }
        if (value > maxValue) {
            return maxValue;
        }
        return value;
    }

    public static BufferedImage colorImage(BufferedImage image, Color color) {

        int R = color.getRed(), G = color.getGreen(), B = color.getBlue();

        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, a;
                p = tmp.getRGB(xx, yy);
                a = (p >> 24) & 0xff;
                p = (a << 24) | (R << 16) | (G << 8) | B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage colorImage(BufferedImage image, float R, float G, float B) {

        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, a;
                p = tmp.getRGB(xx, yy);
                a = (p >> 24) & 0xff;
                p = (a << 24) | ((int) R << 16) | ((int) G << 8) | (int) B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage colorImage(BufferedImage image, int R, int G, int B) {

        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, a;
                p = tmp.getRGB(xx, yy);
                a = (p >> 24) & 0xff;
                p = (a << 24) | (R << 16) | (G << 8) | B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage alphaImage(BufferedImage image, int a) {

        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, A, R, G, B;
                p = tmp.getRGB(xx, yy);
                A = (p >> 24) & 0xff;
                if (A == 0) {
                    continue;
                }
                R = (p >> 16) & 0xff;
                G = (p >> 8) & 0xff;
                B = p & 0xff;
                p = (a << 24) | (R << 16) | (G << 8) | B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage addAlphaImage(BufferedImage image, int a) {

        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, A, R, G, B;
                p = tmp.getRGB(xx, yy);
                A = (p >> 24) & 0xff;
                A = clamp(A + a, 0, 255);
                R = (p >> 16) & 0xff;
                G = (p >> 8) & 0xff;
                B = p & 0xff;
                p = (A << 24) | (R << 16) | (G << 8) | B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage pixelImage(BufferedImage image, float xpixel, float ypixel) {

        BufferedImage tmp = new BufferedImage(Math.round(xpixel), Math.round(ypixel), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) tmp.getGraphics();
        AffineTransform at = new AffineTransform();
        at.scale((float) xpixel / image.getWidth(), (float) ypixel / image.getHeight());
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(image, op, 0, 0);

        return tmp;
    }
}
