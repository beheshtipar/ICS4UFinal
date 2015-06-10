import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;


public class guiGuess_v07 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiGuess_v07 window = new guiGuess_v07();
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
	public guiGuess_v07() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents ofthe frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame("Guess That Champion!");
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 600);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 800, 578);
		frame.getContentPane().add(layeredPane);
		
		BufferedImage BG = ImageIO.read(new File("lib//akaliBG.jpg"));
		JLabel lblBG = new JLabel(/*new ImageIcon(BG)*/);
		layeredPane.setLayer(lblBG, 0);
		lblBG.setBounds(0, 0, 800, 578);
		layeredPane.add(lblBG);
		
		JPanel mainPane = new JPanel();
		mainPane.setForeground(new Color(255, 255, 255));
		layeredPane.setLayer(mainPane, 2);
		mainPane.setBounds(0, 0, 800, 578);
		layeredPane.add(mainPane);
		mainPane.setBackground(null);
		mainPane.setOpaque(false);
		mainPane.setLayout(null);
		
		JLabel lblQuizPic = new JLabel("");
		lblQuizPic.setBounds(368, 102, 64, 64);
		mainPane.add(lblQuizPic);
		
		JButton btnChamp1 = new JButton("");
		btnChamp1.setBounds(270, 190, 120, 120);
		mainPane.add(btnChamp1);
		
		JButton btnChamp2 = new JButton("");
		btnChamp2.setBounds(408, 190, 120, 120);
		mainPane.add(btnChamp2);
		
		JButton btnChamp3 = new JButton("");
		btnChamp3.setBounds(270, 320, 120, 120);
		mainPane.add(btnChamp3);
		
		JButton btnChamp4 = new JButton("");
		btnChamp4.setBounds(408, 320, 120, 120);
		mainPane.add(btnChamp4);
		
		JLabel lblScore = new JLabel("Score: 200\n");
		lblScore.setFont(new Font("Bangla MN", Font.BOLD, 13));
		lblScore.setForeground(new Color(255, 255, 255));
		lblScore.setBounds(351, 470, 113, 25);
		mainPane.add(lblScore);
		
		JLabel lblTimer = new JLabel("59");
		lblTimer.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		lblTimer.setForeground(new Color(255, 255, 255));
		lblTimer.setBounds(384, 512, 32, 37);
		mainPane.add(lblTimer);
		
		JLabel lblLife1 = new JLabel("");
		lblLife1.setBounds(68, 200, 64, 64);
		mainPane.add(lblLife1);
		
		JLabel lblLife2 = new JLabel("\n");
		lblLife2.setBounds(68, 274, 64, 64);
		mainPane.add(lblLife2);
		
		JLabel lblLife3 = new JLabel("");
		lblLife3.setBounds(68, 348, 64, 64);
		mainPane.add(lblLife3);
		
		JPanel timerBG = new JPanel();
		timerBG.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		timerBG.setBackground(new Color(0, 0, 0));
		layeredPane.setLayer(timerBG, 1);
		timerBG.setBounds(300, 460, 200, 100);
		layeredPane.add(timerBG);
		
		
//		BufferedImage BG = ImageIO.read(new File("src/akaliBG.jpg"));
	
	}
}