package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.shop.ShopScreen;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.variables.Strings;

public class MerchantNecklace extends CustomRelic implements ClickableRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MerchantNecklace");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public MerchantNecklace() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK);
	}
	
	@Override
	public void onRightClick()
	{
		if (AbstractDungeon.getCurrRoom() instanceof ShopRoom && this.counter > 0)
		{
			setCounter(this.counter - 1);
			ShopScreen shop = AbstractDungeon.shopScreen;
	    	if (shop == null) { return; }
	    	ArrayList<AbstractCard> newColored = new ArrayList<AbstractCard>();
	    	ArrayList<AbstractCard> newColorless = new ArrayList<AbstractCard>();
	    	for (int i = 0; i < 4; i++)
	    	{
	    		newColored.add(DuelistMod.nonPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.nonPowers.size() - 1)).makeCopy());
	    	}
	    	newColored.add(DuelistMod.merchantPendantPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.merchantPendantPowers.size() - 1)).makeCopy());
	    	for (int i = 0; i < 2; i++)
	    	{
	    		newColorless.add(DuelistMod.rareNonPowers.get(AbstractDungeon.cardRandomRng.random(DuelistMod.rareNonPowers.size() - 1)).makeCopy());
	    	}
	    	
	    	shop.init(newColored, newColorless);
	    	
	    	int roll = AbstractDungeon.cardRandomRng.random(1, 3);
	    	if (roll == 1) { shop.applyUpgrades(CardType.ATTACK); }
	    	else if (roll == 2) { shop.applyUpgrades(CardType.SKILL); }
	    	else { shop.applyUpgrades(CardType.POWER); }
	    	
	    	int discountRoll = AbstractDungeon.cardRandomRng.random(1, 4);
	    	float disc = 0.1F * discountRoll;
	    	shop.applyDiscount(disc, true);
	    	shop.update();
		}
	}
	
	@Override
    public void onVictory() 
    {
    	if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) 
        {
            flash();
            setCounter(this.counter + 1);
        }
    }
	
	@Override
    public void onEquip()
    {
		setCounter(2);
		//setCounter(600);
    }

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MerchantNecklace();
	}	
}
