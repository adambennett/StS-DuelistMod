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
	private final AbstractPlayer p;
    private final AbstractMonster m;
	private int amountOfPowersToChoose = 1;
	private int turns = 1;
	private final HashMap<UUID, AbstractPower> powerMap = new HashMap<>();

	public SphereChaosAction(int amount, AbstractMonster target, int noOfChoices, int turns) {
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
		this.m = target;
        this.amountOfPowersToChoose = Math.min(noOfChoices, createBuffSet().size());
		this.turns = turns;
	}
	
	public ArrayList<AbstractPower> createBuffSet() {
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


	public void update() {
		if (this.duration == Settings.ACTION_DUR_MED) {
			ArrayList<BuffCard> buffs = new ArrayList<>();
			ArrayList<ArrayList<AbstractPower>> powerSets = new ArrayList<>();
			ArrayList<String> powerList = new ArrayList<>();
			for (int i = 0; i < amountOfPowersToChoose; i++) {
				powerSets.add(createBuffSet());
			}
			
			for (int i = 0; i < amountOfPowersToChoose; i++) {
				AbstractPower power = powerSets.get(i).get(AbstractDungeon.cardRandomRng.random(powerSets.get(i).size() - 1));
				while (powerList.contains(power.name)) { power = powerSets.get(i).get(AbstractDungeon.cardRandomRng.random(powerSets.get(i).size() - 1)); } 
				powerList.add(power.name);

				BuffCard powerCard = new AbstractDebuffCard(power);
				powerCard.baseMagicNumber += power.amount;
				powerCard.magicNumber = powerCard.baseMagicNumber;
				String coloredPowerName = "*" + power.name.replaceAll(" ", " *");
				if (power.amount != 0) {
					powerCard.rawDescription = "Apply !M! " + coloredPowerName;
				} else { powerCard.rawDescription = "Apply " + coloredPowerName;  }
				powerCard.name = power.name;
				powerCard.initializeDescription();
				buffs.add(powerCard);
				powerMap.put(powerCard.uuid, power);
			}

			CardGroup tmp;
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (BuffCard card : buffs) {
				tmp.addToTop(card);
			}
			tmp.group.sort(GridSort.getComparator());
			if (this.amount == 1) {
				SelectScreenHelper.open(tmp, this.amount, Strings.configChooseString + this.amount + " Debuff");
			} else {
				SelectScreenHelper.open(tmp, this.amount, Strings.configChooseString + this.amount + " Debuffs");
			}
			tickDuration();
			return;
		}

		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)) {
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				c.unhover();
				c.stopGlowing();
				if (c instanceof BuffCard) {
					BuffCard bC = (BuffCard)c;
					AbstractPower power = powerMap.get(bC.uuid);
					bC.baseMagicNumber += power.amount;
					bC.magicNumber = bC.baseMagicNumber;
					bC.setPowerToApply(power);
					bC.rawDescription = DuelistMod.powerGainCardText + power.name + ".";
					String coloredPowerName = "*" + power.name.replaceAll(" ", " *");
					if (power.amount != 0) {
						bC.rawDescription = "Apply " + bC.getPowerToApply().amount + " " + coloredPowerName;
					} else {
						bC.rawDescription = "Apply " + coloredPowerName;
					}
					bC.name = power.name;
					bC.initializeDescription();
					if (bC.getPowerToApply() != null) {
						DuelistCard.playNoResummon(bC, false, this.m, false);
					}
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
