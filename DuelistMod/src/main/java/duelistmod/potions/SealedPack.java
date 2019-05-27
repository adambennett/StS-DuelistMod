package duelistmod.potions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.*;
import duelistmod.actions.common.*;
import duelistmod.interfaces.DuelistCard;

public class SealedPack extends AbstractPotion {


	public static final String POTION_ID = DuelistMod.makeID("SealedPack");
	private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

	public static final String NAME = potionStrings.NAME;
	public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

	public SealedPack() {
		// The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
		super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.SMOKE);

		// Potency is the damage/magic number equivalent of potions.
		this.potency = this.getPotency();

		// Initialize the Description
		this.description = DESCRIPTIONS[0];

		// Do you throw this potion at an enemy or do you just consume it.
		this.isThrown = false;

		// Initialize the on-hover name + description
		this.tips.add(new PowerTip(this.name, this.description));

	}

	@Override
	public void use(AbstractCreature target) 
	{
		for (int i = 0; i < this.potency; i++)
		{
			DuelistCard randomCard = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.LEGEND_BLUE_EYES);
			int roll = AbstractDungeon.cardRandomRng.random(1, 5);
			if (roll == 1)
			{
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomCard, true, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));
			}
			else
			{
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomCard, false, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));
			}
		}
	}

	@Override
	public AbstractPotion makeCopy() {
		return new SealedPack();
	}

	// This is your potency.
	@Override
	public int getPotency(final int potency) 
	{
		if (DuelistMod.challengeMode)
		{
			return 2;
		}
		else
		{
			return 3;
		}
	}

	public void upgradePotion()
	{
		this.potency += 1;
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, this.description));
	}
}
