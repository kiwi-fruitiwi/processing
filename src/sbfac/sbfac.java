package sbfac;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Thanks to our hero Abe Pazos at https://vimeo.com/channels/p5idea, who teaches us how to use Processing inside IDEA
 */
public class sbfac extends PApplet {
	List<Vehicle> vehicles;
	PVector gravity;
	PVector mouse_acc;

	public static void main(String[] args) {
		PApplet.main(new String[]{sbfac.class.getName()});
	}

	@Override
	public void settings() {
		size(1000, 800);
	}

	@Override
	public void setup() {
		rectMode(PConstants.RADIUS);
		colorMode(HSB, 360f, 100f, 100f, 100f);
		frameRate(144);
		vehicles = new ArrayList<>();
		repopulate();

		gravity = new PVector(0, 1);
		mouse_acc = new PVector(0, 0);
	}

	@Override
	public void draw() {
		background(210, 80, 40);

//		mouse_attracts_vehicles();
		
	}

	private void mouse_attracts_vehicles() {
		for(Vehicle v : vehicles) {
			v.show(this);
			v.update(this);
			v.edge_bounce(this);

			mouse_acc = PVector.sub(new PVector(mouseX, mouseY), v.getPos());
			v.apply_force(mouse_acc);
		}
	}

	public void repopulate() {
		for(int i=0; i < 100; i++) {
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			vehicles.add(new Vehicle((int) random(10, width-10 +1), (int) random(10, height-10 +1)));
		}
	}

	// The apparent best way to get a random number that doesn't have seed problems
	// ThreadLocalRandom.current().nextInt(10,  width-10+1)

	@Override
	public void mousePressed() {
		System.out.println(mouseX);
	}
}