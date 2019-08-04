package duelistmod.powers.incomplete;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.interfaces.*;
import duelistmod.variables.*;


@SuppressWarnings("unused")
public class JumboDrillPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("JumboDrillPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("JumboDrillPower.png");
	private boolean finished = false;
	
	public JumboDrillPower(final AbstractCreature owner, final AbstractCreature source) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 0;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		this.description = DESCRIPTIONS[0];
	}

	public void onAfterUseCard(AbstractCard card, UseCardAction action) 
	{
		if (card.type.equals(CardType.ATTACK) && card.cost > 1)
		{
			ArrayList<AbstractCard> machines = new ArrayList<AbstractCard>();
			ArrayList<String> machNames = new ArrayList<String>();
			for (int i = 0; i < 3; i++)
			{
				DuelistCard randMachine = (DuelistCard)DuelistCard.returnTrulyRandomFromSet(Tags.MACHINE);
				//while (machNames.contains(randMachine.originalName) || randMachine.cardID.equals("theDuelist:ScrapFactory") || randMachine.cardID.equals("theDuelist:DarkFactory")) { randMachine = (DuelistCard)DuelistCard.returnTrulyRandomFromSet(Tags.MACHINE); }
				while (machNames.contains(randMachine.originalName)) { randMachine = (DuelistCard)DuelistCard.returnTrulyRandomFromSet(Tags.MACHINE); }
				machines.add(randMachine); machNames.add(randMachine.originalName);
			}
			AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(true, machines, false, 1, false, false, false, true, true, true, true, 0, 0, 0, 2, 0, 1));
		}
	}
	
	/*
	@Override
	public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) 
	{
		ArrayList<AbstractCard> machines = new ArrayList<AbstractCard>();
		ArrayList<String> machNames = new ArrayList<String>();
		for (int i = 0; i < 3; i++)
		{
			DuelistCard randMachine = (DuelistCard)DuelistCard.returnTrulyRandomFromSet(Tags.MACHINE);
			while (machNames.contains(randMachine.originalName) || randMachine.cardID.equals("theDuelist:ScrapFactory") || randMachine.cardID.equals("theDuelist:DarkFactory")) { randMachine = (DuelistCard)DuelistCard.returnTrulyRandomFromSet(Tags.MACHINE); }
			machines.add(randMachine); machNames.add(randMachine.originalName);
		}
		AbstractDungeon.actionManager.addToBottom(new CardSelectScreenIntoHandAction(true, machines, false, 1, false, false, false, true, true, true, true, 1, 3, 0, 2, 0, 1));
	}
	*/
}
