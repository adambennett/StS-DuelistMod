package duelistmod.cards.pools.warrior;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class CrossAttack extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CrossAttack");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CrossAttack.png");
    public static final String IMGB = DuelistMod.makeCardPath("CrossAttackS.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public CrossAttack() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.SPELL);
        this.baseDamage = this.damage = 12;
        this.baseBlock = this.block = 8;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (GameActionManager.turn % 2 == 0) { attack(m); }
    	else { block(); }
    }
    
    public void update() 
    {
		super.update();
		if (AbstractDungeon.currMapNode != null) 
		{ 
			if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) 
			{
				if (AbstractDungeon.player != null) 
				{
					if (AbstractDungeon.player.masterDeck.contains(this)) { this.rawDescription = DESCRIPTION; initializeDescription(); } 
					else { this.updateCA();}
				} 
				else if (this.rawDescription != DESCRIPTION) { this.rawDescription = DESCRIPTION; initializeDescription(); }
			}
		}
	}
    
    public void updateCA() {
		if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) 
		{
			if (GameActionManager.turn % 2 == 0) 
			{
				if (this.rawDescription == EXTENDED_DESCRIPTION[0]) { return; }
				this.superFlash(Color.CORAL.cpy());
				this.target = AbstractCard.CardTarget.ENEMY;
				this.type = AbstractCard.CardType.ATTACK;
				this.loadCardImage(IMG);
				this.rawDescription = EXTENDED_DESCRIPTION[0];
			} 
			else if (GameActionManager.turn % 2 == 1) 
			{
				if (this.rawDescription == EXTENDED_DESCRIPTION[1]) { return; }
				this.superFlash(Color.LIME.cpy());
				this.target = AbstractCard.CardTarget.SELF;
				this.type = AbstractCard.CardType.SKILL;
				this.loadCardImage(IMGB);
				this.rawDescription = EXTENDED_DESCRIPTION[1];
			}
		} 
		else { this.rawDescription = DESCRIPTION;	}
      	initializeDescription();
	}

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CrossAttack();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded)
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeDamage(3);
        	this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		// TODO Auto-generated method stub
		
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