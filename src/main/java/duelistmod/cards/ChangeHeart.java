package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.Summoner;
import duelistmod.patches.*;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

import java.util.List;

public class ChangeHeart extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("ChangeHeart");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.CHANGE_HEART);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ChangeHeart() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.EXODIA_DECK);
        this.tags.add(Tags.METAL_RAIDERS);
        this.exodiaDeckCopies = 1;
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 8;
        this.setupStartingCopies();
        this.enemyIntent = AbstractMonster.Intent.BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        setMaxSummons(AnyDuelist.from(this), this.magicNumber);
        postDuelistUseCard(owner, targets);
    }

    @Override
    public int incrementGeneratedIfPlayed() {
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.hasPower(SummonPower.POWER_ID)) {
            SummonPower pow = (SummonPower) duelist.getPower(SummonPower.POWER_ID);
            if (pow.getMaxSummons() > this.magicNumber || pow.getMaxSummons() < this.magicNumber) {
                return this.magicNumber - pow.getMaxSummons();
            }
        }
        return 0;
    }

    public boolean cardSpecificCanUse(final AbstractCreature owner) {
        AnyDuelist duelist = AnyDuelist.from(owner);
        if (duelist.getEnemy() != null && duelist.getEnemy().hasPower(SummonPower.POWER_ID)) {
            SummonPower pow = (SummonPower)duelist.getEnemy().getPower(SummonPower.POWER_ID);
            return pow.getMaxSummons() < this.magicNumber;
        }
        return true;
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ChangeHeart();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION);
        }
    }


}
