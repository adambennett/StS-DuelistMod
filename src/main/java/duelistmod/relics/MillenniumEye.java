package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.enums.CardPoolType;
import duelistmod.enums.StartingDeck;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.powers.ToonWorldPower;

public class MillenniumEye extends DuelistRelic implements MillenniumItem {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumEye");
	public static final String IMG = DuelistMod.makeRelicPath("MillenniumEyeRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MillenniumEyeRelic_Outline.png");

	public MillenniumEye() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
        return DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveToons() && (DuelistMod.cardPoolType == CardPoolType.DECK_BASIC_2_RANDOM || DuelistMod.cardPoolType == CardPoolType.ALL_CARDS);
	}

	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
		if (!(AbstractDungeon.player.hasRelic(MillenniumSymbol.ID) && StartingDeck.currentDeck == StartingDeck.TOON))
		{
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ToonWorldPower(AbstractDungeon.player, AbstractDungeon.player, 0)));
		}
		this.grayscale = true;
	}
	
	@Override
    public void onVictory() 
    {
		this.grayscale = false;
    }

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumEye();
	}

}
