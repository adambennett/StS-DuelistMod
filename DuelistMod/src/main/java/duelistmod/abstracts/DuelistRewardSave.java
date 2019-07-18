package duelistmod.abstracts;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rewards.RewardSave;

public class DuelistRewardSave extends RewardSave 
{
	public ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
	public String packName;
	public String img;
	
	public DuelistRewardSave(String type, String id, String pack, ArrayList<AbstractCard> cards) 
	{
		super(type, id);
		this.packName = pack;
		this.cards = cards;
	}
}
