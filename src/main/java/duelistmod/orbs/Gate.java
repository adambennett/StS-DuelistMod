package duelistmod.orbs;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.actions.enemyDuelist.EnemyLightningOrbPassiveAction;
import duelistmod.helpers.Util;

public class Gate extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("Gate");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private int evokeIndex;
	private ArrayList<String> evokeDescriptions = new ArrayList<>();
	
	public Gate() {
		this.setID(ID);
		this.inversion = "MillenniumOrb";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Gate.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = Util.getOrbConfiguredEvoke(ID);
		this.basePassiveAmount = this.passiveAmount = Util.getOrbConfiguredPassive(ID);
		this.configShouldAllowEvokeDisable = true;
		this.configShouldAllowPassiveDisable = true;
		this.configShouldModifyPassive = true;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		this.evokeIndex = ThreadLocalRandom.current().nextInt(0, 3);
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus();
		updateEvokeValues();
	}

	@Override
	public void updateDescription() {
		applyFocus();
		updateEvokeValues();
		this.description = DESC[0] + this.passiveAmount + DESC[1] + (this.passiveAmount + 1) + DESC[2] + evokeDescriptions.get(evokeIndex);
	}
	
	public void updateEvokeValues() {
		int block = (this.passiveAmount + 1) * 2;
		int dmg = this.passiveAmount * 2;
		evokeDescriptions = new ArrayList<>();
		evokeDescriptions.add(DESC[3]);
		evokeDescriptions.add(DESC[4] + block + DESC[5]);
		evokeDescriptions.add(DESC[6] + dmg + DESC[7]);
	}

	@Override
	public void onEvoke() {
		if (Util.getOrbConfiguredEvokeDisabled(ID)) return;

		if (this.evokeAmount > 0) {
			switch (evokeIndex) {
				// Gain energy
				case 0:
					this.owner.gainEnergy(2);
					break;
				case 1:
					this.owner.block((this.passiveAmount + 1) * 2);
					break;
				case 2:
					if (this.owner.player()) {
						AbstractDungeon.actionManager.addToTop(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount * 2, DamageInfo.DamageType.THORNS), this, true));
					} else  if (this.owner.getEnemy() != null) {
						AbstractDungeon.actionManager.addToTop(new EnemyLightningOrbPassiveAction(new DamageInfo(AbstractEnemyDuelist.enemyDuelist, this.passiveAmount * 2, DamageInfo.DamageType.THORNS), this, false));
					}
					break;
			}
		}
	}

	@Override
	public void onEndOfTurn() {
		evokeIndex++;
		if (evokeIndex > 2) {
			evokeIndex = 0;
		}
		checkFocus();
		updateDescription();
	}

	public void triggerPassiveEffect() {
		if (Util.getOrbConfiguredPassiveDisabled(ID)) return;

		if (doesNotHaveNegativeFocus()) {
			if (this.owner.player()) {
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
				AbstractDungeon.actionManager.addToTop(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), this, true));
			} else if (this.owner.getEnemy() != null) {
				AbstractDungeon.actionManager.addToTop(new EnemyLightningOrbPassiveAction(new DamageInfo(AbstractEnemyDuelist.enemyDuelist, this.passiveAmount, DamageInfo.DamageType.THORNS), this, false));
			}
			this.owner.gainEnergy(1);
			this.owner.block(this.passiveAmount + 1);
		} else if (this.passiveAmount > 0) {
			if (this.owner.player()) {
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
				AbstractDungeon.actionManager.addToTop(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), this, true));
			} else if (this.owner.getEnemy() != null) {
				AbstractDungeon.actionManager.addToTop(new EnemyLightningOrbPassiveAction(new DamageInfo(AbstractEnemyDuelist.enemyDuelist, this.passiveAmount, DamageInfo.DamageType.THORNS), this, false));
			}
			this.owner.block(this.passiveAmount + 1);
		} else if (this.passiveAmount + 1 > 0) {
			if (this.owner.player()) {
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
			}
			this.owner.block(this.passiveAmount + 1);
		}
	}
	
	@Override
	public void onStartOfTurn() {
		triggerPassiveEffect();
	}

	@Override
	public void updateAnimation() {
		super.updateAnimation();
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
		this.hb.render(sb);
	}

	@Override
	public void playChannelSFX() {
		CardCrawlGame.sound.playV("theDuelist:GateChannel", 1.0F);
	}

	@Override
	protected void renderText(SpriteBatch sb) {
		if (renderInvertText(sb, true) || this.showEvokeValue) {
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
		}
	}
		
	@Override
	public AbstractOrb makeCopy() {
		return new Gate();
	}

	@Override
	public void applyFocus() {
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}
