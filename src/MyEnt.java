import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class MyEnt {
    private int x, y;

    private int raduis = 7;
    private int eraseRad = 50;

    private long moveDelay = 15;
    private long lastMove = System.currentTimeMillis();

    public boolean draw = false, erase = false;

    String imageSrc = "C:\\Users\\phybe\\Desktop\\api.PNG";

    private int moveOne(int a, int target){
        lastMove = System.currentTimeMillis();
        return a + ((target - a) / 10);
    }

    void move(Location loc){
        if(System.currentTimeMillis() - lastMove > moveDelay) {
            lastMove = System.currentTimeMillis();
            x = moveOne(x, loc.x);
            y = moveOne(y, loc.y);
        }
    }

    void drawPicture(Graphics g, BufferedImage img){
        g.drawImage(img, x, y, null);
    }

    void drawCircle(Graphics g){
        g.setColor(Color.blue);
        if(erase){
            g.setColor(Color.white);
            g.fillOval(x - eraseRad, y-eraseRad, eraseRad, eraseRad);
        }else{
            g.fillOval(x, y , raduis, raduis);
        }
    }

    void clear(Graphics g){
        g.setColor(Color.blue);
    }

    BufferedImage loadImage(){
        BufferedImage img;
        try {
            img = ImageIO.read(new File(imageSrc));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
