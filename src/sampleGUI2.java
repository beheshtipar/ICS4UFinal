import java.awt.*;

import javax.swing.*;

import java.awt.event.*;


public class sampleGUI2 {
	protected static void init(){
		JFrame frame = new JFrame("Guess that Champion!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		Font titleFont = new Font("Helvetica", Font.BOLD, 20);
		
		Container contentPane = frame.getContentPane();
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(800, 75));
		titlePanel.setBackground(Color.RED);
		
		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(800, 550));
		content.setBackground(Color.GREEN);
		
		
		JLabel title = new JLabel ("Guess that Champion!");
		title.setFont(titleFont);
		titlePanel.add(title);
		
		
		
		contentPane.add(titlePanel);
		contentPane.add(content);
		
		frame.pack();
		String disc = "\"Guess That Champion!\" isn't endorsed by Riot Games and doesn't reflect \nthe views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. \nLeague of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.";
		JOptionPane disclaimer = new JOptionPane();
	
		frame.setVisible(true);
	}
	
	public static void main (String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                init();
            }
        });
	}
}
