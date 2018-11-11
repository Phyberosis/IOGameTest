package mechanics;

import IO.EatGame;
import IO.SceneIDs;
import entities.Entities;
import entities.MyEnt;
import entities.Player;
import entities.Shark;

import java.util.LinkedList;

public class LogicHandler implements Runnable {

    private Thread thread = new Thread();
    private boolean running = true;

    private PhysicsHandler physicsHandler;
    private LinkedList<MyEnt> ents;

    private EatGame parent;

    private long lastUpdate;
    private final long UPDATE_PERIOD = 16000000;
    private boolean isSlowing = false;

    public LogicHandler(LinkedList<MyEnt> ents, EatGame eg){
        physicsHandler = new PhysicsHandler();
        this.ents = ents;
        parent = eg;
    }

    public void start(){
        running = true;
        thread = new Thread(this);
        thread.start();

        lastUpdate = getCurrentTime();
    }

    private long getCurrentTime(){
        return System.nanoTime();
    }

    private void update(long now){
        if(now - lastUpdate < UPDATE_PERIOD)
            return;
        lastUpdate = getCurrentTime();

        physicsHandler.checkCollisions(ents, now);
        Player player = null;
        Shark shark = null;

        for(MyEnt e : ents){
            e.update(now);
            if(e.getType() == Entities.PLAYER)
                player = (Player) e;
            else if(e.getType() == Entities.SHARK)
                shark = (Shark) e;
        }

        if(shark != null && player != null){
//            System.out.println("player loc given to shark" + player.getBounds()[0] + ", "+player.getBounds()[1]);
            shark.givePlayerLocation(player.getBounds());

            if(shark.isEaten()){
                parent.changeScene(SceneIDs.WIN);
            }else if(player.isEaten()){
                parent.changeScene(SceneIDs.LOSE);
            }
        }else{
            System.out.println("no shark/player");
        }
    }

    public void slowTime(){
        isSlowing = true;
    }

    public void resetTime(){
        isSlowing = false;
    }

    @Override
    public void run(){
        long lastUpdate = getCurrentTime();
        while(running){
            update(currentTime());
            lastUpdate = getCurrentTime();
        }

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long currentTime(){
        return System.nanoTime();
    }

    public void stop(){
        running = false;
    }
}
