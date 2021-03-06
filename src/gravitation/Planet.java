package gravitation;

import processing.core.PApplet;
import processing.core.PVector;

public class Planet {
    PVector pos;
    PVector vel;
    PVector acc;
    float r; // this is our radius
    float mass;

    public Planet(PApplet app, int x, int y, int z, float mass) {
        pos = new PVector(x, y, z);

        float mag = app.random(2.5f, 10.5f);
        vel = PVector.random3D().setMag(mag);
        acc = new PVector(0, 0, 0);
        this.mass = mass;
        r = (float) Math.sqrt(mass)*5;
    }

    public void setVel(PApplet app, PVector vel) {
        this.vel = vel;
    }

    public void show(PApplet app) {
        app.fill(0, 0, 100, 20); // white
        app.noStroke();
        app.pushMatrix();
        app.translate(pos.x, pos.y, pos.z);
        app.sphere(r);
//        app.circle(pos.x, pos.y, r*2);
        app.popMatrix();
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
//        constrain(force.mag(), 10, 33);
        distance = constrain(distance, 20, 50);

        // universal gravitational constant
        float G = 0.8f;
        float strength = (G*mass*target.mass)/((float) Math.pow(distance, 2));
        force.setMag(strength);
        target.apply_force(app, force);
    }

    // copies the functionality of processing's constrain() function
    private float constrain(float subject, float low, float high) {
        if (subject < low)
            return low;
        else if (subject > high)
            return high;
        else return subject;
    }
}
