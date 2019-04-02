package defaultmod.characters;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.*;

import defaultmod.DefaultMod;
import defaultmod.cards.*;
import defaultmod.relics.MillenniumPuzzle;

//Copied from The Animator, then modified
public class DuelistCustomLoadout
{
	public int ID;
	public String Name;
	public String cardCount;
	public int StartingGold;
	public boolean Locked;
	private static int index;
	protected String lockedDescription;
	protected String description;
	protected int unlockLevel;

	public void Refresh(int currentLevel, CharacterSelectScreen selectScreen, CharacterOption option)
	{
		DefaultMod.deckIndex = index;
		Locked = unlockLevel > currentLevel;
		
		if (Locked)
		{
			lockedDescription = "Unlocks at " + unlockLevel +  " Total Score (" + currentLevel +  ")";
		}
		
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DefaultMod.duelistDefaults);
			config.setInt(DefaultMod.PROP_DECK, DefaultMod.deckIndex);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DefaultMod.setSelectColorTxtB.text = DefaultMod.startingDecks.get(DefaultMod.deckIndex);
		
		//DefaultMod.resetCharSelect();
		
		/*
        try
        {
            if (goldField == null)
            {
                goldField = CharacterOption.class.getDeclaredField("gold");
                goldField.setAccessible(true);
            }
            defaultmod.Utilities.Logger.info("Gold Field: " + (goldField != null) + ", " + this.Name + ", " + this.StartingGold + ", Option: " + (option != null));
            goldField.set(option, this.StartingGold);
        }
        catch (NoSuchFieldException | IllegalAccessException ex)
        {
            ex.printStackTrace();
        }

        selectScreen.bgCharImg = AnimatorResources.GetCharacterPortrait(ID);
        Locked = unlockLevel > currentLevel;
        if (Locked)
        {
            lockedDescription =
                    AnimatorCharacterSelect.uiText[2] + unlockLevel +
                            AnimatorCharacterSelect.uiText[3] + currentLevel +
                            AnimatorCharacterSelect.uiText[4];
        }
		 */
	}

	public String GetDescription()
	{
		if (Locked)
		{
			return lockedDescription;
		}
		else
		{
			return description;
		}
	}

	public ArrayList<String> GetStartingRelics()
	{
		ArrayList<String> res = new ArrayList<>();
		res.add(MillenniumPuzzle.ID);
		return res;
	}

	public ArrayList<String> GetStartingDeck()
	{
		ArrayList<String> res = new ArrayList<>();
		res.add(SevenColoredFish.ID);
		res.add(SevenColoredFish.ID);
		res.add(GiantSoldier.ID);
		res.add(GiantSoldier.ID);
		res.add(CastleWalls.ID);
		res.add(CastleWalls.ID);
		res.add(ScrapFactory.ID);
		res.add(Ookazi.ID);
		res.add(Ookazi.ID);
		res.add(SummonedSkull.ID);
		return res;
	}

	public CharSelectInfo GetLoadout(String name, String description, TheDuelist duelistCharacter)
	{
		return new CharSelectInfo(name, description, DefaultMod.maxHP, DefaultMod.maxHP, DefaultMod.orbSlots, DefaultMod.startGold, DefaultMod.cardDraw, duelistCharacter,
				GetStartingRelics(), GetStartingDeck(), false);
	}
	
	public void setIndex(int ind)
	{
		index = ind;
	}
	
	public int getLoadoutIndex()
	{
		return index;
	}
}
