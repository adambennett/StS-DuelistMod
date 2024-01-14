package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Strings;

public class ToonWorldPower extends AbstractPower {
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ToonWorldPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.TOON_WORLD_POWER);
    private final AnyDuelist duelist;
    
    public ToonWorldPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amount;
        this.duelist = AnyDuelist.from(this);
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() {
    	if (this.duelist.creature().hasPower(ToonKingdomPower.POWER_ID)) {
    		DuelistCard.removePower(this, this.duelist.creature());
    	}
    }
    
    @Override
    public void atStartOfTurn() {
        if (this.duelist.creature().hasPower(ToonKingdomPower.POWER_ID)) {
            DuelistCard.removePower(this, this.duelist.creature());
        }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (this.duelist.creature().hasPower(ToonKingdomPower.POWER_ID)) {
            DuelistCard.removePower(this, this.duelist.creature());
        }
    }

    
    @Override
	public void atEndOfTurn(final boolean isPlayer) {
        if (this.duelist.creature().hasPower(ToonKingdomPower.POWER_ID)) {
            DuelistCard.removePower(this, this.duelist.creature());
        }
	}
    
    @Override
    public void onInitialApplication() {
        this.duelist.handGroup().glowCheck();
    }

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
