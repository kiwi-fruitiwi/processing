package quadtree;

import processing.core.PApplet;
import sbfac.Vehicle;

import java.util.List;

public class Main extends PApplet {
	// generate points, draw points on mouse drag
	// make quadtree
	// .insert(), .subdivide()
	// .query()
	// query box with mouse hover
	// add collision detection » point.intersects
	// use quadtree in detection

	List<Point> points;

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


	}

	@Override
	public void draw() {
		background(210, 80, 40);


	}
}