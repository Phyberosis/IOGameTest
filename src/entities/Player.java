package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends MyEnt{
    Image me;

    private Location mouseLocation;
    private boolean eaten = false;
    public Player(){
        super(60, "face.PNG", Entities.PLAYER);
        me = loadImage();
        mouseLocation = new Location(0,0);
    }

    public void giveMouseLocation(int x, int y){
        synchronized (this){
//            System.out.println(x + "," +y);
            mouseLocation.x = x;
            mouseLocation.y = y;
        }
    }

    private int move(int a, int target){
        double rate = 10;
        int da = (int) Math.round(((target - a) / rate));
//        if(Math.abs(target - a) < rate/10 && target-a != 0){
//            da = Math.abs(target - a)/(target-a);
//        }
        return a+da;
    }

    public void grow(){
        radius+=6;
        synchronized (this){
            me = loadImage();
        }
    }

    public void render(GraphicsContext g){
        synchronized (this){
//            System.out.println("player render");
            drawPicture(g, me);
//            drawCircle(g);
        }
    }

    @Override
    public void update(long now) {
//        System.out.println(now-lastActionTime + "/"+actionDelay);
        if(now-lastActionTime > actionDelay) {
            lastActionTime = now;
            synchronized (this){
                x = move(x, mouseLocation.x);
                y = move(y, mouseLocation.y);
            }
        }
    }

    void eaten(){
        eaten = true;
    }

    public boolean isEaten(){
        return eaten;
    }

    @Override
    public void collided(MyEnt ent, long now) {

        switch (ent.getType()){
            case FOOD:
//            System.out.println("grow!" + now);
                Food f = (Food) ent;
                f.eat(now);
                grow();
                break;
            case SHARK:
                if(getBounds()[2] > ent.getBounds()[2]) {
                    Shark s = (Shark) ent;
                    s.eaten();
                }
        }
    }
}