package snake;

import java.awt.Graphics;
import java.awt.Rectangle;


public abstract class Entity {

    protected int x, y, width, height;

    public Entity() {
        this.height = 30;
        this.width = 30;
    }
    
    public abstract void draw(Graphics g);
    public abstract void update();
    public abstract void spawnCoordinates();
    public abstract void restartGame();
    
    public int boundX(GamePanel panel){
        return (panel.getWidth() - (width * panel.getScale())) / (width * panel.getScale());
        
    }
    
    public int boundY(GamePanel panel){
        return (panel.getHeight() - (height * panel.getScale())) / (height * panel.getScale());
    }  
}
