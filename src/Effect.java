import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Effect {
	public enum Type {
		HEAL,
		EXPLOSION,
		BLOOD
	}
	private BufferedImage [] sprite;
	private int i;
	private int max;
	public Effect (Type type) {
		try {
			switch (type) {
			case HEAL:
				this.max = 2;
				this.sprite = new BufferedImage[max + 1];
				for (int j=0; j<max+1; j++){
					this.sprite[j] = ImageIO.read(getClass().getResource("/effects/healing/" + j + ".png"));
				}
				break;
			case EXPLOSION:
				this.max = 2;
				this.sprite = new BufferedImage[max + 1];
				for (int j=0; j<max+1; j++){
					this.sprite[j] = ImageIO.read(getClass().getResource("/effects/explosion/" + j + ".png"));
				}
				break;
			case BLOOD:
				this.max = 2;
				this.sprite = new BufferedImage[max + 1];
				for (int j=0; j<max+1; j++){
					this.sprite[j] = ImageIO.read(getClass().getResource("/effects/blood/" + j + ".png"));
				}
				break;
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	
	public BufferedImage pop() {
		return sprite[i++%max];
	}
}
