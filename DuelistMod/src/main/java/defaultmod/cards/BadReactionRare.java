package defaultmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class BadReactionRare extends DuelistCard 
{
    // TEXT DECLARATION 
    public static final String ID = defaultmod.DefaultMod.makeID("BadReactionRare");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.BAD_REACTION);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION 	
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 2;
    private static final int DAMAGE = 1;
    private static final int HEAL = 1;
    private static final int U_HEAL = 1;
    private static final int U_DMG = 1;
    // /STAT DECLARATION/

    public BadReactionRare() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = HEAL;
        this.tags.add(DefaultMod.TRAP);
        this.tags.add(DefaultMod.LIMITED);
        this.tags.add(DefaultMod.ORIGINAL_HEAL_DECK);
        this.startingOPHDeckCopies = 1;
        this.originalName = this.name; 
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (p.hasPower(BadReactionPower.POWER_ID)) { removePower(p.getPower(BadReactionPower.POWER_ID), p); }
    	if (this.upgraded)
    	{
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SpecialBadReactionPower(p, p, true, U_DMG, U_HEAL)));
    	}
    	else
    	{
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SpecialBadReactionPower(p, p, false, 0, 0)));
    	}
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BadReactionRare();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(U_DMG);
            this.upgradeMagicNumber(U_HEAL);
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