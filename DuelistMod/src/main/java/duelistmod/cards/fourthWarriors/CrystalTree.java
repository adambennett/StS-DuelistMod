package duelistmod.cards.fourthWarriors;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.stances.AbstractStance;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class CrystalTree extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CrystalTree");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CrystalTree.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public CrystalTree() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.SPELL);
        this.baseMagicNumber = this.magicNumber = 3;
        this.secondMagic = this.baseSecondMagic = 3;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		boolean calm = p.stanceName.equals(AbstractStance.StanceName.CALM);
		boolean samurai = p.stance.ID.equals("theDuelist:Samurai");
		boolean wrath = p.stanceName.equals(AbstractStance.StanceName.WRATH);
		boolean guarded = p.stance.ID.equals("theDuelist:Guarded");
		if (calm) { applyPower(new WeakPower(m, this.magicNumber, false), m); }
		if (samurai) { applyPower(new SlowPower(m, this.magicNumber), m); }
		if (wrath) { applyPower(new VulnerablePower(m, this.magicNumber, false), m); }
		if (upgraded && guarded) { applyPowerToSelf(new DexterityPower(p, this.secondMagic)); }
		Util.log("CrystalTree bools :: calm=" + calm + ", samurai=" + samurai + ", wrath=" + wrath + ", guarded=" + guarded);
		Util.log("p.stance=" + p.stance + ", p.stanceName=" + p.stanceName + ", toString versions; p.stance=" + p.stance.toString() + ", p.stanceName=" + p.stanceName.toString() + ", p.stance.ID=" + p.stance.ID);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CrystalTree();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded)
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}