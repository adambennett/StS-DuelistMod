package duelistmod.orbs;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.pools.zombies.VampireFamiliar;
import duelistmod.dto.DuelistConfigurationData;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Shadow extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("Shadow");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 0.5F; 	
	protected static final float VFX_INTERVAL_TIME = 0.25F;
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	private boolean wasSpawnedWithRelic = false;
	
	public Shadow()
	{
		this.setID(ID);
		this.inversion = "Light";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Shadow.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 1;
		this.basePassiveAmount = this.passiveAmount = 3;
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus(false);
		this.updateDescription();
	}

	@Override
	public DuelistConfigurationData getConfigurations() {
		ArrayList<IUIElement> settingElements = new ArrayList<>();
		RESET_Y();
		LINEBREAK();
		LINEBREAK();
		LINEBREAK();
		LINEBREAK();
		settingElements.add(new ModLabel("Configurations for " + this.name + " not setup yet.", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		return new DuelistConfigurationData(this.name, settingElements);
	}
	
	public Shadow(boolean hasZombieRelic)
	{
		this.inversion = "Light";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Shadow.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 1;
		if (hasZombieRelic) { this.basePassiveAmount = this.passiveAmount = 5; this.wasSpawnedWithRelic = true; }
		else { this.basePassiveAmount = this.passiveAmount = 3; }
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
		if (this.evokeAmount == 1) { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2]; }
		else { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[3]; }
	}

	@Override
	public void onEvoke()
	{
		DuelistCard.addCardToHand(new VampireFamiliar(), this.evokeAmount);
	}

	@Override
	public void onStartOfTurn()
	{
		applyFocus();
	}
	
	private int getDmg()
	{
		applyFocus();
		int dmg = this.passiveAmount;
		dmg += DuelistCard.handleModifyShadowDamage();
		if (dmg > 0) { return dmg; }
		else { return 0; }
	}

	public void triggerPassiveEffect()
	{
		int dmg = getDmg();
		if (dmg > 0) 
		{
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
			AbstractMonster targ = AbstractDungeon.getRandomMonster();
			if (targ != null) { DuelistCard.staticThornAttack(targ, AttackEffect.POISON, dmg); }
			if (gpcCheck()) { DuelistCard.staticThornAttack(targ, AttackEffect.POISON, dmg); }
		}
	}
	
	@Override
	public void onResummon(DuelistCard resummoned)
	{
		triggerPassiveEffect();
	}
	
	public void buffShadowDmg(int amt)
	{
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
		this.basePassiveAmount += amt;
		this.passiveAmount += amt;
		this.originalPassive += amt;
		updateDescription();
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
		this.angle += Gdx.graphics.getDeltaTime() * 120.0F;
		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) 
		{
			AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
			this.vfxTimer = 0.25F;
		}	
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
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(getDmg()), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
		}
	}

	@Override
	public void playChannelSFX()
	{
		CardCrawlGame.sound.playV("theDuelist:ShadowChannel", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new Shadow(this.wasSpawnedWithRelic);
	}
	
	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


