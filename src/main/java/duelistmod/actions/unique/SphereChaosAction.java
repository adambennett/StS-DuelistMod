package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.variables.Strings;

public class SphereChaosAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractPower> powers;
	private AbstractMonster m;
	private int amountOfPowersToChoose = 1;
	private int turns = 1;
	private HashMap<UUID, AbstractPower> powerMap = new HashMap<UUID, AbstractPower>();

	public SphereChaosAction(int amount, AbstractMonster target, int noOfChoices, int turns)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
		this.m = target;
		if (noOfChoices <= createBuffSet().size()) { this.amountOfPowersToChoose = noOfChoices; }
		else { this.amountOfPowersToChoose = createBuffSet().size(); }
		this.turns = turns;
	}
	
	public ArrayList<AbstractPower> createBuffSet()
	{
		ArrayList<AbstractPower> toReturn = new ArrayList<AbstractPower>();
		int turnNum = this.turns;
		toReturn.add(new StrengthPower(m, -turnNum));
		toReturn.add(new SlowPower(m, turnNum));
		toReturn.add(new FrozenDebuff(m, p));
		toReturn.add(new BurningDebuff(m, p, turnNum));
		toReturn.add(new PoisonPower(m, p, turnNum));
		toReturn.add(new WeakPower(m, turnNum, false));
		toReturn.add(new ConstrictedPower(m, p, turnNum));
		toReturn.add(new LoseStrengthPower(m, turnNum));
		toReturn.add(new VulnerablePower(m, turnNum, false));
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
				BuffCard powerCard = new AbstractDebuffCard();
				AbstractPower power = powerSets.get(i).get(AbstractDungeon.cardRandomRng.random(powerSets.get(i).size() - 1));
				while (powerList.contains(power.name)) { power = powerSets.get(i).get(AbstractDungeon.cardRandomRng.random(powerSets.get(i).size() - 1)); } 
				powerList.add(power.name);
				powers.add(power);
				powerCard.baseMagicNumber += power.amount;
				powerCard.magicNumber = powerCard.baseMagicNumber;
				powerCard.powerToApply = power;
				String powerName = power.name;
				powerName = powerName.equals("Strength") ? powerName + " *Down" : powerName;
				Util.log("theDuelist:SphereChaosAction:update() ---> powerCard.powerToApply was set to: " + power.name + ", with amount: " + power.amount);
				if (power.amount > 0) {
					powerCard.rawDescription = "Apply !M! " + powerName + ".";
				} else if (power.amount < 0) {
					powerCard.rawDescription = "Apply " + power.amount*-1 + " " + powerName + ".";
				} else { powerCard.rawDescription = "Apply " + powerName + ".";  }
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
				Util.log("theDuelist:SphereChaosAction:update() ---> added " + card.originalName + " into grid selection pool");
			}
			Collections.sort(tmp.group, GridSort.getComparator());
			tmp.addToTop(new CancelCard());
			if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + " Debuff", false); }
			else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + " Debuffs", false); }
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
					bC.magicNumber = bC.baseMagicNumber;
					bC.powerToApply = powerMap.get(bC.uuid);
					Util.log("theDuelist:SphereChaosAction:update() ---> bC.powerToApply was set to: " + powerMap.get(bC.uuid).name + ", with amount: " + powerMap.get(bC.uuid).amount);
					bC.rawDescription = DuelistMod.powerGainCardText + powerMap.get(bC.uuid).name + ".";
					String powerName = powerMap.get(bC.uuid).name;
					powerName = powerName.equals("Strength") ? powerName + " Down" : powerName;
					if (powerMap.get(bC.uuid).amount > 0) { bC.rawDescription = "Apply " + bC.powerToApply.amount + " " + powerName + "."; }
					else if (powerMap.get(bC.uuid).amount < 0) { bC.rawDescription = "Apply " + bC.powerToApply.amount*-1 + " " + powerName + "."; }
					else { bC.rawDescription = "Apply " + powerName + ".";  }
					bC.name = powerMap.get(bC.uuid).name;
					bC.initializeDescription();
					if (bC.powerToApply != null) { DuelistCard.playNoResummon(bC, false, this.m, false); }
					else { Util.log("sphere chaos action got null power for " + c.name); }
				}
				else { Util.log("sphere chaos action was cancelled by player"); }
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		
		tickDuration();
	}
}
