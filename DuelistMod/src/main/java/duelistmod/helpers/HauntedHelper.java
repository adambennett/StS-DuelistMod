package duelistmod.helpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.statuses.*;
import duelistmod.cards.tokens.PlagueToken;
import duelistmod.powers.MortalityPower;
import duelistmod.powers.incomplete.HauntedDebuff;

public class HauntedHelper
{
    private static boolean printing = DuelistMod.debug;
    
    // Action List
    public static ArrayList<String> actions = new ArrayList<String>();
    public static int randomIndex = 0;
    
    public static String triggerRandomAction(int timesTriggered, AbstractCard tc, boolean isDebuff)
	{
    	initList(isDebuff);
		int randomActionNum = 0;
		String lastAction = "";
		for (int i = 0; i < timesTriggered; i++)
		{
			randomActionNum = AbstractDungeon.cardRandomRng.random(actions.size() - 1);
			if (DuelistMod.debug) { System.out.println("theDuelist:HauntedHelper:runAction() ---> randomActionNum: " + randomActionNum); }
			if (i + 1 >= timesTriggered) { lastAction += runAction(actions.get(randomActionNum), tc, isDebuff); }
			else { lastAction += runAction(actions.get(randomActionNum), tc, isDebuff) + ", "; }			
		}
		return lastAction;
	}

	public static String runAction(String string, AbstractCard tc, boolean isDebuff) 
	{
		initList(isDebuff);
		AbstractPlayer p = AbstractDungeon.player;
		switch (string)
		{
			case "Discard #b1 card":
				DuelistCard.discard(1, false);
				if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
				break;
			case "Discard #b2 cards":
				DuelistCard.discard(2, false);
				if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
				break;
			case "Discard #b1 card randomly":
				DuelistCard.discardTop(1, true);
				if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
				break;
			case "Lose a random amount of gold (1-50)":
				int goldRoll = AbstractDungeon.cardRandomRng.random(1, 50);
				p.loseGold(goldRoll);
				string = "Lose #b" + goldRoll + " gold";
				if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
				break;
			case "Lose #b1 HP":
				DuelistCard.damageSelf(1);
				if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
				break;
			case "Lose #b2 HP":
				DuelistCard.damageSelf(2);
				if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
				break;
			case "Lose #b1 HP for each card in your hand":
				if (p.hand.group.size() - 1 > 0) { DuelistCard.damageSelf(p.hand.group.size() - 1); }
				if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
				break;
			case "Tribute ALL Summons":
				if (DuelistCard.getSummons(p) < 1)
				{
					String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
					string = runAction(randomAction, tc, isDebuff);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered (no summons == no tribute): " + string); }
					break;
				}
				else
				{
					DuelistCard.powerTribute(p, 0, true);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
					break;
				}
			case "Tribute #b1":
				if (DuelistCard.getSummons(p) < 1)
				{
					String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
					string = runAction(randomAction, tc, isDebuff);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered (no summons == no tribute): " + string); }
					break;
				}
				else
				{
					DuelistCard.powerTribute(p, 1, false);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
					break;
				}
			case "Tribute #b2":
				if (DuelistCard.getSummons(p) < 2)
				{
					String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
					string = runAction(randomAction, tc, isDebuff);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered (no summons == no tribute): " + string); }
					break;
				}
				else
				{
					DuelistCard.powerTribute(p, 2, false);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
					break;
				}
			case "Increase the Tribute cost of a card in hand by #b1":
				if (!runTributeIncreaseEffect(string, 1, tc)) 
				{
					String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
					string = runAction(randomAction, tc, isDebuff);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered (no tribute cards in hand == cant increase any): " + string); } 
				}
				break;
			case "Increase the Tribute cost of a card in hand by #b2":
				if (!runTributeIncreaseEffect(string, 2, tc)) 
				{
					String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
					string = runAction(randomAction, tc, isDebuff);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered (no tribute cards in hand == cant increase any): " + string); } 
				}
				break;
			case "Increase the Tribute cost of a card in hand by #b3":
				if (!runTributeIncreaseEffect(string, 3, tc)) 
				{
					String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
					string = runAction(randomAction, tc, isDebuff);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered (no tribute cards in hand == cant increase any): " + string); } 
				}
				break;
			case "Reduce the Summons of a monster in hand by #b1":
				if (!runSummonReduceEffect(string, 1, tc))
				{
					String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
					string = runAction(randomAction, tc, isDebuff);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered (no summon cards in hand == cant reduce any): " + string); }
				}
				break;
			case "Reduce the Summons of a monster in hand by #b2":
				if (!runSummonReduceEffect(string, 2, tc))
				{
					String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
					string = runAction(randomAction, tc, isDebuff);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered (no summon cards in hand == cant reduce any): " + string); }
				}
				break;
			case "Lose [E] ":
				p.loseEnergy(1);
				break;
			case "Reduce your Max Summons by #b1":
				if (DuelistCard.getMaxSummons(p) > 1)
				{
					DuelistCard.decMaxSummons(p, 1);
				}
				else
				{
					String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
					string = runAction(randomAction, tc, isDebuff);
					if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered (max summons too low to reduce by 1): " + string); }
				}				
				break;
			case "Gain #b1 random debuff":
				AbstractPower randomDebuff = DebuffHelper.getRandomPlayerDebuffForHaunt(p, 2);
				DuelistCard.applyPowerToSelf(randomDebuff);
				break;
			case "Gain #b2 random debuffs":
				AbstractPower randomDebuffB = DebuffHelper.getRandomPlayerDebuffForHaunt(p, 2);
				AbstractPower randomDebuffC = DebuffHelper.getRandomPlayerDebuffForHaunt(p, 2);
				DuelistCard.applyPowerToSelf(randomDebuffB);
				DuelistCard.applyPowerToSelf(randomDebuffC);
				break;
			case "Apply #b1 Strength to a random enemy":
				AbstractCreature mon = AbstractDungeon.getRandomMonster();
				if (mon != null)
				{
					DuelistCard.applyPower(new StrengthPower(mon, 1), mon);
				}
				break;
			case "Apply #b1 Thorns to a random enemy":
				AbstractCreature monB = AbstractDungeon.getRandomMonster();
				if (monB != null)
				{
					DuelistCard.applyPower(new ThornsPower(monB, 1), monB);
				}
				break;
			case "Apply #b2 Thorns to a random enemy":
				AbstractCreature monC = AbstractDungeon.getRandomMonster();
				if (monC != null)
				{
					DuelistCard.applyPower(new ThornsPower(monC, 2), monC);
				}
				break;
			case "Apply #b3 Thorns to a random enemy":
				AbstractCreature monD = AbstractDungeon.getRandomMonster();
				if (monD != null)
				{
					DuelistCard.applyPower(new ThornsPower(monD, 3), monD);
				}
				break;
			case "Apply #b4 Thorns to a random enemy":
				AbstractCreature monE = AbstractDungeon.getRandomMonster();
				if (monE != null)
				{
					DuelistCard.applyPower(new ThornsPower(monE, 4), monE);
				}
				break;
			case "Apply #b2 Thorns to ALL enemies":
				for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
				{
					if (!m.isDead && !m.halfDead && !m.isDying && !m.escaped && !m.isEscaping && !m.isDeadOrEscaped() && m != null)
					{
						DuelistCard.applyPower(new ThornsPower(m, 2), m);
					}
				}
				break;
			case "Apply #b1 Strength to ALL enemies":
				for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
				{
					if (!m.isDead && !m.halfDead && !m.isDying && !m.escaped && !m.isEscaping && !m.isDeadOrEscaped() && m != null)
					{
						DuelistCard.applyPower(new StrengthPower(m, 1), m);
					}
				}				
				break;
			case "Apply #b2 Strength to ALL enemies":
				for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
				{
					if (!m.isDead && !m.halfDead && !m.halfDead && !m.isDying && !m.escaped && !m.isEscaping && !m.isDeadOrEscaped() && m != null)
					{
						DuelistCard.applyPower(new StrengthPower(m, 2), m);
					}
				}				
				break;
			case "Increase Haunted stacks by #b2":
				for (AbstractPower pow : p.powers)
				{
					if (pow instanceof HauntedDebuff)
					{
						pow.amount += 2;
						pow.updateDescription();
					}
				}
				break;
			case "Increase Haunted stacks by #b1":
				for (AbstractPower pow : p.powers)
				{
					if (pow instanceof HauntedDebuff)
					{
						pow.amount++;
						pow.updateDescription();
					}
				}			
				break;
			case "Add a random Duelist Curse to your draw pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(DuelistCardLibrary.getRandomDuelistCurse(), 1, true, true));				
				break;
			case "ALL enemies gain #b5 Block":
				int block = 5;
				for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) { if (!m.isDead && !m.halfDead && !m.isDying && !m.escaped && !m.isEscaping && !m.isDeadOrEscaped()) { m.addBlock(block); }}	
				break;
			case "ALL enemies gain #b10 Block":
				int blockB = 10;
				for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) { if (!m.isDead && !m.halfDead && !m.isDying && !m.escaped && !m.isEscaping && !m.isDeadOrEscaped()) { m.addBlock(blockB); }}
				break;
			case "Add #b1 Wound to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new HauntedWound(), 1));
				break;
			case "Add #b1 Swarm to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Swarm(), 1));
				break;
			case "Add #b1 Cold-Blooded to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new ColdBlooded(), 1));
				break;
			case "Add #b1 Dazed to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new HauntedDaze(), 1));				
				break;
			case "Add #b1 Slimed to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new HauntedSlime(), 1));				
				break;
			case "Add #b1 Burn to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new HauntedBurn(), 1));				
				break;
			case "Add #b1 Wound to your draw pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new HauntedWound(), 1, true, true));
				break;
			case "Add #b1 Swarm to your draw pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Swarm(), 1, true, true));
				break;
			case "Add #b1 Cold-Blooded to your draw pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new ColdBlooded(), 1, true, true));
				break;
			case "Add #b1 Dazed to your draw pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new HauntedDaze(), 1, true, true));				
				break;
			case "Add #b1 Slimed to your draw pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new HauntedSlime(), 1, true, true));				
				break;
			case "Add #b1 Burn to your draw pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new HauntedBurn(), 1, true, true));				
				break;
			case "Add #b2 Wounds to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new HauntedWound(), 2));				
				break;
			case "Add #b2 Swarms to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Swarm(), 2));				
				break;
			case "Add #b2 Cold-Blooded to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new ColdBlooded(), 2));				
				break;
			case "Add #b2 Dazed to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new HauntedDaze(), 2));			
				break;
			case "Add #b2 Slimed to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new HauntedSlime(), 2));				
				break;
			case "Add #b2 Burns to your discard pile":
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new HauntedBurn(), 2));				
				break;
			case "Lose #b1 Max HP":
				p.decreaseMaxHealth(1);
				break;
			case "Summon #b3 Plague Tokens":
				DuelistCard.summon(p, 3, new PlagueToken());
				break;
			case "Summon #b2 Plague Tokens":
				DuelistCard.summon(p, 2, new PlagueToken());
				break;
			case "Summon #b1 Plague Token":
				DuelistCard.summon(p, 1, new PlagueToken());
				break;
			case "Restrict Resummons":
				DuelistCard.applyPowerToSelf(new MortalityPower(p, p, 2));
				break;
			default:
				String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
				runAction(randomAction, tc, isDebuff);
				if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered (default): " + string); }
				break;
		}
		
		return string;
	}

	private static void initList(boolean isDebuff)
	{
		actions = new ArrayList<String>();		 
		//actions.add("Lose a random amount of gold (1-50)");							//
		//actions.add("Lose a random amount of gold (1-50)");							//
		actions.add("Discard #b2 cards");											//
		actions.add("Discard #b1 card");											//
		actions.add("Discard #b1 card");											//
		actions.add("Discard #b1 card");											//
		actions.add("Discard #b1 card randomly");									//
		actions.add("Discard #b1 card randomly");									//
		//actions.add("Lose #b2 HP");													//
		actions.add("Lose #b1 HP");													//
		actions.add("Lose #b1 HP");													//
		actions.add("Lose #b1 HP for each card in your hand");						//
		//actions.add("Tribute ALL Summons");											//
		actions.add("Tribute #b1");													//
		actions.add("Tribute #b1");													//
		//actions.add("Tribute #b2");													//
		actions.add("Increase the Tribute cost of a card in hand by #b1");			//
		actions.add("Increase the Tribute cost of a card in hand by #b1");			//
		actions.add("Increase the Tribute cost of a card in hand by #b1");			//
		actions.add("Increase the Tribute cost of a card in hand by #b2");			//
		actions.add("Increase the Tribute cost of a card in hand by #b3");			//
		actions.add("Reduce the Summons of a monster in hand by #b1");				//
		actions.add("Reduce the Summons of a monster in hand by #b1");				//
		actions.add("Reduce the Summons of a monster in hand by #b2");				//
		actions.add("Lose [E] "); 													//
		actions.add("Lose [E] "); 													//
		//actions.add("Reduce your Max Summons by #b1");								//
		//actions.add("Lose #b1 Max HP");												//
		//actions.add("Gain #b2 random debuffs");										//
		actions.add("Gain #b1 random debuff");						
		actions.add("Gain #b1 random debuff");
		actions.add("Apply #b1 Strength to a random enemy");
		actions.add("Apply #b1 Strength to a random enemy");
		actions.add("Apply #b1 Strength to a random enemy");
		actions.add("Apply #b1 Strength to ALL enemies");
		//actions.add("Apply #b2 Strength to ALL enemies");
		actions.add("Apply #b1 Thorns to a random enemy");
		actions.add("Apply #b1 Thorns to a random enemy");
		actions.add("Apply #b2 Thorns to a random enemy");
		actions.add("Apply #b2 Thorns to a random enemy");
		//actions.add("Apply #b3 Thorns to a random enemy");
		//actions.add("Apply #b4 Thorns to a random enemy");
		actions.add("Apply #b2 Thorns to ALL enemies");
		//actions.add("Summon #b3 Plague Tokens");
		//actions.add("Summon #b2 Plague Tokens");
		//actions.add("Summon #b1 Plague Token");
		//actions.add("Summon #b1 Plague Token");
		//actions.add("Summon #b1 Plague Token");
		actions.add("Restrict Resummons");
		actions.add("Restrict Resummons");
		actions.add("Add #b2 Burns to your discard pile");
		actions.add("Add #b2 Slimed to your discard pile");
		actions.add("Add #b2 Dazed to your discard pile");
		actions.add("Add #b2 Wounds to your discard pile");
		actions.add("Add #b2 Swarms to your discard pile");
		actions.add("Add #b2 Cold-Blooded to your discard pile");
		actions.add("Add #b1 Burn to your discard pile");
		actions.add("Add #b1 Slimed to your discard pile");
		actions.add("Add #b1 Dazed to your discard pile");
		actions.add("Add #b1 Wound to your discard pile");
		actions.add("Add #b1 Swarm to your discard pile");
		actions.add("Add #b1 Cold-Blooded to your discard pile");
		actions.add("Add #b1 Burn to your draw pile");
		actions.add("Add #b1 Slimed to your draw pile");
		actions.add("Add #b1 Dazed to your draw pile");
		actions.add("Add #b1 Wound to your draw pile");
		actions.add("Add #b1 Swarm to your draw pile");
		actions.add("Add #b1 Cold-Blooded to your draw pile");
		actions.add("ALL enemies gain #b10 Block");
		actions.add("ALL enemies gain #b5 Block");
		//actions.add("Add a random Duelist Curse to your draw pile");
		
		if (isDebuff) 
		{ 
			//actions.add("Increase Haunted stacks by #b1"); 
			//actions.add("Increase Haunted stacks by #b2"); 
		}
	}
	
	private static boolean runTributeIncreaseEffect(String string, int amt, AbstractCard tc)
	{
		AbstractPlayer p = AbstractDungeon.player;
		ArrayList<DuelistCard> handTribs = new ArrayList<DuelistCard>();
		for (AbstractCard c : p.hand.group)
		{
			if (c instanceof DuelistCard && !c.uuid.equals(tc.uuid))
			{
				DuelistCard dc = (DuelistCard)c;
				if (dc.tributes > 0) { handTribs.add(dc); }
			}
		}
		if (handTribs.size() > 0)
		{
			handTribs.get(AbstractDungeon.cardRandomRng.random(handTribs.size() - 1)).modifyTributesForTurn(amt);
			if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static boolean runSummonReduceEffect(String string, int amt, AbstractCard tc)
	{
		AbstractPlayer p = AbstractDungeon.player;
		ArrayList<DuelistCard> handTribs = new ArrayList<DuelistCard>();
		for (AbstractCard c : p.hand.group)
		{
			if (c instanceof DuelistCard && !c.uuid.equals(tc.uuid))
			{
				DuelistCard dc = (DuelistCard)c;
				if (dc.summons > 0) { handTribs.add(dc); }
			}
		}
		if (handTribs.size() > 0)
		{
			handTribs.get(AbstractDungeon.cardRandomRng.random(handTribs.size() - 1)).modifySummonsForTurn(-amt);
			if (printing) { System.out.println("theDuelist:HauntedHelper:runAction ---> triggered: " + string); }
			return true;
		}
		else
		{
			return false;
		}
	}

}
