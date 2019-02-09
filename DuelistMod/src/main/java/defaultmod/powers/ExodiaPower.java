package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.cards.ExodiaHead;


public class ExodiaPower extends AbstractPower 
{
	public AbstractCreature source;
	public static final String POWER_ID = defaultmod.DefaultMod.makeID("ExodiaPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DefaultMod.makePath(DefaultMod.EXODIA_POWER);
	public static CustomCard ATTACHED_AXE = null;
	private static int DAMAGE = 7;
	
	public ExodiaPower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.updateDescription();
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = newAmount;
	}

	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		int playerSummons = 1;
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID)) 
		{ 
			playerSummons = ExodiaHead.getSummons(AbstractDungeon.player); 
			playerSummons += ExodiaHead.getMaxSummons(AbstractDungeon.player); 
		}
		if (amount >= 5)
		{
			int[] catapultDmg = new int[] {DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE};
			for (int i = 0; i < catapultDmg.length; i++) { catapultDmg[i] = DAMAGE * playerSummons; }
        	AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(this.owner, catapultDmg, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH));
        	amount = 0;
		}
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}
