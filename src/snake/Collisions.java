package snake;

import java.awt.Rectangle;

public class Collisions {

    public static void updateCollision(Snake snake, Worm worm) {
        if (snake.getHitbox().intersects(worm.getHitbox()) && !snake.panel.win) {
            snake.setCollision(true);

            worm.points++;
            if (worm.points == 40) {
                snake.panel.win = true;
            } else {
                worm.spawnCoordinates();
            }
        }
    }

    public static void addBodyParts(Snake snake, int x, int y, GamePanel panel) {
        if (snake.isCollision()) {
            snake.hitboxes.add(new Rectangle(x, y, snake.width, snake.height));
            snake.bodyParts.add(new Rectangle(x, y, snake.width, snake.height));
            snake.setCollision(false);
        }
    }

    public static void moveBodyParts(int x, int y, Snake snake, GamePanel panel) {
        if (!snake.isListEmpty()) {
            addBodyParts(snake, snake.bodyParts.get(snake.bodyParts.size() - 1).x,
                    snake.bodyParts.get(snake.bodyParts.size() - 1).y, panel);

            for (int i = snake.hitboxes.size() - 1; i > 0; i--) {
                snake.hitboxes.get(i).x = snake.hitboxes.get(i - 1).x;
                snake.hitboxes.get(i).y = snake.hitboxes.get(i - 1).y;

                if (!snake.isSelfCollision()) {
                    snake.bodyParts.get(i).x = snake.hitboxes.get(i).x;
                    snake.bodyParts.get(i).y = snake.hitboxes.get(i).y;
                }
            }
            snake.hitboxes.get(0).x = x;
            snake.hitboxes.get(0).y = y;

            if (!snake.isSelfCollision()) {
                snake.bodyParts.get(0).x = snake.hitboxes.get(0).x;
                snake.bodyParts.get(0).y = snake.hitboxes.get(0).y;
            }

        } else {
            addBodyParts(snake, x, y, panel);
        }

    }

    public static void selfCollision(Snake snake, GamePanel panel) {
        /*
        if (!snake.isListEmpty()) {
            for (Rectangle hitboxe : snake.hitboxes) {
                if (snake.getHitbox().intersects(hitboxe)) {
                    snake.setSelfCollision(true);
                    panel.gameOver = true;
                }
            }
        }i had to comment this because there is an annoying bug where the 
        snake head collides with its first body part, apparently is a bug in
        the keys, but i added a temporary fix below. The keys bug shows itself when
        the snake is for example going to the left side and you press left and down
        keys at the same time and the snake moves to the opposite side, when this happened
        the snake collided with its first body part. So the fix below adds a constraint
        so that snake cannot collide with its first body part, obviously the bug when 
        snake moves to opposite side by pressing the two keys mentioned previously is 
        present yet, but the snake head no longer collide with its first body part.*/
        
        if (snake.hitboxes.size() > 1) {
            for (int i = 1; i < snake.hitboxes.size() - 1; i++) {
                if (snake.getHitbox().intersects(snake.hitboxes.get(i))) {
                    snake.setSelfCollision(true);
                    panel.gameOver = true;
                }
            }
        }
    }
}
