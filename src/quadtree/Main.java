package quadtree;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
		noStroke();

		points = new ArrayList<>();

		IntStream.range(0, 400).forEach(i -> points.add(
				new Point(random(width), random(height))
		));

	}

	@Override
	public void draw() {
		background(210, 80, 40);

		for (Point point : points) {
			circle(point.x, point.y, 10);
		}
	}
}