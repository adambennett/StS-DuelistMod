package duelistmod.relics.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.MillenniumItem;

public class EnemyMillenniumScale extends AbstractEnemyDuelistRelic implements MillenniumItem {

	public static final String ID = DuelistMod.makeID("EnemyMillenniumScale");
	public static final String IMG = DuelistMod.makeRelicPath("ScalesRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Scales_Outline.png");

	public EnemyMillenniumScale() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public void onSynergyTribute() {
		AnyDuelist duelist = AnyDuelist.from(this);
		DuelistCard.gainTempHP(duelist.creature(), duelist.creature(), 3);
	}

	@Override
	public AbstractRelic makeCopy() {
		return new EnemyMillenniumScale();
	}
}
