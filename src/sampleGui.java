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
		
		JFrame frame = new JFrame("Guess That Champion!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		frame.setLayout(gridbag);
		
		Font titleFont = new Font("Helvetica", Font.BOLD, 25);
		Font text = new Font("Arial", Font.PLAIN, 13);
		
		c.anchor = GridBagConstraints.NORTH;
		c.gridheight = 2; 
		c.weightx = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;														//setting for title
		c.ipady = 20;
		JLabel title = new JLabel("Guess That Champion!");
		title.setFont(titleFont);
		gridbag.setConstraints(title, c);
		frame.getContentPane().add(title);
		c.fill = GridBagConstraints.BOTH;
		
		
//		c.gridheight = 1;
//		c.weightx = 1.0;
//		
//		c.ipady = 10;
//		
//		JLabel choices = new JLabel("Please select the quiz categories and number of trials: ");
//		choices.setFont(text);
//		gridbag.setConstraints(choices, c);
//		frame.getContentPane().add(choices);
//		c.fill = GridBagConstraints.BOTH;
		
		
		frame.setVisible(true);
	}
	public void draw (Paint c){
		
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
