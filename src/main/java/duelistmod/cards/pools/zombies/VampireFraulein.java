package duelistmod.cards.pools.zombies;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCardWithAltVersions;
import duelistmod.dto.AnyDuelist;
import duelistmod.enums.StartingDeck;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class VampireFraulein extends DuelistCardWithAltVersions {

    public static final String ID = DuelistMod.makeID("VampireFraulein");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("VampireFraulein.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public VampireFraulein() {
        this(null, null);
    }

    public VampireFraulein(StartingDeck deck, String generalKey) {
        super(deck, generalKey, ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ZOMBIE);
        this.tags.add(Tags.VAMPIRE);
        this.tags.add(Tags.PHARAOH_TWO_DECK);
        this.originalName = this.name;
        this.p2DeckCopies = 1;
        this.baseBlock = this.block = deck == StartingDeck.PHARAOH_II ? 8 : 9;
        this.baseMagicNumber = this.magicNumber = deck == StartingDeck.PHARAOH_II ? 1 : 2;
        this.summons = this.baseSummons = deck == StartingDeck.PHARAOH_II ? 1 : 2;
        this.misc = 0;
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
        block();
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.discard(this.magicNumber, !duelist.player());
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
        	if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
        	this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

	@Override
    public AbstractCard makeCopy() {
        return new VampireFraulein(this.getDeckVersionKey(), this.getGeneralVersionKey());
    }

    @Override
    public VampireFraulein getSpecialVersion(StartingDeck deck, String key) {
        return new VampireFraulein(deck, key);
    }
}
