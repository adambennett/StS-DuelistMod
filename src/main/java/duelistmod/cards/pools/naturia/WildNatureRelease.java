package duelistmod.cards.pools.naturia;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.WildNatureActionA;
import duelistmod.actions.unique.WildNatureActionB;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class WildNatureRelease extends DuelistCard {
    public static final String ID = DuelistMod.makeID("WildNatureRelease");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("WildNatureRelease.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;

    public WildNatureRelease() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 3;
        this.damage = this.baseDamage = 1;
        this.misc = 0;
        this.tags.add(Tags.SPELL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	this.addToTop(new WildNatureActionA(this.magicNumber));
    	this.addToBot(new WildNatureActionB());
    }

    @Override
    public void upgrade()  {
        if (!upgraded) {
        	if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
        	this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
	
	@Override
    public AbstractCard makeCopy() {
        return new WildNatureRelease();
    }
}
