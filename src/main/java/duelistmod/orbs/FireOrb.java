package duelistmod.orbs;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.powers.incomplete.FlameTigerPower;
import duelistmod.variables.Tags;

@SuppressWarnings("unused")
public class FireOrb extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("FireOrb");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	public FireOrb()
	{
		this.setID(ID);
		this.inversion = "Water";
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/FireOrb.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 1;
		this.basePassiveAmount = this.passiveAmount = 2;
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus(false);
		this.updateDescription();
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
		if (this.evokeAmount > 0)
		{
			for (AbstractCard c : AbstractDungeon.player.hand.group)
			{
				if (c.hasTag(Tags.DRAGON))
				{
					DuelistCard dragC = (DuelistCard)c;
					dragC.changeTributesInBattle(-this.evokeAmount, true);
				}
			}
			
			for (AbstractCard c : AbstractDungeon.player.discardPile.group)
			{
				if (c.hasTag(Tags.DRAGON))
				{
					DuelistCard dragC = (DuelistCard)c;
					dragC.changeTributesInBattle(-this.evokeAmount, true);
				}
			}
			
			for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
			{
				if (c.hasTag(Tags.DRAGON))
				{
					DuelistCard dragC = (DuelistCard)c;
					dragC.changeTributesInBattle(-this.evokeAmount, true);
				}
			}
			if (DuelistMod.debug) { System.out.println("theDuelist:Fire --- > triggered evoke!"); }
		}
	}

	@Override
	public void onStartOfTurn()
	{
		
	}
	
	public void triggerPassiveEffect()
	{		
		if (AbstractDungeon.player.hasPower(FlameTigerPower.POWER_ID))
		{
			AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.1f));
			if (this.passiveAmount > 0) 
			{ 
				ArrayList<AbstractMonster> mons = DuelistCard.getAllMonsForFireOrb();
				for (AbstractMonster mon : mons) 
				{ 
					DuelistCard.staticThornAttack(mon, AttackEffect.FIRE, this.passiveAmount); 
					if (gpcCheck()) { DuelistCard.staticThornAttack(mon, AttackEffect.FIRE, this.passiveAmount); }
				}
			}
		}
		else
		{
			ArrayList<AbstractMonster> mons = DuelistCard.getAllMonsForFireOrb();
			if (mons.size() > 0)
			{
				AbstractMonster mon = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() - 1));			
				if (this.passiveAmount > 0 && mon != null) 
				{ 
					AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.1f));
					DuelistCard.staticThornAttack(mon, AttackEffect.FIRE, this.passiveAmount); 
					if (gpcCheck()) { DuelistCard.staticThornAttack(mon, AttackEffect.FIRE, this.passiveAmount); }
				}
			}
		}
		
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
		CardCrawlGame.sound.playV("theDuelist:FireChannel", 25.0F);
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new FireOrb();
	}
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		if (renderInvertText(sb, true) || this.showEvokeValue)
		{
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
		}
		else if (!this.showEvokeValue)
		{
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
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
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


