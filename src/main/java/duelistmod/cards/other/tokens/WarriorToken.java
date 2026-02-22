package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.TokenCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class WarriorToken extends TokenCard {

    public static final String ID = DuelistMod.makeID("WarriorToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("WarriorToken.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;

    public WarriorToken() {
        this(NAME);
    }

    public WarriorToken(String tokenName) {
        super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TOKEN);
        this.tags.add(Tags.WARRIOR);
        this.purgeOnUse = true;
        this.baseSummons = this.summons = 1;
        this.baseMagicNumber = this.magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        summon();
        if (roulette()) {
            applyPowerToSelf(new VigorPower(p, this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WarriorToken();
    }

    @Override
    public void upgrade() {
        if (canUpgrade()) {
            if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
            this.upgradeSummons(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
