package ru.geekbrains.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private ProjectileController projectileController;
    private int width;
    private int height;
    private Vector2 position;
    private float velocity;
    private float angle;
    private TextureRegion texture;
    private boolean doubleShot;

    public Hero(TextureAtlas atlas, ProjectileController projectileController) {
        this.position = new Vector2(100, 100);
        this.velocity = 150.0f;
        this.texture = atlas.findRegion("tank");
        this.projectileController = projectileController;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
    }

    public void update(float dt) {
        checkMovement(dt);
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) doubleShot = !doubleShot;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) fire();
    }

    public void checkMovement(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= velocity * dt;
            angle = 180.0f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += velocity * dt;
            angle = 0.0f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += velocity * dt;
            angle = 90.0f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= velocity * dt;
            angle = 270.0f;
        }

        if (position.x < width / 2.0f) position.x = width / 2.0f;
        if (position.x > GameMap.CELLS_X * 40 - width / 2.0f) position.x = GameMap.CELLS_X * 40 - width / 2.0f;
        if (position.y < 0.0f + height / 2.0f) position.y = 0.0f + height / 2.0f;
        if (position.y > Gdx.graphics.getHeight() - height / 2.0f)
            position.y = Gdx.graphics.getHeight() - height / 2.0f;
    }


    public void fire() {
        projectileController.activate(position.x, position.y, 200* MathUtils.cosDeg(angle), 200*MathUtils.sinDeg(angle));
        if (doubleShot) projectileController.activate(position.x-20*MathUtils.cosDeg(angle), position.y-20*MathUtils.sinDeg(angle), 200* MathUtils.cosDeg(angle), 200*MathUtils.sinDeg(angle));
    }

//    Так как в закомментированном методе нже делать не надо: while и другие бесконечные циклы отъедают много ресурсов
    /*public void fireWithTimer(float dt) {
        projectileController.activate(position.x, position.y, 200* MathUtils.cos(MathUtils.degRad*angle), 200*MathUtils.sin(MathUtils.degRad*angle));

        if (doubleShot) {
            float secondShotTimer = 0.0f;
            while (secondShotTimer < 20.0f/200.0f) secondShotTimer = secondShotTimer + dt;
            projectileController.activate(position.x, position.y, 200* MathUtils.cos(MathUtils.degRad*angle), 200*MathUtils.sin(MathUtils.degRad*angle));}
    }*/
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - width / 2.0f, position.y - height / 2.0f, width / 2.0f, height / 2.0f, width, height, 1, 1, angle);
    }
}

