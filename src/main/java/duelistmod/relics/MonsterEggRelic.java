package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.actions.unique.MonsterEggRelicAction;
import duelistmod.cards.MonsterEggSpecial;

public class MonsterEggRelic extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MonsterEggRelic");
	public static final String IMG = DuelistMod.makeRelicPath("MonsterEggRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("MonsterEggRelic_Outline.png");

	public MonsterEggRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.SOLID);
	}
	
	@Override
	public void atBattleStart()
	{
		this.flash();
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		cards.add(new MonsterEggSpecial());
		cards.add(new MonsterEggSpecial());
		//AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToBottom(new MonsterEggRelicAction(cards));
		this.grayscale = true;
	}
	
	@Override
    public void onVictory() 
    {
		this.grayscale = false;
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
		return new MonsterEggRelic();
	}
}
