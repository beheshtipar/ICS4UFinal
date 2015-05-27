package test;

/* "Guess That Champion!" isn't endorsed by Riot Games and doesn't reflect 
 * the views or opinions of Riot Games or anyone 
 * officially involved in producing or managing League of Legends. 
 * League of Legends and Riot Games are trademarks or 
 * registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.core.staticdata.ChampionSpell;

public class sampleImage {
	
	static List<Champion> champions;
	static Image i;
	static String fileName;
	
	protected static void init() throws IOException{
		
		JFrame frame = new JFrame("Guess That Champion!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		frame.setLayout(gridbag);
		
		// Draw stuff
		
		BufferedImage champPic = ImageIO.read(new File(fileName));
		JLabel champLabel = new JLabel(new ImageIcon(champPic));
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		c.gridheight = 3; 
		c.weightx = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;														//setting for title
		c.ipady = 20;
		gridbag.setConstraints(champLabel, c);
		frame.getContentPane().add(champLabel);
		
		frame.setVisible(true);
	}
	
	public static void getChamp() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader("lib\\api-key.txt")); 
    	String text = in.readLine(); 
    	in.close();
    	
        RiotAPI.setMirror(Region.NA);
        RiotAPI.setRegion(Region.NA);
        RiotAPI.setAPIKey(text);
        
        champions = RiotAPI.getChampions();
        Champion champ = champions.get(0);
        String champName = champ.getName();
        fileName = "icons/"+champName+".png";
//        System.out.println(fileName);
        i = Toolkit.getDefaultToolkit().getImage(fileName);
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