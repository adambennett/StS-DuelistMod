package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;


public class DarklordMarie extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = duelistmod.DuelistMod.makeID("DarklordMarie");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.DARKLORD_MARIE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int SUMMONS = 1;
    private static final int COST = 1;
    private static final int DAMAGE = 6;

    public DarklordMarie() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.baseDamage = this.damage = DAMAGE;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
        this.tags.add(Tags.LABYRINTH_NIGHTMARE);
        this.tags.add(Tags.HEAL_DECK);
        this.tags.add(Tags.ORIGINAL_HEAL_DECK);
        this.tags.add(Tags.NEVER_GENERATE);
        this.tags.add(Tags.IS_OVERFLOW);
        this.startingOPHDeckCopies = 2;
        this.healDeckCopies = 2;
        this.originalName = this.name;
        this.summons = this.baseSummons = SUMMONS;
        this.secondMagic = this.baseSecondMagic = 3;
        this.setupStartingCopies();
    }
    
    @Override
    public void triggerOverflowEffect()
    {
    	super.triggerOverflowEffect();
    	 heal(player(), this.secondMagic);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
    	summon(p, this.summons, this);
    	attack(m, AFX, this.damage);
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarklordMarie();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }




}
