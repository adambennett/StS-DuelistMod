package defaultmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.actions.unique.TheCreatorAction;
import defaultmod.cards.*;
import defaultmod.patches.DuelistCard;
import defaultmod.powers.OrbHealerPower;

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
        runSpecialEffect();
        DuelistCard.powerSummon(AbstractDungeon.player, SUMMONS, "Puzzle Token", false);
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
	        	
	        	break;
	        
	        // Dragon Deck
	        case 1:
	        	int floor = AbstractDungeon.actNum;
	        	for (int i = 0; i < 1 + floor; i++)
	        	{
		        	DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.DRAGON);
		        	AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
	        	}
	        	
	        	break;
	        
	        // Nature Deck
	        case 2:
	        	// Apply power that rolls 1-3 at the start of each turn and summons 1 of: insect token, predaplant token, or plant token depending on roll
	        	break;
	        
	        // Spellcaster Deck
	        case 3:
	        	int floorB = AbstractDungeon.actNum;
	        	for (int i = 0; i < 1 + floorB; i++)
	        	{
		        	DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.SPELLCASTER);
		        	AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
	        	}
	        	break;
	       
	        // Creator Deck
	        case 4:
	        	int roll = AbstractDungeon.cardRandomRng.random(1, 9);
	        	if (roll == 1)
	        	{
	        		DuelistCard jinzo = new Jinzo();
	        		DuelistCard.addCardToHand(jinzo);
	        	}
	        	else if (roll == 2)
	        	{
	        		DuelistCard jinzo = new TrapHole();
	        		jinzo.costForTurn = 0;
	        		jinzo.isCostModifiedForTurn = true;
	        		DuelistCard.addCardToHand(jinzo);
	        	}
	        	else if (roll == 3)
	        	{
	        		DuelistCard jinzo = new Jinzo();
	        		jinzo.upgrade();
	        		jinzo.costForTurn = 0;
	        		jinzo.isCostModifiedForTurn = true;
	        		DuelistCard.addCardToHand(jinzo);
	        	}
	        	else if (roll == 4)
	        	{
	        		DuelistCard jinzo = new TrapHole();
	        		jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
	        		if (jinzo.costForTurn != 3) { jinzo.isCostModifiedForTurn = true; }
	        		DuelistCard.addCardToHand(jinzo);
	        	}
	        	else if (roll == 5)
	        	{
	        		DuelistCard jinzo = new Jinzo();
	        		jinzo.costForTurn = AbstractDungeon.cardRandomRng.random(0, 3);
	        		if (jinzo.costForTurn != 3) { jinzo.isCostModifiedForTurn = true; }
	        		DuelistCard.addCardToHand(jinzo);
	        	}
	        	else if (roll == 6)
	        	{
	        		DuelistCard jinzo = new Jinzo();
	        		jinzo.costForTurn = 0;
	        		jinzo.isCostModifiedForTurn = true;
	        		DuelistCard.addCardToHand(jinzo);
	        	}
	        	else if (roll == 7)
	        	{
	        		DuelistCard jinzo = new TrapHole();
	        		jinzo.upgrade();
	        		DuelistCard.addCardToHand(jinzo);
	        	}
	        	else if (roll == 8)
	        	{
	        		DuelistCard jinzo = new UltimateOffering();
	        		jinzo.costForTurn = 0;
	        		jinzo.isCostModifiedForTurn = true;
	        		DuelistCard.addCardToHand(jinzo);
	        	}
	        	else if (roll == 9)
	        	{
	        		DuelistCard jinzo = new Jinzo();
	        		DuelistCard trap = new Jinzo();
	        		DuelistCard ultimate = new Jinzo();
	        		jinzo.costForTurn = 0; trap.costForTurn = 0; ultimate.costForTurn = 0;
	        		jinzo.isCostModifiedForTurn = true; trap.isCostModifiedForTurn = true; ultimate.isCostModifiedForTurn = true;
	        		DuelistCard.addCardToHand(jinzo); DuelistCard.addCardToHand(trap); DuelistCard.addCardToHand(ultimate);
	        	}
	        	break;
	       
	        // Random (Small) Deck
	        case 5:
	        	// Go through deck, count up total cost of all cards
	        	// Add a card to deck that deal damage equal to that number for 2 mana and exhausts
	        	break;
	       
	        // Random (Big) Deck
	        case 6:
	        	// Go through deck, count up total amount of tributes on all cards
	        	// Add a card that gives a random buff with that much turnNum
	        	// Write separate randomBuff function that only lets it choose powers that can handle many turnNum
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
	        	break;
	        
	        // Orb Deck
	        case 8:
	        	int rollB = AbstractDungeon.cardRandomRng.random(1, 10);
	        	if (rollB < 6) { DuelistCard.channelRandom(); }
	        	else if (rollB < 10) { AbstractDungeon.player.increaseMaxOrbSlots(1, true); }
	        	else { AbstractDungeon.player.increaseMaxOrbSlots(2, true); }
	        	break;
	        
	        // Resummon Deck
	        case 9:
	        	// Nothing? Something negative?
	        	break;
	        
	        // Generation Deck
	        case 10:
	        	int floorE = AbstractDungeon.actNum;
	        	for (int i = 0; i < 1 + floorE; i++)
	        	{
		        	DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomDuelistCard();
		        	AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
	        	}
	        	
	        	break;
	        
	        // Ojama Deck
	        case 11:
	        	int floorD = AbstractDungeon.actNum;
	        	for (int i = 0; i < 1 + floorD; i++)
	        	{
		        	DuelistCard randomDragon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.OJAMA);
		        	AbstractDungeon.actionManager.addToTop(new TheCreatorAction(p, p, randomDragon, 1, true, false));
	        	}
	        	break;
	        
	        // Heal Deck
	        case 12:
	        	DuelistCard.applyPowerToSelf(new OrbHealerPower(p, 5));
	        	break;
	        
	        // Increment Deck
	        case 13:
	        	DuelistCard.incMaxSummons(p, 1);
	        	break;
	        
	        // --- Deck
	        case 14:
	        	
	        	break;
	        
	        // Generic
	        default:
	        	break;
        }
    }
}
