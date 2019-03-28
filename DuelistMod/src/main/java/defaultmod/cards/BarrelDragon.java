package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import defaultmod.DefaultMod;
import defaultmod.interfaces.RandomEffectsHelper;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class BarrelDragon extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("BarrelDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.BARREL_DRAGON);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static int MIN_TURNS_ROLL = 1;
    private static int MAX_TURNS_ROLL = 10;
    private static final int RANDOM_ENEMIES = 3;
    // /STAT DECLARATION/

    public BarrelDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = RANDOM_ENEMIES;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.DRAGON);
        this.tags.add(DefaultMod.METAL_RAIDERS);
        this.tags.add(DefaultMod.GOOD_TRIB);
        this.tags.add(DefaultMod.REPLAYSPIRE);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = 3;
    }

    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		tribute(p, this.tributes, false, this);
		attack(m, AFX, this.damage);
    
		// Get number of enemies
		//int monsters = AbstractDungeon.getMonsters().monsters.size();
		
		int temp = 0;
		for (AbstractMonster a : AbstractDungeon.getMonsters().monsters)
		{
			if (!a.isDeadOrEscaped())
			{
				temp++;
			}
		}
		
		// If number of enemies < debuff targets, set debuff targets # to number of enemies
		if (temp < this.magicNumber) { this.magicNumber = this.baseMagicNumber = temp; }
		
		// 3-4 times, apply 1 or 2 random debuffs to a random enemy
		for (int i = 0; i < this.magicNumber; i++)
		{
			// Get random number of turns for debuff to apply for (1-10)
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);
			int randomTurnNumB = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);
			
			// Get random monster target
			AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();

			// Get two random debuffs
			AbstractPower randomDebuffA = RandomEffectsHelper.getRandomDebuff(p, targetMonster, randomTurnNum);
			AbstractPower randomDebuffB = RandomEffectsHelper.getRandomDebuff(p, targetMonster, randomTurnNumB);
	    
			// Apply random debuff(s)
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(targetMonster, p, randomDebuffA));
			if (this.upgraded) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(targetMonster, p, randomDebuffB)); }
		}
    
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BarrelDragon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            MIN_TURNS_ROLL = 4;
            MAX_TURNS_ROLL = 12;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
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
    	this.cantUseMessage = "Not enough Summons";
    	return false;
    }


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		if (tributingCard.hasTag(DefaultMod.DRAGON) && !AbstractDungeon.player.hasPower(GravityAxePower.POWER_ID)) 
		{ 
			if (!AbstractDungeon.player.hasPower(MountainPower.POWER_ID)) { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, 1)); }
			else { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, 2)); }
		}
	}

	@Override
	public void onResummon(int summons) 
	{
	
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