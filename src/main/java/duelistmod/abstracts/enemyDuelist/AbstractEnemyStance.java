package duelistmod.abstracts.enemyDuelist;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.PowerTip;
import duelistmod.abstracts.DuelistStance;
import duelistmod.stances.enemy.EnemyCalm;
import duelistmod.stances.enemy.EnemyDivinity;
import duelistmod.stances.enemy.EnemyNeutralStance;
import duelistmod.stances.enemy.EnemyRealWrath;
import duelistmod.stances.enemy.EnemyWrath;

import java.util.ArrayList;

public abstract class AbstractEnemyStance extends DuelistStance {

    public String name;
    public String description;
    protected ArrayList<PowerTip> tips;
    protected DuelistStance base;
    protected Color c;
    protected static final int W = 512;
    protected Texture img;
    protected float angle;
    protected float particleTimer;
    protected float particleTimer2;

    public AbstractEnemyStance() {
        this(null);
    }

    public AbstractEnemyStance(DuelistStance base) {
        this.tips = new ArrayList<>();
        this.c = Color.WHITE.cpy();
        this.img = null;
        this.particleTimer = 0.0f;
        this.particleTimer2 = 0.0f;
        this.base = base;
    }

    public abstract void updateDescription();

    public void atStartOfTurn() {
    }

    public void onEndOfTurn() {
    }

    public void onEnterStance() {
    }

    public void onExitStance() {
    }

    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        return damage;
    }

    public float atDamageGive(final float damage, final DamageInfo.DamageType type, final AbstractCard card) {
        return this.atDamageGive(damage, type);
    }

    public float atDamageReceive(final float damage, final DamageInfo.DamageType damageType) {
        return damage;
    }

    public void update() {
        this.updateAnimation();
    }

    public void updateAnimation() {
    }

    public void render(final SpriteBatch sb) {
        if (this.img != null) {
            sb.setColor(this.c);
            sb.setBlendFunction(770, 1);
            sb.draw(this.img, AbstractEnemyDuelist.enemyDuelist.drawX - 256.0f + AbstractEnemyDuelist.enemyDuelist.animX, AbstractEnemyDuelist.enemyDuelist.drawY - 256.0f + AbstractEnemyDuelist.enemyDuelist.animY + AbstractEnemyDuelist.enemyDuelist.hb_h / 2.0f, 256.0f, 256.0f, 512.0f, 512.0f, Settings.scale, Settings.scale, -this.angle, 0, 0, 512, 512, false, false);
            sb.setBlendFunction(770, 771);
        }
    }

    public void stopIdleSfx() {
    }

    public static AbstractEnemyStance getStanceFromName(final String name) {
        if (name.equals("Calm")) {
            return new EnemyCalm();
        }
        if (name.equals("Wrath")) {
            return new EnemyWrath();
        }
        if (name.equals("Real Wrath")) {
            return new EnemyRealWrath();
        }
        if (name.equals("Divinity")) {
            return new EnemyDivinity();
        }
        if (name.equals("Neutral")) {
            return new EnemyNeutralStance();
        }
        return null;
    }

}
