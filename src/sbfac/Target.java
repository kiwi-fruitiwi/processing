package sbfac;

import processing.core.PApplet;

public class Target extends Vehicle {
	public Target(int x, int y) {
		super(x, y);
		max_speed = 18;
		max_force = 15;
		ACC_VECTOR_SCALE = 250;
		r = 10;
	}

	public void show(PApplet app) {
		app.fill(205, 100, 100, 50);
		app.stroke(0, 0, 100);

		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(vel.heading());
		app.circle(0f, 0f, r*2);
		app.popMatrix();

		show_acc_vector(app);
	}
}
