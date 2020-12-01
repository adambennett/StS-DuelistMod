package duelistmod.cards.pools.dragons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.BurningDebuff;
import duelistmod.variables.Tags;

public class ArtifactIgnition extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ArtifactIgnition");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ArtifactIgnition.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public ArtifactIgnition() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber 	= 9; 
        this.baseSecondMagic = this.secondMagic 	= 2;  
        this.tags.add(Tags.SPELL);       
        this.tags.add(Tags.IS_OVERFLOW);
        this.tags.add(Tags.NEVER_GENERATE);
        this.misc = 0;
        this.originalName = this.name;
    }
    
    @Override
    public void triggerOverflowEffect()
    {
    	super.triggerOverflowEffect();
    	applyPowerToSelf(new BurningDebuff(AbstractDungeon.player, AbstractDungeon.player, this.secondMagic));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (p.hasPower(ArtifactPower.POWER_ID))
    	{
    		burnAllEnemies(this.magicNumber);
    	}
    }
    
    @Override
    public void triggerOnGlowCheck() 
    {
    	super.triggerOnGlowCheck();
    	if (player().hasPower(ArtifactPower.POWER_ID)) {
            this.glowColor = Color.GOLD;
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ArtifactIgnition();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeSecondMagic(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription(); 
        }
    }
    


	@Override
	public void onTribute(DuelistCard tributingCard)
	{
		
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
