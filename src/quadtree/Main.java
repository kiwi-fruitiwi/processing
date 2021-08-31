package quadtree;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This is a Java port of my Python port of Daniel Shiffman's Quadtree
 * Collisions Coding Challenge #98.3 https://www.youtube.com/watch?v=z0YFFg_nBjw
 *
 * @author Kiwi
 * @date 2021.08.31
 */
public class Main extends PApplet {
	/*
	  make particles, .move(), .render()
	  generate particles, draw points on mouse drag
	  make Rectangle, Point
	  make Quadtree
	  .insert(), .subdivide()

	 TODO .edge_wrap() or .edge_bounce()
	 TODO .query()
	 TODO query box with mouse hover
	 TODO add collision detection » point.intersects
	 TODO use quadtree in collision detection
	*/

	List<Particle> particles;
	List<Point> points;
	Quadtree qt;

	public static void main(String[] args) {
		PApplet.main(new String[] {Main.class.getName()});
	}

	@Override
	public void settings() {
		size(1000, 800);
	}

	@Override
	public void setup() {
		colorMode(HSB, 360f, 100f, 100f, 100f);
		frameRate(144);
		noStroke();

		particles = new ArrayList<>();

		IntStream.range(0, 2000).forEach(i -> particles.add(
				new Particle(this, random(width), random(height))
		));

	}

	@Override
	public void draw() {
		background(210, 80, 40);

		for (Particle particle : particles) {
			particle.move(this);
		}

		for (Particle particle : particles) {
			particle.render(this);
		}

		// populate our quadtree and display it
		points = new ArrayList<>();
		qt = new Quadtree(this, new Rectangle(this, 0, 0, width, height), 4);

		for (Particle particle : particles) {
			Point p = new Point(this, particle.x, particle.y);
			points.add(p);
			qt.insert(this, p);
		}

		qt.show(this);
		display_FPS();
		println(qt.count(this));
	}

	@Override
	public void mouseDragged() {
		particles.add(new Particle(this, mouseX, mouseY));
	}

	private void display_FPS() {
		PFont mono = createFont("data/terminus.ttf", 24);
		textFont(mono);
		String s = String.format("FPS: %.0f", frameRate);
		int pad = 5;
		fill(0, 0, 100, 50);
		rect(12-pad, 24+pad, textWidth(s)+10, -25, 3);
		fill(0, 0, 100, 100);
		text(s, 12, 24);
	}
}