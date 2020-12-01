package duelistmod.cards.pools.dragons;

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
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class BusterBladerDDS extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("BusterBladerDDS");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BusterBladerDDS.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    private int dragons = 0;
    // /STAT DECLARATION/

    public BusterBladerDDS() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 16;
        this.magicNumber = this.baseMagicNumber = 4;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WARRIOR);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 4; 
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	dragons = 0;
    	ArrayList<DuelistCard> tributeList = tribute(p, this.tributes, false, this);
    	if (tributeList.size() > 0)
    	{
    		for (DuelistCard c : tributeList)
	    	{
	    		if (c.hasTag(Tags.DRAGON))
	    		{
	    			dragons++;
	    		}
	    	}
    	}
    	
    	int newDamage = this.damage + (this.magicNumber * dragons);
    	attack(m, AFX, newDamage);
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
							if (pow.actualCardSummonList.get(i).hasTag(Tags.DRAGON))
							{
								dmg += this.magicNumber;
							}
						}
						
						if (dmg > 0)
						{
							this.secondMagic = this.baseSecondMagic = dmg;
						}
					}	
					else
					{
						this.secondMagic = this.baseSecondMagic =  0;
					}
				}
				else
				{
					this.secondMagic = this.baseSecondMagic =  0;
				}
			}
			else
			{
				this.secondMagic = this.baseSecondMagic =  0;
			}
		}
	}

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BusterBladerDDS();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (DuelistMod.hasUpgradeBuffRelic) { this.upgradeDamage(4); }
            this.upgradeMagicNumber(3);
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
