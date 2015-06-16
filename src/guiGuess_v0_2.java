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
 * - Fully-functional gameplay w/champion passive and champion ability as hint
 * 
 * NEW FEATURES
 * - Instead of displaying the name of an ability and name of the passive,
 *   the hint now displays the icon of a champ's passive, or of one of their
 *   abilities
 *   
 * PLANNED FEATURES
 * - Menu
 * - Select which categories you'll be tested on (only marksmans, only E, etc.)
 * - Display score in GUI
 * - Skip button
 * - Remove 2 options button (limited uses)
 *   
 * CODE ADJUSTMENTS
 * - Swapped over to using Object arrays to store JButtons and JLabels
 * - After some testing, it turns out that you can't just make a for loop to
 *   add mmouseListeners to a group of Objects
 *   
 * KNOWN BUGS
 * - Screen requires minimizing, then reopening in order for next set of
 *   champions to appear
 */

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.core.staticdata.ChampionSpell;

public class guiGuess_v0_2 {
	
	static ArrayList<Integer> used = new ArrayList<Integer>();
	static List<Champion> champions;
	
	static JFrame frame;
	static GridBagLayout gridbag;
	static GridBagConstraints c;
	
	static Champion champ;
//	static List<ChampionSpell> spells;
	static JLabel champAbility;
	
	static String pass;
	static int answer;
	static int i;

	static BufferedImage champAbi;
	static BufferedImage champPics[] = new BufferedImage[4];
	static JButton champButts[] = new JButton[4];
	
	static int score = 0;
	static int total = 0;
	
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
		Font titleFont = new Font("Helvetica", Font.BOLD, 25);
//		Font text = new Font("Arial", Font.PLAIN, 13);
		title.setFont(titleFont);
		
		// Select champion, choose hint to be displayed
		
		champ = newChamp();
//		spells = champ.getSpells();
		int rn = (int) (5 * Math.random());
		
    	if(rn==0) pass = "Q";
    	else if(rn==1) pass = "W";
    	else if(rn==2) pass = "E";
    	else if(rn==3) pass = "R";
    	else pass = "Passive";
    	
    	// Load and display image to be displayed as hint
    	champAbi = ImageIO.read(new File("abilities/" + champ.getName() + "_" + pass + ".png"));
    	champAbility = new JLabel(new ImageIcon(champAbi));
    	
		// Load and display correct champion image, and 3 other champions
    	answer = (int) (4 * Math.random());
    	for(int i = 0; i < champPics.length; i++){
    		if(i==answer)
    			champPics[i] = ImageIO.read(new File("champs/" + champ.getName() + ".png"));
    		else
    			champPics[i] = ImageIO.read(new File(newChampFill()));
    	}
		for(int i = 0; i < champButts.length; i++){
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
		
		// Add listeners to buttons
		
		champButts[0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(0);
				try {
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(1);
				try {
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(2);
				try {
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(3);
				try {
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
	
	public static void reset() throws IOException{
		
		// Select new champion, choose hint to be displayed
			
		champ = newChamp();
//		spells = champ.getSpells();
		pass = "";
		int rn = (int) (5 * Math.random());
    	answer = (int) (4 * Math.random());
		
    	if(rn==0) pass = "Q";
    	else if(rn==1) pass = "W";
    	else if(rn==2) pass = "E";
    	else if(rn==3) pass = "R";
    	else pass = "Passive";
    	
    	// Load and display hint image
    	
    	champAbi = ImageIO.read(new File("abilities/" + champ.getName() + "_" + pass + ".png"));
    	frame.getContentPane().remove(champAbility);
    	champAbility = new JLabel(new ImageIcon(champAbi));
    	
    	// Load and display correct champion image, and 3 other champion images
		
    	for(int i = 0; i < champPics.length; i++){
			if(i==answer)
				champPics[i] = ImageIO.read(new File("champs/" + champ.getName() + ".png"));
			else
				champPics[i] = ImageIO.read(new File(newChampFill()));
		}
    	for(int i = 0; i < champButts.length; i++){
    		frame.getContentPane().remove(champButts[i]);
			champButts[i] = new JButton(new ImageIcon(champPics[i]));
    	}
    	
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
		
		// Add listeners to buttons
		
		champButts[0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(0);
				try {
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(1);
				try {
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(2);
				try {
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		champButts[3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(3);
				try {
					reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Refresh frame
		frame.setVisible(true);
	}
	
	public static void handleScore(int spot){
		if(answer==spot) score++;
		total++;
		System.out.println("Score: "+score + " / " + total);
	}
	
	public static void getChamp() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader("lib\\api-key.txt")); 
    	String text = in.readLine(); 
    	in.close();
    	
        RiotAPI.setMirror(Region.NA);
        RiotAPI.setRegion(Region.NA);
        RiotAPI.setAPIKey(text);
        
        champions = RiotAPI.getChampions();
	}
	
	public static Champion newChamp(){
		int index = (int)(champions.size() * Math.random());
		while(used.contains(index))
			index = (int)(champions.size() * Math.random());
        Champion c = champions.get(index);
        used.add(index);
        return c;
	}
	
	public static String newChampFill(){
		int index = (int)(champions.size() * Math.random());
		while(used.contains(index))
			index = (int)(champions.size() * Math.random());
        Champion c = champions.get(index);
        return "champs/" + c.getName() + ".png";
	}
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
        
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
					getChamp();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                try {
					init();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}

}