package duelistmod.cards.typecards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.Tags;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.YamiForm;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;

public class DynamicTypeCard extends DuelistCard 
{

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private DuelistCard callCard;
    private String imgSave;
    private CardTags tagSave;
    // /STAT DECLARATION/

    public DynamicTypeCard(String ID, String NAME, String IMG, String DESCRIPTION, CardTags tag, DuelistCard callingCard, int magicNum) 
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
    @Override public AbstractCard makeCopy() { return new DynamicTypeCard(this.cardID, this.name, this.imgSave, this.rawDescription, this.tagSave, this.callCard, this.baseMagicNumber); } 
    @Override public AbstractCard makeStatEquivalentCopy() { return new DynamicTypeCard(this.cardID, this.name, this.imgSave, this.rawDescription, this.tagSave, this.callCard, this.magicNumber); }
    @Override public void use(AbstractPlayer p, AbstractMonster m)  
    {
    	if (this.callCard instanceof RainbowJar)
    	{
    		applyPowerToSelf(new RainbowCapturePower(p, p, this.tagSave));
    	}
    	
    	if (this.callCard instanceof ShardGreed)
    	{
    		applyPowerToSelf(new GreedShardPower(p, p, this.magicNumber, this.tagSave));
    	}
    	
    	if (this.callCard instanceof WingedKuriboh9)
    	{
    		drawTags(this.magicNumber, this.tagSave, Tags.MONSTER, false);
    	}
    	
    	if (this.callCard instanceof WingedKuriboh10)
    	{
    		drawTags(this.magicNumber, this.tagSave, Tags.MONSTER, false);
    	}
    	
    	if (this.callCard instanceof YamiForm)
    	{
    		DuelistCard randResu = (DuelistCard) returnTrulyRandomFromSet(tagSave);
    		while (randResu.hasTag(Tags.EXEMPT)) { randResu = (DuelistCard) returnTrulyRandomFromSet(tagSave); }
    		DuelistCard.fullResummon(randResu, false, AbstractDungeon.getRandomMonster(), false);
    	}
    }   
	@Override public void onTribute(DuelistCard tributingCard)  {}	
	@Override public void onResummon(int summons) {}	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade()  {}	
	@Override public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2)  {}
}