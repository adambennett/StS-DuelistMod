package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.MonsterEggRelicAction;
import duelistmod.variables.*;

public class MetronomeRelicB extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MetronomeRelicB");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public MetronomeRelicB() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.SOLID);
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
