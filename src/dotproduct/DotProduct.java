package dotproduct;

import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.core.PVector;

public class DotProduct extends PApplet {
    PVector a, b; // vector b is the base

    public static void main(String[] args) {
        PApplet.main(new String[]{DotProduct.class.getName()});
    }

    @Override
    public void settings() {
        size(640, 360);
    }

    @Override
    public void setup() {
        rectMode(RADIUS);
        colorMode(HSB, 360f, 100f, 100f, 100f);

        b = new PVector(10, 7).mult(10);
    }


    /* find the scalar projection of two vectors, a and b */
    float scalarProjection(@NotNull PVector a, @NotNull PVector b) {
        PVector copy = b.copy().normalize();
        return a.dot(copy);
    }

    /**
     * the scalar projection is a scalar value. to see a vector projection,
     * you can take the vector b, make a copy of it, and set its magnitude to
     * the scalar projection!
     */
    PVector vectorProjection(PVector a, PVector b) {
        PVector copy = b.copy().normalize();
        copy.mult(scalarProjection(a, b));
        return copy;
    }

    @Override
    public void draw() {
        background(234, 34, 24);
        strokeWeight(3);
        stroke(0, 0, 70);

        PVector origin = new PVector(width/2, height/2);
        PVector mouse = new PVector(mouseX, mouseY);

        // set vector 'a' to where our mouse cursor is
        a = PVector.sub(mouse, origin);

        // draw an arrow from our origin to vectors a and b
        drawArrow(origin, new PVector(origin.x+a.x, origin.y+a.y));
        drawArrow(origin, new PVector(origin.x+b.x, origin.y+b.y));

        PVector v = vectorProjection(a, b);
        stroke(201, 96, 83, 75); // blue
        strokeWeight(5);
        drawArrow(origin, new PVector(origin.x+v.x, origin.y+v.y));

        // draw the normal vector too
        strokeWeight(1);
        stroke(0, 0, 80);

        line(origin.x+v.x, origin.y+v.y, mouseX, mouseY);
    }

    /* draws an arrow from our start vector to our end vector */
    void drawArrow(PVector start, PVector end) {
        PVector diff = PVector.sub(end, start);
        pushMatrix();
        translate(start.x, start.y);
        rotate(diff.heading());

        float r = diff.mag();
        line(0, 0, r, 0);
        line(r, 0, r-3, -3);
        line(r, 0, r-3, 3);
        popMatrix();
    }

    @Override
    public void mousePressed() {
        System.out.println(mouseX);
    }
}
