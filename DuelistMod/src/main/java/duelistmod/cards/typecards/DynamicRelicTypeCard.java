package duelistmod.cards.typecards;

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
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private AbstractRelic callCard;
    private String imgSave;
    private CardTags tagSave;
    // /STAT DECLARATION/

    public DynamicRelicTypeCard(String ID, String NAME, String IMG, String DESCRIPTION, CardTags tag, AbstractRelic callingCard, int magicNum) 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.ARCHETYPE);
    	this.tags.add(tag);
    	this.purgeOnUse = true;
    	this.dontTriggerOnUseCard = true;
    	this.callCard = callingCard;
    	this.imgSave = IMG;
    	this.tagSave = tag;
    	this.magicNumber = this.baseMagicNumber = magicNum;
    }
    
    @Override public String getID() { return this.cardID; }
    @Override public AbstractCard makeCopy() { return new DynamicRelicTypeCard(this.cardID, this.name, this.imgSave, this.rawDescription, this.tagSave, this.callCard, this.baseMagicNumber); } 
    @Override public AbstractCard makeStatEquivalentCopy() { return new DynamicRelicTypeCard(this.cardID, this.name, this.imgSave, this.rawDescription, this.tagSave, this.callCard, this.magicNumber); }
    @Override public void use(AbstractPlayer p, AbstractMonster m)  
    {
    	
    }   
    
    public CardTags getTypeTag()
    {
    	return this.tagSave;
    }
    
    
	@Override public void onTribute(DuelistCard tributingCard)  {}	
	@Override public void onResummon(int summons) {}	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade()  {}	
	@Override public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2)  {}
}