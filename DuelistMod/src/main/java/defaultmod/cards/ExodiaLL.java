package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.ExodiaPower;

public class ExodiaLL extends DuelistCard 
{

    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("ExodiaLL");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.EXODIA_LEFT_LEG);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ExodiaLL() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.EXODIA);
        this.tags.add(DefaultMod.SPELLCASTER);
        this.tags.add(DefaultMod.LEGEND_BLUE_EYES);
        this.tags.add(DefaultMod.LIMITED);
        this.tags.add(DefaultMod.BAD_TRIB);
        this.tags.add(DefaultMod.SPELLCASTER_DECK);
        this.startingDeckCopies = 1;
        this.baseBlock = this.block = 5;
        this.summons = this.baseMagicNumber = this.magicNumber = 1;
        this.damage = this.baseDamage = 1;
        this.exhaust = true;
        this.exodiaName = "Left Leg";
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
    	summon(p, this.summons, this);
    	block(this.block);
    	
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
        return new ExodiaLL();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            //this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
		if (tributingCard != null && tributingCard.hasTag(DefaultMod.DRAGON)) { damageSelf(2); }
		
	}

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
    	block(this.block);
    	
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

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
    	block(this.block);
    	
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

	@Override
	public String getID() {
		return ID;
	}
}