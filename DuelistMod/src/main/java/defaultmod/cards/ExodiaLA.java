package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.ExodiaPower;

public class ExodiaLA extends DuelistCard 
{

    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("ExodiaLA");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.EXODIA_LEFT_ARM);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final AttackEffect AFX = AttackEffect.BLUNT_HEAVY;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ExodiaLA() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.EXODIA);
        this.damage = this.baseDamage = 9;
        this.summons = this.magicNumber = this.baseMagicNumber = 1;
        this.block = this.baseBlock = 1;
        this.exhaust = true;
        this.exodiaName = "Left Arm";
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
    	summon(p, this.summons);
    	attack(m, AFX, this.damage);
    	
    	// If player has already played at least 1 other piece of exodia
    	if (p.hasPower(ExodiaPower.POWER_ID))
    	{
    		// If power has not already triggered once or this is not the first piece played in second set
    		if (p.getPower(ExodiaPower.POWER_ID).amount > 0)
    		{
    			ExodiaPower power = (ExodiaPower) p.getPower(ExodiaPower.POWER_ID);
    			power.addNewPiece(this);
    		}
    		
    		// If power has already triggered and player has the power but it's 0
    		// Just reroll the power
    		else
    		{
    			applyPowerToSelf(new ExodiaPower(p, p, this));
    		}
    	}
    	
    	// If player doesn't yet have any pieces assembled
    	else { applyPowerToSelf(new ExodiaPower(p, p, this)); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ExodiaLA();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            //this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}