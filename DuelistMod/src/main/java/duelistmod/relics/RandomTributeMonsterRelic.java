package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

public class RandomTributeMonsterRelic extends CustomRelic {

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
    	CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		ArrayList<AbstractCard> multiTributeMonsters = new ArrayList<AbstractCard>();
		ArrayList<AbstractCard> tributeMonsters = new ArrayList<AbstractCard>();
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (!c.hasTag(Tags.NEVER_GENERATE) && c.hasTag(Tags.MONSTER) && c.tributes > 1 && c.tributes < 6 && c.rarity != CardRarity.BASIC && c.rarity != CardRarity.SPECIAL) { multiTributeMonsters.add(c.makeCopy()); }
			if (!c.hasTag(Tags.NEVER_GENERATE) && c.hasTag(Tags.MONSTER) && c.tributes > 0 && c.rarity != CardRarity.BASIC && c.rarity != CardRarity.SPECIAL) { tributeMonsters.add(c.makeCopy()); } 
		}
		List<AbstractCard> tributeList = tributeMonsters;
		List<AbstractCard> multiTributeList = multiTributeMonsters;
		List<AbstractCard> list = new ArrayList<AbstractCard>();
		ArrayList<String> currentChoices = new ArrayList<String>();
		for (int i = 0; i < 4; i++)
		{
			DuelistCard rand = (DuelistCard) multiTributeList.get(AbstractDungeon.relicRng.random(multiTributeList.size() - 1));
			while (currentChoices.contains(rand.originalName)) { rand = (DuelistCard) multiTributeList.get(AbstractDungeon.relicRng.random(multiTributeList.size() - 1)); }
			rand.setTributes(1);
			currentChoices.add(rand.originalName);
			list.add(rand);
		}
		
		DuelistCard rand = (DuelistCard) tributeList.get(AbstractDungeon.relicRng.random(tributeList.size() - 1));
		while (currentChoices.contains(rand.originalName)) { rand = (DuelistCard) tributeList.get(AbstractDungeon.relicRng.random(tributeList.size() - 1)); }
		rand.setTributes(1);
		currentChoices.add(rand.originalName);
		list.add(rand);
		
		for (AbstractCard c : list)
		{
			group.addToBottom(c);
		}
		group.sortAlphabetically(true);
		AbstractDungeon.gridSelectScreen.open(group, 1, "Select a Tribute monster to add to your deck.", false);
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
