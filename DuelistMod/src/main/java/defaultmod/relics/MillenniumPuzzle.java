package defaultmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.actions.unique.TheCreatorAction;
import defaultmod.patches.DuelistCard;

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
	        	System.out.println("theDuelist:MillenniumPuzzle:runSpecialEffect() ---> floor: " + floor);
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
	        	
	        	break;
	       
	        // Creator Deck
	        case 4:
	        	
	        	break;
	       
	        // Random (Small) Deck
	        case 5:
	        	
	        	break;
	       
	        // Random (Big) Deck
	        case 6:
	        	
	        	break;
	        
	        // Toon Deck
	        case 7:
	        	
	        	break;
	        
	        // Orb Deck
	        case 8:
	        	
	        	break;
	        
	        // Resummon Deck
	        case 9:
	        	
	        	break;
	        
	        // Generation Deck
	        case 10:
	        	
	        	break;
	        
	        // Ojama Deck
	        case 11:
	        	
	        	break;
	        
	        // Heal Deck
	        case 12:
	        	
	        	break;
	        
	        // Increment Deck
	        case 13:
	        	
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
