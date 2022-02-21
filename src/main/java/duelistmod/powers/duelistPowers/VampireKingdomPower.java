package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.variables.Tags;

public class VampireKingdomPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("VampireKingdomPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("VampireKingdomPower.png");
	
	public VampireKingdomPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public VampireKingdomPower(AbstractCreature owner, AbstractCreature source, int stacks) 
	{ 
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
	public void atStartOfTurn()
	{
		if (this.amount > 0) { 
			ArrayList<AbstractCard> randZombies = DuelistCard.findAllOfType(Tags.VAMPIRE, this.amount);
			this.addToBot(new CardSelectScreenIntoHandAction(true, randZombies, true, 1, false, true, true, true, true, true, false, 0, 4, 0, 2, 0, 2));
		}
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
