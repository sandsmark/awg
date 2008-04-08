import java.awt.Point;
import java.awt.Shape;
/**
 * 
 * @author Stian Veum Møllersen
 * makur of awsoem AI
 *
 */
public class AI {
	//her skal oppfÃ¸rselen til AI legges til
	AIrules oppforsel;
	int fighters, workers, healers;

	
	public AI() {
		oppforsel = new AIrules();

			
	}
//	FORELØPIG IKKE BRUK FOR DENNE METODEN	
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
		Point mainb = GameState.getComputer().getMainBuilding().getPosition();
		for(Unit unit: GameState.getUnits().getUnits()) {
			if(!(unit.getPlayer().isAI()) ) {
			if(mainb.distance(unit.getPosition())<defParamRad) {
				
				return true;
			}
			}
		}
		
		return false;
	}
	public void defend() { //forsvarer seg
		double defParamRad = oppforsel.getBaseSenseRange();
		Point mainb = GameState.getComputer().getMainBuilding().getPosition();
		Unit target = null;
		
		for (Unit unit: GameState.getUnits().getUnits()) {
				if(!(unit.getPlayer().isAI()) ) {
					if(mainb.distance(unit.getPosition())<defParamRad) {
						target = unit;
						break;
					}	
				}
				
		}
		for (Unit unit : GameState.getUnits().getUnits()) {
			if(unit.getPlayer().isAI()) {
				if(unit instanceof Fighter) {
					unit.setTargetUnit(target);
				}
			}
		}
	}
	public boolean willLaunchAttack() { //om han skal angripe/sjekke om antallet units er riktig for attack
		long timePassed = GameState.getTime();
		if(timePassed>oppforsel.getAggro() && (this.getFighters()+this.getHealers())>oppforsel.getAttackForce() && ((this.getHealers()*oppforsel.getfighterPerHealer())/this.getFighters())>=1)
		return true;
		else return false;
	}
	public void build() { //AI sjekker om han kan bygge en unit, og hvilken unit han skal bygge
		// mÃ¥ ogsÃ¥ sjekke om han builder noe/eventuelt oppgraderer noe
	}
	public boolean willUpgrade() {//sjekker om AI vil upgrade
		long tid = GameState.getTime();
//		if(GameState.getComputer().getResources()>GameState.getComputer().getMainBuilding().getUpgradeCost()) settes inn når upgradecost kommer
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
	
	
	
	
}
