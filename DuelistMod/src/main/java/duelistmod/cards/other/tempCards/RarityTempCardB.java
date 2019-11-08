package duelistmod.cards.other.tempCards;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.CardSelectScreenObtainAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class RarityTempCardB extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("RarityTempCardB");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RarityTempCard.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private final CardTags tagSave;
    // /STAT DECLARATION/

    private static String getDesc(CardRarity rare, int mag, CardTags tag)
    {
    	boolean usePlural = mag != 1;
    	String tagString = tag.toString().toLowerCase();
		String temp = tagString.substring(0, 1).toUpperCase();
		tagString = temp + tagString.substring(1);
    	if (rare.equals(CardRarity.COMMON)) { if (usePlural) { return "Choose !duelist:M! of !M! random Common " + tagString + " cards. Add the chosen cards to your deck."; } else { return "Choose !duelist:M! of !M! random Common " + tagString + " cards. Add the chosen card to your deck."; }}
    	else if (rare.equals(CardRarity.UNCOMMON)) { if (usePlural) { return "Choose !duelist:M! of !M! random Uncommon " + tagString + " cards. Add the chosen cards to your deck."; } else { return "Choose !duelist:M! of !M! random Uncommon " + tagString + " cards. Add the chosen card to your deck."; }}
    	else if (rare.equals(CardRarity.RARE)) { if (usePlural) { return "Choose !duelist:M! of !M! random Rare " + tagString + " cards. Add the chosen cards to your deck."; } else { return "Choose !duelist:M! of !M! random Rare " + tagString + " cards. Add the chosen card to your deck."; }}
    	else if (rare.equals(CardRarity.SPECIAL)) { if (usePlural) { return "Choose !duelist:M! of !M! random Special " + tagString + " cards. Add the chosen cards to your deck."; } else { return "Choose !duelist:M! of !M! random Special " + tagString + " cards. Add the chosen card to your deck."; }}
    	else { if (usePlural) { return "Choose !duelist:M! of !M! random Basic " + tagString + " cards. Add the chosen cards to your deck."; } else { return "Choose !duelist:M! of !M! random Basic " + tagString + " cards. Add the chosen card to your deck."; }}
    }
    
    private static String getName(CardRarity rare)
    {
    	if (rare.equals(CardRarity.COMMON)) { return "Common"; }
    	else if (rare.equals(CardRarity.UNCOMMON)) { return "Uncommon"; }
    	else if (rare.equals(CardRarity.RARE)) { return "Rare"; }
    	else if (rare.equals(CardRarity.SPECIAL)) { return "Special"; }
    	else { return "Basic"; }
    }
    
    public RarityTempCardB(int toChooseFrom, int toChoose, CardRarity rare, CardTags tag) 
    { 
    	super(ID, getName(rare), IMG, COST, getDesc(rare, toChoose, tag), TYPE, COLOR, rare, TARGET); 
    	this.dontTriggerOnUseCard = true;
    	this.baseMagicNumber = this.magicNumber = toChooseFrom;
    	this.secondMagic = this.baseSecondMagic = toChoose;
    	this.tagSave = tag;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> cardSelection = new ArrayList<>();
    	Map<String, AbstractCard> added = new HashMap<>();
    	while (cardSelection.size() < 3)
    	{
    		AbstractCard rand = DuelistCard.returnTrulyRandomFromSet(this.tagSave);
    		while (added.containsKey(rand.cardID) || !rand.rarity.equals(this.rarity)) { rand = DuelistCard.returnTrulyRandomFromSet(Tags.MACHINE); }
    		cardSelection.add(rand.makeStatEquivalentCopy());
    		added.put(rand.cardID, rand.makeCopy());
    	}
    	
    	this.addToBot(new CardSelectScreenObtainAction(cardSelection, this.secondMagic));
    }
    @Override public AbstractCard makeCopy() { return new RarityTempCardB(this.magicNumber, this.secondMagic, this.rarity, this.tagSave); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		
	}
	
	@Override public void onResummon(int summons) 
	{ 
		
	}
	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade() {}
	
	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}