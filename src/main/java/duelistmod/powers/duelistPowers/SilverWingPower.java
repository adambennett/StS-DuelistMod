package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class SilverWingPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("SilverWingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("SilverWingPower.png");
	private final AnyDuelist duelist;

	public SilverWingPower(AbstractCreature source, AbstractCreature owner, int amt, int amt2) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
		this.duelist = AnyDuelist.from(this);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amt;
		this.amount2 = amt2;
		updateDescription(); 
	}
	
	@Override
	public void onPlayCard(AbstractCard c, AbstractMonster m) {
		if (AnyDuelist.from(c).creature().equals(this.duelist.creature())) {
			if (c.hasTag(Tags.DRAGON)) {
				if (this.amount > 0) {
					this.duelist.block(this.amount);
				}
				if (this.amount2 > 0) {
					this.duelist.applyPowerToSelf(new Dragonscales(this.duelist.creature(), this.duelist.creature(), this.amount2));
				}
			}
		}
	}
	
    @Override
    public void onEnemyUseCard(AbstractCard card) {
    	//onPlayCard(card, null);
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2] + (this.amount2 != 1 ? "s." : ".");
	}
}
