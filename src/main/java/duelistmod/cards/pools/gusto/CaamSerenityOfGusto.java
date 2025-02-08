package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.MillAndCheckAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CaamSerenityOfGusto extends DuelistCard {
    public static final String ID = DuelistMod.makeID("CaamSerenityOfGusto");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CaamSerenityOfGusto.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;

    public CaamSerenityOfGusto() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 3;
        this.baseDamage = this.damage = 10;
        this.baseTributes = this.tributes = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.SPELLCASTER);
    }

    @Override
    public void upgrade() {
        if (upgraded) return;
        this.upgradeName();
        this.upgradeMagicNumber(1);
        this.upgradeDamage(3);
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.upgraded = true;
        this.fixUpgradeDesc();
        this.initializeDescription();
    }

    //Mill some cards, if a Beast was milled among them, draw a card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        tribute();
        if (targets.size() > 0) {
            attack(targets.get(0), this.baseAFX, this.damage);
        }
        Consumer<ArrayList<AbstractCard>> drawIfMilledBeast = milledCards -> {
            boolean milledBeast = milledCards.stream().anyMatch(card -> card.hasTag(Tags.BEAST) && card.hasTag(Tags.MONSTER));
            if (milledBeast) {
                duelist.draw(1);
            }
        };
        addToBot(new MillAndCheckAction(duelist, this.magicNumber, drawIfMilledBeast));
        postDuelistUseCard(owner, targets);
    }
}
