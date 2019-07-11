package duelistmod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.interfaces.*;

@SuppressWarnings("unused")
public class Summoner extends DuelistOrb
{
	public static final String ID = duelistmod.DuelistMod.makeID("Summoner");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;

	public Summoner(int passive)
	{
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Summoner.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 2;
		this.basePassiveAmount = this.passiveAmount = 1;
		if (DuelistMod.challengeMode)
		{
			this.baseEvokeAmount = this.evokeAmount = 1;
			this.basePassiveAmount = this.passiveAmount = 1;
		}
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus(true);
		this.updateDescription();
	}

	@Override
	public void updateDescription()
	{
		applyFocus();
		if (this.passiveAmount > 1) { this.description = DESC[0] + this.passiveAmount + DESC[3] + this.evokeAmount + DESC[2]; }
		else { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2]; }
	}

	@Override
	public void onEvoke()
	{
		if (this.evokeAmount > 0) { DuelistCard.incMaxSummons(AbstractDungeon.player, this.evokeAmount); }
		else if (this.evokeAmount < 0) { DuelistCard.decMaxSummons(AbstractDungeon.player, -this.evokeAmount); }
	}

	@Override
	public void onEndOfTurn()
	{
		if (this.passiveAmount > 0) { this.triggerPassiveEffect(); }
	}
	
	private void triggerPassiveEffect()
	{
		if (!DuelistMod.challengeMode) { DuelistCard.powerSummon(AbstractDungeon.player, this.passiveAmount, "Spellcaster Token", false); }
		else { DuelistCard.powerSummon(AbstractDungeon.player, this.passiveAmount, "Summoner Token", false); }
	}
	

	@Override
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
		this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) 
		{
			AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
			if (MathUtils.randomBoolean()) {
				AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
			}
			this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
		}
	}

	@Override
	public void playChannelSFX()
	{
		
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new Summoner(this.basePassiveAmount);
	}
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		// Render passive amount text
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, this.c, this.fontScale);
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
			this.baseEvokeAmount = this.originalEvoke + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;	
		}
		else
		{
			this.basePassiveAmount = this.originalPassive;
			this.baseEvokeAmount = this.originalEvoke;
		}
		if (DuelistMod.debug)
		{
			System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalPassive: " + originalPassive + " :: new passive amount: " + this.basePassiveAmount);
			System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalEvoke: " + originalEvoke + " :: new evoke amount: " + this.baseEvokeAmount);
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


