package duelistmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;

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
    private static final int COST = 10;
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
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
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
    	}
    }
    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
    	if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		// Fiend Tribute
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower(DoomdogPower.POWER_ID) && tributingCard.hasTag(Tags.FIEND)) { int dmgAmount = p.getPower(DoomdogPower.POWER_ID).amount; damageAllEnemiesThornsNormal(dmgAmount); }
		if (p.hasPower(RedMirrorPower.POWER_ID) && tributingCard.hasTag(Tags.FIEND)) { for (AbstractCard c : p.discardPile.group) { if (c.cost > 0)	{ c.modifyCostForTurn(-p.getPower(RedMirrorPower.POWER_ID).amount);	c.isCostModifiedForTurn = true;	}}}
		if (tributingCard.hasTag(Tags.FIEND)) { AbstractDungeon.actionManager.addToBottom(new FetchAction(p.discardPile, DuelistMod.fiendDraw)); }		
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