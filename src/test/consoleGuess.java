package test;

/* "Guess That Champion!" isn't endorsed by Riot Games and doesn't reflect 
 * the views or opinions of Riot Games or anyone 
 * officially involved in producing or managing League of Legends. 
 * League of Legends and Riot Games are trademarks or 
 * registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.core.staticdata.ChampionSpell;

public class consoleGuess {
	
	// Global variables
	static List<Champion> champions;
	static ArrayList<Integer> used = new ArrayList<Integer>();
	static Scanner sc = new Scanner(System.in);
	
	/*********************************************************/
	
	/*
	 * Main Method
	 */
	public static void main(String[] args) throws IOException {
    	BufferedReader in = new BufferedReader(new FileReader("lib\\api-key.txt")); 
    	String text = in.readLine(); 
    	in.close();
    	
        RiotAPI.setMirror(Region.NA);
        RiotAPI.setRegion(Region.NA);
        RiotAPI.setAPIKey(text);
        RiotAPI.setRateLimit(10, 10);
        
        champions = RiotAPI.getChampions();
        
        boolean gameOver = false;
        
        while(used.size() < champions.size() && !gameOver){
        	clear();
        	guess();
        	gameOver = stopPrompt();
        }
        
        sc.close();
    }
	
	/*
	 * Select a(nother) random champion out of the remaining pool
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
	 * Take user's guess, respond with correct answer if guess is wrong
	 */
	public static void guess(){
		Champion c = newChamp();

    	List<ChampionSpell> spells = c.getSpells();
    	
    	System.out.println("Passive: " + c.getPassive());
    	
    	int rn = (int) (3 * Math.random());
    	if(rn==0) System.out.print("Q");
    	else if(rn==1) System.out.print("W");
    	else if(rn==2) System.out.print("E");
    	System.out.println(": " + spells.get(rn));

    	System.out.print("Who's that champion? ");
    	String guess = sc.nextLine();
    	if(guess.toLowerCase().equals(c.getName().toLowerCase())) System.out.println("Correct!");
    	else if(guess.equals("")) System.out.println("It's " + c.getName() + "!");
    	else System.out.println("Nope, it's " + c.getName() + "!");
	}
	
	/*
	 * Prompt the user asking if they would like to stop yet
	 */
	public static boolean stopPrompt(){
		System.out.print("Stop? ");
    	String ans = sc.nextLine();
    	if(ans.contains("y")||ans.contains("Y")){
    		System.out.println("Goodbye!");
			return true;
		}
		return false;
	}
	
	/*
	 * Clear the screen
	 */
	public static void clear(){
		for(int i = 0; i < 20; i++) System.out.println();
	}
}