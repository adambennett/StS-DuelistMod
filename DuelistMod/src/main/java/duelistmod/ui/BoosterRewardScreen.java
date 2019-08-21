package duelistmod.ui;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

public class BoosterRewardScreen extends CardRewardScreen
{
	@Override
	public void open(final ArrayList<AbstractCard> cards, final RewardItem rItem, final String header) 
	{
		super.open(cards, rItem, header);
	}
}
