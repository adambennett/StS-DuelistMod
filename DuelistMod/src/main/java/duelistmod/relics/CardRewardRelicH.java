package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

public class CardRewardRelicH extends CustomRelic
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("CardRewardRelicH");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
    public boolean cardSelected = false;
    // /FIELDS

    public CardRewardRelicH() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL); }
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
	public boolean canSpawn()
	{
		// Only spawn for non-Duelist characters
		if (DuelistMod.hasCardRewardRelic) { return false; }
		else { return true; }
	}
    
    @Override
    public void onEquip()
    {
    	DuelistMod.hasCardRewardRelic = true;
    	
    	CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		ArrayList<AbstractCard> myCardsCopy = new ArrayList<AbstractCard>();
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (c.hasTag(Tags.MONSTER) && c.rarity != CardRarity.BASIC && c.rarity != CardRarity.SPECIAL) { myCardsCopy.add(c.makeCopy()); }
		}
		List<AbstractCard> list = myCardsCopy;
		for (AbstractCard c : list)
		{
			group.addToBottom(c);
		}
		group.sortAlphabetically(true);
		AbstractDungeon.gridSelectScreen.open(group, 1, "Select a Monster to add to your deck", false);
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
   
}
