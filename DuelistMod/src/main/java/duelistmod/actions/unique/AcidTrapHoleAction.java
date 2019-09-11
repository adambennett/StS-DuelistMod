package duelistmod.actions.unique;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class AcidTrapHoleAction extends AbstractGameAction
{
	private DamageInfo info;
	private int goldAmount = 0;
	private static final float DURATION = 0.1F;
	private static final float POST_ATTACK_WAIT_DUR = 0.1F;
	private boolean skipWait = false; private boolean muteSfx = false;
	private int magicNumber = 0;

	public AcidTrapHoleAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect, int magicNumber) 
	{
		this.info = info;
		setValues(target, info);
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.attackEffect = effect;
		this.duration = 0.1F;
		this.magicNumber = magicNumber;
	}

	public AcidTrapHoleAction(AbstractCreature target, DamageInfo info, int stealGoldAmount, int magicNumber) 
	{
		this(target, info, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, magicNumber);
	}

	public AcidTrapHoleAction(AbstractCreature target, DamageInfo info, int magicNumber)
	{
		this(target, info, AbstractGameAction.AttackEffect.NONE, magicNumber);
	}

	public AcidTrapHoleAction(AbstractCreature target, DamageInfo info, boolean superFast, int magicNumber) 
	{
		this(target, info, AbstractGameAction.AttackEffect.NONE, magicNumber);
		this.skipWait = superFast;
	}

	public AcidTrapHoleAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect, boolean superFast, int magicNumber) 
	{
		this(target, info, effect, magicNumber);
		this.skipWait = superFast;
	}

	public AcidTrapHoleAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect, boolean superFast, boolean muteSfx, int magicNumber)
	{
		this(target, info, effect, superFast, magicNumber);
		this.muteSfx = muteSfx;
	}
	
	private void runUseEffect()
	{
		int damageTotal = 0;
		
		// Acid Trap Function
		int monstersToPull = this.magicNumber;
    	ArrayList<AbstractCard> drawPile = AbstractDungeon.player.drawPile.group;
    	ArrayList<AbstractCard> toDiscard = new ArrayList<AbstractCard>();
    	ArrayList<AbstractCard> randomMonsters = new ArrayList<AbstractCard>();
    	ArrayList<DuelistCard> drawPileMonsters = new ArrayList<DuelistCard>();
    	for (AbstractCard c : drawPile)
    	{
    		if (c.hasTag(Tags.MONSTER))
    		{
    			drawPileMonsters.add((DuelistCard)c);    			
    		}
    	}
    	
    	if (!(drawPileMonsters.size() > this.magicNumber))
    	{
    		monstersToPull = drawPileMonsters.size();    		
    	}
    	
    	for (int i = 0; i < monstersToPull; i++)
    	{
    		DuelistCard random = DuelistCard.returnRandomFromArray(drawPileMonsters);
    		while (randomMonsters.contains(random)) { random = DuelistCard.returnRandomFromArray(drawPileMonsters); }
    		randomMonsters.add(random);
    	}
    	
    	for (int i = 0; i < randomMonsters.size(); i++)
    	{
    		damageTotal += randomMonsters.get(i).baseDamage;
			toDiscard.add(randomMonsters.get(i)); 			
    	}

    	for (AbstractCard c : toDiscard)
    	{
    		AbstractDungeon.player.drawPile.moveToDiscardPile(c);		
    	}
    	// END Acid Trap Function

    	this.info.base += damageTotal;
	}

	public void update()
	{
		if ((shouldCancelAction()) && (this.info.type != DamageInfo.DamageType.THORNS)) 
		{
			this.isDone = true;
			return;
		}

		if (this.duration == 0.1F)
		{
			if ((this.info.type != DamageInfo.DamageType.THORNS) && ((this.info.owner.isDying) || (this.info.owner.halfDead))) 
			{
				this.isDone = true;
				return;
			}

			//this.target.damageFlash = true;
			//this.target.damageFlashFrames = 4;
			AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, this.muteSfx));

			if (this.goldAmount != 0) 
			{
				stealGold();
			}
		}

		tickDuration();

		if (this.isDone) 
		{
			if (this.attackEffect == AbstractGameAction.AttackEffect.POISON) 
			{
				this.target.tint.color.set(Color.CHARTREUSE.cpy());
				this.target.tint.changeColor(Color.WHITE.cpy());
			}
			else if (this.attackEffect == AbstractGameAction.AttackEffect.FIRE) 
			{
				this.target.tint.color.set(Color.RED);
				this.target.tint.changeColor(Color.WHITE.cpy());
			}
			runUseEffect();
			this.info.applyPowers(AbstractDungeon.player, this.target);
			Util.log(this.getClass().getName() + ": this.info.base=" + this.info.base);
			if (this.info.base > 0) { this.target.damage(this.info); }

			if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) 
			{
				AbstractDungeon.actionManager.clearPostCombatActions();
			}
			if ((!this.skipWait) && (!Settings.FAST_MODE)) 
			{
				AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
			}
		}
	}

	private void stealGold()
	{
		if (this.target.gold == 0)
		{
			return;
		}

		CardCrawlGame.sound.play("GOLD_JINGLE");
		if (this.target.gold < this.goldAmount) 
		{
			this.goldAmount = this.target.gold;
		}

		this.target.gold -= this.goldAmount;
		for (int i = 0; i < this.goldAmount; i++) 
		{
			if (this.source.isPlayer) 
			{
				AbstractDungeon.effectList.add(new GainPennyEffect(this.target.hb.cX, this.target.hb.cY));
			}
			else 
			{
				AbstractDungeon.effectList.add(new GainPennyEffect(this.source, this.target.hb.cX, this.target.hb.cY, this.source.hb.cX, this.source.hb.cY, false));
			}
		}
	}
}