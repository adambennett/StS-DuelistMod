package duelistmod.cards.pools.beast;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.EndureCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class GladiatorBeastSamnite extends DuelistCard implements EndureCard {
    public static final String ID = DuelistMod.makeID("GladiatorBeastSamnite");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GladiatorBeastSamnite.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public GladiatorBeastSamnite() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 9;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.GLADIATOR);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.summons = this.baseSummons = 2;
        this.baseMagicNumber = this.magicNumber = 1;
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
        if (targets.size() > 0) {
            attack(targets.get(0), this.baseAFX, this.damage);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onEndure(AnyDuelist duelist) {
        if (this.magicNumber > 0) {
            duelist.drawTag(this.magicNumber, Tags.BEAST, true);
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new GladiatorBeastSamnite();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSummons(1);
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
