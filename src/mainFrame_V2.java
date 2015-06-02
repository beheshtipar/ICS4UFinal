import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.JButton;



public class mainFrame_V2 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {			//secure way to run swing applications
			public void run() {
				try {
					mainFrame_V2 window = new mainFrame_V2();
					String disc = "\"Guess That Champion!\" isn't endorsed by Riot Games and doesn't reflect \nthe views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. \nLeague of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.";
					JOptionPane.showMessageDialog(window.frame, disc, "Disclaimer", JOptionPane.WARNING_MESSAGE );
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public mainFrame_V2() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame("Guess That Champion!");
		frame.setResizable(false);
		frame.setBounds(100, 100, 687, 289);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		
		
		JLayeredPane mainPane = new JLayeredPane();
		frame.getContentPane().add(mainPane, "name_78800221383633");
		
		
		BufferedImage BG = ImageIO.read(new File("src/morgana_vs_ahri_3.jpg"));
		mainPane.setLayout(null);
		JLabel lblBG = new JLabel(new ImageIcon(BG));
		lblBG.setBounds(-50, -10, 737, 375);
		
		
		mainPane.add(lblBG);
		
		JLabel lblTitle = new JLabel("Guess That Champion!");
		lblTitle.setForeground(UIManager.getColor("Menu.background"));
		lblTitle.setBounds(10, 23, 644, 27);
		
		lblTitle.setFont(new Font("WeblySleek UI Semibold", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mainPane.setLayer(lblTitle, 1);
		mainPane.add(lblTitle);
		
		JCheckBox checkPassive = new JCheckBox("Champion Passive");
		checkPassive.setForeground(Color.GREEN);
		checkPassive.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		checkPassive.setOpaque(false);
		mainPane.setLayer(checkPassive, 1);
		checkPassive.setBounds(6, 88, 164, 37);
		mainPane.add(checkPassive);
		
		JCheckBox checkDefault = new JCheckBox("Champion Default Ability");
		checkDefault.setOpaque(false);
		checkDefault.setForeground(Color.GREEN);
		checkDefault.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		checkDefault.setToolTipText("Such as champion Q, W, E");
		mainPane.setLayer(checkDefault, 1);
		checkDefault.setBounds(227, 88, 225, 37);
		mainPane.add(checkDefault);
		
		JCheckBox checkUlti = new JCheckBox("Champion Ultimate");
		checkUlti.setOpaque(false);
		checkUlti.setForeground(Color.GREEN);
		checkUlti.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		mainPane.setLayer(checkUlti, 1);
		checkUlti.setBounds(503, 88, 172, 37);
		mainPane.add(checkUlti);
		
		JButton btnStart = new JButton("Start!");
		btnStart.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 15));
		mainPane.setLayer(btnStart, 1);
		btnStart.setBounds(260, 171, 150, 62);
		mainPane.add(btnStart);
	}
}
