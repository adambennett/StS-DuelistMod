package duelistmod.cards.pools.beast;

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
import duelistmod.powers.duelistPowers.TriBrigadeArmsBucephalusPower;
import duelistmod.variables.Tags;

import java.util.List;

public class TriBrigadeArmsBucephalus extends DuelistCard {
    public static final String ID = DuelistMod.makeID("TriBrigadeArmsBucephalus");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("TriBrigadeArmsBucephalus.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public TriBrigadeArmsBucephalus() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 8;
    	this.tags.add(Tags.MONSTER);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.tributes = this.baseTributes = 2;
        this.baseSecondMagic = this.secondMagic = 5;
    	this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        tribute();
        AnyDuelist duelist = AnyDuelist.from(this);
        if (!duelist.hasPower(TriBrigadeArmsBucephalusPower.POWER_ID)) {
            duelist.applyPowerToSelf(new TriBrigadeArmsBucephalusPower(duelist.creature(), duelist.creature(), this.secondMagic));
        } else {
            TriBrigadeArmsBucephalusPower pow = (TriBrigadeArmsBucephalusPower)duelist.getPower(TriBrigadeArmsBucephalusPower.POWER_ID);
            pow.amount2 += this.secondMagic;
            pow.updateDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new TriBrigadeArmsBucephalus();
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
