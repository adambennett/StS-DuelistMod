package duelistmod.orbs;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;

import basemod.interfaces.CloneablePowerInterface;
import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

@SuppressWarnings("unused")
public class DuelistGlass extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("GlassOrb");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private float vfxTimer = 0.5F; 	
	protected static final float VFX_INTERVAL_TIME = 0.25F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	public DuelistGlass()
	{
		this.setID(ID);
		this.inversion = "Sand";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Glass.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 0;
		this.basePassiveAmount = this.passiveAmount = 0;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus(false);
	}
	
	@Override
	public void updateDescription()
	{
		applyFocus();
		this.description = DESC[0]; 
	}

	@Override
	public void onEvoke()
	{
		applyFocus();
		ArrayList<Integer> enemyBuffs = new ArrayList<Integer>();
		for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
		{
			if (mon.powers.size() > 0)
			{
				for (AbstractPower p : mon.powers)
				{
					if (p instanceof StrengthPower)
					{
						enemyBuffs.add(p.amount);
					}
				}
			}
		}
		
		if (enemyBuffs.size() > 0)
		{
			DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, enemyBuffs.get(AbstractDungeon.cardRandomRng.random(enemyBuffs.size() - 1))));
		}		
	}

	@Override
	public void onStartOfTurn()
	{
		
	}

	private void triggerPassiveEffect()
	{
		
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
	}

	@Override
	public void playChannelSFX()
	{
		CardCrawlGame.sound.playV("POTION_DROP_2", 1.0F);
	}
	
	@Override
	public void checkFocus(boolean a)
	{
		
	}
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		renderInvertText(sb, false);
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new DuelistGlass();
	}

	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


