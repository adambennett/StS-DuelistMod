package duelistmod.orbs;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

@SuppressWarnings("unused")
public class Consumer extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("Consumer");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	public int currentEnergyGain = 0;
	
	public Consumer()
	{
		this.setID(ID);
		this.inversion = "Summoner";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Consumer.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 2;
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
		this.description = DESC[0] + this.evokeAmount + DESC[1];
	}

	@Override
	public void onEvoke()
	{
		applyFocus();
		if (this.currentEnergyGain > 0)
		{
			DuelistCard.applyPowerToSelf(new EnergizedBluePower(AbstractDungeon.player, this.currentEnergyGain));
		}
	}
	
	@Override
	public void onExhaust(AbstractCard c)
	{
		triggerPassiveEffect();
		//if (gpcCheck()) { triggerPassiveEffect(); }
	}
	
	@Override
	public void onEndOfTurn()
	{
		checkFocus(false);
	}

	@Override
	public void onStartOfTurn()
	{
		
	}

	public void triggerPassiveEffect()
	{
		boolean hasOtherOrbs = false;
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		for (AbstractOrb o : AbstractDungeon.player.orbs) { if (!o.equals(this)) { hasOtherOrbs = true; orbs.add(o); }}
		if (hasOtherOrbs && orbs.size() > 0) 
		{ 
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), 0.1f));
			AbstractOrb orb = orbs.get(AbstractDungeon.cardRandomRng.random(orbs.size() - 1));
			AbstractDungeon.actionManager.addToTop(new EvokeSpecificOrbAction(orb));
			if (this.evokeAmount > 0) { this.currentEnergyGain += this.evokeAmount; }
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
		CardCrawlGame.sound.playV("EVENT_PURCHASE", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new Consumer();
	}
	
	@Override
	public void checkFocus(boolean allowNegativeFocus)
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
		{
			if (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount > 0 || AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount + originalEvoke > 0)
			{
				this.baseEvokeAmount = originalEvoke + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
			}
			else
			{
				this.baseEvokeAmount = 0;
			}
		}
		else 
		{
			this.baseEvokeAmount = originalEvoke;
		}
		if (DuelistMod.debug)
		{
			//System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalEvoke: " + originalEvoke + " :: new evoke amount: " + this.baseEvokeAmount);
		}
		applyFocus();
		updateDescription();
	}
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		renderInvertText(sb, true);
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.currentEnergyGain), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
	}
	
	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


