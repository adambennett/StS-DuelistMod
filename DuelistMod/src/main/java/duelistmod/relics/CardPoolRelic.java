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
import com.megacrit.cardcrawl.relics.AbstractRelic.*;
import com.megacrit.cardcrawl.rooms.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.*;
import duelistmod.helpers.poolhelpers.GlobalPoolHelper;
import duelistmod.ui.DuelistMasterCardViewScreen;

public class CardPoolRelic extends DuelistRelic implements ClickableRelic
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("CardPoolRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("CardPoolRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolRelic_Outline.png");
	public CardGroup pool;

	public CardPoolRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.MASTER_DECK);
		setDescription();
		DuelistMod.poolIsCustomized = false;
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
	
	@Override
	public void onVictory() 
	{
		boolean eliteVictory = AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite;
		boolean boss = AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
		if (!StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Metronome Deck") && !boss)
		{
			if (StarterDeckSetup.getCurrentDeck().getIndex() > 0 && StarterDeckSetup.getCurrentDeck().getIndex() < 14) { BoosterPackHelper.generateBoosterOnVictory(DuelistMod.lastPackRoll, eliteVictory, StarterDeckSetup.getCurrentDeck().tagsThatMatchCards); }
			else { BoosterPackHelper.generateBoosterOnVictory(DuelistMod.lastPackRoll, eliteVictory, null); }
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
		DuelistMasterCardViewScreen dmcvs = new DuelistMasterCardViewScreen("Card Pool", pool);
		AbstractDungeon.deckViewScreen = dmcvs;
		DuelistMod.lastDeckViewWasCustomScreen = true;
		if (pool.size() > 0) { dmcvs.open(); }
	}
	
	public void setDescription()
	{
		this.description = getUpdatedDescription();
		/* Relic Description			*/
		String poolDesc = "FILLPLEASE";
		
		/* Extra Card Pool Description	*/
		
		// Customized Pool
		if (DuelistMod.poolIsCustomized)
		{
			poolDesc = " NL NL Card Pool NL #yColored #b(" + TheDuelist.cardPool.size() + "): NL ";
			poolDesc += "Custom Pool";
		}
		
		// All cards fill
		else if (DuelistMod.setIndex == 9)
		{
			poolDesc = " NL NL Card Pool NL #yColored #b(" + TheDuelist.cardPool.size() + "): NL All Duelist Cards";
			if (DuelistMod.baseGameCards)
			{
				poolDesc += " NL All Base Game Cards";
			}
		}
		
		// Fill type is something other than "All Cards"
		else
		{
			poolDesc = " NL NL Card Pool NL #yColored #b(" + TheDuelist.cardPool.size() + "): NL ";
			boolean deckIsNormalName = true;
			String altDeckName = "Random Cards";
			if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Random Deck (Big)") || StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Random Deck (Small)") || StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Upgrade Deck")) { deckIsNormalName = false; }
			
			/* Colored Cards	*/
			if (DuelistMod.setIndex == 0)
			{
				if (deckIsNormalName) { poolDesc += StarterDeckSetup.getCurrentDeck().getSimpleName(); }
				else { poolDesc += altDeckName; }
			}
			else if (DuelistMod.setIndex == 1)
			{
				if (deckIsNormalName) { poolDesc += StarterDeckSetup.getCurrentDeck().getSimpleName(); }
				else { poolDesc += altDeckName; }
			}
			else if (DuelistMod.setIndex == 2)
			{
				poolDesc += "Basic Cards";
			}
			else if (DuelistMod.setIndex == 3)
			{
				if (deckIsNormalName) { poolDesc += StarterDeckSetup.getCurrentDeck().getSimpleName(); }
				else { poolDesc += altDeckName; }
			}
			else if (DuelistMod.setIndex == 4)
			{
				if (deckIsNormalName) { poolDesc += StarterDeckSetup.getCurrentDeck().getSimpleName(); }
				else { poolDesc += altDeckName; }
			}
			else if (DuelistMod.setIndex == 6)
			{
				if (deckIsNormalName) { poolDesc += StarterDeckSetup.getCurrentDeck().getSimpleName(); }
				else { poolDesc += altDeckName; }
			}
			else if (DuelistMod.setIndex == 8)
			{
				if (deckIsNormalName) { poolDesc += StarterDeckSetup.getCurrentDeck().getSimpleName(); }
				else { poolDesc += altDeckName; }
			}
			
			if (GlobalPoolHelper.addedAnyDecks())
			{
				poolDesc += " NL ";
				ArrayList<String> decks = new ArrayList<>();
				if (DuelistMod.addedAquaSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Aqua Deck"))
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
				
				if (DuelistMod.addedDragonSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Dragon Deck"))
				{
					decks.add("Dragon Deck");
				}
				
				if (DuelistMod.addedFiendSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Fiend Deck"))
				{
					decks.add("Fiend Deck");
				}
				
				if (DuelistMod.addedIncrementSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Increment Deck"))
				{
					decks.add("Increment Deck");
				}
				
				if (DuelistMod.addedInsectSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Insect Deck"))
				{
					decks.add("Insect Deck");
				}
				
				if (DuelistMod.addedMachineSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Machine Deck"))
				{
					decks.add("Machine Deck");
				}
				
				if (DuelistMod.addedNaturiaSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Naturia Deck"))
				{
					decks.add("Naturia Deck");
				}
				
				if (DuelistMod.addedOjamaSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Ojama Deck"))
				{
					decks.add("Ojama Deck");
				}
				
				if (DuelistMod.addedPlantSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Plant Deck"))
				{
					decks.add("Plant Deck");
				}
				
				if (DuelistMod.addedRockSet)
				{
					decks.add("Rock Pool");
				}
				
				if (DuelistMod.addedSpellcasterSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Spellcaster Deck"))
				{
					decks.add("Spellcaster Deck");
				}
				
				if (DuelistMod.addedStandardSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Standard Deck"))
				{
					decks.add("Standard Deck");
				}
				
				if (DuelistMod.addedToonSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Toon Deck"))
				{
					decks.add("Toon Deck");
				}
				
				if (DuelistMod.addedWarriorSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Warrior Deck"))
				{
					decks.add("Warrior Deck");
				}
				
				if (DuelistMod.addedZombieSet && !StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Zombie Deck"))
				{
					decks.add("Zombie Deck");
				}
				
				for (int i = 0; i < decks.size(); i++) 
				{ 
					if (i + 1 < decks.size()) { poolDesc += (decks.get(i) + " NL "); }
					else { poolDesc += (decks.get(i)); }
				}
				
				if (deckIsNormalName && DuelistMod.addedRandomCards) { poolDesc += " NL Random Cards"; }
			}
			
			// Base Game Cards
			if (DuelistMod.baseGameCards)
			{
				poolDesc += " NL Base Game Cards";
			}
			
			else if (DuelistMod.addedRedSet) 
			{
				poolDesc += " NL Ironclad Set";
			}
			
			else if (DuelistMod.addedBlueSet) 
			{
				poolDesc += " NL Defect Set";
			}
			
			else if (DuelistMod.addedGreenSet) 
			{
				poolDesc += " NL Silent Set";
			}
			
			else if (DuelistMod.addedPurpleSet) 
			{
				poolDesc += " NL Watcher Set";
			}
			
		}
		

		
		/* Colorless Cards	*/
		// Basic Cards
		boolean holiday = DuelistMod.holidayNonDeckCards.size() > 0;
		boolean basic = DuelistMod.duelColorlessCards.size() > 0;
		if (holiday && basic)
		{
			poolDesc += " NL #yColorless #b(" + (DuelistMod.duelColorlessCards.size() + DuelistMod.holidayNonDeckCards.size()) + "): NL Basic Cards NL ";
			if (DuelistMod.addedBirthdayCards)  
			{ 
				if (Util.whichBirthday() == 1) { poolDesc += "Birthday Cards NL (Nyoxide's Birthday)";  }
				else if (Util.whichBirthday() == 2) 
				{
					String playerName = CardCrawlGame.playerName;
					poolDesc += "Birthday Cards NL (" + playerName + "'s Birthday)";
				}
				else if (Util.whichBirthday() == 3) { poolDesc += "Birthday Cards NL (DuelistMod's Birthday)"; }
			}
			if (DuelistMod.addedHalloweenCards) 
			{ 
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL Halloween Cards"; }
				else { poolDesc += "Halloween Cards"; }
			}
		}
		else if (basic)
		{
			poolDesc += " NL #yColorless #b(" + DuelistMod.duelColorlessCards.size() + "): NL Basic Cards";
		}
		else if (holiday)
		{
			poolDesc += " NL #yColorless #b(" + DuelistMod.holidayNonDeckCards.size() + "): NL ";
			if (DuelistMod.addedBirthdayCards)  
			{ 
				if (Util.whichBirthday() == 1) { poolDesc += "Birthday Cards NL (Nyoxide's Birthday)";  }
				else if (Util.whichBirthday() == 2) 
				{
					String playerName = CardCrawlGame.playerName;
					poolDesc += "Birthday Cards NL (" + playerName + "'s Birthday)";
				}
				else if (Util.whichBirthday() == 3) { poolDesc += "Birthday Cards NL (DuelistMod's Birthday)"; }
			}
			if (DuelistMod.addedHalloweenCards) 
			{ 
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL Halloween Cards"; }
				else { poolDesc += "Halloween Cards"; }
			}
		}
		if (!poolDesc.equals("FILLPLEASE") && TheDuelist.cardPool.size() > 0) { description += poolDesc; }
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
}
