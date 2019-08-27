package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.StarterDeckSetup;
import duelistmod.variables.*;

public class MetronomeRelicC extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MetronomeRelicC");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public MetronomeRelicC() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.SOLID);
	}
	
	@Override
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		boolean hasMetronomes = false;
		if (AbstractDungeon.player != null) { for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c.hasTag(Tags.METRONOME)) { hasMetronomes = true; break; }}}
		if (deck.equals("Metronome Deck") || hasMetronomes) { return true; }
		else { return false; }
	}
	
	@Override
	public void onUseCard(final AbstractCard targetCard, final UseCardAction useCardAction) 
	{
		if (targetCard.hasTag(Tags.METRONOME))
		{
			DuelistCard.staticBlock(2);
			this.flash();
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
		return new MetronomeRelicC();
	}
}
