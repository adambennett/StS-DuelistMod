package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.powers.enemyPowers.EnemySummonsPower;
import duelistmod.variables.*;

public class HeavyStorm extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("HeavyStorm");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.HEAVY_STORM);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public HeavyStorm() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
       	this.exhaust = true;
       	this.tags.add(Tags.SPELL);
       	this.tags.add(Tags.ALL);
       	this.tags.add(Tags.METAL_RAIDERS);
		this.originalName = this.name;
		this.baseMagicNumber = this.magicNumber = 4;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (p.hasPower(SummonPower.POWER_ID))
    	{
	    	SummonPower summonsInstance = (SummonPower) p.getPower(SummonPower.POWER_ID);
			summonsInstance.setCardsSummoned(new ArrayList<>());
	    	int summonsRemoved = summonsInstance.amount;
	    	
	    	for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
	    	{
	    		if (mon.hasPower(EnemySummonsPower.POWER_ID))
	    		{
	    			int amt = mon.getPower(EnemySummonsPower.POWER_ID).amount;
	    			DuelistCard.removePower(mon.getPower(EnemySummonsPower.POWER_ID), mon);
	    			summonsRemoved += amt;
	    			if (mon instanceof DuelistMonster)
	    			{
	    				DuelistMonster kaiba = (DuelistMonster)mon;
	    				kaiba.triggerHandReset();
	    			}
	    		}
	    	}
	    	
	    	for (int i = 0; i < summonsRemoved; i++)
	    	{
	    		AbstractMonster mon = AbstractDungeon.getRandomMonster();
	    		if (mon != null) { thornAttack(mon, this.magicNumber); }
	    	}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
    	return new HeavyStorm();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
    	if (!this.upgraded) {
    		this.upgradeName();
    		if (DuelistMod.hasUpgradeBuffRelic)
    		{
    			this.upgradeMagicNumber(2);
    		}
    		else
    		{
    			this.upgradeMagicNumber(1);
    		}    		
    		this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
    		this.initializeDescription();
    	}
    }












}
