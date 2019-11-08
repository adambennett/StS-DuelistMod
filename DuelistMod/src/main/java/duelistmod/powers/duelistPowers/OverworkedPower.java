package duelistmod.powers.duelistPowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;

public class OverworkedPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("OverworkedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public OverworkedPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public OverworkedPower(AbstractCreature owner, AbstractCreature source, int stacks) 
	{ 
		//super(owner, source, stacks);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = true;
        this.source = source;
        this.amount = stacks;
        this.loadRegion("anger");
		updateDescription();
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.type = PowerType.BUFF; this.description = DESCRIPTIONS[2] + -this.amount + DESCRIPTIONS[1]; }
		else { this.type = PowerType.DEBUFF; this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
	}
	
	@Override
	public void onEnemyUseCard(AbstractCard card)
	{
		if (card.type == AbstractCard.CardType.ATTACK) 
        {
            this.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -this.amount), -this.amount));
            this.flash();
        }
	}
	
	@Override
    public void onAfterUseCard(final AbstractCard card, final UseCardAction action) 
	{
        if (card.type == AbstractCard.CardType.ATTACK) 
        {
            this.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -this.amount), -this.amount));
            this.flash();
        }
    }

}
