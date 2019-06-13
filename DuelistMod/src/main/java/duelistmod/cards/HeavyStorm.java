package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;

public class HeavyStorm extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("HeavyStorm");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.HEAVY_STORM);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public HeavyStorm() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
       	this.exhaust = true;
       	this.tags.add(Tags.SPELL);
       	this.tags.add(Tags.ALL);
       	this.tags.add(Tags.METAL_RAIDERS);
		this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (p.hasPower(SummonPower.POWER_ID))
    	{
	    	SummonPower summonsInstance = (SummonPower) p.getPower(SummonPower.POWER_ID);
	    	summonsInstance.summonList = new ArrayList<String>();
	    	summonsInstance.amount -= summonsInstance.amount;
	    	summonsInstance.updateDescription();
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new HeavyStorm();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        //if (!this.upgraded) {
           // this.upgradeName();
            //this.upgradeBaseCost(0);
           // this.rawDescription = UPGRADE_DESCRIPTION;
           // this.initializeDescription();
       // }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	return false;
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