package test;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {

	public static void main(String[] args) throws InterruptedException {
        JFrame f = new JFrame("aaaa");
        f.setSize(100, 100);
        f.setLocation(100, 100);
        JPanel p = new JPanel();
        f.add(p);

        f.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println((char)e.getKeyCode());
            }
        });

        p.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println((char)e.getKeyCode());
            }
        });
        f.setVisible(true);

    }

}
