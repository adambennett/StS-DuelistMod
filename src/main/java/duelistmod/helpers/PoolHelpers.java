package duelistmod.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.enums.StartingDeck;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PoolHelpers {
	public static void newFillColored(String deckName) {
		// before calling this we try to load saved array for colored cards
		// if save exists, fill colored cards with saved cards
		// if the save does not exist, fill colored cards with the below code
		
		// refresh all the cards in all pools to match any "remove all of.." options the player may have selected since startup
		StarterDeckSetup.refreshPoolOptions(deckName);
		
		// All Cards - exception where pool is filled based on DuelistMod.myCards (and possibly the base game set)
		if (!DuelistMod.isNotAllCardsPoolType())
		{
			allCardsFillHelper();
			Util.log("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 9");
		}
		
		else
		{
			Map<String,String> added = new HashMap<>();
			for (AbstractCard c : StartingDeck.currentDeck.coloredPoolCopies()) {
				if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && !added.containsKey(c.name)) {
					boolean startingCheck = DuelistMod.persistentDuelistData.CardPoolSettings.getAllowStartingDeckCardsInPool() || !StartingDeck.currentDeck.isCardInStartingDeck(c.cardID);
					if (startingCheck) {
						added.put(c.name, c.name);
						DuelistMod.coloredCards.add(c);
					}
				}
			}
			Util.log("Card Fill was NOT 'All Cards' so we are filling the pool specifically based on your fill type");
		}
		
		// POWER CHECK
		boolean hasPower = false;
		int powerCounter = 0;
		int powersToFind = 5;
		for (AbstractCard c : DuelistMod.coloredCards) { if (c.type.equals(CardType.POWER)) { powerCounter++; }}
		if (powerCounter >= powersToFind) { hasPower = true; }
		if (!hasPower)
		{
			Util.log("Power check triggered!");
		}
		ArrayList<AbstractCard> nonDupes = new ArrayList<AbstractCard>();
		ArrayList<String> nonDupeNames = new ArrayList<String>();
		for (AbstractCard c : DuelistMod.coloredCards)
		{
			if (nonDupeNames.contains(c.name))
			{
				Util.log("Found duplicate of " + c.name + " so it got removed");
			}
			else
			{
				nonDupes.add(c.makeCopy());
				nonDupeNames.add(c.name);
			}
		}
		
		DuelistMod.coloredCards.clear();
		DuelistMod.coloredCards.addAll(nonDupes);
		
		// EXTRA PROCESSING
		if (DuelistMod.debug) { int counter = 1; for (AbstractCard c : DuelistMod.coloredCards) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredCards() ---> coloredCards (" + counter + "): " + c.originalName); counter++; }}
		if (DuelistMod.debug) { for (AbstractCard c : DuelistMod.coloredCards) { if (c.rarity.equals(CardRarity.SPECIAL) || c.rarity.equals(CardRarity.BASIC)) { Util.log("Found bad card inside colored cards after filling. Card Name: " + c.originalName); }}}
		DuelistMod.rareCardInPool = new ArrayList<AbstractCard>(); // For CardLibPatch (fills rare cards properly from pool to return rare cards from)
		for (AbstractCard c : DuelistMod.coloredCards) { if (c.rarity.equals(CardRarity.RARE) && !c.hasTag(Tags.TOKEN)) { DuelistMod.rareCardInPool.add(c); }}
		// END EXTRA PROCESSING
	}

	public static void allCardsFillHelper() {
		for (DuelistCard c : DuelistMod.myCards) {
			if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && !c.color.equals(AbstractCardEnum.DUELIST_SPECIAL)) {
				DuelistMod.coloredCards.add(c);
			}
		}
		
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards()) {
			 DuelistMod.coloredCards.addAll(BaseGameHelper.getAllBaseGameCards());
		}
	}
}
