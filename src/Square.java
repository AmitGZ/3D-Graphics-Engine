
public class Square extends Obj {
	private Polygon[] triangles;

	public Square(Vector a, Vector b, Vector c, Vector d) {
		triangles = new Polygon[2];
		triangles[0] = new Polygon(a, b, c);
		triangles[1] = new Polygon(a, c, d);
	}

	@Override
	public Obj calcLength(Vector origin, Vector direction) {
		Obj tmp1 = triangles[0].calcLength(origin, direction);
		Obj tmp2 = triangles[1].calcLength(origin, direction);
		if (tmp2 != null)
			return tmp2;
		return tmp1;
	}

	@Override
	public Vector getNormal(Vector point) {
		return triangles[1].getNormal(point);
	}

	@Override
	public void setRGB(Vector RGB) {
		for (int i = 0; i < 2; i++)
			triangles[i].setRGB(RGB);
	}

	@Override
	public void setReflection(double reflection) {
		for (int i = 0; i < 2; i++)
			triangles[i].setReflection(reflection);
	}

	public Polygon getTriangle(int index) {
		try {
			return triangles[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("The index you have entered is invalid");
		}
		return null;
	}
}
