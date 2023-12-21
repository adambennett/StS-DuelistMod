package duelistmod.cards.pools.beast;

import com.badlogic.gdx.graphics.Color;
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
import duelistmod.variables.Tags;

import java.util.List;

public class BigWingedBerfomet extends DuelistCard {
    public static final String ID = DuelistMod.makeID("BigWingedBerfomet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BigWingedBerfomet.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public BigWingedBerfomet() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseMagicNumber = this.magicNumber = 2;
        this.baseDamage = this.damage = 4;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
    	this.misc = 0;
    	this.originalName = this.name;
        this.isMultiDamage = true;
    	this.tributes = this.baseTributes = 2;
    	this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.player()) {
            attackAllEnemies();
        } else if (duelist.getEnemy() != null) {
            attack(targets.get(0), this.baseAFX, this.damage);
        }
        if (this.magicNumber > 0 && duelist.hand().stream().anyMatch(c -> c.hasTag(Tags.APEX))) {
            duelist.draw(this.magicNumber);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (AnyDuelist.from(this).hand().stream().anyMatch(c -> c.hasTag(Tags.APEX))) {
            this.glowColor = Color.GOLD;
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new BigWingedBerfomet();
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
