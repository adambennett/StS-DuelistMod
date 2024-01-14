package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class GustoGriffin extends DuelistCard {

    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("GustoGriffin");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoGriffin.png");
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

    public GustoGriffin() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.BEAST);
        this.baseSummons = this.summons = 1;
        this.baseMagicNumber = this.magicNumber = 2;
        this.baseDamage = this.damage = 3;
        this.originalName = this.name;
    }

    @Override
    public void upgrade() {
        if (upgraded) return;
        this.upgradeName();
        this.upgradeDamage(1);
        this.upgradeMagicNumber(1);
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
        this.upgraded = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        summon();
        for(int i = 0; i < this.magicNumber; i++)
        {
            attack(m);
        }
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        block(1);

        if(!tc.hasTag(Tags.SPELLCASTER)) return;
        addToBot(new ApplyPowerAction(player(), player(), new StrengthPower(player(), 1)));
    }
}
