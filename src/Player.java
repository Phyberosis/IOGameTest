import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MyEnt{
    Image me;
    BufferedImage bufMe;

    public Player(){
        super(60, "face.PNG");
        me = loadImage();
        me = me.getScaledInstance(radius*2, radius*2, Image.SCALE_DEFAULT);
        bufMe = new BufferedImage(me.getWidth(null), me.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufMe.createGraphics();
        bGr.drawImage(me, 0, 0, null);
        bGr.dispose();
    }

    public void update(long timePassed, Location mouseLocation){
        if(System.currentTimeMillis() - lastActionTime > actionDelay) {
            lastActionTime = System.currentTimeMillis();
            x = move(x, mouseLocation.x);
            y = move(y, mouseLocation.y);
        }
    }

    private int move(int a, int target){
        double rate = 20;
        int da = (int) Math.round(((target - a) / rate));
//        if(Math.abs(target - a) < rate/10 && target-a != 0){
//            da = Math.abs(target - a)/(target-a);
//        }
        return a+da;
    }

    public void grow(){
        radius++;
        bufMe = toBufImage(me);
    }

    public void render(Graphics g){
        drawPicture(g, bufMe);
    }
}