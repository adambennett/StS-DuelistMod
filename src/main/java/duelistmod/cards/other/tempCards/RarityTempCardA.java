package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;

public class RarityTempCardA extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("RarityTempCardA");
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
    // /STAT DECLARATION/

    private static String getDesc(CardRarity rare, int mag)
    {
    	boolean usePlural = mag != 1;
    	if (rare.equals(CardRarity.COMMON)) { if (usePlural) { return "Draw !M! Commons"; } else { return "Draw !M! Common"; }}
    	else if (rare.equals(CardRarity.UNCOMMON)) { if (usePlural) { return "Draw !M! Uncommons"; } else { return "Draw !M! Uncommon"; }}
    	else if (rare.equals(CardRarity.RARE)) { if (usePlural) { return "Draw !M! Rares"; } else { return "Draw !M! Rare"; }}
    	else if (rare.equals(CardRarity.SPECIAL)) { if (usePlural) { return "Draw !M! Specials"; } else { return "Draw !M! Special"; }}
    	else { if (usePlural) { return "Draw !M! Basics"; } else { return "Draw !M! Basic"; }}
    }
    
    private static String getName(CardRarity rare)
    {
    	if (rare.equals(CardRarity.COMMON)) { return "Common"; }
    	else if (rare.equals(CardRarity.UNCOMMON)) { return "Uncommon"; }
    	else if (rare.equals(CardRarity.RARE)) { return "Rare"; }
    	else if (rare.equals(CardRarity.SPECIAL)) { return "Special"; }
    	else { return "Basic"; }
    }
    
    public RarityTempCardA(int magicNum, CardRarity rare) 
    { 
    	super(ID, getName(rare), IMG, COST, getDesc(rare, magicNum), TYPE, COLOR, rare, TARGET); 
    	this.dontTriggerOnUseCard = true;
    	this.baseMagicNumber = this.magicNumber = magicNum;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	drawRare(this.magicNumber, this.rarity);
    }
    @Override public AbstractCard makeCopy() { return new RarityTempCardA(this.magicNumber, this.rarity); }

    
    
	
	@Override public void upgrade() {}
	


}
