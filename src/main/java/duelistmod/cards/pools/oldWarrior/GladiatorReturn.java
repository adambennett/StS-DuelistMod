package duelistmod.cards.pools.oldWarrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.GladiatorReturnAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class GladiatorReturn extends DuelistCard {

    public static final String ID = DuelistMod.makeID("GladiatorReturn");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GladiatorReturn.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;

    public GladiatorReturn() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.SPELL);
        this.baseMagicNumber = this.magicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GladiatorReturnAction(1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            boolean hasWarrior = false;

            for (AbstractCard c : p.discardPile.group) {
                if (c.hasTag(Tags.WARRIOR)) {
                    hasWarrior = true;
                }
            }

            if (!hasWarrior) {
                this.cantUseMessage = "There are no Warriors NL in my discard pile.";
                canUse = false;
            }

            return canUse;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new GladiatorReturn();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
