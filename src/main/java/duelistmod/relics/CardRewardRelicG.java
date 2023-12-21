package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.Util;
import duelistmod.interfaces.CardRewardRelic;
import duelistmod.variables.Tags;

public class CardRewardRelicG extends DuelistRelic implements CardRewardRelic
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("CardRewardRelicG");
    public static final String IMG = DuelistMod.makeRelicPath("TrickstoneRelic.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("BookStone_Outline.png");
    // /FIELDS

    public CardRewardRelicG() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL); }
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		// Only spawn for non-Duelist characters
		return !Util.hasCardRewardRelic();
	}
    
    @Override
    public void onEquip()
    {
    	CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		ArrayList<AbstractCard> myCardsCopy = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.hasTag(Tags.TRAP) && c.rarity != CardRarity.BASIC && c.rarity != CardRarity.SPECIAL) { myCardsCopy.add(c.makeCopy()); }
		}
		for (AbstractCard c : myCardsCopy)
		{
			group.addToBottom(c);
		}
		group.sortAlphabetically(true);
		DuelistMod.duelistCardSelectScreen.open(true, group, 1, "Select a Trap to add to your deck", this::confirmLogic);
    }

	private void confirmLogic(List<AbstractCard> selectedCards) {
		if (!selectedCards.isEmpty()) {
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(selectedCards.get(0).makeCopy(), (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
		}
	}

    @Override
    public int getPrice()
    {
    	return 250;
    }
   
}
