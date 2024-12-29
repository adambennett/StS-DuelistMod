package duelistmod.cards.pools.dragons;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class CyberneticRevolution extends DuelistCard {
    public static final String ID = DuelistMod.makeID("CyberneticRevolution");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CyberneticRevolution.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;

    public CyberneticRevolution() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 1;
        this.showEvokeValue = true;
        this.showInvertValue   = true;
        this.tags.add(Tags.TRAP);
        this.misc = 0;
        this.originalName = this.name;
        this.exhaust = true;
    }

	@Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	invertAll(1);
    	ArrayList<AbstractMonster> mons = getAllMons();
    	for (AbstractMonster mon : mons)
    	{
    		applyPower(new StrengthPower(mon, -this.magicNumber), mon);
    	}
    }

    @Override
    public AbstractCard makeCopy() {
        return new CyberneticRevolution();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
    }

    public String failedCardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) { return "Requires 1+ orbs"; }

    public boolean cardSpecificCanUse(final AbstractCreature owner) {
        boolean hasOneOrb = false;
        if (owner instanceof AbstractPlayer && ((AbstractPlayer)owner).orbs != null) {
            for (AbstractOrb orb : ((AbstractPlayer)owner).orbs) {
                if (!(orb instanceof EmptyOrbSlot)) {
                    hasOneOrb = true;
                    break;
                }
            }
        }
        return hasOneOrb;
    }
}
