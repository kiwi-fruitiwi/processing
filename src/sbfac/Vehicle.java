package sbfac;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import static java.lang.Math.sqrt;


public class Vehicle extends PApplet {
	private PVector pos;
	private PVector vel;
	private PVector acc;
	private final float max_speed;
	private final float max_force;
	private int r;
	private int ACC_VECTOR_SCALE;


	public Vehicle(int x, int y) {
		pos = new PVector(x, y);
		vel = new PVector(random(-1, 1), random(-1, 1));
		acc = new PVector(0, 0);

		max_speed = random(3f, 5f);
		max_force = random(0.02f, 0.03f);
		r = 20;
		ACC_VECTOR_SCALE = 2000;
	}

	// assume self.mass is 1, so we don't need to worry about mass
	public void apply_force(PVector force) {
		// F = ma.Since m = 1, F = a
		force.limit(max_force);
		acc.add(force);
	}

	// how do the position and velocity change with each frame?
	// don't forget to limit the velocity!
	public void update(PApplet self) {
		pos.add(vel);
		vel.add(acc);
		vel.limit(max_speed);
		acc = new PVector(0, 0);
	}

	public void show(PApplet self) {
		self.pushMatrix();
		self.translate(pos.x, pos.y);

		self.rotate(vel.heading());

		// this is where we draw our object. we're going to try for a 9S Hackbot
        // https://puu.sh/I3E19/9d32002c25.png

		float T = 0.4f; // how far away is the tip away from the origin?
		float C = 0.2f; // what is the radius of the inner circle?
		float B = 0.3f; // how far away is the butt away from the origin?

		self.fill(0, 0, 100, 75);
		self.stroke(0, 0, 0, 100);
		self.strokeWeight(1);
		self.beginShape();
		self.vertex(r, 0); // front tip
		self.vertex(0, r*T); // top
		self.vertex(-r*T, 0); // butt
		self.vertex(0, -r*T); // bottom
		self.vertex(r, 0); // front tip
		self.endShape();

		self.fill(0, 0, 0, 90);
		self.circle(0, 0, r*C);
		self.stroke(0, 0, 0, 100);
		self.strokeWeight(1);
		self.line(0, 0, -r*T, 0); // line to the butt

		float x = (float) ((r*T)/(sqrt(3)+T));
		self.line(0, 0, x, (float) sqrt(3)*x); // line to the top 120 degrees
		self.line(0, 0, x, (float) -sqrt(3)*x); // line to the bottom 120 degrees

        // two little squares in the back
		self.rectMode(PConstants.CENTER);
		self.fill(0, 0, 100, 50);
		self.strokeWeight(1);
		self.square(r*-B, r*T, r*0.2f);
		self.square(r*-B, -r*T, r*0.2f);

		self.popMatrix();
	}

	public void edge_wrap(PApplet self) {
		if(pos.x + r > self.width)
			pos.x = r;
		if(pos.x - r < 0)
			pos.x = self.width - r;

		if(pos.y + r > self.height)
			pos.y = r;
		if(pos.y - r < 0)
			pos.y = self.height - r;
	}
}