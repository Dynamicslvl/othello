package othello.global;

/**
 *
 * @author Admin
 */
public class BoxCollider {
    private float x, y, w, h;
    
    public BoxCollider(){}
    
    public BoxCollider(float x, float y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    public void setSize(float w, float h){
        this.w = w;
        this.h = h;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }
    
}
