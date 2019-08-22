package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class SuperheavySwordsman extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("SuperheavySwordsman");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.SUPERHEAVY_SWORDSMAN);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public SuperheavySwordsman() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = 5;
		this.summons = this.baseSummons = 1;
		//this.exhaust = true;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.SUPERHEAVY);
		this.originalName = this.name;
		this.isSummon = true;
		this.isMultiDamage = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, this.summons, this);
		attack(m, AFX, this.damage);
		this.damage = this.baseDamage;
		if (p.hasPower(DexterityPower.POWER_ID) && this.damage > 0)
		{
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
		}
	}

    @Override
    public void applyPowers() 
    {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID))
		{
        	 this.baseDamage = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
             this.damage = this.baseDamage;
             this.initializeDescription();
		}
        else
        {
        	 this.baseDamage = 0;
             this.damage = this.baseDamage;
             this.initializeDescription();
        }	   
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster mo) 
    {
        super.calculateCardDamage(mo);
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID))
		{
        	 this.baseDamage = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
             this.damage = this.baseDamage;
             this.initializeDescription();
		}
        else
        {
        	 this.baseDamage = 0;
             this.damage = this.baseDamage;
             this.initializeDescription();
        }	
    }

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new SuperheavySwordsman();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(3);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	@Override
	public void onTribute(DuelistCard tributingCard) {
		superSynTrib(tributingCard);

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
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		
		
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