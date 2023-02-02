package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.variables.*;

import java.util.*;
import java.util.concurrent.*;

public class DakkiAction extends AbstractGameAction
{
	private final AbstractPlayer p;
	private ArrayList<AbstractPower> powers;
	private final ArrayList<AbstractMonster> monsters;
	private int amountOfPowersToChoose = 1;
	private int turns = 1;
	private boolean choosePhase = true;
	private final HashMap<UUID, AbstractPower> powerMap = new HashMap<UUID, AbstractPower>();
	private final Map<AbstractCreature, Map<String, AbstractPower>> powerSets;

	public DakkiAction(int amount, ArrayList<AbstractMonster> targets, int noOfChoices, int turns)
	{
		int BUFF_SET_SIZE = 8;
		this.p = AbstractDungeon.player;
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
		this.monsters = targets;
		this.amountOfPowersToChoose = Math.min(noOfChoices, BUFF_SET_SIZE);
		this.turns = turns;
		this.powerSets = createBuffSet();
	}

	public AbstractPower findPower(AbstractCreature mon, String powerName) {
		return this.powerSets.get(mon).get(powerName);
	}

	public Map<AbstractCreature, Map<String, AbstractPower>> createBuffSet()
	{
		Map<AbstractCreature, Map<String, AbstractPower>> toReturn = new HashMap<>();
		int turnNum = this.turns;
		for (AbstractCreature monster : this.monsters) {
			HashMap<String, AbstractPower> debuffs = new HashMap<>();

			AbstractPower debuff = new StrengthPower(monster, -turnNum);
			debuffs.put(debuff.name, debuff);

			debuff = new SlowPower(monster, turnNum);
			debuffs.put(debuff.name, debuff);

			debuff = new FrozenDebuff(monster, p);
			debuffs.put(debuff.name, debuff);

			debuff = new BurningDebuff(monster, p, turnNum);
			debuffs.put(debuff.name, debuff);

			debuff = new PoisonPower(monster, p, turnNum);
			debuffs.put(debuff.name, debuff);

			debuff = new WeakPower(monster, turnNum, false);
			debuffs.put(debuff.name, debuff);

			debuff = new ConstrictedPower(monster, p, turnNum);
			debuffs.put(debuff.name, debuff);

			debuff = new VulnerablePower(monster, turnNum, false);
			debuffs.put(debuff.name, debuff);

			toReturn.put(monster, debuffs);
		}
		return toReturn;
	}


	public void update()
	{
		if (this.duration == Settings.ACTION_DUR_MED) {
			powers = new ArrayList<>();
			ArrayList<BuffCard> buffs = new ArrayList<>();
			List<AbstractPower> availableDebuffs = new ArrayList<>();

			AbstractCreature ref = this.monsters.get(0);
			for (Map.Entry<String, AbstractPower> entry : powerSets.get(ref).entrySet()) {
				availableDebuffs.add(entry.getValue());
			}

			for (int i = 0; i < this.amountOfPowersToChoose; i++) {

				AbstractPower power = availableDebuffs.remove(AbstractDungeon.cardRandomRng.random(availableDebuffs.size() - 1));
				BuffCard powerCard = new AbstractDebuffCard();
				powerCard.baseMagicNumber += power.amount;
				powerCard.magicNumber = powerCard.baseMagicNumber;
				powerCard.powerToApply = power;
				String powerName = power.name;
				powerName = powerName.equals("Strength") ? powerName + " *Down" : powerName;
				if (power.amount > 0) {
					powerCard.rawDescription = "Apply !M! " + powerName;
				} else if (power.amount < 0) {
					powerCard.rawDescription = "Apply " + power.amount * -1 + " " + powerName;
				} else {
					powerCard.rawDescription = "Apply " + powerName + ".";
				}
				Util.log("Dakki: powerCard description:" + powerCard.rawDescription);
				powerCard.name = power.name;
				powerCard.initializeDescription();
				buffs.add(powerCard);
				powerMap.put(powerCard.uuid, power);
			}

			CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (BuffCard card : buffs) {
				tmp.addToTop(card);
			}
			tmp.group.sort(GridSort.getComparator());
			tmp.addToTop(new CancelCard());
			if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + " Debuff", false); }
			else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + " Debuffs", false); }
			tickDuration();
			this.choosePhase = false;
			return;
		}

		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)) {
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				c.unhover();
				c.stopGlowing();
				if (c instanceof BuffCard) {
					for (AbstractCreature monster : this.monsters) {
						BuffCard bC = ((BuffCard) c).makeStatEquivalentCopy();
						String powerName = powerMap.get(c.uuid).name;
						int powerAmt = powerMap.get(c.uuid).amount;
						bC.baseMagicNumber += powerAmt;
						bC.magicNumber = bC.baseMagicNumber;
						bC.powerToApply = findPower(monster, powerName);
						powerName = powerName.equals("Strength") ? powerName + " Down" : powerName;
						if (powerMap.get(c.uuid).amount > 0) { bC.rawDescription = "Apply " + bC.powerToApply.amount + " " + powerName + "."; }
						else if (powerMap.get(c.uuid).amount < 0) { bC.rawDescription = "Apply " + bC.powerToApply.amount*-1 + " " + powerName + "."; }
						else { bC.rawDescription = "Apply " + powerName + ".";  }
						Util.log("Dakki: bC description:" + bC.rawDescription);
						bC.name = powerName;
						bC.initializeDescription();
						if (bC.powerToApply != null) {
							DuelistCard.playNoResummonBuffCard(1, bC, false, monster, false);
						} else {
							Util.log("Dakki action got null power for " + c.name);
						}
					}
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
