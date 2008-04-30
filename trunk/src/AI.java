import java.awt.Point;
import java.util.ArrayList;
/**
 * 
 * @author Stian Veum M�llersen
 * makur of awsoem AI
 *
 */
public class AI {
	//her skal oppførselen til AI legges til
	AIrules oppforsel;
	int HealersInDefence, FightersInDefence, WorkersActive;
	Player AI;
	ArrayList<Unit> fighters = new ArrayList<Unit>();
	ArrayList<Unit> workers = new ArrayList<Unit>();
	ArrayList<Unit> healers = new ArrayList<Unit>();
	ArrayList<Unit> fightersdef = new ArrayList<Unit>();
	ArrayList<Unit> healersdef = new ArrayList<Unit>();
	Resource firstClosestResource = GameState.getMap().getClosestNode(GameState.getComputer().getMainBuilding().getPosition());
	int fPrH;
	long TimePassedSinceLast;
	
	public AI() {
		oppforsel = new AIrules();
		AI = GameState.getComputer();
		GameState.getState().setAi(this);
		HealersInDefence = oppforsel.getHealersInDefence();
		FightersInDefence = oppforsel.getFightersInDefence();
		WorkersActive = oppforsel.getWorkers();
		AI.setResources(oppforsel.getStartGold());
		fPrH = oppforsel.getAttackForce()%oppforsel.getfighterPerHealer();	//==antallet healers i attackforce
		TimePassedSinceLast = 0;
	}


	public boolean willDefend() { //om AI vil forsvare basen sin under angrep/range
		double defParamRad = oppforsel.getBaseSenseRange();
		Point mainb = GameState.getComputer().getMainBuilding().getPosition();
		for(Unit unit: GameState.getUnits().getUnits()) {
			if(!(unit.getPlayer().isAI()) && mainb.distance(unit.getPosition())<=defParamRad ) {
				return true;
			}
		}
		return false;
	}
	
	public Unit getOffender() {
		double defParamRad = oppforsel.getBaseSenseRange();
		Point mainb = GameState.getComputer().getMainBuilding().getPosition();
		for(Unit unit: GameState.getUnits().getUnits()) {
			if(unit.getPlayer().equals(GameState.getHuman()) && mainb.distance(unit.getPosition())<defParamRad ) {
				return unit;
			}
		}
		return null;
	}
	
	public void defendAgainst(Unit offender) { //forsvarer seg		
		for (Unit unit : this.getFightersdef()) { //henter AI
						unit.goTo(offender.getPosition());
						unit.setTargetUnit(offender);
						
		}
	}
	public boolean willLaunchAttack() { //om han skal angripe/sjekke om antallet units er riktig for attack
		TimePassedSinceLast = GameState.getTime()-TimePassedSinceLast;
//		System.out.println("Healers:"+healers.size()+"  Fighters:"+fighters.size());
		if(((TimePassedSinceLast) >= oppforsel.getAggro()) && ((this.getFighters().size()+this.getHealers().size())>=(oppforsel.getAttackForce()))) {
			System.out.println("Attack!");
			return true;
		}
		return false;
	}
	
	
	public void launchAttack() { //kj�res n�r willLaunchAttack er true
		Point target = GameState.getHuman().getMainBuilding().getPosition();
		for (Unit unit : fighters) {
			unit.goTo(target);
			System.out.println("I have attacked!");
		}
		for (Unit unit : healers) {
			unit.goTo(target);
			System.out.println("I have attacked!");
		}
		
		
	}
	public void build() { //AI sjekker om han kan bygge en unit, og hvilken unit han skal bygge
		while(true) {
		if(willUpgrade()) {
			GameState.getComputer().getMainBuilding().upgradeBuilding();
			GameState.getUnits().upgradeUnits(GameState.getComputer());
			break;
		}
		if(this.getWorkers().size()<WorkersActive && AI.getResources()>=Worker.cost) { //bygge workers
			GameState.getUnits().addUnit(new Worker(AI));
			Unit unit = GameState.getUnits().getUnit(GameState.getUnits().getUnits().size()-1);
			unit.goTo(firstClosestResource.getPosition());
			unit.setTargetResource(firstClosestResource);
			workers.add(unit);
			break;
			
		}
		if(this.getFightersdef().size()<FightersInDefence && AI.getResources()>=Fighter.cost) { //bygge fighters til forsvar
			GameState.getUnits().addUnit(new Fighter(AI));
			Unit unit = GameState.getUnits().getUnit(GameState.getUnits().getUnits().size()-1);
			unit.goTo(new Point(unit.getPosition().x-(int)((Math.random()+1)*45), unit.getPosition().y-(int)((Math.random()+1)*65)));
			fightersdef.add(unit);
			break;
		}
		if(this.getHealerssdef().size()<(HealersInDefence) && AI.getResources()>=Healer.cost) {
			GameState.getUnits().addUnit(new Healer(AI));
			Unit unit = GameState.getUnits().getUnit(GameState.getUnits().getUnits().size()-1);
			unit.goTo(new Point(unit.getPosition().x-(int)((Math.random()+1)*45), unit.getPosition().y-(int)((Math.random()+1)*65)));
			healersdef.add(unit);
			break;
		}
		if(this.getFighters().size()<oppforsel.getAttackForce()-fPrH && AI.getResources() >= Fighter.cost) {
			GameState.getUnits().addUnit(new Fighter(AI));
			Unit unit = GameState.getUnits().getUnit(GameState.getUnits().getUnits().size()-1);
			unit.goTo(new Point(unit.getPosition().x-(int)((Math.random()+1)*85), unit.getPosition().y-(int)((Math.random()+1)*5)));
			fighters.add(unit);
			break;
		}
		if(this.getHealers().size()<fPrH && AI.getResources() >= Healer.cost) {
			GameState.getUnits().addUnit(new Healer(AI));
			Unit unit = GameState.getUnits().getUnit(GameState.getUnits().getUnits().size()-1);
			unit.goTo(new Point(unit.getPosition().x-(int)((Math.random()+1)*85), unit.getPosition().y-(int)((Math.random()+1)*5)));
			healers.add(unit);
			break;
		}
		
		
		}
		
	}
	public boolean willUpgrade() {//sjekker om AI vil upgrade
		long tid = GameState.getTime();
		if(GameState.getComputer().getResources()> Building.getUpgradeCost()) //settes inn n�r upgradecost kommer
			if(tid>oppforsel.getUpgrade() && !(GameState.getComputer().getMainBuilding().getBuildingLevel()>1)) {
				return true;
			}
		return false; 
	}
	
	public ArrayList<Unit> getFighters() { //henter antallet fighters
			return fighters;
	}
	public ArrayList<Unit> getWorkers() { // henter antallet workers
		return workers;
	}
	public ArrayList<Unit> getHealers() { //henter antallet healers
		return healers;
	}
	public ArrayList<Unit> getFightersdef() {
		return fightersdef;
	}
	public ArrayList<Unit> getHealerssdef() {
		return healersdef;
	}

	public void idleWorkers() {
		Resource wasClosest = firstClosestResource;
//		System.out.println(wasClosest.getRemaining());
		if(wasClosest.getRemaining()<1){
			Resource newClosest = GameState.getMap().getClosestNode(wasClosest.getPosition());
			firstClosestResource = newClosest;
			System.out.println("Its empty! :|");
			for (Unit unit : workers) {
				unit.goTo(newClosest.getPosition());
				unit.setTargetResource(newClosest);
				System.out.println("Work work");
			}
		}
	}
	
	
	
	
}
