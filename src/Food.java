import java.awt.*;
import java.awt.image.BufferedImage;

public class Food extends MyEnt{

    Image me;
    BufferedImage bufMe;

    boolean eaten = false;

    public Food(String imgSrc, int x, int y) {
        super(30, imgSrc);

        this.x = x;
        this.y = y;

        actionDelay = (int)Math.round(Math.random()*1000 + 1500);

        me = loadImage();
        bufMe = toBufImage(me);
    }

    public void eat(){
        eaten = true;
        lastActionTime = System.currentTimeMillis();
    }

    public void tryReset(){
        if(System.currentTimeMillis() - lastActionTime > actionDelay){
            eaten = false;
        }
    }

    public int[] getBoundData(){
        return new int[]{x, y, radius};
    }

    public void render(Graphics g){
        if(!eaten)
            drawPicture(g, bufMe);
    }
}
