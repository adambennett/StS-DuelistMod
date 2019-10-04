package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.variables.*;

public class KaibaToken extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("KaibaToken");
    public static final String IMG = DuelistMod.makeRelicPath("KaibaToken.png");
    public static final String OUTLINE = DuelistMod.makePath(Strings.M_COIN_RELIC_OUTLINE);
	//public boolean cardSelected = false;
	//public DuelistCard cardToAdd = null;

	public KaibaToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void onObtainCard(AbstractCard c)
	{
		if (c.rarity.equals(CardRarity.COMMON) && c.hasTag(Tags.MONSTER))
		{
			AbstractDungeon.player.gainGold(10);
			this.flash();
		}
		
		else if (c.rarity.equals(CardRarity.UNCOMMON) && c.hasTag(Tags.MONSTER))
		{
			AbstractDungeon.player.gainGold(20);
			this.flash();
		}
		
		else if (c.rarity.equals(CardRarity.RARE) && c.hasTag(Tags.MONSTER))
		{
			AbstractDungeon.player.gainGold(30);
			this.flash();
		}
		
		else if (c.hasTag(Tags.MONSTER))
		{
			AbstractDungeon.player.gainGold(40);
			this.flash();
		}
	}
	
	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new KaibaToken();
	}
}
