package duelistmod.relics.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;

public class RingOfRecoil extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("RingOfRecoil");
    public static final String IMG = DuelistMod.makeRelicPath("RingOfRecoil.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("RingOfRecoilOutline.png");

    private int procsRemaining = 0;

    public RingOfRecoil() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public boolean canSpawn() {
        boolean superCheck = super.canSpawn();
        if (!superCheck) return false;
        return Util.deckIs("Warrior Deck");
    }

    @Override
    public void atBattleStart() {
        procsRemaining = 2;
        this.grayscale = false;
        this.pulse = true;
        beginPulse();
    }

    @Override
    public void onVictory() {
        procsRemaining = 0;
        this.grayscale = false;
        this.pulse = false;
        stopPulse();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (procsRemaining <= 0 || info == null || info.type != DamageInfo.DamageType.NORMAL) return damageAmount;
        int block = AbstractDungeon.player.currentBlock;
        int unblocked = Math.max(0, damageAmount - block);
        if (unblocked > 0) {
            procsRemaining--;
            this.flash();
            AnyDuelist duelist = AnyDuelist.from(this);
            duelist.applyPowerToSelf(new ThornsPower(duelist.creature(), 2));
            if (procsRemaining <= 0) {
                this.pulse = false;
                this.grayscale = true;
                stopPulse();
            }
        }
        return damageAmount;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
