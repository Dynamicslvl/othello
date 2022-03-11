package othello.global;

/**
 *
 * @author ADMIN
 */
public enum Layer {
    Background(0),
    Tile(1),
    Piece(2),
    Effect(3),
    UI(4);
    
    private final int id;
    
    private Layer(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
}
