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
		
		BufferedImage BG = ImageIO.read(new File("src/akaliBG.jpg"));
		JLabel lblBG = new JLabel(/*new ImageIcon(BG)*/);
		lblBG.setBounds(0, 0, 800, 578);
		layeredPane.add(lblBG);
		
		JPanel mainPane = new JPanel();
		layeredPane.setLayer(mainPane, 1);
		mainPane.setBounds(0, 0, 800, 578);
		layeredPane.add(mainPane);
		mainPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Guess That Champion!");
		lblTitle.setFont(new Font("Baskerville", Font.BOLD, 16));
		lblTitle.setBounds(311, 25, 177, 20);
		mainPane.add(lblTitle);
		
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
		
		
//		BufferedImage BG = ImageIO.read(new File("src/akaliBG.jpg"));
	
	}
}
