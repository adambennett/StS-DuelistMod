package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class GalactikuribohPower extends AbstractPower {
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("GalactikuribohPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	private final AnyDuelist duelist;

	public GalactikuribohPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		this.duelist = AnyDuelist.from(this);
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[amount == 1 ? 1 : 2];
	}

	@Override
	public void onChannel(AbstractOrb orb) {
		ArrayList<AbstractCard> list = DuelistCard.findAllOfTypeForResummon(Tags.KURIBOH, Tags.MONSTER, this.amount);
		for (AbstractCard toResummon : list) {
			if (toResummon instanceof DuelistCard) {
				if (this.duelist.player()) {
					AbstractMonster m = AbstractDungeon.getRandomMonster();
					if (m != null) {
						DuelistCard.resummon(toResummon, m);
					}
				} else if (this.duelist.getEnemy() != null) {
					DuelistCard.anyDuelistResummon(toResummon, this.duelist, AbstractDungeon.player);
				}
			}
		}
	}
}
