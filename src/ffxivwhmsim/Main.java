package ffxivwhmsim;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PShape;

/**
 * I don't know why the python version keeps crashing, so I'm porting
 * over to java to see if the issue persists ; ;
 */
public class Main extends PApplet {
    PFont font;
    PImage benison, holy, regen, border;
    float cooldown;
    boolean benison_alpha_full, action;

    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    @Override
    public void settings() {
        size(640, 360, P2D);
        noSmooth();
    }

    @Override
    public void setup() {
        colorMode(HSB, 360f, 100f, 100f, 100f);
        ellipseMode(CENTER);
        imageMode(CENTER);
        rectMode(CENTER);

        font = createFont("Meiryo", 12);
        textFont(font);

        cooldown = 0;
//        benison_alpha_full = true;
        action = false;

        border = loadImage("ffxivwhmsim/icons/border.png");
        border.resize(76, 76);
        benison = loadImage("ffxivwhmsim/icons/divine benison.png");
        holy = loadImage("ffxivwhmsim/icons/holy.png");
        regen = loadImage("ffxivwhmsim/icons/regen.png");
    }

    @Override
    public void draw() {
        background(210, 100, 30, 100);

        draw_icon(benison, 100, width/2f, height/2f);
        draw_icon(holy, 100, width/2f + 68, height/2f);
        draw_icon(regen, 100, width/2f + 68 + 68, height/2f);

        fill(0, 0, 100, 100);
        text("Divine Benison, Holy, and Regen\n[Cody Berry, Lv.72 White Mage]",
                width/4f, height/4f);

        if (action) {
            float c = map(cooldown, 0, 100, -PI/2, 3*PI/2);
            if (c > 3*PI/2) {
//                benison_alpha_full = true;
            } else {
                noStroke();
                fill(0, 0, 20, 70);
                shape(make_donut(32, 32, 50), width/2f, height/2f);

                stroke(0, 0, 100, 100);
                strokeWeight(2);
                arc(width/2f, height/2f, 62, 64, c, 3*PI/2, PIE);

                noFill();
                // this should be an arc
                // ellipse(width/2f, height/2f, 62, 64);
                arc(width/2f, height/2f, 62, 64, c, 3*PI/2);
            }
        }
        cooldown += 0.6;
    }

    public void draw_icon(PImage img, float a, float x, float y) {
        tint(0, 0, 100, a);
        image(img, x, y);
        tint(0, 0, 100, 100);
        image(border, x, y+3);
    }

    public PShape make_donut(int outerRadius, int innerRadius, int steps) {
        PShape s = createShape();
        s.beginShape();

        // outer square
        s.vertex(-outerRadius,-outerRadius);
        s.vertex(outerRadius,-outerRadius);
        s.vertex(outerRadius,outerRadius);
        s.vertex(-outerRadius,outerRadius);

        // inner negative shape
        s.beginContour();
        for (float a = 0; a < TAU; a += TAU/steps)
            s.vertex(innerRadius*cos(-a), innerRadius*sin(-a));

        s.endContour();
        s.endShape();
        return s;
    }

    @Override
    public void mousePressed() {
        action = true;
        benison_alpha_full = false;
        cooldown = 0;
    }
}