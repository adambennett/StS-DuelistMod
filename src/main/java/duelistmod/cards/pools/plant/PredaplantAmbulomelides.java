package duelistmod.cards.pools.plant;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class PredaplantAmbulomelides extends DuelistCard implements RevengeCard {
    public static final String ID = DuelistMod.makeID("PredaplantAmbulomelides");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("PredaplantAmbulomelides.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public PredaplantAmbulomelides() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.PREDAPLANT);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.PLANT);
        this.tags.add(Tags.GOOD_TRIB);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.baseSummons = this.summons = 2;
        this.baseTributes = this.tributes = 4;
        this.baseBlock = this.block = 15;
        this.baseMagicNumber = this.magicNumber = 2;    // Random enemies to lose strength
        this.baseSecondMagic = this.secondMagic = 2;    // Strength loss
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return RevengeCard.super.isRevengeActive(card) && this.magicNumber > 0 && this.secondMagic > 0;
    }

    @Override
    public void triggerRevenge(AnyDuelist duelist) {
        if (duelist.player()) {
            List<AbstractMonster> validTargets = new ArrayList<>();
            for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                if (mon != null && !mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead) {
                    validTargets.add(mon);
                }
            }
            while (validTargets.size() > this.magicNumber) {
                validTargets.remove(AbstractDungeon.cardRandomRng.random(validTargets.size() - 1));
            }
            for (AbstractMonster mon : validTargets) {
                duelist.applyPower(mon, duelist.creature(), new StrengthPower(mon, -this.secondMagic));
            }
        } else if (duelist.getEnemy() != null) {
            duelist.applyPower(AbstractDungeon.player, duelist.creature(), new StrengthPower(AbstractDungeon.player, -this.secondMagic));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        summon();
        block();
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new PredaplantAmbulomelides();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
