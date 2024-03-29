package duelistmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.actions.common.FetchAndReduceAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class FeatherPho extends DuelistCard
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("FeatherPho");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.FEATHER_PHOENIX);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public FeatherPho() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LIMITED);
        this.magicNumber = this.baseMagicNumber = 1;
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (this.magicNumber < 1) {
            return;
        }
        discardTop(this.magicNumber, false);
        if (!upgraded) { this.addToBot(new FetchAction(p.discardPile, 1)); }
        else { this.addToBot(new FetchAndReduceAction(1, p.discardPile, this.magicNumber, false)); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new FeatherPho();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    public String failedCardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) { return DuelistMod.featherPhoCantUseString + this.magicNumber + DuelistMod.featherPhoCantUseStringB; }

    public boolean cardSpecificCanUse(final AbstractCreature owner) {
        int size = owner instanceof AbstractPlayer ? ((AbstractPlayer)owner).hand.group.size() : owner instanceof AbstractEnemyDuelist ? ((AbstractEnemyDuelist)owner).hand.group.size() : 0;
        return size >= this.magicNumber + 1;
    }
}
