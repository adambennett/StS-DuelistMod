package duelistmod.cards.other.tempCards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class DynamicRelicTypeCard extends DuelistCard 
{

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private AbstractRelic callCard;
    private String imgSave;
    private CardType tagSave;
    // /STAT DECLARATION/

    public DynamicRelicTypeCard(String ID, String NAME, String IMG, String DESCRIPTION, CardType tag, AbstractRelic callingCard, int magicNum) 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, tag, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.ARCHETYPE);
    	this.purgeOnUse = true;
    	this.dontTriggerOnUseCard = true;
    	this.callCard = callingCard;
    	this.imgSave = IMG;
    	this.tagSave = tag;
    	this.magicNumber = this.baseMagicNumber = magicNum;
        CommonKeywordIconsField.useIcons.set(this, false);
    }
    

    @Override public AbstractCard makeCopy() { return new DynamicRelicTypeCard(this.cardID, this.name, this.imgSave, this.rawDescription, this.tagSave, this.callCard, this.baseMagicNumber); } 
    @Override public AbstractCard makeStatEquivalentCopy() { return new DynamicRelicTypeCard(this.cardID, this.name, this.imgSave, this.rawDescription, this.tagSave, this.callCard, this.magicNumber); }
    @Override public void use(AbstractPlayer p, AbstractMonster m)  
    {
    	
    }   
    
    public CardType getTypeTag()
    {
    	return this.tagSave;
    }
    
    
	
	@Override public void upgrade()  {}	
	
}
