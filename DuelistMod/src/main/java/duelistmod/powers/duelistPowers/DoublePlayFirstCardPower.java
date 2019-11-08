package duelistmod.powers.duelistPowers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class DoublePlayFirstCardPower extends DuelistPower
{	
	public AbstractCreature source;

	public static final String POWER_ID = DuelistMod.makeID("DoublePlayFirstCardPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public DoublePlayFirstCardPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}

	public DoublePlayFirstCardPower(AbstractCreature owner, AbstractCreature source, int stacks) 
	{ 
		//super(owner, source, stacks);
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;        
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.canGoNegative = false;
		this.source = source;
		this.amount = stacks;
		this.loadRegion("echo");
		updateDescription();
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { DuelistCard.removePower(this, this.owner); }
		else if (this.amount == 1) { this.description = DESCRIPTIONS[3] + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
	}

	@Override
	public void onUseCard(final AbstractCard card, final UseCardAction action) 
	{
		if (!card.purgeOnUse && this.amount > 0)
		{
			this.flash();
			AbstractMonster m = null;
			if (action.target != null) { m = (AbstractMonster)action.target; }
			final AbstractCard tmp = card.makeSameInstanceOf();
			AbstractDungeon.player.limbo.addToBottom(tmp);
			tmp.current_x = card.current_x;
			tmp.current_y = card.current_y;
			tmp.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
			tmp.target_y = Settings.HEIGHT / 2.0f;
			if (m != null) { tmp.calculateCardDamage(m); }
			tmp.purgeOnUse = true;
			AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
			this.amount--;
			if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
			else { updateDescription(); }
		}
	}

}
