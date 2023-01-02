package duelistmod.relics;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.BaseGameHelper;
import duelistmod.ui.DuelistCardSelectScreen;

public class CardRewardRelicI extends DuelistRelic
{
	public static final String ID = DuelistMod.makeID("CardRewardRelicI");
    public static final String IMG = DuelistMod.makeRelicPath("BaseGameRelic.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("BookStone_Outline.png");
    public boolean cardSelected = false;
    private int colorIndex = -1;

    public CardRewardRelicI() 
    { 
	    super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	    setDescription(); 
    }
    @Override public String getUpdatedDescription() 
    {
    	if (this.counter > -1) { return this.DESCRIPTIONS[this.counter]; }
    	else { return this.DESCRIPTIONS[19]; }
    }

    @Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return !DuelistMod.hasCardRewardRelic;
	}
    
    public int getRandomColors()
    {
    	return ThreadLocalRandom.current().nextInt(0, 19);
    }
    
    public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
    
    private String getColorName()
    {
    	switch (this.colorIndex)
    	{
	    	case 0:
	    		return "Ironclad";
	    	case 1:
	    		return "Defect";
	    	case 2:
	    		return "Silent";
	    	case 3:
	    		return "Watcher";
	    	case 4:
	    		return "Ironclad or Defect";
	    	case 5:
	    		return "Ironclad or Silent";
	    	case 6:
	    		return "Ironclad or Watcher";
	    	case 7:
	    		return "Silent or Defect";
	    	case 8:
	    		return "Watcher or Defect";
	    	case 9:
	    		return "Silent or Watcher";
	    	case 10:
	    		return "Colorless";
	    	case 11:
	    		return "Ironclad or Colorless";
	    	case 12:
	    		return "Silent or Colorless";
	    	case 13:
	    		return "Defect or Colorless";
	    	case 14:
	    		return "Watcher or Colorless";
	    	case 15:
	    		return "Ironclad";
	    	case 16:
	    		return "Defect";
	    	case 17:
	    		return "Silent";
	    	case 18:
	    		return "Watcher";
	    	default:
	    		return "Ironclad or Silent or Defect or Watcher or Colorless";
    	}
    }
    
    private ArrayList<AbstractCard> getColorCards()
    {
    	ArrayList<AbstractCard> all = new ArrayList<AbstractCard>();
    	if (this.colorIndex < 0) {
			this.colorIndex = getRandomColors();
			this.setCounter(colorIndex);
			setDescription();
		}
    	switch (this.colorIndex)
    	{
	    	case 0:
			case 15:
				return BaseGameHelper.getAllIroncladCards();
	    	case 1:
			case 16:
				return BaseGameHelper.getAllDefectCards();
	    	case 2:
			case 17:
				return BaseGameHelper.getAllSilentCards();
	    	case 3:
			case 18:
				return BaseGameHelper.getAllWatcherCards();
	    	case 4:
				all.addAll(BaseGameHelper.getAllIroncladCards());
	    		all.addAll(BaseGameHelper.getAllDefectCards());
	    		return all;
	    	case 5:
	    		all.addAll(BaseGameHelper.getAllIroncladCards());
	    		all.addAll(BaseGameHelper.getAllSilentCards());
	    		return all;
	    	case 6:
	    		all.addAll(BaseGameHelper.getAllIroncladCards());
	    		all.addAll(BaseGameHelper.getAllWatcherCards());
	    		return all;
	    	case 7:
	    		all.addAll(BaseGameHelper.getAllSilentCards());
	    		all.addAll(BaseGameHelper.getAllDefectCards());
	    		return all;
	    	case 8:
	    		all.addAll(BaseGameHelper.getAllWatcherCards());
	    		all.addAll(BaseGameHelper.getAllDefectCards());
	    		return all;
	    	case 9:
	    		all.addAll(BaseGameHelper.getAllSilentCards());
	    		all.addAll(BaseGameHelper.getAllWatcherCards());
	    		return all;
	    	case 10:
	    		all.addAll(BaseGameHelper.getAllColorlessCards());
	    		return all;
	    	case 11:
	    		all.addAll(BaseGameHelper.getAllIroncladCards());
	    		all.addAll(BaseGameHelper.getAllColorlessCards());
	    		return all;
	    	case 12:
	    		all.addAll(BaseGameHelper.getAllSilentCards());
	    		all.addAll(BaseGameHelper.getAllColorlessCards());
	    		return all;
	    	case 13:
	    		all.addAll(BaseGameHelper.getAllDefectCards());
	    		all.addAll(BaseGameHelper.getAllColorlessCards());
	    		return all;
	     	case 14:
	    		all.addAll(BaseGameHelper.getAllWatcherCards());
	    		all.addAll(BaseGameHelper.getAllColorlessCards());
	    		return all;
			default:
	    		all.addAll(BaseGameHelper.getAllIroncladCards());
	    		all.addAll(BaseGameHelper.getAllSilentCards());
	    		all.addAll(BaseGameHelper.getAllDefectCards());
	    		all.addAll(BaseGameHelper.getAllWatcherCards());
	    		all.addAll(BaseGameHelper.getAllColorlessCards());
	    		return all;
    	}
    }
    
    @Override
    public void onEquip()
    {
    	DuelistMod.hasCardRewardRelic = true;
    	
    	CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		List<AbstractCard> list = new ArrayList<>(getColorCards());
		for (AbstractCard c : list)
		{
			group.addToBottom(c);
			UnlockTracker.unlockCard(c.cardID);
		}

		DuelistMod.duelistCardSelectScreen.open(true, group, 1, "Select any " + getColorName() + " card to add to your deck", (selectedCards) -> {
			if (selectedCards.size() > 0) {
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(selectedCards.get(0).makeCopy(), (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
			}
		});

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
    public int getPrice()
    {
    	return 250;
    }
   
}
