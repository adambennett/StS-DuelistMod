package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class ExplosiveToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ExplosiveToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.EXPLOSIVE_TOKEN);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public ExplosiveToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.BAD_TRIB); 
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.EXPLODING_TOKEN); 
    	this.tags.add(Tags.ALLOYED); 
    	this.baseMagicNumber = this.magicNumber = DuelistMod.explosiveDmgLow;
    	this.secondMagic = this.baseSecondMagic = DuelistMod.explosiveDmgHigh;
    	this.purgeOnUse = true; 
    	this.summons = this.baseSummons = 1;
    }
    
    public ExplosiveToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.BAD_TRIB); 
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.EXPLODING_TOKEN); 
    	this.tags.add(Tags.ALLOYED); 
    	this.baseMagicNumber = this.magicNumber = DuelistMod.explosiveDmgLow;
    	this.secondMagic = this.baseSecondMagic = DuelistMod.explosiveDmgHigh;
    	this.purgeOnUse = true;
    	this.summons = this.baseSummons = 1;
    }
    
    @Override
    public void update()
    {
    	super.update();
    	if (this.baseMagicNumber != DuelistMod.explosiveDmgLow) { this.baseMagicNumber = this.magicNumber = DuelistMod.explosiveDmgLow; }
    	if (this.baseSecondMagic != DuelistMod.explosiveDmgHigh) { this.secondMagic = this.baseSecondMagic = DuelistMod.explosiveDmgHigh; }
    }
    
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(); 
    }
    @Override public AbstractCard makeCopy() { return new ExplosiveToken(); }
    
    @Override
    public void customOnTribute(DuelistCard tc)
    {
    	detonate(false, !tc.hasTag(Tags.DETONATE_DMG_SELF_DISABLED), tc.hasTag(Tags.DETONATE_DMG_ALL_ENEMIES), tc.hasTag(Tags.DETONATE_DMG_ENEMIES_ALLOWED), tc.hasTag(Tags.DETONATE_RANDOM_NUMBER_OF_EXPLOSIONS), (!tc.hasTag(Tags.DETONATE_DMG_SPECIFIC_TARGET) || tc.detonationTarget == null), tc.detonationTarget, tc.detonations, tc.detonationsExtraRandomLow, tc.detonationsExtraRandomHigh);
    }
    
	@Override public void onTribute(DuelistCard tributingCard) {}
	@Override public void onResummon(int summons) { }
	@Override public void summonThis(int summons, DuelistCard c, int var) { summon(AbstractDungeon.player, 1, this); }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { summon(AbstractDungeon.player, 1, this); }
	
	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			this.upgradeSummons(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
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