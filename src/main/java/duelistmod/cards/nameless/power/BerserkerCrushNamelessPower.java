package duelistmod.cards.nameless.power;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.interfaces.NamelessTombCard;
import duelistmod.cards.incomplete.BerserkerCrush;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class BerserkerCrushNamelessPower extends DuelistCard implements NamelessTombCard
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Nameless:Power:BerserkerCrush");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BerserkerCrush.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public BerserkerCrushNamelessPower() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 2;
        this.damage = this.baseDamage = 11 + DuelistMod.namelessTombPowerMod;
        this.tags.add(Tags.SPELL);
		this.tags.add(Tags.ARCANE);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	attack(m);
    	applyPower(new StrengthPower(m, -this.magicNumber), m);
    }

    @Override
    public DuelistCard getStandardVersion() { return new BerserkerCrush(); }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BerserkerCrushNamelessPower();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade())
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	if (this.timesUpgraded%2==0) { this.upgradeMagicNumber(1); }
        	this.upgradeDamage(3);
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber < 4) { return true; }
    	else { return false; }
    }



	









}
