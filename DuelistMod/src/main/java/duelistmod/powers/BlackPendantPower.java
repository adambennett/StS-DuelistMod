package duelistmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.SpecificCardDiscardToHandAction;
import duelistmod.variables.Strings;

public class BlackPendantPower extends AbstractPower 
{
	public AbstractCreature source;

	public static final String POWER_ID = DuelistMod.makeID("BlackPendantPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.DESPAIR_POWER);
	public ArrayList<DuelistCard> allPendants = new ArrayList<DuelistCard>();


	public BlackPendantPower(AbstractCreature owner, AbstractCreature source, DuelistCard card) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;		
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		if (!allPendants.contains(card)) { this.allPendants.add(card); }
		this.amount = allPendants.size();
		this.updateDescription();
	}
	
	// At the start of next turn, re-add Black Pendant to hand
	@Override
	public void atStartOfTurn() 
	{
		boolean cardStillExists = false;
		ArrayList<AbstractCard> discardPendants = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.discardPile.group) 
		{ 
			for (DuelistCard d : allPendants)
			{
				if (c.uuid.equals(d.uuid))
				{
					cardStillExists = true;
					discardPendants.add(c);
				}
			}
		}
		if (cardStillExists && discardPendants.size() > 0) 
		{ 
			for (AbstractCard c : discardPendants)
			{
				AbstractDungeon.actionManager.addToTop(new SpecificCardDiscardToHandAction(this.owner, c, AbstractDungeon.player.discardPile)); 
				ArrayList<DuelistCard> newAllPens = new ArrayList<DuelistCard>();
				for (DuelistCard d : allPendants) { if (!d.uuid.equals(c.uuid)) { newAllPens.add(d); }}
				this.allPendants.clear();
				this.allPendants.addAll(newAllPens);
			}
		}
		this.amount = allPendants.size();
		updateDescription();		
		if (this.allPendants.size() < 1) { DuelistCard.removePower(this, this.owner); }
	}

	@Override
	public void updateDescription() 
	{
		this.amount = allPendants.size();
		this.description = DESCRIPTIONS[0] + allPendants.size();
	}
}
