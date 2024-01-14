package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class DustStormOfGusto extends DuelistCard {
    public static final String ID = DuelistMod.makeID("DustStormOfGusto");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DustStormOfGusto.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 4;

    public DustStormOfGusto() {
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
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.fixUpgradeDesc();
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.hand().forEach(card -> {
            if (card == this) return;
            duelist.handGroup().removeCard(card);
            addToBot(new MakeTempCardInDrawPileAction(card, 1, true, false));
        });
        duelist.discardPile().forEach(card -> {
            duelist.discardPileGroup().removeCard(card);
            addToBot(new MakeTempCardInDrawPileAction(card, 1, true, false));
        });
        duelist.drawPileGroup().shuffle();
        if (duelist.player()) {
            applyPowerToSelf(new IntangiblePlayerPower(duelist.creature(), 1));
        } else if (duelist.getEnemy() != null) {
            applyPowerToSelf(new IntangiblePower(duelist.creature(), 1));
        }
        applyPowerToSelf(new ThornsPower(duelist.creature(), this.magicNumber));
        postDuelistUseCard(owner, targets);
    }
}
