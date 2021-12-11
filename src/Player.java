import java.util.ArrayList;
import java.util.stream.IntStream;

public class Player {
	private ArrayList<ArrayList<Ray>> rays;
	private int ray_num_x, ray_num_y;
	private Vector position;
	private double x_fov, y_fov;
	private double yaw, pitch;

	Player(Vector position, int ray_num_x, int ray_num_y, double fov) {
		this.position = position;
		this.ray_num_x = ray_num_x;
		this.ray_num_y = ray_num_y;
		x_fov = fov;
		y_fov = fov * (double) ray_num_y / ray_num_x;
		createRays();
	}

	private void createRays() {
		rays = new ArrayList<>();
		for (int y = 0; y < ray_num_y; y++)
			rays.add(new ArrayList<Ray>());

		double ray_yaw, ray_pitch;
		for (int y = 0; y < ray_num_y; y++)
			for (int x = 0; x < ray_num_x; x++) {
				ray_pitch = y_fov * (0.5 - ((double) y / (ray_num_y - 1)));
				ray_yaw = x_fov * (((double) x / (ray_num_x - 1)) - 0.5);
				rays.get(y).add(new Ray(position, ray_yaw, ray_pitch));
			}
	}

	public ArrayList<ArrayList<Ray>> getRays() {
		return rays;
	}

	public int getRayNumX() {
		return ray_num_x;
	}

	public int getRayNumY() {
		return ray_num_y;
	}

	public void setPos(Vector position) {
		this.position = position;
	}

	public Vector getPos() {
		return position;
	}

	public double getPitch() {
		return pitch;
	}

	public double getYaw() {
		return yaw;
	}

	public void movX(double speed) {
		position.setX(position.getX() + speed);
	}

	public void movY(double speed) {
		position.setY(position.getY() + speed);
	}

	public void movZ(double speed) {
		position.setZ(position.getZ() + speed);
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
	}

	public void movAhead(double speed) {
		movX(speed * Math.sin(pitch) * Math.cos(yaw));
		movY(speed * Math.sin(pitch) * Math.sin(yaw));
		movZ(speed * Math.cos(pitch));
	}

	public void movRight(double speed) {
		movX(speed * Math.sin(yaw));
		movY(speed * -Math.cos(yaw));
	}

	public void calculateRays(ArrayList<Obj> objects, ArrayList<Light> lights) {
		Vector direction = new Vector(Math.sin(pitch) * Math.cos(yaw), Math.sin(pitch) * Math.sin(yaw),
				Math.cos(pitch));
		Vector pitch_axis = new Vector(Math.sin(pitch - 1.57) * Math.cos(yaw), Math.sin(pitch - 1.57) * Math.sin(yaw),
				Math.cos(pitch - 1.57));
		Vector yaw_axis = direction.crossProduct(pitch_axis);

		IntStream.range(0, ray_num_y * ray_num_x).parallel().forEach(i -> {
			rays.get(i / ray_num_x).get(i % ray_num_x).calculateDirection(direction, pitch_axis, yaw_axis);
			rays.get(i / ray_num_x).get(i % ray_num_x).calculateRay(objects, lights,2);
		});
	}
}