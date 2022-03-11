package othello.manager;

import static othello.global.General.*;
import othello.global.Layer;
import othello.global.State;
import static othello.manager.Game.gameState;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import othello.global.BoxCollider;

/**
 *
 * @author ADMIN
 */
public abstract class GameObject {

    protected long id;
    protected long owner;
    protected float x, y;
    protected float image_angle = 0;
    protected float image_xscale = 1, image_yscale = 1;
    protected float x_offset = 0, y_offset = 0;
    protected Layer layer;
    protected LinkedList<State> states = new LinkedList<>();
    protected float speed = 0;
    protected float velX = 0, velY = 0;
    protected BufferedImage image = null;
    protected boolean init = true;
    protected boolean active = true;
    protected BoxCollider boxCollider;

    public static long ID = 0;

    public GameObject(float x, float y, Layer layer) {
        this.x = x;
        this.y = y;
        this.layer = layer;
        ID++;
        this.id = ID;
        Handler.addObject((GameObject) this);
    }

    public void destroySelf() {
        Handler.removeObject(this);
    }

    public void tick() {
        if (!states.contains(gameState) || !active) {
            return;
        }
        if (pause && layer != Layer.UI && layer != Layer.Effect) {
            return;
        }
        if (init) {
            create();
            init = false;
        } else {
            update();
            x += velX;
            y += velY;
            lateUpdate();
        }
    }

    protected AffineTransformOp op;

    public void render(Graphics2D g) {
        if (!states.contains(gameState) || !active) {
            return;
        }
        if (image == null || init || image_xscale == 0 || image_yscale == 0) {
            return;
        }
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(image_angle), x_offset, y_offset);
        at.scale(image_xscale, image_yscale);
        op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(image, op, (int) Math.round(x - x_offset), (int) Math.round(y - y_offset));
    }

    public abstract void create();

    public abstract void update();
    
    public void lateUpdate(){};

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public void set_centered_offset(){
        this.x_offset = image.getWidth(null)*image_xscale/2;
        this.y_offset = image.getHeight(null)*image_yscale/2;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public float getImage_angle() {
        return image_angle;
    }

    public void setImage_angle(float image_angle) {
        this.image_angle = image_angle;
    }

    public float getImage_xscale() {
        return image_xscale;
    }

    public void setImage_xscale(float image_xscale) {
        this.image_xscale = image_xscale;
    }

    public float getImage_yscale() {
        return image_yscale;
    }

    public void setImage_yscale(float image_yscale) {
        this.image_yscale = image_yscale;
    }

    public float getX_offset() {
        return x_offset;
    }

    public void setX_offset(int x_offset) {
        this.x_offset = x_offset;
    }

    public float getY_offset() {
        return y_offset;
    }

    public void setY_offset(int y_offset) {
        this.y_offset = y_offset;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
