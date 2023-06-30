package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private boolean running;
    public static final byte FPS = 10;
    private int scale = 1;
    private int _width = 450, _height = 450;
    private byte fps;
    public final Snake snake;
    public final Worm worm;
    private DebugScreen screenDbg;
    public boolean gameOver;
    public final Color color;
    public final Font font;
    public final Font fontFPS;

    public GamePanel() {
        checkScale();
        setPreferredSize(new Dimension(_width, _height));
        setBackground(Color.BLACK);
        color = new Color(255, 255, 255, 80);
        snake = new Snake(this);
        worm = new Worm(this, snake);
        font = new Font("Arial", Font.ITALIC, 50 * scale);
        fontFPS = new Font(font.getFontName(), Font.BOLD, 15 * scale);
        //screenDbg = new DebugScreen(this, snake);
        addKeyListener(new KeyInputs(snake, worm));
        setFocusable(true);
    }

    @Override
    public void run() {

        running = true;
        long startTime = System.nanoTime();
        long endTime;
        long delta = 0;
        byte frames = 0;
        double nanoPerFrame = 1_000_000_000 / FPS;
        long clockTime = System.currentTimeMillis();
        long clockElapsed;
        while (running) {
            endTime = System.nanoTime();
            delta += endTime - startTime;
            startTime = endTime;

            if (delta >= nanoPerFrame) {
                update();
                frames++;
                delta -= nanoPerFrame;
            }
            repaint();

            clockElapsed = System.currentTimeMillis() - clockTime;
            if (clockElapsed > 1000) {
                clockTime += 1000;
                fps = frames;
                frames = 0;
            }
        }
    }

    private void update() {
        snake.update();
        worm.update();

    }

    private void checkScale() {
        if (scale < 1) {
            scale = 1;
        } else if (scale > 2) {
            scale = 2;
        }
        _width *= scale;
        _height *= scale;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        worm.draw(g);
        snake.draw(g);
        drawFps(g);
        drawGameOverScreen(g);

        if (screenDbg != null) {
            screenDbg.drawScreen(g);
        }
    }

    public void drawFps(Graphics g) {
        g.setColor(Color.red);
        g.setFont(fontFPS);
        g.drawString("FPS: " + fps, 15 * scale, 15 * scale);
    }

    public int getScale() {
        return scale;
    }

    @Override
    public int getWidth() {
        return getPreferredSize().width;
    }

    @Override
    public int getHeight() {
        return getPreferredSize().height;
    }

    public void drawGameOverScreen(Graphics g) {
        if (gameOver) {
            drawAbstractScreen(g, "You lose!", "score: " + Integer.toString(worm.points));
        }else if(snake.win){
            drawAbstractScreen(g, "You Won!", "");
        }
    }

    public void drawAbstractScreen(Graphics g, String msg1, String msg2) {
        int xy = 100 * scale;
        int width_height = getWidth() - 2 * xy;
        g.setColor(color);
        g.fillRect(xy, xy, width_height, width_height);
        g.setColor(Color.white);
        g.drawRect(xy, xy, width_height, width_height);
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString(msg1, 120 * scale, 200 * scale);
        g.drawString(msg2, 120 * scale, 300 * scale);
    }
}
