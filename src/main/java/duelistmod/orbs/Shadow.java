package duelistmod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistCard;
import duelistmod.actions.enemyDuelist.EnemyDrawActualCardsAction;
import duelistmod.cards.pools.zombies.VampireFamiliar;
import duelistmod.helpers.Util;
import duelistmod.relics.ZombieRelic;

import java.util.ArrayList;

public class Shadow extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("Shadow");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 0.5F;
	protected static final float VFX_INTERVAL_TIME = 0.25F;

	public Shadow() {
		this.setID(ID);
		this.inversion = "Light";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Shadow.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = Util.getOrbConfiguredEvoke(ID);
		int basePassive = Util.getOrbConfiguredPassive(ID);
		if (this.owner != null && this.owner.hasRelic(ZombieRelic.ID)) {
			basePassive += 2;
		}
		this.basePassiveAmount = this.passiveAmount = basePassive;
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
		if (this.evokeAmount == 1) {
			this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2];
		} else {
			this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[3];
		}
	}

	@Override
	public void onEvoke() {
		if (Util.getOrbConfiguredEvokeDisabled(ID)) return;

		if (this.evokeAmount > 0) {
			if (this.owner.getEnemy() != null) {
				ArrayList<AbstractEnemyDuelistCard> cards = new ArrayList<>();
				for (int i = 0; i < this.evokeAmount; i++) {
					cards.add(AbstractEnemyDuelist.fromCard(new VampireFamiliar()));
				}
				this.addToTop(new EnemyDrawActualCardsAction(this.owner.getEnemy(), cards));
			} else if (this.owner.player()) {
				DuelistCard.addCardToHand(new VampireFamiliar(), this.evokeAmount);
			}
		}
	}

	@Override
	public void onStartOfTurn() {
		applyFocus();
	}

	private int getDmg() {
		applyFocus();
		int dmg = this.passiveAmount;
		dmg += DuelistCard.handleModifyShadowDamage(this.owner);
		return Math.max(dmg, 0);
	}

	public void triggerPassiveEffect() {
		if (Util.getOrbConfiguredPassiveDisabled(ID)) return;

		int dmg = getDmg();
		if (dmg > 0) {
			if (this.owner.player()) {
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
				AbstractMonster target = AbstractDungeon.getRandomMonster();
				if (target != null) {
					DuelistCard.staticThornAttack(target, AttackEffect.POISON, dmg);
					if (gpcCheck()) { DuelistCard.staticThornAttack(target, AttackEffect.POISON, dmg); }
				}
			} else if (this.owner.getEnemy() != null) {
				AbstractCreature target = AbstractDungeon.player;
				if (target != null) {
					this.owner.damage(target, this.owner.creature(), dmg, DamageInfo.DamageType.THORNS, AttackEffect.POISON);
					if (gpcCheck()) {
						this.owner.damage(target, this.owner.creature(), dmg, DamageInfo.DamageType.THORNS, AttackEffect.POISON);
					}
				}
			}
		}
	}

	@Override
	public void onResummon(DuelistCard resummoned) {
		triggerPassiveEffect();
	}

	public void buffShadowDmg(int amt) {
		if (this.owner.player()) {
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
		}
		this.basePassiveAmount += amt;
		this.passiveAmount += amt;
		this.originalPassive += amt;
		updateDescription();
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
	protected void renderText(SpriteBatch sb) {
		if (renderInvertText(sb, true) || this.showEvokeValue) {
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
		} else {
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(getDmg()), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
		}
	}

	@Override
	public void playChannelSFX() {
		CardCrawlGame.sound.playV("theDuelist:ShadowChannel", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy() {
		return new Shadow();
	}

	@Override
	public void applyFocus() {
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}
