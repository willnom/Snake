package snake;

import java.awt.Rectangle;

public class Collisions {

    public static void updateCollision(Snake snake, Worm worm) {
        if (snake.getHitbox().intersects(worm.getHitbox()) && !snake.win) { 
            snake.setCollision(true);
            worm.points++;
            if (worm.points == 30) {
               snake.win = true;
            }else{
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
        if (!snake.isListEmpty()) {
            for (Rectangle hitboxe : snake.hitboxes) {
                if (snake.getHitbox().intersects(hitboxe)) {
                    snake.setSelfCollision(true);
                    panel.gameOver = true;
                }
            }
        }
    }
}
