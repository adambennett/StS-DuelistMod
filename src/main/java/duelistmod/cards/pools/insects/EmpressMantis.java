package duelistmod.cards.pools.insects;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.variables.*;

public class EmpressMantis extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("EmpressMantis");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.EMPRESS_MANTIS);
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
    private static final int COST = 1;
    // /STAT DECLARATION/

    public EmpressMantis() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.LABYRINTH_NIGHTMARE);
        this.tags.add(Tags.GOOD_TRIB);
        this.tags.add(Tags.INSECT_DECK);
        this.tags.add(Tags.INSECT);
        this.insectDeckCopies = 1;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 2;
        this.damage = this.baseDamage = 10;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(p, this.tributes, false, this);
    	attack(m, AFX, this.damage);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new EmpressMantis();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    

    













}
