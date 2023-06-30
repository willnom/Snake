package snake;

import java.awt.Color;
import java.awt.Graphics;


public class DebugScreen {

    GamePanel panel;
    Snake snake;

    public DebugScreen(GamePanel panel, Snake snake) {
        this.panel = panel;
        this.snake = snake;
    }
    
    public int getX(){
        return snake.width;
    }
    
    public void drawScreen(Graphics g){
        g.setColor(Color.white);        
        for (int i = getX(); i < panel.getWidth(); i+=getX()) {
            g.drawLine(0, i, panel.getWidth(), i);
            g.drawLine(i, 0, i, panel.getWidth());
        }       
    }
}
