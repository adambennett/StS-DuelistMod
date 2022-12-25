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

public class CyberEndDragon extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CyberEndDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CyberEndDragon.png");
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
    private int originalTribCost = 7;
    // /STAT DECLARATION/

    public CyberEndDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 28;
        this.tributes = this.baseTributes = this.originalTribCost = 7;
        this.specialCanUseLogic = true;	
        this.useTributeCanUse   = true;
        this.baseMagicNumber = this.magicNumber = 2;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.CYBER);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.BAD_MAGIC);
        this.misc = 0;
        this.originalName = this.name;
        this.baseAFX = AttackEffect.FIRE;
    }
    
    @Override
    public void modifyTributesForTurn(int add)
	{
		if (this.originalTribCost + add <= 0)
		{
			this.tributesForTurn = 0;
			this.tributes = 0;
			this.originalTribCost = 0;
			/*int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			
			if (indexOfTribText > -1)
			{
				if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.originalDescription = this.rawDescription;
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.tributes + add : " + this.tributes + add); }
			}*/
		}
		else { this.originalDescription = this.rawDescription; this.tributesForTurn = this.tributes = this.originalTribCost += add; }
		this.isTributesModifiedForTurn = true;
		this.isTributesModified = true;
		this.initializeDescription();
	}

    @Override
	public void modifyTributes(int add)
	{
		if (this.originalTribCost + add <= 0)
		{
			this.tributes = this.baseTributes = this.originalTribCost = 0;
			/*int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			if (indexOfTribText > -1)
			{
				if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
				this.originalDescription = this.rawDescription;
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseTributes + add : " + this.baseTributes + add); }
			}			*/
		}
		else { this.tributes = this.baseTributes = this.originalTribCost += add; }
		this.isTributesModified = true; 
		this.initializeDescription();
	}
    
    @Override
    public void postTurnReset()
	{
		if (this.isTributesModifiedForTurn) { this.originalTribCost = this.baseTributes; }
		super.postTurnReset();
	}
    
    public void modifyTributesForEffect(int add)
    {
    	if (this.tributes + add <= 0)
		{
			this.tributesForTurn = 0;
			this.tributes = 0;
			/*int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			
			if (indexOfTribText > -1)
			{
				if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.originalDescription = this.rawDescription;
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.tributes + add : " + this.tributes + add); }
			}*/
		}
		else { this.originalDescription = this.rawDescription; this.tributesForTurn = this.tributes += add; }
		this.isTributesModifiedForTurn = true;
		this.isTributesModified = true;
		this.initializeDescription();
    }
    
    private void costUpdater(boolean hadTribute)
    {
    	if (hadTribute)
    	{ 
    		this.modifyTributesForEffect(-this.tributes + this.magicNumber);
    	}
    	else  
    	{ 
    		this.modifyTributesForEffect(-this.tributes + this.originalTribCost);
    		this.isTributesModified = false;
    		this.isTributesModifiedForTurn = false;
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
				boolean hadTribute = false;
				for (AbstractCard c : AbstractDungeon.player.hand.group)
				{
					if (c instanceof DuelistCard)
					{
						DuelistCard dc = (DuelistCard)c;
						if (dc.isTributesModified || dc.isTributesModifiedForTurn) { hadTribute = true; break; }
					}
				}
				costUpdater(hadTribute);
			}
		}
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	attack(m);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CyberEndDragon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
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
