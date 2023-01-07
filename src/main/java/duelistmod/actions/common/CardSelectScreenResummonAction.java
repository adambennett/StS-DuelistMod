package duelistmod.actions.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.Util;
import duelistmod.variables.*;

public class CardSelectScreenResummonAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade;
	private ArrayList<AbstractCard> cards;
	private boolean damageBlockRandomize = false;
	private boolean randomTarget = true;
	private boolean targetAllEnemy = false;
	private AbstractMonster target;
	private boolean resummon = true;
	private boolean canCancel = false;
	private boolean allowExempt = false;
	private int copies = 1;
	
	// Cards: 	Dark Paladin, Gemini Elf, Rainbow Jar, Shard of Greed, Toon Masked Sorcerer, Winged Kuriboh Lv 9 & Lv10, Rainbow Gravity, Orb Token
	// Relics: 	Millennium Puzzle orb deck effect
	// Potions: Big Orb Bottle
	public CardSelectScreenResummonAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount, boolean upgraded, boolean randomizeBlockDamage, boolean resummon, boolean canCancel)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.damageBlockRandomize = randomizeBlockDamage;
		this.resummon = resummon;
		this.canCancel = canCancel;
	}
	
	// All Polymerizations (as of 12-24-19)
	public CardSelectScreenResummonAction(int copies, ArrayList<AbstractCard> cardsToChooseFrom, int amount, boolean upgraded, boolean randomizeBlockDamage, AbstractMonster m, boolean canCancel)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.damageBlockRandomize = randomizeBlockDamage;
		this.target = m;
		this.randomTarget = false;
		this.canCancel = canCancel;
		this.copies = copies;
	}
	
	// Invigoration, Polymerization, Mini Poly, Call Mummy
	public CardSelectScreenResummonAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount, boolean upgraded, boolean randomizeBlockDamage, AbstractMonster m, boolean canCancel)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.damageBlockRandomize = randomizeBlockDamage;
		this.target = m;
		this.randomTarget = false;
		this.canCancel = canCancel;
	}
	
	// Twilight Rose Knight
	public CardSelectScreenResummonAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount, AbstractMonster m, boolean canCancel, boolean random)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.damageBlockRandomize = false;
		this.target = m;
		this.randomTarget = random;
		this.canCancel = canCancel;
	}
	
	// Splendid Rose (power)
	public CardSelectScreenResummonAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.damageBlockRandomize = false;
		this.randomTarget = true;
		this.canCancel = false;
	}
	
	public CardSelectScreenResummonAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount, Double unusued)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = new ArrayList<>();
		for (AbstractCard c : cardsToChooseFrom) { if (c instanceof DuelistCard) { this.cards.add((DuelistCard) c); }}
		this.damageBlockRandomize = false;
		this.randomTarget = true;
		this.canCancel = true;
	}
	
	// Inzektron
	public CardSelectScreenResummonAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount, AbstractMonster m)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.damageBlockRandomize = false;
		this.randomTarget = false;
		this.target = m;
		this.canCancel = true;
	}
	
	public CardSelectScreenResummonAction(boolean allowExempt, ArrayList<AbstractCard> cardsToChooseFrom, int amount, AbstractMonster m)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.damageBlockRandomize = false;
		this.randomTarget = false;
		this.target = m;
		this.canCancel = true;
		this.allowExempt = allowExempt;
	}
	
	public CardSelectScreenResummonAction(boolean targetAll, ArrayList<AbstractCard> cardsToChooseFrom, int amount)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.damageBlockRandomize = false;
		this.randomTarget = false;
		this.targetAllEnemy = true;
		this.target = null;
		this.canCancel = true;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard card : cards)
			{
				boolean allow = DuelistCard.allowResummonsWithExtraChecks(card);
				if (!allow && DuelistCard.allowResummonsSkipOnlyExempt(card) && this.allowExempt) { allow = true; }
				if (allow)
				{
					AbstractCard gridCard = card.makeStatEquivalentCopy();

					if (this.upgrade) { gridCard.upgrade(); }
					if (this.resummon && !(gridCard instanceof GeneticAlgorithm)) { gridCard.misc = 52; }
					if (this.target == null && !this.targetAllEnemy) { Util.log("Is this it? Big bug guy? C"); }
					if (!this.targetAllEnemy && (randomTarget || this.target == null)) { this.target = AbstractDungeon.getRandomMonster(); }
		    		if (damageBlockRandomize)
		    		{
		    			if (gridCard.damage > 0)
		    			{
		    				int low = gridCard.damage * -1;
		    				int high = gridCard.damage + 6;
		    				int roll = AbstractDungeon.cardRandomRng.random(low, high);
		    				AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(gridCard.uuid, roll));
		    				gridCard.isDamageModified = true;
		    			}
		    			
		    			if (gridCard.block > 0)
		    			{
		    				int low = gridCard.block * -1;
		    				int high = gridCard.block + 6;
		    				int roll = AbstractDungeon.cardRandomRng.random(low, high);
		    				AbstractDungeon.actionManager.addToTop(new ModifyBlockAction(gridCard.uuid, roll));
		    				gridCard.isBlockModified = true;
		    			}
		    		}	
			        gridCard.initializeDescription();
					tmp.addToTop(gridCard);
					if (DuelistMod.debug) { System.out.println("theDuelist:CardSelectScreenResummonAction:update() ---> added " + gridCard.originalName + " into grid selection pool"); }
				}
			}
			
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (this.amount > 0 && tmp.group.size() > 0)
			{
				if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
				if (this.targetAllEnemy)
				{
					if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + " card to Resummon on ALL enemies", false, false, false, false); }
					else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + " cards to Resummon on ALL enemies", false, false, false, false); }
					
					tickDuration();
					return;
				}
				else if (this.randomTarget && this.resummon)
				{
					if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configResummonRandomlyString, false, false, false, false); }
					else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configResummonRandomlyPluralString, false, false, false, false); }
					
					tickDuration();
					return;
				}
				else if (!this.randomTarget && this.resummon)
				{
					if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configResummonRandomlyTargetString, false, false, false, false); }
					else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configResummonRandomlyTargetPluralString, false, false, false, false); }
					tickDuration();
					return;
				}
				else if (this.randomTarget && !this.resummon)
				{
					if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configCardPlayString, false, false, false, false); }
					else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configCardPlayPluralString, false, false, false, false); }
					tickDuration();
					return;
				}
				else if (!this.randomTarget && !this.resummon)
				{
					if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configCardPlayTargetString, false, false, false, false); }
					else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configCardPlayTargetPluralString, false, false, false, false); }
					tickDuration();
					return;
				}
				
			}
		}
		
		if (this.amount > 0 && (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel))
				{
					if (this.targetAllEnemy)
					{
						DuelistCard.resummonOnAll(c, this.copies, false, this.allowExempt);
					}
					else if (this.resummon && this.target != null)
					{
						DuelistCard.resummon(c, this.target, this.copies, false, this.allowExempt);
						Util.log("CardSelectScreenResummonAction :: fullResummon triggered with " + c.name);
					}
					else if (!this.resummon && this.target != null)
					{
						DuelistCard.playNoResummon(this.copies, (DuelistCard)c, false, this.target, false);
						Util.log("CardSelectScreenResummonAction :: playNoResummon triggered with " + c.name);
					}
					
					else if (this.target == null)
					{
						Util.log("BIGGEST BADDEST GUYY cmon GUY getout");
						DuelistCard.resummon(c, AbstractDungeon.getRandomMonster(), this.copies, false, this.allowExempt);
					}
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
