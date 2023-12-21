package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class ToadallyAwesomePower extends DuelistPower {
	public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ToadallyAwesomePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("ToadallyAwesomePower.png");

	public ToadallyAwesomePower(int turns) {
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}

	public ToadallyAwesomePower(AbstractCreature owner, AbstractCreature source, int stacks) {
		//super(owner, source, stacks);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = stacks;
		updateDescription();
	}

	@Override
	public void atStartOfTurnPostDraw() {
		AnyDuelist duelist = AnyDuelist.from(this);
		for (AbstractCard c : duelist.hand()) {
			if (c.hasTag(Tags.TOON_POOL)) {
				return;
			}
		}

		if (duelist.player()) {
			DuelistCard.gainTempHP(this.amount);
		} else {
			DuelistCard.gainTempHP(duelist.getEnemy(), duelist.getEnemy(), this.amount);
		}
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
