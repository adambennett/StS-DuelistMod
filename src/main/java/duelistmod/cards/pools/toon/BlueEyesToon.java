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
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;
import java.util.List;

public class BlueEyesToon extends DuelistCard {

    public static final String ID = duelistmod.DuelistMod.makeID("BlueEyesToon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.BLUE_EYES_TOON);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    private boolean isReduced = false;

    public BlueEyesToon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 20;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.REQUIRES_TOON_WORLD);
        this.tags.add(Tags.TOON);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.GOOD_TRIB);
        this.tags.add(Tags.FULL);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 2;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        if (targets.size() > 0) {
            attack(targets.get(0));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void update() {
        super.update();
        AnyDuelist duelist = AnyDuelist.from(this);
        boolean anyToonsOrDragons = duelist.hand().stream().anyMatch(c -> c.hasTag(Tags.TOON) || c.hasTag(Tags.DRAGON));
        if (anyToonsOrDragons && !this.isReduced) {
            this.isReduced = true;
            this.costForTurn -= this.magicNumber;
            if (this.costForTurn <= 0) this.costForTurn = 0;
            if (this.costForTurn != this.cost) this.isCostModifiedForTurn = true;
        } else if (!anyToonsOrDragons && this.isReduced) {
            this.isReduced = false;
            this.costForTurn += this.magicNumber;
            if (this.costForTurn == this.cost) this.isCostModifiedForTurn = false;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlueEyesToon();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (DuelistMod.hasUpgradeBuffRelic) { this.upgradeBaseCost(0); }
            this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
