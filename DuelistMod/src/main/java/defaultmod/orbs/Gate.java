package defaultmod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import defaultmod.powers.SummonPower;

@SuppressWarnings("unused")
public class Gate extends AbstractOrb
{
	public static final String ID = defaultmod.DefaultMod.makeID("Gate");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; private float vfxIntervalMin = 0.15F; private float vfxIntervalMax = 0.8F;

	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	private int DAMAGE = 5;
	private int BLOCK = 7;
	private int SUMMONS = 3;
	private int MANA = 1;
	
	public Gate()
	{
		this.img = com.megacrit.cardcrawl.helpers.ImageMaster.ORB_LIGHTNING;
		this.name = orbString.NAME;
		this.baseEvokeAmount = 3;
		this.evokeAmount = this.baseEvokeAmount;
		this.basePassiveAmount = 3;
		this.passiveAmount = this.basePassiveAmount;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
	}

	public void updateDescription()
	{
		this.description = "#yPassive: At the start of turn, deal #b" + DAMAGE + " damage to ALL enemies, gain #b" + BLOCK + " #yBlock, and gain [e]. NL #yEvoke: #ySummon #b" + SUMMONS + ".";
	}

	public void onEvoke()
	{
		int temp = 0;
		if (!AbstractDungeon.player.hasPower(SummonPower.POWER_ID)) 
    	{
    		AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS), SUMMONS));
    	}
    	else
    	{
    		temp = (AbstractDungeon.player.getPower(SummonPower.POWER_ID).amount);
    		if (!(temp > 5 - SUMMONS)) 
    		{
    			AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS), SUMMONS));
    		}
    		else
    		{
    			AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, 5 - temp), 5 - temp));
    		}
    	}
	}

	public void onEndOfTurn()
	{
		float speedTime = 0.2F / AbstractDungeon.player.orbs.size();
		if (Settings.FAST_MODE) 
		{
			speedTime = 0.0F;
		}
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
	}
	
	public void onStartOfTurn()
	{
		AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.DAMAGE, DamageInfo.DamageType.THORNS), this, true));
		AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.MANA));
		AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.BLOCK, true));
	}

	private void triggerPassiveEffect(DamageInfo info, boolean hitAll)
	{
		float speedTime;

		if (!hitAll) 
		{
			AbstractCreature m = AbstractDungeon.getRandomMonster();
			if (m != null) 
			{
				speedTime = 0.2F / AbstractDungeon.player.orbs.size();
				if (Settings.FAST_MODE) 
				{
					speedTime = 0.0F;
				}
				AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m, info, AbstractGameAction.AttackEffect.NONE, true));
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.drawX, m.drawY), speedTime));
				AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_FROST_EVOKE"));
			}
		} 
		else 
		{
			speedTime = 0.2F / AbstractDungeon.player.orbs.size();
			if (Settings.FAST_MODE) 
			{
				speedTime = 0.0F;
			}
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
			AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(AbstractDungeon.player, 
			DamageInfo.createDamageMatrix(info.base, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
			for (AbstractMonster m3 : AbstractDungeon.getMonsters().monsters) 
			{
				if ((!m3.isDeadOrEscaped()) && (!m3.halfDead)) 
				{
					AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m3.drawX, m3.drawY), speedTime));
				}
			}
			AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_FROST_EVOKE"));
		}
	}

	public void triggerEvokeAnimation()
	{
		CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
		AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.combat.LightningOrbActivateEffect(this.cX, this.cY));
	}

	public void updateAnimation()
	{
		super.updateAnimation();
		this.angle += Gdx.graphics.getDeltaTime() * 180.0F;

		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) {
			AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
			if (MathUtils.randomBoolean()) {
				AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
			}
			this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
		}
	}

	public void render(SpriteBatch sb)
	{
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

	public void playChannelSFX()
	{
		CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
	}

	public AbstractOrb makeCopy()
	{
		return new Gate();
	}
}


