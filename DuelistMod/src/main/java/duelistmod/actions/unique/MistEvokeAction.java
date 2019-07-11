package duelistmod.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class MistEvokeAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
{
	private DamageInfo info;
	//private static final float DURATION = 0.1F;
	//private static final float POST_ATTACK_WAIT_DUR = 0.1F;

	public MistEvokeAction(DamageInfo info)
	{
		this.info = info;
		setValues(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info);
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.duration = 0.1F;
	}

	public void update()
	{
		if (shouldCancelAction()) 
		{
			this.isDone = true;
			return;
		}

		if (this.duration == 0.1F)
		{
			this.target.damageFlash = true;
			this.target.damageFlashFrames = 4;
			AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.POISON));
		}

		tickDuration();

		if (this.isDone) 
		{
			this.target.tint.color.set(Color.BLUE);
			this.target.tint.changeColor(Color.WHITE.cpy());
			this.target.damage(this.info);
			if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) { AbstractDungeon.actionManager.clearPostCombatActions();  }
			AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
		}
	}
}
