
public class AIrules {

	double UnitSenseRange; //radius for sansevidde
	int AttackForce; //hvor mange units før attack
	int Workers; //hvor mange workers han opererer med
	double BaseSenseRange; //hvor nære basen før han forsvarer
	int Aggro; //hvor ofte han sender wave i sekunder
	int Defence; //hvor mange units i forsvar
	
	public AIrules() {
		this.UnitSenseRange=15;
		this.AttackForce = 5;
		this.Workers = 5;
		this.BaseSenseRange = 30;
		this.Aggro = 36000; //i sek
		this.Defence = 5;
	}
	public void setUnitSenseRange(double d) {
		this.UnitSenseRange = d;
	}
	public void setAttackForce(int af) {
		this.AttackForce = af;
	}
	public void setWorkers(int w) {
		this.Workers = w;
	}
	public void setBaseSenseRange(double b) {
		this.BaseSenseRange = b;
	}
	public void setAggro(int a) {
		this.UnitSenseRange = a;
	}
	public void setDefence(int d) {
		this.UnitSenseRange = d;
	}
	public double getUnitSenseRange() {
		return this.UnitSenseRange;
	}
	public int getAttackForce() {
		return this.AttackForce;
	}
	public int getWorkers() {
		return this.Workers;
	}
	public double getBaseSenseRange() {
		return this.BaseSenseRange;
	}
	public int getAggro() {
		return this.Aggro;
	}
	public int getDefence() {
		return this.Defence;
	}
	
	
}
