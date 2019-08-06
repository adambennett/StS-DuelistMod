package duelistmod.cards.typecards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.incomplete.HauntedPower;
import duelistmod.variables.Tags;

public class HauntSpells extends DuelistCard 
{
	
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("HauntSpells");
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
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = -2;
    // /STAT DECLARATION/
    
    public HauntSpells(int magic)
    {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
       	this.purgeOnUse = true;
    	this.dontTriggerOnUseCard = true;
    	this.magicNumber = this.baseMagicNumber = magic;
    }

    @Override public String getID() { return this.cardID; }
    @Override public AbstractCard makeCopy() { return new HauntSpells(this.magicNumber); } 
    @Override public AbstractCard makeStatEquivalentCopy() { return new HauntSpells(this.magicNumber); }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	applyPowerToSelf(new HauntedPower(p, p, this.magicNumber, Tags.SPELL));
    }   
	@Override public void onTribute(DuelistCard tributingCard)  {}	
	@Override public void onResummon(int summons) {}	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade()  {}	
	@Override public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2)  {}
}