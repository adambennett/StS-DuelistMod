package duelistmod.cards.other.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
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
    	if (rare.equals(CardRarity.COMMON)) { if (usePlural) { return "Choose !duelist:M! of !M! random Common " + tagString + " cards. Add the chosen cards to your hand."; } else { return "Choose !duelist:M! of !M! random Common " + tagString + " cards. Add the chosen card to your hand."; }}
    	else if (rare.equals(CardRarity.UNCOMMON)) { if (usePlural) { return "Choose !duelist:M! of !M! random Uncommon " + tagString + " cards. Add the chosen cards to your hand."; } else { return "Choose !duelist:M! of !M! random Uncommon " + tagString + " cards. Add the chosen card to your hand."; }}
    	else if (rare.equals(CardRarity.RARE)) { if (usePlural) { return "Choose !duelist:M! of !M! random Rare " + tagString + " cards. Add the chosen cards to your hand."; } else { return "Choose !duelist:M! of !M! random Rare " + tagString + " cards. Add the chosen card to your hand."; }}
    	else if (rare.equals(CardRarity.SPECIAL)) { if (usePlural) { return "Choose !duelist:M! of !M! random Special " + tagString + " cards. Add the chosen cards to your hand."; } else { return "Choose !duelist:M! of !M! random Special " + tagString + " cards. Add the chosen card to your hand."; }}
    	else { if (usePlural) { return "Choose !duelist:M! of !M! random Basic " + tagString + " cards. Add the chosen cards to your hand."; } else { return "Choose !duelist:M! of !M! random Basic " + tagString + " cards. Add the chosen card to your hand."; }}
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
    	this.tags.add(Tags.ALLOYED);
    	this.dontTriggerOnUseCard = true;
    	this.baseMagicNumber = this.magicNumber = toChooseFrom;
    	this.secondMagic = this.baseSecondMagic = toChoose;
    	this.tagSave = tag;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> cardSelection = hundredMachines(this.rarity, this.magicNumber);
    	this.addToBot(new CardSelectScreenIntoHandAction(cardSelection, this.secondMagic));
    }
    @Override public AbstractCard makeCopy() { return new RarityTempCardB(this.magicNumber, this.secondMagic, this.rarity, this.tagSave); }

    
    
	
	@Override public void upgrade() {}
	


}
