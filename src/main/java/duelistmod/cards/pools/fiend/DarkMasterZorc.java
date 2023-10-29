package duelistmod.cards.pools.fiend;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class DarkMasterZorc extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DarkMasterZorc");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkMasterZorc.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public DarkMasterZorc() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tributes = this.baseTributes = 2;
        this.baseMagicNumber = this.magicNumber = 3;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 3;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
        this.tags.add(Tags.APEX);
        this.tags.add(Tags.EXODIA_DECK);
		this.exodiaDeckCopies = 1;
		this.setupStartingCopies();
        this.originalName = this.name;
        this.baseDamage = this.damage = 20;
    }
    
    @Override
    public void update()
    {
		super.update();
    	this.showEvokeOrbCount = this.magicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	attack(m, this.baseAFX, this.damage);
    	tribute(p, this.tributes, false, this);    	
    	channel(new Dark(), this.magicNumber);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkMasterZorc();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
            this.upgradeDamage(5);
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
        }
    }

	
	












}
