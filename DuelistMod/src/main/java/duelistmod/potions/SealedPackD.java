package duelistmod.potions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.*;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.interfaces.DuelistCard;

public class SealedPackD extends AbstractPotion {


	public static final String POTION_ID = DuelistMod.makeID("SealedPackD");
	private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

	public static final String NAME = potionStrings.NAME;
	public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

	public SealedPackD() {
		// The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
		super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.CARD, PotionColor.SMOKE);

		// Potency is the damage/magic number equivalent of potions.
		this.potency = this.getPotency();

		// Initialize the Description
		this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];

		// Do you throw this potion at an enemy or do you just consume it.
		this.isThrown = false;

		// Initialize the on-hover name + description
		this.tips.add(new PowerTip(this.name, this.description));

	}

	@Override
	public void use(AbstractCreature target) 
	{
		ArrayList<DuelistCard> packCards = new ArrayList<DuelistCard>();

		// Add random guaranteed rare to the pack
		if (DuelistMod.rareCards.size() > 0) { packCards.add(DuelistMod.rareCards.get(AbstractDungeon.potionRng.random(DuelistMod.rareCards.size() - 1))); }
		
		// Add a completely random card from all cards to pack (no special, basic, toon, ojama, exodia, or never-generate cards
		DuelistCard rand = DuelistMod.myCards.get(AbstractDungeon.potionRng.random(DuelistMod.myCards.size() - 1));
		while (rand.hasTag(Tags.NEVER_GENERATE) || rand.hasTag(Tags.TOON) || rand.hasTag(Tags.EXODIA) || rand.hasTag(Tags.OJAMA)) { rand = DuelistMod.myCards.get(AbstractDungeon.potionRng.random(DuelistMod.myCards.size() - 1)); }
		packCards.add((DuelistCard) rand.makeStatEquivalentCopy());
		
		// Fill the rest of the pack with commons and uncommons
		if (this.potency - 2 > 0)
		{
			for (int i = 0; i < this.potency - 2; i++)
			{
				DuelistCard random = DuelistMod.nonRareCards.get(AbstractDungeon.potionRng.random(DuelistMod.nonRareCards.size() - 1));
				packCards.add((DuelistCard) random.makeStatEquivalentCopy()); 
			}
		}
		
		// Randomize pack cards and add to hand (10% chance to upgrade each card)
		for (DuelistCard c : packCards)
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, 10);
			if (roll == 1)
			{
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(c, true, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));
			}
			else
			{
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(c, false, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));
			}
		}
	}

	@Override
	public AbstractPotion makeCopy() {
		return new SealedPackD();
	}

	// This is your potency.
	@Override
	public int getPotency(final int potency) 
	{
		if (DuelistMod.challengeMode)
		{
			return 4;
		}
		else
		{
			return 5;
		}
	}

	public void upgradePotion()
	{
		this.potency += 2;
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, this.description));
	}
}
