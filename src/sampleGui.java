/*[The title of Your Application] isn't 
endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone 
officially involved in producing or managing League of Legends. 
League of Legends and Riot Games are trademarks or 
registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.*/


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
