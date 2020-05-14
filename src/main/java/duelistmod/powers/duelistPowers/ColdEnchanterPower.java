package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class ColdEnchanterPower extends NoStackDuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("ColdEnchanterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
	public ColdEnchanterPower() 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player);
	}
	
	public ColdEnchanterPower(AbstractCreature owner, AbstractCreature source) 
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
		updateDescription();
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
		for (AbstractMonster m : mons)
		{
			if (m.hasPower(DampDebuff.POWER_ID) && !m.hasPower(FrozenDebuff.POWER_ID))
			{
				DuelistCard.applyPower(new FrozenDebuff(m, this.owner), m); 
			}
		}		
	}

	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}

}
