import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MyEnt{
    BufferedImage img;

    public Player(){
        img = loadImage();
    }

    public void update(long timePassed, Location mouseLocation){
        move(mouseLocation);
    }

    public void render(Graphics g){
        if(draw || erase)
        drawCircle(g);
    }
}