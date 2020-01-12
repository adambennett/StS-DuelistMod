package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;

public class SkillsagaSupremacyAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int check = 0;
    private AbstractMonster targ = null;

    public SkillsagaSupremacyAction(int amt, AbstractMonster targ)
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        this.check = amt;
        this.targ = targ;
    }

    @Override
    public void update()
    {
    	ArrayList<AbstractCard> skills = DuelistCard.findAllOfCardTypeForResummon(CardType.SKILL, 1);
        if (duration == DURATION && AbstractDungeon.player.currentBlock < this.check && skills.size() > 0) 
        {
        	if (targ == null) 
        	{
        		targ = AbstractDungeon.getRandomMonster();
        		if (targ != null)
        		{
        			AbstractCard rand = skills.get(AbstractDungeon.cardRandomRng.random(skills.size() - 1)).makeStatEquivalentCopy();
            		DuelistCard.resummon(rand, targ);
        		}
        	}
        	else
        	{
        		AbstractCard rand = skills.get(AbstractDungeon.cardRandomRng.random(skills.size() - 1)).makeStatEquivalentCopy();
        		DuelistCard.resummon(rand, targ);
        	}
        	
        	
        	if (AbstractDungeon.player.currentBlock < this.check)
        	{
        		this.addToBot(new SkillsagaSupremacyAction(this.check, this.targ));
        	}
        }
        tickDuration();
    }
}
