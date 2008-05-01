/**
 * 
 * @author Stian Veum Møllersen
 * makur of awsoem AI
 *
 */
public class AIrules {

	int attackForce; //hvor mange units før attack
	int workers; //hvor mange workers han opererer med
	double baseSenseRange; //hvor nære basen før han forsvarer
	long aggro; //hvor ofte han sender wave i ms
	int fighterPerHealer; //healer/fighter ratio
	int startGold; //start gull
	long upgrade; // når han skal oppgradere hovedbygg i ms
	int healersInDefence; //Hvor mange healers i forsvar
	int fightersInDefence; //Hvor mange fighters i forsvar
	
	public AIrules() {
		
		this.attackForce = AIConfig.getAttackForce();
		this.workers = AIConfig.getWorkers();
		this.baseSenseRange = AIConfig.getBaseSenseRange();
		this.aggro = AIConfig.getAggro();
		this.fighterPerHealer = AIConfig.getFighterPerHealer();
		this.startGold = AIConfig.getStartGold();
		this.upgrade = AIConfig.getUpgrade();
		this.healersInDefence = AIConfig.getHealersInDefence();
		this.fightersInDefence = AIConfig.getFightersInDefence();
	}
	public void setAttackForce(int af) {
		this.attackForce = af;
	}
	public void setWorkers(int w) {
		this.workers = w;
	}
	public void setBaseSenseRange(double b) {
		this.baseSenseRange = b;
	}
	public void setAggro(long a) {
		this.aggro = a;
	}
	public int getAttackForce() {
		return this.attackForce;
	}
	public int getWorkers() {
		return this.workers;
	}
	public double getBaseSenseRange() {
		return this.baseSenseRange;
	}
	public long getAggro() {
		return this.aggro;
	}
	public int getfighterPerHealer() {
		return this.fighterPerHealer;
	}
	public void setfighterPerHealer(int fighterPerHealer) {
		this.fighterPerHealer = fighterPerHealer;
	}
	public int getStartGold() {
		return this.startGold;
	}
	public void setStartGold(int startGold) {
		this.startGold = startGold;
	}
	public long getUpgrade() {
		return this.upgrade;
	}
	public void setUpgrade(long upgrade) {
		this.upgrade = upgrade;
	}
	public int getHealersInDefence() {
		return healersInDefence;
	}
	public void setHealersInDefence(int healersInDefence) {
		this.healersInDefence = healersInDefence;
	}
	public int getFightersInDefence() {
		return fightersInDefence;
	}
	public void setFightersInDefence(int fightersInDefence) {
		this.fightersInDefence = fightersInDefence;
	}
	
	
}
