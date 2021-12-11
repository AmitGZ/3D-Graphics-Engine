
public class Polygon extends Obj {
	private Vector normal;
	private Vector a, b, c;

	Polygon(Vector a, Vector b, Vector c) {
		this.a = a;
		this.b = b;
		this.c = c;
		normal = ((b.vectorSub(a)).crossProduct(c.vectorSub(a))).getUnit();
	}

	public Vector getA() {
		return a;
	}

	public Vector getB() {
		return b;
	}

	public Vector getC() {
		return c;
	}

	@Override
	public Vector getNormal(Vector point) {
		return normal;
	}

	public Vector getNormal() {
		return getNormal(null);
	}

	@Override
	public Obj calcLength(Vector position, Vector direction) {
		double length = ((getA().vectorSub(position)).dotProduct(getNormal())) / (direction.dotProduct(getNormal()));

		if (length > 0 && length < direction.getLength())
			if (isInTriangle(getA(), getB(), getC(), position.vectorAdd(direction.constMult(length)))) {
				direction.setLength(length);
				return this;
			}
		return null;
	}

	private boolean isInTriangle(Vector point_a, Vector point_b, Vector point_c, Vector v) {
		double vector_angle = (point_b.vectorSub(point_a)).calcCosAngle(v.vectorSub(point_a));
		double triangle_angle = (point_b.vectorSub(point_a)).calcCosAngle(point_c.vectorSub(point_a));
		if (vector_angle < triangle_angle)
			return false;

		vector_angle = (point_c.vectorSub(point_b)).calcCosAngle(v.vectorSub(point_b));
		triangle_angle = (point_a.vectorSub(point_b)).calcCosAngle(point_c.vectorSub(point_b));
		if (vector_angle < triangle_angle)
			return false;

		vector_angle = (point_a.vectorSub(point_c)).calcCosAngle(v.vectorSub(point_c));
		triangle_angle = (point_a.vectorSub(point_c)).calcCosAngle(point_b.vectorSub(point_c));
		if (vector_angle < triangle_angle)
			return false;

		return true;
	}
}
