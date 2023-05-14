package duelistmod.relics.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.WhiteOrb;

public class EnemyWhiteBowlRelic extends AbstractEnemyDuelistRelic {

	public static final String ID = DuelistMod.makeID("EnemyWhiteBowlRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("WhiteBowlRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("WhiteBowlRelic_Outline.png");

	public EnemyWhiteBowlRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
	}
	
	@Override
	public void atBattleStart()
	{
		AnyDuelist duelist = AnyDuelist.from(this);
		duelist.channel(new WhiteOrb());
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new EnemyWhiteBowlRelic();
	}
}
