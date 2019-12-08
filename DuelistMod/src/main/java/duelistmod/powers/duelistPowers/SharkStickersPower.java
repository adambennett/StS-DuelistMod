package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.actions.common.CardSelectScreenTriggerOverflowAction;

public class SharkStickersPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("SharkStickersPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
	public SharkStickersPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public SharkStickersPower(AbstractCreature owner, AbstractCreature source, int stacks) 
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
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
	}
	
	@Override
	public void onIncrement(int amount, int newMaxSummons) 
	{
		if (amount > 0 && this.amount > 0)
		{
			AbstractPlayer p = AbstractDungeon.player;
			this.addToBot(new CardSelectScreenTriggerOverflowAction(p.hand.group, this.amount, 1, true));
		}
	}
	

}
