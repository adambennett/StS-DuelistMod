package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class Carboneddon extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = duelistmod.DuelistMod.makeID("Carboneddon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Carboneddon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public Carboneddon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DINOSAUR);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 1;
        this.summons = this.baseSummons = 2;
        this.isSummon = true;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	for (AbstractCard c : p.drawPile.group)
    	{
    		if (c instanceof DuelistCard && c.hasTag(Tags.DINOSAUR))
    		{
    			DuelistCard dc = (DuelistCard)c;
    			if (dc.isTributeCard())
    			{
    				dc.modifyTributes(-this.magicNumber);
    			}
    		}
    	}
    	for (AbstractCard c : p.drawPile.group)
    	{
    		if (c instanceof DuelistCard && c.hasTag(Tags.DINOSAUR))
    		{
    			DuelistCard dc = (DuelistCard)c;
    			if (dc.isTributeCard())
    			{
    				dc.modifyTributes(-this.magicNumber);
    			}
    		}
    	}
    	
    	for (AbstractCard c : p.drawPile.group)
    	{
    		if (c instanceof DuelistCard && c.hasTag(Tags.DINOSAUR))
    		{
    			DuelistCard dc = (DuelistCard)c;
    			if (dc.isTributeCard())
    			{
    				dc.modifyTributes(-this.magicNumber);
    			}
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Carboneddon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
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
