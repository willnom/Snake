package snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputs extends KeyAdapter {
    
    private final Snake snake;
    private final Worm worm;
    private int u = 1, d = 2, l = 3, r = 4;
    int directions = 0b1111;
    
    public KeyInputs(Snake snake, Worm worm) {
        this.snake = snake;
        this.worm = worm;
    }
    
    private void changeKeyValues(int u, int d, int l, int r,
            boolean up, boolean down, boolean left, boolean right) {
       
        this.u = u;
        this.d = d;
        this.l = l;
        this.r = r;
        
        snake.up = up;
        snake.down = down;
        snake.left = left;
        snake.right = right;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && u == 1) {
            changeKeyValues(1, 0, 3, 4, true, false, false, false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && d == 2) {
            changeKeyValues(0, 2, 3, 4, false, true, false, false);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && l == 3) {
            changeKeyValues(1, 2, 3, 0, false, false, true, false);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && r == 4) {
            changeKeyValues(1, 2, 0, 4, false, false, false, true);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && (snake.panel.gameOver || snake.panel.win)) {
            changeKeyValues(1, 2, 3, 4, false, false, false, false);    
            snake.restartGame();
            worm.spawnCoordinates();
            snake.panel.gameOver = false;
            snake.panel.win = false;
            snake.panel.start = true;       
        }        
    }
    
}
