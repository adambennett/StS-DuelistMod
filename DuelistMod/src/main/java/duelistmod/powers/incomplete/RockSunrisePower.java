package duelistmod.powers.incomplete;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.actions.unique.YamiFormAction;
import duelistmod.cards.YamiForm;
import duelistmod.interfaces.*;
import duelistmod.variables.*;


@SuppressWarnings("unused")
public class RockSunrisePower extends AbstractPower implements NonStackablePower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("RockSunrisePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("RockSunrisePower.png");
	private boolean finished = false;
	private CardTags tag = Tags.DUMMY_TAG;
	
	public RockSunrisePower(final AbstractCreature owner, final AbstractCreature source, CardTags tag) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.tag = tag;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		if (!this.tag.equals(Tags.DUMMY_TAG)) { this.description = "#y" + DuelistMod.typeCardMap_NAME.get(this.tag) + DESCRIPTIONS[0]; }
		else { this.description = "Not a good thing to be seeing.."; }
	}

}
