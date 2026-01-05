package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.List;

public class SuperheavyScales extends DuelistCard {

    public static final String ID = DuelistMod.makeID("SuperheavyScales");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SUPERHEAVY_SCALES);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    private boolean triggeredThisTurn = false;

    public SuperheavyScales() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 5;
        this.baseMagicNumber = this.magicNumber = 2; // block times
        this.summons = this.baseSummons = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SUPERHEAVY);
        this.exhaust = true;
        this.originalName = this.name;
        this.isSummon = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        AnyDuelist duelist = AnyDuelist.from(this);
        for (int i = 0; i < this.magicNumber; i++) {
            duelist.block(this.block);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onSynergyTributeWhileInExhaust(List<CardTags> allMatchingTypes) {
        if (allMatchingTypes == null || allMatchingTypes.isEmpty() || this.triggeredThisTurn) return;

        if (allMatchingTypes.stream().anyMatch(t -> t.equals(Tags.WARRIOR))) {
            this.addToBot(new ExhaustToHandAction(this));
            this.triggeredThisTurn = true;
        }
    }

    /*@Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        this.triggeredThisTurn = false;
    }*/

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        if (card instanceof SuperheavyScales) {
            SuperheavyScales dCard = (SuperheavyScales) card;
            dCard.triggeredThisTurn = this.triggeredThisTurn;
            return dCard;
        }
        return card;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SuperheavyScales();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
