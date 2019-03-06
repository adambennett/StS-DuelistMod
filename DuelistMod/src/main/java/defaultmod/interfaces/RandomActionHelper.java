package defaultmod.interfaces;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;

import defaultmod.DefaultMod;
import defaultmod.cards.Token;
import defaultmod.orbs.*;
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
    
    // Action List
    public static ArrayList<String> actions = new ArrayList<String>();
    
    public static String triggerRandomAction(int timesTriggered, boolean upgradeOjamaniaCards)
	{
    	initList();
		int randomActionNum = 0;
		String lastAction = "";
		for (int i = 0; i < timesTriggered; i++)
		{
			randomActionNum = AbstractDungeon.cardRandomRng.random(actions.size() - 1);
			lastAction = runAction(actions.get(randomActionNum), upgradeOjamaniaCards);
		}
		
		return lastAction;
	}
	
	public static String runAction(String string, boolean upgradeCards) 
	{
		initList();
		switch (string)
		{
			case "Draw #b2 cards":
				DuelistCard.draw(2);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Gain #b10 HP":
				DuelistCard.heal(AbstractDungeon.player, 10);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Lose #b5 HP":
				DuelistCard.damageSelf(5);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "#yExhaust #b1 random card in hand":
				AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, true));
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Gain a random amount of gold (50-100)":
				int randomGold = AbstractDungeon.cardRandomRng.random(50, 100);
				DuelistCard.gainGold(randomGold, AbstractDungeon.player, true);
				string = "Gain a random amount of gold #b" + randomGold;
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Apply #b1 random #ybuff":
				int randomTurnNum = AbstractDungeon.cardRandomRng.random(1, 3);
				DuelistCard.applyRandomBuff(AbstractDungeon.player, randomTurnNum);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Apply #b1 random #ydebuff to random enemy":
				int randomTurnNumD = AbstractDungeon.cardRandomRng.random(1, 3);
				AbstractMonster m = DuelistCard.getRandomMonster();
				AbstractPower debuff = RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, m, randomTurnNumD);
				DuelistCard.applyPower(debuff, (AbstractCreature)m);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Apply #b2 random #ydebuffs to random enemy":
				
				int randomTurnNumDe = AbstractDungeon.cardRandomRng.random(1, 3);
				AbstractMonster ma = DuelistCard.getRandomMonster();
				AbstractPower debuffB = RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, ma, randomTurnNumDe);
				DuelistCard.applyPower(debuffB, (AbstractCreature)ma);
				
				randomTurnNumDe = AbstractDungeon.cardRandomRng.random(1, 3);
				debuffB = RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, ma, randomTurnNumDe);
				DuelistCard.applyPower(debuffB, (AbstractCreature)ma);
				
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "#yChannel #b1 random orb":
				DuelistCard.channelRandom();
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Add #b1 random #ySpell to hand":
				DuelistCard randomSpell = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.SPELL);
				DuelistCard.addCardToHand(randomSpell);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "#yEvoke #b1 Orb":
				if (AbstractDungeon.player.orbs.size() < 1) 
				{ 
					triggerRandomAction(1, upgradeCards);
					//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: Evoke 1 Orb, but skipped due to no orbs ");
					break; 
				}
				else { DuelistCard.evoke(1); }
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Add #b1 random #yTrap to hand":
				DuelistCard randomTrap = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.TRAP);
				DuelistCard.addCardToHand(randomTrap);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Add #b1 random #ySpellcaster to hand":
				DuelistCard randomSpellcaster = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.SPELLCASTER);
				DuelistCard.addCardToHand(randomSpellcaster);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Add #b1 random #yMonster to hand":
				DuelistCard randomMonster = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.MONSTER);
				DuelistCard.addCardToHand(randomMonster);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Lose #b1 strength":
				AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Lose #b1 dexterity":
				AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Gain #b5 #yBlock":
				DuelistCard.staticBlock(5);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Gain #b10 #yBlock":
				DuelistCard.staticBlock(10);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Gain #b15 #yBlock":
				DuelistCard.staticBlock(15);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "#ySummon #b1":
				DuelistCard.summon(AbstractDungeon.player, 1, new Token("Random Token"));
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "#ySummon #b2":
				DuelistCard.summon(AbstractDungeon.player, 2, new Token("Random Token"));
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "#yIncrement #b1":
				DuelistCard.incMaxSummons(AbstractDungeon.player, 1);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "#yIncrement #b2":
				DuelistCard.incMaxSummons(AbstractDungeon.player, 2);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "#yOjamania":
				AbstractMonster mO = DuelistCard.getRandomMonster();
				// Add 5 random cards to hand, set cost to 0
				for (int i = 0; i < RAND_CARDS; i++)
				{
					AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
					card.costForTurn = 0;
					card.isCostModifiedForTurn = true;
					if (upgradeCards) { card.upgrade(); }
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
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Gain 1 [E] ":
				DuelistCard.gainEnergy(1);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Channel a Glitch":
				AbstractOrb glitch = new Glitch();
				DuelistCard.channel(glitch);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Channel a Buffer":
				AbstractOrb buffer = new Buffer();
				DuelistCard.channel(buffer);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			case "Gain #b3 Artifacts":
				AbstractPower art = new ArtifactPower(AbstractDungeon.player, 3);
				DuelistCard.applyPowerToSelf(art);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered: " + string);
				break;
			default:
				String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
				runAction(randomAction, upgradeCards);
				//System.out.println("theDuelist:RandomActionHelper:runAction ---> triggered (default): " + string);
				break;
		}
		
		return string;
	}

	private static void initList()
	{
		actions = new ArrayList<String>();
		actions.add("Draw #b2 cards");	
		actions.add("Gain #b10 HP"); 
		actions.add("Gain #b5 #yBlock");
		actions.add("Gain a random amount of gold (50-100)"); 
		actions.add("Gain #b10 #yBlock"); 
		actions.add("Apply #b2 random #ydebuffs to random enemy");
		actions.add("Apply #b1 random #ybuff");	
		actions.add("Apply #b1 random #ydebuff to random enemy");	
		actions.add("#yChannel #b1 random orb");
		actions.add("#yEvoke #b1 Orb");	
		actions.add("Add #b1 random #ySpell to hand");
		actions.add("Add #b1 random #yTrap to hand");	
		actions.add("Add #b1 random #ySpellcaster to hand");	
		actions.add("Add #b1 random #yMonster to hand");
		actions.add("Gain #b15 #yBlock");	
		actions.add("#ySummon #b1");
		actions.add("#yIncrement #b1");	
		actions.add("#yIncrement #b2");
		actions.add("#yOjamania"); 
		actions.add("Gain 1 [E] "); 
		actions.add("Channel a Glitch"); 
		actions.add("Channel a Buffer");
		actions.add("Gain #b3 Artifacts");
	}

}
