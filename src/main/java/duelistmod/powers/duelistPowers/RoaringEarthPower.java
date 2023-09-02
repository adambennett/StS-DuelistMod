package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class RoaringEarthPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("RoaringEarthPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    private final AnyDuelist duelist;

	public RoaringEarthPower(AbstractCreature owner, AbstractCreature source, int beastsToDraw) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.duelist = AnyDuelist.from(this);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = beastsToDraw;
		updateDescription(); 
	}

    @Override
    public void atStartOfTurn() {
        this.duelist.drawTag(this.amount, Tags.BEAST);
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[this.amount == 1 ? 1 : 2];
	}
}
