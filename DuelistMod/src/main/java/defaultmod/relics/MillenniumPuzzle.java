package defaultmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.actions.unique.TheCreatorAction;
import defaultmod.cards.*;
import defaultmod.patches.DuelistCard;
import defaultmod.powers.*;

public class MillenniumPuzzle extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DefaultMod.makeID("MillenniumPuzzle");
	public static final String IMG = DefaultMod.makePath(DefaultMod.M_PUZZLE_RELC);
	public static final String OUTLINE = DefaultMod.makePath(DefaultMod.M_PUZZLE_RELIC_OUTLINE);
	private static int SUMMONS = 2;

	public MillenniumPuzzle() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
	}

	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
		this.flash();
		
		// Normal Runs
		if (AbstractPlayer.customMods.size() < 1 && !DefaultMod.challengeMode) { runSpecialEffect(); }
		
		// Custom Runs & No Challenge Mode
		else if (!DefaultMod.challengeMode)
		{
			DuelistCard.powerSummon(AbstractDungeon.player, 2, "Puzzle Token", false);
		}
		
		// Challenge Mode (anywhere)
		else
		{
			DuelistCard.summon(AbstractDungeon.player, 2, new ExplosiveToken("Exploding Token"));
		}
	}

	@Override
	public void atTurnStart()
	{
		
	}

	// Description
	@Override
	public String getUpdatedDescription() 
	{
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumPuzzle();
	}

	private static void runSpecialEffect()
	{
		AbstractPlayer p = AbstractDungeon.player;
		switch (DefaultMod.getCurrentDeck().getIndex())
		{
			// Standard Deck
			case 0:
				DuelistCard.powerSummon(AbstractDungeon.player, 3, "Puzzle Token", false);
				DuelistCard.applyPowerToSelf(new BlurPower(p, 1));
				DuelistCard.staticBlock(10);
				break;
	
			// Dragon Deck
			case 1:
				int floor = AbstractDungeon.actNum;
				DuelistCard.applyPowerToSelf(new StrengthPower(p, floor));
				DuelistCard.powerSummon(AbstractDungeon.player, 1, "Puzzle Token", false);
				break;
	
			// Nature Deck
			case 2:
				DuelistCard.applyPowerToSelf(new NaturePower(p, p, 1));
				int rollN = AbstractDungeon.cardRandomRng.random(1, 6);
		    	switch (rollN)
		    	{
			    	case 1:
			    		DuelistCard.powerSummon(AbstractDungeon.player, 1, "Plant Token", false);
			    		break;
			    	case 2:
			    		DuelistCard.powerSummon(AbstractDungeon.player, 1, "Insect Token", false);
			    		break;
			    	case 3:
			    		DuelistCard.powerSummon(AbstractDungeon.player, 1, "Predaplant Token", false);
			    		break;
			    	case 4:
			    		DuelistCard.powerSummon(AbstractDungeon.player, 2, "Plant Token", false);
			    		break;
			    	case 5:
			    		DuelistCard.powerSummon(AbstractDungeon.player, 2, "Insect Token", false);
			    		break;
			    	case 6:
			    		DuelistCard.powerSummon(AbstractDungeon.player, 2, "Predaplant Token", false);			    		
			    		break;
			    	default:
			    		DuelistCard.powerSummon(AbstractDungeon.player, 1, "Insect Token", false);
			    		break;
		    	
				}
				
				break;
	
			// Spellcaster Deck
			case 3:
				int floorB = AbstractDungeon.actNum;
				for (int i = 0; i < 1 + floorB; i++)
				{
					DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.SPELLCASTER);
					AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
				}
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS, "Puzzle Token", false);
				break;
	
			// Creator Deck
			case 4:
				int roll = AbstractDungeon.cardRandomRng.random(1, 9);
				if (roll == 1)
				{
					DuelistCard jinzo = new Jinzo();
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(15);
				}
				else if (roll == 2)
				{
					DuelistCard jinzo = new TrapHole();
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(10);
				}
				else if (roll == 3)
				{
					DuelistCard jinzo = new Jinzo();
					jinzo.upgrade();
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(5);
				}
				else if (roll == 4)
				{
					DuelistCard jinzo = new TrapHole();
					jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
					if (jinzo.costForTurn != 3) { jinzo.isCostModifiedForTurn = true; }
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(15);
				}
				else if (roll == 5)
				{
					DuelistCard jinzo = new Jinzo();
					jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
					if (jinzo.costForTurn != 3) { jinzo.isCostModifiedForTurn = true; }
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(10);
				}
				else if (roll == 6)
				{
					DuelistCard jinzo = new Jinzo();
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(5);
				}
				else if (roll == 7)
				{
					DuelistCard jinzo = new TrapHole();
					jinzo.upgrade();
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(20);
				}
				else if (roll == 8)
				{
					DuelistCard jinzo = new UltimateOffering();
					jinzo.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(jinzo);
					DuelistCard.staticBlock(15);
				}
				else if (roll == 9)
				{
					DuelistCard jinzo = new Jinzo();
					DuelistCard trap = new Jinzo();
					DuelistCard ultimate = new Jinzo();
					jinzo.costForTurn = 0; trap.costForTurn = 0; ultimate.costForTurn = 0;
					jinzo.isCostModifiedForTurn = true; trap.isCostModifiedForTurn = true; ultimate.isCostModifiedForTurn = true;
					DuelistCard.addCardToHand(jinzo); DuelistCard.addCardToHand(trap); DuelistCard.addCardToHand(ultimate);
					DuelistCard.staticBlock(25);
				}
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS, "Puzzle Token", false);
				break;
	
			// Random (Small) Deck
			case 5:
				// Go through deck, count up total cost of all cards
				// Add a card to deck that deals damage equal to that number for 2 mana and exhausts
				int summonRollA = AbstractDungeon.cardRandomRng.random(2, 5);
				DuelistCard.powerSummon(AbstractDungeon.player, summonRollA, "Puzzle Token", false);
				break;
	
			// Random (Big) Deck
			case 6:
				// Go through deck, count up total amount of tributes on all cards
				// Add a card that gives a random buff with that much turnNum
				// Write separate randomBuff function that only lets it choose powers that can handle many turnNum
				int summonRollB = AbstractDungeon.cardRandomRng.random(2, 5);
				DuelistCard.powerSummon(AbstractDungeon.player, summonRollB, "Puzzle Token", false);
				break;
	
			// Toon Deck
			case 7:
				if (!DefaultMod.toonBtnBool)
				{
					int floorC = AbstractDungeon.actNum;
					for (int i = 0; i < 1 + floorC; i++)
					{
						DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.TOON);
						AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
					}
				}
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS, "Puzzle Token", false);
				break;
	
			// Orb Deck
			case 8:
				new Token().openRandomOrbChoiceNoGlass(3);
				DuelistCard.powerSummon(AbstractDungeon.player, 1, "Orb Token", false);
				break;
	
			// Resummon Deck
			case 9:
				if (DefaultMod.challengeMode)
				{
					DuelistCard.damageSelf(DefaultMod.resummonDeckDamage);
					DefaultMod.resummonDeckDamage++;
					try {
						SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DefaultMod.duelistDefaults);
						config.setInt(DefaultMod.PROP_RESUMMON_DMG, DefaultMod.resummonDeckDamage);
						config.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else
				{
					int rollR = AbstractDungeon.cardRandomRng.random(1, 2);
					if (rollR == 1)
					{
						DuelistCard.powerSummon(AbstractDungeon.player, 1, "Resummon Token", false);
					}
				}
				break;
	
			// Generation Deck
			case 10:
				int floorE = AbstractDungeon.actNum;
				for (int i = 0; i < 1 + floorE; i++)
				{
					DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomDuelistCard();
					AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
				}
				DuelistCard.powerSummon(AbstractDungeon.player, 5, "Bonanza Token", false);
				break;
	
			// Ojama Deck
			case 11:
				int floorD = AbstractDungeon.actNum;
				for (int i = 0; i < 1 + floorD; i++)
				{
					DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.OJAMA);
					AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
				}
				DuelistCard.powerSummon(AbstractDungeon.player, 3, "Bonanza Token", false);
				break;
	
			// Heal Deck
			case 12:
				DuelistCard.applyPowerToSelf(new OrbHealerPower(p, 3));
				//DuelistCard.powerSummon(AbstractDungeon.player, 1, "Puzzle Token", false);
				break;
	
			// Increment Deck
			case 13:
				DuelistCard.incMaxSummons(p, 2);
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS, "Puzzle Token", false);
				break;
	
			// --- Deck
			case 14:
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS, "Puzzle Token", false);
				break;
	
			// Generic
			default:
				DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS, "Puzzle Token", false);
				break;
		}
	}
}
