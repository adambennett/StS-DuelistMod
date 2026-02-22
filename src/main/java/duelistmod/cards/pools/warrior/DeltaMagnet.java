package duelistmod.cards.pools.warrior;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.MagnetCard;
import duelistmod.actions.unique.MagnetEnergyGainAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.warrior.DeltaMagnetPower;
import duelistmod.variables.Tags;

import java.util.List;

public class DeltaMagnet extends MagnetCard {

    public static final String ID = DuelistMod.makeID("DeltaMagnet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DeltaMagnet.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public DeltaMagnet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons = 1;
        this.baseBlock = this.block = 3;
        this.baseMagicNumber = this.magicNumber = 3;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WARRIOR);
        this.tags.add(Tags.MAGNET);
        this.tags.add(Tags.ROCK);
        this.originalName = this.name;
        this.isSummon = true;
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }

    public DeltaMagnet(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (isTriggering()) {
            this.glowColor = Color.GOLD;
        }
    }

    private boolean isTriggering() {
        return !AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() &&
                AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1).hasTag(Tags.ROCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        AnyDuelist duelist = AnyDuelist.from(this);
        for (int i = 0; i < magicNumber; i++) {
            duelist.block(this.block);
        }
        this.addToBot(new MagnetEnergyGainAction(owner, Tags.ROCK));
        this.gainMagnetPower(duelist);
        postDuelistUseCard(owner, targets);
    }

    public void gainMagnetPower(AnyDuelist duelist) {
        if (!duelist.hasPower(DeltaMagnetPower.POWER_ID)) {
            duelist.applyPowerToSelf(new DeltaMagnetPower(duelist.creature()), duelist.creature());
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DeltaMagnet();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        } else {
            this.timesUpgraded++;
            transformIntoElectro(new EpsilonMagnet());
        }
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

}

