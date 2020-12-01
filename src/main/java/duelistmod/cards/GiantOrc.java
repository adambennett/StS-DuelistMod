package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class GiantOrc extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GiantOrc");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GiantOrc.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 11;
    // /STAT DECLARATION/

    public GiantOrc() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 30;
        this.summons = this.baseSummons = 1;
        this.baseMagicNumber = this.magicNumber = 1;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
        this.tags.add(Tags.GIANT);
        this.tags.add(Tags.EXEMPT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	attack(m);
    	if (this.cost != 10)
    	{
    		this.modifyCostForCombat(-this.cost + 10);
    		this.isCostModified = false;
    	}
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) 
    {
    	if (c.type.equals(CardType.ATTACK))
    	{
    		this.modifyCostForCombat(-this.magicNumber);
    		this.isCostModified = true;
    		AbstractDungeon.player.hand.glowCheck();
    	}
    }
    
    @Override
    public void onEnemyUseCardWhileInHand(AbstractCard c)
    {
    	if (c.type.equals(CardType.ATTACK))
    	{
    		this.modifyCostForCombat(-this.magicNumber);
    		this.isCostModified = true;
    		AbstractDungeon.player.hand.glowCheck();
    	}
    }
    
    @Override
    public void onEnemyUseCardWhileInDiscard(AbstractCard c)
    {
    	if (c.type.equals(CardType.ATTACK))
    	{
    		this.modifyCostForCombat(-this.magicNumber);
    		this.isCostModified = true;
    	}
    }
    
    @Override
    public void onEnemyUseCardWhileInDraw(AbstractCard c)
    {
    	if (c.type.equals(CardType.ATTACK))
    	{
    		this.modifyCostForCombat(-this.magicNumber);
    		this.isCostModified = true;
    	}
    }
    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
    	if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		fiendSynTrib(tributingCard);	
	}
	
	@Override
	public void onResummon(int summons) 
	{
		
		
	}
	


	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new GiantOrc(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
