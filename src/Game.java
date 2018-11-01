import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements Runnable, KeyListener{

    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;

    private Thread thread = new Thread();
    private boolean running = true;

    private boolean first = true;

    private Player player = new Player();

    public Game(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void start(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void update(long dt){
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;
        player.update(dt, new Location(mouseX, mouseY));
    }

    public void paint(Graphics g){
//        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.white);
        if(first) {
        g.fillRect(0, 0, WIDTH, HEIGHT);
            first  = false;
        }

        player.render(g);
    }

    public void run(){
        long lastUpdate = System.currentTimeMillis();
        while(running){
            update(System.currentTimeMillis() - lastUpdate);
            lastUpdate = System.currentTimeMillis();
            repaint();
        }
    }

    public void stop(){
        running = false;
        try{
            thread.join();
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println(e.toString());
        switch (key){
            case KeyEvent.VK_E:
                running = false;
                break;
            case KeyEvent.VK_D:
                player.draw = true;
                break;
            case KeyEvent.VK_F:
                player.erase = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_D:
                player.draw = false;
                break;
            case KeyEvent.VK_F:
                player.erase = false;
        }
    }
}
