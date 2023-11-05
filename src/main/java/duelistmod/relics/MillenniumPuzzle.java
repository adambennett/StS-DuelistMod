package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.PuzzleHelper;
import duelistmod.helpers.Util;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.interfaces.VisitFromAnubisRemovalFilter;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.powers.duelistPowers.FangsPower;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

public class MillenniumPuzzle extends DuelistRelic implements VisitFromAnubisRemovalFilter, MillenniumItem {

	public static final String ID = DuelistMod.makeID("MillenniumPuzzle");
	public static final String IMG = DuelistMod.makePath(Strings.M_PUZZLE_RELC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_PUZZLE_RELIC_OUTLINE);
	public int extra = 0;
	
	public MillenniumPuzzle() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		this.counter = Util.deckIs("Beast Deck") ? 0 : -1;
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
	public void onBeastIncrement(int amtIncremented) {
		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		if (config.getFangTriggerEffect()) {
			int trigger = config.getAmountOfBeastsToTrigger();
			int gain = config.getFangsToGain();
			AnyDuelist duelist = AnyDuelist.from(this);
			if (duelist.hasRelic(MillenniumSymbol.ID)) {
				gain += 2;
			}
			++this.counter;
			if (this.counter % trigger == 0) {
				this.counter = 0;
				if (gain > 0) {
					this.stopPulse();
					this.flash();

					this.addToBot(new RelicAboveCreatureAction(duelist.creature(), this));
					duelist.applyPowerToSelf(new FangsPower(duelist.creature(), duelist.creature(), gain));
				}
			} else if (this.counter + 1 == trigger && gain > 0) {
				this.beginLongPulse();
			}
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
		PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
		if (StartingDeck.currentDeck == StartingDeck.EXODIA && config.getDrawExodiaHead() != null && config.getDrawExodiaHead()) {
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


	public void setDescription(String desc) {
		description = desc;
        tips.clear();
        String header = name;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(MillenniumSymbol.ID)) {
			header = "Millennium Puzzle (S)";
		}
        tips.add(new PowerTip(header, description));
		if (CardCrawlGame.mainMenuScreen.screen != MainMenuScreen.CurScreen.CHAR_SELECT) {
			initializeTips();
		}
	}

	public void getDeckDesc() {
		getDeckDesc(null);
	}
	
	public void getDeckDesc(Boolean hasMillenniumSymbol) {
		setDescription(StartingDeck.currentDeck.generatePuzzleDescription(hasMillenniumSymbol));
	}

}
