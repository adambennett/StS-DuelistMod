package defaultmod.interfaces;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

public class StarterDeck 
{

	private CardTags deckTag;
	private CardTags cardTag;
	private String name;
	private String simpleName;
	private ArrayList<DuelistCard> deck = new ArrayList<DuelistCard>();
	private static Map<CardTags, Integer> deckCopiesMap = new HashMap<CardTags, Integer>();
	private int index;
	
	public StarterDeck(CardTags deck, CardTags card, String name, ArrayList<DuelistCard> deckList, int index, String simpleName)
	{
		this.deckTag = deck;
		this.cardTag = card;
		this.name = name;
		this.deck = new ArrayList<DuelistCard>();
		this.deck.addAll(deckList);
		this.index = index;
		this.simpleName = simpleName;
		setupMap();
	}
	
	public StarterDeck(CardTags deck, String name, ArrayList<DuelistCard> deckList, int index, String simpleName)
	{
		this.deckTag = deck;
		this.name = name;
		this.deck = new ArrayList<DuelistCard>();
		this.deck.addAll(deckList);
		this.index = index;
		this.simpleName = simpleName;
		setupMap();
	}
	
	public StarterDeck(CardTags deck, String name, int index, String simpleName)
	{
		this.deckTag = deck;
		this.name = name;
		this.deck = new ArrayList<DuelistCard>();
		this.index = index;
		this.simpleName = simpleName;
		setupMap();
	}
	
	private static void setupMap()
	{
		deckCopiesMap.put(DefaultMod.STANDARD_DECK, 0);
		deckCopiesMap.put(DefaultMod.DRAGON_DECK, 0);
		deckCopiesMap.put(DefaultMod.NATURE_DECK, 0);
		deckCopiesMap.put(DefaultMod.CREATOR_DECK, 0);
		deckCopiesMap.put(DefaultMod.TOON_DECK, 0);
		deckCopiesMap.put(DefaultMod.SPELLCASTER_DECK, 0);
		deckCopiesMap.put(DefaultMod.ORB_DECK, 6);
		deckCopiesMap.put(DefaultMod.RESUMMON_DECK, 7);
		deckCopiesMap.put(DefaultMod.GENERATION_DECK, 8);
		deckCopiesMap.put(DefaultMod.OJAMA_DECK, 9);
		deckCopiesMap.put(DefaultMod.HEAL_DECK, 10);
		deckCopiesMap.put(DefaultMod.INCREMENT_DECK, 11);
	}

	public CardTags getDeckTag() {
		return deckTag;
	}
	public void setDeckTag(CardTags deckTag) {
		this.deckTag = deckTag;
	}
	public CardTags getCardTag() {
		return cardTag;
	}
	public void setCardTag(CardTags cardTag) {
		this.cardTag = cardTag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<DuelistCard> getDeck() {
		return deck;
	}
	public void setDeck(ArrayList<DuelistCard> deck) {
		this.deck = deck;
	}
	
	public static Map<CardTags, Integer> getDeckCopiesMap() {
		return deckCopiesMap;
	}

	public static void setDeckCopiesMap(Map<CardTags, Integer> deckCopiesMap) {
		StarterDeck.deckCopiesMap = deckCopiesMap;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public void fillDeck(ArrayList<DuelistCard> deck)
	{
		this.deck.addAll(deck);
	}
	
	public void emptyAndFillDeck(ArrayList<DuelistCard> deck)
	{
		this.deck = new ArrayList<DuelistCard>();
		this.deck.addAll(deck);
	}
	
	public void addToDeck(DuelistCard card)
	{
		this.deck.add(card);
	}
	
}
