import java.awt.Point;
import java.util.ArrayList;
/**
 * 
 * @author Stian Veum Møllersen
 * makur of awsoem AI
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
	Resource ClosestResource = GameState.getMap().getClosestNode(GameState.getComputer().getMainBuilding().getPosition());
	int fPrH;
	long TimePassedSinceLast;
	ArrayList<Resource> nodeHandler = new ArrayList<Resource>();
	int countNode = 0;
	
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
		for (int i = 0; i < GameState.getMap().getResources().length-1; i++) {
			if(i==0)
			nodeHandler.add(GameState.getMap().getClosestNode(ClosestResource.getPosition()));
			else {
				nodeHandler.add(GameState.getMap().getClosestNode(nodeHandler.get(nodeHandler.size()-1).getPosition()));
			}
		} 
	}

/**
 * Checks if the AI is in a state where it will defend itself
 * 
 */
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
	/**
	 * The AI gets the unit that it wants to defend itself against
	 * 
	 */
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
	/**
	 * The AI defends itself against the offender
	 * @param offender
	 */
	public void defendAgainst(Unit offender) { //forsvarer seg		
		for (Unit unit : this.getFightersdef()) { //henter AI
						unit.goTo(offender.getPosition());
						unit.setTargetUnit(offender);
						
		}
		for (Unit unit : this.getHealerssdef()) {
			unit.goTo(offender.getPosition());
		}
	}
	/**
	 * The AI checks if it is in a state where it wants to launch an attack against the human player
	 * @return
	 */
	public boolean willLaunchAttack() { //om han skal angripe/sjekke om antallet units er riktig for attack
		TimePassedSinceLast = GameState.getTime()-TimePassedSinceLast;
		if(((TimePassedSinceLast) >= oppforsel.getAggro()) && ((this.getFighters().size()+this.getHealers().size())>=(oppforsel.getAttackForce()))) {
			return true;
		}
		return false;
	}
	
	/**
	 * The AI Launches the attack, only if it wants to launch an attack
	 */
	public void launchAttack() { //kj�res n�r willLaunchAttack er true
		Point target = GameState.getHuman().getMainBuilding().getPosition();
		for (Unit unit : fighters) {
			unit.goTo(target);
		}
		for (Unit unit : healers) {
			unit.goTo(target);
		}
		
		
	}
	/**
	 * This is the build method of the AI. Its based on a priority list and it makes one unit (or not) each time it is run
	 */
	
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
			if(countNode==0){
				unit.goTo(ClosestResource.getPosition());
				unit.setTargetResource(ClosestResource);
			}
			else {
				unit.goTo(nodeHandler.get(countNode).getPosition());
				unit.setTargetResource(nodeHandler.get(countNode));
			}
			
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
	/**
	 * Check if the AI is in a state where it wants to upgrade its mainbuilding
	 * @return
	 */
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
/**
 * The AI checks if the workers are idle or not
 * @return
 */
	public boolean idleWorkers() {
		if(countNode==0) {
			if(ClosestResource.getRemaining()<10000){
				return true;
			}
		}
		else {
			if(nodeHandler.get(countNode).getRemaining()<1000) {
				return true;
			}
		}
		return false;
	}
	/**
	 * The AI defends its workers if they are under attack
	 */
	public void defendWorkers() {
		boolean isBeingAttacked = false;
		Point target = null;
		for (Unit unit : workers) {
			for(Unit offender: GameState.getUnits().getUnits()) {
					if(!(offender.getPlayer().isAI()) && unit.getPosition().distance(offender.getPosition())<=35 ) {
						target = offender.getPosition();
						isBeingAttacked = true;
						break;
					}
			}
		}
		if(isBeingAttacked) {
			for (Unit unit : this.getFightersdef()) {
				unit.goTo(target);
			}
			for (Unit unit : this.getHealerssdef()) {
				unit.goTo(target);
			}
		}
	}
	/**
	 * The AI sends the idle workers to the next node
	 */
	public void goNext() {
		Resource newClosest = nodeHandler.get(countNode);
		for (Unit unit : workers) {
			unit.goTo(newClosest.getPosition());
			unit.setTargetResource(newClosest);
		}
		countNode +=1;
	}
	
	
	
	
}
