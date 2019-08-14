package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class ChaosAncientGearGiant extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ChaosAncientGearGiant");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ChaosAncientGearGiant.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public ChaosAncientGearGiant() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 70;
        this.tributes = this.baseTributes = 12;
        this.baseMagicNumber = this.magicNumber = 1;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GIANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	attack(m);
    	if (this.tributes == 0)
    	{
    		AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, 12 - this.tributes, true));
    		this.rawDescription = this.originalDescription;
    		this.initializeDescription();    		
    	}
    	else
    	{
    		AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, 12 - this.tributes, true));
    	}
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) 
    {
    	if (c.hasTag(Tags.MACHINE) && this.tributes > 0)
    	{
    		AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(this, -this.magicNumber, true));
    	}
    }
    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
    	 if (!this.upgraded) 
    	 {
             this.upgradeName();
             this.upgradeTributes(-2);
             this.rawDescription = UPGRADE_DESCRIPTION;
             this.initializeDescription();
         }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
		
	}
	
    // If player doesn't have enough summons, can't play card
  	@Override
  	public boolean canUse(AbstractPlayer p, AbstractMonster m)
  	{
  		// Check super canUse()
  		boolean canUse = super.canUse(p, m); 
  		if (!canUse) { return false; }
  		
  		// Pumpking & Princess
  		else if (this.misc == 52) { return true; }
  		
  		// Mausoleum check
    	else if (p.hasPower(EmperorPower.POWER_ID))
		{
			EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
			if (!empInstance.flag)
			{
				return true;
			}
			
			else
			{
				if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } }
			}
		}

  		// Check for # of summons >= tributes
  		else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } } }

  		// Player doesn't have something required at this point
  		this.cantUseMessage = this.tribString;
  		return false;
  	}


	@Override
	public void onResummon(int summons) 
	{
		
		
	}

	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new ChaosAncientGearGiant(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}