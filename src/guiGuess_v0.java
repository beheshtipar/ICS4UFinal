/* "Guess That Champion!" isn't endorsed by Riot Games and doesn't reflect 
 * the views or opinions of Riot Games or anyone 
 * officially involved in producing or managing League of Legends. 
 * League of Legends and Riot Games are trademarks or 
 * registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.
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
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.core.staticdata.ChampionSpell;

public class guiGuess_v0  {
	
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

	static BufferedImage r0Pic = null;
	static BufferedImage r1Pic = null;
	static BufferedImage r2Pic = null;
	static BufferedImage r3Pic = null;
	
	static JButton r0Label = null;
	static JButton r1Label = null;
	static JButton r2Label = null;
	static JButton r3Label = null;
	
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
		
    	if(answer==0){
    		r0Pic = ImageIO.read(new File("icons/" + champ.getName() + ".png"));
    		r1Pic = ImageIO.read(new File(newChampFill()));
    		r2Pic = ImageIO.read(new File(newChampFill()));
    		r3Pic = ImageIO.read(new File(newChampFill()));
    	}else if(answer==1){
    		r0Pic = ImageIO.read(new File(newChampFill()));
    		r1Pic = ImageIO.read(new File("icons/" + champ.getName() + ".png"));
    		r2Pic = ImageIO.read(new File(newChampFill()));
    		r3Pic = ImageIO.read(new File(newChampFill()));
    	}else if(answer==2){
    		r0Pic = ImageIO.read(new File(newChampFill()));
    		r1Pic = ImageIO.read(new File(newChampFill()));
    		r2Pic = ImageIO.read(new File("icons/" + champ.getName() + ".png"));
    		r3Pic = ImageIO.read(new File(newChampFill()));
    	}else{
    		r0Pic = ImageIO.read(new File(newChampFill()));
    		r1Pic = ImageIO.read(new File(newChampFill()));
    		r2Pic = ImageIO.read(new File(newChampFill()));
    		r3Pic = ImageIO.read(new File("icons/" + champ.getName() + ".png"));
    	}
    	
		Font titleFont = new Font("Helvetica", Font.BOLD, 25);
		Font text = new Font("Arial", Font.PLAIN, 13);
		
		JLabel title = new JLabel("Guess That Champion!");
		champPassive = new JLabel("Passive: " + champ.getPassive());
		champAbility = new JLabel(pass + ": " + spells.get(rn));
		
		title.setFont(titleFont);
		champPassive.setFont(text);
		champAbility.setFont(text);
		
		r0Label = new JButton(new ImageIcon(r0Pic));
		r1Label = new JButton(new ImageIcon(r1Pic));
		r2Label = new JButton(new ImageIcon(r2Pic));
		r3Label = new JButton(new ImageIcon(r3Pic));
		
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
		gridbag.setConstraints(r0Label, c);
		frame.getContentPane().add(r0Label);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(r1Label, c);
		frame.getContentPane().add(r1Label);
		
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(r2Label, c);
		frame.getContentPane().add(r2Label);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(r3Label, c);
		frame.getContentPane().add(r3Label);
		
		r0Label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(0);
				reset();
			}
		});
		r1Label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(1);
				reset();
			}
		});
		r2Label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(2);
				reset();
			}
		});
		r3Label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(3);
				reset();
			}
		});
		
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
	
	public static void reset(){
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
    	try{
    		if(answer==0){
    			r0Pic = ImageIO.read(new File("icons/" + champ.getName() + ".png"));
    			r1Pic = ImageIO.read(new File(newChampFill()));
    			r2Pic = ImageIO.read(new File(newChampFill()));
    			r3Pic = ImageIO.read(new File(newChampFill()));
    		}else if(answer==1){
    			r0Pic = ImageIO.read(new File(newChampFill()));
    			r1Pic = ImageIO.read(new File("icons/" + champ.getName() + ".png"));
    			r2Pic = ImageIO.read(new File(newChampFill()));
    			r3Pic = ImageIO.read(new File(newChampFill()));
    		}else if(answer==2){
    			r0Pic = ImageIO.read(new File(newChampFill()));
    			r1Pic = ImageIO.read(new File(newChampFill()));
    			r2Pic = ImageIO.read(new File("icons/" + champ.getName() + ".png"));
    			r3Pic = ImageIO.read(new File(newChampFill()));
    		}else{
    			r0Pic = ImageIO.read(new File(newChampFill()));
    			r1Pic = ImageIO.read(new File(newChampFill()));
    			r2Pic = ImageIO.read(new File(newChampFill()));
    			r3Pic = ImageIO.read(new File("icons/" + champ.getName() + ".png"));
    		}
    	}catch(IOException e){}
    	
    	frame.getContentPane().remove(r0Label);
    	frame.getContentPane().remove(r1Label);
    	frame.getContentPane().remove(r2Label);
    	frame.getContentPane().remove(r3Label);
    	
    	r0Label = new JButton(new ImageIcon(r0Pic));
		r1Label = new JButton(new ImageIcon(r1Pic));
		r2Label = new JButton(new ImageIcon(r2Pic));
		r3Label = new JButton(new ImageIcon(r3Pic));
    	
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(r0Label, c);
		frame.getContentPane().add(r0Label);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(r1Label, c);
		frame.getContentPane().add(r1Label);
		
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(r2Label, c);
		frame.getContentPane().add(r2Label);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(r3Label, c);
		frame.getContentPane().add(r3Label);
		
		r0Label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(0);
				reset();
			}
		});
		r1Label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(1);
				reset();
			}
		});
		r2Label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(2);
				reset();
			}
		});
		r3Label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				handleScore(3);
				reset();
			}
		});
		
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
        return "icons/" + c.getName() + ".png";
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