package duelistmod.cards.pools.beast;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
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
import duelistmod.powers.duelistPowers.FangsPower;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class ChowChowChan extends DuelistCard {
    public static final String ID = DuelistMod.makeID("ChowChowChan");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ChowChowChan.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;

    public ChowChowChan() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseMagicNumber = this.magicNumber = 3;
        this.baseSecondMagic = this.secondMagic = 6;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.summons = this.baseSummons = 1;
        this.exhaust = true;
    	this.setupStartingCopies();
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
        if (this.secondMagic > 0) {
            duelist.applyPowerToSelf(new FangsPower(duelist.creature(), duelist.creature(), this.secondMagic));
        }

        int beasts = 0;
        List<AbstractCard> toExhaust = new ArrayList<>();
        for (AbstractCard c : duelist.hand()) {
            if (!c.uuid.equals(this.uuid) && c.hasTag(Tags.BEAST)) {
                beasts++;
                toExhaust.add(c);
            }
        }
        for (AbstractCard c : toExhaust) {
            this.addToBot(new ExhaustSpecificCardAction(c, duelist.handGroup()));
        }
        if (beasts > 0) {
            duelist.gainEnergy(beasts * 2);
        }
        duelist.drawTag(this.magicNumber, Tags.BEAST, true);
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new ChowChowChan();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeSummons(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
