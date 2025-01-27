package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.pools.zombies.FossilSkullConvoy;

public class FossilSkullConvoyPower extends NoStackDuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("FossilSkullConvoyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("FossilSkullConvoyPower.png");

	private boolean isUpgraded;
    
	public FossilSkullConvoyPower(boolean isUpgraded) {
		this(AbstractDungeon.player, AbstractDungeon.player, isUpgraded);
	}
	
	public FossilSkullConvoyPower(AbstractCreature owner, AbstractCreature source, boolean isUpgraded) {
		super(owner, source);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
		this.isUpgraded = isUpgraded;
		updateDescription();
	}

	@Override
	public void atStartOfTurn() {
		AbstractMonster rand = AbstractDungeon.getRandomMonster();
		if (rand != null) {
			FossilSkullConvoy card = new FossilSkullConvoy();
			if (this.isUpgraded) {
				card.upgrade();
			}
			DuelistCard.resummon(card, rand);
		}
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[this.isUpgraded ? 1 : 0];
	}

	public boolean isUpgraded() {
		return isUpgraded;
	}

	public void setUpgraded(boolean upgraded) {
		isUpgraded = upgraded;
	}
}
