package duelistmod.cards.pools.insects;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class MirrorLadybug extends DuelistCard {

    public static final String ID = DuelistMod.makeID("MirrorLadybug");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MirrorLadybug.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;

    public MirrorLadybug() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 12;
        this.isEthereal = true;
        this.tributes = this.baseTributes = 1;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.INSECT);
        this.tags.add(Tags.BUG);
        this.originalName = this.name;
        this.isSummon = true;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("Bug", "The #b2nd #yBug you play each combat gives you #b5 #yTemporary #yHP."));
        return retVal;
    }

    @Override
    public void onSummonWhileInHand(DuelistCard c, int amt) {
        if (c.hasTag(Tags.INSECT) && amt > 0) {
            AbstractMonster rand = AbstractDungeon.getRandomMonster();
            if (rand != null) {
                DuelistMod.mirrorLadybug = true;
                resummon(c.makeStatEquivalentCopy(), rand);
                this.modifyGiantTributes(this.magicNumber);
            }
        }
    }

    @Override
    public void onSummonWhileInDiscard(DuelistCard c, int amt) {
        if (c.hasTag(Tags.INSECT) && amt > 0) {
            AbstractMonster rand = AbstractDungeon.getRandomMonster();
            if (rand != null) {
                DuelistMod.mirrorLadybug = true;
                resummon(c.makeStatEquivalentCopy(), rand);
                this.modifyGiantTributes(this.magicNumber);
            }
        }
    }

    @Override
    public void onSummonWhileInDraw(DuelistCard c, int amt) {
        if (c.hasTag(Tags.INSECT) && amt > 0) {
            AbstractMonster rand = AbstractDungeon.getRandomMonster();
            if (rand != null) {
                DuelistMod.mirrorLadybug = true;
                resummon(c.makeStatEquivalentCopy(), rand);
                this.modifyGiantTributes(this.magicNumber);
            }
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
        block();
        this.resetGiantTributes();
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new MirrorLadybug();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
