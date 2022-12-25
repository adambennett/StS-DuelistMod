package duelistmod.cards.pools.dragons;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class SuperancientDinobeast extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("SuperancientDinobeast");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SUPERANCIENT_DINOBEAST);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public SuperancientDinobeast() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 8;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DINOSAUR);
		this.tags.add(Tags.ANCIENT_FOR_PIXIE);
		this.tags.add(Tags.ANCIENT_FOR_MACHINE);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	boolean foundDrag = false; int drags = 0;
    	ArrayList<DuelistCard> tributeList = tribute();
    	if (tributeList.size() > 0) 
    	{ 
    		for (DuelistCard c : tributeList) 
    		{ 
    			if (c.hasTag(Tags.DRAGON)) 
    			{ 
    				foundDrag = true; drags++;
    			} 
    		} 
    	}
    	
    	if (foundDrag) { block(this.magicNumber * drags); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SuperancientDinobeast();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    


	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
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
