package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.ui.DuelistCardSelectScreen;
import duelistmod.variables.Tags;

public class CardRewardRelicG extends DuelistRelic
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("CardRewardRelicG");
    public static final String IMG = DuelistMod.makeRelicPath("TrickstoneRelic.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("BookStone_Outline.png");
    public boolean cardSelected = false;
	private DuelistCardSelectScreen dcss;
    // /FIELDS

    public CardRewardRelicG() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL); this.dcss = new DuelistCardSelectScreen(true); }
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
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.hasTag(Tags.TRAP) && c.rarity != CardRarity.BASIC && c.rarity != CardRarity.SPECIAL) { myCardsCopy.add(c.makeCopy()); }
		}
		List<AbstractCard> list = myCardsCopy;
		for (AbstractCard c : list)
		{
			group.addToBottom(c);
		}
		group.sortAlphabetically(true);
		
		AbstractDungeon.gridSelectScreen = this.dcss;
		DuelistMod.wasViewingSelectScreen = true;
		((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(group, 1, "Select a Trap to add to your deck");
		
        try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setBool(DuelistMod.PROP_CARD_REWARD_RELIC, DuelistMod.hasCardRewardRelic);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
    }
    

    @Override
    public void onUnequip()
    {
        DuelistMod.hasCardRewardRelic = false;        
        try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setBool(DuelistMod.PROP_CARD_REWARD_RELIC, DuelistMod.hasCardRewardRelic);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
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
    
    @Override
    public int getPrice()
    {
    	return 250;
    }
   
}
