/* 							GUESS THAT CHAMPION
 * 
 * ----------------------------------------------------------------------------
 * "Guess That Champion!" isn't endorsed by Riot Games and doesn't reflect 
 * the views or opinions of Riot Games or anyone 
 * officially involved in producing or managing League of Legends. 
 * League of Legends and Riot Games are trademarks or 
 * registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.
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
 * - Program now tells user what their score was after each round ends
 * 
 * PLANNED FEATURES
 * - Select which *champion* categories you'll be tested on (only Marksmen, only Fighters, etc.)
 * - System to keep track of high scores
 * 
 * CODE ADJUSTMENTS
 * - Injected Parsa's GUI code into init and reset methods
 * - Updated method names
 * - Moved loading time to initial start of mainFrame program
 *   
 * KNOWN BUGS
 * - Takes extremely long time to contact RiotAPI for full champion list (on slow connection)
 * 
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.robrua.orianna.type.core.staticdata.Champion;

public class guiGuess_v1_2 {
	
	// Instance variables
	
	// JFrame variables
	static JLayeredPane layeredPane;
	static JPanel mainPane;
	static JPanel timerBG;
	static JFrame frame;
	static GridBagLayout gridbag;
	static GridBagConstraints c;
	static JLabel lblBG;
	
	// Champion variables
	static ArrayList<Integer> used = new ArrayList<Integer>();
	static ArrayList<Integer> usedFills = new ArrayList<Integer>();
	static List<Champion> champions;
	static Champion champ;
	static Font text;
	static BufferedImage champAbi;
	static BufferedImage champPics[] = new BufferedImage[4];
	static JButton champButts[] = new JButton[4];
	
	// Hint variables
	static JLabel champAbility;
	static boolean passive;
	static boolean regular;
	static boolean ultimate;
	
	// Counters/temporary variables
	static String pass;
	static int answer;
	static int i;
	
	// Keep track of score
	static JLabel scoreLabel;
	static JLabel pointsLabel;
	static int score = 0;
	static int total = 0;
	static int points = 0;
	
	// Keep track of lives
	static JLabel lblLife1;
	static JLabel lblLife2;
	static JLabel lblLife3;
	static int lives = 3;
	
	// Keep track of time
	static StopWatch watch = new StopWatch();
	static Timer timer;
	static JLabel timeLabel = new JLabel();
	static long gameStart;
	static long roundStart;
	static long roundEnd;
	static long roundTime;
	static final int cap = 60;
	static long time = cap;
	
	/* Default: Passives			[X]
	 * 			Regular abilities	[X]
	 * 			Ultimate ability	[X]
	 */
	public guiGuess_v1_2(List<Champion> champs) throws IOException{
		passive = true;
		regular = true;
		ultimate = true;
		champions = champs;
		play();
	}
	
	/*
	 * Use parameters to select which types of icons to display
	 */
	public guiGuess_v1_2(List<Champion> champs, boolean doPassives, boolean doRegulars, boolean doUltimates) throws IOException{
		passive = doPassives;
		regular = doRegulars;
		ultimate = doUltimates;
		champions = champs;
		play();
	}
	
	/*
	 * First set of icons
	 */
	protected static void play() throws IOException{
		
		// Create JFrame
		frame = new JFrame("Guess That Champion!");
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 600);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initialize layered frame system; crucial to getting background image to work
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 800, 578);
		frame.getContentPane().add(layeredPane);
		
		// Background image setup/assignment
		BufferedImage BG = ImageIO.read(new File("lib//akaliBG.jpg"));
		lblBG = new JLabel(new ImageIcon(BG));
		layeredPane.setLayer(lblBG, 0);
		lblBG.setBounds(0, 0, 800, 578);
		layeredPane.add(lblBG);
		
		// Add pane for buttons/content
		mainPane = new JPanel();
		mainPane.setForeground(new Color(255, 255, 255));
		layeredPane.setLayer(mainPane, 2);
		mainPane.setBounds(0, 0, 800, 578);
		layeredPane.add(mainPane);
		mainPane.setBackground(null);
		mainPane.setOpaque(false);
		mainPane.setLayout(null);
		
		// Choose title of application
		scoreLabel = new JLabel("Score: " + score + " / " + total);
		pointsLabel = new JLabel("Points: " + points);
		
		// Select champion, choose hint to be displayed
		champ = newChamp();
		getAbilityType();
    	
    	// Load and display image to be displayed as hint
		try{
			champAbi = ImageIO.read(new File("lib/images/abilities/" + champ.getName() + "_" + pass + ".png"));
		}catch(IOException e){
			System.out.println("lib/images/abilities/" + champ.getName() + "_" + pass + ".png");
		}
		champAbility = new JLabel(new ImageIcon(champAbi));
    	
		// Load and display correct champion image, and 3 other champions
    	answer = (int) (4 * Math.random());
    	for(int i = 0; i < champPics.length; i++){
    		if(i==answer)
    			champPics[i] = ImageIO.read(new File("lib/images/champs/" + champ.getName() + ".png"));
    		else
    			champPics[i] = ImageIO.read(new File(newChampFill()));
    		champButts[i] = new JButton(new ImageIcon(champPics[i]));
    	}

    	// Initialize buttons, score label and timers.
    	champAbility.setBounds(368, 102, 64, 64);
    	champButts[0].setBounds(270, 190, 120, 120);
    	champButts[1].setBounds(408, 190, 120, 120);
    	champButts[2].setBounds(270, 320, 120, 120);
    	champButts[3].setBounds(408, 320, 120, 120);

    	// Initialize score label
    	pointsLabel.setFont(new Font("Bangla MN", Font.BOLD, 13));
    	pointsLabel.setForeground(new Color(255, 255, 255));
    	pointsLabel.setBounds(351, 470, 113, 25);

    	// Initialize timers
    	timeLabel.setText(Long.toString(time));
    	timeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
    	timeLabel.setForeground(new Color(255, 255, 255));
    	timeLabel.setBounds(384, 512, 32, 37);

    	// Initialize life bars
    	BufferedImage life = ImageIO.read(new File("lib//images//lives.png"));
    	lblLife1 = new JLabel(new ImageIcon(life));
    	lblLife1.setBounds(650, 200, 64, 64);
    	lblLife2 = new JLabel(new ImageIcon(life));
    	lblLife2.setBounds(650, 274, 64, 64);
    	lblLife3 = new JLabel(new ImageIcon(life));
    	lblLife3.setBounds(650, 348, 64, 64);
    	
    	// Add elements to pane
    	
    	mainPane.add(champAbility);
		mainPane.add(champButts[0]);
		mainPane.add(champButts[1]);
		mainPane.add(champButts[2]);
		mainPane.add(champButts[3]);
		
		mainPane.add(pointsLabel);
		mainPane.add(timeLabel);
		
		mainPane.add(lblLife1);
		mainPane.add(lblLife2);
		mainPane.add(lblLife3);
		
		// Background for timer/score tracker
		timerBG = new JPanel();
		timerBG.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		timerBG.setBackground(new Color(0, 0, 0));
		layeredPane.setLayer(timerBG, 1);
		timerBG.setBounds(300, 460, 200, 100);
		layeredPane.add(timerBG);
		
		// Add listeners to window (WIP)
		
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.out.println((char)e.getKeyCode());
                if((char)e.getKeyCode()=='d'){		// Option 0
                	try {
    					handleScore(0);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='e'){	// Option 1
                	try {
    					handleScore(1);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='a'){	// Option 2
                	try {
    					handleScore(2);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='b'){	// Option 3
                	try {
    					handleScore(3);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }
            }
        });
		mainPane.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.out.println((char)e.getKeyCode());
                if((char)e.getKeyCode()=='d'){		// Option 0
                	try {
    					handleScore(0);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='e'){	// Option 1
                	try {
    					handleScore(1);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='a'){	// Option 2
                	try {
    					handleScore(2);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='b'){	// Option 3
                	try {
    					handleScore(3);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }
            }
        });
		layeredPane.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.out.println((char)e.getKeyCode());
                if((char)e.getKeyCode()=='d'){		// Option 0
                	try {
    					handleScore(0);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='e'){	// Option 1
                	try {
    					handleScore(1);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='a'){	// Option 2
                	try {
    					handleScore(2);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='b'){	// Option 3
                	try {
    					handleScore(3);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }
            }
        });
		timerBG.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.out.println((char)e.getKeyCode());
                if((char)e.getKeyCode()=='d'){		// Option 0
                	try {
    					handleScore(0);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='e'){	// Option 1
                	try {
    					handleScore(1);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='a'){	// Option 2
                	try {
    					handleScore(2);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }else if((char)e.getKeyCode()=='b'){	// Option 3
                	try {
    					handleScore(3);
    					roundEnd = watch.getElapsedTimeSecs();
    					roundTime = roundEnd-roundStart;
    					roundStart = roundEnd;
    					System.out.println("R: " + roundTime);
    					if((total < champions.size() - 3) && (gameStart < 60))
    						nextRound();
    				} catch (IOException x) {
    					// TODO Auto-generated catch block
    					x.printStackTrace();
    				}
                }
            }
        });
		
		// Add listeners to buttons
		
		champButts[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				try {
					handleScore(0);
					roundEnd = watch.getElapsedTimeSecs();
					roundTime = roundEnd-roundStart;
					roundStart = roundEnd;
//					System.out.println("R: " + roundTime);
					if((total < champions.size() - 3) && (gameStart < 60))
						nextRound();
					else{
						frame.dispose();
						new mainFrame_v3(0);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				try {
					handleScore(1);
					roundEnd = watch.getElapsedTimeSecs();
					roundTime = roundEnd-roundStart;
					roundStart = roundEnd;
//					System.out.println("R: " + roundTime);
					if((total < champions.size() - 3) && (gameStart < 60))
						nextRound();
					else{
						frame.dispose();
						new mainFrame_v3(0);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[2].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				try {
					handleScore(2);
					roundEnd = watch.getElapsedTimeSecs();
					roundTime = roundEnd-roundStart;
					roundStart = roundEnd;
//					System.out.println("R: " + roundTime);
					if((total < champions.size() - 3) && (gameStart < 60))
						nextRound();
					else{
						frame.dispose();
						new mainFrame_v3(0);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[3].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				try {
					handleScore(3);
					roundEnd = watch.getElapsedTimeSecs();
					roundTime = roundEnd-roundStart;
					roundStart = roundEnd;
//					System.out.println("R: " + roundTime);
					if((total < champions.size() - 3) && (gameStart < 60))
						nextRound();
					else{
						frame.dispose();
						new mainFrame_v3(0);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Create game timer
		int delay = 1000; //milliseconds
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				gameStart = watch.getElapsedTimeSecs();
				if(gameStart < cap)
					timeLabel.setText(Long.toString(60-gameStart));
				else timeLabel.setText("0");
				timeLabel.setVisible(true);
			}
		};
		
		// Start timers
		new Timer(delay, taskPerformer).start();
		
		watch.start();
		gameStart = watch.getElapsedTimeSecs();
		roundStart = watch.getElapsedTimeSecs();
		
		// Refresh frame with new elements
		frame.setVisible(true);
	}
	
	/*
	 * Second set of icons (and onwards)
	 */
	public static void nextRound() throws IOException{
		
		// Remove elements from pane
		
		mainPane.remove(champAbility);
		mainPane.remove(champButts[0]);
		mainPane.remove(champButts[1]);
		mainPane.remove(champButts[2]);
		mainPane.remove(champButts[3]);

		mainPane.remove(pointsLabel);
		mainPane.remove(timeLabel);

		mainPane.remove(lblLife1);
		mainPane.remove(lblLife2);
		mainPane.remove(lblLife3);
		
		layeredPane.remove(timerBG);
		
		// Select new champion, choose hint to be displayed
		champ = newChamp();
		answer = (int) (4 * Math.random());
		getAbilityType();
    	
    	// Load and display hint image
    	try{
    	champAbi = ImageIO.read(new File("lib/images/abilities/" + champ.getName() + "_" + pass + ".png"));
    	}catch (IOException e){System.out.println("Can't read: lib/images/abilities/" + champ.getName() + "_" + pass + ".png");}
    	frame.getContentPane().remove(champAbility);
    	champAbility = new JLabel(new ImageIcon(champAbi));
    	
    	// Load and display correct champion image, and 3 other champion images
		for(int i = 0; i < champPics.length; i++){
			if(i==answer){
				try{
				champPics[i] = ImageIO.read(new File("lib/images/champs/" + champ.getName() + ".png"));
				}catch(IOException e) {System.out.println("lib/images/champs/" + champ.getName() + ".png");}
			}else{
				try{
				champPics[i] = ImageIO.read(new File(newChampFill()));
				}catch(IOException e){ System.out.println();}
			}
		}
    	
    	for(int i = 0; i < champButts.length; i++){
    		frame.getContentPane().remove(champButts[i]);
			champButts[i] = new JButton(new ImageIcon(champPics[i]));
			champButts[i].setVisible(true);
    	}
    	
    	// Display score and time
    	scoreLabel = new JLabel("Score: " + score + " / " + total);
    	pointsLabel = new JLabel("Points: " + points);
    	scoreLabel.setVisible(true);
    	timeLabel.setVisible(true);
    	pointsLabel.setVisible(true);
    	
    	// Initialize buttons, score label and timers.
    	champAbility.setBounds(368, 102, 64, 64);
    	champButts[0].setBounds(270, 190, 120, 120);
    	champButts[1].setBounds(408, 190, 120, 120);
    	champButts[2].setBounds(270, 320, 120, 120);
    	champButts[3].setBounds(408, 320, 120, 120);

    	// Initialize score label
    	pointsLabel.setFont(new Font("Bangla MN", Font.BOLD, 13));
    	pointsLabel.setForeground(new Color(255, 255, 255));
    	pointsLabel.setBounds(351, 470, 113, 25);

    	// Initialize timers
    	timeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
    	timeLabel.setForeground(new Color(255, 255, 255));
    	timeLabel.setBounds(384, 512, 32, 37);
    	
    	// Add elements to pane

    	mainPane.add(champAbility);
    	mainPane.add(champButts[0]);
    	mainPane.add(champButts[1]);
    	mainPane.add(champButts[2]);
    	mainPane.add(champButts[3]);

    	mainPane.add(pointsLabel);
    	mainPane.add(timeLabel);
    	
    	layeredPane.add(timerBG);
    	
    	// Manage lives count
    	if(lives>2)
    		mainPane.add(lblLife1);
    	if(lives>1)
    		mainPane.add(lblLife2);
    	if(lives>0)
    		mainPane.add(lblLife3);
		
		// Add listeners to buttons
		
		champButts[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				try {
					if((total < champions.size() - 3) && (gameStart < 60) && (lives > 0)){
						handleScore(0);
						roundEnd = watch.getElapsedTimeSecs();
						roundTime = roundEnd-roundStart;
						roundStart = roundEnd;
//						System.out.println("R: " + roundTime);
						nextRound();
					}else{
						frame.setVisible(false);
						newGame();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				try {
					if((total < champions.size() - 3) && (gameStart < 60) && (lives > 0)){
						handleScore(1);
						roundEnd = watch.getElapsedTimeSecs();
						roundTime = roundEnd-roundStart;
						roundStart = roundEnd;
//						System.out.println("R: " + roundTime);
						nextRound();
					}else{
						frame.setVisible(false);
						newGame();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[2].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				try {
					if((total < champions.size() - 3) && (gameStart < 60) && (lives > 0)){
						handleScore(2);
						roundEnd = watch.getElapsedTimeSecs();
						roundTime = roundEnd-roundStart;
						roundStart = roundEnd;
//						System.out.println("R: " + roundTime);
						nextRound();
					}else{
						frame.setVisible(false);
						newGame();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[3].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				try {
					if((total < champions.size() - 3) && (gameStart < 60) && (lives > 0)){
						handleScore(3);
						roundEnd = watch.getElapsedTimeSecs();
						roundTime = roundEnd-roundStart;
						roundStart = roundEnd;
//						System.out.println("R: " + roundTime);
						nextRound();
					}else{
						frame.setVisible(false);
						newGame();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Refresh frame
		mainPane.setVisible(false);
		layeredPane.setVisible(false);
		mainPane.setVisible(true);
		layeredPane.setVisible(true);
	}
	
	/*
	 * Starts a fresh new game
	 */
	public static void newGame(){
		frame.setVisible(false);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Reset stats
					lives = 3;
					mainFrame_v3 window = new mainFrame_v3(points);
					points = 0;
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*
	 * Find new champ, mark as used
	 */
	public static Champion newChamp(){
		
		// Pull random champion from list
		int index = (int)(champions.size() * Math.random());
		
		// Make sure champion hasn't already been used as an answer
		while(used.contains(index))
			index = (int)(champions.size() * Math.random());
		
		// Save that champion
        Champion c = champions.get(index);
       
        // Add champion to used array
        used.add(index);
        
        return c;
	}
	
	/*
	 * Find new champ to fill in empty slot, don't mark as used yet
	 */
	public static String newChampFill(){
		
		// Pull random champion from list
		int index = (int)(champions.size() * Math.random());
		
		// Make sure champion hasn't already been used as an answer
		while(used.contains(index)||usedFills.contains(index))
			index = (int)(champions.size() * Math.random());
		
		// Save that champion
		Champion c = champions.get(index);
        
		usedFills.add(index);
		
        //Return the appropriate file name
        return "lib/images/champs/" + c.getName() + ".png";
	}
	
	/*
	 * Generate string for an ability type to display
	 */
	public static void getAbilityType(){
		String returnThis = "";
		
		if(passive){
			if(regular){
				if(ultimate){ // All enabled
					int rn = (int) (5 * Math.random());
					if(rn==0) returnThis = "Q";
					else if(rn==1) returnThis = "W";
					else if(rn==2) returnThis = "E";
					else if(rn==3) returnThis = "R";
					else returnThis = "Passive";
				}else{ // No ultimates
					int rn = (int) (4 * Math.random());
					if(rn==0) returnThis = "Q";
					else if(rn==1) returnThis = "W";
					else if(rn==2) returnThis = "E";
					else returnThis = "Passive";
				}
			}else{
				if(ultimate){ // No regular abilities
					int rn = (int) (3 * Math.random());
			    	if(rn==0) returnThis = "Q";
			    	else if(rn==1) returnThis = "R";
			    	else returnThis = "Passive";
				}else{ // Only passives
					returnThis = "Passive";
				}
			}
		}else{
			if(regular){
				if(ultimate){ // No passive
					int rn = (int) (4 * Math.random());
			    	if(rn==0) returnThis = "Q";
			    	else if(rn==1) returnThis = "W";
			    	else if(rn==2) returnThis = "E";
			    	else returnThis = "R";
				}
				else{ // Only regular abilities
					int rn = (int) (3 * Math.random());
			    	if(rn==0) returnThis = "Q";
			    	else if(rn==1) returnThis = "W";
			    	else returnThis = "E";
				}
			}else{
				if(ultimate){ // Only ultimates
					returnThis = "R";
				}
			}
		}
		pass = returnThis;
	}
	
	/*
	 * Play a sound
	 */
	public static void playSound(String soundFile){
		try {
			// Create AudioStream from sound file
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile).getAbsoluteFile());
	        // Create clip from AudioStream and play clip
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Sound error on playing file: " + soundFile);
	        ex.printStackTrace();
	    }
	}
	
	/*
	 * Increment score when needed, and total guesses always
	 */
	public static void handleScore(int spot) throws IOException{
		
		if((total < champions.size() - 3) && (gameStart < 60)){
			
			int inc = (int) (400 / Math.pow(2, roundTime));
			int dec = (int) (300 / Math.pow(1.5, roundTime));
			
			// Play appropriate sound, change score
			if(answer==spot){
				playSound("lib/sounds/correct.wav");
				score++;
				points += inc;
			}else{
				playSound("lib/sounds/incorrect.wav");
				lives--;
				points -= dec;
			}

			total++;
//			System.out.println("P: " + points);
		}
		
		// Refresh score / points
		scoreLabel.setVisible(true);
		pointsLabel.setVisible(true);
	}
	
}