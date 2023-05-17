package duelistmod.orbs;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistCard;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.actions.enemyDuelist.EnemyDrawActualCardsAction;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.RandomizedOptions;
import duelistmod.dto.builders.RandomizedOptionsBuilder;
import duelistmod.helpers.PuzzleHelper;
import duelistmod.helpers.Util;

public class MillenniumOrb extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("MillenniumOrb");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F;

	public MillenniumOrb() {
		this(-1);
	}

	public MillenniumOrb(int evoke) {
		this.setID(ID);
		this.inversion = "???";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/MillenniumOrb.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = (evoke > 0) ? evoke : Util.getOrbConfiguredEvoke(this.name);
		this.basePassiveAmount = this.passiveAmount = Util.getOrbConfiguredPassive(this.name);
		this.configShouldAllowEvokeDisable = true;
		this.configShouldAllowPassiveDisable = true;
		this.configShouldModifyEvoke = true;
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
		this.description = DESC[0] + this.evokeAmount + DESC[1];
	}

	@Override
	public void onEvoke() {
		if (Util.getOrbConfiguredEvokeDisabled(this.name)) return;

		applyFocus();
		if (doesNotHaveNegativeFocus()) {
			ArrayList<AbstractCard> deckCards = new ArrayList<>();
			ArrayList<String> deckCardNames = new ArrayList<>();
			for (int i = 0; i < 20; i++) {
				int index = TheDuelist.cardPool.group.size() - 1;
				int indexRoll = AbstractDungeon.cardRandomRng.random(index);
				AbstractCard c = TheDuelist.cardPool.group.get(indexRoll).makeStatEquivalentCopy();
				while (deckCardNames.contains(c.name)) {
					indexRoll = AbstractDungeon.cardRandomRng.random(index);
					c = TheDuelist.cardPool.group.get(indexRoll).makeStatEquivalentCopy();
				}
				deckCards.add(c);
				deckCardNames.add(c.name);
			}
			if (this.owner.player()) {
				AbstractDungeon.actionManager.addToTop(new CardSelectScreenIntoHandAction(true, deckCards, true, this.evokeAmount, false, false, false, true, true, true, true,
						0, 3, 0, 2, 0, 1));
			} else if (this.owner.getEnemy() != null) {
				RandomizedOptions options = new RandomizedOptionsBuilder()
						.setUpgrade(false)
						.setExhaustCheck(false)
						.setEtherealCheck(false)
						.setCostChangeCheck(true)
						.setSummonCheck(true)
						.setTributeCheck(true)
						.setDamageBlockRandomize(true)
						.setTributeChangeCombatCheck(true)
						.setSummonChangeCombatCheck(true)
						.setLowCostRoll(0)
						.setHighCostRoll(3)
						.setLowTributeRoll(0)
						.setHighTributeRoll(2)
						.setLowSummonRoll(0)
						.setHighSummonRoll(1)
						.createRandomizedOptions();
				List<AbstractEnemyDuelistCard> selected = this.owner.getEnemy().selectCards(this.evokeAmount, deckCards, options);
				AbstractDungeon.actionManager.addToTop(new EnemyDrawActualCardsAction(this.owner.getEnemy(), selected));
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
		if (Util.getOrbConfiguredPassiveDisabled(this.name) || this.owner.getEnemy() != null) return;

		AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
		PuzzleHelper.runStartOfBattleEffect(true);
	}
	
	@Override
	public void checkFocus() {
		if (this.owner != null && this.owner.hasPower(FocusPower.POWER_ID)) {
			if (this.owner.getPower(FocusPower.POWER_ID).amount > 0 || this.owner.getPower(FocusPower.POWER_ID).amount + originalEvoke > 0) {
				this.baseEvokeAmount = originalEvoke + this.owner.getPower(FocusPower.POWER_ID).amount;
			} else {
				this.baseEvokeAmount = 0;
			}
		} else {
			this.baseEvokeAmount = originalEvoke;
		}
		applyFocus();
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
		this.angle += Gdx.graphics.getDeltaTime() * 180.0F;

		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) {
			AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
			if (MathUtils.randomBoolean()) {
				AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
			}
			float vfxIntervalMax = 0.8F;
			float vfxIntervalMin = 0.15F;
			this.vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
		}
	}

	@Override
	public void playChannelSFX() {
		CardCrawlGame.sound.playV("HEAL_1", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy() {
		return new MillenniumOrb(2);
	}
	
	@Override
	protected void renderText(SpriteBatch sb) {
		renderInvertText(sb, true);
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
	}
	
	@Override
	public void applyFocus() {
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}
