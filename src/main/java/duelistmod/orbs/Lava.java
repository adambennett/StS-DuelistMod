package duelistmod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.LavaOrbEruptionResult;
import duelistmod.helpers.Util;
import duelistmod.powers.duelistPowers.FlameKuribohPower;
import duelistmod.powers.duelistPowers.SabatielPower;

import static com.megacrit.cardcrawl.cards.DamageInfo.*;

public class Lava extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("Lava");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 0.5F;
	protected static final float VFX_INTERVAL_TIME = 0.25F;

	public Lava() {
		this(0);
	}

	public Lava(int startingDamage) {
		this.setID(ID);
		this.inversion = "Frost";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Lava.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = startingDamage + Util.getOrbConfiguredEvoke(ID);
		this.basePassiveAmount = this.passiveAmount = Util.getOrbConfiguredPassive(ID);
		this.configShouldAllowEvokeDisable = true;
		this.configShouldAllowPassiveDisable = true;
		this.configShouldModifyEvoke = true;
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
		this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2];
	}

	@Override
	public void onEvoke() {
		applyFocus();
		if (Util.getOrbConfiguredEvokeDisabled(ID)) return;

		if (this.evokeAmount > 0 && this.owner.hand().size() > 0) {
			for (AbstractCard c : this.owner.hand()) {
				int extra = 0;
				if (c instanceof DuelistCard) {
					DuelistCard dc = (DuelistCard)c;
					int eruptionTriggers = 1;
					if (this.owner.hasPower(SabatielPower.POWER_ID)) {
						eruptionTriggers += this.owner.getPower(SabatielPower.POWER_ID).amount;
					}
					for (int i = 0; i < eruptionTriggers; i++) {
						LavaOrbEruptionResult eruption = dc.lavaEvokeEffect();
						extra += eruption.extraDamage();
						if (eruption.erupted()) {
							for (AbstractRelic relic : this.owner.relics()) {
								if (relic instanceof DuelistRelic) {
									((DuelistRelic)relic).onEruption(dc);
								}
							}
						}
					}
				}
				if (this.owner.player()) {
					AbstractMonster m = AbstractDungeon.getRandomMonster();
					if (m != null) {
						DuelistCard.staticThornAttack(m, AttackEffect.FIRE, this.evokeAmount + extra);
					}
				} else if (this.owner.getEnemy() != null) {
					this.owner.damage(AbstractDungeon.player, this.owner.creature(), this.evokeAmount + extra, DamageType.THORNS, AttackEffect.FIRE);
				}
			}
		}
	}

	@Override
	public void onEndOfTurn() {
		checkFocus();
	}

	public void triggerPassiveEffect() {
		if (Util.getOrbConfiguredPassiveDisabled(ID)) return;

		if (this.passiveAmount > 0) {
			boolean flameKuriboh = this.owner.hasPower(FlameKuribohPower.POWER_ID);
			int triggers = flameKuriboh ? 2 : 1;

			for (int i = 0; i < triggers; i++) {

				// Damage
				if (this.owner.player()) {
					AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.1f));
					DuelistCard.attackAllEnemiesFireThorns(this.passiveAmount);
				} else if (this.owner.getEnemy() != null) {
					this.owner.damage(AbstractDungeon.player, this.owner.creature(), this.passiveAmount, DamageType.THORNS, AttackEffect.FIRE);
				}

				// Burn
				if (flameKuriboh) {
					DuelistCard.burnAllEnemies(1, this.owner);
				}
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
		CardCrawlGame.sound.playV("GHOST_ORB_IGNITE_1", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy() {
		return new Lava();
	}

	@Override
	protected void renderText(SpriteBatch sb) {
		if (renderInvertText(sb, true) || this.showEvokeValue) {
			int handS = this.owner.hand().size() - 1;
			if (handS < 0) { handS = 0; }
			String damageString = this.evokeAmount + "x" + handS;
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, damageString, this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
		} else {
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
		}
	}

	@Override
	public void checkFocus() {
		if (this.owner != null && this.owner.hasPower(FocusPower.POWER_ID)) {
			if ((this.owner.getPower(FocusPower.POWER_ID).amount > 0) || (this.owner.getPower(FocusPower.POWER_ID).amount + this.originalPassive > 0)) {
				this.basePassiveAmount = this.originalPassive + this.owner.getPower(FocusPower.POWER_ID).amount;
			} else {
				this.basePassiveAmount = 0;
			}
			if ((this.owner.getPower(FocusPower.POWER_ID).amount > 0) || (this.owner.getPower(FocusPower.POWER_ID).amount + this.originalEvoke > 0)) {
				this.baseEvokeAmount = this.originalEvoke + this.owner.getPower(FocusPower.POWER_ID).amount;
			} else {
				this.baseEvokeAmount = 0;
			}
		} else {
			this.basePassiveAmount = this.originalPassive;
			this.baseEvokeAmount = this.originalEvoke;
		}
		applyFocus();
		updateDescription();
	}

	@Override
	public void applyFocus() {
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}
