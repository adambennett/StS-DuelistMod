package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.RandomizedHelper;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class SkilledBrownMagicianPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("SkilledBrownMagicianPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    private final AnyDuelist duelist;

	public SkilledBrownMagicianPower(AbstractCreature owner, AbstractCreature source, int beasts) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = beasts;
        this.duelist = AnyDuelist.from(this);
		updateDescription(); 
	}

    @Override
    public void atStartOfTurn() {
        if (this.amount > 0) {
            ArrayList<AbstractCard> kuribohCard = DuelistCard.findAllOfType(Tags.KURIBOH, 1);
            if (!kuribohCard.isEmpty()) {
                this.duelist.addCardToHand(RandomizedHelper.randomize(kuribohCard.get(0)));
            }
            this.amount--;
        }


        if (this.amount <= 0) {
            DuelistCard.removePower(this, this.duelist.creature());
        }
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
	}
}
