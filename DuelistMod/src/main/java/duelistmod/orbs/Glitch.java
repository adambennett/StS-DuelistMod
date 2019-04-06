package duelistmod.orbs;

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

import duelistmod.*;
import duelistmod.actions.common.*;
import duelistmod.cards.Token;
import duelistmod.interfaces.*;
import duelistmod.patches.DuelistCard;

@SuppressWarnings("unused")
public class Glitch extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("Glitch");
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
	public ArrayList<String> lastTurnActions = new ArrayList<String>();
	
	// OJAMANIA FIELDS
	private static int MIN_BUFF_TURNS_ROLL = 1;
    private static int MAX_BUFF_TURNS_ROLL = 3;
    private static int MIN_DEBUFF_TURNS_ROLL = 1;
    private static int MAX_DEBUFF_TURNS_ROLL = 6;
    private static int RAND_CARDS = 2;
    private static int RAND_BUFFS = 1;
    private static int RAND_DEBUFFS = 2;
    private static boolean printing = DuelistMod.debug;
	
	public Glitch()
	{
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Glitch.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 2;
		this.basePassiveAmount = this.passiveAmount = 1;
		if (DuelistMod.challengeMode)
		{
			this.baseEvokeAmount = this.evokeAmount = 1;
			this.basePassiveAmount = this.passiveAmount = 1;
		}
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		lastTurnActions.add("None");
		checkFocus();
		this.updateDescription();
		
		// Setup passive action list
		passiveActions.add("Draw #b1 card");	
		passiveActions.add("Draw #b1 card");
		passiveActions.add("Gain #b10 HP");
		passiveActions.add("Gain #b5 HP");
		passiveActions.add("Gain #b5 HP");
		passiveActions.add("Lose #b5 HP");
		passiveActions.add("Lose #b5 HP");
		passiveActions.add("Lose #b5 HP");
		passiveActions.add("Apply #b1 random #ydebuff to random enemy");	
		passiveActions.add("Apply #b1 random #ydebuff to random enemy");
		passiveActions.add("Add #b1 random #yTrap to hand");
		passiveActions.add("Add #b1 random #yTrap to hand");
		passiveActions.add("Add #b1 random #yMonster to hand");
		passiveActions.add("Add #b1 random #yEthereal Duelist card to hand");
		passiveActions.add("Gain #b15 #yBlock");	
		passiveActions.add("Gain #b10 #yBlock");
		passiveActions.add("Gain #b5 #yBlock");
		passiveActions.add("Gain #b5 #yBlock");
		passiveActions.add("#ySummon #b1");	
		passiveActions.add("#ySummon #b1");	
		passiveActions.add("#ySummon #b2");
		passiveActions.add("#yIncrement #b1");	
		passiveActions.add("#yIncrement #b2");
		passiveActions.add("Gain [E] "); 
		//passiveActions.add("Gain #b1 Artifacts");
		//passiveActions.add("Gain #b2 Artifacts");
		passiveActionSize = passiveActions.size();
		
		// Setup evoke action list
		evokeActions.add("Orb slots+1");
		evokeActions.add("Draw #b1 card");	
		evokeActions.add("Draw #b1 card");	
		evokeActions.add("Draw #b2 cards");	
		evokeActions.add("Gain a random amount of gold (5-200)");
		evokeActions.add("Apply #b1 random #ydebuff to random enemy");
		evokeActions.add("Add #b1 random #yTrap to hand");
		evokeActions.add("Add #b1 random #ySpellcaster to hand");
		evokeActions.add("Add #b1 random #yEthereal Duelist card to hand");
		evokeActions.add("Gain #b15 #yBlock");	
		evokeActions.add("#ySummon #b2");
		evokeActions.add("#yIncrement #b2");
		evokeActions.add("#yOjamania");	
		if (!DuelistMod.challengeMode) { evokeActions.add("Gain [E] [E] "); }
		evokeActions.add("Channel a Glitch");
		//evokeActions.add("Gain #b3 Artifacts");
		evokeActionSize = evokeActions.size();
	}

	@Override
	public void updateDescription()
	{
		applyFocus();
		if (lastTurnActions.size() < 2 && lastTurnActions.size() > 0)
		{
			String actionString = "";
			for (String s : lastTurnActions) { actionString += s + ", "; }
			int endingIndex = actionString.lastIndexOf(",");
	        String finalActionString = actionString.substring(0, endingIndex) + ".";
	        if (this.evokeAmount < 2) { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2] + finalActionString; }
			else { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[3] + finalActionString; }
		}
		else if (lastTurnActions.size() >= 2)
		{
			String actionString = "";
			for (String s : lastTurnActions) { actionString += s + ", "; }
			int endingIndex = actionString.lastIndexOf(",");
	        String finalActionString = actionString.substring(0, endingIndex) + ".";
	        if (this.evokeAmount < 2) { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2] + finalActionString; }
			else { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[3] + finalActionString; }
		} 
		else
		{
			if (this.evokeAmount < 2) { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2] + "None."; }
			else { this.description = DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[3] + "None."; }
		}
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
		lastTurnActions = new ArrayList<String>();
		int randomActionNum = 0;
		for (int i = 0; i < this.passiveAmount; i++)
		{
			randomActionNum = AbstractDungeon.cardRandomRng.random(passiveActions.size() - 1);
			//randomActionNum = 3; // Exhaust
			//randomActionNum = 4; // Gold
			lastAction = runAction(passiveActions.get(randomActionNum));
			lastTurnActions.add(lastAction);
		}
		
		applyFocus();
		updateDescription();
	}

	
	public String runAction(String string) 
	{
		switch (string)
		{
			case "Draw #b1 card":
				DuelistCard.draw(1);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Draw #b2 cards":
				DuelistCard.draw(2);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain #b10 HP":
				DuelistCard.heal(AbstractDungeon.player, 10);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain #b5 HP":
				DuelistCard.heal(AbstractDungeon.player, 5);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Lose #b5 HP":
				DuelistCard.damageSelf(5);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "#yExhaust #b1 random card in hand":
				AbstractDungeon.actionManager.addToBottom(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, true));
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain a random amount of gold (50-100)":
				int randomGold = AbstractDungeon.cardRandomRng.random(50, 100);
				DuelistCard.gainGold(randomGold, AbstractDungeon.player, true);
				string = "Gain a random amount of gold (50-100): #b" + randomGold ;
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain a random amount of gold (1-50)":
				int randomGoldB = AbstractDungeon.cardRandomRng.random(1, 50);
				DuelistCard.gainGold(randomGoldB, AbstractDungeon.player, true);
				string = "Gain a random amount of gold (1-50): #b" + randomGoldB;
				if (printing) { if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); } }
				break;
			case "Gain a random amount of gold (5-200)":
				int randomGoldC = AbstractDungeon.cardRandomRng.random(5, 200);
				DuelistCard.gainGold(randomGoldC, AbstractDungeon.player, true);
				string = "Gain a random amount of gold (5-200): #b" + randomGoldC;
				if (printing) { if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); } }
				break;
			case "Apply #b1 random #ybuff":
				int randomTurnNum = AbstractDungeon.cardRandomRng.random(1, 3);
				DuelistCard.applyRandomBuff(AbstractDungeon.player, randomTurnNum);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Apply #b1 random #ydebuff to random enemy":
				int randomTurnNumD = AbstractDungeon.cardRandomRng.random(1, 3);
				AbstractMonster m = DuelistCard.getRandomMonster();
				AbstractPower debuff = RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, m, randomTurnNumD);
				DuelistCard.applyPower(debuff, (AbstractCreature)m);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "#yChannel #b1 random orb":
				DuelistCard.channelRandom();
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #ySpell to hand":
				DuelistCard randomSpell = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.SPELL);
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomSpell, false, true, true, false, false, false, false, false, 1, 4, 0, 0, 0, 0));
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "#yEvoke #b1 Orb.":
				DuelistCard.evoke(1);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #yTrap to hand":
				DuelistCard randomTrap = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.TRAP);
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomTrap, false, true, true, false, false, false, false, false, 1, 4, 0, 0, 0, 0));
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #ySpellcaster to hand":
				DuelistCard randomSpellcaster = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.SPELLCASTER);
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomSpellcaster, false, true, true, false, true, false, true, false, 1, 4, 0, 1, 0, 0));
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #yMonster to hand":
				DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.MONSTER);
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomMonster, false, true, true, false, false, true, false, true, 1, 4, 0, 0, 1, 2));
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Lose #b1 strength":
				AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Lose #b1 dexterity":
				AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain #b15 #yBlock":
				DuelistCard.staticBlock(15);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain #b10 #yBlock":
				DuelistCard.staticBlock(10);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain #b5 #yBlock":
				DuelistCard.staticBlock(5);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "#ySummon #b1":
				DuelistCard.summon(AbstractDungeon.player, 1, new Token("Glitch Token"));
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "#ySummon #b2":
				DuelistCard.summon(AbstractDungeon.player, 2, new Token("Glitch Token"));
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "#yIncrement #b1":
				DuelistCard.incMaxSummons(AbstractDungeon.player, 1);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "#yIncrement #b2":
				DuelistCard.incMaxSummons(AbstractDungeon.player, 2);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "#yOjamania":
				AbstractMonster mO = DuelistCard.getRandomMonster();
				// Add 5 random cards to hand, set cost to 0
				for (int i = 0; i < RAND_CARDS; i++)
				{
					AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
					AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(card, false, true, true, false, true, true, true, true, 1, 4, 0, 1, 1, 2));
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
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Increase this orb's Passive amount by #b1":
				increaseOrbAmounts("Passive", 1);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Increase this orb's Evoke amount by #b1":
				increaseOrbAmounts("Evoke", 1);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Increase this orb's Evoke amount by #b2":
				increaseOrbAmounts("Evoke", 2);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Orb slots+1":
				AbstractDungeon.player.increaseMaxOrbSlots(1, true);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain [E] ":
				DuelistCard.gainEnergy(1);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain [E] [E] ":
				DuelistCard.gainEnergy(2);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Channel a Glitch":
				AbstractOrb glitch = new Glitch();
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				DuelistCard.channel(glitch);
				break;
			case "Channel a Buffer":
				AbstractOrb buffer = new Buffer();
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				DuelistCard.channel(buffer);
				break;
			case "Gain #b3 Artifacts":
				AbstractPower art = new ArtifactPower(AbstractDungeon.player, 3);
				DuelistCard.applyPowerToSelf(art);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain #b2 Artifacts":
				AbstractPower artB = new ArtifactPower(AbstractDungeon.player, 2);
				DuelistCard.applyPowerToSelf(artB);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Gain #b1 Artifacts":
				AbstractPower artC = new ArtifactPower(AbstractDungeon.player, 1);
				DuelistCard.applyPowerToSelf(artC);
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #yEthereal Duelist card to hand":
				AbstractDungeon.actionManager.addToTop(new RandomEtherealDuelistCardToHandAction());
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
				break;
			default:
				String randomAction = evokeActions.get(AbstractDungeon.cardRandomRng.random(evokeActions.size() - 1));
				if (printing) { System.out.println("theDuelist:Glitch:runAction ---> triggered: " + string); }
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
		
		updateDescription();
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


