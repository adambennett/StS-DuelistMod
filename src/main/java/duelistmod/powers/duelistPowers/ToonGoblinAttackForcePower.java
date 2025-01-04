package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.NoStackDuelistPower;
import duelistmod.actions.enemyDuelist.DuelistDiscardSpecificCardAction;
import duelistmod.dto.AnyDuelist;

public class ToonGoblinAttackForcePower extends NoStackDuelistPower {

	public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ToonGoblinAttackForcePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    private final AnyDuelist duelist;

	public ToonGoblinAttackForcePower(AbstractCreature owner, AbstractCreature source) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.duelist = AnyDuelist.from(this);
		updateDescription();
	}

    @Override
    public void atStartOfTurnPostDraw() {
        if (this.duelist.hand().isEmpty()) return;

        AbstractCard randomCard = this.duelist.handGroup().getRandomCard(true);
        this.addToBot(new DuelistDiscardSpecificCardAction(randomCard, this.duelist));
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}

}
