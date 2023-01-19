package duelistmod.characters;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.*;

import duelistmod.DuelistMod;
import duelistmod.cards.*;
import duelistmod.cards.pools.aqua.SevenColoredFish;
import duelistmod.cards.pools.machine.ScrapFactory;
import duelistmod.characters.Loadouts.A1Deck;
import duelistmod.characters.Loadouts.A2Deck;
import duelistmod.characters.Loadouts.RandomDeckMetronome;
import duelistmod.characters.Loadouts.RandomDeckUpgrade;
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
	public boolean longDesc = false;
	private static int index;
	protected String lockedDescription;
	protected String description;
	public String unlockReq = "";
	public String deckDesc = "";
	protected String permaLockMessage = "Locked";
	protected int unlockLevel;

	public void Refresh(int currentLevel, CharacterSelectScreen selectScreen)
	{
		DuelistMod.deckIndex = index;
		DuelistMod.normalSelectDeck = index;
		Locked = (unlockLevel > currentLevel && !DuelistMod.unlockAllDecks) || permaLocked;
		if (Locked && unlockLevel <= 0 && !permaLocked) {
			Locked = false;
		}

		if (Locked && permaLocked && DuelistMod.unlockAllDecks) {
			if (this instanceof A1Deck || this instanceof A2Deck || this instanceof RandomDeckUpgrade || this instanceof RandomDeckMetronome) {
				Locked = false;
				permaLocked = false;
			}
		}

		if (permaLocked) {
			lockedDescription = permaLockMessage;
		} else if (Locked) {
			lockedDescription = "Unlocks at " + unlockLevel +  " Total Score (" + currentLevel +  ")";
		}

		if (selectScreen != null) {
			switch (DuelistMod.deckIndex) {
				case 0: // Standard
				case 1: // Dragon
				case 2: // Naturia
				case 3: // Spellcaster
				case 4: // Toon
				case 5: // Zombie
				case 6: // Aqua
				case 7: // Fiend
				case 8: // Machine
				case 9: // Warrior
				case 10: // Insect
				case 11: // Plant
				case 13: // Megatype
				case 16: // Ojama
				case 17: // Exodia
					selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(DuelistMod.deckIndex);
					break;
				default:
					selectScreen.bgCharImg = TheDuelist.GetCharacterPortrait(0);
			}
		}
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
