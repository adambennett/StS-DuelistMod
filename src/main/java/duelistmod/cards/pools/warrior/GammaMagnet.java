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
import duelistmod.powers.warrior.GammaMagnetPower;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.List;

public class GammaMagnet extends MagnetCard {

    public static final String ID = DuelistMod.makeID("GammaMagnet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GAMMA_MAGNET);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public GammaMagnet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.MAGNET);
        this.tags.add(Tags.WARRIOR);
        this.tags.add(Tags.ROCK);
        this.originalName = this.name;
        this.isSummon = true;
        this.baseMagicNumber = this.magicNumber = 1; // monsters to draw
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }

    public GammaMagnet(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1).type == CardType.POWER) {
            this.glowColor = Color.GOLD;
        }
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
        duelist.drawTag(this.magicNumber, Tags.MONSTER);
        this.addToBot(new MagnetEnergyGainAction(owner, CardType.POWER));
        if (!owner.hasPower(GammaMagnetPower.POWER_ID)) {
            duelist.applyPowerToSelf(new GammaMagnetPower(owner, owner), owner);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new GammaMagnet();
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
            transformIntoElectro(new GammaElectro());
        }
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }
}
