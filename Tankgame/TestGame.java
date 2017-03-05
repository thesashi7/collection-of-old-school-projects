package Tankgame;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

public class TestGame {
    public static void main(String argv[]) {
        final Tankgame demo = new Tankgame();
        demo.init();
        JFrame f = new JFrame("Tank Wars");
        f.addWindowListener(new WindowAdapter() {});
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(948, 548));
        f.setVisible(true);
        f.setResizable(false);
        demo.start();
      
    }

}
