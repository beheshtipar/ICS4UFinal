/* 							GUESS THAT CHAMPION
 * 
 * ----------------------------------------------------------------------------
 * "Guess That Champion!" isn't endorsed by Riot Games and doesn't reflect 
 * the views or opinions of Riot Games or anyone 
 * officially involved in producing or managing League of Legends. 
 * League of Legends and Riot Games are trademarks or 
 * registered trademarks of Riot Games, Inc. League of Legends Â© Riot Games, Inc.
 * 
 * ----------------------------------------------------------------------------
 * 
 * FEATURES
 * - Fully functional gameplay w/images of champion ability/passive as hint
 * - Select which categories to be tested on in menu screen
 * - Displays score in GUI
 * - Plays sound to let user know if their guess was correct
 * - Scoring becoomes disabled when timer reaches zero
 * - Player has a limited number of incorrect answers (lives) before their scoring doesn't work
 * - After playing on round, game loops to the category select screen
 * - Pressing the exit button closes the program (everywhere except during disclaimer)
 * 
 * NEW FEATURES
 * - Time limit can now be changed to 30, 60, 90, 120, or 150 seconds
 * - Life limit can now be changed to 1, 3, 5, 7, or 9 lives
 * 
 * PLANNED FEATURES
 * - Select which *champion* categories you'll be tested on (only Marksmen, only Fighters, etc.)
 * - System to keep track of high scores
 * 
 * CODE ADJUSTMENTS
 * - Some commenting
 *   
 * KNOWN BUGS
 * - Takes extremely long time to contact RiotAPI for full champion list (on slow connection)
 * 
 */

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;

import javax.swing.JComboBox;
import java.awt.SystemColor;

public class mainFrame_v4 {
	
	// Instance variables
	
	static List<Champion> champions;
	
	JFrame frame;
	JLabel lblLoadingGif;
	JCheckBox checkPassive, checkDefault, checkUlti;
	JButton btnStart;
	private JComboBox btnDiff;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {			//secure way to run swing applications
			public void run() {
				try {
					mainFrame_v4 window = new mainFrame_v4();
					String disc = "\"Guess That Champion!\" isn't endorsed by Riot Games and doesn't reflect \nthe views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. \nLeague of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.";
					JOptionPane.showMessageDialog(window.frame, disc, "Disclaimer", JOptionPane.WARNING_MESSAGE );
					
					// Get list of champions
					BufferedReader in = new BufferedReader(new FileReader("lib//api-key.txt")); 
			    	String text = in.readLine(); 
			    	in.close();
			        RiotAPI.setMirror(Region.NA);
			        RiotAPI.setRegion(Region.NA);
			        RiotAPI.setAPIKey(text);
			        champions = RiotAPI.getChampions();
					
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
	public mainFrame_v4() throws IOException {
		initialize();
	}
	
	public mainFrame_v4(int score) throws IOException {
		String disc = "You got a score of: " + score;
		JOptionPane.showMessageDialog(this.frame, disc, "Alert", JOptionPane.WARNING_MESSAGE );
		reset();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		
		// JFrame settings
		frame = new JFrame("Guess That Champion!");
		frame.setResizable(false);
		frame.setBounds(100, 100, 687, 289);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		// Set up LayeredPane
		JLayeredPane mainPane = new JLayeredPane();														//The content of the launcher are layered to enable										
		frame.getContentPane().add(mainPane, "name_78800221383633");									//use of background image.
		
		// Set up background imagery
		BufferedImage BG = ImageIO.read(new File("lib/morgana_vs_ahri_3.jpg"));
		mainPane.setLayout(null);
		
		// Loading gif to be played if app is hanging
		URL gifUrl = getClass().getResource("loading.gif");
		ImageIcon loadingGif = new ImageIcon(gifUrl.getPath());
		lblLoadingGif = new JLabel(loadingGif);
		lblLoadingGif.setBounds(272, 140, 128, 128);
		mainPane.add(lblLoadingGif);
		lblLoadingGif.setVisible(false);
		JLabel lblBG = new JLabel(new ImageIcon(BG));
		lblBG.setBounds(-50, -10, 737, 375);
		
		// Add background to pane
		mainPane.add(lblBG);
		
		// Set title
		JLabel lblTitle = new JLabel("Guess That Champion!");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setBounds(10, 23, 644, 27);
		
		// Title font settings
		lblTitle.setFont(new Font("Engravers MT", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mainPane.setLayer(lblTitle, 1);
		mainPane.add(lblTitle);
		
		// Passive checkbox
		checkPassive = new JCheckBox("Champion Passive");
		checkPassive.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		checkPassive.setOpaque(false);
		checkPassive.setToolTipText("Ex: Bard's Traveler's Call");
		mainPane.setLayer(checkPassive, 1);
		checkPassive.setBounds(6, 88, 164, 37);
		
		// Default checkbox
		checkDefault = new JCheckBox("Champion Non-Ultimate");
		checkDefault.setOpaque(false);
		checkDefault.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		checkDefault.setToolTipText("Ex: Ahri's Foxfire");
		mainPane.setLayer(checkDefault, 1);
		checkDefault.setBounds(227, 88, 225, 37);
		
		// Ultimate checkbox
		checkUlti = new JCheckBox("Champion Ultimate");
		checkUlti.setOpaque(false);
		checkUlti.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		checkUlti.setToolTipText("Ex: Ekko's Chronobreak");
		mainPane.setLayer(checkUlti, 1);
		checkUlti.setBounds(503, 88, 172, 37);
		
		// Start button
		btnStart = new JButton("Start!");
		btnStart.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 15));
		mainPane.setLayer(btnStart, 1);
		btnStart.setBounds(260, 171, 150, 62);
		mainPane.add(btnStart);
		
		String OS = System.getProperty("os.name").toLowerCase();
		
		if(OS.indexOf("win") == 0){
			checkPassive.setForeground(Color.RED);
			checkDefault.setForeground(Color.ORANGE);
			checkUlti.setForeground(Color.YELLOW);
		}
		if(OS.indexOf("mac") == 0){
			checkPassive.setForeground(Color.GREEN);
			checkDefault.setForeground(Color.GREEN);
			checkUlti.setForeground(Color.GREEN);
		}
		
		mainPane.add(checkPassive);
		mainPane.add(checkDefault);
		mainPane.add(checkUlti);
		
		String[] diffStrings = { "30 seconds", "60 seconds", "90 seconds", "120 seconds", "150 seconds" };
		
		btnDiff = new JComboBox(diffStrings);
		btnDiff.setBackground(SystemColor.inactiveCaption);
		mainPane.setLayer(btnDiff, 1);
		btnDiff.setToolTipText("Time Limit");
		btnDiff.setMaximumRowCount(5);
		btnDiff.setBounds(504, 140, 150, 20);
		mainPane.add(btnDiff);
		
		String[] lifeString = { "1 life", "3 lives", "5 lives", "7 lives", "9 lives" };
		
		JComboBox btnLives = new JComboBox(lifeString);
		btnLives.setMaximumRowCount(5);
		btnLives.setBackground(SystemColor.inactiveCaption);
		mainPane.setLayer(btnLives, 1);
		btnLives.setToolTipText("Lives");
		btnDiff.setMaximumRowCount(5);
		btnLives.setBounds(10, 140, 160, 20);
		mainPane.add(btnLives);
		
		// Add functionality to start button
		btnStart.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me) {
				try {
					try {
						// Play startup sound
				        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("lib/sounds/open.wav").getAbsoluteFile());
				        Clip clip = AudioSystem.getClip();
				        clip.open(audioInputStream);
				        clip.start();
				    } catch(Exception ex) {
				    	// Catch file error
				        System.out.println("Sound error on playing file: " + "lib/sounds/open.wav");
				        ex.printStackTrace();
				    }
					// If app is hanging, loading gif will play
					btnStart.setVisible(false);
					lblLoadingGif.setVisible(true);
					
					int x = btnDiff.getSelectedIndex();
					int limit = (x+1) * 30;
//					System.out.println(limit);
					
					int y = btnLives.getSelectedIndex();
					int lives = (y*2)+1;
//					System.out.println(lives);
					
					// Move on to game, pass in appropriate variables
					if(!(checkPassive.isSelected() || checkDefault.isSelected() || checkUlti.isSelected()) )
						new guiGuess_v1_3(champions, limit, lives, true, true, true);
					else
						new guiGuess_v1_3(champions, limit, lives, checkPassive.isSelected(), checkDefault.isSelected(), checkUlti.isSelected());
					
					// Dispose of current frame
					frame.dispose();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private void reset() throws IOException {
		
		// JFrame settings
		frame = new JFrame("Guess That Champion!");
		frame.setResizable(false);
		frame.setBounds(100, 100, 687, 289);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		// Set up LayeredPane
		JLayeredPane mainPane = new JLayeredPane();														//The content of the launcher are layered to enable										
		frame.getContentPane().add(mainPane, "name_78800221383633");									//use of background image.
		
		// Set up background imagery
		BufferedImage BG = ImageIO.read(new File("lib/morgana_vs_ahri_3.jpg"));
		mainPane.setLayout(null);
		
		// Create loading gif to be played if app is hanging
		URL gifUrl = getClass().getResource("loading.gif");
		ImageIcon loadingGif = new ImageIcon(gifUrl.getPath());
		lblLoadingGif = new JLabel(loadingGif);
		lblLoadingGif.setBounds(272, 140, 128, 128);
		mainPane.add(lblLoadingGif);
		lblLoadingGif.setVisible(false);
		JLabel lblBG = new JLabel(new ImageIcon(BG));
		lblBG.setBounds(-50, -10, 737, 375);

		// Add background to pane
		mainPane.add(lblBG);

		// Set title
		JLabel lblTitle = new JLabel("Guess That Champion!");
		lblTitle.setForeground(UIManager.getColor("Menu.background"));
		lblTitle.setBounds(10, 23, 644, 27);

		// Title font settings
		lblTitle.setFont(new Font("WeblySleek UI Semibold", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mainPane.setLayer(lblTitle, 1);
		mainPane.add(lblTitle);

		// Passive checkbox
		checkPassive = new JCheckBox("Champion Passive");
		checkPassive.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		checkPassive.setOpaque(false);
		checkPassive.setToolTipText("Ex: Bard's Traveler's Call");
		mainPane.setLayer(checkPassive, 1);
		checkPassive.setBounds(6, 88, 164, 37);

		// Default checkbox
		checkDefault = new JCheckBox("Champion Non-Ultimate");
		checkDefault.setOpaque(false);
		checkDefault.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		checkDefault.setToolTipText("Ex: Ahri's Foxfire");
		mainPane.setLayer(checkDefault, 1);
		checkDefault.setBounds(227, 88, 225, 37);

		// Ultimate checkbox
		checkUlti = new JCheckBox("Champion Ultimate");
		checkUlti.setOpaque(false);
		checkUlti.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		checkUlti.setToolTipText("Ex: Ekko's Chronobreak");
		mainPane.setLayer(checkUlti, 1);
		checkUlti.setBounds(503, 88, 172, 37);

		// Start button
		btnStart = new JButton("Start!");
		btnStart.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 15));
		mainPane.setLayer(btnStart, 1);
		btnStart.setBounds(260, 171, 150, 62);
		mainPane.add(btnStart);

		String OS = System.getProperty("os.name").toLowerCase();

		if(OS.indexOf("win") == 0){
			checkPassive.setForeground(Color.RED);
			checkDefault.setForeground(Color.ORANGE);
			checkUlti.setForeground(Color.YELLOW);
		}
		if(OS.indexOf("mac") == 0){
			checkPassive.setForeground(Color.GREEN);
			checkDefault.setForeground(Color.GREEN);
			checkUlti.setForeground(Color.GREEN);
		}

		mainPane.add(checkPassive);
		mainPane.add(checkDefault);
		mainPane.add(checkUlti);
		
		String[] diffStrings = { "30 seconds", "60 seconds", "90 seconds", "120 seconds", "150 seconds" };
		
		btnDiff = new JComboBox(diffStrings);
		mainPane.setLayer(btnDiff, 1);
		btnDiff.setToolTipText("Time Limit");
		btnDiff.setMaximumRowCount(4);
		btnDiff.setBounds(504, 140, 150, 20);
		mainPane.add(btnDiff);
		
		String[] lifeString = { "1 life", "3 lives", "5 lives", "7 lives", "9 lives" };
		
		JComboBox btnLives = new JComboBox(lifeString);
		mainPane.setLayer(btnLives, 1);
		btnLives.setToolTipText("Lives");
		btnDiff.setMaximumRowCount(5);
		btnLives.setBounds(10, 140, 160, 20);
		mainPane.add(btnLives);
		
		// Add functionality to start button
		btnStart.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me) {
				try {
					try {
						// Play startup sound
				        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("lib/sounds/open.wav").getAbsoluteFile());
				        Clip clip = AudioSystem.getClip();
				        clip.open(audioInputStream);
				        clip.start();
				    } catch(Exception ex) {
				    	// Catch file error
				        System.out.println("Sound error on playing file: " + "lib/sounds/open.wav");
				        ex.printStackTrace();
				    }
					// If app is hanging, loading gif will play
					btnStart.setVisible(false);
					lblLoadingGif.setVisible(true);
					
					int x = btnDiff.getSelectedIndex();
					int limit = (x+1) * 30;
//					System.out.println(limit);
					
					int y = btnLives.getSelectedIndex();
					int lives = (y*2)+1;
//					System.out.println(lives);
					
					// Move on to game, pass in appropriate variables
					if(!(checkPassive.isSelected() || checkDefault.isSelected() || checkUlti.isSelected()) )
						new guiGuess_v1_3(champions, limit, lives, true, true, true);
					else
						new guiGuess_v1_3(champions, limit, lives, checkPassive.isSelected(), checkDefault.isSelected(), checkUlti.isSelected());
					
					// Dispose of current frame
					frame.dispose();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}