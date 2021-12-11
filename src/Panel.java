
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Player player;
	private double fov;
	private ArrayList<Obj> objects;
	private ArrayList<Light> lights;
	private static final int width = 700, height = 450;

	Panel() {
		setPreferredSize(new Dimension(width, height));

		// arbitrary initial states to create scene
		fov = (3.14 * 0.25);
		player = new Player(new Vector(150, 150, 150), width , height , fov);
		player.setPitch(2.35);
		player.setYaw(-2.35);

		objects = new ArrayList<Obj>();
		createObjects();

		lights = new ArrayList<Light>();
		createLights();

		// creating and associating handlers
		setFocusable(true);
		requestFocusInWindow();
		KeyHandler key_handler = new KeyHandler();
		addKeyListener(key_handler);
		MouseHandler mouse_handler = new MouseHandler();
		addMouseListener(mouse_handler);
		addMouseMotionListener(mouse_handler);
		setVisible(true);
	}

	private class MouseHandler extends MouseAdapter {
		double initial_x, initial_y, initial_pitch, initial_yaw;

		@Override
		public void mousePressed(MouseEvent e) {
			initial_x = e.getX();
			initial_y = e.getY();
			initial_pitch = player.getPitch();
			initial_yaw = player.getYaw();
		}

		public void mouseDragged(MouseEvent e) {
			player.setPitch(initial_pitch - fov * (e.getY() - initial_y) / height);
			player.setYaw(initial_yaw + fov * (e.getX() - initial_x) / width);
			repaint();
		}
	}

	private class KeyHandler implements KeyListener {
		double speed = 2;

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == 'w') { // forward
				player.movAhead(speed);
			} else if (e.getKeyChar() == 's') // back
				player.movAhead(-speed);
			else if (e.getKeyChar() == 'd')
				player.movRight(speed);
			else if (e.getKeyChar() == 'a')
				player.movRight(-speed);
			else if (e.getKeyChar() == 'x')
				player.movX(speed);
			else if (e.getKeyChar() == 'y')
				player.movY(speed);
			else if (e.getKeyChar() == 'z')
				player.movZ(speed);

			repaint();
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
	
	private void createLights() {
		lights.add(new Light(new Vector(70, 70, 90), 20000));
		lights.add(new Light(new Vector(20, 20, 12), 3000));
	}

	private void createObjects() {
		// objects
		objects.add(new Polygon(new Vector(-1000, -1000, 0), new Vector(0, 1000, 0), new Vector(1000, 0, 0)));// floor
		objects.add(new Polygon(new Vector(20, 5, 5), new Vector(5, 30, 5), new Vector(5, 5, 53)));
		objects.add(new Cube(15, new Vector(22, 44, 54)));
		objects.add(new Sphere(14, new Vector(55, 33, 33)));

		objects.get(0).setRGB(new Vector(55, 55, 55));
		objects.get(1).setRGB(new Vector(44, 44, 88));
		objects.get(2).setRGB(new Vector(88, 22, 33));
		objects.get(3).setRGB(new Vector(88, 22, 33));
		
		objects.get(0).setReflection(0.4);
		objects.get(1).setReflection(0.2);
		objects.get(2).setReflection(0.8);
		objects.get(3).setReflection(0.8);
	}

	public void start() {
		while (true) {
			player.calculateRays(objects, lights);
			repaint();
		}
	}

	public void paintComponent(Graphics g) {
		long prev = System.nanoTime();
		player.calculateRays(objects, lights);
		int pixel_width = width / player.getRayNumX();
		int pixel_height = height / player.getRayNumY();

		//Canvas c;
		//c.getGraphics
		for (int y = 0; y < player.getRayNumY(); y++) {
			for (int x = 0; x < player.getRayNumX(); x++) {
				g.setColor(player.getRays().get(y).get(x).getColor());
				g.fillRect(x * pixel_width, y * pixel_height, pixel_width, pixel_height);
			}
		}

		long now = System.nanoTime();
		System.out.println(1000000000.0 / (now - prev));
	}
}