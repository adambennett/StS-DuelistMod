package defaultmod.orbs;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;

import defaultmod.DefaultMod;
import defaultmod.cards.Token;
import defaultmod.patches.DuelistCard;

@SuppressWarnings("unused")
public class Shadow extends AbstractOrb
{
	public static final String ID = DefaultMod.makeID("Shadow");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	public Shadow()
	{
		this.img = ImageMaster.loadImage(DefaultMod.makePath("orbs/Shadow.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 2;
		this.basePassiveAmount = this.passiveAmount = 1;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
	}

	@Override
	public void updateDescription()
	{
		applyFocus();
		if (this.evokeAmount < 2) { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2]; }
		else { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[3]; }
	}

	@Override
	public void onEvoke()
	{
		
		ArrayList<AbstractCard> discards = AbstractDungeon.player.discardPile.group;
    	ArrayList<AbstractCard> toChooseFrom = new ArrayList<AbstractCard>();
    	for (AbstractCard c : discards) { if (c.tags.contains(DefaultMod.MONSTER)) { toChooseFrom.add(c); } }
    	if (toChooseFrom.size() > 0)
    	{
    		for (int i = 0; i < this.evokeAmount; i++)
    		{
    			AbstractMonster m = AbstractDungeon.getRandomMonster();
	    		int randomAttack = AbstractDungeon.cardRandomRng.random(toChooseFrom.size() - 1);
	    		AbstractCard chosen = toChooseFrom.get(randomAttack).makeStatEquivalentCopy();
	    		String cardName = chosen.originalName;
	    		if (DefaultMod.debug) { System.out.println("theDuelist:Shadow --- > Found: " + cardName); }
    			DuelistCard cardCopy = DuelistCard.newCopyOfMonster(cardName);
    			if (cardCopy != null)
    			{
    				if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
    				cardCopy.freeToPlayOnce = true;
    				cardCopy.applyPowers();
    				cardCopy.purgeOnUse = true;
    				if (chosen.upgraded) { cardCopy.upgrade(); }
    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
    				cardCopy.onResummon(1);
    				//cardCopy.summonThis(cardCopy.summons, cardCopy, 0, m);
    			}
    		
    		}
    	}
	}

	@Override
	public void onStartOfTurn()
	{
		this.triggerPassiveEffect();
	}

	private void triggerPassiveEffect()
	{
		DuelistCard.summon(AbstractDungeon.player, this.passiveAmount, new Token("Shadow Token"));
	}
	
	public void tribShadowToken()
	{
		this.baseEvokeAmount = this.evokeAmount += 1;
		this.basePassiveAmount = this.passiveAmount += 1;
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
	protected void renderText(SpriteBatch sb)
	{	
		// Render evoke amount text
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
		// Render passive amount text
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET + 20.0F * Settings.scale, this.c, this.fontScale);
	}

	@Override
	public void playChannelSFX()
	{
		
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new Shadow();
	}
	
	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


