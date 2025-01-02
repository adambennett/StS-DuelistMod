package duelistmod.powers;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class ToonRollbackPower extends DuelistPower {

	public AbstractCreature source;
	public static final String POWER_ID = duelistmod.DuelistMod.makeID("ToonRollbackPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("ToonRollbackPower.png");
	public ArrayList<AbstractCard> pieces = new ArrayList<>();
	private final AnyDuelist duelist;
	
	public ToonRollbackPower(final AbstractCreature owner, final AbstractCreature source) {
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
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
		if (c.hasTag(Tags.TOON)) {
			if (this.duelist.player()) {
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(c.makeStatEquivalentCopy(), false, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));
			} else if (this.duelist.getEnemy() != null) {
				this.duelist.addCardToHand(c.makeStatEquivalentCopy());
			}
		}
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}

}
