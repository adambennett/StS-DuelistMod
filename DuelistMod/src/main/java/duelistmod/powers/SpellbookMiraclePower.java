package duelistmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.interfaces.*;


@SuppressWarnings("unused")
public class SpellbookMiraclePower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("SpellbookMiraclePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.SPELLBOOK_POWER);
	private boolean finished = false;
	
	public SpellbookMiraclePower(final AbstractCreature owner, final AbstractCreature source) 
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
	public void onInitialApplication() 
	{
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPower pow : p.powers)
		{
			if (pow.name.equals("Spellbook of Power"))
			{
				DuelistCard.removePower(pow, p);
			}
			
			else if (pow.name.equals("Spellbook of Knowledge"))
			{
				DuelistCard.removePower(pow, p);
			}

			else if (pow.name.equals("Spellbook of Life"))
			{
				DuelistCard.removePower(pow, p);
			}
		}
	}
}
