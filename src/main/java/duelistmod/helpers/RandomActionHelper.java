package duelistmod.helpers;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.*;
import duelistmod.cards.other.tempCards.RarityTempCardA;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.*;
import duelistmod.powers.duelistPowers.FrozenDebuff;

public class RandomActionHelper
{    
    // Action List
    public static ArrayList<String> actions = new ArrayList<String>();
    public static int randomIndex = 0;
    
    public static String triggerRandomAction(int timesTriggered, boolean upgradeOjamaniaCards, boolean talk, boolean screensAllowed)
	{
    	initList(screensAllowed);
		int randomActionNum = 0;
		String lastAction = "";
		for (int i = 0; i < timesTriggered; i++)
		{
			randomActionNum = AbstractDungeon.cardRandomRng.random(actions.size() - 1);
			Util.log("theDuelist:RandomActionHelper:triggerRandomAction() ---> randomActionNum: " + randomActionNum);
			lastAction = runAction(actions.get(randomActionNum), upgradeOjamaniaCards, talk, screensAllowed);
		}
		
		return lastAction;
	}
    

	public static String runAction(String string, boolean upgradeCards, boolean talk, boolean screensAllowed) 
	{
		initList(screensAllowed);
		AbstractPlayer p = AbstractDungeon.player;
		switch (string)
		{
			case "Draw #b1 card":
				DuelistCard.draw(1);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Draw 1 card", 1.0F, 2.0F)); }
				break;
			case "Draw #b1 Common":
				DuelistCard.drawRare(1, CardRarity.COMMON);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Draw 1 Common", 1.0F, 2.0F)); }
				break;
			case "Draw #b1 Uncommon":
				DuelistCard.drawRare(1, CardRarity.UNCOMMON);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Draw 1 Uncommon", 1.0F, 2.0F)); }
				break;
			case "Draw #b1 of chosen rarity":
				ArrayList<AbstractCard> choices = new ArrayList<>();
	    		choices.add(new RarityTempCardA(1, CardRarity.COMMON));
	    		choices.add(new RarityTempCardA(1, CardRarity.UNCOMMON));
	    		choices.add(new RarityTempCardA(1, CardRarity.RARE));
	    		AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(choices, 1));
	    		if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Choose a rarity to draw from", 1.0F, 2.0F)); }
				break;
			case "Draw #b2 cards":
				DuelistCard.draw(2);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Draw 2 cards", 1.0F, 2.0F)); }
				break;
			case "Gain #b5 #yBlock":
				DuelistCard.staticBlock(5);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 5 Block", 1.0F, 2.0F)); }
				break;
			case "Gain #b10 #yBlock":
				DuelistCard.staticBlock(10);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 10 Block", 1.0F, 2.0F)); }
				break;
			case "Gain #b15 #yBlock":
				DuelistCard.staticBlock(15);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 15 Block", 1.0F, 2.0F)); }
				break;
			case "Gain #b5 #yTemp #yHP":
				DuelistCard.gainTempHP(5);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 5 Temporary HP", 1.0F, 2.0F)); }
				break;
			case "Gain #b10 #yTemp #yHP":
				DuelistCard.gainTempHP(10);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 10 Temporary HP", 1.0F, 2.0F)); }
				break;
			case "Gain #b15 #yTemp #yHP":
				DuelistCard.gainTempHP(15);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 15 Temporary HP", 1.0F, 2.0F)); }
				break;
			case "#ySolder #b1":
				AbstractDungeon.actionManager.addToBottom(new SolderAction(p.hand.group, 1, true));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Solder 1", 1.0F, 2.0F)); }
				break;	
			case "#ySolder #b2":
				AbstractDungeon.actionManager.addToBottom(new SolderAction(p.hand.group, 2, true));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Solder 2", 1.0F, 2.0F)); }
				break;	
			case "#ySolder #b3":
				AbstractDungeon.actionManager.addToBottom(new SolderAction(p.hand.group, 3, true));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Solder 3", 1.0F, 2.0F)); }
				break;
			case "#ySolder #b4":
				AbstractDungeon.actionManager.addToBottom(new SolderAction(p.hand.group, 4, true));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Solder 4", 1.0F, 2.0F)); }
				break;	
			case "#ySolder #b5":
				AbstractDungeon.actionManager.addToBottom(new SolderAction(p.hand.group, 5, true));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Solder 5", 1.0F, 2.0F)); }
				break;	
			case "Channel a Fire":
				DuelistCard.channel(new FireOrb());
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Channel a Fire", 1.0F, 2.0F)); }
				break;
			case "Channel an Air":
				DuelistCard.channel(new AirOrb());
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Channel an Air", 1.0F, 2.0F)); }
				break;
			case "Channel a Gadget":
				DuelistCard.channel(new Gadget());
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Channel a Gadget", 1.0F, 2.0F)); }
				break;
			case "Channel a Metal":
				DuelistCard.channel(new Metal());
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Channel a Metal", 1.0F, 2.0F)); }
				break;
			case "Channel a Glitch":
				DuelistCard.channel(new Glitch());
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Channel a Glitch", 1.0F, 2.0F)); }
				break;
			case "Gain [E] ":
				DuelistCard.gainEnergy(1);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 1 Energy", 1.0F, 2.0F)); }
				break;
			case "#yIncrement #b3":
				DuelistCard.incMaxSummons(3, AnyDuelist.from(AbstractDungeon.player));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Increment 3", 1.0F, 2.0F)); }
				break;
			case "#yIncrement #b5":
				DuelistCard.incMaxSummons(5, AnyDuelist.from(AbstractDungeon.player));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Increment 5", 1.0F, 2.0F)); }
				break;
			case "Gain #b1 Artifact":
				AbstractPower artC = new ArtifactPower(p, 1);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 1 Artifact", 1.0F, 2.0F)); }
				DuelistCard.applyPowerToSelf(artC);
				break;
			case "Gain #b2 Artifacts":
				AbstractPower artD = new ArtifactPower(p, 2);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 2 Artifacts", 1.0F, 2.0F)); }
				DuelistCard.applyPowerToSelf(artD);
				break;
			case "#yUpgrade a Card":
				AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(true));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Upgrade a card", 1.0F, 2.0F)); }
				break;
			case "#yResummon a card":
				AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(p.hand.group, 1, new Double(2.0)));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Resummon a random card", 1.0F, 2.0F)); }
				break;
			case "Gain #b1 #yStrength":
				DuelistCard.applyPowerToSelf(new StrengthPower(p, 1));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 1 Strength", 1.0F, 2.0F)); }
				break;	
			case "Gain #b2 #yStrength":
				DuelistCard.applyPowerToSelf(new StrengthPower(p, 2));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Gain 2 Strength", 1.0F, 2.0F)); }
				break;	
			case "#yDetonate X":
				DuelistCard.detonationTributeStatic(AnyDuelist.from(AbstractDungeon.player), 0, true, false, 1, true);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Detonate X", 1.0F, 2.0F)); }
				break;	
			case "#yDetonate #b3":
				DuelistCard.detonationTributeStatic(AnyDuelist.from(AbstractDungeon.player),3, false, false, 1, false);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Detonate 3", 1.0F, 2.0F)); }
				break;	
			case "#yStun a random enemy":
				ArrayList<AbstractMonster> monst = DuelistCard.getAllMons();
				AbstractMonster randy = monst.get(AbstractDungeon.cardRandomRng.random(monst.size() - 1));
				AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(randy, p));
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Stun a random enemy", 1.0F, 2.0F)); }
				break;	
			case "Damage ALL enemies #b5":
				DuelistCard.attackAllEnemiesThorns(5);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Damage ALL enemies for 5", 1.0F, 2.0F)); }
				break;	
			case "Damage ALL enemies #b10":
				DuelistCard.attackAllEnemiesThorns(10);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Damage ALL enemies for 10", 1.0F, 2.0F)); }
				break;	
			case "#yFreeze a random enemy":
				ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
				AbstractMonster targ = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() - 1));
				DuelistCard.applyPower(new FrozenDebuff(targ, p), targ);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Freeze a random enemy", 1.0F, 2.0F)); }
				break;	
			case "#yUpgrade your hand":
				for (AbstractCard c : p.hand.group) { if (c.canUpgrade()) { c.upgrade(); }}
				p.hand.glowCheck();
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Upgrade your hand", 1.0F, 2.0F)); }
				break;	
			case "#yWeaken ALL enemies":
				DuelistCard.weakAllEnemies(1);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Weaken ALL enemies", 1.0F, 2.0F)); }
				break;
			case "#ySlow ALL enemies":
				DuelistCard.slowAllEnemies(1);
				if (talk) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Slow ALL enemies", 1.0F, 2.0F)); }
				break;	
			default:
				String randomAction = actions.get(AbstractDungeon.cardRandomRng.random(actions.size() - 1));
				runAction(randomAction, upgradeCards, talk, screensAllowed);
				Util.log("theDuelist:RandomActionHelper:runAction ---> triggered (default): " + string);
				break;
		}
		
		return string;
	}

	private static void initList(boolean screensAllowed)
	{
		actions = new ArrayList<String>();
		if (screensAllowed)
		{
			actions.add("Draw #b1 of chosen rarity");
			actions.add("#ySolder #b1");
			actions.add("#ySolder #b2");
			actions.add("#ySolder #b3");
			actions.add("#ySolder #b4");
			actions.add("#ySolder #b5");
			actions.add("#yUpgrade a Card");
			actions.add("#yResummon a card");
		}
		else
		{
			actions.add("Gain #b5 #yBlock");
			actions.add("Gain #b15 #yBlock");
			actions.add("Gain #b5 #yTemporary #yHP");
			actions.add("Gain #b15 #yTemporary #yHP");
			actions.add("Damage ALL enemies #b10");
			actions.add("Gain #b2 Artifacts");
			actions.add("Channel an Air");
			actions.add("Channel a Gadget");
			actions.add("Channel a Fire");
		}
		actions.add("#ySlow ALL enemies");
		actions.add("#yWeaken ALL enemies");
		actions.add("#yUpgrade your hand");
		actions.add("#yFreeze a random enemy");
		actions.add("Damage ALL enemies #b5");
		actions.add("Draw #b2 cards");		
		actions.add("Gain [E] "); 
		actions.add("Channel a Fire");
		actions.add("Channel a Gadget");
		actions.add("Channel a Metal");
		//actions.add("Channel a Glitch");
		actions.add("Channel an Air");
		actions.add("Gain #b1 Artifact");
		actions.add("#yIncrement #b5");
		actions.add("#yIncrement #b3");
		actions.add("Draw #b1 card");	
		actions.add("Draw #b1 Common");	
		actions.add("Draw #b1 Uncommon");		
		actions.add("Gain #b10 #yBlock");	
		actions.add("Gain #b10 #yTemp #yHP");		
		actions.add("Gain #b1 #yStrength");
		actions.add("Gain #b2 #yStrength");
		actions.add("#yDetonate X");
		actions.add("#yDetonate #b3");
		actions.add("#yStun a random enemy");
	}

}
