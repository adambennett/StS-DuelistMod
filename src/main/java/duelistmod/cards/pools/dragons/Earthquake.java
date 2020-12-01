package duelistmod.cards.pools.dragons;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
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
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class Earthquake extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Earthquake");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Earthquake.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public Earthquake() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.isMultiDamage = true;
        this.baseDamage = this.damage 				= 7;
        this.baseMagicNumber = this.magicNumber 	= 2;
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	normalMultidmg();
    	ArrayList<AbstractMonster> mons = getAllMons();
    	if (mons.size() <= this.magicNumber)
    	{
    		for (AbstractMonster mon : mons)
    		{
    			int stunRoll = AbstractDungeon.cardRandomRng.random(1, 10);
    	    	if (stunRoll <= 2) 
    	    	{
    	    		this.addToBot(new StunMonsterAction(mon, p));
    	    	}
    		}
    	}
    	else 
    	{
    		ArrayList<AbstractMonster> stunChoices = new ArrayList<AbstractMonster>();
    		while (stunChoices.size() < this.magicNumber)
    		{
    			AbstractMonster rand = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() - 1));
    			while (stunChoices.contains(rand)) { rand = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() - 1)); }
    			stunChoices.add(rand);
    		}
    		
    		for (AbstractMonster mon : stunChoices)
    		{
    			int stunRoll = AbstractDungeon.cardRandomRng.random(1, 11);
    	    	if (stunRoll <= 2) 
    	    	{
    	    		this.addToBot(new StunMonsterAction(mon, p));
    	    	}
    		}
    	}
    	
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Earthquake();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(1);
            this.upgradeDamage(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
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
