package duelistmod.relics.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.interfaces.MillenniumArmorPlate;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.interfaces.VisitFromAnubisRemovalFilter;

public class EnemyMillenniumArmor extends AbstractEnemyDuelistRelic implements VisitFromAnubisRemovalFilter, MillenniumItem {

	public static final String ID = DuelistMod.makeID("EnemyMillenniumArmor");
	public static final String IMG =  DuelistMod.makeRelicPath("MillenniumArmor.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("MillenniumArmor_Outline.png");
    private static final int armorUp = 8;

	public EnemyMillenniumArmor() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.SOLID);
	}
	
	@Override
	public void atBattleStart()
	{
		this.flash();
		DuelistCard.gainTempHP(AbstractEnemyDuelist.enemyDuelist, AbstractEnemyDuelist.enemyDuelist, armorUp);
	}

	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
		tips.add(new PowerTip(name, description));
        initializeTips();
	}

	@Override
	public boolean canRemove() {
		boolean hasPlates = false;
		for (AbstractRelic rel : AbstractEnemyDuelist.enemyDuelist.relics) {
			if (rel instanceof MillenniumArmorPlate) {
				hasPlates = true;
				break;
			}
		}
		return !hasPlates;
	}

	@Override
	public String getUpdatedDescription() 
	{
		return DESCRIPTIONS[0] + armorUp + DESCRIPTIONS[1];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new EnemyMillenniumArmor();
	}
}
