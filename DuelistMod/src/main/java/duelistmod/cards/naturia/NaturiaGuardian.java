package duelistmod.cards.naturia;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class NaturiaGuardian extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("NaturiaGuardian");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.NATURIA_GUARDIAN);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    private ArrayList<AbstractCard> tooltips;
    // /STAT DECLARATION/

    public NaturiaGuardian() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 10;
        this.summons = this.baseSummons = 2;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.NATURIA);
        this.tags.add(Tags.PLANT);
        this.originalName = this.name;
        this.isSummon = true;
        this.baseMagicNumber = this.magicNumber = 1;
        tooltips = new ArrayList<>();
        tooltips.add(new NaturiaHorneedle());
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	attack(m, this.baseAFX, this.damage);
    	//applyPowerToSelf(new NaturiaPower(p, p, 1));
    	if (!upgraded) { AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(new NaturiaHorneedle(), this.magicNumber, true, true)); }
    	else 
    	{
    		DuelistCard uNH = new NaturiaHorneedle();
    		uNH.upgrade();
    		AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(uNH, this.magicNumber, true, true));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new NaturiaGuardian();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
            for (AbstractCard c : tooltips)
            {
            	c.upgrade();
            }
        }
    }
    

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		naturiaSynTrib(tributingCard);
		plantSynTrib(tributingCard);
	}

    // Checking for Monster Zones if the challenge is enabled
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }

    	if (Util.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeMode)
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
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
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