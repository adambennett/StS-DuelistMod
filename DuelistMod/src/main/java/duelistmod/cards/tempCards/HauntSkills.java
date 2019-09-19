package duelistmod.cards.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.powers.incomplete.HauntedPower;

public class HauntSkills extends DuelistCard 
{
	
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("HauntSkills");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("HauntedShrine.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.GREEN;
    private static final int COST = -2;
    // /STAT DECLARATION/
    
    public HauntSkills(int magic)
    {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
       	this.purgeOnUse = true;
    	this.dontTriggerOnUseCard = true;
    	this.magicNumber = this.baseMagicNumber = magic;
    }

    @Override public String getID() { return this.cardID; }
    @Override public AbstractCard makeCopy() { return new HauntSkills(this.magicNumber); } 
    @Override public AbstractCard makeStatEquivalentCopy() { return new HauntSkills(this.magicNumber); }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	applyPowerToSelf(new HauntedPower(p, p, this.magicNumber, CardType.SKILL));
    }   
	@Override public void onTribute(DuelistCard tributingCard)  {}	
	@Override public void onResummon(int summons) {}	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade()  {}	
	@Override public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2)  {}
}