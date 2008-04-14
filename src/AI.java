import java.awt.Point;
/**
 * 
 * @author Stian Veum M�llersen
 * makur of awsoem AI
 *
 */
public class AI {
	//her skal oppførselen til AI legges til
	AIrules oppforsel;
	int fighters, workers, healers, fighersdef, healersdef;
	Player AI = GameState.getComputer();
	
	public AI() {
		oppforsel = new AIrules();
//		workers = 1; //adde testing unit
//		fighersdef = 1; //adde testing unit
			
	}
//	FOREL�PIG IKKE BRUK FOR DENNE METODEN	
//	public void checkAttackUnits() { //for units som senser andre units
//		double senseRange = oppforsel.getUnitSenseRange();
//		for (Unit unit : GameState.getUnits().getUnits()) {
//			if(unit.getPlayer().isAI()) {
//				Point center = unit.getPosition();
//				
//				
//			}
//		}
//	}
	public boolean willDefend() { //om AI vil forsvare basen sin under angrep/range
		double defParamRad = oppforsel.getBaseSenseRange();
		System.out.println("to defend or not to DIEEDIEDIEDIE!");
		Point mainb = GameState.getComputer().getMainBuilding().getPosition();
		for(Unit unit: GameState.getUnits().getUnits()) {
			if(unit.getPlayer().equals(GameState.getHuman()) && mainb.distance(unit.getPosition())<defParamRad ) {
				System.out.println("Thou shalt defend!");
				return true;
			}
		}
		System.out.println("Harakiri");
		return false;
	}
	
	public Unit getOffender() {
		double defParamRad = oppforsel.getBaseSenseRange();
		System.out.println("to defend or not to DIEEDIEDIEDIE!");
		Point mainb = GameState.getComputer().getMainBuilding().getPosition();
		for(Unit unit: GameState.getUnits().getUnits()) {
			if(unit.getPlayer().equals(GameState.getHuman()) && mainb.distance(unit.getPosition())<defParamRad ) {
				System.out.println("Thou shalt defend!");
				return unit;
			}
		}
		System.out.println("Harakiri");
		return null;
	}
	
	public void defendAgainst(Unit offender) { //forsvarer seg		
		for (Unit unit : GameState.getUnits().getUnits()) { //henter AI
			if(unit.getPlayer().isAI()) {
				if(unit instanceof Fighter || unit instanceof Healer) {
					if (!offender.equals(unit.getTargetUnit())) {
						unit.setTargetUnit(offender);
						unit.goTo(offender.getPosition());
					}
				}
			}
		}
		System.out.println("Muhammed JIHAD");
	}
	public boolean willLaunchAttack() { //om han skal angripe/sjekke om antallet units er riktig for attack
		long timePassed = GameState.getTime();
		if(timePassed>oppforsel.getAggro() && ((this.getFighters()+this.getHealers())-oppforsel.getDefence())>oppforsel.getAttackForce() && ((this.getHealers()*oppforsel.getfighterPerHealer())/this.getFighters())>=1)
		return true;
		else return false;
	}
	public void attack() { //kj�res n�r willLaunchAttack er true
		Point target = GameState.getHuman().getMainBuilding().getPosition();
		int countH = 0; //teller healers
		int countF = 0; //teller fighters
		for (Unit unit : GameState.getUnits().getUnits()) {
			if((countH<(oppforsel.getAttackForce()%oppforsel.getfighterPerHealer())) &&(unit instanceof Healer)) {
				unit.goTo(target);
				countH +=1;
			}
			if(countF<(oppforsel.getAttackForce()-countH) && (unit instanceof Fighter)) {
				unit.goTo(target);
				countF +=1;
			}
			if((countH+countF)==oppforsel.getAttackForce())
				break;
		}
		
	}
	public void build() { //AI sjekker om han kan bygge en unit, og hvilken unit han skal bygge
		// må også sjekke om han builder noe/eventuelt oppgraderer noe
		long tid = GameState.getTime();
		while(true) {
		if(willUpgrade()) {
			GameState.getComputer().getMainBuilding().upgradeBuilding();
//			GameState.getUnits().upgradeUnits(GameState.getComputer());
			break;
		}
		if(this.getWorkers()<oppforsel.getWorkers()) { //bygge workers
			GameState.getUnits().addUnit(new Worker(AI));
			Unit unit = GameState.getUnits().getUnit(GameState.getUnits().getUnits().size()-1);
			unit.goTo(GameState.getMap().getClosestNode(GameState.getComputer().getMainBuilding().getPosition()).getPosition());
			unit.setTargetResource(GameState.getMap().getClosestNode(GameState.getComputer().getMainBuilding().getPosition()));
			workers +=1;
			break;
			
		}
		if(this.getFighersdef()<oppforsel.getfighterPerHealer() && this.getFighersdef()<oppforsel.getDefence()) { //bygge fighters til forsvar
			GameState.getUnits().addUnit(new Fighter(AI));
			Unit unit = GameState.getUnits().getUnit(GameState.getUnits().getUnits().size()-1);
//			Point newPoint = unit.getPosition();
//			newPoint.translate(10, 15);
			unit.goTo(new Point(unit.getPosition().x-60, unit.getPosition().y-85));
			fighersdef +=1;
			break;
		}
		if(this.getHealersdef()<(oppforsel.getDefence()-this.getFighersdef())) {
			GameState.getUnits().addUnit(new Healer(AI));
			Unit unit = GameState.getUnits().getUnit(GameState.getUnits().getUnits().size()-1);
//			Point newPoint = unit.getPosition();
//			newPoint.translate(50, 75);
			unit.goTo(new Point(unit.getPosition().x-60, unit.getPosition().y-85));
			healersdef +=1;
			break;
		}
		
		
		}
		
	}
	public boolean willUpgrade() {//sjekker om AI vil upgrade
		long tid = GameState.getTime();
//		if(GameState.getComputer().getResources()>GameState.getComputer().getMainBuilding().getUpgradeCost()) settes inn n�r upgradecost kommer
			if(tid>oppforsel.getUpgrade() && !(GameState.getComputer().getMainBuilding().getBuildingLevel()>1)) {
				return true;
			}
//		}
		return false; 
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
	public int getFighersdef() {
		return fighersdef;
	}
	public void setFighersdef(int fighersdef) {
		this.fighersdef = fighersdef;
	}
	public int getHealersdef() {
		return healersdef;
	}
	public void setHealersdef(int healersdef) {
		this.healersdef = healersdef;
	}
	
	
	
	
}
