package defaultmod.orbs;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;

import defaultmod.DefaultMod;
import defaultmod.cards.Token;
import defaultmod.interfaces.RandomEffectsHelper;
import defaultmod.patches.DuelistCard;

@SuppressWarnings("unused")
public class Glitch extends AbstractOrb
{
	public static final String ID = DefaultMod.makeID("Glitch");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	public static ArrayList<String> passiveActions = new ArrayList<String>();
	public static ArrayList<String> evokeActions = new ArrayList<String>();
	private int passiveActionSize = 0;
	private int evokeActionSize = 0;
	public String lastAction = "None";
	
	// OJAMANIA FIELDS
	private static int MIN_BUFF_TURNS_ROLL = 1;
    private static int MAX_BUFF_TURNS_ROLL = 3;
    private static int MIN_DEBUFF_TURNS_ROLL = 1;
    private static int MAX_DEBUFF_TURNS_ROLL = 6;
    private static int RAND_CARDS = 2;
    private static int RAND_BUFFS = 1;
    private static int RAND_DEBUFFS = 2;
	
	public Glitch()
	{
		this.img = ImageMaster.loadImage(DefaultMod.makePath("orbs/Glitch.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 2;
		this.basePassiveAmount = this.passiveAmount = 1;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		
		// Setup passive action list
		passiveActions.add("Draw #b2 cards");	
		passiveActions.add("Gain #b10 HP");
		//passiveActions.add("#yExhaust #b1 random card in hand");	
		passiveActions.add("Gain a random amount of gold (50-100)");
		passiveActions.add("Apply #b1 random #ybuff");	
		passiveActions.add("Apply #b1 random #ydebuff to random enemy");	
		passiveActions.add("#yChannel #b1 random orb");
		passiveActions.add("#yEvoke #b1 Orb.");	
		passiveActions.add("Add #b1 random #ySpell to hand");
		passiveActions.add("Add #b1 random #yTrap to hand");	
		passiveActions.add("Add #b1 random #ySpellcaster to hand");
		passiveActions.add("Add #b1 random #yMonster to hand");
		passiveActions.add("Gain #b15 #yBlock");	
		passiveActions.add("#ySummon #b1");	
		passiveActions.add("#ySummon #b2");
		passiveActions.add("#yIncrement #b1");	
		passiveActions.add("#yIncrement #b2");
		passiveActions.add("#yOjamania");	
		passiveActions.add("Increase this orb's Passive amount by #b1");	
		passiveActions.add("Increase this orb's Evoke amount by #b1");	
		passiveActions.add("Increase this orb's Evoke amount by #b1");
		passiveActions.add("Increase this orb's Evoke amount by #b1");	
		passiveActions.add("Increase this orb's Evoke amount by #b2");
		passiveActions.add("Gain 1 [E] "); 
		passiveActions.add("Channel a Glitch");
		passiveActions.add("Channel a Buffer");
		passiveActions.add("Gain #b3 Artifacts");
		passiveActionSize = passiveActions.size();
		
		// Setup evoke action list
		evokeActions.add("Draw #b2 cards");	
		evokeActions.add("Gain #b10 HP");
		evokeActions.add("Gain a random amount of gold (50-100)");
		evokeActions.add("Apply #b1 random #ybuff");	
		evokeActions.add("Apply #b1 random #ydebuff to random enemy");	
		evokeActions.add("#yChannel #b1 random orb");
		evokeActions.add("#yEvoke #b1 Orb.");	
		evokeActions.add("Add #b1 random #ySpell to hand");
		evokeActions.add("Add #b1 random #yTrap to hand");	
		evokeActions.add("Add #b1 random #ySpellcaster to hand");	
		evokeActions.add("Add #b1 random #yMonster to hand");
		evokeActions.add("Gain #b15 #yBlock");
		evokeActions.add("#ySummon #b1");	
		evokeActions.add("#ySummon #b2");
		evokeActions.add("#yIncrement #b1");	
		evokeActions.add("#yIncrement #b2");
		evokeActions.add("#yOjamania");	
		evokeActions.add("Orb slots+1");
		evokeActions.add("Gain 1 [E] "); 
		evokeActions.add("Channel a Glitch");
		evokeActions.add("Channel a Buffer");
		evokeActions.add("Gain #b3 Artifacts");
		evokeActionSize = evokeActions.size();
	}

	@Override
	public void updateDescription()
	{
		applyFocus();
		if (this.evokeAmount < 2) { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2] + lastAction; }
		else { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[3] + lastAction; }
	}

	@Override
	public void onEvoke()
	{
		int randomActionNum = 0;
		for (int i = 0; i < this.evokeAmount; i++)
		{
			randomActionNum = AbstractDungeon.cardRandomRng.random(evokeActions.size() - 1);
			lastAction = runAction(evokeActions.get(randomActionNum));
		}
		
		applyFocus();
		updateDescription();
	}

	@Override
	public void onStartOfTurn()
	{
		this.triggerPassiveEffect();
	}

	private void triggerPassiveEffect()
	{
		int randomActionNum = 0;
		for (int i = 0; i < this.passiveAmount; i++)
		{
			randomActionNum = AbstractDungeon.cardRandomRng.random(passiveActions.size() - 1);
			//randomActionNum = 3; // Exhaust
			//randomActionNum = 4; // Gold
			lastAction = runAction(passiveActions.get(randomActionNum));
		}
		
		applyFocus();
		updateDescription();
	}

	
	public String runAction(String string) 
	{
		switch (string)
		{
			case "Draw #b2 cards":
				DuelistCard.draw(2);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Gain #b10 HP":
				DuelistCard.heal(AbstractDungeon.player, 10);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Lose #b5 HP":
				DuelistCard.damageSelf(5);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "#yExhaust #b1 random card in hand":
				AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, true));
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Gain a random amount of gold (50-100)":
				int randomGold = AbstractDungeon.cardRandomRng.random(50, 100);
				DuelistCard.gainGold(randomGold, AbstractDungeon.player, true);
				string = "Gain a random amount of gold #b" + randomGold;
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Apply #b1 random #ybuff":
				int randomTurnNum = AbstractDungeon.cardRandomRng.random(1, 3);
				DuelistCard.applyRandomBuff(AbstractDungeon.player, randomTurnNum);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Apply #b1 random #ydebuff to random enemy":
				int randomTurnNumD = AbstractDungeon.cardRandomRng.random(1, 3);
				AbstractMonster m = DuelistCard.getRandomMonster();
				AbstractPower debuff = RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, m, randomTurnNumD);
				DuelistCard.applyPower(debuff, (AbstractCreature)m);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "#yChannel #b1 random orb":
				DuelistCard.channelRandom();
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Add #b1 random #ySpell to hand":
				DuelistCard randomSpell = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.SPELL);
				DuelistCard.addCardToHand(randomSpell);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "#yEvoke #b1 Orb.":
				DuelistCard.evoke(1);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Add #b1 random #yTrap to hand":
				DuelistCard randomTrap = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.TRAP);
				DuelistCard.addCardToHand(randomTrap);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Add #b1 random #ySpellcaster to hand":
				DuelistCard randomSpellcaster = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.SPELLCASTER);
				DuelistCard.addCardToHand(randomSpellcaster);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Add #b1 random #yMonster to hand":
				DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.MONSTER);
				DuelistCard.addCardToHand(randomMonster);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Lose #b1 strength":
				AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Lose #b1 dexterity":
				AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Gain #b15 #yBlock":
				DuelistCard.staticBlock(15);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "#ySummon #b1":
				DuelistCard.summon(AbstractDungeon.player, 1, new Token("Glitch Token"));
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "#ySummon #b2":
				DuelistCard.summon(AbstractDungeon.player, 2, new Token("Glitch Token"));
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "#yIncrement #b1":
				DuelistCard.incMaxSummons(AbstractDungeon.player, 1);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "#yIncrement #b2":
				DuelistCard.incMaxSummons(AbstractDungeon.player, 2);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "#yOjamania":
				AbstractMonster mO = DuelistCard.getRandomMonster();
				// Add 5 random cards to hand, set cost to 0
				for (int i = 0; i < RAND_CARDS; i++)
				{
					AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
					card.costForTurn = 0;
					card.isCostModifiedForTurn = true;
					if (this.evokeAmount > 3) { card.upgrade(); }
					DuelistCard.addCardToHand(card);
				}
				
				// Give self 3 random buffs
				for (int i = 0; i < RAND_BUFFS; i++)
				{
					int randomTurnNumO = AbstractDungeon.cardRandomRng.random(MIN_BUFF_TURNS_ROLL, MAX_BUFF_TURNS_ROLL);
					DuelistCard.applyRandomBuffPlayer(AbstractDungeon.player, randomTurnNumO, false);
				}
				
				// Give 3 random debuffs to enemy
				for (int i = 0; i < RAND_DEBUFFS; i++)
				{
					int randomTurnNumO2 = AbstractDungeon.cardRandomRng.random(MIN_DEBUFF_TURNS_ROLL, MAX_DEBUFF_TURNS_ROLL);
					DuelistCard.applyPower(RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, mO, randomTurnNumO2), mO);
				}
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Increase this orb's Passive amount by #b1":
				increaseOrbAmounts("Passive", 1);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Increase this orb's Evoke amount by #b1":
				increaseOrbAmounts("Evoke", 1);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Increase this orb's Evoke amount by #b2":
				increaseOrbAmounts("Evoke", 2);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Orb slots+1":
				AbstractDungeon.player.increaseMaxOrbSlots(1, true);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Gain 1 [E] ":
				DuelistCard.gainEnergy(1);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			case "Channel a Glitch":
				AbstractOrb glitch = new Glitch();
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				DuelistCard.channel(glitch);
				break;
			case "Channel a Buffer":
				AbstractOrb buffer = new Buffer();
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				DuelistCard.channel(buffer);
				break;
			case "Gain #b3 Artifacts":
				AbstractPower art = new ArtifactPower(AbstractDungeon.player, 3);
				DuelistCard.applyPowerToSelf(art);
				//System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string);
				break;
			default:
				String randomAction = evokeActions.get(AbstractDungeon.cardRandomRng.random(evokeActions.size() - 1));
				//System.out.println("theDuelist:Glitch:runAction ---> triggered (default): " + string);
				break;
		}
		
		return string;
		
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
	public void playChannelSFX()
	{
		
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new Glitch();
	}
	
	private void increaseOrbAmounts(String which, int amount)
	{
		if (which.equals("Passive") || which.equals("passive"))
		{
			this.basePassiveAmount = this.passiveAmount += amount;
		}
		else if (which.equals("Evoke") || which.equals("evoke"))
		{
			this.baseEvokeAmount = this.evokeAmount += amount;
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
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


