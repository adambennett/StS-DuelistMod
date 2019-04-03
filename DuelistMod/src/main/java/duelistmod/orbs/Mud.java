package duelistmod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;

import duelistmod.*;
import duelistmod.actions.common.RandomizedAction;
import duelistmod.interfaces.DuelistOrb;
import duelistmod.patches.DuelistCard;

@SuppressWarnings("unused")
public class Mud extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("Mud");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	public Mud()
	{
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Mud.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 1;
		this.basePassiveAmount = this.passiveAmount = 1;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus();
	}

	@Override
	public void updateDescription()
	{
		applyFocus();
		this.description = DESC[0] + this.evokeAmount + DESC[1];
	}

	@Override
	public void onEvoke()
	{
		applyFocus();
		for (AbstractCard c : AbstractDungeon.player.hand.group)
		{
			if (c.hasTag(Tags.INSECT) || c.hasTag(Tags.PLANT))
			{
				DuelistCard dragC = (DuelistCard)c;
				dragC.changeSummonsInBattle(this.evokeAmount, true);
			}
		}
		
		for (AbstractCard c : AbstractDungeon.player.drawPile.group)
		{
			if (c.hasTag(Tags.INSECT) || c.hasTag(Tags.PLANT))
			{
				DuelistCard dragC = (DuelistCard)c;
				dragC.changeSummonsInBattle(this.evokeAmount, true);
			}
		}
		
		for (AbstractCard c : AbstractDungeon.player.discardPile.group)
		{
			if (c.hasTag(Tags.INSECT) || c.hasTag(Tags.PLANT))
			{
				DuelistCard dragC = (DuelistCard)c;
				dragC.changeSummonsInBattle(this.evokeAmount, true);
			}
		}
		
		for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
		{
			if (c.hasTag(Tags.INSECT) || c.hasTag(Tags.PLANT))
			{
				DuelistCard dragC = (DuelistCard)c;
				dragC.changeSummonsInBattle(this.evokeAmount, true);
			}
		}
	}
	
	@Override
	public void onEndOfTurn()
	{
		checkFocus();
	}

	@Override
	public void onStartOfTurn()
	{
		this.triggerPassiveEffect();
	}

	public void triggerPassiveEffect()
	{
		DuelistCard randomNature = (DuelistCard) DuelistCard.returnTrulyRandomFromEitherSet(Tags.PLANT, Tags.INSECT);
		int upgradeRoll = AbstractDungeon.cardRandomRng.random(1, 3);
		boolean flippy = false;
		if (upgradeRoll == 1) { flippy = true; }
		AbstractDungeon.actionManager.addToTop(new RandomizedAction(randomNature, flippy, true, true, true, true, true, false, true, 1, 4, 0, 1, 0, 1));
	}

	@Override
	//Taken from frost orb and modified a bit. Works to draw the basic orb image.
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
	public void updateAnimation()
	{
		applyFocus();
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
	public void playChannelSFX()
	{
		
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new Mud();
	}
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		// Render evoke amount text
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
	}
	
	@Override
	public void checkFocus()
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
		{
			this.baseEvokeAmount = 1 + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		}
		else 
		{
			this.baseEvokeAmount = 1;
		}
		if (DuelistMod.debug)
		{
			System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalEvoke: " + originalEvoke + " :: new evoke amount: " + this.baseEvokeAmount);
		}
		applyFocus();
		updateDescription();
	}
	
	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


