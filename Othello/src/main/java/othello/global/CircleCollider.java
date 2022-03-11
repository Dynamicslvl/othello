package othello.global;

/**
 *
 * @author ADMIN
 */
public class CircleCollider {
    
    public float x = 0, y = 0, r = 0;
    
    public CircleCollider(){}
    
    public CircleCollider(float x, float y, float r){
        this.x = x;
        this.y = y;
        this.r = r;
    }
    
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }
    
}
