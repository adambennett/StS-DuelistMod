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
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.EnemyDuelistCard;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.actions.enemyDuelist.EnemyDrawActualCardsAction;
import duelistmod.actions.unique.DragonOrbEvokeAction;
import duelistmod.cards.MonsterEgg;
import duelistmod.dto.RandomizedOptions;
import duelistmod.dto.builders.RandomizedOptionsBuilder;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class MonsterOrb extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("MonsterOrb");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F;

	public MonsterOrb() {
		this.setID(ID);
		this.inversion = "TokenOrb";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Monster.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = Util.getOrbConfiguredEvoke(ID);
		this.basePassiveAmount = this.passiveAmount = Util.getOrbConfiguredPassive(ID);
		this.configShouldAllowEvokeDisable = true;
		this.configShouldAllowPassiveDisable = true;
		this.configShouldModifyEvoke = true;
		this.configShouldModifyPassive = true;
		this.triggersOnSpellcasterPuzzle = false;
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
		if (this.passiveAmount > 1) {
			if (this.evokeAmount == 1) {
				this.description = DESC[0] + this.passiveAmount + DESC[3] + this.evokeAmount + DESC[2];
			} else {
				this.description = DESC[0] + this.passiveAmount + DESC[3] + this.evokeAmount + DESC[4];
			}
		} else {
			if (this.evokeAmount == 1) {
				this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2];
			} else {
				this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[4];
			}
		}
	}

	@Override
	public void onEvoke() {
		if (Util.getOrbConfiguredEvokeDisabled(ID)) return;

		if (this.evokeAmount > 0) {
			AbstractDungeon.actionManager.addToBottom(new DragonOrbEvokeAction(this.evokeAmount, Tags.MONSTER, 0, this.owner));
		}
	}

	@Override
	public void onStartOfTurn() {
		if (this.passiveAmount > 0) {
			this.triggerPassiveEffect();
		}
	}

	public void triggerPassiveEffect() {
		if (Util.getOrbConfiguredPassiveDisabled(ID)) return;

		if (this.owner.player()) {
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
		}
		for (int i = 0; i < this.passiveAmount; i++) {
			DuelistCard randomMonster = new MonsterEgg();
			if (this.owner.player()) {
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomMonster, false, true, true, true, false, false, false, false,
						0, 0, 0, 0, 0, 0));
			} else {
				RandomizedOptions options = new RandomizedOptionsBuilder()
						.setUpgrade(false)
						.setEtherealCheck(true)
						.setExhaustCheck(true)
						.setCostChangeCheck(true)
						.setTributeCheck(false)
						.setSummonCheck(false)
						.setTributeChangeCombatCheck(false)
						.setSummonChangeCombatCheck(false)
						.setLowCostRoll(0)
						.setHighCostRoll(0)
						.setLowTributeRoll(0)
						.setLowSummonRoll(0)
						.setHighSummonRoll(0)
						.createRandomizedOptions();
				ArrayList<EnemyDuelistCard> card = new ArrayList<>();
				card.add(AbstractEnemyDuelist.fromCard(Util.randomize(randomMonster, options)));
				AbstractDungeon.actionManager.addToTop(new EnemyDrawActualCardsAction(this.owner.getEnemy(), card));
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
		this.angle += Gdx.graphics.getDeltaTime() * 45.0F;

		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) {
			AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(this.cX, this.cY));
			float vfxIntervalMax = 0.4F;
			float vfxIntervalMin = 0.1F;
			this.vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
		}
	}

	@Override
	public void playChannelSFX() {
		CardCrawlGame.sound.playV("MAW_DEATH", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy() {
		return new MonsterOrb();
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
