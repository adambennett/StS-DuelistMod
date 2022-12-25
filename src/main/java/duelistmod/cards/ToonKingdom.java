package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class ToonKingdom extends DuelistCard 
{
    // TEXT DECLARATION 
    public static final String ID = duelistmod.DuelistMod.makeID("ToonKingdom");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.TOON_KINGDOM);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION 	
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public ToonKingdom() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.TOON_POOL);
        this.tags.add(Tags.TOON_DONT_TRIG);
        this.tags.add(Tags.FULL);
		this.originalName = this.name;
		this.isInnate = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (!p.hasPower(ToonKingdomPower.POWER_ID) && !p.hasPower(ToonWorldPower.POWER_ID)) { applyPowerToSelf(new ToonKingdomPower(p, p, 2)); }
    	else if (!p.hasPower(ToonKingdomPower.POWER_ID) && p.hasPower(ToonWorldPower.POWER_ID)) 
    	{ 
    		ToonWorldPower pow = (ToonWorldPower) p.getPower(ToonWorldPower.POWER_ID);
    		int lowend = pow.lowend;
    		int maxdmg = pow.maxDmg;
    		int amount = pow.amount;
    		applyPowerToSelf(new ToonKingdomPower(p, p, amount, lowend, maxdmg));
    		removePower(p.getPower(ToonWorldPower.POWER_ID), p);
    	}
    	else if (p.hasPower(ToonKingdomPower.POWER_ID))
    	{ 
    		ToonKingdomPower king = (ToonKingdomPower) p.getPower(ToonKingdomPower.POWER_ID);  
    		if (king.maxDmg > 0)
    		{
    			king.maxDmg--;
    			king.updateDescription();
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ToonKingdom();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
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
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
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
