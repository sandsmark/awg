import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Stian Veum Møllersen
 * makur of awsoem AI
 *
 */
public class AIConfig {

	int attackForce = 12; //hvor mange units før attack
	int workers = 5; //hvor mange workers han opererer med
	double baseSenseRange = 400; //hvor nære basen før han forsvarer
	long aggro = 60000; //hvor ofte han sender wave i ms
	int fighterPerHealer = 5; //healer/fighter ratio
	int startGold = 400; //start gull
	long upgrade = 600000; // når han skal oppgradere hovedbygg i ms
	int healersInDefence = 2; //Hvor mange healers i forsvar
	int fightersInDefence = 10; //Hvor mange fighters i forsvar
	
	private AIConfig() {
		try {
			int attackForce, workers, fighterPerHealer, startGold, healersInDefence, fightersInDefence;
			long aggro, upgrade;
			double baseSenseRange;
			ArrayList<String> values = new ArrayList<String>();
			BufferedReader file = new BufferedReader(new FileReader("aiconfig.ini"));
			while(true) {
				String line = file.readLine();
				if(line==null) break;
				if(line.charAt(0)=='#') continue;
				values.add(line);
				if(values.size()>9) throw new Exception();
			}
			attackForce = Integer.parseInt(values.get(0));
			workers = Integer.parseInt(values.get(1));
			baseSenseRange = Double.parseDouble(values.get(2));
			aggro = Long.parseLong(values.get(3));
			fighterPerHealer = Integer.parseInt(values.get(4));
			startGold = Integer.parseInt(values.get(5));
			upgrade = Long.parseLong(values.get(6));
			healersInDefence = Integer.parseInt(values.get(7));
			fightersInDefence = Integer.parseInt(values.get(8));
			
			this.setAggro(aggro);
			this.setAttackForce(attackForce);
			this.setBaseSenseRange(baseSenseRange);
			this.setFighterPerHealer(fighterPerHealer);
			this.setFightersInDefence(fightersInDefence);
			this.setHealersInDefence(healersInDefence);
			this.setStartGold(startGold);
			this.setUpgrade(upgrade);
			this.setWorkers(workers);			
			
		} catch (Exception e) {
		//Not fatal, should use standard values
			System.err.println("Could not load config from file.");
		}
	}

	public void setAttackForce(int attackForce) {
		this.attackForce = attackForce;
	}

	public void setWorkers(int workers) {
		this.workers = workers;
	}

	public void setBaseSenseRange(double baseSenseRange) {
		this.baseSenseRange = baseSenseRange;
	}

	public void setAggro(long aggro) {
		this.aggro = aggro;
	}

	public void setFighterPerHealer(int fighterPerHealer) {
		this.fighterPerHealer = fighterPerHealer;
	}

	public void setStartGold(int startGold) {
		this.startGold = startGold;
	}

	public void setUpgrade(long upgrade) {
		this.upgrade = upgrade;
	}

	public void setHealersInDefence(int healersInDefence) {
		this.healersInDefence = healersInDefence;
	}

	public void setFightersInDefence(int fightersInDefence) {
		this.fightersInDefence = fightersInDefence;
	}
	private static class AIConfigHolder {
		private final static AIConfig aiconfig = new AIConfig();
	}
	
	public static void reset() {
		try {
		String output = "";
		output += "#!IMPORTANT! !NOT FOR PEOPLE WHO ARE NOT GOOD WITH COMPUTERS!\n#\n#\n#\n#Lines starting with '#' are comments and are not read\n#These lines will be used to explain the different values\n#When editing this file make sure you only edit the lines without '#' signs\n#If you should corrupt this file i suggest you reinstal the game\n#You can reset this config to its initial values by clicking 'Reset AIConfig' ingame\n#Use common sense when experimenting with theese values\n#\n#Attack Force\n#Attack Force determines how many units the AI will send each wave\n";
		output += "10\n";
		output += "#\n#Workers\n#Workers determines how many workers the AI will use at all times\n";
		output += "5\n";
		output += "#\n#Base Sense Range\n#Base Sense Range determines how close to the AI base you can go before he senses you and attacks\n#in pixels\n";
		output += "400\n";
		output += "#\n#Aggro\n#Aggro determines how often the AI will launch an attack\n#Remember that the AI will only be able to build units at intervals and when he can afford it\n#setting this value to low wont make the AI attack instantly but a low value will mean that the AI\n#wont stop attacking after the first wave has been sendt\n#in milliseconds\n";
		output += "60000\n";
		output += "#\n#Fighter Per Healer\n#FIghter Per Healer determines what the fighter:healer ratio should be\n";
		output += "5\n";
		output += "#\n#Start Gold\n#Start Gold determines how much gold the AI will have at the start of the game\n";
		output += "400\n";
		output += "#\n#Upgrade\n#Upgrade determines the threshold for when the AI will begin to consider upgrading his mainbuilding\n#in milliseconds\n";
		output += "600000\n";
		output += "#\n#Healers in Defence\n#Healers in defence determines how many healers the AI will have in defence\n";
		output += "2\n";
		output += "#\n#Fighters in defence\n#Fighters in defence determines how many fighters the AI will have in defence\n";
		output += "10";
		FileWriter file = new FileWriter("aiconfig.ini");
		file.write(output);
		file.close();
		}catch (IOException e) {
			System.err.println("Error writing config file!");
		}
		}

	public static int getAttackForce() {
		return AIConfigHolder.aiconfig.attackForce;
	}

	public static int getWorkers() {
		return AIConfigHolder.aiconfig.workers;
	}

	public static double getBaseSenseRange() {
		return AIConfigHolder.aiconfig.baseSenseRange;
	}

	public static long getAggro() {
		return AIConfigHolder.aiconfig.aggro;
	}

	public static int getFighterPerHealer() {
		return AIConfigHolder.aiconfig.fighterPerHealer;
	}

	public static int getStartGold() {
		return AIConfigHolder.aiconfig.startGold;
	}

	public static long getUpgrade() {
		return AIConfigHolder.aiconfig.upgrade;
	}

	public static int getHealersInDefence() {
		return AIConfigHolder.aiconfig.healersInDefence;
	}

	public static int getFightersInDefence() {
		return AIConfigHolder.aiconfig.fightersInDefence;
	}
}
