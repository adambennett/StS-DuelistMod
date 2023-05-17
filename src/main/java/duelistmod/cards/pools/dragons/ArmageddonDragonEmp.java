package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class ArmageddonDragonEmp extends DuelistCard {
    public static final String ID = DuelistMod.makeID("ArmageddonDragonEmp");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ArmageddonDragonEmp.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    private static final int baseTrib = 16;

    public ArmageddonDragonEmp() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 85;
        this.tributes = this.baseTributes = baseTrib;
        this.baseMagicNumber = this.magicNumber = 1;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GIANT);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.EXEMPT);
        this.enemyIntent = AbstractMonster.Intent.ATTACK;
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
        if (this.tributes == 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, baseTrib - this.tributes, true));
            this.rawDescription = this.originalDescription;
            this.initializeDescription();
        }
        else if (this.tributes != baseTrib)
        {
            AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, baseTrib - this.tributes, true));
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
    	if (c.hasTag(Tags.DRAGON) && this.tributes > 0) {
    		AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(this, -this.magicNumber, true));
    	}
    }
    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
    	if (!upgraded) {
    		if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
    		else { this.upgradeName(NAME + "+"); }
    		this.upgradeDamage(15);
    		this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
    		this.initializeDescription();
    	}
    }

	@Override
    public AbstractCard makeCopy() {
        return new ArmageddonDragonEmp();
    }
}
