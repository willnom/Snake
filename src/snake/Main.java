package snake;

import javax.swing.JFrame;


public class Main {
    
    public static void main(String[] args) {
        GamePanel panel =  new GamePanel();
        JFrame frame = new JFrame("Snake");
        Thread thread =  new Thread(panel);
        thread.start();
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
