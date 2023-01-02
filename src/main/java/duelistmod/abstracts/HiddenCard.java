package duelistmod.abstracts;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;

public abstract class HiddenCard extends DuelistCard
{
	public HiddenCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR,CardRarity RARITY, CardTarget TARGET) 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		CommonKeywordIconsField.useIcons.set(this, false);
	}
}
