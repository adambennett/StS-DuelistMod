package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.*;
import duelistmod.patches.*;
import duelistmod.variables.*;

public class Hinotama extends DuelistCard
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Hinotama");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.HINOTOMA);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final AttackEffect AFX = AttackEffect.FIRE;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public Hinotama() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 4;
		this.originalName = this.name;
		this.tags.add(Tags.LEGEND_BLUE_EYES);
		this.tags.add(Tags.SPELL);
		this.tags.add(Tags.GENERATION_DECK);
		this.tags.add(Tags.ARCANE);
        this.tags.add(Tags.EXODIA_DECK);
        this.tags.add(Tags.EXODIA_DECK_UPGRADE);
        this.exodiaDeckCopies = 2;  
		this.generationDeckCopies = 1;
		this.magicNumber = this.baseMagicNumber = 3;
		this.secondMagic = this.baseSecondMagic = 2;
		this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int randomTime = Math.min(this.magicNumber, this.secondMagic);
    	try { randomTime = AbstractDungeon.cardRandomRng.random(this.secondMagic, this.magicNumber); } catch (Exception ignored) { }
		for (int i = 0; i < randomTime; i++) {
			if (!(m.isDead || m.isDying || m.isDeadOrEscaped())) {
				attack(m, AFX, this.damage);
			}
		}

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Hinotama();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade()) 
        {
        	// Name
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	if (this.timesUpgraded == 1) { this.upgradeMagicNumber(1); }
        	else if (this.timesUpgraded%2 == 1) { this.upgradeMagicNumber(1); }
        	else { this.upgradeDamage(1); }
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber < 6)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
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
