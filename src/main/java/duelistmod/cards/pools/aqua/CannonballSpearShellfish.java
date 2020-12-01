package duelistmod.cards.pools.aqua;

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

public class CannonballSpearShellfish extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CannonballSpearShellfish");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CannonballSpearShellfish.png");
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

    public CannonballSpearShellfish() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 3;
        this.specialCanUseLogic = true;
        this.summons = this.baseSummons = 1;
        this.baseDamage = this.damage = 6;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.AQUA);
    }
    
    public void onSummonWhileSummoned(DuelistCard summoned, int amountSummoned) 
    {
    	if (summoned.hasTag(Tags.AQUA) && amountSummoned > 0 && !(summoned instanceof CannonballSpearShellfish))
    	{
    		for (int i = 0; i < amountSummoned; i++)
    		{
    			AbstractMonster m = AbstractDungeon.getRandomMonster();
    			if (m != null) { attack(m, this.baseAFX, this.magicNumber); }
    		}
    	}
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	attack(m);
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
		
	}

	@Override
	public void onResummon(int summons) 
	{
		
		
	}

	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new CannonballSpearShellfish(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
