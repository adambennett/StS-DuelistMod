package duelistmod.cards.nameless.magic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.interfaces.NamelessTombCard;
import duelistmod.cards.pools.warrior.MagnumShield;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class MagnumShieldNameless extends DuelistCard implements NamelessTombCard
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Nameless:Magic:MagnumShield");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MagnumShield.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public MagnumShieldNameless() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.ARCANE);
        this.baseMagicNumber = this.magicNumber = 2 + DuelistMod.namelessTombMagicMod;
        this.baseBlock = this.block = 8;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		block();
		applyPowerToSelf(new VigorPower(p, this.magicNumber));
    }

    @Override
    public DuelistCard getStandardVersion() { return new MagnumShield(); }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new MagnumShieldNameless();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade())
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(2);
        	this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber < 13) { return true; }
    	else { return false; }
    }

	
	









}
