package duelistmod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StormPower;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.helpers.Util;
import duelistmod.powers.duelistPowers.EnemyStormPower;

public class Storm extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("Storm");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F;

	public Storm() {
		this.setID(ID);
		this.inversion = "Lightning";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Storm.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = Util.getOrbConfiguredEvoke(this.name);
		this.basePassiveAmount = this.passiveAmount = Util.getOrbConfiguredPassive(this.name);
		this.configShouldAllowEvokeDisable = true;
		this.configShouldAllowPassiveDisable = true;
		this.configShouldModifyEvoke = true;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		this.originalEvoke = this.baseEvokeAmount;
		this.originalPassive = this.basePassiveAmount;
		checkFocus();
	}

	@Override
	public void updateDescription() {
		applyFocus();
		this.description = DESC[0] + this.evokeAmount + DESC[1];
	}

	@Override
	public void onEvoke() {
		if (Util.getOrbConfiguredEvokeDisabled(this.name)) return;

		if (this.evokeAmount > 0) {
			AbstractPower storm = this.owner.player() ? new StormPower(this.owner.getPlayer(), this.evokeAmount) : this.owner.getEnemy() != null ? new EnemyStormPower(this.owner.creature(), this.evokeAmount) : null;
			if (storm != null) this.owner.applyPowerToSelf(storm);
		}
	}
	
	@Override
	public void onEndOfTurn() {
		checkFocus();
	}

	@Override
	public void onStartOfTurn() {
		if (doesNotHaveNegativeFocus()) {
			triggerPassiveEffect();
		}
	}

	public void triggerPassiveEffect() {
		if (Util.getOrbConfiguredPassiveDisabled(this.name)) return;

		if (this.owner.player()) {
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.1f));
		}
		applyFocus();
		AbstractCard randomPower = DuelistCard.returnTrulyRandomFromTypeInCombat(CardType.POWER, true);
		RandomizedHandAction action = new RandomizedHandAction(randomPower, false, true, false, true, false, false, false, false, 1, 3, 0, 0, 0, 0);
		action.duelist = this.owner;
		AbstractDungeon.actionManager.addToTop(action);
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
	public void playChannelSFX() {
		CardCrawlGame.sound.playV("ORB_LIGHTNING_EVOKE", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy() {
		return new Storm();
	}
	
	@Override
	protected void renderText(SpriteBatch sb) {
		renderInvertText(sb, true);
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
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
			} else{
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
	
	@Override
	public void updateAnimation() {
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
			float vfxIntervalMax = 0.8F;
			float vfxIntervalMin = 0.15F;
			this.vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
		}
		
	}
}
