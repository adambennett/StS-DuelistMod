package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.*;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.variables.Strings;

public class MillenniumSymbol extends DuelistRelic implements MillenniumItem {

	public static final String ID = DuelistMod.makeID("MillenniumSymbol");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public MillenniumSymbol() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
		setDescription();
	}
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		String deck = StartingDeck.currentDeck.getDeckName();
		PuzzleConfigData dragonConfig = StartingDeck.DRAGON.getActiveConfig();
		if (deck.equals("Dragon Deck") && (dragonConfig.getEffectsChoices() > 5 || dragonConfig.getEffectsDisabled() || dragonConfig.getEffectsToRemove() > 5)) {
			return false;
		}
		if (Util.getChallengeLevel() > 0) { return false; }
		if (deck.equals("Standard Deck")) { return true; }
		if (deck.equals("Dragon Deck")) { return true; }
		if (deck.equals("Naturia Deck")) { return true; }
		if (deck.equals("Spellcaster Deck")) { return true; }
		if (deck.equals("Toon Deck")) { return true; }
		if (deck.equals("Zombie Deck")) { return true; }
		if (deck.equals("Aqua Deck")) { return true; }
		if (deck.equals("Fiend Deck")) { return true; }
		if (deck.equals("Machine Deck")) { return true; }
		if (deck.equals("Warrior Deck")) { return true; }
		if (deck.equals("Insect Deck")) { return true; }
		if (deck.equals("Plant Deck")) { return true; }
		if (deck.equals("Megatype Deck")) { return true; }
		if (deck.equals("Increment Deck")) { return true; }
		if (deck.equals("Beast Deck")) { return true; }
		if (deck.equals("Creator Deck")) { return true; }
		return false;
	}

	// Description
	@Override
	public String getUpdatedDescription() 
	{
		String desc = DESCRIPTIONS[0];
		String deck = StartingDeck.currentDeck.getDeckName();
		if (deck.equals("Standard Deck")) { desc = DESCRIPTIONS[2]; }
		if (deck.equals("Dragon Deck")) { desc = DESCRIPTIONS[3]; }
		if (deck.equals("Naturia Deck")) { desc = DESCRIPTIONS[4]; }
		if (deck.equals("Spellcaster Deck")) { desc = DESCRIPTIONS[5]; }
		if (deck.equals("Toon Deck")) { desc = DESCRIPTIONS[6]; }
		if (deck.equals("Zombie Deck")) { desc = DESCRIPTIONS[7]; }
		if (deck.equals("Aqua Deck")) { desc = DESCRIPTIONS[8]; }
		if (deck.equals("Fiend Deck")) { desc = DESCRIPTIONS[9]; }
		if (deck.equals("Machine Deck")) { desc = DESCRIPTIONS[10]; }
		if (deck.equals("Warrior Deck")) { desc = DESCRIPTIONS[11]; }
		if (deck.equals("Insect Deck")) { desc = DESCRIPTIONS[12]; }
		if (deck.equals("Plant Deck")) { desc = DESCRIPTIONS[13]; }
		if (deck.equals("Megatype Deck")) { desc = DESCRIPTIONS[14]; }
		if (deck.equals("Increment Deck")) { desc = DESCRIPTIONS[15]; }
		if (deck.equals("Creator Deck")) { desc = DESCRIPTIONS[16]; }
		if (deck.equals("Beast Deck")) { desc = DESCRIPTIONS[17]; }
		return desc;
	}
	
	@Override
	public void onEquip() {
		this.resetPuzzleDesc(true);
	}

	@Override
	public void onUnequip() {
		this.resetPuzzleDesc(false);
	}

	public void resetPuzzleDesc(boolean equipped) {
		if (AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID)) {
			MillenniumPuzzle puz = (MillenniumPuzzle) AbstractDungeon.player.getRelic(MillenniumPuzzle.ID);
			puz.getDeckDesc(equipped);
		}
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumSymbol();
	}
}
