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
import duelistmod.abstracts.DuelistRelic;
import duelistmod.cards.*;
import duelistmod.cards.pools.dragons.LivingFossil;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.*;
import duelistmod.variables.Strings;

public class MillenniumToken extends DuelistRelic {

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumToken");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	public boolean cardSelected = false;

	public MillenniumToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public int getPrice()
	{
		return 300;
	}
	
	@Override
	public void onEquip() 
	{
		if (AbstractDungeon.isScreenUp) {
			AbstractDungeon.dynamicBanner.hide();
			AbstractDungeon.overlayMenu.cancelButton.hide();
			AbstractDungeon.previousScreen = AbstractDungeon.screen;
		}
		CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		ArrayList<AbstractCard> myCardsCopy = new ArrayList<AbstractCard>();
		Map<String, AbstractCard> mapp = new HashMap<>();
		myCardsCopy.add(new GreenGadget());
		myCardsCopy.add(new RedGadget());
		myCardsCopy.add(new YellowGadget());
		myCardsCopy.add(new AncientGearChimera());
		myCardsCopy.add(new TurretWarrior());
		myCardsCopy.add(new TokenVacuum());
		myCardsCopy.add(new Linkuriboh());
		myCardsCopy.add(new RainbowBridge());
		myCardsCopy.add(new ClosedForest());
		myCardsCopy.add(new ChrysalisMole());
		myCardsCopy.add(new LivingFossil());
		myCardsCopy.add(new LostGuardian());
		for (AbstractCard c : myCardsCopy) { mapp.put(c.cardID, c); }
		while (myCardsCopy.size() < 20)
		{
			AbstractCard c = DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1));
			while (mapp.containsKey(c.cardID) || c.rarity == CardRarity.BASIC || c.rarity == CardRarity.SPECIAL) { c = DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1)); }
			myCardsCopy.add(c.makeCopy());
			mapp.put(c.cardID, c.makeCopy());
		}
		for (AbstractCard c : myCardsCopy){
			if (c.rarity != CardRarity.BASIC && c.rarity != CardRarity.SPECIAL) 
			{
				group.addToBottom(c);
			}
		}
		group.sortAlphabetically(true);
		DuelistMod.duelistCardSelectScreen.open(true, group, 1, "Select a card to add to your deck", (selectedCards) -> {
			if (selectedCards.size() > 0) {
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(selectedCards.get(0).makeCopy(), (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
			}
		});
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumToken();
	}
}
