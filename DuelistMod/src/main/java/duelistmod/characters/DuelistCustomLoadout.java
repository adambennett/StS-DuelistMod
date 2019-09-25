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
	public boolean permaLocked = false;
	private static int index;
	protected String lockedDescription;
	protected String description;
	protected String permaLockMessage = "Locked";
	protected int unlockLevel;

	public void Refresh(int currentLevel, CharacterSelectScreen selectScreen, CharacterOption option)
	{
		DuelistMod.deckIndex = index;
		DuelistMod.normalSelectDeck = index;
		Locked = unlockLevel > currentLevel || permaLocked;
		
		if (Locked)
		{
			lockedDescription = DuelistMod.deckUnlockString + unlockLevel +  DuelistMod.deckUnlockStringB + currentLevel +  ")";
		}
		
		if (permaLocked && Locked)
		{
			lockedDescription = permaLockMessage;			
		}
		
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_DECK, DuelistMod.deckIndex);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//DuelistMod.setSelectColorTxtB.text = DuelistMod.startingDecks.get(DuelistMod.deckIndex);
		
		// Standard
		if (DuelistMod.deckIndex == 0) { selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex); }
		
		// Dragon
		if (DuelistMod.deckIndex == 1) { selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex); }
		
		// Nature
		else if (DuelistMod.deckIndex == 2) { selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex); }
		
		// Spellcaster
		else if (DuelistMod.deckIndex == 3) { selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex); }
		
		// Toon
		else if (DuelistMod.deckIndex == 4) { selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex); }
		
		// Zombie
		else if (DuelistMod.deckIndex == 5) { selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex); }
		
		// Aqua
		else if (DuelistMod.deckIndex == 6) { selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex); }
		
		// Fiend
		else if (DuelistMod.deckIndex == 7) {  selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex); }
		
		// Machine
		else if (DuelistMod.deckIndex == 8) { selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex); }
		
		// Superheavy
		else if (DuelistMod.deckIndex == 9) {  selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex); }
		
		// Insect
		else if (DuelistMod.deckIndex == 10) {  selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(2); }
				
		// Plant
		else if (DuelistMod.deckIndex == 11) {  selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(2); }

		else { selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(0); }
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
		res.add(PowerWall.ID);
		res.add(PowerWall.ID);
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
