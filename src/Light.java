
public class Light {
	private Vector position;
	private double luminosity;
	
	Light(Vector position,double luminosity){
		this.position = position;
		this.luminosity = luminosity;
	}
	
	public double getLuminosity() {
		return luminosity;
	}

	public Vector getPosition() {
		return position;
	}
}
