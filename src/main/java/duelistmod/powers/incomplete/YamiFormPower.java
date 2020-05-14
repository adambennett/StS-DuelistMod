package duelistmod.powers.incomplete;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.actions.unique.YamiFormAction;
import duelistmod.cards.YamiForm;
import duelistmod.interfaces.*;
import duelistmod.variables.Strings;


@SuppressWarnings("unused")
public class YamiFormPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("YamiFormPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("YamiFormPower.png");
	private boolean finished = false;
	
	public YamiFormPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		if (this.amount > DuelistMod.monsterTypes.size() + 5) { this.amount = DuelistMod.monsterTypes.size() + 5; }
		if (this.amount > 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
	}
	
	@Override
	public void atStartOfTurnPostDraw()
	{
		ArrayList<DuelistCard> types = DuelistCard.generateTypeCards(0, true, new YamiForm(), this.amount, true);
		//AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(types, 1, false, false, false));
		AbstractDungeon.actionManager.addToTop(new YamiFormAction(types, 1, false, false, false));
	}
}
