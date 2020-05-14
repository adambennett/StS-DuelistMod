package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tempCards.CancelCard;

public class GhostrickBreakPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("GhostrickBreakPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
    public AbstractCard res;
    
	public GhostrickBreakPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public GhostrickBreakPower(AbstractCreature owner, AbstractCreature source, int stacks) 
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
        this.res = DuelistMod.firstCardInGraveThisCombat;
		updateDescription();
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer)
	{
		if (this.res instanceof CancelCard)
		{
			DuelistCard.removePower(this, this.owner);
		}
	}
	
	@Override
	public void atStartOfTurn() 
	{
		if (!(this.res instanceof CancelCard))
		{
			AbstractMonster rand = AbstractDungeon.getRandomMonster();
			if (rand != null)
			{
				DuelistCard.resummon(this.res, rand);
				this.amount--;
				updateDescription();
				if (this.amount < 1) {
					DuelistCard.removePower(this, this.owner);
				}
			}
		}
		else
		{
			DuelistCard.removePower(this, this.owner);
		}
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.res.originalName + DESCRIPTIONS[3]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + this.res.originalName + DESCRIPTIONS[3]; }
	}
}
