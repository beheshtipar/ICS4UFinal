/* "Guess That Champion!" isn't endorsed by Riot Games and doesn't reflect 
 * the views or opinions of Riot Games or anyone 
 * officially involved in producing or managing League of Legends. 
 * League of Legends and Riot Games are trademarks or 
 * registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.
 */

/*
 * Swapped over to using Object arrays to store JButtons and JLabels
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

public class guiGuess_v01 {
	
	static ArrayList<Integer> used = new ArrayList<Integer>();
	static List<Champion> champions;
	
	static JFrame frame;
	static GridBagLayout gridbag;
	static GridBagConstraints c;
	
	static Champion champ;
	static List<ChampionSpell> spells;
	static JLabel champPassive;
	static JLabel champAbility;
	
	static String pass;
	static int answer;
	static int i;

	static BufferedImage champPics[] = new BufferedImage[4];
	static JButton champButts[] = new JButton[4];
	
	static int score = 0;
	static int total = 0;
	
	protected static void init() throws IOException{
		
		frame = new JFrame("Guess That Champion!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		
		gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		
		frame.setLayout(gridbag);
		
		champ = newChamp();
		spells = champ.getSpells();
		int rn = (int) (3 * Math.random());
    	answer = (int) (4 * Math.random());
		
    	if(rn==0) pass = "Q";
    	else if(rn==1) pass = "W";
    	else if(rn==2) pass = "E";
    	
		// Draw stuff
    	
    	for(int i = 0; i < champPics.length; i++){
    		if(i==answer)
    			champPics[i] = ImageIO.read(new File("champs/" + champ.getName() + ".png"));
    		else
    			champPics[i] = ImageIO.read(new File(newChampFill()));
    	}
    	
		Font titleFont = new Font("Helvetica", Font.BOLD, 25);
		Font text = new Font("Arial", Font.PLAIN, 13);
		
		JLabel title = new JLabel("Guess That Champion!");
		champPassive = new JLabel("Passive: " + champ.getPassive());
		champAbility = new JLabel(pass + ": " + spells.get(rn));
		
		title.setFont(titleFont);
		champPassive.setFont(text);
		champAbility.setFont(text);
		
		for(int i = 0; i < champButts.length; i++){
			champButts[i] = new JButton(new ImageIcon(champPics[i]));
		}
		
		c.anchor = GridBagConstraints.NORTH;
		c.ipady = 10;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(title, c);
		frame.getContentPane().add(title);
		c.weightx = 0.0;
		gridbag.setConstraints(champPassive, c);
		frame.getContentPane().add(champPassive);
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
		
		for(i = 0; i < champButts.length-1; i++){
			champButts[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					try {
						handleScore(i);
						reset();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		
		frame.setVisible(true);
	}
	
	public static void handleScore(int spot){
		if(answer==spot){
			score++;
		}
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
	
	public static void reset() throws IOException{
		champ = newChamp();
		spells = champ.getSpells();
		pass = "";
		int rn = (int) (3 * Math.random());
    	answer = (int) (4 * Math.random());
		
    	if(rn==0) pass = "Q";
    	else if(rn==1) pass = "W";
    	else if(rn==2) pass = "E";
    	
    	champPassive.setText("Passive: " + champ.getPassive());
		champAbility.setText(pass + ": " + spells.get(rn));
    	
		// Draw stuff
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
		
		for(i = 0; i < champButts.length; i++){
			champButts[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					try {
						handleScore(i);
						reset();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		
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