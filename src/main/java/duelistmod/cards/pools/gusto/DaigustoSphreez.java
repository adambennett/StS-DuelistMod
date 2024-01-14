package duelistmod.cards.pools.gusto;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class DaigustoSphreez extends DuelistCard {

    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("DaigustoSphreez");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DaigustoSphreez.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public DaigustoSphreez() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.SPELLCASTER);
        this.baseTributes = this.tributes = 4;
        this.baseSummons = this.summons = 1;
        this.baseMagicNumber = this.magicNumber = 3;
        this.baseBlock = this.block = 14;
        this.originalName = this.name;
    }

    @Override
    public void upgrade() {
        this.upgradeMagicNumber(1);
        this.updateCost(-1);
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();

    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        block(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        tribute();
        summon();
        addToBot(new AddTemporaryHPAction(p, p, this.block));
    }

    @Override
    public void onIncrementWhileSummoned(int amount, int newMaxSummons) {
        applyPowerToSelf(new ThornsPower(player(), this.magicNumber));
    }
}
