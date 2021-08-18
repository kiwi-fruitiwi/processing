package sbfac;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Thanks to our hero Abe Pazos at https://vimeo.com/channels/p5idea, who teaches us how to use Processing inside IDEA
 */
public class sbfac extends PApplet {
	List<Vehicle> vehicles;

	public static void main(String[] args) {
		PApplet.main(new String[]{sbfac.class.getName()});
	}

	@Override
	public void settings() {
		size(700, 600);
	}

	@Override
	public void setup() {
		rectMode(PConstants.RADIUS);
		colorMode(HSB, 360f, 100f, 100f, 100f);
		frameRate(144);
		vehicles = new ArrayList<>();
		repopulate();
	}

	@Override
	public void draw() {
		background(210, 80, 40);
		rect(mouseX, mouseY, 20, 20);

		PVector gravity = PVector.random2D();
		for(Vehicle v : vehicles) {
			v.show(this);
			v.update(this);
			v.edge_wrap(this);
			v.apply_force(gravity);
		}
	}

	public void repopulate() {
		for(int i=0; i < 100; i++) {
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			vehicles.add(new Vehicle(
							ThreadLocalRandom.current().nextInt(10,  width-10+1),
							ThreadLocalRandom.current().nextInt(10,  height-10+1)));
		}
	}

	@Override
	public void mousePressed() {
		System.out.println(mouseX);
	}
}