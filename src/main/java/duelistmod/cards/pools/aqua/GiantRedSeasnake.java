package duelistmod.cards.pools.aqua;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class GiantRedSeasnake extends DuelistCard 
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

    public GiantRedSeasnake() {
        super(getCARDID(), NAME, getIMG(), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.AQUA);
        this.tags.add(Tags.GIANT);
        this.tags.add(Tags.EXEMPT);
        this.misc = 0;
        this.specialCanUseLogic = true;
        this.useTributeCanUse = true;
        this.originalName = this.name;
        this.damage = this.baseDamage = 55;
        this.baseTributes = this.tributes = 16;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void onIncrementWhileInHand(int amount, int newMaxSummons) 
    {
    	if (this.tributes > 0 && amount > 0)
    	{
    		this.modifyTributes(-this.magicNumber);
    	}
    }

    @Override
	public void onIncrementWhileInDraw(int amount, int newMaxSummons) 
    {
    	if (this.tributes > 0 && amount > 0)
    	{
    		this.modifyTributes(-this.magicNumber);
    	}
    }

    @Override
	public void onIncrementWhileInDiscard(int amount, int newMaxSummons) 
    {
    	if (this.tributes > 0 && amount > 0)
    	{
    		this.modifyTributes(-this.magicNumber);
    	}
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	attack(m);
    	if (this.tributes == 0)
    	{
    		AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, 16 - this.tributes, true));
    		this.rawDescription = this.originalDescription;
    		this.initializeDescription();    		
    	}
    	else if (this.tributes != 16)
    	{
    		AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, 16 - this.tributes, true));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GiantRedSeasnake();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeDamage(10);
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
