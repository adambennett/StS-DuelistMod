package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.LostGuardian;
import duelistmod.cards.pools.dragons.LivingFossil;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.*;
import duelistmod.ui.DuelistCardSelectScreen;
import duelistmod.variables.Strings;

public class MillenniumToken extends DuelistRelic {

	/*
	 * 
	 * Add any Duelist card to hand on pickup, and in TokenCard this relic makes all tokens cost 0
	 * 
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumToken");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	public boolean cardSelected = false;
	private DuelistCardSelectScreen dcss;

	public MillenniumToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
		this.dcss = new DuelistCardSelectScreen(true);
	}
	
	@Override
	public int getPrice()
	{
		return 300;
	}
	
	@Override
	public void onEquip() 
	{
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
		List<AbstractCard> list = myCardsCopy;
		for (AbstractCard c : list){
			if (c.rarity != CardRarity.BASIC && c.rarity != CardRarity.SPECIAL) 
			{
				group.addToBottom(c);
			}
		}
		group.sortAlphabetically(true);
		AbstractDungeon.gridSelectScreen = this.dcss;
		DuelistMod.wasViewingSelectScreen = true;
		((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(group, 1, "Select a card to add to your deck");
	}
	

	@Override
	public void update() 
	{
		super.update();
		if (!cardSelected && !this.dcss.selectedCards.isEmpty()) 
		{
			cardSelected = true;
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.dcss.selectedCards.get(0).makeCopy(), (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
			this.dcss.selectedCards.clear();
			AbstractDungeon.closeCurrentScreen();
			//AbstractDungeon.gridSelectScreen = new GridCardSelectScreen();
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
		return new MillenniumToken();
	}
}
