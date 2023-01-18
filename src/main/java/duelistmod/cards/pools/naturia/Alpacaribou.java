package duelistmod.cards.pools.naturia;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.LeavesPower;
import duelistmod.powers.duelistPowers.VinesPower;
import duelistmod.variables.Tags;

public class Alpacaribou extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Alpacaribou");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Alpacaribou.png");
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

    public Alpacaribou() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 12;
        this.tributes = this.baseTributes = 0;
        this.exhaust = true;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.X_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {

    	xCostTribute();
    	attack(m);
        AbstractPower powCheck = Util.vinesPower(1);
        int vinesAmt = p.hasPower(VinesPower.POWER_ID) ? p.getPower(VinesPower.POWER_ID).amount : 0;
        int leavesAmt = p.hasPower(LeavesPower.POWER_ID) ? p.getPower(LeavesPower.POWER_ID).amount : 0;

        if (powCheck instanceof VinesPower) {
            applyPowerToSelf(Util.vinesPower(vinesAmt));
        } else if (powCheck instanceof LeavesPower) {
            applyPowerToSelf(Util.vinesPower(leavesAmt));
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
        	this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }


	






	
	@Override
    public AbstractCard makeCopy() { return new Alpacaribou(); }
	
}
