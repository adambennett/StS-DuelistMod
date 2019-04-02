package defaultmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

public class ResummonBranch extends CustomRelic
{
	// FIELDS
	public static final String ID = DefaultMod.makeID("ResummonBranch");
    public static final String IMG = DefaultMod.makePath(DefaultMod.RESUMMON_BRANCH_RELIC);
    public static final String OUTLINE = DefaultMod.makePath(DefaultMod.RESUMMON_BRANCH_RELIC_OUTLINE);
    private static int counterLimit = 30;
    // /FIELDS

    public ResummonBranch() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL); }
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public void onEquip()
    {
    	this.counter = 0;
    }
    
    @Override
    public void onExhaust(AbstractCard card) 
    {
		if (this.counter < counterLimit) { this.flash(); setCounter(counter + 1); }
    }
    
    @Override
    public void atTurnStartPostDraw() 
    {
    	int count = this.counter;
    	if (DefaultMod.monstersThisRun.size() > 0)
    	{
			ArrayList<String> notAllowedCards = new ArrayList<String>();
			notAllowedCards.add("The Creator");
			notAllowedCards.add("Dark Creator");
			notAllowedCards.add("Gandora");
			notAllowedCards.add("Steam Train King");
			ArrayList<DuelistCard> modMonsters = new ArrayList<DuelistCard>();
			
			for (DuelistCard c : DefaultMod.monstersThisRun)
			{
				if (!notAllowedCards.contains(c.originalName) && !c.hasTag(DefaultMod.EXEMPT))
				{
					modMonsters.add(c);
				}
			}
			
			if (modMonsters.size() > 0)
			{
				if (count > counterLimit) { counter = counterLimit; }
	    		for (int i = 0; i < count; i++)
	    		{
					this.flash();
	    			DuelistCard toResummon = modMonsters.get(AbstractDungeon.relicRng.random(modMonsters.size() - 1));
					AbstractMonster m = AbstractDungeon.getRandomMonster();
					DuelistCard.fullResummon(toResummon, false, m, true);	  
					if (DefaultMod.debug)
					{
						System.out.println("theDuelist:ResummonBranch:atTurnStart() ---> called resummon block on " + toResummon.originalName);
					}    	
					setCounter(counter - 1);
	    		}
			}
    	}
    	setCounter(0);
    }
    
    @Override
    public void onPlayerEndTurn() 
    {
    	
    }
    
    @Override
    public void onVictory() 
    {

    	setCounter(0);
    }
}
