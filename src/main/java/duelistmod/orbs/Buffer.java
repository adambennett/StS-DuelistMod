package duelistmod.orbs;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.helpers.Util;
import duelistmod.powers.SummonPower;
import duelistmod.powers.TwoJamPower;
import duelistmod.powers.incomplete.HauntedDebuff;
import duelistmod.powers.incomplete.HauntedPower;
import duelistmod.variables.Tags;

public class Buffer extends DuelistOrb {
	public static final String ID = DuelistMod.makeID("Buffer");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 0.5F;
	protected static final float VFX_INTERVAL_TIME = 0.25F;

	public Buffer() {
		this.setID(ID);
		this.inversion = "Reducer";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Earth.png"));
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
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus();
	}

	@Override
	public void updateDescription() {
		applyFocus();
		this.description = DESC[0] + this.passiveAmount + DESC[1];
	}

	@Override
	public void onEvoke() {
		if (Util.getOrbConfiguredEvokeDisabled(this.name)) return;

		if (doesNotHaveNegativeFocus() || this.evokeAmount + getCurrentFocus() > 0) {
			AbstractPower pow = new BufferPower(this.owner.creature(), this.evokeAmount);
			this.owner.applyPowerToSelf(pow);
		}
	}

	@Override
	public void onStartOfTurn() {
		applyFocus();
		int r = AbstractDungeon.cardRandomRng.random(1, 100);
		int c = 10;
		if (this.owner.hasPower(SummonPower.POWER_ID)) {
			SummonPower instance = (SummonPower) this.owner.getPower(SummonPower.POWER_ID);
			if (instance.isEveryMonsterCheck(Tags.SPELLCASTER, false)) {
				c = 70;
			}
		}
		if (r < c) {
			if (doesNotHaveNegativeFocus()) {
				this.triggerPassiveEffect();
			} else if (this.passiveAmount > 0) {
				this.triggerPassiveEffect();
			}
		}
	}

	public void triggerPassiveEffect() {
		if (Util.getOrbConfiguredPassiveDisabled(this.name)) return;

		if (this.owner.player()) {
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
		}
		List<AbstractPower> playerPowers = this.owner.powers();
		ArrayList<AbstractPower> buffs = new ArrayList<>();
		for (AbstractPower a : playerPowers) {
			if (!a.ID.equals("animator_PlayerStatistics") && !a.type.equals(PowerType.DEBUFF) && !(a instanceof InvisiblePower)) {
				buffs.add(a);
			}
		}
		for (int i = 0; i < this.passiveAmount; i++) {
			if (buffs.size() > 0) {
				for (AbstractPower buff : buffs) {
					// This does nothing for these powers in a weird way, the logic was left this way to possibly re-enable summoning buffer tokens when this is triggered
					if (buff.ID.equals("theDuelist:SummonPower") || buff.ID.equals("Focus") || buff.ID.equals("theDuelist:SwordsRevealPower") || buff.ID.equals("MimicSurprisePower") || buff.ID.equals("infinitespire:VenomPower")) {
						//DuelistCard.summon(AbstractDungeon.player, 1, new Token("Buffer Token"));
					}

					// hardcode exception for Jam Breeding Machine to enable a more random buff type for that power
					// this chooses randomly to buff one of the following: summons per turn, damage per turn, number of enemies targeted by damaged
					else if (buff.ID.equals("theDuelist:TwoJamPower")) {
						TwoJamPower jamBreed = (TwoJamPower) buff;
						int roll = AbstractDungeon.cardRandomRng.random(1, 3);
						if (roll == 2) {
							jamBreed.amount2++;
							jamBreed.updateDescription();
						} else if (roll == 3) {
							jamBreed.turnDmg++;
							jamBreed.updateDescription();
						} else {
							jamBreed.amount++;
							jamBreed.updateDescription();
						}
					}

					// hardcode exceptions for Toon World and Toon Kingdom
					else if (buff.ID.equals("theDuelist:ToonWorldPower") || buff.ID.equals("theDuelist:ToonKingdomPower")) {
                        /*if (buff instanceof ToonWorldPower)
						{
							ToonWorldPower tw = (ToonWorldPower)buff;
							if (tw.amount > 0)
							{
								tw.amount--;
								tw.updateDescription();
							}
						}
						else if (buff instanceof ToonKingdomPower)
						{
							ToonKingdomPower tw = (ToonKingdomPower)buff;
							if (tw.amount > 0)
							{
								tw.amount--;
								tw.updateDescription();
							}
						}*/
					}

					// hardcode exception for Haunted
					else if (buff instanceof HauntedPower || buff instanceof HauntedDebuff) {

					}

					// all other powers get buffed normally
					else {
						buff.amount += 1;
						buff.updateDescription();
					}
				}
			}
		}
		applyFocus();
		updateDescription();
	}

	@Override
	//Taken from frost orb and modified a bit. Works to draw the basic orb image.
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
		CardCrawlGame.sound.playV("POWER_INTANGIBLE", 1.0F);
	}

	@Override
	public AbstractOrb makeCopy() {
		return new Buffer();
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
	public void applyFocus() {
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}
