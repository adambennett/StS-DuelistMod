package duelistmod.orbs;

import java.util.ArrayList;

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
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.interfaces.*;

@SuppressWarnings("unused")
public class MillenniumOrb extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("MillenniumOrb");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	private static int setEvoke = 2;
	
	public MillenniumOrb(int evoke)
	{
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/MillenniumOrb.png"));
		this.name = orbString.NAME;
		if (evoke > 0)
		{
			this.baseEvokeAmount = this.evokeAmount = evoke;
			setEvoke = evoke;
		}
		else 
		{
			this.baseEvokeAmount = this.evokeAmount = 1;
			setEvoke = 1;
		}
		this.basePassiveAmount = this.passiveAmount = 2;
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
		if (StarterDeckSetup.isDeckArchetype())
		{
			ArrayList<AbstractCard> deckCards = new ArrayList<AbstractCard>();
			for (int i = 0; i < 50; i++)
			{
				int index = StarterDeckSetup.getCurrentDeck().getPoolCards().size() - 1;
				int indexRoll = AbstractDungeon.cardRandomRng.random(index);
				AbstractCard c = StarterDeckSetup.getCurrentDeck().getPoolCards().get(indexRoll);
				while (deckCards.contains(c))
				{
					indexRoll = AbstractDungeon.cardRandomRng.random(index);
					c = StarterDeckSetup.getCurrentDeck().getPoolCards().get(indexRoll);
				}
				deckCards.add(c);
			}
			//deckCards.addAll(StarterDeckSetup.getCurrentDeck().getPoolCards());
			AbstractDungeon.actionManager.addToTop(new CardSelectScreenIntoHandAction(true, deckCards, false, this.evokeAmount, false, false, false, true, true, true, true, 0, 5, 0, 2, 0, 2));
		}
		else
		{
			ArrayList<AbstractCard> deckCards = new ArrayList<AbstractCard>();
			for (int i = 0; i < 50; i++)
			{
				int index = DuelistMod.myCards.size() - 1;
				int indexRoll = AbstractDungeon.cardRandomRng.random(index);
				AbstractCard c = DuelistMod.myCards.get(indexRoll);
				while (deckCards.contains(c))
				{
					indexRoll = AbstractDungeon.cardRandomRng.random(index);
					c = DuelistMod.myCards.get(indexRoll);
				}
				deckCards.add(c);
			}
			//deckCards.addAll(DuelistMod.myCards);
			AbstractDungeon.actionManager.addToTop(new CardSelectScreenIntoHandAction(true, deckCards, false, this.evokeAmount, false, false, false, true, true, true, true, 0, 5, 0, 2, 0, 2));
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
		triggerPassiveEffect();
	}

	public void triggerPassiveEffect()
	{
		PuzzleHelper.runSpecialEffect(this.passiveAmount, 0);
	}
	
	@Override
	public void checkFocus()
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
		{
			this.baseEvokeAmount = setEvoke + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		}
		else 
		{
			this.baseEvokeAmount = setEvoke;
		}
		if (DuelistMod.debug)
		{
			System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalEvoke: " + originalEvoke + " :: new evoke amount: " + this.baseEvokeAmount);
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
		return new MillenniumOrb(2);
	}
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		// Render evoke amount text
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
	}
	
	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}

