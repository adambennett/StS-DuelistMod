package duelistmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;

public class CastleDarkIllusions extends DuelistCard 
{
    // TEXT DECLARATION 
    public static final String ID = duelistmod.DuelistMod.makeID("CastleDarkIllusions");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.CASTLE_DARK);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION 	
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/


    public CastleDarkIllusions() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.METAL_RAIDERS);
        this.tags.add(Tags.GOOD_TRIB);
        this.originalName = this.name;
        this.summons = this.baseSummons = 1;
        this.isCastle = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	if (this.upgraded) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CastlePower(p, p, true))); }
    	else {  AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CastlePower(p, p, false))); }
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CastleDarkIllusions();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBaseCost(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{

	}

	@Override
	public void onResummon(int summons) 
	{
	
	}
	
    // Checking for Monster Zones if the challenge is enabled
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }

    	if (Utilities.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeMode)
    	{
    		if ((DuelistMod.getChallengeDiffIndex() < 3) && this.misc == 52) { return true; }
    		if (p.hasPower(SummonPower.POWER_ID))
    		{
    			int sums = DuelistCard.getSummons(p); int max = DuelistCard.getMaxSummons(p);
    			if (sums + this.summons <= max) 
    			{ 
    				return true; 
    			}
    			else 
    			{ 
    				if (sums < max) 
    				{ 
    					if (max - sums > 1) { this.cantUseMessage = "You only have " + (max - sums) + " monster zones"; }
    					else { this.cantUseMessage = "You only have " + (max - sums) + " monster zone"; }
    					
    				}
    				else { this.cantUseMessage = "No monster zones remaining"; }
    				return false; 
    			}
    		}
    		else
    		{
    			return true;
    		}
    	}
    	
    	else
    	{
    		return true;
    	}
    }


	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
    	if (this.upgraded) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CastlePower(p, p, true))); }
    	else {  AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CastlePower(p, p, false))); }
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
    	if (this.upgraded) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CastlePower(p, p, true))); }
    	else {  AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CastlePower(p, p, false))); }
		
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