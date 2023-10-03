package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.unique.MonsterEggRelicAction;
import duelistmod.variables.Strings;

public class MetronomeRelicB extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MetronomeRelicB");
    public static final String IMG = DuelistMod.makeRelicPath("Metronometry.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Metronometry_Outline.png");

	public MetronomeRelicB() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.SOLID);
	}
	
	@Override
	public void atBattleStart()
	{
		this.flash();
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		cards.add(DuelistCard.returnRandomMetronome());
		cards.add(DuelistCard.returnRandomMetronome());
		cards.add(DuelistCard.returnRandomMetronome());
		//AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToBottom(new MonsterEggRelicAction(cards));
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
		return new MetronomeRelicB();
	}
}
