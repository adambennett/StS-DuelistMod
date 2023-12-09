package duelistmod.cards.nameless.magic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.interfaces.NamelessTombCard;
import duelistmod.cards.pools.beast.Caninetaur;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class CaninetaurNameless extends DuelistCard implements NamelessTombCard {
    public static final String ID = DuelistMod.makeID("Nameless:Magic:Caninetaur");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Caninetaur.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final int COST = 1;


    public CaninetaurNameless() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 5;
        this.magicNumber = this.baseMagicNumber = 2 + DuelistMod.namelessTombMagicMod;
        this.tags.add(Tags.MONSTER);
        this.misc = 0;
        this.originalName = this.name;
        this.summons = this.baseSummons = 1;
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
        AbstractCreature weakTarget = null;
        if (targets.size() > 0) {
            weakTarget = targets.get(0);
        }
        AbstractCreature weakSource = duelist.creature();
        if (weakTarget != null) {
            duelist.applyPower(weakTarget, weakSource, new WeakPower(weakTarget, this.magicNumber, duelist.getEnemy() != null));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public DuelistCard getStandardVersion() {
        return new Caninetaur();
    }

    @Override
    public void upgrade()  {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
	
	@Override
    public AbstractCard makeCopy() { return new CaninetaurNameless(); }
	
}
