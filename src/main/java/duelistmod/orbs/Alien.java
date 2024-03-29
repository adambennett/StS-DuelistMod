package duelistmod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.helpers.Util;

public class Alien extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("Alien");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 0.5F; 	
	protected static final float VFX_INTERVAL_TIME = 0.25F;
	
	public Alien() {
		this.setID(ID);
		this.inversion = "Void";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Alien.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = Util.getOrbConfiguredEvoke(ID);
		this.basePassiveAmount = this.passiveAmount = Util.getOrbConfiguredPassive(ID);
		this.configShouldAllowEvokeDisable = true;
		this.configShouldAllowPassiveDisable = true;
		this.configShouldModifyPassive = true;
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		this.allowNegativeFocus = true;
		checkFocus();
		this.updateDescription();
	}

	@Override
	public void updateDescription() {
		applyFocus();
		if (this.passiveAmount == 1) { this.description = DESC[0] + this.passiveAmount + DESC[1]; }
		else { this.description = DESC[0] + this.passiveAmount + DESC[2]; }
	}

	@Override
	public void onEvoke() {
		if (Util.getOrbConfiguredEvokeDisabled(ID)) return;

		int handSize = this.owner.hand().size();
		this.owner.discard(handSize, true, false);
		this.owner.draw(handSize, true);
	}

	public void triggerPassiveEffect() {
		if (Util.getOrbConfiguredPassiveDisabled(ID)) return;

		if (this.passiveAmount > 0) {
			if (this.owner.player()) {
				AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
			}
			this.owner.draw(this.passiveAmount);
			this.owner.discard(1, true, true);
			if (gpcCheck()) {
				this.owner.draw(this.passiveAmount);
				this.owner.discard(1, true, true);
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
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
	public void updateAnimation() {
		applyFocus();
		super.updateAnimation();
		this.angle += Gdx.graphics.getDeltaTime() * 120.0F;
		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) {
			AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
			this.vfxTimer = 0.25F;
		}	
	}

	@Override
	public void playChannelSFX() {
		CardCrawlGame.sound.playV("SPHERE_DETECT_VO_2", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy() {
		return new Alien();
	}
	
	@Override
	public void checkFocus() {
		if (this.owner != null && this.owner.hasPower(FocusPower.POWER_ID))
		{
			if ((this.owner.getPower(FocusPower.POWER_ID).amount > 0) || (this.owner.getPower(FocusPower.POWER_ID).amount + this.originalPassive > 0)) {
				this.basePassiveAmount = this.originalPassive + this.owner.getPower(FocusPower.POWER_ID).amount;
			} else {
				this.basePassiveAmount = 0;
			}	
		} else {
			this.basePassiveAmount = this.originalPassive;
			this.baseEvokeAmount = this.originalEvoke;
		}
		applyFocus();
		updateDescription();
	}
	
	@Override
	protected void renderText(SpriteBatch sb) {
		if (renderInvertText(sb, true) || this.showEvokeValue) {
			int val = this.owner.hand().size() - 1;
			if (val < 0) {
				val = 0;
			}
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(val), this.cX + AbstractOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractOrb.NUM_Y_OFFSET, new Color(0.2f, 1.0f, 1.0f, this.c.a), this.fontScale);
        } else {
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + AbstractOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractOrb.NUM_Y_OFFSET, this.c, this.fontScale);
		}
		
	}

	@Override
	public void applyFocus() {
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}
