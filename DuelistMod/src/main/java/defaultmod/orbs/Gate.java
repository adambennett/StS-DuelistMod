package defaultmod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
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
import com.megacrit.cardcrawl.vfx.combat.LightningOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import defaultmod.patches.DuelistCard;
import defaultmod.powers.SummonPower;

@SuppressWarnings("unused")
public class Gate extends AbstractOrb
{
	public static final String ID = defaultmod.DefaultMod.makeID("Gate");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	private int DAMAGE = 4;
	private int BLOCK = 5;
	private int SUMMONS = 2;
	private int MANA = 1;
	
	public Gate()
	{
		this.img = com.megacrit.cardcrawl.helpers.ImageMaster.ORB_LIGHTNING;
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 0;
		this.basePassiveAmount = this.passiveAmount = 0;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
	}

	@Override
	public void updateDescription()
	{
		applyFocus();
		this.description = DESC[0] + DAMAGE + DESC[1] + BLOCK + DESC[2] + SUMMONS + DESC[3];
	}

	@Override
	public void onEvoke()
	{
		DuelistCard.summon(AbstractDungeon.player, SUMMONS);
	}

	@Override
	public void onEndOfTurn()
	{
		float speedTime = 0.2F / AbstractDungeon.player.orbs.size();
		if (Settings.FAST_MODE) 
		{
			speedTime = 0.0F;
		}
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
	}
	
	@Override
	public void onStartOfTurn()
	{
		AbstractDungeon.actionManager.addToTop(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, DAMAGE, DamageInfo.DamageType.THORNS), this, true));
		AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.MANA));
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK, true));
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
				AbstractDungeon.actionManager.addToBottom(new DamageAction(m, info, AbstractGameAction.AttackEffect.NONE, true));
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
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, 
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

	@Override
	public void triggerEvokeAnimation()
	{
		CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
		AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(this.cX, this.cY));
	}

	@Override
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

	@Override
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

	@Override
	public void playChannelSFX()
	{
		CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new Gate();
	}

	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


