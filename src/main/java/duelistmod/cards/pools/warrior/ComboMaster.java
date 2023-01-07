package duelistmod.cards.pools.warrior;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class ComboMaster extends DynamicDamageCard
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ComboMaster");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ComboMaster.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    public Boolean wasRetained = false;
    // /STAT DECLARATION/

    public ComboMaster() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = this.originalDamage = 14;
        this.tributes = this.baseTributes = 3;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WARRIOR);
    }

	@Override
	public int damageFunctionMultiplier() {
		return this.wasRetained ? 3 : 1;
	}


	@Override
	public AbstractCard makeStatEquivalentCopy() {
		ComboMaster ret = (ComboMaster)super.makeStatEquivalentCopy();
		ret.wasRetained = this.wasRetained;
		return ret;
	}

	public void onMoveToDiscard() {
		super.onMoveToDiscard();
		this.wasRetained = false;
	}

	public void update() {
		super.update();
		if (this.retain) { this.wasRetained = true; }
	}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(); 	
    	AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void triggerOnGlowCheck()
    {
    	super.triggerOnGlowCheck();
        if (this.wasRetained) {
        	 this.glowColor = Color.GOLD;
        }
    }
    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeBaseCost(1);
        	this.upgradeTributes(2);
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
	public void onResummon(int summons) 
	{
		
		
	}

	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new ComboMaster(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
