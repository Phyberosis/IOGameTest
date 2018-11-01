import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class MyEnt {
    int x, y;

    int radius;

    long actionDelay = 15;
    long lastActionTime = System.currentTimeMillis();

    String imageSrc;

    public MyEnt(int radius, String imgSrc){
        this.radius = radius;
        imageSrc = imgSrc;
    }

    public boolean inBounds(int x, int y, int r){
        return sqr(this.x - x) + sqr(this.y - y) < sqr(r+radius);
    }

    void drawPicture(Graphics g, BufferedImage img){
        g.drawImage(img, (int)(x-radius), (int)(y-radius), null);
    }

    void drawCircle(Graphics g){
        g.setColor(Color.blue);
        g.fillOval(x, y , radius, radius);
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

    Image scale(Image i, int r){
        return i.getScaledInstance(r, r, Image.SCALE_DEFAULT);
    }

    BufferedImage toBufImage(Image img){
        BufferedImage retImg;
        img = scale(img, radius*2);
        retImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = retImg.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return retImg;
    }

    private int sqr(int a){
        return a*a;
    }

    public String printLoc(){
        return Integer.toString(x)+", "+Integer.toString(y);
    }
}
