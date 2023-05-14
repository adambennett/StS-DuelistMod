package duelistmod.powers.enemyPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

import java.util.ArrayList;

public class EnemyDrawPilePower extends AbstractPower {
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("EnemyDrawPilePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("EnemyHandPower.png");
	public ArrayList<String> handCards = new ArrayList<>();

	public EnemyDrawPilePower() {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractEnemyDuelist.enemyDuelist;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = AbstractEnemyDuelist.enemyDuelist;
		updateDescription();
	}

	private int drawSize() {
		return AbstractEnemyDuelist.enemyDuelist != null ? AbstractEnemyDuelist.enemyDuelist.drawPile.size() : 0;
	}

	@Override
	public void updateDescription() {
		this.amount = drawSize();
		this.description = DESCRIPTIONS[0] + this.amount;
	}
}
