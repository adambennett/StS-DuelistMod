package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.UniZombCard;
import duelistmod.cards.other.tempCards.*;

public class UniZombieAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private AbstractMonster targetMon;
	private int damage;
	private int blk;
	private int inc;

	public UniZombieAction(AbstractMonster target, int dmg, int block, int mag)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duration = Settings.ACTION_DUR_MED;
		this.targetMon = target;
		this.damage = dmg;
		this.blk = block;
		this.inc = mag;
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			
			tmp.addToTop(new UniZombieDMG(this.damage)); 
			tmp.addToTop(new UniZombieBLK(this.blk)); 
			tmp.addToTop(new UniZombieINC(this.inc)); 			
			if (tmp.group.size() > 0)
			{
				tmp.addToTop(new CancelCard());
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Uni Zombie", false, false, false, false); 	
			}
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				if (c instanceof UniZombCard)
				{
					UniZombCard ref = (UniZombCard)c;
					ref.activate(this.targetMon);
				}				
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
