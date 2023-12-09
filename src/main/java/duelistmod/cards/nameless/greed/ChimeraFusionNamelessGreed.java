package duelistmod.cards.nameless.greed;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.interfaces.NamelessTombCard;
import duelistmod.cards.pools.beast.ChimeraFusion;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class ChimeraFusionNamelessGreed extends DuelistCard implements NamelessTombCard {
    public static final String ID = DuelistMod.makeID("Nameless:Greed:ChimeraFusion");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ChimeraFusion.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final int COST = 2;

    public ChimeraFusionNamelessGreed() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 2;
        this.baseTributes = this.tributes = 2;
        this.baseSecondMagic = this.secondMagic = DuelistMod.namelessTombGoldMod;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        if (targets.size() > 0) {
            int counter = 0;
            ArrayList<AbstractCard> beasts = findAllOfTypeForResummonWithDuplicates(Tags.BEAST, this.magicNumber);
            while (!beasts.isEmpty() && counter < this.magicNumber) {
                AbstractCard beast = beasts.size() == 1 ? beasts.remove(0) : beasts.remove(AbstractDungeon.cardRandomRng.random(0, beasts.size() - 1));
                if (duelist.player()) {
                    resummon(beast, (AbstractMonster) targets.get(0));
                } else if (duelist.getEnemy() != null) {
                    anyDuelistResummon(beast, duelist, AbstractDungeon.player);
                }
                counter++;
            }
        }
        if (duelist.player()) {
            duelist.getPlayer().gainGold(this.secondMagic);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public DuelistCard getStandardVersion() {
        return new ChimeraFusion();
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChimeraFusionNamelessGreed();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
