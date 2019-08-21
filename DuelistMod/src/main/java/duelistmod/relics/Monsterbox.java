package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class Monsterbox extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("Monsterbox");
	public static final String IMG =  DuelistMod.makeRelicPath("AeroRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("AeroRelic_Outline.png");

	public Monsterbox() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}
	
	@Override
    public void onEquip()
    {
		int monsters = 0;
		int upgrades = 0;
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c.hasTag(Tags.MONSTER))
			{				
				 monsters++;
				 upgrades = c.timesUpgraded;
				 AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F)); 
			}
		}
		
		CardTags randType = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1));
		while (randType.equals(Tags.TOON)) { randType = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1)); }
		for (int i = 0; i < monsters; i++)
		{
			AbstractCard c = DuelistCard.returnTrulyRandomFromSet(randType, false);
			while (upgrades > 0 && c.canUpgrade()) { c.upgrade(); upgrades--; }
			AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c.makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
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
		return new Monsterbox();
	}
	
	@Override
	public int getPrice()
	{
		return 0;
	}
	
}
