package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.orbs.Earth;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class SangaEarth extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("SangaEarth");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SANGA_EARTH);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 2;
    private static final int DAMAGE = 15;
    //private static final int UPGRADE_PLUS_DMG = 3;
    // /STAT DECLARATION/

    public SangaEarth() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = DAMAGE;
    	this.tags.add(Tags.MONSTER);
    	this.tags.add(Tags.GUARDIAN);
    	this.tags.add(Tags.METAL_RAIDERS);
    	this.tags.add(Tags.GOOD_TRIB);
    	this.tags.add(Tags.SPELLCASTER);
    	this.misc = 0;
		this.originalName = this.name;
		this.isSummon = true;
		this.summons = this.baseSummons = 1;
		this.tributes = this.baseTributes = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(p, this.tributes, false, this);
    	summon(p, this.summons, this);
    	attack(m, AFX, this.damage);
    	AbstractOrb orb = new Earth();
    	channel(orb);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SangaEarth();
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
    

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
	}


	@Override
	public void onResummon(int summons) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		
	}

	@Override
	public String getID() {
		return ID;
	}
	
	

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
   
}
