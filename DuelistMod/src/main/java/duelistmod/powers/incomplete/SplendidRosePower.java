package duelistmod.powers.incomplete;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.cards.typecards.*;
import duelistmod.variables.Tags;


@SuppressWarnings("unused")
public class SplendidRosePower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("SplendidRosePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("SplendidRosePower.png");
	private boolean finished = false;
	
	public SplendidRosePower(final AbstractCreature owner, final AbstractCreature source, int amount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void onAfterUseCard(AbstractCard card, UseCardAction action)
	{
		if (card.hasTag(Tags.ROSE))
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, 3);
			if (roll == 1) { this.amount++; this.flash(); updateDescription(); }
		}
	}
	
	@Override
	public void atStartOfTurnPostDraw()
	{
		ArrayList<DuelistCard> choices = new ArrayList<DuelistCard>();
		choices.add(new SplendidConfirmCard(this.amount));
		choices.add(new SplendidCancel());
		AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(choices, 1));
	}
}
