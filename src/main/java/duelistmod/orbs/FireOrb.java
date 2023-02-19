package duelistmod.orbs;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.helpers.Util;
import duelistmod.powers.duelistPowers.BurningDebuff;
import duelistmod.powers.incomplete.FlameTigerPower;
import duelistmod.variables.Tags;

public class FireOrb extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("FireOrb");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;

	public FireOrb() {
		this.setID(ID);
		this.inversion = "Water";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/FireOrb.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = Util.getOrbConfiguredEvoke(this.name);
		this.basePassiveAmount = this.passiveAmount = Util.getOrbConfiguredPassive(this.name);
		this.configShouldAllowEvokeDisable = true;
		this.configShouldAllowPassiveDisable = true;
		this.configShouldModifyEvoke = true;
		this.configShouldModifyPassive = true;
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus();
		this.updateDescription();
	}

	@Override
	public void updateDescription() {
		applyFocus();
		this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2];
	}

	@Override
	public void onEvoke() {
		if (Util.getOrbConfiguredEvokeDisabled(this.name)) return;

		if (this.evokeAmount > 0) {
			for (AbstractCard c : this.owner.hand()) {
				if (c.hasTag(Tags.DRAGON)) {
					DuelistCard dragC = (DuelistCard)c;
					dragC.changeTributesInBattle(-this.evokeAmount, true);
				}
			}

			for (AbstractCard c : this.owner.drawPile()) {
				if (c.hasTag(Tags.DRAGON)) {
					DuelistCard dragC = (DuelistCard)c;
					dragC.changeTributesInBattle(-this.evokeAmount, true);
				}
			}

			for (AbstractCard c : this.owner.discardPile()) {
				if (c.hasTag(Tags.DRAGON)) {
					DuelistCard dragC = (DuelistCard)c;
					dragC.changeTributesInBattle(-this.evokeAmount, true);
				}
			}

			for (AbstractCard c : this.owner.exhaustPile()) {
				if (c.hasTag(Tags.DRAGON)) {
					DuelistCard dragC = (DuelistCard)c;
					dragC.changeTributesInBattle(-this.evokeAmount, true);
				}
			}
		}
	}

	public void triggerPassiveEffect() {
		if (Util.getOrbConfiguredPassiveDisabled(this.name)) return;

		if (this.owner.player()) {
			if (this.owner.hasPower(FlameTigerPower.POWER_ID)) {
				if (this.owner.player()) {
					AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.1f));
				}
				if (this.passiveAmount > 0) {
					ArrayList<AbstractMonster> mons = DuelistCard.getAllMonsForFireOrb();
					for (AbstractMonster mon : mons) {
						DuelistCard.staticThornAttack(mon, AttackEffect.FIRE, this.passiveAmount);
						if (gpcCheck()) {
							DuelistCard.staticThornAttack(mon, AttackEffect.FIRE, this.passiveAmount);
						}
					}
				}
			} else {
				ArrayList<AbstractMonster> mons = DuelistCard.getAllMonsForFireOrb();
				if (mons.size() > 0) {
					AbstractMonster mon = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() - 1));
					if (this.passiveAmount > 0 && mon != null) {
						AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.1f));
						DuelistCard.staticThornAttack(mon, AttackEffect.FIRE, this.passiveAmount);
						if (gpcCheck()) {
							DuelistCard.staticThornAttack(mon, AttackEffect.FIRE, this.passiveAmount);
						}
					}
				}
			}
		} else if (this.owner.getEnemy() != null && this.passiveAmount > 0 && !AbstractDungeon.player.hasPower(BurningDebuff.POWER_ID)) {
			this.owner.damage(AbstractDungeon.player, this.owner.creature(), this.passiveAmount, DamageInfo.DamageType.THORNS, AttackEffect.FIRE);
			if (gpcCheck()) {
				this.owner.damage(AbstractDungeon.player, this.owner.creature(), this.passiveAmount, DamageInfo.DamageType.THORNS, AttackEffect.FIRE);
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
		super.updateAnimation();
	}

	@Override
	public void playChannelSFX() {
		CardCrawlGame.sound.playV("theDuelist:FireChannel", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy() {
		return new FireOrb();
	}

	@Override
	protected void renderText(SpriteBatch sb) {
		if (renderInvertText(sb, true) || this.showEvokeValue) {
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
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
