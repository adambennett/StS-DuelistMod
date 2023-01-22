package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.enums.StartingDecks;
import duelistmod.helpers.PuzzleHelper;
import duelistmod.helpers.Util;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.interfaces.VisitFromAnubisRemovalFilter;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

public class MillenniumPuzzle extends DuelistRelic implements VisitFromAnubisRemovalFilter, MillenniumItem {

	public static final String ID = DuelistMod.makeID("MillenniumPuzzle");
	public static final String IMG = DuelistMod.makePath(Strings.M_PUZZLE_RELC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_PUZZLE_RELIC_OUTLINE);
	private static int summons = 2;
	public int extra = 0;
	
	public MillenniumPuzzle() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);

	}

	@Override
	public void atPreBattle() {
		getDeckDesc();
		PuzzleHelper.atBattleStartHelper();
		if (Util.getChallengeLevel() < 7) {
			PuzzleHelper.spellcasterEffects();
			PuzzleHelper.zombieEffects();
		}		
	}

	@Override
	public void onEnterRoom(AbstractRoom room) {
		getDeckDesc();
	}

	@Override
	public void onEquip() {
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) {
			getDeckDesc();
		} else {
			switch (AbstractDungeon.player.chosenClass) {
				case IRONCLAD:
	        		setDescription("At the start of combat, heal 1 HP for each Act.");
	        	    break;
	            case THE_SILENT:            	
	            	setDescription("At the start of combat, draw 1 card for each Act.");
	                break;
	            case DEFECT:         	
	            	setDescription("At the start of combat, increase your Orb slots by a random amount or gain Focus. Chosen randomly. The amount of Focus and Orb slots increases the more Acts you complete.");
	                break;            
	            default:
	            	setDescription("At the start of combat, randomly choose to heal or gain gold.");            	
	                break;
			}
		}
	}

	@Override
	public void atBattleStart() {
		this.flash();
	}

	@Override
	public void atTurnStart() {
		PuzzleConfigData config = StartingDecks.currentDeck.getActiveConfig();
		if (StartingDecks.currentDeck == StartingDecks.EXODIA && config.getDrawExodiaHead() != null && config.getDrawExodiaHead()) {
			if (Util.getChallengeLevel() < 1 || AbstractDungeon.cardRandomRng.random(1, 2) == 1) {
				DuelistCard.drawTag(1, Tags.EXODIA_HEAD);
				this.flash();
			}
		}
	}
	
	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];		
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumPuzzle();
	}


	public void setDescription() {
		description = getUpdatedDescription();
        tips.clear();
        String header = name;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(MillenniumSymbol.ID)) {
			header = "Millennium Puzzle (S)";
		}
        tips.add(new PowerTip(header, description));
        initializeTips();
	}
	
	public void setDescription(String desc) {
		description = desc;
        tips.clear();
        String header = name;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(MillenniumSymbol.ID)) {
			header = "Millennium Puzzle (S)";
		}
        tips.add(new PowerTip(header, description));
        initializeTips();
	}
	
	public void getDeckDesc() {
		setDescription(StartingDecks.currentDeck.generatePuzzleDescription());
	}

}
