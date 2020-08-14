package duelistmod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.*;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.actions.unique.DragonOrbEvokeAction;
import duelistmod.interfaces.*;
import duelistmod.variables.Tags;

@SuppressWarnings("unused")
public class TokenOrb extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("TokenOrb");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F;
	private float vfxIntervalMin = 0.1F;
	private float vfxIntervalMax = 0.4F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	public TokenOrb()
	{
		this.setID(ID);
		this.inversion = "MonsterOrb";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Token.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 2;
		this.basePassiveAmount = this.passiveAmount = 1;
		this.triggersOnSpellcasterPuzzle = false;
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus(false);
		this.updateDescription();
	}

	@Override
	public void updateDescription()
	{
		applyFocus();
		if (this.passiveAmount > 1)
		{
			if (this.evokeAmount == 1)
			{
				this.description = DESC[0] + this.passiveAmount + DESC[3] + this.evokeAmount + DESC[2];
			}
			else
			{
				this.description = DESC[0] + this.passiveAmount + DESC[3] + this.evokeAmount + DESC[4];
			}
			 
		}
		else 
		{ 
			if (this.evokeAmount == 1)
			{
				this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2]; 
			}
			else
			{
				this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[4]; 
			}
			
		}
	}

	@Override
	public void onEvoke()
	{
		if (this.evokeAmount > 0)
		{
			AbstractDungeon.actionManager.addToBottom(new DragonOrbEvokeAction(this.evokeAmount, Tags.MONSTER, 0));
			if (DuelistMod.debug) { System.out.println("theDuelist:MonsterOrb --- > triggered evoke!"); }
		}
	}

	@Override
	public void onStartOfTurn()
	{
		if (this.passiveAmount > 0) { this.triggerPassiveEffect(); }
		//if (gpcCheck() && this.passiveAmount > 0) { this.triggerPassiveEffect(); }
	}

	private void triggerPassiveEffect()
	{
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
		for (int i = 0; i < this.passiveAmount; i++)
		{
			DuelistCard randomMonster = DuelistCardLibrary.getRandomTokenForCombat(true, false, true, false, false, false);
			DuelistCard.addCardToHand(randomMonster);
			if (DuelistMod.debug) { System.out.println("theDuelist:TokenOrb --- > Added: " + randomMonster.name + " to player hand."); }
		}
	}

	@Override
	//Taken from frost orb and modified a bit. Works to draw the basic orb image.
	public void render(SpriteBatch sb) 
	{
		sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
		sb.setBlendFunction(770, 1);
		sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
		sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale + 
		MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F, this.angle, 0, 0, 96, 96, false, false);
		sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.2F, this.scale + 
		MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, -this.angle, 0, 0, 96, 96, false, false);
		sb.setBlendFunction(770, 771);
		sb.setColor(this.c);
		sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle / 12.0F, 0, 0, 96, 96, false, false);
		renderText(sb);
		this.hb.render(sb);
	}
	
	@Override
	public void updateAnimation()
	{
		applyFocus();
		super.updateAnimation();
		this.angle += Gdx.graphics.getDeltaTime() * 45.0F;

		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) 
		{
			AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(this.cX, this.cY));
			this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
		}
	}

	@Override
	public void playChannelSFX()
	{
		CardCrawlGame.sound.playV("MONSTER_AWAKENED_POUNCE", 4.0F);
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new TokenOrb();
	}
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		if (renderInvertText(sb, true) || this.showEvokeValue)
		{
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
		}
		else if (!this.showEvokeValue)
		{
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
		}
	}
	
	@Override
	public void checkFocus(boolean allowNegativeFocus) 
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
		{
			if ((AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount > 0) || (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount + this.originalPassive > 0))
			{
				this.basePassiveAmount = this.originalPassive + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
			}
			
			else
			{
				this.basePassiveAmount = 0;
			}
			
			
			if ((AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount > 0) || (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount + this.originalEvoke > 0))
			{
				this.baseEvokeAmount = this.originalEvoke + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
			}
			
			else
			{
				this.baseEvokeAmount = 0;
			}
			
		}
		else
		{
			this.basePassiveAmount = this.originalPassive;
			this.baseEvokeAmount = this.originalEvoke;
		}
		if (DuelistMod.debug)
		{
			//System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalPassive: " + originalPassive + " :: new passive amount: " + this.basePassiveAmount);
			//System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalEvoke: " + originalEvoke + " :: new evoke amount: " + this.baseEvokeAmount);
		}
		applyFocus();
		updateDescription();
	}
	
	
	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


