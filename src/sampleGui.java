import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class sampleGui {
	protected static void init(){
		JFrame frame = new JFrame("grey GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel helloWorld = new JLabel ("Hello World!");
		helloWorld.setPreferredSize(new Dimension(175, 100));
		frame.getContentPane().add(helloWorld, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                init();
            }
        });
	}

}
