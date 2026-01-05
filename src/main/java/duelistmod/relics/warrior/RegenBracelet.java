package duelistmod.relics.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;

public class RegenBracelet extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("RegenBracelet");
    public static final String IMG = DuelistMod.makeRelicPath("RegenBracelet.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("RegenBraceletOutline.png");

    private boolean triggeredThisCombat = false;

    public RegenBracelet() {
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
        triggeredThisCombat = false;
        this.grayscale = false;
        this.pulse = true;
        beginPulse();
    }

    @Override
    public void onVictory() {
        this.pulse = false;
        stopPulse();
        triggeredThisCombat = false;
        this.grayscale = false;
    }

    @Override
    public void onApplyPower(AbstractPower power) {
        if (triggeredThisCombat) return;

        AnyDuelist duelist = AnyDuelist.from(this);
        AbstractCreature target = power.owner;
        if (target == duelist.creature() && power instanceof VigorPower && power.amount > 0) {
            triggeredThisCombat = true;
            this.pulse = false;
            this.grayscale = true;
            stopPulse();
            this.flash();
            this.addToBot(new HealAction(duelist.creature(), duelist.creature(), 5));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
