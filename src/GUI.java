import javax.swing.*;

public class GUI extends JFrame{

    public static void main(String args[]) {
        new GUI();
    }

    public GUI(){
        JFrame frame = new JFrame();
        Game g = new Game();

        frame.add(g);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(g);

        frame.pack();
        frame.setVisible(true);

        g.start();
    }
}
