package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

public class Target {
    private Texture texture;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float speed;
    private float scale;
    private boolean active;
    Circle circle;

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public Target() {
        this.texture = new Texture("badlogic.jpg");
        this.y = 600.0f;
        this.x = 0.0f;
        this.scale = 0.5f;
        this.speed = 100.0f;
        this.vx = speed * MathUtils.cos(0);
        this.circle = new Circle(x, y, scale * (texture.getWidth() + texture.getHeight()) / 4.0f);
        this.active = true;

    }

   /* public void shoot(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.vx = speed * MathUtils.cosDeg(angle);
        this.vy = speed * MathUtils.sinDeg(angle);
        this.active = true;
    }*/

    public void update(float dt) {
        x += vx * dt;
//        y += vy * dt;
        /*if (x < 0 || x > 1280 || y < 0 || y > 720) {
            deactivate();
        }*/

        if (x > Gdx.graphics.getWidth() && vx > 0) vx = -vx;
        if (x < 0 && vx < 0) vx = -vx;

        circle.setPosition(x, y);
    }

    public void render(SpriteBatch batch) {
        if (this.active)
            batch.draw(texture, x - 128, y - 128, 128, 128, 256, 256, scale, scale, 0, 0, 0, 256, 256, false, false);
    }

    public void dispose() {
        texture.dispose();
    }
}
