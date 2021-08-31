package quadtree;

import processing.core.PApplet;

/**
 * A rectangle has an (x,y) top left point along with a width and height. You
 * can ask if it intersects with other rectangles!
 */
public class Rectangle {
	float x, y, w, h;

	public Rectangle(PApplet self, float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/**
	 * Returns true if this Rectangle contains a Point.
	 */
	public boolean contains(Point p) {
		return ((p.x >= x) && (p.x <= x+w) &&
				(p.y >= y) && (p.y <= y+w));

		// return (p.x >= self.x) and (p.x <= self.x+self.w) and
		// (p.y >= self.y) and (p.y <= self.y+self.h)
	}

	/**
	 * Returns true if this rectangle intersects with another
	 */
	public boolean intersects(Rectangle other) {
		/*
			There are eight cases of intersection, but only four cases where
			intersection is false. Thus, we NOT the latter cases to reduce
			computation.
		 */
		return !((x > other.x+other.w) ||
				(other.x > x+w) ||
				(y > other.y+other.h) ||
				(other.y > y+h));
	}
}
