package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.variables.*;

public class AncientRules extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("AncientRules");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.ANCIENT_RULES);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public AncientRules() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = 1;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LIMITED);
		this.tags.add(Tags.ANCIENT_FOR_PIXIE);
		this.tags.add(Tags.ANCIENT_FOR_MACHINE);
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	for (AbstractCard c : p.drawPile.group)
    	{
    		if (c.hasTag(Tags.MONSTER))
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.tributes > 0)
    			{
    				dC.changeTributesInBattle(-this.magicNumber, true);
    			}
    		}
    	}
    	
    	for (AbstractCard c : p.hand.group)
    	{
    		if (c.hasTag(Tags.MONSTER))
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.tributes > 0)
    			{
    				dC.changeTributesInBattle(-this.magicNumber, true);
    			}
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new AncientRules();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
        	this.upgradeName();
        	this.upgradeMagicNumber(1);
            if (DuelistMod.hasUpgradeBuffRelic) { this.upgradeBaseCost(0); }
        	this.rawDescription = UPGRADE_DESCRIPTION;
        	this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
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
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		
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