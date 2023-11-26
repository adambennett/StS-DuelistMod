package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.*;
import duelistmod.powers.*;
import duelistmod.variables.Strings;

public class SolemnStrikeAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractPower> powers;
	private int turns = 1;
	private HashMap<UUID, AbstractPower> powerMap = new HashMap<UUID, AbstractPower>();

	public SolemnStrikeAction(int magic, int turns)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = magic;
		this.turns = turns;
	}
	
	public ArrayList<AbstractPower> createBuffSet()
	{
		ArrayList<AbstractPower> toReturn = new ArrayList<AbstractPower>();
		toReturn.add(new StrengthUpPower(p, p, this.turns, this.amount));
		toReturn.add(new FocusUpPower(p, p, this.turns, this.amount));
		return toReturn;
	}


	public void update()
	{
		
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			powers = new ArrayList<AbstractPower>();
			ArrayList<BuffCard> buffs = new ArrayList<BuffCard>();
			ArrayList<AbstractPower> powerSets = new ArrayList<>();
			powerSets.addAll(createBuffSet());
			
			for (int i = 0; i < 2; i++)
			{
				AbstractPower power = powerSets.get(i);
				BuffCard powerCard = new AbstractBuffCard(power);
				powers.add(power);
				powerCard.baseMagicNumber += power.amount;
				if (powerCard.baseMagicNumber < 0) { powerCard.baseMagicNumber = 0; }
				powerCard.magicNumber = powerCard.baseMagicNumber;
				String coloredPowerName = "*" + power.name.replaceAll(" ", " *");
				Util.log("theDuelist:SolemnStrikeAction:update() ---> powerCard.powerToApply was set to: " + coloredPowerName + ", with amount: " + power.amount);
				if (power.amount > 0) { powerCard.rawDescription = DuelistMod.powerGainCardText + coloredPowerName; }
				else { powerCard.rawDescription = Strings.powerGain0Text + coloredPowerName;  }
				powerCard.name = power.name;
				powerCard.initializeDescription();
				buffs.add(powerCard);
				powerMap.put(powerCard.uuid, power);
			}

			CardGroup tmp;
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (BuffCard card : buffs)
			{
				tmp.addToTop(card);
				Util.log("theDuelist:SolemnStrikeAction:update() ---> added " + card.originalName + " into grid selection pool");
			}
			Collections.sort(tmp.group, GridSort.getComparator());
			//tmp.addToTop(new CancelCard());
			SelectScreenHelper.open(tmp, 1, "Choose Strength or Focus");
			tickDuration();
			return;
		}

		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				c.stopGlowing();
				if (c instanceof BuffCard)
				{
					BuffCard bC = (BuffCard)c;
					AbstractPower power = powerMap.get(bC.uuid);
					bC.baseMagicNumber += power.amount;
					if (bC.baseMagicNumber < 0) { bC.baseMagicNumber = 0; }
					bC.magicNumber = bC.baseMagicNumber;
					bC.setPowerToApply(power);
					String coloredPowerName = "*" + power.name.replaceAll(" ", " *");
					Util.log("theDuelist:SolemnStrikeAction:update() ---> bC.powerToApply was set to: " + coloredPowerName + ", with amount: " + power.amount);
					if (power.amount > 0) { bC.rawDescription = DuelistMod.powerGainCardText +coloredPowerName; }
					else { bC.rawDescription = Strings.powerGain0Text + coloredPowerName;  }
					bC.name = power.name;
					bC.initializeDescription();
					if (bC.getPowerToApply() != null) { DuelistCard.playNoResummon(bC, false, null, false); }
					else { Util.log("solemn strike action got null power for " + c.name); }
				}
				else 
				{
					Util.log("solemn strike action was cancelled by player");
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		
		tickDuration();
	}
}
