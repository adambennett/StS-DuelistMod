package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.OverflowDecrementMagicAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class ArchfiendHeiress extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ArchfiendHeiress");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ArchfiendHeiress.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ArchfiendHeiress() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 22;
        this.tributes = this.baseTributes = 1;
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondMagic = this.baseSecondMagic = 8;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
        this.tags.add(Tags.IS_OVERFLOW);
        this.exhaust = true;
    }
    
    @Override
    public void triggerOverflowEffect()
    {
    	super.triggerOverflowEffect();
    	ArrayList<DuelistCard> monsters = new ArrayList<DuelistCard>();
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) { if (c.hasTag(Tags.MONSTER) && c.damage > 0) { monsters.add((DuelistCard) c); }}
        if (monsters.size() > 0)
        {
            DuelistCard randomMon = monsters.get(AbstractDungeon.cardRandomRng.random(monsters.size() - 1));
            AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(randomMon.uuid, this.secondMagic));
        }
    }
    
    @Override
    public void triggerOnEndOfPlayerTurn() 
    {
    	// If overflows remaining
        if (checkMagicNum() > 0) 
        {
        	// Increase damage of random discard pile monster by secondMagic
            ArrayList<DuelistCard> monsters = new ArrayList<DuelistCard>();
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) { if (c.hasTag(Tags.MONSTER) && c.damage > 0) { monsters.add((DuelistCard) c); }}
            if (monsters.size() > 0)
            {
	            DuelistCard randomMon = monsters.get(AbstractDungeon.cardRandomRng.random(monsters.size() - 1));
	            AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(randomMon.uuid, this.secondMagic));
	            
	        	// Remove 1 overflow
	            AbstractDungeon.actionManager.addToTop(new OverflowDecrementMagicAction(this, -1));
            }        
        }
        super.triggerOnEndOfPlayerTurn();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	attack(m);
    	tribute();    
    }

    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeSecondMagic(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
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
    public AbstractCard makeCopy() { return new ArchfiendHeiress(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
