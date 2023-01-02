package duelistmod.abstracts;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class UniZombCard extends DuelistCard
{
	public UniZombCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR,CardRarity RARITY, CardTarget TARGET) 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		CommonKeywordIconsField.useIcons.set(this, false);
	}
	
	public void activate(AbstractMonster target) { }

}
