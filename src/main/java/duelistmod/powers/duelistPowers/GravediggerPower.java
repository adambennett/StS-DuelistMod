package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tokens.EntombToken;
import duelistmod.cards.pools.zombies.*;

public class GravediggerPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("GravediggerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
	public GravediggerPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public GravediggerPower(AbstractCreature owner, AbstractCreature source, int stacks) 
	{ 
		//super(owner, source, stacks);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = true;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = stacks;
		updateDescription();
	}
	
	@Override
	public float modifyEntomb(float tmp, AbstractCard card)
	{
		if (card instanceof DuelistCard)
		{
			DuelistCard dc = (DuelistCard)card;
			if (dc.entomb > 0) {
				return tmp + this.amount;
			}
			else if (dc instanceof TutanMask || dc instanceof RobbinZombie || dc instanceof EntombToken) {
				return tmp + this.amount;
			}
		}
		return tmp;
	}

	@Override
	public void updateDescription()
	{
		if (this.amount > -1) {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
			this.type = PowerType.BUFF;
		}
		else {
			final int tmp = -this.amount;
			this.description = DESCRIPTIONS[1] + tmp + DESCRIPTIONS[2];
			this.type = PowerType.DEBUFF;
		}
	}
}
