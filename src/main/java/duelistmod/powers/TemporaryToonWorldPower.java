package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Strings;

public class TemporaryToonWorldPower extends DuelistPower {

    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("TemporaryToonWorldPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.TOON_WORLD_POWER);
    private final AnyDuelist duelist;

    public TemporaryToonWorldPower(final AbstractCreature owner, final AbstractCreature source, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.duelist = AnyDuelist.from(this);
        this.amount = turns;
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() {
    	if (this.duelist.creature().hasPower(ToonWorldPower.POWER_ID) || this.duelist.creature().hasPower(ToonKingdomPower.POWER_ID)) {
    		DuelistCard.removePower(this, this.duelist.creature());
    	}
    }
    
    @Override
    public void atStartOfTurn() {
        if (this.duelist.creature().hasPower(ToonWorldPower.POWER_ID) || this.duelist.creature().hasPower(ToonKingdomPower.POWER_ID)) {
            DuelistCard.removePower(this, this.duelist.creature());
        }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (this.duelist.creature().hasPower(ToonWorldPower.POWER_ID) || this.duelist.creature().hasPower(ToonKingdomPower.POWER_ID)) {
            DuelistCard.removePower(this, this.duelist.creature());
        }
    }

    @Override
	public void atEndOfTurn(final boolean isPlayer) {
        if (this.duelist.creature().hasPower(ToonWorldPower.POWER_ID) || this.duelist.creature().hasPower(ToonKingdomPower.POWER_ID)) {
            DuelistCard.removePower(this, this.duelist.creature());
            return;
        }
        this.amount--;
        if (this.amount <= 0) {
            DuelistCard.removePower(this, this.duelist.creature());
            return;
        }
        this.updateDescription();
	}
    
    @Override
    public void onInitialApplication() {
        this.duelist.glowCheck();
        if (this.duelist.creature().hasPower(ToonWorldPower.POWER_ID) || this.duelist.creature().hasPower(ToonKingdomPower.POWER_ID)) {
            DuelistCard.removePower(this, this.duelist.creature());
        }
    }

    @Override
	public void updateDescription() {
        this.description = this.amount == 1
                ? DESCRIPTIONS[0] + DESCRIPTIONS[1]
                : DESCRIPTIONS[0] + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
    }

}
