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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.helpers.DebuffHelper;
import duelistmod.helpers.Util;
import duelistmod.powers.incomplete.HauntedDebuff;
import duelistmod.powers.incomplete.HauntedPower;

public class DarkMillenniumOrb extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("DarkMillennium");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 0.5F; 	
	protected static final float VFX_INTERVAL_TIME = 0.25F;
	
	public DarkMillenniumOrb() {
		this.setID(ID);
		this.inversion = "Light MillenniumOrb";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/DarkMillenniumOrb.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = Util.getOrbConfiguredEvoke(this.name);
		this.basePassiveAmount = this.passiveAmount = Util.getOrbConfiguredPassive(this.name);
		this.configShouldAllowEvokeDisable = true;
		this.configShouldAllowPassiveDisable = true;
		this.configShouldModifyPassive = true;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		this.allowNegativeFocus = true;
		checkFocus();
	}

	@Override
	public void updateDescription() {
		applyFocus();
		if (this.passiveAmount != 1) {
			this.description = DESC[0] + this.passiveAmount + DESC[1];
		}
		else {
			this.description = DESC[0] + this.passiveAmount + DESC[2];
		}
	}

	@Override
	public void onEvoke() {
		applyFocus();
		if (Util.getOrbConfiguredEvokeDisabled(this.name)) return;

		// If not Haunted, become Haunted
		if (!(this.owner.hasPower(HauntedPower.POWER_ID) || this.owner.hasPower(HauntedDebuff.POWER_ID))) {
			DuelistCard.applyPowerToSelf(new HauntedDebuff(this.owner.creature(), this.owner.creature(), 2));
		} else {
			if (this.owner.hasPower(HauntedPower.POWER_ID)) {
				AbstractPower pow = this.owner.getPower(HauntedPower.POWER_ID);
				pow.amount++;
				pow.updateDescription();
			}
			if (this.owner.hasPower(HauntedDebuff.POWER_ID)) {
				AbstractPower pow = this.owner.getPower(HauntedDebuff.POWER_ID);
				pow.amount++;
				pow.updateDescription();
			}
		}
	}
	
	@Override
	public void onEndOfTurn() {
		checkFocus();
	}

	@Override
	public void onStartOfTurn() {
		triggerPassiveEffect();
	}

	public void triggerPassiveEffect() {
		if (Util.getOrbConfiguredPassiveDisabled(this.name)) return;

		if (this.passiveAmount > 0) {
			if (this.owner.player()) {
				AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
				for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
					DuelistCard.applyPower(DebuffHelper.getRandomDebuff(AbstractDungeon.player, m, this.passiveAmount), m);
				}
			} else if (this.owner.getEnemy() != null) {
				AbstractPower debuff = DebuffHelper.getRandomPlayerDebuff(AbstractDungeon.player, this.passiveAmount);
				this.owner.applyPower(AbstractDungeon.player, this.owner.creature(), debuff);
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
		CardCrawlGame.sound.playV("ORB_DARK_CHANNEL", 1.0F);
	}
	
	@Override
	public AbstractOrb makeCopy() {
		return new DarkMillenniumOrb();
	}
	
	@Override
	protected void renderText(SpriteBatch sb) {
		renderInvertText(sb, true);
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
	}

	@Override
	public void applyFocus() {
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}
