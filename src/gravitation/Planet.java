package gravitation;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Planet {
    PVector pos;
    PVector vel;
    PVector acc;
    float r; // this is our radius
    int mass;

    public Planet(PApplet app, int x, int y, int mass) {
        pos = new PVector(x , y);
        vel = PVector.random2D().mult(5);
        acc = new PVector(0, 0);
        this.mass = mass;
        r = (float) Math.sqrt(mass) * 2;
    }

    public void show(PApplet app) {
        app.fill(0, 0, 100, 20);
        app.circle(pos.x, pos.y, r*2);
    }

    public void update(PApplet app) {
        vel.add(acc);
        vel.limit(10);
        pos.add(vel);
        acc.set(0, 0);
    }

    public void apply_force(PApplet app, PVector force) {
        // F=ma, so a=F/m
        acc.add(PVector.div(force, mass));
    }

    public void attract(PApplet app, Planet target) {
        // this is the direction vector from this planet to its target
        PVector force = PVector.sub(pos, target.pos);
        float distance = force.mag();
        distance = constrain(distance, 10, 33);

        // universal gravitational constant
        float G = 1;
        float strength = (G*mass*target.mass)/((float) Math.pow(distance, 2));
        force.setMag(strength);
        target.apply_force(app, force);
    }

    // copies the functionality of processing's constrain function
    private float constrain(float subject, float low, float high) {
        if (subject < low)
            return low;
        else if (subject > high)
            return high;
        else return subject;
    }
}
