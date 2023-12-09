package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.*;

public class OrbCardRelic extends DuelistRelic
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("OrbCardRelic");
    public static final String IMG = DuelistMod.makeRelicPath("SpellcasterRelic.png");
    public static final String OUTLINE = DuelistMod.makeRelicPath("SpellcasterRelic.png");
	public boolean screenOpen = false;
    // /FIELDS

    public OrbCardRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
		this.setDescription();
	}
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return Util.deckIs("Spellcaster Deck");
	}
    
    @Override
    public void onEquip()
    {
    	CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    	ArrayList<AbstractCard> randomOrbs = new ArrayList<AbstractCard>();
    	ArrayList<String> randomOrbNames = new ArrayList<String>();
    	ArrayList<AbstractCard> orbsToChooseFrom = DuelistCardLibrary.orbCardsForGeneration();
    	for (int i = 0; i < 4; i++)
    	{
    		AbstractCard randOrb = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1)).makeCopy();
    		while (randomOrbNames.contains(randOrb.name)) { randOrb = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1)); }
    		randomOrbs.add(randOrb.makeCopy()); randomOrbNames.add(randOrb.name);
    	}
		for (AbstractCard c : randomOrbs) { group.addToBottom(c.makeCopy()); }
		group.sortAlphabetically(true);
		if (AbstractDungeon.isScreenUp) {
			AbstractDungeon.dynamicBanner.hide();
			AbstractDungeon.overlayMenu.cancelButton.hide();
			AbstractDungeon.previousScreen = AbstractDungeon.screen;
		}
		AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
		this.screenOpen = true;
		DuelistMod.shopScreenIgnorePurgeUpdates = true;
		SelectScreenHelper.open(group, 1, "Select an Orb Card to add to your deck");
    }
    
    @Override
	public void update() 
	{
		super.update();
		if (this.screenOpen && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
		{
			this.screenOpen = false;
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeCopy(), (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
			DuelistMod.shopScreenIgnorePurgeUpdates = false;
		}
	}

	public void setDescription()
	{
		description = getUpdatedDescription();
		tips.clear();
		tips.add(new PowerTip(name, description));
		tips.add(new PowerTip("Orb Card", "0-cost #ySpell that #yChannels an orb."));
		initializeTips();
	}
    
    @Override
    public int getPrice()
    {
    	return 100;
    }
   
}
