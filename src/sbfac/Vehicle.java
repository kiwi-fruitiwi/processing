package sbfac;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

/**
 * Hello Cody!
 * omg, why does Vehicle extend pApplet? there must be a better way to use random and sqrt
 */
public class Vehicle {
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	protected float max_speed;
	protected float max_force;
	protected int r;
	protected int ACC_VECTOR_SCALE;


	public Vehicle(PApplet self, int x, int y) {
		pos = new PVector(x, y);
		vel = new PVector(self.random(-1, 1), self.random(-1, 1)).mult(0.4f);
		acc = new PVector(0, 0);

		max_speed = self.random(4f, 6f);
		max_force = self.random(0.02f, 0.04f);
		r = 24;
		ACC_VECTOR_SCALE = 1000;
	}

	public PVector getPos() {
		return pos;
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
		show_acc_vector(self);

		self.pushMatrix();
		self.translate(pos.x, pos.y);
		self.rotate(vel.heading());

		// this is where we draw our object. we're going to try for a 9S Hacking Bot
		// https://puu.sh/I3E19/9d32002c25.png

		/*
			If this project is in P3D, the strokes will be drawn over everything else, resulting in a mess of black.
			You can counter this with hint(DISABLE_OPTIMIZED_STROKE) but this dramatically reduces performance. In
			normal 3D applications, however, shapes are drawn with faces of triangles and quads, so this is a non-issue.
		 */

		float T = 0.4f; // how far away is the tip away from the origin?
		float C = 0.2f; // what is the radius of the inner circle?
		float B = 0.3f; // how far away is the butt away from the origin?

		self.fill(0, 0, 100, 75);
		self.stroke(0, 0, 0, 50);
		self.strokeWeight(1);
		self.beginShape();
		self.vertex(r, 0); // front tip
		self.vertex(0, r * T); // top
		self.vertex(-r * T, 0); // butt
		self.vertex(0, -r * T); // bottom
		self.vertex(r, 0); // front tip
		self.endShape();

		self.fill(0, 0, 0, 90);
		self.circle(0, 0, r * C);
		self.stroke(0, 0, 0, 100);
		self.strokeWeight(1);
		self.line(0, 0, -r * T, 0); // line to the butt

		float x = (r * T) / (self.sqrt(3) + T);
		self.line(0, 0, x, self.sqrt(3) * x); // line to the top 120 degrees
		self.line(0, 0, x, -self.sqrt(3) * x); // line to the bottom 120 degrees

		// two little squares in the back
		self.rectMode(PConstants.CENTER);
		self.fill(0, 0, 100, 50);
		self.strokeWeight(1);
		self.square(r * -B, r * T, r * 0.2f);
		self.square(r * -B, -r * T, r * 0.2f);

		self.popMatrix();
	}

	protected void show_acc_vector(PApplet app) {
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.stroke(200, 100, 100, 50);
		app.strokeWeight(2);
		app.rotate(acc.heading());
		float r = acc.mag() * ACC_VECTOR_SCALE;
		app.line(0, 0, r, 0); // main acceleration vector
		app.line(r, 0, r - 3, -3); // bottom arrow half
		app.line(r, 0, r - 3, 3); // top arrow half
		app.popMatrix();
	}

	//

	/**
	 * Seek behavior for autonomous agents following Craig Reynolds's 1999 paper, SBFAC
	 *
	 * @param location The location of the object we are seeking
	 * @param arrive   If this is true, we will slow down as we arrive; if not we shoot right through
	 * @return a force that seeks our target's location with steering_force = desired_vel - current_vel
	 */
	public PVector seek(PApplet self, PVector location, boolean arrive) {
		final float DECELERATION_RADIUS = 200;

		// our desired velocity is to move at max speed directly toward the target
		PVector desired = PVector.sub(location, pos); // difference of positions gives us direction

		// are we within a set radius of our target? note that PVector.sub(location, pos).mag() gives us the distance
		// between us and our target
		float distance = desired.mag();
		if (arrive && distance < DECELERATION_RADIUS) {
			float m = self.map(distance, 0, 100, 0, max_speed);
			desired.setMag(m);
		} else {
			desired.setMag(max_speed);
		}

		// steering = desired velocity - current velocity to counteract our current heading
		// we want to convert this direction and magnitude into an acceleration vector
		PVector steering_force = PVector.sub(desired, vel);
		steering_force.limit(max_force);

		return steering_force;
	}

	// the opposite of seek. we are trying to avoid a target
	public PVector flee(PApplet self, PVector location) {
		return seek(self, location, false).mult(-1);
	}


	// When this Vehicle hits an edge, wrap around
	public void edge_wrap(PApplet self) {
		if (pos.x + r > self.width)
			pos.x = r;
		if (pos.x - r < 0)
			pos.x = self.width - r;
		if (pos.y + r > self.height)
			pos.y = r;
		if (pos.y - r < 0)
			pos.y = self.height - r;
	}

	// bounce off the edges by inverting velocity
	public void edge_bounce(PApplet self) {
		// right edge
		if (pos.x + r > self.width) {
			pos.x = self.width - r;
			vel.x *= -1;
		}

		// left edge
		if (pos.x - r < 0) {
			pos.x = r;
			vel.x *= -1;
		}

		// bottom edge
		if (pos.y + r > self.height) {
			pos.y = self.height - r;
			vel.y *= -1;
		}

		// top edge
		if (pos.y - r < 0) {
			pos.y = r;
			vel.y *= -1;
		}
	}
}