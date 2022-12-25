package duelistmod.cards.pools.zombies;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.bookOfLifeOptions.CustomResummonCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class Miscellaneousaurus extends DuelistCard 
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
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public Miscellaneousaurus() {
        super(getCARDID(), NAME, getIMG(), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ZOMBIE);
        this.tags.add(Tags.DINOSAUR);
        this.misc = 0;
        this.originalName = this.name;
        this.baseTributes = this.tributes = 2;
        this.baseDamage = this.damage = 10; 
        this.exhaust = true;
        this.specialCanUseLogic = true;
        this.useTributeCanUse = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	attack(m);
    	int costRoll = AbstractDungeon.cardRandomRng.random(1, 3);
    	int noCardsRoll = AbstractDungeon.cardRandomRng.random(1, 3);
    	boolean randomCardRoll = AbstractDungeon.cardRandomRng.randomBoolean();
    	boolean randomTargRoll = false;
    	boolean allTargetsRoll = false;
    	int targetRoll = AbstractDungeon.cardRandomRng.random(1, 100);
    	if (targetRoll < 30) { randomTargRoll = true; }
    	else if (targetRoll > 80) { allTargetsRoll = true; }
    	boolean exhaustRoll = AbstractDungeon.cardRandomRng.randomBoolean();
    	boolean cardinalRoll = AbstractDungeon.cardRandomRng.randomBoolean();
    	if (cardinalRoll) { exhaustRoll = false; }
    	int restrictRoll = AbstractDungeon.cardRandomRng.random(1, 100);
    	int locationRoll = AbstractDungeon.cardRandomRng.random(0, 9);
    	CardTags restrict = null;
    	if (restrictRoll > 20)
    	{
    		restrict = Tags.MONSTER;
    	}
    	else if (restrictRoll > 45)
    	{
    		restrict = Tags.ZOMBIE;
    	}
    	else if (restrictRoll > 90)
    	{
    		restrict = Tags.GHOSTRICK;
    	}
    	addCardToHand(new CustomResummonCard(allTargetsRoll, costRoll, noCardsRoll, randomCardRoll, restrict, locationRoll, randomTargRoll, exhaustRoll, false, cardinalRoll));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Miscellaneousaurus();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
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
