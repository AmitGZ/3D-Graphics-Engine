
public class Sphere extends Obj {
	private Vector position;
	private double radius;

	Sphere(double radius, Vector position) {
		this.radius = radius;
		this.position = position;
	}

	@Override
	public Obj calcLength(Vector origin, Vector direction) {
		double t = position.vectorSub(origin).dotProduct(direction);
		double y = (direction.constMult(t).vectorSub(position.vectorSub(origin))).calcLength();
		double x = Math.sqrt(radius * radius - y * y);
		if ((t - x) > 0 && (t - x) < direction.getLength()) { // outside the sphere
			direction.setLength(t - x);
			return this;
		}
		if (t + x > 0 && (t + x) < direction.getLength()) { // inside the sphere
			direction.setLength(t + x);
			return this;
		}
		// not in range of sphere
		return null;
	}

	public Vector getNormal(Vector point) {
		return point.vectorSub(position).constMult(1 / radius);
	}
}
