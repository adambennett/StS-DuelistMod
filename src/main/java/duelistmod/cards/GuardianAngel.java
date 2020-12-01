package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class GuardianAngel extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GuardianAngel");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GUARDIAN_ANGEL);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public GuardianAngel() 
    {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 0;
    	this.tags.add(Tags.MONSTER);
    	this.tags.add(Tags.SPELLCASTER);
    	this.tags.add(Tags.INVASION_CHAOS);
    	this.tags.add(Tags.ORIGINAL_HEAL_DECK);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.tributes = this.baseTributes = 3;
    	this.magicNumber = this.baseMagicNumber = 0;       
    	this.startingOPHDeckCopies = 1;
    	this.setupStartingCopies();
    }
    
    @Override
	public void update()
	{
    	super.update();
    	if (AbstractDungeon.currMapNode != null)
		{
			if (AbstractDungeon.player != null && AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT))
			{
				int dmg = 0;
				if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
				{				
					SummonPower pow = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
					if (pow.actualCardSummonList.size() >= this.tributes)
					{
						int endIndex = pow.actualCardSummonList.size() - 1;
						for (int i = endIndex; i > endIndex - this.tributes; i--)
						{
							dmg += pow.actualCardSummonList.get(i).block;
						}
						
						if (upgraded)
						{
							this.magicNumber = this.baseMagicNumber = dmg + 9;						
						}
						else if (dmg > 0)
						{
							this.magicNumber = this.baseMagicNumber = dmg;						
						}
					}	
					else
					{
						this.magicNumber = this.baseMagicNumber = 0;
					}
				}
				else
				{
					this.magicNumber = this.baseMagicNumber = 0;
				}
			}
			else
			{
				this.magicNumber = this.baseMagicNumber = 0;
			}
		}
	}

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int damageIncrease = 0;
    	ArrayList<DuelistCard> tributeList = tribute(p, this.tributes, false, this);
    	if (upgraded && tributeList.size() > 0) 
    	{ 
    		for (DuelistCard c : tributeList) 
	    	{ 
	    		damageIncrease += c.baseBlock + 3; 
	    		if (DuelistMod.debug) { System.out.println("theDuelist:GuardianAngel:use() ---> card damage: " + c.baseDamage); }
	    	}
    	}
    	else if (!upgraded && tributeList.size() > 0) 
    	{
    		for (DuelistCard c : tributeList)
	    	{ 
	    		damageIncrease += c.baseBlock; 
	    		if (DuelistMod.debug) { System.out.println("theDuelist:GuardianAngel:use() ---> card damage: " + c.baseDamage); }
	    	}
    	}
    	specialAttack(m, AFX, damageIncrease);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GuardianAngel();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		spellcasterSynTrib(tributingCard);
	}


	@Override
	public void onResummon(int summons) 
	{

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
