package duelistmod.cards.nameless.magic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.NamelessTombCard;
import duelistmod.cards.AxeDespair;
import duelistmod.cards.other.tokens.Token;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class AxeDespairNameless extends NamelessTombCard
{
	/* 	
	 * Gain X strength this turn. 
	 * the end of the turn, Tribute X and 
	 * place this card on top of your draw pile. 
	 */
    // TEXT DECLARATION 
    public static final String ID = duelistmod.DuelistMod.makeID("Nameless:Magic:AxeDespair");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.AXE_DESPAIR);
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
    private static final int STR_GAIN = 8;
    // /STAT DECLARATION/


    public AxeDespairNameless() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = STR_GAIN + DuelistMod.namelessTombMagicMod;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.MAGIC_RULER);
        this.originalName = this.name;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	applyPowerToSelf(new StrengthPower(p, this.magicNumber));
    	if (!p.hasPower(GravityAxePower.POWER_ID)) { applyPowerToSelf(new DespairPower(p, p, this, this.magicNumber, 1)); }
    }

    @Override
    public DuelistCard getStandardVersion() { return new AxeDespair(); }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new AxeDespairNameless();
    }
    
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();            
            if (DuelistMod.hasUpgradeBuffRelic) { this.upgradeBaseCost(0); this.upgradeMagicNumber(2); }
            else { this.upgradeMagicNumber(2); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }


	















}
