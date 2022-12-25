package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.variables.Tags;

public class Berserking extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Berserking");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Berserking.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public Berserking() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 1;
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.NO_METRONOME);
        this.misc = 0;
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (p.hasPower(Dragonscales.POWER_ID)) { applyPowerToSelf(new Dragonscales(5 * p.getPower(Dragonscales.POWER_ID).amount)); }
    	if (p.hasPower(StrengthPower.POWER_ID)) { applyPowerToSelf(new Dragonscales(2 * p.getPower(StrengthPower.POWER_ID).amount)); }
    	applyPowerToSelf(new PoisonPower(p, p, 333));
    	applyPowerToSelf(new PoisonPower(p, p, 333));
    	applyPowerToSelf(new PoisonPower(p, p, 333));
    	applyPowerToSelf(new BeserkingDebuffA(333));
    	applyPowerToSelf(new BeserkingDebuffA(333));
    	applyPowerToSelf(new BeserkingDebuffA(333));
    	applyPowerToSelf(new BeserkingDebuffB());
    	applyPowerToSelf(new BeserkingDebuffB());
    	applyPowerToSelf(new BeserkingDebuffB());
    	applyPowerToSelf(new BeserkingDebuffC());
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Berserking();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeBaseCost(0);
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
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
   
}
