package duelistmod.interfaces;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;

import duelistmod.*;
import duelistmod.patches.DuelistCard;

public class StarterDeck 
{

	private CardTags deckTag;
	private CardTags cardTag;
	private String name;
	private String simpleName;
	private ArrayList<DuelistCard> deck = new ArrayList<DuelistCard>();
	private ArrayList<DuelistCard> poolCards = new ArrayList<DuelistCard>();
	private static Map<CardTags, Integer> deckCopiesMap = new HashMap<CardTags, Integer>();
	private int index;
	private boolean fullPool = false;
	
	public StarterDeck(CardTags deck, CardTags card, String name, ArrayList<DuelistCard> deckList, int index, String simpleName, boolean fullPool)
	{
		this.deckTag = deck;
		this.cardTag = card;
		this.name = name;
		this.deck = new ArrayList<DuelistCard>();
		this.deck.addAll(deckList);
		this.index = index;
		this.simpleName = simpleName;
		this.fullPool = fullPool;
		setupMap();
	}
	
	public StarterDeck(CardTags deck, String name, ArrayList<DuelistCard> deckList, int index, String simpleName, boolean fullPool)
	{
		this.deckTag = deck;
		this.name = name;
		this.deck = new ArrayList<DuelistCard>();
		this.deck.addAll(deckList);
		this.index = index;
		this.simpleName = simpleName;
		this.fullPool = fullPool;
		setupMap();
	}
	
	public StarterDeck(CardTags deck, String name, int index, String simpleName, boolean fullPool)
	{
		this.deckTag = deck;
		this.name = name;
		this.deck = new ArrayList<DuelistCard>();
		this.index = index;
		this.simpleName = simpleName;
		this.fullPool = fullPool;
		setupMap();
	}
	
	private static void setupMap()
	{
		deckCopiesMap.put(Tags.STANDARD_DECK, 0);
		deckCopiesMap.put(Tags.DRAGON_DECK, 1);
		deckCopiesMap.put(Tags.NATURE_DECK, 2);
		deckCopiesMap.put(Tags.CREATOR_DECK, 3);
		deckCopiesMap.put(Tags.TOON_DECK, 4);
		deckCopiesMap.put(Tags.SPELLCASTER_DECK, 5);
		deckCopiesMap.put(Tags.ORB_DECK, 6);
		deckCopiesMap.put(Tags.RESUMMON_DECK, 7);
		deckCopiesMap.put(Tags.GENERATION_DECK, 8);
		deckCopiesMap.put(Tags.OJAMA_DECK, 9);
		deckCopiesMap.put(Tags.HEAL_DECK, 10);
		deckCopiesMap.put(Tags.INCREMENT_DECK, 11);
		deckCopiesMap.put(Tags.EXODIA_DECK, 12);
		deckCopiesMap.put(Tags.MAGNET_DECK, 13);
		deckCopiesMap.put(Tags.AQUA_DECK, 14);
		deckCopiesMap.put(Tags.MACHINE_DECK, 15);
		deckCopiesMap.put(Tags.ORIGINAL_DECK, 16);
		deckCopiesMap.put(Tags.OP_DRAGON_DECK, 17);
		deckCopiesMap.put(Tags.OP_NATURE_DECK, 18);
		deckCopiesMap.put(Tags.OP_SPELLCASTER_DECK, 19);
		deckCopiesMap.put(Tags.ORIGINAL_ORB_DECK, 20);
		deckCopiesMap.put(Tags.ORIGINAL_RESUMMON_DECK, 21);
		deckCopiesMap.put(Tags.ORIGINAL_HEAL_DECK, 22);
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

	public boolean isFullPool() {
		return fullPool;
	}

	public void setFullPool(boolean fullPool) {
		this.fullPool = fullPool;
	}

	public ArrayList<DuelistCard> getPoolCards() {
		return poolCards;
	}

	public void setPoolCards(ArrayList<DuelistCard> poolCards) {
		this.poolCards = poolCards;
	}
	
	public void fillPoolCards(ArrayList<DuelistCard> poolCards)
	{
		this.poolCards.addAll(poolCards);
	}
	
}
