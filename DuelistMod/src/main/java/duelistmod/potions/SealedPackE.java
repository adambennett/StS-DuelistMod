package duelistmod.potions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedHandAction;

public class SealedPackE extends AbstractPotion {


	public static final String POTION_ID = DuelistMod.makeID("SealedPackE");
	private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

	public static final String NAME = potionStrings.NAME;
	public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

	public SealedPackE() {
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
		
		// Fill the rest of the pack with commons and uncommons
		if (this.potency - 1 > 0)
		{
			for (int i = 0; i < this.potency - 1; i++)
			{
				DuelistCard random = DuelistMod.nonRareCards.get(AbstractDungeon.potionRng.random(DuelistMod.nonRareCards.size() - 1));
				packCards.add((DuelistCard) random.makeStatEquivalentCopy()); 
			}
		}
		
		// Randomize, upgrade, and add pack cards to hand
		for (DuelistCard c : packCards)
		{			
			AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(c, true, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));			
		}
	}

	@Override
	public AbstractPotion makeCopy() {
		return new SealedPackE();
	}

	// This is your potency.
	@Override
	public int getPotency(final int potency) 
	{
    	int pot = 3;
    	if (AbstractDungeon.player == null) { return pot; }
        return AbstractDungeon.player.hasRelic("SacredBark") ? pot*2 : pot;
	}
	
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description =  DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

	public void upgradePotion()
	{
		this.potency += 1;
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, this.description));
	}
}
