package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class DeskbotSevenAction extends AbstractGameAction 
{
	private DamageInfo info;
	
	public DeskbotSevenAction(final AbstractMonster target, final DamageInfo info) 
	{
		this.setValues(target, this.info = info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_VERTICAL;
        this.duration = 0.01f;
	}
	
	@Override
	public void update() 
	{
		if (this.target == null) {
            this.isDone = true;
            return;
        }
		if (AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID)) 
		{
            if (this.duration == 0.01f && this.target != null && this.target.currentHealth > 0) 
            {
                if (this.info.type != DamageInfo.DamageType.THORNS && this.info.owner.isDying) 
                {
                    this.isDone = true;
                    return;
                }
               AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            }
            this.tickDuration();
            if (this.isDone && this.target != null && this.target.currentHealth > 0) 
            {
            	for (int i = 0; i < AbstractDungeon.player.getPower(ArtifactPower.POWER_ID).amount; i++) {
            		this.target.damage(this.info);
            	}
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) 
                {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
                this.addToTop(new WaitAction(0.1f));
            }
        }
        else { this.isDone = true; }
	}	
}
