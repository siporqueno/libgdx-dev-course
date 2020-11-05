package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Tank tank;
    private Target target;

    // Домашнее задание:
    // 1. Не дайте танку уехать за пределы экрана
    // 2. * Попробуйте добавить мишень и попадание по ней

    @Override
    public void create() {
        batch = new SpriteBatch();
        tank = new Tank();
        target = new Target();
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        tank.render(batch);
        if (target.isActive()) target.render(batch);
        batch.end();
    }

    public void update(float dt) {
        tank.update(dt);
        target.update(dt);
//        if (Gdx.input.justTouched()) {
//            System.out.println(Gdx.input.getX());
//            System.out.println(Gdx.graphics.getHeight() - Gdx.input.getY());
//        }
        checkIfTargetIsHit();
    }

    public void checkIfTargetIsHit() {
        Projectile p = tank.getProjectile();
        if (p.isActive() && target.isActive()) if (target.circle.contains(p.getX(), p.getY())) target.deactivate();
    }

    ;

    @Override
    public void dispose() {
        batch.dispose();
        tank.dispose();
        target.dispose();
    }
}
