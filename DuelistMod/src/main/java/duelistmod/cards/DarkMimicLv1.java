package duelistmod.cards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import duelistmod.*;
import duelistmod.actions.common.ModifyMagicNumberAction;
import duelistmod.actions.unique.PurgeSpecificCard;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;

public class DarkMimicLv1 extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DarkMimicLv1");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkMimicLv1.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;
    private ArrayList<AbstractCard> tooltips;
    // /STAT DECLARATION/

    public DarkMimicLv1() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
        this.misc = 0;
        this.originalName = this.name;
        this.isSummon = true;
        this.summons = this.baseSummons = 1;
        this.magicNumber = this.baseMagicNumber = 1;
        tooltips = new ArrayList<>();
        tooltips.add(new DarkMimicLv3());
        
    }
    
    @Override
    public void triggerOnEndOfPlayerTurn() 
    {
    	// If overflows remaining
        if (this.magicNumber > 0) 
        {
        	// Remove 1 overflow
            AbstractDungeon.actionManager.addToTop(new ModifyMagicNumberAction(this, -1));
            
            // Set appropriate global flags for next battle
            if (!DuelistMod.gotMimicLv1) 
            { 
            	DuelistMod.gotMimicLv1 = true; 
            	DuelistMod.mimic1Copies = 1; 
            	DuelistMod.giveUpgradedMimicLv3 = this.upgraded; 
            }
        	else 
        	{ 
        		DuelistMod.mimic1Copies++; 
        		if (!DuelistMod.giveUpgradedMimicLv3) { DuelistMod.giveUpgradedMimicLv3 = this.upgraded; }
        	}
            
            for (AbstractCard c : player().masterDeck.group)
            {
            	if (c.originalName.equals(this.originalName))
            	{
            		AbstractDungeon.actionManager.addToTop(new PurgeSpecificCard(c, player().masterDeck));
            		break;
            	}
            }          
           
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, player().discardPile));          
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	if (!upgraded) { draw(1); }
    	else { draw(2); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkMimicLv1();
    }
    
    @Override
   	public void renderCardTip(SpriteBatch sb) {
   		super.renderCardTip(sb);
   		boolean renderTip = (boolean) ReflectionHacks.getPrivate(this, AbstractCard.class, "renderTip");

   		int count = 0;
   		if (!Settings.hideCards && renderTip) {
   			if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard) {
   				return;
   			}
   			for (AbstractCard c : tooltips) {
   				float dx = (AbstractCard.IMG_WIDTH * 0.9f - 5f) * drawScale;
   				float dy = (AbstractCard.IMG_HEIGHT * 0.4f - 5f) * drawScale;
   				if (current_x > Settings.WIDTH * 0.75f) {
   					c.current_x = current_x + dx;
   				} else {
   					c.current_x = current_x - dx;
   				}
   				if (count == 0) {
   					c.current_y = current_y + dy;
   				} else {
   					c.current_y = current_y - dy;
   				}
   				c.drawScale = drawScale * 0.8f;
   				c.render(sb);
   				count++;
   			}
   		}
   	}

    // Upgraded stats.
    @Override
    public void upgrade() 
    {        
    	if (!upgraded)
    	{
	    	this.upgradeName();
	        this.rawDescription = UPGRADE_DESCRIPTION;
	        for (AbstractCard c : tooltips) { c.upgrade(); }
	        this.initializeDescription();       
    	}
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		fiendSynTrib(tributingCard);
	}



	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}
	
    // Checking for Monster Zones if the challenge is enabled
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }

    	if (Utilities.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeMode)
    	{
    		if ((DuelistMod.getChallengeDiffIndex() < 3) && this.misc == 52) { return true; }
    		if (p.hasPower(SummonPower.POWER_ID))
    		{
    			int sums = DuelistCard.getSummons(p); int max = DuelistCard.getMaxSummons(p);
    			if (sums + this.summons <= max) 
    			{ 
    				return true; 
    			}
    			else 
    			{ 
    				if (sums < max) 
    				{ 
    					if (max - sums > 1) { this.cantUseMessage = "You only have " + (max - sums) + " monster zones"; }
    					else { this.cantUseMessage = "You only have " + (max - sums) + " monster zone"; }
    					
    				}
    				else { this.cantUseMessage = "No monster zones remaining"; }
    				return false; 
    			}
    		}
    		else
    		{
    			return true;
    		}
    	}
    	
    	else
    	{
    		return true;
    	}
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