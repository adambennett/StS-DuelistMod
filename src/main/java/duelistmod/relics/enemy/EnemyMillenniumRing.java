package duelistmod.relics.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.interfaces.MillenniumItem;

public class EnemyMillenniumRing extends AbstractEnemyDuelistRelic implements MillenniumItem {

	public static final String ID = DuelistMod.makeID("EnemyMillenniumRing");
	public static final String IMG = DuelistMod.makeRelicPath("MillenniumRingRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MillenniumRingRelic_Outline.png");

	public EnemyMillenniumRing() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new EnemyMillenniumRing();
	}
}
