package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Worm extends Entity {

    private final GamePanel panel;
    private final Snake snake;
    private final Random random;
    private final Rectangle hitbox;
    public int points;
    private int counter;

    Worm(GamePanel panel, Snake snake) {
        //super.x = 20;
        this.panel = panel;
        this.snake = snake;
        super.width *= panel.getScale();
        super.height *= panel.getScale();
        hitbox = new Rectangle(x, y, super.width, super.height);
        random = new Random();
        spawnCoordinates();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.blue.brighter());
        g.fillRect(x, y, super.width, super.height);
        g.setColor(Color.white);
        g.drawRect(x, y, super.width, super.height);
    }

    @Override
    public void update() {
        Collisions.updateCollision(snake, this);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public void spawnCoordinates() {

        int boundX = super.boundX(panel);
        int boundY = super.boundY(panel);

        if (panel.gameOver || panel.win) {
            points = 0;
            super.x = random.nextInt(boundX) * super.width;
            super.y = random.nextInt(boundY) * super.height;            
        }

        if (snake.x == super.x) {
            super.x = random.nextInt(boundX) * super.width;
        }
        if (snake.y == super.y) {
            super.y = random.nextInt(boundY) * super.height;
        }

        hitbox.x = super.x;
        hitbox.y = super.y;
    }
}
