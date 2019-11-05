package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class YugiMirror extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("YugiMirror");
	public static final String IMG = DuelistMod.makeRelicPath("YugiMirrorRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Mirror_Outline.png");
	//public boolean cardSelected = false;
	//public DuelistCard cardToAdd = null;

	public YugiMirror() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void onEquip()
	{
		boolean hasDuelistCards = false;
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dc = (DuelistCard)c;
				if (dc.baseTributes > 0) { hasDuelistCards = true; break; }
			}
		}
		
		if (hasDuelistCards)
		{
			DuelistCard selected = null;
			boolean found = false;
			int deckSize = AbstractDungeon.player.masterDeck.group.size() - 1;
			AbstractCard deckUpgradedTribs = AbstractDungeon.player.masterDeck.group.get(AbstractDungeon.relicRng.random(deckSize));
			while (!(deckUpgradedTribs instanceof DuelistCard) || !found) 
			{ 
				deckUpgradedTribs = AbstractDungeon.player.masterDeck.group.get(AbstractDungeon.relicRng.random(deckSize)); 
				if (deckUpgradedTribs instanceof DuelistCard)
				{
					selected = (DuelistCard)deckUpgradedTribs;
					if (selected.baseTributes > 0) { found = true; }
				}
			}
			
			if (selected != null)
			{
				DuelistCard finalSelection = (DuelistCard) selected.makeStatEquivalentCopy();
				if (finalSelection.cost > 0) { finalSelection.updateCost(-finalSelection.cost); finalSelection.isCostModified = true; }
		    	AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(finalSelection, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
			}
			
			else if (DuelistMod.debug)
			{
				DuelistMod.logger.info("Yugi Mirror somehow did not generate a Duelist Card, maybe this just crashed your game.. if so, sorry! Please report this bug. I will be interested to know what cards were in your deck.");
			}
		}
		
		else if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Skipped Yugi Mirror effect - it seems you have no Tribute monsters in your deck.");
		}  	
	}
	
	/*
	@Override
	public void update() 
	{
		super.update();
		if (cardSelected && cardToAdd != null) 
		{
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cardToAdd, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
		}
	}
	*/

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new YugiMirror();
	}
}
