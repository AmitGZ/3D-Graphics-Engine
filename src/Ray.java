import java.awt.Color;
import java.math.MathContext;
import java.util.ArrayList;

public class Ray extends Vector {
	private Vector origin;
	private double ray_pitch, ray_yaw;
	private Vector RGB;

	Ray(Vector origin, double ray_yaw, double ray_pitch) {
		this.ray_yaw = ray_yaw;
		this.ray_pitch = ray_pitch;
		this.origin = origin;
		RGB = new Vector();
	}

	Ray(Vector origin, Vector direction) {
		this.origin = origin;
		RGB = new Vector();
		setVector(direction);
	}

	public double getRayPitch() {
		return ray_pitch;
	}

	public double getRayYaw() {
		return ray_yaw;
	}

	public Vector getRGB() {
		return RGB;
	}

	public Color getColor() {
		int range=255;
		return new Color((int) map(RGB.getX(), 0, range, 0, 255), (int) map(RGB.getY(), 0, range, 0, 255), (int) map(RGB.getZ(), 0, range, 0, 255));
	}

	public void calculateDirection(Vector direction, Vector pitch_axis, Vector yaw_axis) {
		setVector((direction.vectorAdd(yaw_axis.constMult(ray_yaw)).vectorAdd(pitch_axis.constMult(ray_pitch)))
				.getUnit());
	}

	public void calculateRay(ArrayList<Obj> objects, ArrayList<Light> lights, int reflections) {
		// resetting Ray
		RGB.setXYZ(0, 0, 0);

		// calculating closest object and setting the distance
		Obj closest = getClosest(origin, objects);
		if (closest == null)
			return;

		// getting normal
		Vector intersection = origin.vectorAdd(constMult(getLength()));
		Vector normal = closest.getNormal(intersection);
		if (calcCosAngle(normal) > 0)
			normal = normal.constMult(-1);

		RGB.setVector(RGB.vectorAdd(closest.getRGB()));
		double light_normal_angle, light_strength = 0; // ambient lighting
		Vector light_to_obj;
		for (int i = 0; i < lights.size(); i++) {
			light_to_obj = intersection.vectorSub(lights.get(i).getPosition()).getUnit();
			// if in blocked by another shape set in shadow
			if (closest != light_to_obj.getClosest(lights.get(i).getPosition(), objects))
				continue;

			light_normal_angle = -normal.calcCosAngle(light_to_obj);
			if (light_normal_angle > 0) {
				light_strength += light_normal_angle * lights.get(i).getLuminosity()
						/ (light_to_obj.getLength() * light_to_obj.getLength());
			}
		}
		RGB.setVector(RGB.constMult(light_strength));

		// calculating reflections
		if (reflections > 0 && closest.getReflection() > 0) {
			// rotating by 180
			Ray reflection_ray = new Ray(intersection.vectorAdd(normal.constMult(0.000001)),
					vectorSub(normal.constMult(2 * dotProduct(normal))));
			reflection_ray.calculateRay(objects, lights, --reflections);
			RGB.setVector(RGB.constMult(1 - closest.getReflection()).vectorAdd(reflection_ray.getRGB().constMult(closest.getReflection())));
		}
	}

	private double map(double x, double in_min, double in_max, double out_min, double out_max) {
		double fraction = (x - in_min) / (in_max - in_min);
		if (fraction < 0)
			fraction = 0;
		else if (fraction > 1)
			fraction = 1;
		return (fraction * (out_max - out_min) + out_min);
	}
}
