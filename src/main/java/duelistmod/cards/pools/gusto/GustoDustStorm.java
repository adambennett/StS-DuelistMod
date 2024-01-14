package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class GustoDustStorm extends DuelistCard {
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("GustoDustStorm");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoDustStorm.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 4;


    // /STAT DECLARATION/

    public GustoDustStorm() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.GUSTO);
        this.baseMagicNumber = this.magicNumber = 10;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (upgraded) return;
        this.upgradeName();
        this.upgradeBaseCost(3);
        this.upgradeMagicNumber(5);
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.hand.group.forEach(card -> {
            if (card == this) return;
            p.hand.removeCard(card);
            addToBot(new MakeTempCardInDrawPileAction(card, 1, true, false));
        });
        p.discardPile.group.forEach(card -> {
            p.discardPile.removeCard(card);
            addToBot(new MakeTempCardInDrawPileAction(card, 1, true, false));
        });
        p.drawPile.shuffle();
        applyPowerToSelf(new IntangiblePower(p, 1));
        applyPowerToSelf(new ThornsPower(p, this.magicNumber));
    }
}
