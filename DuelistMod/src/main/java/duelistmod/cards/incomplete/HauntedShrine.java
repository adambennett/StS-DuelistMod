package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.HauntedShrineAction;
import duelistmod.cards.typecards.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.incomplete.*;
import duelistmod.variables.Tags;

public class HauntedShrine extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("HauntedShrine");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("HauntedShrine.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public HauntedShrine() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.TRAP);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (p.hasPower(HauntedPower.POWER_ID))
    	{
    		removePower(p.getPower(HauntedPower.POWER_ID), p);
    		ArrayList<DuelistCard> cardTypes = new ArrayList<DuelistCard>();
	    	cardTypes.add(new HauntAttacks(this.magicNumber));
	    	cardTypes.add(new HauntSkills(this.magicNumber));
	    	cardTypes.add(new HauntPowers(this.magicNumber));    	
	    	cardTypes.add(new HauntMonsters(this.magicNumber));    	
	    	cardTypes.add(new HauntSpells(this.magicNumber));
	    	cardTypes.add(new HauntTraps(this.magicNumber));
	    	cardTypes.add(new CancelCard());
	    	AbstractDungeon.actionManager.addToBottom(new HauntedShrineAction(cardTypes));
    	}
    	else if (p.hasPower(HauntedDebuff.POWER_ID))
    	{
    		removePower(p.getPower(HauntedDebuff.POWER_ID), p);
    		ArrayList<DuelistCard> cardTypes = new ArrayList<DuelistCard>();
	    	cardTypes.add(new HauntAttacks(this.magicNumber));
	    	cardTypes.add(new HauntSkills(this.magicNumber));
	    	cardTypes.add(new HauntPowers(this.magicNumber));    	
	    	cardTypes.add(new HauntMonsters(this.magicNumber));    	
	    	cardTypes.add(new HauntSpells(this.magicNumber));
	    	cardTypes.add(new HauntTraps(this.magicNumber));
	    	cardTypes.add(new CancelCard());
	    	AbstractDungeon.actionManager.addToBottom(new HauntedShrineAction(cardTypes));
    	}
    	else
    	{
	    	ArrayList<DuelistCard> cardTypes = new ArrayList<DuelistCard>();
	    	cardTypes.add(new HauntAttacks(this.magicNumber));
	    	cardTypes.add(new HauntSkills(this.magicNumber));
	    	cardTypes.add(new HauntPowers(this.magicNumber));    	
	    	cardTypes.add(new HauntMonsters(this.magicNumber));    	
	    	cardTypes.add(new HauntSpells(this.magicNumber));
	    	cardTypes.add(new HauntTraps(this.magicNumber));
	    	cardTypes.add(new CancelCard());
	    	AbstractDungeon.actionManager.addToBottom(new HauntedShrineAction(cardTypes));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new HauntedShrine();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(-1);
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