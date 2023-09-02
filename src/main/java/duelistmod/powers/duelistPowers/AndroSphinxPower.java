package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class AndroSphinxPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("AndroSphinxPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");

	public AndroSphinxPower(AbstractCreature owner, AbstractCreature source, int beasts) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = beasts;
		updateDescription(); 
	}

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.hasTag(Tags.BEAST)) {
            this.amount--;
            if (this.amount < 1) {
                AnyDuelist duelist = AnyDuelist.from(this);
                AbstractPower instance = duelist.getPower(AndroSphinxPower.POWER_ID);
                DuelistCard.removePower(instance, duelist.creature());
            }
        }
    }
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1 ? DESCRIPTIONS[2] : DESCRIPTIONS[1]);
	}
}
