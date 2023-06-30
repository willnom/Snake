package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake extends Entity {

    public final int scale;
    public boolean up, down, left, right;
    public final Rectangle hitbox;
    public final GamePanel panel;
    public final int speed;
    public final Random random;
    public final List<Rectangle> hitboxes;
    public final List<Rectangle> bodyParts;
    private boolean collision;
    private boolean selfCollision;
 
    public Snake(GamePanel panel) {
        this.panel = panel;
        this.scale = panel.getScale();
        random = new Random();
        super.width *= scale;
        super.height *= scale;
        spawnCoordinates();
        super.x *= scale;
        super.y *= scale;
        hitbox = new Rectangle(x, y, width, height);
        speed = width;
        hitboxes = new ArrayList<>();
        bodyParts = new ArrayList<>();
    }

    @Override
    public void spawnCoordinates() {
        int boundX = super.boundX(panel);
        int boundY = super.boundY(panel);

        super.x = random.nextInt(boundX) * super.width;
        super.y = random.nextInt(boundY) * super.height;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.green.darker());
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        drawBodyParts(g);
    }

    public void drawBodyParts(Graphics g) {
        if (!bodyParts.isEmpty()) {
            for (int i = 0; i < bodyParts.size(); i++) {
                g.setColor(Color.green.brighter());
                g.fillRect(bodyParts.get(i).x, bodyParts.get(i).y, super.width, super.height);
                g.setColor(Color.black);
                g.drawRect(bodyParts.get(i).x, bodyParts.get(i).y, super.width, super.height);
            }
        }
    }

    public void moveSnake() {
        if (up || down || left || right) {
            panel.start = false;
            if (up) {
                hitbox.y -= speed;
            }
            if (down) {
                hitbox.y += speed;
            }
            if (left) {
                hitbox.x -= speed;
            }
            if (right) {
                hitbox.x += speed;
            }
        }
    }

    @Override
    public void update() {
        if (!panel.gameOver && !panel.win) {

            moveSnake();
            Collisions.selfCollision(this, panel);
            checkBoundaries();

            if (!selfCollision) {
                Collisions.moveBodyParts(x, y, this, panel);
                x = hitbox.x;
                y = hitbox.y;
            }
        }
    }

    public void restartGame() {
        spawnCoordinates();
        hitboxes.clear();
        bodyParts.clear();
        selfCollision = false;
        collision = false;
    }

    public void checkBoundaries() {
        if (hitbox.x + hitbox.width > panel.getWidth()) {
            hitbox.x = 0;
        }
        if (hitbox.x < 0) {
            hitbox.x = panel.getWidth() - width;
        }
        if (hitbox.y + hitbox.height > panel.getHeight()) {
            hitbox.y = 0;
        }
        if (hitbox.y < 0) {
            hitbox.y = panel.getHeight() - height;
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean isCollision() {
        return collision;
    }

    public boolean isSelfCollision() {
        return selfCollision;
    }

    public void setSelfCollision(boolean selfCollision) {
        this.selfCollision = selfCollision;
    }

    public boolean isListEmpty() {
        return bodyParts.isEmpty() && hitboxes.isEmpty();
    }
}
