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
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;
import java.util.List;

public class ToonCyberDragon extends DuelistCard {

    public static final String ID = duelistmod.DuelistMod.makeID("ToonCyberDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ToonCyberDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public ToonCyberDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 6;
        this.summons = this.baseSummons = 3;            // Summons if triggered
        this.baseMagicNumber = this.magicNumber = 1;    // Machine card cost reduce
        this.baseSecondMagic = this.secondMagic = 1;    // Summons if not triggered
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.REQUIRES_TOON_WORLD);
        this.tags.add(Tags.TOON);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.FULL);
		this.originalName = this.name;
        this.isSummon = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        block();
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.hasPower(SummonPower.POWER_ID) && duelist.getPower(SummonPower.POWER_ID).amount > 0) {
            summon();
            // TODO: Your next Machine costs magicNumber less this turn
        } else {
            summon(duelist.creature(), this.secondMagic, this);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ToonCyberDragon();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSecondMagic(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
