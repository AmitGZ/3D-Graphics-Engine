
public class Cube extends Obj {
	private Vector position;
	private double size;
	private Vector[] vertices;
	private Square[] faces;

	Cube(double size, Vector position) {
		this.size = size;
		this.position = position;

		double diagonal = size * Math.sqrt(3) / 2;
		vertices = new Vector[8];
		vertices[0] = new Vector(diagonal, diagonal, diagonal).vectorAdd(position);
		vertices[1] = new Vector(diagonal, -diagonal, diagonal).vectorAdd(position);
		vertices[2] = new Vector(-diagonal, diagonal, diagonal).vectorAdd(position);
		vertices[3] = new Vector(-diagonal, -diagonal, diagonal).vectorAdd(position);
		vertices[4] = new Vector(diagonal, diagonal, -diagonal).vectorAdd(position);
		vertices[5] = new Vector(diagonal, -diagonal, -diagonal).vectorAdd(position);
		vertices[6] = new Vector(-diagonal, diagonal, -diagonal).vectorAdd(position);
		vertices[7] = new Vector(-diagonal, -diagonal, -diagonal).vectorAdd(position);

		faces = new Square[6];
		faces[0] = new Square(vertices[0], vertices[1], vertices[3], vertices[2]);
		faces[1] = new Square(vertices[0], vertices[1], vertices[5], vertices[4]);
		faces[2] = new Square(vertices[0], vertices[2], vertices[6], vertices[4]);
		faces[3] = new Square(vertices[1], vertices[3], vertices[7], vertices[5]);
		faces[4] = new Square(vertices[4], vertices[5], vertices[7], vertices[6]);
		faces[5] = new Square(vertices[2], vertices[3], vertices[7], vertices[6]);
	}

	@Override
	public Obj calcLength(Vector origin, Vector direction) {
		Obj closest = null, tmp;
		for (int i = 0; i < 6; i++) {
			tmp = faces[i].calcLength(origin, direction);
			if (tmp != null)
				closest = tmp;
		}
		return closest;
	}

	@Override
	public void setRGB(Vector RGB) {
		for (int i = 0; i < 6; i++)
			faces[i].setRGB(RGB);
	}

	@Override
	public void setReflection(double reflection) {
		for (int i = 0; i < 6; i++)
			faces[i].setReflection(reflection);
	}

	@Override
	public Vector getNormal(Vector point) {
		return null;
	}

	public Square getFace(int index) {
		try {
			return faces[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("The index you have entered is invalid");
		}
		return null;
	}
}
