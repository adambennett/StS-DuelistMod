package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.enums.StartingDecks;
import duelistmod.helpers.*;
import duelistmod.helpers.poolhelpers.GlobalPoolHelper;
import duelistmod.interfaces.*;

public class CardPoolRelic extends DuelistRelic implements ClickableRelic, VisitFromAnubisRemovalFilter
{
	public static final String ID = DuelistMod.makeID("CardPoolRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("CardPoolRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolRelic_Outline.png");
	public CardGroup pool;

	public CardPoolRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.MASTER_DECK);
		setDescription();
	}
	
	public void refreshPool()
	{
		if (DuelistMod.duelistChar != null)
		{
			pool.clear();
			pool.group.addAll(TheDuelist.cardPool.group);
			setDescription();
		}
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new CardPoolRelic();
	}

	@Override
	public void onRightClick() 
	{
		if (pool.size() > 0) {
			DuelistMod.duelistMasterCardViewScreen.open("Card Pool", pool);
		}
	}
	
	public void setDescription()
	{
		this.description = getUpdatedDescription();
		/* Relic Description			*/
		StringBuilder poolDesc;
		
		/* Extra Card Pool Description	*/
		
		// Customized Pool
		if (DuelistMod.poolIsCustomized)
		{
			poolDesc = new StringBuilder(" NL NL Card Pool NL #yColored #b(" + TheDuelist.cardPool.size() + "): NL ");
			poolDesc.append("Custom Pool");
		}
		
		// All cards fill
		else if (DuelistMod.setIndex == 9)
		{
			poolDesc = new StringBuilder(" NL NL Card Pool NL #yColored #b(" + TheDuelist.cardPool.size() + "): NL All Duelist Cards");
			if (DuelistMod.baseGameCards)
			{
				poolDesc.append(" NL All Base Game Cards");
			}
		}
		
		// Fill type is something other than "All Cards"
		else
		{
			poolDesc = new StringBuilder(" NL NL Card Pool NL #yColored #b(" + TheDuelist.cardPool.size() + "): NL ");
			boolean deckIsNormalName = true;
			String altDeckName = "Random Cards";
			if (StartingDecks.currentDeck == StartingDecks.RANDOM_SMALL || StartingDecks.currentDeck == StartingDecks.RANDOM_BIG || StartingDecks.currentDeck == StartingDecks.RANDOM_UPGRADE) {
				deckIsNormalName = false;
			}
			
			/* Colored Cards	*/
			if (DuelistMod.setIndex == 0 || DuelistMod.setIndex == 1 || DuelistMod.setIndex == 3 || DuelistMod.setIndex == 4 || DuelistMod.setIndex == 6 || DuelistMod.setIndex == 8) {
				if (deckIsNormalName) {
					poolDesc.append(StartingDecks.currentDeck.getDeckName());
				} else {
					poolDesc.append(altDeckName);
				}
			} else if (DuelistMod.setIndex == 2) {
				poolDesc.append("Basic Cards");
			}
			
			if (GlobalPoolHelper.addedAnyDecks())
			{
				poolDesc.append(" NL ");
				ArrayList<String> decks = new ArrayList<>();
				if (DuelistMod.addedAquaSet && StartingDecks.currentDeck != StartingDecks.AQUA)
				{
					decks.add("Aqua Deck");
				}
				
				if (DuelistMod.addedArcaneSet)
				{
					decks.add("Arcane Pool");
				}
				
				if (DuelistMod.addedDinoSet)
				{
					decks.add("Dinosaur Pool");
				}
				
				if (DuelistMod.addedDragonSet && StartingDecks.currentDeck != StartingDecks.DRAGON)
				{
					decks.add("Dragon Deck");
				}
				
				if (DuelistMod.addedFiendSet && StartingDecks.currentDeck != StartingDecks.FIEND)
				{
					decks.add("Fiend Deck");
				}
				
				if (DuelistMod.addedIncrementSet && StartingDecks.currentDeck != StartingDecks.INCREMENT)
				{
					decks.add("Increment Deck");
				}
				
				if (DuelistMod.addedInsectSet && StartingDecks.currentDeck != StartingDecks.INSECT)
				{
					decks.add("Insect Deck");
				}
				
				if (DuelistMod.addedMachineSet && StartingDecks.currentDeck != StartingDecks.MACHINE)
				{
					decks.add("Machine Deck");
				}
				
				if (DuelistMod.addedNaturiaSet && StartingDecks.currentDeck != StartingDecks.NATURIA)
				{
					decks.add("Naturia Deck");
				}
				
				if (DuelistMod.addedOjamaSet && StartingDecks.currentDeck != StartingDecks.OJAMA)
				{
					decks.add("Ojama Deck");
				}
				
				if (DuelistMod.addedPlantSet && StartingDecks.currentDeck != StartingDecks.PLANT)
				{
					decks.add("Plant Deck");
				}
				
				if (DuelistMod.addedRockSet)
				{
					decks.add("Rock Pool");
				}
				
				if (DuelistMod.addedSpellcasterSet && StartingDecks.currentDeck != StartingDecks.SPELLCASTER)
				{
					decks.add("Spellcaster Deck");
				}
				
				if (DuelistMod.addedStandardSet && StartingDecks.currentDeck != StartingDecks.STANDARD)
				{
					decks.add("Standard Deck");
				}
				
				if (DuelistMod.addedToonSet && StartingDecks.currentDeck != StartingDecks.TOON)
				{
					decks.add("Toon Deck");
				}
				
				if (DuelistMod.addedWarriorSet && StartingDecks.currentDeck != StartingDecks.WARRIOR)
				{
					decks.add("Warrior Deck");
				}
				
				if (DuelistMod.addedZombieSet && StartingDecks.currentDeck != StartingDecks.ZOMBIE)
				{
					decks.add("Zombie Deck");
				}
				
				for (int i = 0; i < decks.size(); i++) 
				{ 
					if (i + 1 < decks.size()) { poolDesc.append(decks.get(i)).append(" NL "); }
					else { poolDesc.append(decks.get(i)); }
				}
				
				if (deckIsNormalName && DuelistMod.addedRandomCards) { poolDesc.append(" NL Random Cards"); }
			}
			
			// Base Game Cards
			if (DuelistMod.baseGameCards)
			{
				poolDesc.append(" NL Base Game Cards");
			}
			
			else if (DuelistMod.addedRedSet) 
			{
				poolDesc.append(" NL Ironclad Set");
			}
			
			else if (DuelistMod.addedBlueSet) 
			{
				poolDesc.append(" NL Defect Set");
			}
			
			else if (DuelistMod.addedGreenSet) 
			{
				poolDesc.append(" NL Silent Set");
			}
			
			else if (DuelistMod.addedPurpleSet) 
			{
				poolDesc.append(" NL Watcher Set");
			}
			
		}
		

		
		/* Colorless Cards	*/
		// Basic Cards
		boolean holiday = DuelistMod.holidayNonDeckCards.size() > 0;
		boolean basic = DuelistMod.duelColorlessCards.size() > 0;
		if (holiday && basic)
		{
			poolDesc.append(" NL #yColorless #b(").append(DuelistMod.duelColorlessCards.size() + DuelistMod.holidayNonDeckCards.size()).append("): NL Basic Cards NL ");
			if (DuelistMod.addedBirthdayCards)  
			{ 
				if (Util.whichBirthday() == 1) { poolDesc.append("Birthday Cards NL (Nyoxide's Birthday)");  }
				else if (Util.whichBirthday() == 2) 
				{
					String playerName = CardCrawlGame.playerName;
					poolDesc.append("Birthday Cards NL (").append(playerName).append("'s Birthday)");
				}
				else if (Util.whichBirthday() == 3) { poolDesc.append("Birthday Cards NL (DuelistMod's Birthday)"); }
			}
			if (DuelistMod.addedHalloweenCards) 
			{ 
				if (DuelistMod.addedBirthdayCards) { poolDesc.append(" NL Halloween Cards"); }
				else { poolDesc.append("Halloween Cards"); }
			}
			
			else if (DuelistMod.addedXmasCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc.append(" NL Christmas Cards"); }
				else { poolDesc.append("Christmas Cards"); }
			}
			
			else if (DuelistMod.addedWeedCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc.append(" NL 420 Cards"); }
				else { poolDesc.append("420 Cards"); }
			}
		}
		else if (basic)
		{
			poolDesc.append(" NL #yColorless #b(").append(DuelistMod.duelColorlessCards.size()).append("): NL Basic Cards");
		}
		else if (holiday)
		{
			poolDesc.append(" NL #yColorless #b(").append(DuelistMod.holidayNonDeckCards.size()).append("): NL ");
			if (DuelistMod.addedBirthdayCards)  
			{ 
				if (Util.whichBirthday() == 1) { poolDesc.append("Birthday Cards NL (Nyoxide's Birthday)");  }
				else if (Util.whichBirthday() == 2) 
				{
					String playerName = CardCrawlGame.playerName;
					poolDesc.append("Birthday Cards NL (").append(playerName).append("'s Birthday)");
				}
				else if (Util.whichBirthday() == 3) { poolDesc.append("Birthday Cards NL (DuelistMod's Birthday)"); }
			}
			if (DuelistMod.addedHalloweenCards) 
			{ 
				if (DuelistMod.addedBirthdayCards) { poolDesc.append(" NL Halloween Cards"); }
				else { poolDesc.append("Halloween Cards"); }
			}
			
			else if (DuelistMod.addedXmasCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc.append(" NL Christmas Cards"); }
				else { poolDesc.append("Christmas Cards"); }
			}
			
			else if (DuelistMod.addedWeedCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc.append(" NL 420 Cards"); }
				else { poolDesc.append("420 Cards"); }
			}
		}
		if (!poolDesc.toString().equals("FILLPLEASE") && TheDuelist.cardPool.size() > 0) { description += poolDesc; }
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
}
