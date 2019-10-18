package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.tokens.Token;
import duelistmod.orbs.DragonOrb;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;


@SuppressWarnings("unused")
public class CastleDragonSoulsPower extends NoStackDuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("CastleDragonSoulsPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	private boolean finished = false;
	
	public CastleDragonSoulsPower() 
	{
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		 this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void atStartOfTurn()
	{
		DuelistCard.channel(new DragonOrb());
	}
}
