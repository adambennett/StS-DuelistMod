package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.enums.CardPoolType;
import duelistmod.enums.StartingDeck;
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
		else if (DuelistMod.cardPoolType == CardPoolType.ALL_CARDS)
		{
			poolDesc = new StringBuilder(" NL NL Card Pool NL #yColored #b(" + TheDuelist.cardPool.size() + "): NL All Duelist Cards");
			if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards())
			{
				poolDesc.append(" NL All Base Game Cards");
			}
		}
		
		// Fill type is something other than "All Cards"
		else
		{
			poolDesc = new StringBuilder(" NL NL Card Pool NL #yColored #b(" + TheDuelist.cardPool.size() + "): ");
			boolean deckIsNormalName = true;
			String altDeckName = "Random Cards";
			if (StartingDeck.currentDeck == StartingDeck.RANDOM_SMALL || StartingDeck.currentDeck == StartingDeck.RANDOM_BIG || StartingDeck.currentDeck == StartingDeck.RANDOM_UPGRADE) {
				deckIsNormalName = false;
			}
			
			/* Colored Cards	*/
			if (DuelistMod.cardPoolType.includesDeck()) {
				poolDesc.append("NL ");
				if (deckIsNormalName) {
					poolDesc.append(StartingDeck.currentDeck.getDeckName());
				} else {
					poolDesc.append(altDeckName);
				}
			} else if (DuelistMod.cardPoolType == CardPoolType.BASIC_ONLY) {
				poolDesc.append("NL Basic Cards");
			}
			
			if (GlobalPoolHelper.addedAnyDecks())
			{
				poolDesc.append(" NL ");
				ArrayList<String> decks = new ArrayList<>();
				if (DuelistMod.addedAquaSet && (StartingDeck.currentDeck != StartingDeck.AQUA || !DuelistMod.cardPoolType.includesDeck()))
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
				
				if (DuelistMod.addedDragonSet && (StartingDeck.currentDeck != StartingDeck.DRAGON || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Dragon Deck");
				}
				
				if (DuelistMod.addedFiendSet && (StartingDeck.currentDeck != StartingDeck.FIEND || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Fiend Deck");
				}

				if (DuelistMod.addedIncrementSet && (StartingDeck.currentDeck != StartingDeck.INCREMENT || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Increment Deck");
				}

				if (DuelistMod.addedInsectSet && (StartingDeck.currentDeck != StartingDeck.INSECT || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Insect Deck");
				}
				
				if (DuelistMod.addedMachineSet && (StartingDeck.currentDeck != StartingDeck.MACHINE || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Machine Deck");
				}
				
				if (DuelistMod.addedNaturiaSet && (StartingDeck.currentDeck != StartingDeck.NATURIA || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Naturia Deck");
				}
				
				if (DuelistMod.addedBeastSet && (StartingDeck.currentDeck != StartingDeck.BEAST || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Beast Deck");
				}
				
				if (DuelistMod.addedPlantSet && (StartingDeck.currentDeck != StartingDeck.PLANT || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Plant Deck");
				}
				
				if (DuelistMod.addedRockSet)
				{
					decks.add("Rock Pool");
				}
				
				if (DuelistMod.addedSpellcasterSet && (StartingDeck.currentDeck != StartingDeck.SPELLCASTER || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Spellcaster Deck");
				}
				
				if (DuelistMod.addedStandardSet && (StartingDeck.currentDeck != StartingDeck.STANDARD || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Standard Deck");
				}
				
				if (DuelistMod.addedToonSet && (StartingDeck.currentDeck != StartingDeck.TOON || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Toon Deck");
				}
				
				if (DuelistMod.addedWarriorSet && (StartingDeck.currentDeck != StartingDeck.WARRIOR || !DuelistMod.cardPoolType.includesDeck()))
				{
					decks.add("Warrior Deck");
				}
				
				if (DuelistMod.addedZombieSet && (StartingDeck.currentDeck != StartingDeck.ZOMBIE || !DuelistMod.cardPoolType.includesDeck()))
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
			if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards())
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
		int basicCards = (int) AbstractDungeon.colorlessCardPool.group.stream().filter(c -> c.color != AbstractCard.CardColor.COLORLESS).count();
		int colorlessCards = (int) AbstractDungeon.colorlessCardPool.group.stream().filter(c -> c.color == AbstractCard.CardColor.COLORLESS).count();
		boolean colorless = colorlessCards > 0;
		if (holiday && basic && colorless) {
			poolDesc.append(" NL #yColorless #b(").append(AbstractDungeon.colorlessCardPool.group.size() + DuelistMod.holidayNonDeckCards.size()).append("): NL Basic Cards #b(").append(basicCards).append(") NL Colorless Cards #b(").append(colorlessCards).append(") NL ");
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
		} else if (holiday && colorless) {
			poolDesc.append(" NL #yColorless #b(").append(AbstractDungeon.colorlessCardPool.group.size() + DuelistMod.holidayNonDeckCards.size()).append("): NL Colorless Cards #b(").append(colorlessCards).append(") NL ");
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
		} else if (basic && colorless) {
			poolDesc.append(" NL #yColorless #b(").append(AbstractDungeon.colorlessCardPool.group.size()).append("): NL Basic Cards #b(").append(basicCards).append(") NL Colorless Cards #b(").append(colorlessCards).append(")");
		} else if (holiday && basic) {
			poolDesc.append(" NL #yColorless #b(").append(AbstractDungeon.colorlessCardPool.group.size() + DuelistMod.holidayNonDeckCards.size()).append("): NL Basic Cards #b(").append(basicCards).append(") NL ");
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
		} else if (basic) {
			poolDesc.append(" NL #yColorless #b(").append(AbstractDungeon.colorlessCardPool.group.size()).append("): NL Basic Cards #b(").append(basicCards).append(")");
		} else if (holiday) {
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
		} else if (colorless) {
			poolDesc.append(" NL #yColorless #b(").append(AbstractDungeon.colorlessCardPool.group.size()).append("): NL Colorless Cards #b(").append(colorlessCards).append(")");
		}
		if (!poolDesc.toString().equals("FILLPLEASE") && TheDuelist.cardPool.size() > 0) { description += poolDesc; }
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
}
