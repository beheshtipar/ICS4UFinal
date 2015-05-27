/* "Guess That champion:" isn't endorsed by Riot Games and doesn't reflect 
 * the views or opinions of Riot Games or anyone 
 * officially involved in producing or managing League of Legends. 
 * League of Legends and Riot Games are trademarks or 
 * registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.*/


import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
public class sampleGui extends JFrame{
	protected static void init(){
		
		JFrame frame = new JFrame("Guess That Champion!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		
		
		Container contentPane = frame.getContentPane();
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		contentPane.setLayout(gridbag);
		contentPane.setSize(new Dimension(800, 600));
		contentPane.setBackground(Color.RED);
		
		
		JPanel titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(300, 600));
		c.anchor = GridBagConstraints.PAGE_START;
		titlePanel.setBackground(Color.GREEN);
		contentPane.add(titlePanel, c);
		
		
		Font titleFont = new Font("Calibri", Font.BOLD, 25);    //font selection
		Font subTitleFont = new Font ("Calibri", Font.ITALIC, 18);
		Font textFont = new Font("Arial", Font.PLAIN, 13);
		
		
		
		c.gridx ++;
		c.insets = new Insets(10, 20, 20, 50);
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridheight = 2; 
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;														//setting for title
		c.ipady = 20;
		JLabel title = new JLabel("Guess That Champion!");
		title.setFont(titleFont);
		titlePanel.add(title, c);
		
		
//		c.anchor = GridBagConstraints.PAGE_END;
//		c.gridy ++;
//		
//		c.gridwidth = GridBagConstraints.REMAINDER;
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.weightx = 1;
//		c.weighty = 1.0;
//		c.ipady = 10;
//		JLabel subTitle = new JLabel("A quizzing tool made by Parsa Beheshti and Ian Bantoto");
//		subTitle.setFont(subTitleFont);
//		titlePanel.add(subTitle, c);
		
		
//		c.anchor = GridBagConstraints.LINE_START;
//		c.gridheight = 1;
//		c.weightx = 1.0;
//		c.gridy ++;
//		c.ipady = 10;
//		//c.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
//		JLabel choices = new JLabel("Please select the quiz categories and number of trials: ");
//		choices.setFont(text);
//		
//		frame.getContentPane().add(choices, c);
//		c.fill = GridBagConstraints.BOTH;
//		
		
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
