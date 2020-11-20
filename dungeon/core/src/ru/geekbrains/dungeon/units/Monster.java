package ru.geekbrains.dungeon.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import ru.geekbrains.dungeon.BattleCalc;
import ru.geekbrains.dungeon.GameController;

public class Monster extends Unit {
    private float aiBrainsImplseTime;
    private Unit target;
    private int rndTgX, rndTgY;

    public Monster(TextureAtlas atlas, GameController gc) {
        super(gc, 5, 2, 10);
        this.texture = atlas.findRegion("monster");
        this.textureHp = atlas.findRegion("hp");
        this.hp = -1;
    }

    public void activate(int cellX, int cellY) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.targetX = cellX;
        this.targetY = cellY;
        this.hpMax = 10;
        this.hp = hpMax;
        this.target = gc.getUnitController().getHero();
        this.rndTgX = 18;
        this.rndTgY = 10;
    }

    public void update(float dt) {
        super.update(dt);
        if (canIMakeAction()) {
            if (isStayStill()) {
                aiBrainsImplseTime += dt;
            }
            if (aiBrainsImplseTime > 0.4f) {
                aiBrainsImplseTime = 0.0f;

                if ((int) Math.sqrt((cellX - target.getCellX()) * (cellX - target.getCellX())
                        + (cellY - target.getCellY()) * (cellY - target.getCellY())) <= 5) {

                    if (canIAttackThisTarget(target)) {
                        attack(target);
                    } else {
                        tryToMove();
                    }
                } else {
                    if (cellX == rndTgX && cellY == rndTgY) {
                        do {
                            rndTgX = MathUtils.random(0, 19);
                            rndTgY = MathUtils.random(0, 11);
                        } while (!gc.getGameMap().isCellPassable(rndTgX, rndTgY));

                    }
                    tryToMove(rndTgX, rndTgY);
                }

            }

        }
    }

    public void tryToMove() {
        int bestX = -1, bestY = -1;
        float bestDst = 10000;
        for (int i = cellX - 1; i <= cellX + 1; i++) {
            for (int j = cellY - 1; j <= cellY + 1; j++) {
                if (Math.abs(cellX - i) + Math.abs(cellY - j) == 1 && gc.getGameMap().isCellPassable(i, j) && gc.getUnitController().isCellFree(i, j)) {
                    float dst = (float) Math.sqrt((i - target.getCellX()) * (i - target.getCellX()) + (j - target.getCellY()) * (j - target.getCellY()));
                    if (dst < bestDst) {
                        bestDst = dst;
                        bestX = i;
                        bestY = j;
                    }
                }
            }
        }
        goTo(bestX, bestY);
    }

    public void tryToMove(int tgX, int tgY) {
        int bestX = -1, bestY = -1;
        float bestDst = 10000;
        for (int i = cellX - 1; i <= cellX + 1; i++) {
            for (int j = cellY - 1; j <= cellY + 1; j++) {
                if (Math.abs(cellX - i) + Math.abs(cellY - j) == 1 && gc.getGameMap().isCellPassable(i, j) && gc.getUnitController().isCellFree(i, j)) {
                    float dst = (float) Math.sqrt((i - tgX) * (i - tgX) + (j - tgY) * (j - tgY));
                    if (dst < bestDst) {
                        bestDst = dst;
                        bestX = i;
                        bestY = j;
                    }
                }
            }
        }
        goTo(bestX, bestY);
    }
}
