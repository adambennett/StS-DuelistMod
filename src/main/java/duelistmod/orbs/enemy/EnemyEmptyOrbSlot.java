package duelistmod.orbs.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyOrb;

public class EnemyEmptyOrbSlot extends AbstractEnemyOrb {
    public static final String[] DESC;
    private static final OrbStrings orbString;

    public EnemyEmptyOrbSlot(final float x, final float y) {
        this.angle = MathUtils.random(360.0f);
        this.ID = "Empty";
        this.name = EnemyEmptyOrbSlot.orbString.NAME;
        this.evokeAmount = 0;
        this.cX = x;
        this.cY = y;
        this.updateDescription();
        this.channelAnimTimer = 0.5f;
        originalEvoke = this.baseEvokeAmount;
        originalPassive = this.basePassiveAmount;
    }

    public EnemyEmptyOrbSlot() {
        this.angle = MathUtils.random(360.0f);
        this.name = EnemyEmptyOrbSlot.orbString.NAME;
        this.evokeAmount = 0;
        this.cX = AbstractEnemyDuelist.enemyDuelist.drawX + AbstractEnemyDuelist.enemyDuelist.hb_x;
        this.cY = AbstractEnemyDuelist.enemyDuelist.drawY + AbstractEnemyDuelist.enemyDuelist.hb_y + AbstractEnemyDuelist.enemyDuelist.hb_h / 2.0f;
        originalEvoke = this.baseEvokeAmount;
        originalPassive = this.basePassiveAmount;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = EnemyEmptyOrbSlot.DESC[0];
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 10.0f;
    }

    public void render(final SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(ImageMaster.ORB_SLOT_2, this.cX - 48.0f - this.bobEffect.y / 8.0f, this.cY - 48.0f + this.bobEffect.y / 8.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, false, false);
        sb.draw(ImageMaster.ORB_SLOT_1, this.cX - 48.0f + this.bobEffect.y / 8.0f, this.cY - 48.0f - this.bobEffect.y / 8.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        this.hb.render(sb);
    }

    public AbstractOrb makeCopy() {
        return new EnemyEmptyOrbSlot();
    }

    @Override
    public void checkFocus()
    {
        if (this.owner != null && this.owner.hasPower(FocusPower.POWER_ID)) {
            if ((this.owner.getPower(FocusPower.POWER_ID).amount > 0) || (this.owner.getPower(FocusPower.POWER_ID).amount + this.originalPassive > 0)) {
                this.basePassiveAmount = this.originalPassive + this.owner.getPower(FocusPower.POWER_ID).amount;
            } else {
                this.basePassiveAmount = 0;
            }
        } else {
            this.basePassiveAmount = this.originalPassive;
            this.baseEvokeAmount = this.originalEvoke;
        }
        applyFocus();
        updateDescription();
    }


    @Override
    public void applyFocus() {
        this.passiveAmount = this.basePassiveAmount;
        this.evokeAmount = this.baseEvokeAmount;
    }

    static {
        orbString = CardCrawlGame.languagePack.getOrbString("Empty");
        DESC = EnemyEmptyOrbSlot.orbString.DESCRIPTION;
    }
}
