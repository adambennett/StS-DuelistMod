package duelistmod.helpers;

import java.util.*;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.*;
import duelistmod.orbs.Glitch;
import duelistmod.variables.*;

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
    private static boolean printing = DuelistMod.debug;
    
    // Action List
    public static ArrayList<String> actions = new ArrayList<String>();
    public static int randomIndex = 0;
    public static Map<String, String> translationMap = new HashMap<String, String>();	
    
    public static String triggerRandomAction(int timesTriggered, boolean upgradeOjamaniaCards)
	{
    	initList();
		int randomActionNum = 0;
		String lastAction = "";
		for (int i = 0; i < timesTriggered; i++)
		{
			randomActionNum = AbstractDungeon.cardRandomRng.random(actions.size() - 1);
			if (DuelistMod.debug) { System.out.println("theDuelist:RandomActionHelper:triggerRandomAction() ---> randomActionNum: " + randomActionNum); }
			lastAction = runAction(actions.get(randomActionNum), upgradeOjamaniaCards);
		}
		
		if (translationMap.get(lastAction) != null) { lastAction = translationMap.get(lastAction); }
		else if (DuelistMod.debug) { DuelistMod.logger.info("Got a null translation for a random action, so we just loaded the English"); }
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
			case "Lose #b1 HP":
				DuelistCard.damageSelf(1);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#yExhaust #b1 random card in hand":
				AbstractDungeon.actionManager.addToTop(new ExhaustAction(1, true));
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
				if (m != null)
				{
					AbstractPower debuff = DebuffHelper.getRandomDebuff(AbstractDungeon.player, m, randomTurnNumD);
					DuelistCard.applyPower(debuff, (AbstractCreature)m);
				}
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Apply #b2 random #ydebuffs to random enemy":
				
				int randomTurnNumDe = AbstractDungeon.cardRandomRng.random(1, 3);
				AbstractMonster ma = DuelistCard.getRandomMonster();
				if (ma != null)
				{
					AbstractPower debuffB = DebuffHelper.getRandomDebuff(AbstractDungeon.player, ma, randomTurnNumDe);
					DuelistCard.applyPower(debuffB, (AbstractCreature)ma);
					
					randomTurnNumDe = AbstractDungeon.cardRandomRng.random(1, 3);
					debuffB = DebuffHelper.getRandomDebuff(AbstractDungeon.player, ma, randomTurnNumDe);
					DuelistCard.applyPower(debuffB, (AbstractCreature)ma);
				}				
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#yChannel #b1 random orb":
				DuelistCard.channelRandom();
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Channel a Glitch":
				AbstractOrb glitch = new Glitch();
				DuelistCard.channel(glitch);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #ySpell to hand":
				DuelistCard randomSpell = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL);
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomSpell, false, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));
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
				DuelistCard randomTrap = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.TRAP);
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomTrap, false, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #yDragon to hand":
				DuelistCard randomSpellcaster = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.DRAGON);
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomSpellcaster, false, true, true, false, randomSpellcaster.baseTributes > 0, false, true, false, 1, 3, 0, 1, 0, 0));
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "Add #b1 random #yMonster to hand":
				DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomInCombatFromSet(Tags.MONSTER);
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randomMonster, false, true, true, false, false, randomMonster.baseSummons > 0, false, true, 1, 3, 0, 0, 1, 2));
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
				DuelistCard randomToken = (DuelistCard) DuelistCardLibrary.getTokensForCombat().get(AbstractDungeon.cardRandomRng.random(DuelistCardLibrary.getTokensForCombat().size() - 1)).makeCopy();
				DuelistCard.summon(AbstractDungeon.player, 1, randomToken);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
				break;
			case "#ySummon #b2":
				DuelistCard randomTokenB = (DuelistCard) DuelistCardLibrary.getTokensForCombat().get(AbstractDungeon.cardRandomRng.random(DuelistCardLibrary.getTokensForCombat().size() - 1)).makeCopy();
				DuelistCard.summon(AbstractDungeon.player, 2, randomTokenB);
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
					AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat().makeStatEquivalentCopy();
					AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(card, false, true, true, false, false, false, true, true, 1, 3, 0, 1, 1, 2));
				}
				
				// Give self 3 random buffs
				for (int i = 0; i < RAND_BUFFS; i++)
				{
					int randomTurnNumO = AbstractDungeon.cardRandomRng.random(MIN_BUFF_TURNS_ROLL, MAX_BUFF_TURNS_ROLL);
					DuelistCard.applyRandomBuffPlayer(AbstractDungeon.player, randomTurnNumO, false);
				}
				
				// Give 3 random debuffs to enemy
				if (mO != null)
				{
					for (int i = 0; i < RAND_DEBUFFS; i++)
					{
						int randomTurnNumO2 = AbstractDungeon.cardRandomRng.random(MIN_DEBUFF_TURNS_ROLL, MAX_DEBUFF_TURNS_ROLL);
						DuelistCard.applyPower(DebuffHelper.getRandomDebuff(AbstractDungeon.player, mO, randomTurnNumO2), mO);
					}
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
			case "Gain #b1 Max HP":
				AbstractDungeon.player.increaseMaxHp(1, true);
				if (printing) { System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string); }
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
		actions.add("Draw #b2 cards");	
		actions.add("#ySummon #b2");
		actions.add("#yOjamania");	
		actions.add("Gain [E] "); 
		actions.add("Channel a Glitch");
		actions.add("#yIncrement #b2");
		actions.add("Gain #b1 Artifact");
		actions.add("Add #b1 random #yEthereal Duelist card to hand");
		actions.add("#ySummon #b1");
		actions.add("#yIncrement #b1");
		actions.add("Draw #b1 card");	
		actions.add("Draw #b1 card");	
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
		
		translationMap.put("Gain a random amount of gold (1-50)", Strings.configGainGoldA);
		translationMap.put("Draw #b2 cards", Strings.configDraw2Cards);
		translationMap.put("Gain #b5 HP", Strings.configGain5HP);
		translationMap.put("Gain #b10 HP", Strings.configGain10HP);
		translationMap.put("#ySummon #b2", Strings.configSummon2);
		translationMap.put("#yOjamania", Strings.configOjamania);
		translationMap.put("Gain [E] ", Strings.configGainEnergy);
		translationMap.put("Channel a Glitch", Strings.configChannel);
		translationMap.put("#yIncrement #b2", Strings.configIncrement2);
		translationMap.put("Lose #b1 HP", Strings.configLose1HP);
		translationMap.put("Gain #b1 Artifact", Strings.configGainArtifact);
		translationMap.put("Add #b1 random #yEthereal Duelist card to hand", Strings.configAddRandomEtherealDuelist);
		translationMap.put("#ySummon #b1", Strings.configSummon);
		translationMap.put("#yIncrement #b1", Strings.configIncrement);
		translationMap.put("Draw #b1 card", Strings.configDraw1Card);
		translationMap.put("Apply #b2 random #ydebuffs to random enemy", Strings.configApply1RandomDebuff);
		translationMap.put("Apply #b1 random #ydebuff to random enemy", Strings.configApply2RandomDebuffs);
		translationMap.put("Add #b1 random #yTrap to hand", Strings.configAddRandomTrap);
		translationMap.put("Add #b1 random #yMonster to hand", Strings.configAddRandomMonster);
		translationMap.put("Gain #b10 #yBlock", Strings.configGain10Block);
		translationMap.put("Gain #b5 #yBlock",Strings.configGain5Block);
		translationMap.put("Gain #b1 Max HP",Strings.configGain1MAXHPText);
	}

}
