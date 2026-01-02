package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

public class MillenniumRod extends DuelistRelic implements MillenniumItem {

	public static final String ID = duelistmod.DuelistMod.makeID("MillenniumRod");
	public static final String IMG = DuelistMod.makePath(Strings.M_ROD_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_ROD_RELIC_OUTLINE);
	private boolean activatedThisCombat = false;

	public MillenniumRod() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}

	@Override
	public void atBattleStart() {
		this.grayscale = false;
		this.activatedThisCombat = false;
	}

	@Override
	public void onVictory() {
		this.grayscale = false;
		this.activatedThisCombat = false;
	}
	
	@Override
	public void atTurnStart() {
		if (!this.activatedThisCombat) {
			this.flash();
			AbstractCard randomCard = DuelistCard.returnTrulyRandomInCombatFromSet(Tags.SPELL, true);
			int roll = AbstractDungeon.relicRng.random(100);
			boolean upgrade = roll <= 5;
			this.addToTop(new RandomizedHandAction(randomCard, upgrade, false, false, true, true, false, false, false, 0, 4, 0, 4, 0, 0));
			this.grayscale = true;
			this.activatedThisCombat = true;
		}
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumRod();
	}
}
