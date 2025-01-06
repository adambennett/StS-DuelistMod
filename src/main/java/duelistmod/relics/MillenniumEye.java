package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.enums.CardPoolType;
import duelistmod.helpers.Util;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.powers.ToonKingdomPower;
import duelistmod.powers.ToonWorldPower;

public class MillenniumEye extends DuelistRelic implements MillenniumItem {

	public static final String ID = DuelistMod.makeID("MillenniumEye");
	public static final String IMG = DuelistMod.makeRelicPath("MillenniumEyeRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MillenniumEyeRelic_Outline.png");

	public MillenniumEye() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return (!DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveToons() && (Util.deckIs("Spellcaster Deck") || Util.deckIs("Dragon Deck"))) || DuelistMod.cardPoolType == CardPoolType.ALL_CARDS;
	}

	@Override
	public void atBattleStart() {
		AnyDuelist duelist = AnyDuelist.from(this);
		if (!duelist.hasPower(ToonWorldPower.POWER_ID) && !duelist.hasPower(ToonKingdomPower.POWER_ID)) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(duelist.creature(), this));
			duelist.applyPowerToSelf(new ToonWorldPower(duelist.creature(), duelist.creature()));
		}
		this.grayscale = true;
	}
	
	@Override
    public void onVictory() {
		this.grayscale = false;
    }

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumEye();
	}

}
