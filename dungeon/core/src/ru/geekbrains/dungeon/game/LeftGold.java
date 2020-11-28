package ru.geekbrains.dungeon.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Data;
import ru.geekbrains.dungeon.helpers.Assets;
import ru.geekbrains.dungeon.helpers.Poolable;

@Data
public class LeftGold implements Poolable {
    GameController gc;
    TextureRegion texture;
    private int cellX, cellY, gold;

    public LeftGold(GameController gc) {
        this.gc = gc;
        this.texture = Assets.getInstance().getAtlas().findRegion("projectile");
        this.cellX = -1;
        this.cellY = -1;
    }

    @Override
    public boolean isActive() {
        return gold >0;
    }

    public void activate(int cellX, int cellY, int gold) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.gold = gold;
    }

    public void update(float dt) {

    }

    public void render(SpriteBatch batch) {
        float posX = cellX * GameMap.CELL_SIZE;
        float posY = cellY * GameMap.CELL_SIZE;
        if (isActive()) batch.draw(texture, posX + 22, posY + 22);
    }
}
