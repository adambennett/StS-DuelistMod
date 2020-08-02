package duelistmod.orbs;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.variables.Tags;

@SuppressWarnings("unused")
public class Anticrystal extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("Anticrystal");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	private int MANA = 1;
	private int evokeIndex = 0;
	private ArrayList<String> evokeDescriptions = new ArrayList<String>();
	
	public Anticrystal()
	{
		this.setID(ID);
		this.inversion = "Crystal";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Anticrystal.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 2;
		this.basePassiveAmount = this.passiveAmount = 4;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		this.evokeIndex = ThreadLocalRandom.current().nextInt(0, 3);
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus(false);
		updateEvokeValues();
	}

	@Override
	public void updateDescription()
	{
		applyFocus();
		updateEvokeValues();
		this.description = DESC[0] + 1 + DESC[1] + this.passiveAmount + DESC[2] + evokeDescriptions.get(evokeIndex);
	}
	
	public void updateEvokeValues()
	{
		int tempHP = this.evokeAmount * 5;
		evokeDescriptions = new ArrayList<String>();
		evokeDescriptions.add(DESC[3]);
		evokeDescriptions.add(DESC[4] + this.evokeAmount + DESC[5]);
		evokeDescriptions.add(DESC[6] + tempHP + DESC[7]);
	}

	@Override
	public void onEvoke()
	{
		if (this.evokeAmount > 0)
		{
			switch (evokeIndex)
			{
				// 2 randomized Spells to hand
				case 0:
					for (int i = 0; i < 2; i++)
					{
						DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false);
						while (randomMonster.hasTag(Tags.OJAMA)) { randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false); }
						AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomMonster, false, true, true, true, false, false, false, false, 0, 3, 0, 0, 0, 0));
						if (DuelistMod.debug) { System.out.println("theDuelist:Anticrystal --- > Added: " + randomMonster.name + " to player hand."); }
					}					
					break;
					
				// Draw 2 cards
				case 1:
					DuelistCard.draw(this.evokeAmount);
					break;
					
				// Gain 10 Temp HP
				case 2:
					DuelistCard.gainTempHP(this.evokeAmount * 5);
					break;
			}
		}
	}

	@Override
	public void onEndOfTurn()
	{
		evokeIndex++;
		if (evokeIndex > 2) { evokeIndex = 0; }
		//evokeIndex = AbstractDungeon.cardRandomRng.random(0, 2);
		checkFocus(false);
		updateDescription();
	}
	
	private void triggerPassive()
	{
		if (!hasNegativeFocus() || this.passiveAmount > 0)
		{
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
			DuelistCard.draw(1);
			DuelistCard.gainTempHP(this.passiveAmount);
			DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false);
			while (randomMonster.hasTag(Tags.OJAMA)) { randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false); }
			AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomMonster, false, true, true, true, false, false, false, false, 0, 3, 0, 0, 0, 0));
			if (DuelistMod.debug) { System.out.println("theDuelist:Anticrystal --- > Added: " + randomMonster.name + " to player hand."); }
			
		}
		else 
		{
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
			DuelistCard.draw(1);
			DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false);
			while (randomMonster.hasTag(Tags.OJAMA)) { randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, false); }
			AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomMonster, false, true, true, true, false, false, false, false, 0, 3, 0, 0, 0, 0));
			if (DuelistMod.debug) { System.out.println("theDuelist:Anticrystal --- > Added: " + randomMonster.name + " to player hand."); }
		}
	}
	
	@Override
	public void onStartOfTurn()
	{
		triggerPassive();
		//if (gpcCheck()) { triggerPassive(); }
	}

	@Override
	public void triggerEvokeAnimation()
	{

	}

	@Override
	public void updateAnimation()
	{
		applyFocus();
		super.updateAnimation();
		this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) 
		{
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
		//renderText(sb);
		this.hb.render(sb);
	}

	@Override
	public void playChannelSFX()
	{
		CardCrawlGame.sound.playV("ORB_FROST_CHANNEL", 8.0F);
	}
	
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		if (renderInvertText(sb, true) || this.showEvokeValue)
		{
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
		}
	}
	
	@Override
	public void checkFocus(boolean allowNegativeFocus) 
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
		{
			if ((AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount > 0) || (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount + this.originalPassive > 0))
			{
				this.basePassiveAmount = this.originalPassive + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
			}
			
			else
			{
				this.basePassiveAmount = 0;
			}
			
			
			if ((AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount > 0) || (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount + this.originalEvoke > 0))
			{
				this.baseEvokeAmount = this.originalEvoke + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
			}
			
			else
			{
				this.baseEvokeAmount = 0;
			}
			
		}
		else
		{
			this.basePassiveAmount = this.originalPassive;
			this.baseEvokeAmount = this.originalEvoke;
		}
		if (DuelistMod.debug)
		{
			//System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalPassive: " + originalPassive + " :: new passive amount: " + this.basePassiveAmount);
			//System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalEvoke: " + originalEvoke + " :: new evoke amount: " + this.baseEvokeAmount);
		}
		applyFocus();
		updateDescription();
	}
		
	@Override
	public AbstractOrb makeCopy()
	{
		return new Anticrystal();
	}

	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


