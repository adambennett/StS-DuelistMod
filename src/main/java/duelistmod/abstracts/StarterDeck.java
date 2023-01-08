package duelistmod.abstracts;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;

import duelistmod.dto.builders.PuzzleConfigDataBuilder;
import duelistmod.variables.Tags;

public class StarterDeck 
{

	private final CardTags deckTag;
	private CardTags cardTag;
	private final String simpleName;
	public ArrayList<CardTags> tagsThatMatchCards = new ArrayList<>();
	private final ArrayList<DuelistCard> deck;
	private final ArrayList<AbstractCard> poolCards = new ArrayList<>();
	private static final Map<CardTags, Integer> deckCopiesMap = new HashMap<>();
	private final int index;

	public StarterDeck(CardTags deck, int index, String simpleName)
	{
		this.deckTag = deck;
		this.deck = new ArrayList<>();
		this.index = index;
		this.simpleName = simpleName;
		setupMap();
	}

	public PuzzleConfigDataBuilder setupBuilder(PuzzleConfigDataBuilder builder) {
		return builder;
	}
	
	private static void setupMap()
	{
		deckCopiesMap.put(Tags.STANDARD_DECK, 0);
		deckCopiesMap.put(Tags.DRAGON_DECK, 1);
		deckCopiesMap.put(Tags.NATURIA_DECK, 2);
		deckCopiesMap.put(Tags.SPELLCASTER_DECK, 3);
		deckCopiesMap.put(Tags.TOON_DECK, 4);
		deckCopiesMap.put(Tags.ZOMBIE_DECK, 5);
		deckCopiesMap.put(Tags.AQUA_DECK, 6);
		deckCopiesMap.put(Tags.FIEND_DECK, 7);
		deckCopiesMap.put(Tags.MACHINE_DECK, 8);
		deckCopiesMap.put(Tags.WARRIOR_DECK, 9);
		deckCopiesMap.put(Tags.INSECT_DECK, 10);
		deckCopiesMap.put(Tags.PLANT_DECK, 11);
		deckCopiesMap.put(Tags.PREDAPLANT_DECK, 12);
		deckCopiesMap.put(Tags.MEGATYPE_DECK, 13);
		deckCopiesMap.put(Tags.INCREMENT_DECK, 14);
		deckCopiesMap.put(Tags.CREATOR_DECK, 15);
		deckCopiesMap.put(Tags.OJAMA_DECK, 16);		
		deckCopiesMap.put(Tags.EXODIA_DECK, 17);
		deckCopiesMap.put(Tags.GIANT_DECK, 18);
		deckCopiesMap.put(Tags.ASCENDED_ONE_DECK, 19);
		deckCopiesMap.put(Tags.ASCENDED_TWO_DECK, 20);
		deckCopiesMap.put(Tags.ASCENDED_THREE_DECK, 21);
		deckCopiesMap.put(Tags.PHARAOH_ONE_DECK, 22);
		deckCopiesMap.put(Tags.PHARAOH_TWO_DECK, 23);
		deckCopiesMap.put(Tags.PHARAOH_THREE_DECK, 24);
		deckCopiesMap.put(Tags.PHARAOH_FOUR_DECK, 25);
		deckCopiesMap.put(Tags.PHARAOH_FIVE_DECK, 26);
		deckCopiesMap.put(Tags.METRONOME_DECK, 27);
	}

	public CardTags getDeckTag() {
		return deckTag;
	}
	public CardTags getCardTag() {
		return cardTag;
	}
	public ArrayList<DuelistCard> getDeck() {
		return deck;
	}
	public static Map<CardTags, Integer> getDeckCopiesMap() {
		return deckCopiesMap;
	}
	public int getIndex() {
		return this.index;
	}
	public String getSimpleName() {
		return simpleName;
	}

	public ArrayList<AbstractCard> getPoolCards() {
		return poolCards;
	}

	public void fillPoolCards(ArrayList<AbstractCard> poolCards)
	{
		this.poolCards.addAll(poolCards);
	}

}
