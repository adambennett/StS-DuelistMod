package duelistmod.cards.pools.pharaoh;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.VoidOrb;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class VoidApocalypse extends DuelistCard {
    public static final String ID = DuelistMod.makeID("VoidApocalypse");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("VoidApocalypse.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;

    public VoidApocalypse() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 10;
    	this.tags.add(Tags.SPELL);
    	this.misc = 0;
    	this.originalName = this.name;
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = this.magicNumber = 1;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.player()) {
            attackAllEnemies();
        } else if (duelist.getEnemy() != null) {
            attack(targets.get(0), this.baseAFX, this.damage);
        }
        duelist.channel(new VoidOrb(), this.magicNumber);
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new VoidApocalypse();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
