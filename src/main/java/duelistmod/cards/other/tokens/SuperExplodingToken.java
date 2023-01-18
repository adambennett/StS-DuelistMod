package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.unique.DetonationAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.MachineToken;
import duelistmod.variables.Tags;

public class SuperExplodingToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("SuperExplodingToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("SuperExplodingToken.png");
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

    public SuperExplodingToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.BAD_TRIB); 
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.MACHINE); 
    	this.tags.add(Tags.SUPER_EXPLODING_TOKEN);
    	this.tags.add(Tags.ALLOYED); 
    	this.purgeOnUse = true; 
    	this.summons = this.baseSummons = 1;
    	this.baseMagicNumber = this.magicNumber = Util.getExplodingTokenDamageInfo(true).low();
    	this.secondMagic = this.baseSecondMagic = Util.getExplodingTokenDamageInfo(true).high();
    	this.isSummon = true;
    }
    
    public SuperExplodingToken(String tokenName) 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.BAD_TRIB); 
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.MACHINE); 
    	this.tags.add(Tags.SUPER_EXPLODING_TOKEN);
    	this.tags.add(Tags.ALLOYED); 
    	this.purgeOnUse = true; 
    	this.summons = this.baseSummons = 1;
    	this.baseMagicNumber = this.magicNumber = Util.getExplodingTokenDamageInfo(true).low();
    	this.secondMagic = this.baseSecondMagic = Util.getExplodingTokenDamageInfo(true).high();
    	this.isSummon = true;
    }
    
    @Override
    public void update()
    {
    	super.update();
		int low = Util.getExplodingTokenDamageInfo(true).low();
		int high = Util.getExplodingTokenDamageInfo(true).high();
    	if (this.baseMagicNumber != low) { this.baseMagicNumber = this.magicNumber = low; }
    	if (this.baseSecondMagic != high) { this.secondMagic = this.baseSecondMagic = high; }
    }
    
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(); 
    }
    @Override public AbstractCard makeCopy() { return new SuperExplodingToken(); }
    
    @Override
    public void customOnTribute(DuelistCard tc)
    {
    	detonate(
		true,																			// True because Super Exploding
		!tc.hasTag(Tags.DETONATE_DMG_SELF_DISABLED),									// Tag cards with this tag to prevent self damage from these tokens
		tc.hasTag(Tags.DETONATE_DMG_ALL_ENEMIES),										// Tag cards with this tag to damage ALL enemies instead of one random enemy each explosion
		tc.hasTag(Tags.DETONATE_DMG_ENEMIES_ALLOWED),									// Tag cards with this if you wish tokens tributed by it to deal damage to enemies (must use to enable all enemy dmg)
		tc.hasTag(Tags.DETONATE_RANDOM_NUMBER_OF_EXPLOSIONS),							// Tag cards with this to get a random number of explosions per Token
		(!tc.hasTag(Tags.DETONATE_DMG_SPECIFIC_TARGET) || tc.detonationTarget == null), // Cards must be tagged with this tag and given a detonation target beforce tributing any tokens to enable directed damage
		tc.detonationTarget,															// here we pass in that target, usually just null
		tc.detonations,																	// number of detonations per token, defaults to 1 without setting anything on the card
		tc.detonationsExtraRandomLow,													// if random detonations is enabled, adds a random number of extra detonations between these two arguments to the previous argument
		tc.detonationsExtraRandomHigh													// the final sum is the number of detonations per token, if these are 0 you will just get the number of detonations passed in even with the random flag enabled
		);
    }

	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			this.upgradeSummons(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
	

}
