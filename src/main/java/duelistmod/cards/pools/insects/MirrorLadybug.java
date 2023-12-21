package duelistmod.cards.pools.insects;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class MirrorLadybug extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MirrorLadybug");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MirrorLadybug.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public MirrorLadybug() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 12;
        this.isEthereal = true;
        this.tributes = this.baseTributes = 1;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.INSECT);
        this.tags.add(Tags.BUG);
        this.originalName = this.name;
        this.isSummon = true;
    }
    
    @Override
    public void onSummonWhileInHand(DuelistCard c, int amt) 
    { 
    	if (c.hasTag(Tags.INSECT) && amt > 0) 
    	{
	    	AbstractMonster rand = AbstractDungeon.getRandomMonster();
	    	if (rand != null) 
	    	{ 
	    		if (c instanceof DuelistCard)
	    		{
	    			DuelistMod.mirrorLadybug = true;	    		
		    		fullResummon((DuelistCard) c.makeStatEquivalentCopy(), false, rand, false);
		    		this.modifyTributes(this.magicNumber);
	    		}
	    	} 	
    	}
    }

    @Override
    public void onSummonWhileInDiscard(DuelistCard c, int amt) 
    {  
    	if (c.hasTag(Tags.INSECT) && amt > 0) 
    	{
	    	AbstractMonster rand = AbstractDungeon.getRandomMonster();
	    	if (rand != null) 
	    	{
	    		if (c instanceof DuelistCard)
	    		{
	    			DuelistMod.mirrorLadybug = true;	
		    		fullResummon((DuelistCard) c.makeStatEquivalentCopy(), false, rand, false);
		    		this.modifyTributes(this.magicNumber);
	    		}	    		
	    	} 	
    	}
    }

    @Override
    public void onSummonWhileInDraw(DuelistCard c, int amt) 
    { 
    	if (c.hasTag(Tags.INSECT) && amt > 0) 
    	{
	    	AbstractMonster rand = AbstractDungeon.getRandomMonster();
	    	if (rand != null) 
	    	{
	    		if (c instanceof DuelistCard)
	    		{
	    			DuelistMod.mirrorLadybug = true;	    		
		    		fullResummon((DuelistCard) c.makeStatEquivalentCopy(), false, rand, false);
		    		this.modifyTributes(this.magicNumber);
	    		}	    		
	    	} 	 	
    	}
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	block();
    	if (this.tributes == 0)
    	{
    		AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, 1, true));
    		this.rawDescription = this.originalDescription;
    		this.initializeDescription();    		
    	}
    	else if (this.tributes != 1)
    	{
    		AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, 1 - this.tributes, true));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new MirrorLadybug();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    













}
