package duelistmod.cards.pools.toon;

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
import duelistmod.powers.duelistPowers.ShadowToonPower;
import duelistmod.powers.duelistPowers.ThereCanBeOnlyOnePower;
import duelistmod.variables.Tags;
import java.util.List;

public class ThereCanBeOnlyOne extends DuelistCard {

    public static final String ID = DuelistMod.makeID("ThereCanBeOnlyOne");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ThereCanBeOnlyOne.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 2;

    public ThereCanBeOnlyOne() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
		this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.applyPowerToSelf(new ThereCanBeOnlyOnePower(duelist.creature(), duelist.creature(), this.magicNumber));
        // TODO: Implement power
        // TODO: Enforce can't play effect
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ThereCanBeOnlyOne();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
