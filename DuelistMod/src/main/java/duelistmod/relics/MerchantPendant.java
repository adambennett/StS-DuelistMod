package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;

public class MerchantPendant extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MerchantPendant");
	public static final String IMG =  DuelistMod.makeRelicPath("AeroRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("AeroRelic_Outline.png");

	public MerchantPendant() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}
	
	@Override
    public void onEquip()
    {
		ShopScreen shop = AbstractDungeon.shopScreen;
    	if (shop == null) { return; }
    	ArrayList<AbstractCard> newColored = new ArrayList<AbstractCard>();
    	ArrayList<AbstractCard> newColorless = new ArrayList<AbstractCard>();
    	for (int i = 0; i < 4; i++)
    	{
    		newColored.add(DuelistMod.rareNonPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.rareNonPowers.size() - 1)).makeCopy());
    	}
    	newColored.add(DuelistMod.allPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.allPowers.size() - 1)).makeCopy());
    	for (int i = 0; i < 2; i++)
    	{
    		newColorless.add(DuelistMod.uncommonCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.uncommonCards.size() - 1)).makeCopy());
    	}
    	
    	shop.init(newColored, newColorless);
    	
    	int roll = AbstractDungeon.cardRandomRng.random(1, 3);
    	if (roll == 1) { shop.applyUpgrades(CardType.ATTACK); }
    	else if (roll == 2) { shop.applyUpgrades(CardType.SKILL); }
    	else { shop.applyUpgrades(CardType.POWER); }
    	
    	int discountRoll = AbstractDungeon.cardRandomRng.random(1, 9);
    	float disc = 0.1F * discountRoll;
    	shop.applyDiscount(disc, true);
    
    	shop.update();
    	
    }

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MerchantPendant();
	}
	
	@Override
	public int getPrice()
	{
		return 0;
	}
	
}
