package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class FogKing extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("FogKing");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.FOG_KING);
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

    public FogKing() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SPELLCASTER);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 3;
        this.magicNumber = this.baseMagicNumber = 0;
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
					if (pow.getCardsSummoned().size() >= this.tributes)
					{
						int endIndex = pow.getCardsSummoned().size() - 1;
						for (int i = endIndex; i > endIndex - this.tributes; i--)
						{
							dmg += pow.getCardsSummoned().get(i).damage;
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
	    		damageIncrease += c.baseDamage + 3; 
	    		if (DuelistMod.debug) { System.out.println("theDuelist:FogKing:use() ---> card damage: " + c.baseDamage); }
	    	}
    	}
    	else if (!upgraded && tributeList.size() > 0) 
    	{
    		for (DuelistCard c : tributeList)
	    	{ 
	    		damageIncrease += c.baseDamage; 
	    		if (DuelistMod.debug) { System.out.println("theDuelist:FogKing:use() ---> card damage: " + c.baseDamage); }
	    	}
    	}
    	specialAttack(m, AFX, damageIncrease);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new FogKing();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    















   
}
