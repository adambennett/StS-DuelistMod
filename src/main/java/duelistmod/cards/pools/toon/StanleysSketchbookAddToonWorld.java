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

public class StanleysSketchbookAddToonWorld extends DuelistCard {

    public static final String ID = DuelistMod.makeID("StanleysSketchbookAddToonWorld");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.TOON_WORLD);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;
    private final boolean isUpgraded;

    public StanleysSketchbookAddToonWorld(boolean upgraded) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.NEVER_GENERATE);
        this.misc = 0;
        this.originalName = this.name;
        this.isUpgraded = upgraded;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        ToonWorld toonWorld = new ToonWorld();
        if (!this.isUpgraded) {
            toonWorld.isEthereal = true;
            toonWorld.rawDescription = "Ethereal NL " + toonWorld.rawDescription;
            toonWorld.fixUpgradeDesc();
            toonWorld.initializeDescription();
        }
        duelist.addCardToHand(toonWorld);
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new StanleysSketchbookAddToonWorld(this.isUpgraded);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = "Upgraded " + this.name;
            this.initializeTitle();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
