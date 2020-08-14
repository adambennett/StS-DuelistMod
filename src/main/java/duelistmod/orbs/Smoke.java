package duelistmod.orbs;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.combat.*;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.interfaces.*;
import duelistmod.variables.Tags;

@SuppressWarnings("unused")
public class Smoke extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("Smoke");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	private int currentEvokeDmg = 0;
	private ArrayList<UUID> savedhand = new ArrayList<UUID>();
	
	public Smoke()
	{
		this.setID(ID);
		this.inversion = "Air";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Smoke.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 2;
		this.basePassiveAmount = this.passiveAmount = 4;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus(true);
	}

	@Override
	public void updateDescription()
	{
		applyFocus();
		this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2];
	}
	
	@Override
	public void update()
	{
		super.update();
		if (AbstractDungeon.player != null && AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT))
		{
			int current = 0;
			for (AbstractCard c : AbstractDungeon.player.hand.group)
			{
				if (c instanceof DuelistCard && c.hasTag(Tags.MONSTER))
				{
					DuelistCard dc = (DuelistCard)c;
					if (dc.isTributeCard(true)) { current += dc.tributes; }
				}
				
				if (c.costForTurn > 0) { current += c.costForTurn; }
			}
			currentEvokeDmg = current * this.evokeAmount;
		}
	}

	@Override
	public void onEvoke()
	{
		applyFocus();
		if (currentEvokeDmg > 0)
		{
			AbstractDungeon.actionManager.addToTop(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, currentEvokeDmg, DamageInfo.DamageType.THORNS), this, true));
		}
	}
	
	@Override
	public void onEndOfTurn()
	{
		checkFocus(true);
	}

	@Override
	public void onStartOfTurn()
	{
		
	}

	public void triggerPassiveEffect(DuelistCard c)
	{
		if (c.hasTag(Tags.MONSTER))
		{
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
			if (this.passiveAmount > 0) { AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(c.uuid, this.passiveAmount)); }
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
		if (this.vfxTimer < 0.0F) {
			AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
			if (MathUtils.randomBoolean()) {
				AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
			}
			this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
		}
	}

	@Override
	public void playChannelSFX()
	{
		CardCrawlGame.sound.playV("ORB_DARK_CHANNEL", 5.0F);
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new Smoke();
	}
	
	@Override
	protected void renderText(SpriteBatch sb)
	{	
		if (renderInvertText(sb, true) || this.showEvokeValue)
		{
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.currentEvokeDmg), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
		}
		else if (!this.showEvokeValue)
		{
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
		}
	}

	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


