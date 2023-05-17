package duelistmod.relics.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.helpers.Util;

public class EnemyDragonRelic extends AbstractEnemyDuelistRelic {

	public static final String ID = DuelistMod.makeID("EnemyDragonRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("DragonStatue.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DragonStatue_Outline.png");

	public EnemyDragonRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void setDescription() {
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	@Override
	public AbstractRelic makeCopy() {
		return new EnemyDragonRelic();
	}
}
