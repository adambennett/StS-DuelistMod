package duelistmod.characters;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.*;

import duelistmod.DuelistMod;
import duelistmod.cards.*;
import duelistmod.relics.MillenniumPuzzle;

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
		DuelistMod.deckIndex = index;
		Locked = unlockLevel > currentLevel;
		
		if (Locked)
		{
			lockedDescription = "Unlocks at " + unlockLevel +  " Total Score (" + currentLevel +  ")";
		}
		
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_DECK, DuelistMod.deckIndex);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DuelistMod.setSelectColorTxtB.text = DuelistMod.startingDecks.get(DuelistMod.deckIndex);
		if (DuelistMod.deckIndex == 1) { selectScreen.bgCharImg = DuelistMod.GetCharacterPortrait(1); }
		else { selectScreen.bgCharImg = DuelistMod.GetCharacterPortrait(0); }
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
		return new CharSelectInfo(name, description, DuelistMod.maxHP, DuelistMod.maxHP, DuelistMod.orbSlots, DuelistMod.startGold, DuelistMod.cardDraw, duelistCharacter,
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
