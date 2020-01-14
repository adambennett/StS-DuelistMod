package duelistmod.abstracts;

import java.util.UUID;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class MapCardItem 
{
	public AbstractCard card;
	public UUID id;
	
	public MapCardItem(AbstractCard card) {
		this.card = card;
		this.id = card.uuid;
	}
}
