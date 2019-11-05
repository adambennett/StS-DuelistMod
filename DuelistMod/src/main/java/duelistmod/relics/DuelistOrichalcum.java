package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tokens.RelicToken;

public class DuelistOrichalcum extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("DuelistOrichalcum");
	public static final String IMG = DuelistMod.makeRelicPath("DuelistOrichalcum.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DuelistOrichalcum_Outline.png");

	public DuelistOrichalcum() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.HEAVY);
	}
	
	@Override
	public void update()
	{
		super.update();
		if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT))
		{
			if (DuelistCard.getSummons(AbstractDungeon.player) == 0) { this.beginLongPulse(); }
			else { this.stopPulse(); }
		}
	}

	@Override
	public void onPlayerEndTurn() 
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (DuelistCard.getSummons(AbstractDungeon.player) == 0) 
		{
			DuelistCard.summon(p, 3, new RelicToken());
			this.stopPulse();
			this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		}
	}
	
	@Override
	public void onTribute(DuelistCard c, DuelistCard d)
	{
		if (DuelistCard.getSummons(AbstractDungeon.player) == 0) { this.beginLongPulse(); }
		else { this.stopPulse(); }
	}


	@Override
	public void onSummon(DuelistCard card, int amt)
	{
		if (DuelistCard.getSummons(AbstractDungeon.player) == 0) { this.beginLongPulse(); }
		else { this.stopPulse(); }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new DuelistOrichalcum();
	}
}
