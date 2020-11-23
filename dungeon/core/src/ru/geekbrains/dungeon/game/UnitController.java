package ru.geekbrains.dungeon.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class UnitController {
    private GameController gc;
    private MonsterController monsterController;
    private Hero hero;
    private Unit currentUnit;
    private int index;
    private List<Unit> allUnits;
    private int roundNo;

    public MonsterController getMonsterController() {
        return monsterController;
    }

    public Hero getHero() {
        return hero;
    }

    public boolean isItMyTurn(Unit unit) {
        return currentUnit == unit;
    }

    public boolean isCellFree(int cellX, int cellY) {
        for (Unit u : allUnits) {
            if (u.getCellX() == cellX && u.getCellY() == cellY) {
                return false;
            }
        }
        return true;
    }

    public UnitController(GameController gc) {
        this.gc = gc;
        this.hero = new Hero(gc);
        this.monsterController = new MonsterController(gc);
    }

    public void init() {
        this.monsterController.activate(5, 5);
        this.monsterController.activate(9, 5);
        this.index = -1;
        this.allUnits = new ArrayList<>();
        this.allUnits.add(hero);
        this.allUnits.addAll(monsterController.getActiveList());
        this.nextTurn();
    }

    public void nextTurn() {
        index++;
        if (index >= allUnits.size()) {
            index = 0;
        }
        currentUnit = allUnits.get(index);
        currentUnit.startTurn();
        if (currentUnit.equals(hero) /*&& allUnits.size() != 1*/) {
            System.out.println(++roundNo);
            if (roundNo % 3 == 0) launchAnotherMonster();
        }

    }

    public void render(SpriteBatch batch, BitmapFont font18) {
        hero.render(batch, font18);
        monsterController.render(batch, font18);
    }

    public void update(float dt) {

        hero.update(dt);
        monsterController.update(dt);

        if (!currentUnit.isActive() || currentUnit.getTurns() == 0) {
            nextTurn();
        }
    }

    public void removeUnitAfterDeath(Unit unit) {
        int unitIndex = allUnits.indexOf(unit);
        allUnits.remove(unit);
        if (unitIndex <= index) {
            index--;
        }
    }

    public void launchAnotherMonster(){
        int freshMonX = -1, freshMonY = -1;
        do {
            freshMonX = MathUtils.random(0, gc.getGameMap().getCellsX() - 1);
            freshMonY = MathUtils.random(0, gc.getGameMap().getCellsY() - 1);
        } while (!isCellFree(freshMonX, freshMonY));
        monsterController.activate(freshMonX, freshMonY);
        int tmp = monsterController.getActiveList().size();
        this.allUnits.add(monsterController.getActiveList().get(tmp-1));
    }
}
