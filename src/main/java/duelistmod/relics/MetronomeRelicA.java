package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.*;
import duelistmod.variables.*;

public class MetronomeRelicA extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MetronomeRelicA");
	public static final String IMG = DuelistMod.makeRelicPath("WoodMetronome.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Metronome_Outline.png");

	public MetronomeRelicA() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.SOLID);
	}
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Metronome Deck")) { return true; }
		else { return false; }
	}
	
	@Override
	public void onUseCard(final AbstractCard targetCard, final UseCardAction useCardAction) 
	{
		if (targetCard.hasTag(Tags.METRONOME))
		{
			DuelistCard met = (DuelistCard) DuelistCard.returnRandomMetronome();
			AbstractMonster m = AbstractDungeon.getRandomMonster();
			if (m != null)
			{
				Util.log("MetronomeRelicA resummoned: " + met.name);
				DuelistCard.resummon(met, m, true);
				this.flash();
			}			
		}
    }

	// Description
	@Override
	public String getUpdatedDescription() 
	{
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MetronomeRelicA();
	}
}
