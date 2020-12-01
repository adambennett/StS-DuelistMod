package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
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
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class CyberValley extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CyberValley");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CyberValley.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    private int originalCostForTurn = 1;
    // /STAT DECLARATION/

    public CyberValley() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage 				= 8;		// dmg
        this.summons = this.baseSummons				= 1;		// summons
        this.originalCostForTurn = this.costForTurn;
        this.specialCanUseLogic = true;							// for any summon or tribute card
        this.baseMagicNumber = this.magicNumber 	= 1;		// 
        this.baseSecondMagic = this.secondMagic 	= 1;		//
        this.baseThirdMagic = this.thirdMagic 		= 1;		//
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.CYBER);
        this.tags.add(Tags.MACHINE);
        this.misc = 0;
        this.originalName = this.name;
        this.baseAFX = AttackEffect.FIRE;
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
				if (hasTributeMod) { AbstractDungeon.player.hand.glowCheck(); }
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
        return new CyberValley();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeDamage(3);
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
