package duelistmod.cards.metronomes;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.MetronomeCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.ArrayList;

public class RevengeAttackMetronome extends MetronomeCard {

    public static final String ID = DuelistMod.makeID("RevengeAttackMetronome");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MetronomeAttack.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public RevengeAttackMetronome() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.EXEMPT);
        this.tags.add(Tags.NEVER_GENERATE);
        this.tags.add(Tags.METRONOME);
        this.tags.add(Tags.ALLOYED);
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        metronomeAction(m);
    }

    public AbstractCard returnCard() {
        ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<>();
        for (DuelistCard c : DuelistMod.myCards) {
            if (c instanceof RevengeCard && c.type.equals(CardType.ATTACK) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) {
                cardsToPullFrom.add((DuelistCard) c.makeCopy());
            }
        }
        if (cardsToPullFrom.size() > 0) {
            DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
            return c.makeCopy();
        } else {
            return new CancelCard();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RevengeAttackMetronome();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
            else { this.upgradeName(NAME + "+"); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
