package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.cards.other.tokens.Token;
import duelistmod.powers.duelistPowers.WorldTreePower;
import duelistmod.variables.Tags;

public class WorldTreeAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private int prevMagic = 3;
	private int regrowMagic = 5;

	// Splendid Rose (power)
	public WorldTreeAction(int magic)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = 1;
		this.prevMagic = magic;
		this.regrowMagic = new WorldTreeRegrow().magicNumber;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			tmp.addToTop(new WorldTreeRegrow());
			tmp.addToTop(new SplendidCancel());
			AbstractDungeon.gridSelectScreen.open(tmp, 1, "World Tree has Perished", false, false, false, false); 
			tickDuration();
			return;
		}

		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				c.stopGlowing();
				if (c instanceof WorldTreeRegrow)
				{
					int plants = 0;
					ArrayList<DuelistCard> tribs = DuelistCard.tribute(p, 0, true, new Token());
					for (DuelistCard card : tribs)
					{
						if (card.hasTag(Tags.PLANT))
						{
							plants++;
						}
					}
					DuelistCard.applyPowerToSelf(new WorldTreePower(p, p, this.prevMagic, plants * this.regrowMagic));					
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
