package duelistmod.actions.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomCard;

public class SpecificCardDiscardToDeckAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
{
	private static final com.megacrit.cardcrawl.localization.UIStrings uiStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getUIString("DiscardPileToTopOfDeckAction");
	public static final String[] TEXT = uiStrings.TEXT;
	private AbstractPlayer p;
	private CustomCard c;

	public SpecificCardDiscardToDeckAction(AbstractCreature source, CustomCard axe) {
		this.p = AbstractDungeon.player;
		setValues(null, source, this.amount);
		this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FASTER;
		this.c = axe;
	}

	@Override
	public void update()
	{
		if (AbstractDungeon.getCurrRoom().isBattleEnding()) { this.isDone = true; return; }
		this.p.discardPile.removeCard(this.c);
		this.p.discardPile.moveToDeck(this.c, false);
		this.isDone = true; return;
	}
}