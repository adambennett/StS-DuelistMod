package duelistmod.cards.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.interfaces.*;
import duelistmod.orbs.Metal;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class MachineToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MachineToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("YellowGadget.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/a

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public MachineToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.tags.add(Tags.MACHINE); 
    	this.purgeOnUse = true;
    	this.isEthereal = true;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
    }
    public MachineToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.MACHINE); 
    	this.purgeOnUse = true;
    	this.isEthereal = true;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, 1, this);
    	int roll = AbstractDungeon.cardRandomRng.random(1,2);
    	if (roll == 1) { AbstractOrb metal = new Metal(); channel(metal); }
    }
    @Override public AbstractCard makeCopy() { return new MachineToken(); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		machineSynTrib(tributingCard);
	}
	
	@Override public void onResummon(int summons) 
	{ 
		
	}
	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade() 
	{
		if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
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