package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class CyberDragon extends DuelistCard 
{
	// TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CyberDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.CYBER_DRAGON);
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
    private int originalCostForTurn = 2;
    // /STAT DECLARATION/

    public CyberDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 13;
        this.originalCostForTurn = this.costForTurn;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.CYBER);
        this.misc = 0;
        this.originalName = this.name;
        this.summons = this.baseSummons = 1;
    }
  
    @Override
    public void modifyCostForCombat(final int amt)
    {
    	super.modifyCostForCombat(amt);
    	if (this.costForTurn != this.originalCostForTurn) { this.originalCostForTurn = this.costForTurn; }
    }
    
    @Override
    public void setCostForTurn(final int amt)
    {
    	super.setCostForTurn(amt);
    	if (this.costForTurn != this.originalCostForTurn) { this.originalCostForTurn = this.costForTurn; }
    }
    
    @Override
    public void updateCost(final int amt)
    {
    	super.updateCost(amt);
    	if (this.costForTurn != this.originalCostForTurn) { this.originalCostForTurn = this.costForTurn; }
    }
    
    @Override
    protected void upgradeBaseCost(final int newBaseCost)
    {
    	super.upgradeBaseCost(newBaseCost);
    	if (this.costForTurn != this.originalCostForTurn) { this.originalCostForTurn = this.costForTurn; }
    }
    
    @Override
    public void resetAttributes()
    {
    	super.resetAttributes();
    	if (this.costForTurn != this.originalCostForTurn) { this.originalCostForTurn = this.costForTurn; }
    }

    private void costUpdater(boolean hadTribMods)
    {
    	if (hadTribMods && this.costForTurn != 0)
    	{ 
    		this.costForTurn = 0; 
    		if (this.cost != 0) { this.isCostModifiedForTurn = true; }
    		else { this.isCostModifiedForTurn = false; }
    	}
    	else if (!hadTribMods) 
    	{ 
    		this.costForTurn = this.originalCostForTurn;
    		if (this.cost != this.originalCostForTurn) { this.isCostModifiedForTurn = true; }
    		else { this.isCostModifiedForTurn = false; }
    	}
    }
    
    @Override
    public void update()
    {
    	super.update();
		if (AbstractDungeon.currMapNode != null)
		{
			if (AbstractDungeon.player != null && AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT))
			{
				boolean hasTributeMod = false;
				for (AbstractCard c : AbstractDungeon.player.hand.group)
				{
					if (c instanceof DuelistCard)
					{
						DuelistCard dc = (DuelistCard)c;
						if (dc.isTributesModified || dc.isTributesModifiedForTurn) { hasTributeMod = true; break; }
					}
				}
				costUpdater(hasTributeMod);
			}
		}
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	attack(m);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CyberDragon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    // Checking for Monster Zones if the challenge is enabled
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }

    	if (Util.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeLevel20)
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