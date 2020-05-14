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
import duelistmod.powers.duelistPowers.*;
import duelistmod.variables.Strings;

public class FossilSkullbuggyAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractPower> powers;
	private int turns = 1;
	private HashMap<UUID, AbstractPower> powerMap = new HashMap<UUID, AbstractPower>();

	public FossilSkullbuggyAction(int magic, int turns)
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
		toReturn.add(new ElectricityUpPower(p, p, this.turns, this.amount));
		toReturn.add(new BloodUpPower(p, p, this.turns, this.amount));
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
				BuffCard powerCard = new AbstractBuffCard();
				AbstractPower power = powerSets.get(i);				
				powers.add(power);
				powerCard.baseMagicNumber += power.amount;
				if (powerCard.baseMagicNumber < 0) { powerCard.baseMagicNumber = 0; }
				powerCard.magicNumber = powerCard.baseMagicNumber;
				powerCard.powerToApply = power;
				Util.log("theDuelist:FossilSkullbuggyAction:update() ---> powerCard.powerToApply was set to: " + power.name + ", with amount: " + power.amount);
				if (power.amount > 0) { powerCard.rawDescription = DuelistMod.powerGainCardText + power.name + "."; }
				else { powerCard.rawDescription = Strings.powerGain0Text + power.name + ".";  }
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
				Util.log("theDuelist:FossilSkullbuggyAction:update() ---> added " + card.originalName + " into grid selection pool");
			}
			Collections.sort(tmp.group, GridSort.getComparator());
			tmp.addToTop(new CancelCard());
			AbstractDungeon.gridSelectScreen.open(tmp, 1, "Choose Electricity or Blood", false);
			tickDuration();
			return;
		}

		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				if (c instanceof BuffCard)
				{
					BuffCard bC = (BuffCard)c;
					bC.baseMagicNumber += powerMap.get(bC.uuid).amount;
					if (bC.baseMagicNumber < 0) { bC.baseMagicNumber = 0; }
					bC.magicNumber = bC.baseMagicNumber;
					bC.powerToApply = powerMap.get(bC.uuid);
					Util.log("theDuelist:FossilSkullbuggyAction:update() ---> bC.powerToApply was set to: " + powerMap.get(bC.uuid).name + ", with amount: " + powerMap.get(bC.uuid).amount);
					bC.rawDescription = DuelistMod.powerGainCardText + powerMap.get(bC.uuid).name + ".";
					if (powerMap.get(bC.uuid).amount > 0) { bC.rawDescription = DuelistMod.powerGainCardText + powerMap.get(bC.uuid).name + "."; }
					else { bC.rawDescription = Strings.powerGain0Text + powerMap.get(bC.uuid).name + ".";  }
					bC.name = powerMap.get(bC.uuid).name;
					bC.initializeDescription();
					if (bC.powerToApply != null) { DuelistCard.playNoResummon(bC, false, null, false); }
					else { Util.log("fossil skullbuggy action got null power for " + c.name); }
				}
				else 
				{
					Util.log("fossil skullbuggy action was cancelled by player");
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		
		tickDuration();
	}
}
