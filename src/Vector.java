import java.util.ArrayList;

public class Vector {

	private double x, y, z;
	private double length;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector() {
		this(0, 0, 0);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setVector(Vector v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}

	public void setXYZ(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getLength() {
		return length;
	}
	
	public void setLength(double length) {
		this.length = length;
	}

	public double calcLength() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	public double calcDistance(Vector v) {
		return Math.sqrt(Math.pow(v.getX() - x, 2) + Math.pow(v.getY() - y, 2) + Math.pow(v.getZ() - z, 2));
	}

	public Vector constMult(double scaler) {
		return new Vector(x * scaler, y * scaler, z * scaler);
	}

	public double dotProduct(Vector v) {
		return (v.getX() * x + v.getY() * y + v.getZ() * z);
	}

	public Vector vectorSub(Vector v) {
		return new Vector(x - v.getX(), y - v.getY(), z - v.getZ());
	}

	public Vector vectorAdd(Vector v) {
		return new Vector(x + v.getX(), y + v.getY(), z + v.getZ());
	}

	public double calcCosAngle(Vector v) {
		return dotProduct(v) / (v.calcLength() * calcLength());
	}

	public Vector crossProduct(Vector v) {
		return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}

	public Vector getUnit() {
		return constMult(1 / calcLength());
	}

	public Vector rotateAround(Vector unit, double theta) {
		Vector a = this.constMult(Math.cos(theta));
		Vector b = unit.crossProduct(this).constMult(Math.sin(theta));
		Vector c = unit.constMult(unit.dotProduct(this) * (1 - Math.cos(theta)));

		return ((a.vectorAdd(b)).vectorAdd(c));
	}
	
	public Obj getClosest(Vector origin, ArrayList<Obj> objects) {
		setLength(Double.MAX_VALUE);

		// calculating closest object and setting the distance
		Obj closest = null, tmp;
		for (int i = 0; i < objects.size(); i++) {
			tmp = objects.get(i).calcLength(origin, this);
			if (tmp != null)
				closest = tmp;
		}
		return closest;
	}

}