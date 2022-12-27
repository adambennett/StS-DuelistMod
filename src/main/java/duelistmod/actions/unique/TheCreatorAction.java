package duelistmod.actions.unique;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.*;

public class TheCreatorAction extends AbstractGameAction {
	private ArrayList<AbstractCard> cardToMake;
	private boolean randomSpot = false;
	private boolean cardOffset = false;
	private boolean trigExtra = false;

	public TheCreatorAction(AbstractCreature target, AbstractCreature source, ArrayList<AbstractCard> card,int amount, boolean randomSpot, boolean cardOffset, boolean trigExtraEffect) 
	{
		setValues(target, source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = 0.001F;
		this.cardToMake = card;
		this.randomSpot = randomSpot;
		this.cardOffset = cardOffset;
		this.trigExtra = trigExtraEffect;
	}

	@Override
	public void update() {
		if (this.duration == 0.001F) 
		{
			boolean flipper = false;
			for (int i = 0; i < this.amount; i++) 
			{
				for (AbstractCard card : this.cardToMake)
				{
					if (!card.hasTag(Tags.NO_CREATOR) && !card.hasTag(Tags.TOKEN) && !card.rarity.equals(CardRarity.SPECIAL)) 
					{
						AbstractCard c = card.makeStatEquivalentCopy();
						if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) 
						{
				            c.isEthereal = true;
				            c.rawDescription = Strings.etherealForCardText + c.rawDescription;
							if (c instanceof DuelistCard) {
								((DuelistCard)c).fixUpgradeDesc();
							}
				            c.initializeDescription();
						}
						if (c.cost > 0)
						{
							c.modifyCostForCombat(-(c.cost - 1));
							c.isCostModified = true;
						}
						else if (c.cost == 0)
						{
							c.updateCost(1);
							c.isCostModified = true;
						}
						if (flipper) { AbstractDungeon.player.discardPile.addToRandomSpot(c); }
						else { AbstractDungeon.player.drawPile.addToRandomSpot(c); }
						if (!flipper && this.trigExtra) { flipper = true; }
						else if (flipper) { flipper = false; }
					}
				}
			}
		}
		this.duration -= Gdx.graphics.getDeltaTime();
		tickDuration();
	}
}
