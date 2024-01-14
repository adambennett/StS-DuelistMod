package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class PlagueToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("PlagueToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("PlagueToken.png");
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

    public PlagueToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.tags.add(Tags.BAD_MAGIC);
    	this.purgeOnUse = true;
    	this.baseSummons = this.summons = 1;
    	this.magicNumber = this.baseMagicNumber = 3;
    }
    public PlagueToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.BAD_MAGIC);
    	this.purgeOnUse = true;
    	this.baseSummons = this.summons = 1;
    	this.magicNumber = this.baseMagicNumber = 3;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    }
    @Override public AbstractCard makeCopy() { return new PlagueToken(); }

    @Override
    public void customOnTribute(DuelistCard tc)
    {
		super.customOnTribute(tc);
    	if ((tc == null || !tc.hasTag(Tags.ZOMBIE)) && this.magicNumber > 0)
    	{
    		AbstractDungeon.player.decreaseMaxHealth(this.magicNumber);
    		if (Settings.FAST_MODE) 
    		{
    			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.1F));
    		} 
    		else 
    		{
    			AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.1F));
    		}
    	}
    }
    


	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			this.upgradeMagicNumber(-2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
	


}
