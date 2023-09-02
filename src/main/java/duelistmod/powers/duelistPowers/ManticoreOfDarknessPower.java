package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.enemy.EnemyDark;
import duelistmod.variables.Tags;

public class ManticoreOfDarknessPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("ManticoreOfDarknessPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    private final AnyDuelist duelist;

	public ManticoreOfDarknessPower(AbstractCreature owner, AbstractCreature source, int darkOrbs) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.duelist = AnyDuelist.from(this);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = darkOrbs;
		updateDescription(); 
	}

    @Override
    public void atStartOfTurnPostDraw() {
        int beasts = (int) this.duelist.hand().stream().filter(c -> c.hasTag(Tags.BEAST)).count();
        if (beasts > 0 && this.amount > 0) {
            AbstractOrb dark = this.duelist.player() ? new Dark() : this.duelist.getEnemy() != null ? new EnemyDark() : null;
            if (dark != null) {
                this.duelist.channel(dark, this.amount);
            }
        }
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
