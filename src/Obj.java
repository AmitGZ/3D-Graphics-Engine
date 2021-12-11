
public abstract class Obj {
	protected double reflection;
	protected Vector RGB;

	public abstract Obj calcLength(Vector origin, Vector direction);

	public abstract Vector getNormal(Vector point);

	public Vector getRGB() {
		return RGB;
	}
	
	public void setRGB(Vector RGB) {
		this.RGB=RGB;
	}
	
	public double getReflection() {
		return reflection;
	}
	
	public void setReflection(double reflection) {
		this.reflection=reflection;
	}
}
