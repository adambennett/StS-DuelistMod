package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.GuardedDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class CommanderSwords extends GuardedDuelistCard {

    public static final String ID = DuelistMod.makeID("CommanderSwords");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CommanderSwords.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 0;
    private static final int energyNow = 2;
    private static final int energyNext = 1;


    public CommanderSwords() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
        this.exhaust = true;
        this.tributes = this.baseTributes = 2;
        this.setBaseGuardedCheck(20);
        this.setGuardedCheck(20);
        this.originalName = this.name;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.gainEnergy(energyNow);
        if (isGuardedActive(this, this.getGuardedCheck())) {
            triggerGuarded(duelist, targets);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onGuardedTriggered(AnyDuelist duelist, List<AbstractCreature> targets) {
        duelist.applyPowerToSelf(new EnergizedBluePower(duelist.creature(), energyNext));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CommanderSwords();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeGuardedCheck(-5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
