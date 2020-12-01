package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.orbs.*;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class GateGuardian extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = duelistmod.DuelistMod.makeID("GateGuardian");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GateGuardian.png");
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
    //private static final int SUMMONS = 1;
    // /STAT DECLARATION/

    public GateGuardian() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUARDIAN);
        this.tags.add(Tags.METAL_RAIDERS);
        this.tags.add(Tags.WARRIOR);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 3;
        this.damage = this.baseDamage = 25;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<DuelistCard> tributeList = tribute(p, this.tributes, false, this);
    	if (tributeList.size() > 0)
    	{
    		for (DuelistCard c : tributeList)
    		{
    			switch(c.originalName)
    			{
	    			case "Sanga of Thunder":
	    				AbstractOrb orb = new Lightning();
	    				channel(orb);
	    				break;
	    			case "Kazejin":
	    				AbstractOrb orbB = new Earth();
	    				channel(orbB);
	    				break;
	    			case "Suijin":
	    				channelWater();
	    				break;
	    			default:
	    				break;
    			}
    		}
    	}
    	attack(m);
    	AbstractOrb orb = new Gate();
    	AbstractDungeon.actionManager.addToBottom(new ChannelAction(orb));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GateGuardian();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
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
