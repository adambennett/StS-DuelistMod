package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.common.MillAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class GustoCaam extends DuelistCard {
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GustoCaam");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoCaam.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public GustoCaam() {
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
        this.upgradeMagicNumber(2);
        this.upgradeDamage(6);
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.upgraded = true;
        this.initializeDescription();
    }

    //Mill some cards, if a Beast was milled among them, draw a card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Util.log("Caam action starting");
        tribute();
        attack(m);
        long beastsInDiscardPreMill = getBeastsInDiscardPile();
        addToBot(new MillAction(p, p, this.magicNumber));
        if (getBeastsInDiscardPile() > beastsInDiscardPreMill && upgraded)
        {
            p.draw();
        }
    }

    private long getBeastsInDiscardPile()
    {
        return player().discardPile.group.stream()
                .filter(c -> c.hasTag(Tags.BEAST))
                .count();
    }
}
