package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.enemy.EnemyLightning;
import duelistmod.variables.Tags;

public class LightningTricornPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("LightningTricornPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("LightningTricornPower.png");
    private int beastsDrawnThisTurn = 0;
    private boolean isActive = false;

	public LightningTricornPower(AbstractCreature owner, AbstractCreature source, int beastsCheck, int lightning) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = beastsCheck;
        this.amount2 = lightning;
		updateDescription(); 
	}

    @Override
    public void atStartOfTurn() {
        this.beastsDrawnThisTurn = 0;
        this.isActive = true;
    }

    @Override
    public void onCardDrawn(AbstractCard drawn) {
        if (!this.isActive) return;

        if (drawn.hasTag(Tags.BEAST)) {
            beastsDrawnThisTurn++;
        }
        if (beastsDrawnThisTurn >= this.amount) {
            AnyDuelist duelist = AnyDuelist.from(this);
            AbstractOrb lightning = duelist.player() ? new Lightning() : duelist.getEnemy() != null ? new EnemyLightning() : null;
            if (lightning != null) {
                duelist.channel(lightning, this.amount2);
            }
            AbstractPower instance = duelist.getPower(LightningTricornPower.POWER_ID);
            DuelistCard.removePower(instance, duelist.creature());
        }
        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (!this.isActive) return;
        AnyDuelist duelist = AnyDuelist.from(this);
        AbstractPower instance = duelist.getPower(LightningTricornPower.POWER_ID);
        DuelistCard.removePower(instance, duelist.creature());
    }

	@Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[this.isActive ? 1 : 0];
        this.description += DESCRIPTIONS[2];
        int remaining = this.amount - beastsDrawnThisTurn;
        if (remaining <= 0) {
            this.description = DESCRIPTIONS[5] + this.amount2 + DESCRIPTIONS[6];
            return;
        }
        this.description += remaining;
        this.description += remaining == 1 ? DESCRIPTIONS[3] : DESCRIPTIONS[4];
        this.description += DESCRIPTIONS[5] + this.amount2 + DESCRIPTIONS[6];
	}
}
