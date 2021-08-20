package sbfac;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Vehicle extends PApplet {
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	protected float max_speed;
	protected float max_force;
	protected int r;
	protected int ACC_VECTOR_SCALE;


	public Vehicle(int x, int y) {
		pos = new PVector(x, y);
		vel = new PVector(random(-1, 1), random(-1, 1)).mult(0.05f);
		acc = new PVector(0, 0);

		max_speed = random(3f, 5f);
		max_force = random(0.01f, 0.05f);
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

		float x = (r*T)/(sqrt(3)+T);
		self.line(0, 0, x, sqrt(3) *x); // line to the top 120 degrees
		self.line(0, 0, x, -sqrt(3) *x); // line to the bottom 120 degrees

        // two little squares in the back
		self.rectMode(PConstants.CENTER);
		self.fill(0, 0, 100, 50);
		self.strokeWeight(1);
		self.square(r*-B, r*T, r*0.2f);
		self.square(r*-B, -r*T, r*0.2f);

		self.popMatrix();
	}

	protected void show_acc_vector(PApplet app) {
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.stroke(200, 100, 100, 50);
		app.strokeWeight(1);
		app.rotate(acc.heading());
		float r = acc.mag()*ACC_VECTOR_SCALE;
		app.line(0, 0, r, 0); // main acceleration vector
		app.line(r, 0, r-3, -3); // bottom arrow half
		app.line(r, 0, r-3, 3); // top arrow half
		app.popMatrix();
	}

	// Seek our a target's location with steering = desired_vel - current_vel
	public PVector seek(PVector location) {
		// our desired velocity is to move at max speed directly toward the target
		PVector desired = PVector.sub(location, pos); // difference of positions gives us direction
		desired.setMag(max_speed);

		// steering = desired velocity - current velocity to counteract our current heading
		// we want to convert this direction and magnitude into an acceleration vector
		desired.sub(vel);
		desired.limit(max_force);

		return desired;
	}

	// the opposite of seek. we are trying to avoid a target
	public PVector flee(PVector location) {
		return seek(location).mult(-1);
	}


	// When this Vehicle hits an edge, wrap around
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

	// bounce off the edges by inverting velocity
	public void edge_bounce(PApplet self) {
		// right edge
		if(pos.x + r > self.width) {
			pos.x = self.width - r;
			vel.x *= -1;
		}

		// left edge
		if(pos.x - r < 0) {
			pos.x = r;
			vel.x *= -1;
		}

		// bottom edge
		if(pos.y + r > self.height) {
			pos.y = self.height - r;
			vel.y *= -1;
		}

		// top edge
		if(pos.y - r < 0) {
			pos.y = r;
			vel.y *= -1;
		}
	}
}