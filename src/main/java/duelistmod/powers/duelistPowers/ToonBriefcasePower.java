package duelistmod.powers.duelistPowers;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;

public class ToonBriefcasePower extends DuelistPower {

	public AbstractCreature source;
	public static final String POWER_ID = duelistmod.DuelistMod.makeID("ToonBriefcasePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("ToonBriefPower.png");
	public ArrayList<AbstractCard> pieces = new ArrayList<>();
	private final AnyDuelist duelist;

	public ToonBriefcasePower(final AbstractCreature owner, final AbstractCreature source) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 0;
		this.duelist = AnyDuelist.from(this);
		this.updateDescription();
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}

}
