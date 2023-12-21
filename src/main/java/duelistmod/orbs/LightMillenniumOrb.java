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
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.actions.unique.RedMedicineAction;
import duelistmod.helpers.BuffHelper;
import duelistmod.helpers.Util;
import duelistmod.powers.incomplete.HauntedDebuff;
import duelistmod.powers.incomplete.HauntedPower;

public class LightMillenniumOrb extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("LightMillennium");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F;
	
	public LightMillenniumOrb() {
		this.setID(ID);
		this.inversion = "Dark MillenniumOrb";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/LightMillenniumOrb.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = Util.getOrbConfiguredEvoke(ID);
		this.basePassiveAmount = this.passiveAmount = Util.getOrbConfiguredPassive(ID);
		this.configShouldAllowEvokeDisable = true;
		this.configShouldAllowPassiveDisable = true;
		this.configShouldModifyPassive = true;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus();
	}

	@Override
	public void updateDescription() {
		applyFocus();
		if (this.passiveAmount != 1) {
			this.description = DESC[0] + this.passiveAmount + DESC[1];
		} else {
			this.description = DESC[0] + this.passiveAmount + DESC[2];
		}
	}

	@Override
	public void onEvoke() {
		if (Util.getOrbConfiguredEvokeDisabled(ID)) return;

		if (this.owner.hasPower(HauntedPower.POWER_ID)) {
			this.owner.removePowerFromSelf(this.owner.getPower(HauntedPower.POWER_ID));
		}
		if (this.owner.hasPower(HauntedDebuff.POWER_ID)) {
			this.owner.removePowerFromSelf(this.owner.getPower(HauntedDebuff.POWER_ID));
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
		if (Util.getOrbConfiguredPassiveDisabled(ID)) return;

		if (this.passiveAmount > 0) {
			if (this.owner.player()) {
				AbstractMonster m = AbstractDungeon.getRandomMonster();
				if (m != null) {
					AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.1f));
					AbstractDungeon.actionManager.addToTop(new RedMedicineAction(1, m, 3, this.passiveAmount, this.passiveAmount));
				}
			} else if (this.owner.getEnemy() != null) {
				boolean naturia = Util.deckIs("Naturia Deck");
				AbstractPower buff = Util.getChallengeLevel() > 0
						? BuffHelper.randomBuffEnemyChallenge(this.owner.getEnemy(), 0, naturia)
						: BuffHelper.randomBuffEnemy(this.owner.getEnemy(), 0, naturia);
				this.owner.applyPowerToSelf(buff);
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
		this.angle += Gdx.graphics.getDeltaTime() * 180.0F;

		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) {
			AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
			if (MathUtils.randomBoolean()) {
				AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
			}
			float vfxIntervalMin = 0.15F;
			float vfxIntervalMax = 0.8F;
			this.vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
		}
	}

	@Override
	public void playChannelSFX() {
		CardCrawlGame.sound.playV("HEAL_2", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy() {
		return new LightMillenniumOrb();
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
