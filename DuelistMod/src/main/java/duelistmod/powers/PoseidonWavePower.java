package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.PoseidonWave;
import duelistmod.variables.*;


@SuppressWarnings("unused")
public class PoseidonWavePower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("PoseidonWavePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.PLACEHOLDER_POWER);
	private boolean finished = false;
	
	public PoseidonWavePower(final AbstractCreature owner, final AbstractCreature source) 
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

	@Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
		AbstractPlayer p = AbstractDungeon.player;
    	if ((info.type != DamageInfo.DamageType.THORNS) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.owner != null) && (info.owner != this.owner) && (damageAmount > 0) && (!this.owner.hasPower("Buffer")))
    	{
    		int x = DuelistCard.xCostTributeStatic(Tags.AQUA, new PoseidonWave());
    		if (x > 0) { return 0; }
    	}
    	return damageAmount;
    }
}
