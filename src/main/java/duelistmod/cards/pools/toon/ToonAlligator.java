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
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.TemporaryToonWorldPower;
import duelistmod.powers.ToonKingdomPower;
import duelistmod.powers.ToonWorldPower;
import duelistmod.variables.Tags;
import java.util.List;

public class ToonAlligator extends DuelistCard implements RevengeCard {

    public static final String ID = DuelistMod.makeID("ToonAlligator");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ToonAlligator.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public ToonAlligator() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 5;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.REPTILE);
        this.tags.add(Tags.TOON_WITHOUT_KEYWORD);
        this.tags.add(Tags.BAD_MAGIC);
        this.tags.add(Tags.TOON_DECK);
        this.toonDeckCopies = 2;
        this.misc = 0;
        this.originalName = this.name;
        this.summons = this.baseSummons = 1;
        this.setupStartingCopies();
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        AnyDuelist duelist = AnyDuelist.from(this);
        return RevengeCard.super.isRevengeActive(card) && !duelist.hasPower(ToonWorldPower.POWER_ID) && !duelist.hasPower(ToonKingdomPower.POWER_ID);
    }

    @Override
    public void triggerRevenge(AnyDuelist duelist) {
        if (!duelist.hasPower(ToonWorldPower.POWER_ID) && !duelist.hasPower(ToonKingdomPower.POWER_ID)) {
            duelist.applyPowerToSelf(new TemporaryToonWorldPower(duelist.creature(), duelist.creature(), 1));
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
        block();
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ToonAlligator();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
