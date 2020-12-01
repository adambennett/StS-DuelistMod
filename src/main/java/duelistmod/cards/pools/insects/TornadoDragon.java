package duelistmod.cards.pools.insects;

import java.util.*;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.EvokeRandomOrbAction;
import duelistmod.actions.common.EvokeSpecificOrbAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class TornadoDragon extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("TornadoDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("TornadoDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public TornadoDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons = 3;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WYRM);
        this.tags.add(Tags.DRAGON);
        this.showEvokeValue = true;
        this.originalName = this.name;
        this.isSummon = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	if (p.hasOrb())
    	{
    		int orbCount = 0;
    		for (AbstractOrb o : p.orbs) { if (!(o instanceof EmptyOrbSlot)) { orbCount++; }}
    		if (orbCount > 0)
    		{
    			int orbsToEvoke = AbstractDungeon.cardRandomRng.random(0, orbCount);
    			Util.log("Tornado Dragon is evoking " + orbCount + " orbs.");
    			if (orbsToEvoke == orbCount) { DuelistCard.evokeAll(); }
    			else if (orbsToEvoke > 0)
    			{
    				for (int i=0; i<orbsToEvoke; i++) {
    					this.addToBot(new EvokeRandomOrbAction());
					}
    			}
    			else { Util.log("Tornado Dragon generated 0 for number of orbs to evoke! Discarding promptly! :)"); }
    		}
    	}
    	for (AbstractCard c : p.drawPile.group)
    	{
    		if (c.type.equals(CardType.STATUS))
    		{
    			this.addToBot(new ExhaustSpecificCardAction(c, p.drawPile));
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new TornadoDragon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
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
