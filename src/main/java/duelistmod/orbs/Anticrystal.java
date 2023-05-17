package duelistmod.orbs;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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

import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class Anticrystal extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("Anticrystal");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F;
	private int evokeIndex;
	private ArrayList<String> evokeDescriptions = new ArrayList<>();
	
	public Anticrystal() {
		this.setID(ID);
		this.inversion = "Crystal";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Anticrystal.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = Util.getOrbConfiguredEvoke(this.name);
		this.basePassiveAmount = this.passiveAmount = Util.getOrbConfiguredPassive(this.name);
		this.configShouldAllowEvokeDisable = true;
		this.configShouldAllowPassiveDisable = true;
		this.configShouldModifyEvoke = true;
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
		this.description = DESC[0] + 1 + DESC[1] + this.passiveAmount + DESC[2] + evokeDescriptions.get(evokeIndex);
	}
	
	public void updateEvokeValues() {
		int tempHP = this.evokeAmount * 5;
		evokeDescriptions = new ArrayList<>();
		evokeDescriptions.add(DESC[3]);
		evokeDescriptions.add(DESC[4] + this.evokeAmount + DESC[5]);
		evokeDescriptions.add(DESC[6] + tempHP + DESC[7]);
	}

	@Override
	public void onEvoke() {
		if (Util.getOrbConfiguredEvokeDisabled(this.name)) return;

		if (this.evokeAmount > 0) {
			switch (evokeIndex) {
				// 2 randomized Spells to hand
				case 0:
					for (int i = 0; i < 2; i++) {
						DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false);
						while (randomMonster.hasTag(Tags.OJAMA)) {
							randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false);
						}
						RandomizedHandAction action = new RandomizedHandAction(randomMonster, false, true, true, true, false, false, false, false, 0, 3, 0, 0, 0, 0);
						action.duelist = this.owner;
						AbstractDungeon.actionManager.addToTop(action);
					}					
					break;
					
				// Draw 2 cards
				case 1:
					this.owner.draw(this.evokeAmount);
					break;
					
				// Gain 10 Temp HP
				case 2:
					DuelistCard.gainTempHP(this.owner.creature(), this.owner.creature(), this.evokeAmount * 5);
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

	public void triggerPassiveEffect()
	{
		if (Util.getOrbConfiguredPassiveDisabled(this.name)) return;

		if (doesNotHaveNegativeFocus() || this.passiveAmount > 0) {
			if (this.owner.player()) {
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
			}
			this.owner.draw(1);
			DuelistCard.gainTempHP(this.owner.creature(), this.owner.creature(), this.passiveAmount);
			DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false);
			while (randomMonster.hasTag(Tags.OJAMA)) {
				randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false);
			}
			RandomizedHandAction action = new RandomizedHandAction(randomMonster, false, true, true, true, false, false, false, false, 0, 3, 0, 0, 0, 0);
			action.duelist = this.owner;
			AbstractDungeon.actionManager.addToTop(action);
		} else {
			if (this.owner.player()) {
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
			}
			this.owner.draw(1);
			DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false);
			while (randomMonster.hasTag(Tags.OJAMA)) {
				randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false);
			}
			RandomizedHandAction action = new RandomizedHandAction(randomMonster, false, true, true, true, false, false, false, false, 0, 3, 0, 0, 0, 0);
			action.duelist = this.owner;
			AbstractDungeon.actionManager.addToTop(action);
		}
	}
	
	@Override
	public void onStartOfTurn() {
		triggerPassiveEffect();
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
		CardCrawlGame.sound.playV("ORB_FROST_CHANNEL", 1.0F);
	}
	
	
	@Override
	protected void renderText(SpriteBatch sb) {
		if (renderInvertText(sb, true) || this.showEvokeValue) {
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
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
	public AbstractOrb makeCopy() {
		return new Anticrystal();
	}

	@Override
	public void applyFocus() {
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}
