package duelistmod.cards.pools.toon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.PotOfGreed;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

public class JarRobber extends DuelistCard {
    public static final String ID = DuelistMod.makeID("JarRobber");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("JarRobber.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public JarRobber() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseMagicNumber = this.magicNumber = 1;
    	this.tags.add(Tags.SPELL);
    	this.misc = 0;
    	this.originalName = this.name;
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
        boolean hasPotOfGreed = duelist.hand().stream().anyMatch(c -> c instanceof PotOfGreed);
        if (hasPotOfGreed) {
            int energy = this.upgraded ? 3 : 2;
            duelist.gainEnergy(energy);
        } else {
            duelist.draw(this.magicNumber);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        AnyDuelist duelist = AnyDuelist.from(this);
        boolean hasPotOfGreed = duelist.hand().stream().anyMatch(c -> c instanceof PotOfGreed);
        if (hasPotOfGreed) {
            this.glowColor = Color.GOLD;
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new JarRobber();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
