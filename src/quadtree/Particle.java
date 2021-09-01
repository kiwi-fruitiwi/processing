package quadtree;

import processing.core.PApplet;

/**
 * A particle has (x,y) coordinates and can jitter around the canvas with its
 * own show / render method.
 */
public class Particle {
	float x, y;
	int r;

	public Particle(PApplet self, float x, float y) {
		this.x = x;
		this.y = y;

		this.r = 4;
	}

	public void move(PApplet self) {
		x += self.random(-1, 1);
		y += self.random(-1, 1);
	}

	public void render(PApplet self) {
		self.noStroke();
		self.fill(0, 0, 100);
		self.circle(x, y, r*2);
	}

	/**
	  When a particle reaches 0, height, 0, or width, wrap around.
	 */
	public void edge_wrap(PApplet self) {
		// I think we want everything to wrap when the center of the particle
		// reaches a canvas boundary
		if (x > self.width)
			x = 0;
		else if (x < 0)
			x = self.width;

		if (y > self.height)
			y = 0;
		else if (y < 0)
			y = self.height;
	}

	/**
	 * Let's make Particles bounce off canvas edges.
	 */
	public void edge_bounce(PApplet self) {
		// TODO this actually requires velocity and Mover methods
		//     apply_force, update, PVector representation
		if (x + r >= self.width) {
			x = self.width - r;

		}
	}
}