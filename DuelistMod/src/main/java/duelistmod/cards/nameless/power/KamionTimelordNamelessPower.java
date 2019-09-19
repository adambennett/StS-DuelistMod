package duelistmod.cards.nameless.power;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.tempCards.CancelCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class KamionTimelordNamelessPower extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Nameless:Power:KamionTimelord");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("KamionTimelord.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public KamionTimelordNamelessPower() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 25 + DuelistMod.namelessTombPowerMod;
        this.tributes = this.baseTributes = 5;
        this.magicNumber = this.baseMagicNumber = 4;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.EXEMPT);
        this.tags.add(Tags.NEVER_GENERATE);
        this.misc = 0;
        this.originalName = this.name;
        this.makeMegatyped();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	attackAllEnemies(this.damage);
    	for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
    	{
    		if (!mon.isDead && !mon.isDying && !mon.isDeadOrEscaped())
    		{
    			applyPower(new WeakPower(mon, this.magicNumber, false), mon);
    		}
    	}
    	if (!(DuelistMod.secondLastCardPlayed instanceof CancelCard))
    	{
    		AbstractCard lastCard = DuelistMod.secondLastCardPlayed.makeStatEquivalentCopy();
        	if (lastCard.cost > 0) { lastCard.modifyCostForCombat(-lastCard.cost); lastCard.isCostModified = true; }
        	addCardToHand(lastCard);
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new KamionTimelordNamelessPower();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();           
            this.upgradeBaseCost(1);
            this.upgradeMagicNumber(1);
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
    	this.cantUseMessage = this.tribString;
    	return false;
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