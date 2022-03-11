package othello.ui;

import othello.global.Layer;
import static othello.manager.Game.gameState;
import othello.manager.GameObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Admin
 */
public abstract class TextArea extends GameObject {
    
    protected float text_width  = 0;
    protected Font font = null;
    protected Color color = Color.white;
    protected String text = "";
    protected float tx_offset = 0, ty_offset = 0;
    private final FontRenderContext frc = new FontRenderContext(new AffineTransform(),true,true);

    public TextArea(float x, float y, Layer layer) {
        super(x, y, layer);
    }
    
    @Override
    public void render(Graphics2D g) {
        super.render(g);
        if(!states.contains(gameState)) return;
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x - x_offset + tx_offset, y - y_offset + ty_offset);
    }

    @Override
    public void tick() {
        super.tick();
        if(font != null){
            text_width = (float) font.getStringBounds(text, frc).getWidth();
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getTx_offset() {
        return tx_offset;
    }
    
    public float getTy_offset() {
        return ty_offset;
    }

    public void setText_offset(float tx_offset, float ty_offset) {
        this.tx_offset = tx_offset;
        this.ty_offset = ty_offset;
    }
}
