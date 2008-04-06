

public class AI {
	//her skal oppførselen til AI legges til
	AIrules oppforsel;
	int fighters, workers, healers;
	
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
		long timePassed = System.currentTimeMillis();
		if(timePassed>oppforsel.getAggro() && (this.getFighters()+this.getHealers())>oppforsel.getAttackForce())
		return true;
		else return false;
	}
	public void build() { //AI sjekker om han kan bygge en unit, og hvilken unit han skal bygge
		// må også sjekke om han builder noe/eventuelt oppgraderer noe
	}
	public boolean willUpgrade() {//sjekker om AI vil upgrade
		//if(GameState.getComputer().getResources()>) settes inn n�r upgradecost kommer
		return true; 
	}
	
	public int getFighters() { //henter antallet fighters
			return fighters;
	}
	public int getWorkers() { // henter antallet workers
		return workers;
	}
	public int getHealers() { //henter antallet healers
		return healers;
	}
	public int getUnits() { //henter totale antallet units
		return this.getFighters() + this.getWorkers() + this.getHealers();
	}
	public void setFighters(int c) {
		this.fighters+=c;
	}
	public void setHealers(int c) {
		this.healers+=c;
	}
	public void setWorkers(int c) {
		this.workers+=c;
	}
	
	
	
	
}
