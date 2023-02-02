package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.variables.Strings;

public class SoulBoneAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cards;

	public SoulBoneAction(ArrayList<AbstractCard> cardsToChooseFrom)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = 2;
		this.cards = cardsToChooseFrom;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			tmp.group.addAll(this.cards);

			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }
				if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + " Card for Soul Bone Tower", false, false, false, false); }
				else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + " Cards for Soul Bone Tower", false, false, false, false); }
				tickDuration();
				return;
			}
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			ArrayList<AbstractCard> added = new ArrayList<>();
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				c.stopGlowing();
				if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel))
				{
					DuelistCard.addToGraveyard(c.makeStatEquivalentCopy());
					if (DuelistCard.allowResummonsWithExtraChecks(c)) { added.add(c.makeStatEquivalentCopy()); }
				}
			}
			
			if (added.size() > 0)
			{
				AbstractMonster randTarg = AbstractDungeon.getRandomMonster();
				if (randTarg != null)
				{
					AbstractCard c = added.get(AbstractDungeon.cardRandomRng.random(added.size() - 1));
					DuelistCard.resummon(c, randTarg);
				}
			}
			
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
