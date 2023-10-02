package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.variables.Tags;

public class BeastFrenzyPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("BeastFrenzyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("BeastFrenzyPower.png");
	private boolean playedBeastThisTurn = false;

	public BeastFrenzyPower(AbstractCreature owner, AbstractCreature source) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
		updateDescription();
	}

	@Override
	public void atStartOfTurn() {
		flash();
		this.setPlayedBeastThisTurn(false);
	}

	@Override
	public void updateDescription() {
		 this.description = DESCRIPTIONS[0];
	}

	@Override
	public void onUseCard(final AbstractCard card, final UseCardAction action) {
		if (card.hasTag(Tags.BEAST) && !card.purgeOnUse && this.amount > 0) {
			this.flash();
			this.playedBeastThisTurn = true;
		}
	}

	public boolean isPlayedBeastThisTurn() {
		return playedBeastThisTurn;
	}

	public void setPlayedBeastThisTurn(boolean playedBeastThisTurn) {
		this.playedBeastThisTurn = playedBeastThisTurn;
	}
}
