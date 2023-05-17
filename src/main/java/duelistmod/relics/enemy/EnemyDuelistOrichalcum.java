package duelistmod.relics.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import duelistmod.DuelistCardLibrary;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.cards.other.tokens.RelicToken;

public class EnemyDuelistOrichalcum extends AbstractEnemyDuelistRelic {

	public static final String ID = DuelistMod.makeID("EnemyDuelistOrichalcum");
	public static final String IMG = DuelistMod.makeRelicPath("DuelistOrichalcum.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DuelistOrichalcum_Outline.png");

	public EnemyDuelistOrichalcum() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.HEAVY);
	}
	
	@Override
	public void update()
	{
		super.update();
		if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT))
		{
			if (DuelistCard.getSummons(AbstractEnemyDuelist.enemyDuelist) == 0) { this.beginLongPulse(); }
			else { this.stopPulse(); }
		}
	}

	@Override
	public void onPlayerEndTurn() 
	{
		if (DuelistCard.getSummons(AbstractEnemyDuelist.enemyDuelist) == 0)
		{
			DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new RelicToken());
			DuelistCard.summon(AbstractEnemyDuelist.enemyDuelist, 3, tok);
			this.stopPulse();
			this.addToTop(new RelicAboveCreatureAction(AbstractEnemyDuelist.enemyDuelist, this));
		}
	}
	
	@Override
	public void onTribute(DuelistCard c, DuelistCard d)
	{
		if (DuelistCard.getSummons(AbstractEnemyDuelist.enemyDuelist) == 0) { this.beginLongPulse(); }
		else { this.stopPulse(); }
	}


	@Override
	public void onSummon(DuelistCard card, int amt)
	{
		if (DuelistCard.getSummons(AbstractEnemyDuelist.enemyDuelist) == 0) { this.beginLongPulse(); }
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
		return new EnemyDuelistOrichalcum();
	}
}
