package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.function.Predicate;

public class GustoGulldo extends DuelistCard {

    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("GustoGulldo");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoGulldo.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public GustoGulldo() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.BEAST);
        this.baseSummons = this.summons = 1;
        this.baseDamage = this.damage = 5;
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        summon(p, this.summons, this);
        attack(m);
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeName();
        this.upgradeDamage(2);
        this.upgradeSummons(1);
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        block(1);
        Predicate<AbstractCard> gustoSpellcaster = c -> c.hasTag(Tags.GUSTO) && c.hasTag(Tags.SPELLCASTER);
        ArrayList<AbstractCard> result = CardFinderHelper.find(1, TheDuelist.cardPool.group, DuelistMod.myCards, gustoSpellcaster);
        addToBot(new MakeTempCardInDrawPileAction(result.get(0), 1, true, false));
    }
}
