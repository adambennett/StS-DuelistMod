package duelistmod.cards.pools.machine;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.GameActionManager;
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

public class FrontlineObserver extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("FrontlineObserver");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("FrontlineObserver.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXT = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    private int origDam = 10;
    private int frstTurnDam = 16;
    // /STAT DECLARATION/

    public FrontlineObserver() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = this.origDam = 10;
        this.tributes = this.baseTributes = 1;
        this.baseSecondMagic = this.secondMagic = this.frstTurnDam = 16;
        this.specialCanUseLogic = true;
        this.useTributeCanUse = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.MACHINE);
        this.misc = 0;
        this.originalName = this.name;
    }
    
    @Override
    public void update()
    {
    	super.update();
    	if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT))
    	{
    		if (GameActionManager.turn == 1)
    		{
    			this.baseDamage = this.frstTurnDam;
    			this.rawDescription = EXT[0];
    			this.initializeDescription();
    		}
    		else
    		{
    			this.baseDamage = this.origDam;
    			this.rawDescription = EXT[0];
    			this.initializeDescription();
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
    
    @Override
    public void triggerOnGlowCheck() 
    {
    	super.triggerOnGlowCheck();
    	if (GameActionManager.turn == 1) {
            this.glowColor = Color.GOLD;
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new FrontlineObserver();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeDamage(2);
            this.upgradeSecondMagic(6);
            this.frstTurnDam += 6;
            this.origDam += 2;
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
