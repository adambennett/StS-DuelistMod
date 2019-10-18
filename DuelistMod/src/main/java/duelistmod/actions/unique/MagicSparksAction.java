package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class MagicSparksAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;

    public MagicSparksAction(AbstractMonster monster)
    {
        target = monster;
        actionType = ActionType.DAMAGE;
        duration = DURATION;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) 
        {
            if (target.isDying || target.currentHealth <= 0) 
            {
            	ArrayList<AbstractCard> spells = new ArrayList<AbstractCard>();
            	ArrayList<AbstractCard> deck = new ArrayList<AbstractCard>();
            	for (AbstractCard c : AbstractDungeon.player.masterDeck.group) 
            	{ 
            		if (c.hasTag(Tags.SPELL) && c.canUpgrade()) { spells.add(c); }
            		else { deck.add(c); }
            	}
            	
            	if (spells.size() > 0)
            	{
            		int index = AbstractDungeon.cardRandomRng.random(spells.size() - 1);
	            	AbstractCard randSpell = spells.get(index);
	            	randSpell.upgrade();
	            	AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
	                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(randSpell.makeStatEquivalentCopy()));
	            	Util.log("Golden Sparks upgraded: " + randSpell.originalName);
	            	deck.add(randSpell);
	            	for (int i = 0; i < spells.size(); i++)
	            	{
	            		if (i != index) { deck.add(spells.get(i)); }
	            	}
            	}
            	else
            	{
            		Util.log("Golden Sparks found no Spells in your deck");
            	}
            	
            	AbstractDungeon.player.masterDeck.clear();
            	for (AbstractCard c : deck) 
            	{
            		AbstractDungeon.player.masterDeck.addToTop(c);
            	}
            }
            
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}
