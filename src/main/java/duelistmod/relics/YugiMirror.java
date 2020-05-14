package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Tags;

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
		ArrayList<DuelistCard> list = new ArrayList<>();
		ArrayList<DuelistCard> exclusionsList = new ArrayList<>();
		ArrayList<DuelistCard> secondExclusionsList = new ArrayList<>();
		ArrayList<DuelistCard> thirdExclusionsList = new ArrayList<>();
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dc = (DuelistCard)c;
				if (dc.isTributeCard()) 
				{ 
					list.add((DuelistCard) c); 
					if (!c.hasTag(Tags.NO_YUGI_MIRROR)) 
					{
						exclusionsList.add((DuelistCard) c);
						if (c.cost > 0)
						{
							thirdExclusionsList.add((DuelistCard) c);
						}
					}
					
					if (c.cost > 0)
					{
						secondExclusionsList.add((DuelistCard) c);
					}
				}
			}
		}
		
		DuelistCard selected = null;
		if (thirdExclusionsList.size() > 0)
		{
			selected = thirdExclusionsList.get(AbstractDungeon.cardRandomRng.random(thirdExclusionsList.size() - 1));
		}
		else if (secondExclusionsList.size() > 0)
		{
			selected = secondExclusionsList.get(AbstractDungeon.cardRandomRng.random(secondExclusionsList.size() - 1));
		}
		else if (exclusionsList.size() > 0)
		{
			selected = exclusionsList.get(AbstractDungeon.cardRandomRng.random(exclusionsList.size() - 1));
		}
		else if (list.size() > 0)
		{
			selected = list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
		}
		
		if (selected != null)
		{
			DuelistCard finalSelection = (DuelistCard) selected.makeStatEquivalentCopy();
			if (finalSelection.cost > 0) { finalSelection.permUpdateCost(-finalSelection.cost); finalSelection.isCostModified = true; }
	    	AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(finalSelection, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
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
		return new YugiMirror();
	}
}
