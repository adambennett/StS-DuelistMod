package duelistmod.cards.pools.zombies;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class Mispolymerization extends DuelistCard 
{
    // TEXT DECLARATION
    private static final CardStrings cardStrings = getCardStrings();
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public Mispolymerization() {
        super(getCARDID(), NAME, getIMG(), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.FUSION);
        this.misc = 0;
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 2;
        this.baseSecondMagic = this.secondMagic = 5;
        this.selfRetain = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> drawCards = new ArrayList<AbstractCard>();
    	if (upgraded) { for (AbstractCard c : p.drawPile.group) { if (allowResummonsWithExtraChecks(c) && c.hasTag(Tags.MONSTER)) { drawCards.add(c); }}}
    	else { for (AbstractCard c : p.drawPile.group) { if (allowResummonsWithExtraChecks(c)) { drawCards.add(c); }} }
    	if (drawCards.size() > 0)
    	{
    		AbstractCard rand = drawCards.get(AbstractDungeon.cardRandomRng.random(drawCards.size() - 1));
    		resummon(rand, m, this.magicNumber);
    		if (!rand.hasTag(Tags.ZOMBIE)) { damageSelf(this.secondMagic); }
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Mispolymerization();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription(); 
        }
    }
    


	@Override
	public void onTribute(DuelistCard tributingCard)
	{
		
	}

	

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getID() {
		return getCARDID();
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	// AUTOSETUP - ID/IMG - Id, Img name, and class name all must match to use this
    public static String getCARDID()
    {
    	return DuelistMod.makeID(getCurClassName());
    }
    
	public static CardStrings getCardStrings()
    {
    	return CardCrawlGame.languagePack.getCardStrings(getCARDID());
    }
    
    public static String getIMG()
    {
    	return DuelistMod.makeCardPath(getCurClassName() + ".png");
    }
    
    public static String getCurClassName()
    {
    	return (new CurClassNameGetter()).getClassName();
    }

    public static class CurClassNameGetter extends SecurityManager{
    	public String getClassName(){
    		return getClassContext()[1].getSimpleName();
    	}
    }
    // END AUTOSETUP
}
