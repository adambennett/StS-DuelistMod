package duelistmod.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public abstract class ArmedDragonCard extends DuelistCard {

	public ArmedDragonCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE,
			CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
	}

	public abstract AbstractCard nextLevel();
	
	public static void armedProtectorLvlUp()
	{
		lvlUpGroup(AbstractDungeon.player.hand.group);
		lvlUpGroup(AbstractDungeon.player.discardPile.group);
		lvlUpGroup(AbstractDungeon.player.exhaustPile.group);
		lvlUpHand();
	}

	public static void lvlUpGroup(ArrayList<AbstractCard> group)
	{
		for (int i = 0; i < group.size(); i++)
		{
			AbstractCard current = group.get(i);
			if (current instanceof ArmedDragonCard)
			{
				AbstractCard next = ((ArmedDragonCard) current).nextLevel();
				if (next != null)
				{
					group.set(i, next);
				}
				else // Card is already at the highest level
				{
					current.upgrade();
				}
			}
		}
	}

	public static void lvlUpHand()
	{
		for (int i = 0; i < AbstractDungeon.player.hand.group.size(); i++)
		{
			AbstractCard current = AbstractDungeon.player.hand.group.get(i);
			if (current instanceof ArmedDragonCard)
			{
				AbstractCard next = ((ArmedDragonCard) current).nextLevel();
				if (next != null)
				{
					next.current_x = current.current_x;
					next.current_y = current.current_y;
					next.target_x = current.target_x;
					next.target_y = current.target_y;
					next.drawScale = 1.0f;
					next.targetDrawScale = current.targetDrawScale;
					next.angle = current.angle;
					next.targetAngle = current.targetAngle;
					next.superFlash(Color.WHITE.cpy());
					AbstractDungeon.player.hand.group.set(i, next);
					AbstractDungeon.player.hand.glowCheck();
				}
				else // Card is already at the highest level
				{
					current.upgrade();
				}
			}
		}
	}
	
	public void lvlUpNoExhaust()
	{
		AbstractCard ad = nextLevel();
		if (ad != null) {
			addToBot(new MakeTempCardInDiscardAction(ad, 1));
		}
	}
}
