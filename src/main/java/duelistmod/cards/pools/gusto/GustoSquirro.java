package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class GustoSquirro extends DuelistCard {

    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("GustoSquirro");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoSquirro.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    // /STAT DECLARATION/

    public GustoSquirro() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 2;
        this.baseDamage = this.damage = 4;
        this.baseSummons = this.summons = 1;
        this.baseMagicNumber = this.magicNumber = 2;
        this.originalName = this.name;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.BEAST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        summon();
        block();
        attack(m);
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        block(1);
        if(!tc.hasTag(Tags.SPELLCASTER) && !upgraded) return;
        addToBot(new ScryAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeDamage(3);
        this.upgradeBlock(2);
        this.upgradeMagicNumber(1);
        this.upgradeName();
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }
}
