import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class Game extends JPanel implements Runnable, KeyListener{

    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;

    private Thread thread = new Thread();
    private boolean running = true;

    private Player player = new Player();
    private LinkedList<Food> foods = new LinkedList<>();

    //debug
    private long lastUpdate;
    private int FPS;
    private int frameCount;
    private String cursorLoc;
    private String plrLoc;
    private int collisions;
    private boolean debug = true;

    public Game(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        if(debug){
            lastUpdate = System.currentTimeMillis();
            FPS = 0;
            frameCount = 0;
            collisions = 0;
            cursorLoc = "";
            plrLoc = "";
        }

        for(int i = 0; i<40; i++) {
            int choice = (int) Math.round(Math.random() * 3 + 1);
            String src = "candy.png";
            switch (choice) {
                case 2:
                    src = "broccoli.png";
                    break;
                case 3:
                    src = "pizza.png";
                    break;
            }

            int x, y;
            while (true){
                x = getRandInt(WIDTH);
                y = getRandInt(HEIGHT);

                boolean pass = true;
                for(Food f : foods){
                    if(f.inBounds(x, y, f.radius)){
                        pass = false;
                        break;
                    }
                }

                if(pass){
                    break;
                }
            }


            foods.add(new Food(src, x, y));
        }
    }

    public void initialize(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void update(long dt){
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;

        player.update(dt, new Location(mouseX, mouseY));

        for(Food f : foods){
            if(f.eaten){
                f.tryReset();
                continue;
            }

            int[] boundData = f.getBoundData();

            if(player.inBounds(boundData[0], boundData[1], boundData[2])){
                f.eat();
                player.grow();
            }
        }

        if(debug){
            plrLoc = player.printLoc();
            cursorLoc = Integer.toString(mouseX)+", "+Integer.toString(mouseY);
        }
    }

    public void paint(Graphics g){
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.white);

        for(Food f : foods){
            f.render(g);
        }

        player.render(g);

//        System.out.println(debug);
        if(debug){
            frameCount++;
            if(System.currentTimeMillis() - lastUpdate > 1000){
                lastUpdate = System.currentTimeMillis();
                FPS = frameCount;
                frameCount = 0;
            }
            printDebug(g);
        }
    }

    private void printDebug(Graphics g){
//        System.out.println(FPS);
        g.setColor(Color.RED);
        g.drawString(Integer.toString(FPS), 2, 22);
        g.drawString(plrLoc, 2, 42);
        g.drawString(cursorLoc, 2, 62);

    }

    public void run(){
        long lastUpdate = System.currentTimeMillis();
        while(running){
            update(System.currentTimeMillis() - lastUpdate);
            lastUpdate = System.currentTimeMillis();
            repaint();
        }
    }

    private int getRandInt(int range){
        return (int)Math.round(Math.random()*range*0.9 + range*0.05);
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

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
