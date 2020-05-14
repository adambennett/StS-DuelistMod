package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;import duelistmod.characters.TheDuelist;
import duelistmod.variables.*;

public class RandomTributeMonsterRelic extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("RandomTributeMonsterRelic");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	public boolean cardSelected = false;
	public DuelistCard cardToAdd = null;

	public RandomTributeMonsterRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public void onEquip()
	{
		ArrayList<DuelistCard> list = new ArrayList<>();
		CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dc = (DuelistCard)c;
				if (dc.tributes > 0 && !c.hasTag(Tags.NEVER_GENERATE) && dc.hasTag(Tags.MONSTER) && dc.rarity != CardRarity.BASIC && dc.rarity != CardRarity.SPECIAL)
				{
					list.add((DuelistCard) c.makeStatEquivalentCopy());
				}
			}			
		}
		if (list.size() < 5)
		{
			ArrayList<DuelistCard> temp = new ArrayList<>();
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c instanceof DuelistCard)
				{
					DuelistCard dc = (DuelistCard)c;
					if (dc.tributes > 0 && !c.hasTag(Tags.NEVER_GENERATE) && dc.hasTag(Tags.MONSTER) && dc.rarity != CardRarity.BASIC && dc.rarity != CardRarity.SPECIAL)
					{
						temp.add((DuelistCard) c.makeStatEquivalentCopy());
					}
				}		
			}
			
			while (list.size() < 5 && temp.size() > 0)
			{
				list.add(temp.remove(AbstractDungeon.cardRandomRng.random(temp.size() - 1)));
			}
		}
		
		if (list.size() >= 5)
		{
			while (list.size() > 5)
			{
				list.remove(AbstractDungeon.cardRandomRng.random(list.size() - 1));
			}
			
			for (DuelistCard c : list)
			{
				c.modifyTributesPerm(-c.tributes + 1);
			}
			
			for (DuelistCard c : list)
			{
				group.addToBottom(c);
			}
			group.sortAlphabetically(true);
			AbstractDungeon.gridSelectScreen.open(group, 1, "Select a Tribute monster to add to your deck.", false);
		}
	}
	
	
	@Override
	public void update() 
	{
		super.update();
		if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) 
		{
			cardSelected = true;
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeCopy(), (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
	}
	

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new RandomTributeMonsterRelic();
	}
}
