package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.*;
import duelistmod.variables.Strings;

public class RedMedicineAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractPower> powers;
	private AbstractMonster m;
	private int amountOfPowersToChoose = 1;
	private int lowTurn = 1;
	private int highTurn = 4;
	private HashMap<UUID, AbstractPower> powerMap = new HashMap<UUID, AbstractPower>();

	public RedMedicineAction(int amount, AbstractMonster target, int noOfChoices, int lowTurns, int highTurns)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
		this.m = target;
		if (noOfChoices <= 25) { this.amountOfPowersToChoose = noOfChoices; }
		else { this.amountOfPowersToChoose = 25; }
		this.lowTurn = lowTurns;
		this.highTurn = highTurns;
	}
	
	public ArrayList<AbstractPower> createBuffSet()
	{
		
		int turnNum = AbstractDungeon.cardRandomRng.random(lowTurn, highTurn); 
		int secondRoll = AbstractDungeon.cardRandomRng.random(1, 3);
		ArrayList<AbstractPower> toReturn = BuffHelper.returnBuffList(AbstractDungeon.player, turnNum, secondRoll);
		return toReturn;
	}


	public void update()
	{
		
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			powers = new ArrayList<AbstractPower>();
			ArrayList<BuffCard> buffs = new ArrayList<BuffCard>();
			ArrayList<ArrayList<AbstractPower>> powerSets = new ArrayList<ArrayList<AbstractPower>>();
			ArrayList<String> powerList = new ArrayList<String>();
			for (int i = 0; i < amountOfPowersToChoose; i++) { powerSets.add(createBuffSet()); }
			
			for (int i = 0; i < amountOfPowersToChoose; i++)
			{
				BuffCard powerCard = new AbstractBuffCard();
				AbstractPower power = powerSets.get(i).get(AbstractDungeon.cardRandomRng.random(powerSets.get(i).size() - 1));
				while (powerList.contains(power.name)) { power = powerSets.get(i).get(AbstractDungeon.cardRandomRng.random(powerSets.get(i).size() - 1)); } 
				powerList.add(power.name);
				powers.add(power);
				powerCard.baseMagicNumber += power.amount;
				if (powerCard.baseMagicNumber < 0) { powerCard.baseMagicNumber = 0; }
				powerCard.magicNumber = powerCard.baseMagicNumber;
				powerCard.powerToApply = power;
				if (DuelistMod.debug) { System.out.println("theDuelist:RedMedicineAction:update() ---> powerCard.powerToApply was set to: " + power.name + ", with amount: " + power.amount); }
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
				if (DuelistMod.debug) { System.out.println("theDuelist:RedMedicineAction:update() ---> added " + card.originalName + " into grid selection pool"); }
			}
			Collections.sort(tmp.group, GridSort.getComparator());
			tmp.addToTop(new CancelCard());
			if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configBuffToGainString, false); }
			else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configBuffToGainPluralString, false); }
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
					bC.baseMagicNumber += powerMap.get(bC.uuid).amount;
					if (bC.baseMagicNumber < 0) { bC.baseMagicNumber = 0; }
					bC.magicNumber = bC.baseMagicNumber;
					bC.powerToApply = powerMap.get(bC.uuid);
					if (DuelistMod.debug) { System.out.println("theDuelist:RedMedicineAction:update() ---> bC.powerToApply was set to: " + powerMap.get(bC.uuid).name + ", with amount: " + powerMap.get(bC.uuid).amount); }
					bC.rawDescription = DuelistMod.powerGainCardText + powerMap.get(bC.uuid).name + ".";
					if (powerMap.get(bC.uuid).amount > 0) { bC.rawDescription = DuelistMod.powerGainCardText + powerMap.get(bC.uuid).name + "."; }
					else { bC.rawDescription = Strings.powerGain0Text + powerMap.get(bC.uuid).name + ".";  }
					bC.name = powerMap.get(bC.uuid).name;
					bC.initializeDescription();
					if (bC.powerToApply != null) { DuelistCard.playNoResummon(bC, false, this.m, false); }
					else if (DuelistMod.debug) { DuelistMod.logger.info("red medicine action got null power for " + c.name); }
				}
				else if (DuelistMod.debug)
				{
					DuelistMod.logger.info("red medicine action was cancelled by player");
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		
		tickDuration();
	}
}
