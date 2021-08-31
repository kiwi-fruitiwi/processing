package quadtree;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Quadtree {
    // the maximum number of points inside before we subdivide
    final int capacity;
    final Rectangle boundary;
    final List<Point> points;
    boolean divided; // have we called the subdivide method yet?

    Quadtree northwest;
    Quadtree northeast;
    Quadtree southwest;
    Quadtree southeast;

    public Quadtree(PApplet self, Rectangle boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>();
        divided = false;
    }

    /**
     * Inserts a point into this Quadtree if it's within our boundary.
     * @param self PApplet
     * @param p the Point we are inserting into our quadtree
     * @return true if insertion succeeds, false if p is not within our boundary
     */
    public boolean insert(PApplet self, Point p) {
        if (!boundary.contains(p))
            return false;

        if (points.size() < capacity) {
            points.add(p);
            return true;
        } else {
            if (!divided)
                subdivide(self);

            // make sure if a point is inserted in any quadrant, it's not
            // in any other wait... can we just return all of these without the
            // else statements?
            if (northwest.insert(self, p))
                return true;
            else if (northeast.insert(self, p))
                return true;
            else if (southwest.insert(self, p))
                return true;
            else return southeast.insert(self, p);
        }
    }

    public void subdivide(PApplet self) {
        float x = boundary.x;
        float y = boundary.y;
        float w = boundary.w;
        float h = boundary.h;

        // subdivide
        Rectangle nw = new Rectangle(self, x, y, w/2, h/2);
        Rectangle ne = new Rectangle(self, x+w/2, y, w/2, h/2);
        Rectangle sw = new Rectangle(self, x, y+h/2, w/2, h/2);
        Rectangle se = new Rectangle(self, x+w/2, y+h/2, w/2, h/2);

        northwest = new Quadtree(self, nw, capacity);
        northeast = new Quadtree(self, ne, capacity);
        southwest = new Quadtree(self, sw, capacity);
        southeast = new Quadtree(self, se, capacity);

        divided = true;
    }


    public int count(PApplet self) {
        if (divided)
            return points.size() +
                    northwest.count(self) +
                    northeast.count(self) +
                    southwest.count(self) +
                    southeast.count(self);
        else return points.size();

    }


    public void show(PApplet self) {
        self.stroke(0, 0, 100, 100);
        self.noFill();
        self.rect(boundary.x, boundary.y, boundary.w, boundary.h);

        if (divided) {
            northwest.show(self);
            northeast.show(self);
            southwest.show(self);
            southeast.show(self);
        }
    }
}
