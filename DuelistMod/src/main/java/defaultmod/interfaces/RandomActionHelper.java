package defaultmod.interfaces;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import defaultmod.DefaultMod;
import defaultmod.actions.common.*;
import defaultmod.cards.Token;
import defaultmod.patches.DuelistCard;

public class RandomActionHelper
{
	
	// OJAMANIA FIELDS
	private static int MIN_BUFF_TURNS_ROLL = 1;
    private static int MAX_BUFF_TURNS_ROLL = 3;
    private static int MIN_DEBUFF_TURNS_ROLL = 1;
    private static int MAX_DEBUFF_TURNS_ROLL = 6;
    private static int RAND_CARDS = 2;
    private static int RAND_BUFFS = 1;
    private static int RAND_DEBUFFS = 2;
    private static boolean printing = DefaultMod.debug;
    
    // Action List
    public static ArrayList<String> actions = new ArrayList<String>();
    public static int randomIndex = 0;
    
    public static String triggerRandomAction(int timesTriggered, boolean upgradeOjamaniaCards)
	{
    	initList();
		int randomActionNum = 0;
		String lastAction = "";
		for (int i = 0; i < timesTriggered; i++)
		{
			randomActionNum = AbstractDungeon.cardRandomRng.random(actions.size() - 1);
			if (DefaultMod.debug) { System.out.println("theDuelist:RandomActionHelper:triggerRandomAction() ---> randomActionNum: " + randomActionNum); }
			lastAction = runAction(actions.get(randomActionNum), upgradeOjamaniaCards);
		}
		
		return lastAction;
	}

	public static String runAction(String string, boolean upgradeCards) 
	{
		initList();
		switch (string)
		{
			case "Draw #b1 card":
				DuelistCard.draw(1);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Draw #b2 cards":
				DuelistCard.draw(2);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain #b10 HP":
				DuelistCard.heal(AbstractDungeon.player, 10);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain #b5 HP":
				DuelistCard.heal(AbstractDungeon.player, 5);
				if (printing) { if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); } }
				break;
			case "Lose #b5 HP":
				DuelistCard.damageSelf(5);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#yExhaust #b1 random card in hand":
				AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, true));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain a random amount of gold (50-100)":
				int randomGold = AbstractDungeon.cardRandomRng.random(50, 100);
				DuelistCard.gainGold(randomGold, AbstractDungeon.player, true);
				string = "Gain a random amount of gold (50-100): #b" + randomGold ;
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain a random amount of gold (1-50)":
				int randomGoldB = AbstractDungeon.cardRandomRng.random(1, 50);
				DuelistCard.gainGold(randomGoldB, AbstractDungeon.player, true);
				string = "Gain a random amount of gold (1-50): #b" + randomGoldB;
				if (printing) { if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); } }
				break;
			case "Gain a random amount of gold (5-200)":
				int randomGoldC = AbstractDungeon.cardRandomRng.random(5, 200);
				DuelistCard.gainGold(randomGoldC, AbstractDungeon.player, true);
				string = "Gain a random amount of gold (5-200): #b" + randomGoldC;
				if (printing) { if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); } }
				break;
			case "Gain a random amount of gold (0-1000)":
				int randomGoldD = AbstractDungeon.cardRandomRng.random(0, 1000);
				DuelistCard.gainGold(randomGoldD, AbstractDungeon.player, true);
				string = "Gain a random amount of gold (5-1000): #b" + randomGoldD;
				if (printing) { if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); } }
				break;
			case "Apply #b1 random #ybuff":
				int randomTurnNum = AbstractDungeon.cardRandomRng.random(1, 3);
				DuelistCard.applyRandomBuff(AbstractDungeon.player, randomTurnNum);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Apply #b1 random #ydebuff to random enemy":
				int randomTurnNumD = AbstractDungeon.cardRandomRng.random(1, 3);
				AbstractMonster m = DuelistCard.getRandomMonster();
				AbstractPower debuff = RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, m, randomTurnNumD);
				DuelistCard.applyPower(debuff, (AbstractCreature)m);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Apply #b2 random #ydebuffs to random enemy":
				
				int randomTurnNumDe = AbstractDungeon.cardRandomRng.random(1, 3);
				AbstractMonster ma = DuelistCard.getRandomMonster();
				AbstractPower debuffB = RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, ma, randomTurnNumDe);
				DuelistCard.applyPower(debuffB, (AbstractCreature)ma);
				
				randomTurnNumDe = AbstractDungeon.cardRandomRng.random(1, 3);
				debuffB = RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, ma, randomTurnNumDe);
				DuelistCard.applyPower(debuffB, (AbstractCreature)ma);
				
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#yChannel #b1 random orb":
				DuelistCard.channelRandom();
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #ySpell to hand":
				DuelistCard randomSpell = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.SPELL);
				AbstractDungeon.actionManager.addToTop(new RandomizedAction(randomSpell, false, true, true, false, 1, 4));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#yEvoke #b1 Orb":
				if (AbstractDungeon.player.orbs.size() < 1) 
				{ 
					triggerRandomAction(1, upgradeCards);
					//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: Evoke 1 Orb, but skipped due to no orbs ");
					break; 
				}
				else { DuelistCard.evoke(1); }
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #yTrap to hand":
				DuelistCard randomTrap = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.TRAP);
				AbstractDungeon.actionManager.addToTop(new RandomizedAction(randomTrap, false, true, true, false, 1, 4));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #yDragon to hand":
				DuelistCard randomSpellcaster = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.DRAGON);
				AbstractDungeon.actionManager.addToTop(new RandomizedAction(randomSpellcaster, false, true, true, false, 1, 4));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #yMonster to hand":
				DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.MONSTER);
				AbstractDungeon.actionManager.addToTop(new RandomizedAction(randomMonster, false, true, true, false, 1, 4));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #yEthereal Duelist card to hand":
				AbstractDungeon.actionManager.addToTop(new RandomEtherealDuelistCardToHandAction());
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Lose #b1 strength":
				AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Lose #b1 dexterity":
				AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain #b5 #yBlock":
				DuelistCard.staticBlock(5);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain #b10 #yBlock":
				DuelistCard.staticBlock(10);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain #b15 #yBlock":
				DuelistCard.staticBlock(15);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#ySummon #b1":
				DuelistCard.summon(AbstractDungeon.player, 1, new Token("Random Token"));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#ySummon #b2":
				DuelistCard.summon(AbstractDungeon.player, 2, new Token("Random Token"));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#yIncrement #b1":
				DuelistCard.incMaxSummons(AbstractDungeon.player, 1);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#yIncrement #b2":
				DuelistCard.incMaxSummons(AbstractDungeon.player, 2);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#yOjamania":
				AbstractMonster mO = DuelistCard.getRandomMonster();
				// Add 5 random cards to hand, set cost to 0
				for (int i = 0; i < RAND_CARDS; i++)
				{
					AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
					AbstractDungeon.actionManager.addToTop(new RandomizedAction(card, false, true, true, false, 1, 4));
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
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain [E] ":
				DuelistCard.gainEnergy(1);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain [E] [E] ":
				DuelistCard.gainEnergy(2);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain #b3 Artifacts":
				AbstractPower art = new ArtifactPower(AbstractDungeon.player, 3);
				DuelistCard.applyPowerToSelf(art);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Gain #b2 Artifacts":
				AbstractPower artB = new ArtifactPower(AbstractDungeon.player, 2);
				DuelistCard.applyPowerToSelf(artB);
				if (printing) { if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); } }
				break;
			case "Gain #b1 Artifact":
				AbstractPower artC = new ArtifactPower(AbstractDungeon.player, 1);
				DuelistCard.applyPowerToSelf(artC);
				if (printing) { if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); } }
				break;
			default:
				String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
				runAction(randomAction, upgradeCards);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered (default): " + string); }
				break;
		}
		
		return string;
	}

	private static void initList()
	{
		actions = new ArrayList<String>();
		if (!DefaultMod.challengeMode) 
		{ 
			actions.add("Gain a random amount of gold (1-50)"); 
			actions.add("Draw #b2 cards");	
			actions.add("Gain #b5 HP");
			actions.add("Gain #b10 HP");
			actions.add("#ySummon #b2");
			actions.add("#yOjamania");	
			actions.add("Gain [E] "); 
			actions.add("Channel a Glitch");
			actions.add("#yIncrement #b2");
		}
		else
		{
			//actions.add("#yExhaust #b1 random card in hand");
			//actions.add("#yExhaust #b1 random card in hand");	
			actions.add("Add #b1 random #yEthereal Duelist card to hand");
			actions.add("Gain #b5 HP");
			actions.add("#ySummon #b1");
			actions.add("#yIncrement #b1");
		}
		actions.add("Draw #b1 card");	
		actions.add("Draw #b1 card");	
		actions.add("Gain #b5 HP");
		actions.add("Lose #b5 HP");
		actions.add("Lose #b5 HP");
		//actions.add("#yExhaust #b1 random card in hand");
		//actions.add("#yExhaust #b1 random card in hand");	
		actions.add("Apply #b2 random #ydebuffs to random enemy");	
		actions.add("Apply #b1 random #ydebuff to random enemy");
		actions.add("Add #b1 random #yTrap to hand");
		actions.add("Add #b1 random #yTrap to hand");	
		actions.add("Add #b1 random #yMonster to hand");
		actions.add("Add #b1 random #yEthereal Duelist card to hand");
		actions.add("Gain #b10 #yBlock");
		actions.add("Gain #b5 #yBlock");
		actions.add("Gain #b5 #yBlock");
		actions.add("#ySummon #b1");	
		actions.add("#ySummon #b1");	
		actions.add("#yIncrement #b1");	
		actions.add("Gain #b1 Artifact");
	}

}
