
public class AIrules {

	double unitSenseRange; //radius for sansevidde
	int attackForce; //hvor mange units før attack
	int workers; //hvor mange workers han opererer med
	double baseSenseRange; //hvor nære basen før han forsvarer
	int aggro; //hvor ofte han sender wave i sekunder
	int defence; //hvor mange units i forsvar
	int fighterPerHealer; //healer/fighter ratio
	int startGold; //start gull
	int upgrade; // når han skal oppgradere hovedbygg i sek
	
	public AIrules() {
		this.unitSenseRange=15;
		this.attackForce = 6;
		this.workers = 5;
		this.baseSenseRange = 30;
		this.aggro = 36000; //i sek
		this.defence = 5;
		this.fighterPerHealer = 5;
		this.startGold = 50000;
		this.upgrade = 108000; //i sek
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
	public void setAggro(int a) {
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
	public int getAggro() {
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
	public int getUpgrade() {
		return this.upgrade;
	}
	public void setUpgrade(int upgrade) {
		this.upgrade = upgrade;
	}
	
	
}
