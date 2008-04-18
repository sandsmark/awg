/**
 * 
 * @author Stian Veum M�llersen
 * makur of awsoem AI
 *
 */
public class AIrules {

	double unitSenseRange; //radius for sansevidde
	int attackForce; //hvor mange units før attack
	int workers; //hvor mange workers han opererer med
	double baseSenseRange; //hvor nære basen før han forsvarer
	long aggro; //hvor ofte han sender wave i ms
	int defence; //hvor mange units i forsvar
	int fighterPerHealer; //healer/fighter ratio
	int startGold; //start gull
	long upgrade; // når han skal oppgradere hovedbygg i ms
	int totalAttackForce; //hvor mange units AIen skal maks ha
	
	public int getTotalAttackForce() {
		return totalAttackForce;
	}
	public void setTotalAttackForce(int totalAttackForce) {
		this.totalAttackForce = totalAttackForce;
	}
	public AIrules() {
		this.unitSenseRange=15;
		this.attackForce = 6;
		this.workers = 5;
		this.baseSenseRange = 500;
		this.aggro = 36000000; //i ms
		this.defence = 6;
		this.fighterPerHealer = 5;
		this.startGold = 50000;
		this.upgrade = 108000000; //i ms
		this.totalAttackForce = 50;
	}
	public void setunitSenseRange(double d) {
		this.unitSenseRange = d;
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
	public void setDefence(int d) {
		this.defence = d;
	}
	public double getUnitSenseRange() {
		return this.unitSenseRange;
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
	public int getDefence() {
		return this.defence;
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
	
	
}