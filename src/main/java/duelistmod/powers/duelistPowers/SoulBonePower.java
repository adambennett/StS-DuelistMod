package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.unique.SoulBoneAction;
import duelistmod.variables.Tags;

public class SoulBonePower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("SoulBonePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("SoulBonePower.png");
	
    public SoulBonePower(AbstractCreature source, AbstractCreature owner, int turns) 
	{ 
    	this(turns);
	}
    
	public SoulBonePower(int turns) 
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.amount = turns;
        if (this.amount < 2) { this.amount = 2; }
        this.source = AbstractDungeon.player;
		updateDescription(); 
	}
	
	@Override
	public void atStartOfTurn()
	{
		ArrayList<AbstractCard> list = DuelistCard.findAllOfType(Tags.ZOMBIE, this.amount);
		this.addToBot(new SoulBoneAction(list));
	}
	
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
