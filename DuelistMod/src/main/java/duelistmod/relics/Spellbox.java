package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.*;

public class Spellbox extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("Spellbox");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	
	private boolean run = false;

	public Spellbox() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.CLINK);
	}
	
	@Override
    public void onEquip()
    {
		run = true;
    }
	
	@Override
	public void update()
	{
		if (run)
		{
			int monsters = 0;
			int upgrades = 0;
			ArrayList<AbstractCard> toKeep = new ArrayList<AbstractCard>();
			ArrayList<AbstractCard> toKeepFromDeck = new ArrayList<AbstractCard>();
			for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
			{
				if (c.hasTag(Tags.SPELL)) { monsters++; upgrades += c.timesUpgraded; }
				else { toKeepFromDeck.add(c); }
			}
	
			for (int i = 0; i < monsters; i++)
			{
				AbstractCard c = DuelistCard.returnTrulyRandomFromSet(Tags.TRAP, false);
				while (upgrades > 0 && c.canUpgrade()) { c.upgrade(); upgrades--; }
				toKeep.add(c.makeStatEquivalentCopy());
			}
			
			AbstractDungeon.player.masterDeck.group.clear();
			if (AbstractDungeon.player.hasRelic(MarkExxod.ID))
			{
				for (AbstractCard c : toKeepFromDeck) { AbstractDungeon.player.masterDeck.addToTop(c); }
				for (AbstractCard c : toKeep) { AbstractDungeon.player.masterDeck.addToBottom(c); }
			}
			else
			{
				for (AbstractCard c : toKeepFromDeck) { AbstractDungeon.player.masterDeck.addToRandomSpot(c); }
				for (AbstractCard c : toKeep) { AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f)); }
			}
			
			run = false;
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
		return new Spellbox();
	}
	
	@Override
	public int getPrice()
	{
		return 0;
	}
	
}
