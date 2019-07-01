package duelistmod.orbs;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;

import duelistmod.*;
import duelistmod.cards.tokens.Token;
import duelistmod.interfaces.*;
import duelistmod.powers.*;

@SuppressWarnings("unused")
public class Buffer extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("Buffer");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 0.5F; 	
	protected static final float VFX_INTERVAL_TIME = 0.25F;
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	public Buffer()
	{
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Earth.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 2;
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
		this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2];
	}

	@Override
	public void onEvoke()
	{
		BuffHelper.resetRandomBuffs(this.evokeAmount);
		DuelistCard.applyRandomBuffPlayer(AbstractDungeon.player, this.evokeAmount, false);
		if (DuelistMod.debug) { System.out.println("theDuelist:Buffer --- > triggered evoke!"); }
	}

	@Override
	public void onStartOfTurn()
	{
		applyFocus();
		int roll = AbstractDungeon.cardRandomRng.random(1, 10);
		int rollCheck = AbstractDungeon.cardRandomRng.random(1, 3);
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			if (instance.isEveryMonsterCheck(Tags.SPELLCASTER, false))
			{
				rollCheck += 4;
			}
		}
		if (roll < rollCheck)
		{
			this.triggerPassiveEffect();
		}
		
		else if (DuelistMod.debug) { DuelistMod.logger.info("Buffer orb missed the roll"); }

	}

	private void triggerPassiveEffect()
	{
		ArrayList<AbstractPower> playerPowers = AbstractDungeon.player.powers;
		ArrayList<AbstractPower> buffs = new ArrayList<AbstractPower>();
		if (DuelistMod.debug) 
		{ 
			for (int i = 0; i < playerPowers.size(); i++)
			{
				System.out.println("theDuelist:Buffer:triggerPassiveEffect() ---> before removing, players powers buff[" + i + "]: " + playerPowers.get(i).name + " -- type: " +  playerPowers.get(i).type);
				
			}
		}
		
		for (AbstractPower a : playerPowers)
		{
			if (!a.ID.equals("animator_PlayerStatistics") && !a.type.equals(PowerType.DEBUFF) && !(a instanceof InvisiblePower))
			{
				buffs.add(a);
				if (DuelistMod.debug) { System.out.println("theDuelist:Buffer --- > added buff to array of buffs: " + a.name); }
			}
			
			else if (DuelistMod.debug)
			{
				DuelistMod.logger.info("theDuelist:Buffer --- > SKIPPED adding this buff: " + a.name);
			}
		}
		
		for (int i = 0; i < this.passiveAmount; i++)
		{
			if (buffs.size() > 0)
			{				
				for (AbstractPower buff : buffs)
				{
					// This does nothing for these powers in a weird way, the logic was left this way to possibly re-enable summoning buffer tokens when this is triggered
					if (buff.ID.equals("theDuelist:SummonPower") || buff.ID.equals("Focus") || buff.ID.equals("theDuelist:SwordsRevealPower") || buff.ID.equals("MimicSurprisePower")) 
					{
						//DuelistCard.summon(AbstractDungeon.player, 1, new Token("Buffer Token")); 
						//if (DuelistMod.debug) { System.out.println("theDuelist:Buffer --- > Summoned token on passive trigger"); }
					}
					
					// hardcode exception for Jam Breeding Machine to enable a more random buff type for that power
					// this chooses randomly to buff one of the following: summons per turn, damage per turn, number of enemies targeted by damaged
					else if (buff.ID.equals("theDuelist:TwoJamPower"))
					{
						TwoJamPower jamBreed = (TwoJamPower) buff;
						String buffIndex = "Enemies Damaged (Amount #1)";	// string is only for debug
						int roll = AbstractDungeon.cardRandomRng.random(1, 3);
						if (roll == 2) { buffIndex = "Summons/turn (Amount #2)"; jamBreed.amount2++; jamBreed.updateDescription(); }						
						else if (roll == 3) { buffIndex = "Damage (turnDmg)"; jamBreed.turnDmg++; jamBreed.updateDescription(); }
						else { jamBreed.amount++; jamBreed.updateDescription(); }
						if (DuelistMod.debug) { System.out.println("theDuelist:Buffer --- > Buffed Jam Breeding Machine! Which amount did we buff: " + buffIndex); }
					}
					
					// all other powers get buffed normally
					else
					{
						buff.amount += 1; buff.updateDescription(); 
						if (DuelistMod.debug) { System.out.println("theDuelist:Buffer --- > Buffed " + buff.name + " on passive trigger. New amount: " + buff.amount); }
					}
					
					
				}
			}
		}
	
		applyFocus();
		updateDescription();
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
		this.angle += Gdx.graphics.getDeltaTime() * 120.0F;
		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) 
		{
			AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
			this.vfxTimer = 0.25F;
		}	
	}

	@Override
	public void playChannelSFX()
	{
		
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new Buffer();
	}
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		// Render passive amount text
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, this.c, this.fontScale);
	}
	
	
	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


