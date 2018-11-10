package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import IO.EatGame;

public class Food extends MyEnt{

    private Image me;

    public Food(String imgSrc) {
        super(30, imgSrc, Entities.FOOD);

        generate();

        final double avgDuration = 1500000000.0;
        final double deviation = 100000000.0;
        actionDelay = Math.round((Math.random()*deviation) + avgDuration);

        me = loadImage();
    }

    public void eat(long now){
//        System.out.println(now);
        active = false;
        lastActionTime = now;
    }

    private void generate(){
        active = true;
        x = getRandInt(EatGame.WIDTH);
        y = getRandInt(EatGame.HEIGHT);

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

        imageSrc = src;
        me = loadImage();
    }

    public void tryReset(long now){
//        System.out.println(now - lastActionTime + ", " + actionDelay);
        if(now - lastActionTime > actionDelay){
            generate();
        }
    }

    private int getRandInt(int range){
        return (int)Math.round(Math.random()*range*0.9 + range*0.05);
    }

    @Override
    public void update(long now) {
        if(!active) {
            tryReset(now);
        }
    }

    @Override
    public void collided(MyEnt ent, long now){
        if(ent.getType() == Entities.FOOD)
            generate();
    }

    @Override
    public void render(GraphicsContext g) {
        if(active)
//            drawCircle(g);
            drawPicture(g, me);
    }
}
