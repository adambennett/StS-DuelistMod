package duelistmod.cards.pools.dragons;

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

public class AncientRules extends DuelistCard {

    public static final String ID = duelistmod.DuelistMod.makeID("AncientRules");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.ANCIENT_RULES);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public AncientRules() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = 1;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LIMITED);
		this.tags.add(Tags.ANCIENT_FOR_PIXIE);
		this.tags.add(Tags.ANCIENT_FOR_MACHINE);
        this.originalName = this.name;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        for (AbstractCard c : duelist.drawPile()) {
            if (c.hasTag(Tags.MONSTER)) {
                DuelistCard dC = (DuelistCard) c;
                if (dC.isTributeCard()) {
                    dC.modifyTributesForCombat(-this.magicNumber);
                }
            }
        }

        for (AbstractCard c : duelist.hand()) {
            if (c.hasTag(Tags.MONSTER)) {
                DuelistCard dC = (DuelistCard) c;
                if (dC.isTributeCard()) {
                    dC.modifyTributesForCombat(-this.magicNumber);
                }
            }
        }

        for (AbstractCard c : duelist.discardPile()) {
            if (c.hasTag(Tags.MONSTER)) {
                DuelistCard dC = (DuelistCard) c;
                if (dC.isTributeCard()) {
                    dC.modifyTributesForCombat(-this.magicNumber);
                }
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new AncientRules();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
        	this.upgradeName();
        	this.upgradeMagicNumber(1);
            if (DuelistMod.hasUpgradeBuffRelic) { this.upgradeBaseCost(0); }
        	this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
        	this.initializeDescription();
        }
    }

}
