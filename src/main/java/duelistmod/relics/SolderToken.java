package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.actions.common.SolderAction;
import duelistmod.variables.Strings;

public class SolderToken extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("SolderToken");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	public static int resetTo = 2;

	public SolderToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
		this.counter = resetTo;
	}

	@Override
	public void atTurnStartPostDraw() {
		if (this.counter > 0) {
			this.addToBot(new SolderAction(1, this));
		}
    }

	public void onSolderRan() {
		this.counter--;
		this.flash();
		if (this.counter <= 0) {
			this.counter = 0;
			this.grayscale = true;
		}
	}

	@Override
	public void onVictory() {
		this.counter = resetTo;
		this.grayscale = false;
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new SolderToken();
	}
}
