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
 * - Select which categories to be tested on
 * - Displays score in GUI
 * 
 * NEW FEATURES
 * - Plays sound to let user know if their guess was correct
 * - Menu screen (WIP)
 *   
 * PLANNED FEATURES
 * - Select which *champion* categories you'll be tested on (only Marksmen, only Fighters, etc.)
 * - Skip button
 * - Remove 2 options button (limited uses)
 *   
 * CODE ADJUSTMENTS
 * - Created new method for playing sound files
 *   
 * KNOWN BUGS
 * - Sometimes requires double-clicking or triple-clicking icons
 * - Screen sometimes doesn't refresh
 */

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;

public class guiGuess_v05 {
	
	// Instance variables
	
	// JFrame stuff
	static JFrame frame;
	static GridBagLayout gridbag;
	static GridBagConstraints c;
	
	// Champion stuff
	static ArrayList<Integer> used = new ArrayList<Integer>();
	static List<Champion> champions;
	static Champion champ;
//	static List<ChampionSpell> spells;
	static Font text;
	static BufferedImage champAbi;
	static BufferedImage champPics[] = new BufferedImage[4];
	static JButton champButts[] = new JButton[4];
	
	// Hint stuff
	static JLabel champAbility;
	static JLabel display;
	static boolean passive;
	static boolean regular;
	static boolean ultimate;
	
	// Counters/temporary variables
	static String pass;
	static int answer;
	static int i;
	
	// Keep track of score
	static int score = 0;
	static int total = 0;
	
	/* Default: Passives			[X]
	 * 			Regular abilities	[X]
	 * 			Ultimate ability	[X]
	 */
	public guiGuess_v05() throws IOException{
		passive = true;
		regular = true;
		ultimate = true;
		getChamp();
		init();
	}
	
	/*
	 * 
	 */
	public guiGuess_v05(boolean doPassives, boolean doRegulars, boolean doUltimates) throws IOException{
		passive = doPassives;
		regular = doRegulars;
		ultimate = doUltimates;
		getChamp();
		init();
	}
	
	/*
	 * 
	 */
	protected static void init() throws IOException{
		
		// Create JFrame
		frame = new JFrame("Guess That Champion!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		
		// Choose GUI Layout
		gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		frame.setLayout(gridbag);
		
		// Choose title of application
		JLabel title = new JLabel("Guess That Champion!");
		display = new JLabel("Score: " + score + " / " + total);
		Font titleFont = new Font("Helvetica", Font.BOLD, 25);
		text = new Font("Arial", Font.PLAIN, 13);
		title.setFont(titleFont);
		display.setFont(text);
		
		// Select champion, choose hint to be displayed
		champ = newChamp();
		getAbi();
    	
    	// Load and display image to be displayed as hint
		try{
			champAbi = ImageIO.read(new File("abilities/" + champ.getName() + "_" + pass + ".png"));
		}catch(IOException e){
			System.out.println("abilities/" + champ.getName() + "_" + pass + ".png");
		}
		champAbility = new JLabel(new ImageIcon(champAbi));
    	
		// Load and display correct champion image, and 3 other champions
    	answer = (int) (4 * Math.random());
    	for(int i = 0; i < champPics.length; i++){
    		if(i==answer)
    			champPics[i] = ImageIO.read(new File("champs/" + champ.getName() + ".png"));
    		else
    			champPics[i] = ImageIO.read(new File(newChampFill()));
    		champButts[i] = new JButton(new ImageIcon(champPics[i]));
    	}
		
		// Add elements to screen
		
		c.anchor = GridBagConstraints.NORTH;
		c.ipady = 10;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(title, c);
		frame.getContentPane().add(title);
		c.weightx = 0.0;
		gridbag.setConstraints(champAbility, c);
		frame.getContentPane().add(champAbility);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(champButts[0], c);
		frame.getContentPane().add(champButts[0]);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(champButts[1], c);
		frame.getContentPane().add(champButts[1]);
		
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(champButts[2], c);
		frame.getContentPane().add(champButts[2]);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(champButts[3], c);
		frame.getContentPane().add(champButts[3]);
		
		c.anchor = GridBagConstraints.EAST;
		gridbag.setConstraints(display, c);
		frame.getContentPane().add(display);
		
		// Add listeners to buttons
		
		champButts[0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				try {
					handleScore(0);
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				try {
					handleScore(1);
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				try {
					handleScore(2);
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				try {
					handleScore(3);
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Refresh frame with new elements
		
		frame.setVisible(true);
	}
	
	/*
	 * 
	 */
	public static void reset() throws IOException{
		
		// Select new champion, choose hint to be displayed
		
		champ = newChamp();
//		spells = champ.getSpells();
		answer = (int) (4 * Math.random());
		getAbi();
    	
    	// Load and display hint image
    	
    	champAbi = ImageIO.read(new File("abilities/" + champ.getName() + "_" + pass + ".png"));
    	frame.getContentPane().remove(champAbility);
    	champAbility = new JLabel(new ImageIcon(champAbi));
    	
    	// Load and display correct champion image, and 3 other champion images
    	
		for(int i = 0; i < champPics.length; i++){
			if(i==answer){
				try{
				champPics[i] = ImageIO.read(new File("champs/" + champ.getName() + ".png"));
				}catch(IOException e) {System.out.println("champs/" + champ.getName() + ".png");}
			}else{
				try{
				champPics[i] = ImageIO.read(new File(newChampFill()));
				}catch(IOException e){ System.out.println();}
			}
		}
    	
    	for(int i = 0; i < champButts.length; i++){
    		frame.getContentPane().remove(champButts[i]);
			champButts[i] = new JButton(new ImageIcon(champPics[i]));
    	}
    	
    	frame.getContentPane().remove(display);
    	display = new JLabel("Score: " + score + " / " + total);
    	display.setFont(text);
    	
    	// Add elements to screen
    	
    	c.weightx = 0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(champAbility, c);
		frame.getContentPane().add(champAbility);
		
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(champButts[0], c);
		frame.getContentPane().add(champButts[0]);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(champButts[1], c);
		frame.getContentPane().add(champButts[1]);
		
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(champButts[2], c);
		frame.getContentPane().add(champButts[2]);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(champButts[3], c);
		frame.getContentPane().add(champButts[3]);
		
		c.anchor = GridBagConstraints.EAST;
		gridbag.setConstraints(display, c);
		frame.getContentPane().add(display);
		
		// Add listeners to buttons
		
		champButts[0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				try {
					handleScore(0);
					if(total < champions.size() - 3)
						reset();
					else frame.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				try {
					handleScore(1);
					if(total < champions.size() - 3)
						reset();
					else frame.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				try {
					handleScore(2);
					if(total < champions.size() - 3)
						reset();
					else frame.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				try {
					handleScore(3);
					if(total < champions.size() - 3)
						reset();
					else frame.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Refresh frame
		
		frame.setVisible(true);
	}
	
	/*
	 * 
	 */
	public static void getChamp() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader("lib\\api-key.txt")); 
    	String text = in.readLine(); 
    	in.close();
    	
        RiotAPI.setMirror(Region.NA);
        RiotAPI.setRegion(Region.NA);
        RiotAPI.setAPIKey(text);
        
        champions = RiotAPI.getChampions();
	}
	
	/*
	 * 
	 */
	public static Champion newChamp(){
		int index = (int)(champions.size() * Math.random());
		while(used.contains(index))
			index = (int)(champions.size() * Math.random());
        Champion c = champions.get(index);
        used.add(index);
        return c;
	}
	
	/*
	 * 
	 */
	public static String newChampFill(){
		int index = (int)(champions.size() * Math.random());
		while(used.contains(index))
			index = (int)(champions.size() * Math.random());
        Champion c = champions.get(index);
        return "champs/" + c.getName() + ".png";
	}
	
	/*
	 * 
	 */
	public static void getAbi(){
		String returnThis = "";
		
		if(passive){
			if(regular){
				int rn = (int) (5 * Math.random());
				if(rn==0) returnThis = "Q";
				else if(rn==1) returnThis = "W";
				else if(rn==2) returnThis = "E";
				else if(rn==3) returnThis = "R";
				else returnThis = "Passive";
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
					returnThis = "Passive";
				}
			}
		}
		pass = returnThis;
	}
	
	/*
	 * 
	 */
	public static void playSound(String soundFile){
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Sound error on playing file: " + soundFile);
	        ex.printStackTrace();
	    }
	}
	
	/*
	 * 
	 */
	public static void handleScore(int spot) throws IOException{
		if(answer==spot){
			playSound("sounds/correct.wav");
			score++;
		}else playSound("sounds/incorrect.wav");
		total++;
//		System.out.println("Score: "+score + " / " + total);
	}
	
}