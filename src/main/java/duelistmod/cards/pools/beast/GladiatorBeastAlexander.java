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
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class GladiatorBeastAlexander extends DuelistCard {
    public static final String ID = DuelistMod.makeID("GladiatorBeastAlexander");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GladiatorBeastAlexander.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public GladiatorBeastAlexander() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 14;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.GLADIATOR);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.tributes = this.baseTributes = 1;
        this.magicNumber = this.baseMagicNumber = 4;
    	this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        tribute();
        if (targets.size() > 0) {
            attack(targets.get(0), this.baseAFX, this.damage);
        }
        AnyDuelist.from(this).endure(this);
    }

    @Override
    public void onEndure() {
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.applyPowerToSelf(new StrengthPower(duelist.creature(), this.magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (AnyDuelist.from(this).hasPower(StrengthPower.POWER_ID)) {
            this.glowColor = Color.GOLD;
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new GladiatorBeastAlexander();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
