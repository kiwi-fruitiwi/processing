package sbfac;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Thanks to our hero Abe Pazos at https://vimeo.com/channels/p5idea, who
 * teaches us how to use Processing inside IDEA
 */
public class sbfac extends PApplet {
	// done: vehicle, target
	// done: arrows on acceleration vectors
	// done: seek and flee, +Target
	// TODO: pursue and evade
	// TODO: arrive
	// TODO: more complex behaviors that lead to 2D boids
	// TODO: 3D

	List<Vehicle> vehicles;
	PVector gravity;
	PVector mouse_acc;
	Target target;
	boolean seek; // is the behavior seek / pursue or flee / evade?

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
		seek = true;

		vehicles = new ArrayList<>();
		repopulate();

		gravity = new PVector(0, 1);
		mouse_acc = new PVector(0, 0);
		target = new Target(this, width / 2, height / 2);
	}

	@Override
	public void draw() {
		background(210, 80, 40);
//		mouse_attracts_vehicles();

		target.pos = new PVector(mouseX, mouseY);
		target.show(this, seek);
		target.update(this);

		for (Vehicle v : vehicles) {
			if (seek) {
				v.apply_force(v.seek(this, target.pos, true));
			} else {
				v.apply_force(v.flee(this, target.pos));
			}

			v.show(this);
			v.update(this);
			v.edge_wrap(this);
		}
	}

	private void mouse_attracts_vehicles() {
		PVector mouse_pos = new PVector(mouseX, mouseY);

		for (Vehicle v : vehicles) {
			v.show(this);
			v.update(this);
			v.edge_bounce(this);

			mouse_acc = PVector.sub(
					mouse_pos,
					v.getPos()).normalize().mult(0.01f);
			v.apply_force(mouse_acc);
		}
	}

	public void repopulate() {
		IntStream.range(0, 100).forEach(i -> vehicles.add(
				new Vehicle(
						this,
						(int) random(10, width - 10 + 1),
						(int) random(10, height - 10 + 1))));
	}

	// The apparent best way to get a random number that doesn't have seed
	// problems ThreadLocalRandom.current().nextInt(10,  width-10+1)

	@Override
	public void mouseDragged() {
//		vehicles.add(new Vehicle(this, mouseX, mouseY));
		seek = !seek;
	}
}