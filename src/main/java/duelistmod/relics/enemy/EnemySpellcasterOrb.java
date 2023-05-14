package duelistmod.relics.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnChannelRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.dto.AnyDuelist;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import static com.megacrit.cardcrawl.cards.DamageInfo.DamageType;

public class EnemySpellcasterOrb extends AbstractEnemyDuelistRelic implements OnChannelRelic {

	public static final String ID = DuelistMod.makeID("EnemySpellcasterOrb");
	public static final String IMG = DuelistMod.makeRelicPath("SpellcasterOrb.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("SpellcasterOrb_Outline.png");

	public EnemySpellcasterOrb() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
		setDescription();
	}

	@Override
	public void onChannel(AbstractOrb arg0) {
		AnyDuelist duelist = AnyDuelist.from(this);
		duelist.damage(AbstractDungeon.player, duelist.creature(), 3, DamageType.THORNS, AttackEffect.FIRE);
	}

	@Override
	public void onEquip()
	{
		setDescription();
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	@Override
	public AbstractRelic makeCopy() {
		return new EnemySpellcasterOrb();
	}
}
