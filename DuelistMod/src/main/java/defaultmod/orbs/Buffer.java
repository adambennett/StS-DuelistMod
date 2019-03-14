package defaultmod.orbs;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import defaultmod.DefaultMod;
import defaultmod.cards.Token;
import defaultmod.interfaces.DuelistOrb;
import defaultmod.patches.DuelistCard;

@SuppressWarnings("unused")
public class Buffer extends DuelistOrb
{
	public static final String ID = DefaultMod.makeID("Buffer");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	public Buffer()
	{
		this.img = ImageMaster.loadImage(DefaultMod.makePath("orbs/Earth.png"));
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
		DuelistCard.applyRandomBuffPlayer(AbstractDungeon.player, this.evokeAmount, false);
		if (DefaultMod.debug) { System.out.println("theDuelist:Buffer --- > triggered evoke!"); }
	}

	@Override
	public void onStartOfTurn()
	{
		this.triggerPassiveEffect();
	}

	private void triggerPassiveEffect()
	{
		ArrayList<AbstractPower> playerPowers = AbstractDungeon.player.powers;
		ArrayList<AbstractPower> buffs = new ArrayList<AbstractPower>();
		if (DefaultMod.debug) 
		{ 
			for (int i = 0; i < playerPowers.size(); i++)
			{
				System.out.println("theDuelist:Buffer:triggerPassiveEffect() ---> before removing, players powers buff[" + i + "]: " + playerPowers.get(i).name + " -- type: " +  playerPowers.get(i).type);
				
			}
		}
		
		for (AbstractPower a : playerPowers)
		{
			if (!a.name.equals("Player Statistics") && !a.type.equals(PowerType.DEBUFF))
			{
				buffs.add(a);
				if (DefaultMod.debug) { System.out.println("theDuelist:Buffer --- > added buff to array of buffs: " + a.name); }
			}
		}
		
		for (int i = 0; i < this.passiveAmount; i++)
		{
			if (buffs.size() > 0)
			{
				int randomNum = AbstractDungeon.cardRandomRng.random(buffs.size() - 1);
				if (buffs.get(randomNum).name.equals("Summons")) 
				{
					DuelistCard.summon(AbstractDungeon.player, 1, new Token("Buffer Token")); 
					if (DefaultMod.debug) { System.out.println("theDuelist:Buffer --- > Summoned token on passive trigger"); }
				}
				else 
				{ 
					buffs.get(randomNum).amount += 1; buffs.get(randomNum).updateDescription(); 
					if (DefaultMod.debug) { System.out.println("theDuelist:Buffer --- > Buffed " + buffs.get(randomNum).name + " on passive trigger. New amount: " + buffs.get(randomNum).amount); }
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
		super.updateAnimation();
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


