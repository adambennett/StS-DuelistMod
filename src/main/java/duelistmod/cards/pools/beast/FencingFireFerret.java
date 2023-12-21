package duelistmod.cards.pools.beast;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistCardLibrary;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.FerretToken;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.FireOrb;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.FangsPower;
import duelistmod.variables.Tags;

import java.util.List;

public class FencingFireFerret extends DuelistCard {
    public static final String ID = DuelistMod.makeID("FencingFireFerret");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("FencingFireFerret.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public FencingFireFerret() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.FERAL);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.summons = this.baseSummons = 1;
        this.baseMagicNumber = this.magicNumber = 1;
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.cardsToPreview = new FerretToken();
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
        duelist.channel(new FireOrb(), this.magicNumber);
        if (duelist.hasPower(FangsPower.POWER_ID)) {
            int amt = this.magicNumber * duelist.getPower(FangsPower.POWER_ID).amount;
            for (int i = 0; i < amt; i++) {
                addCardToHand(new FerretToken().makeCopy());
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new FencingFireFerret();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSummons(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
