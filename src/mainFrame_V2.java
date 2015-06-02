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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame_V2 window = new mainFrame_V2();
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
		lblBG.setBounds(-10, 0, 691, 260);
		
		
		mainPane.add(lblBG);
		
		JLabel lblTitle = new JLabel("Guess That Champion!");
		lblTitle.setForeground(UIManager.getColor("Menu.background"));
		lblTitle.setBounds(0, 0, 681, 27);
		
		lblTitle.setFont(new Font("WeblySleek UI Semibold", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mainPane.setLayer(lblTitle, 1);
		mainPane.add(lblTitle);
		
		JCheckBox checkPassive = new JCheckBox("Champion Passive");
		checkPassive.setFont(new Font("Yu Gothic", Font.PLAIN, 14));
		mainPane.setLayer(checkPassive, 1);
		checkPassive.setBounds(6, 88, 150, 37);
		mainPane.add(checkPassive);
		
		JCheckBox checkDefault = new JCheckBox("Champion Default Ability");
		checkDefault.setFont(new Font("Yu Gothic", Font.PLAIN, 14));
		checkDefault.setToolTipText("Such as champion Q, W, E");
		mainPane.setLayer(checkDefault, 1);
		checkDefault.setBounds(241, 88, 198, 37);
		mainPane.add(checkDefault);
		
		JCheckBox checkUlti = new JCheckBox("Champion Ultimate");
		checkUlti.setFont(new Font("Yu Gothic", Font.PLAIN, 14));
		mainPane.setLayer(checkUlti, 1);
		checkUlti.setBounds(518, 88, 157, 37);
		mainPane.add(checkUlti);
		
		JButton btnStart = new JButton("Start!");
		btnStart.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 15));
		mainPane.setLayer(btnStart, 1);
		btnStart.setBounds(260, 171, 150, 62);
		mainPane.add(btnStart);
	}
}
