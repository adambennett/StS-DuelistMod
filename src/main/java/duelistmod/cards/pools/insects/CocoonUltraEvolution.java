package duelistmod.cards.pools.insects;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.statuses.ColdBlooded;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class CocoonUltraEvolution extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CocoonUltraEvolution");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CocoonUltraEvolution.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public CocoonUltraEvolution() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseBlock = this.block = 18;
        this.tributes = this.baseTributes = 6;
        this.magicNumber = this.baseMagicNumber = 5;
        this.misc = 0;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.BAD_MAGIC);
        this.exhaust = true;
        this.cardsToPreview = new GreatMoth();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<DuelistCard> tribs = tribute();
    	block();
    	boolean foundPetit = false;
    	for (AbstractCard c : tribs)
    	{
    		if (c instanceof PetitMoth) { foundPetit = true; }
    	}
    	if (foundPetit && !p.hasPower(CocoonPower.POWER_ID)) { applyPowerToSelf(new CocoonPower(p, p, 3)); }
    	else if (foundPetit) 
    	{
    		CocoonPower power = (CocoonPower) player().getPower(CocoonPower.POWER_ID);
    		power.amount -= 2;
			power.updateDescription();
    	}
    	else if (!foundPetit && !p.hasPower(CocoonPower.POWER_ID))
    	{
    		applyPowerToSelf(new CocoonPower(p, p, this.magicNumber));
    	}
    	else if (!foundPetit)
    	{
    		CocoonPower power = (CocoonPower) player().getPower(CocoonPower.POWER_ID);
    		power.amount -= 2;
			power.updateDescription();
    	}
    }

    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(-2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }







	
	@Override
    public AbstractCard makeCopy() { return new CocoonUltraEvolution(); }

}
