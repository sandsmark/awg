
public class AI {
	//her skal oppførselen til AI legges til
	AIrules oppforsel;
	
	
	public AI() {
		oppforsel = new AIrules();
	}
	
	public boolean willAttack() { //for units som senser andre units
		return true;
	}
	public boolean willDefend() { //forsvare basen sin under angrep/range
		return true;
	}
	public boolean willLaunchAttack() { //om han skal angripe/sjekke om antallet units er riktig for attack
		return true;
	}
	public void build() { //AI sjekker om han kan bygge en unit, og hvilken unit han skal bygge
		// må også sjekke om han builder noe/eventuelt oppgraderer noe
	}
	public boolean willUpgrade() {//sjekker om AI vil angripe
		return true; 
	}
	
	
	
	
}
