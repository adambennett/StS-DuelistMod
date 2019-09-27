package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.variables.Tags;


@SuppressWarnings("unused")
public class RockSunrisePower extends DuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("RockSunrisePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("RockSunrisePower.png");
	private boolean finished = false;
	private CardTags tag = Tags.DUMMY_TAG;
	
	public RockSunrisePower(int dmgMod, CardTags tag) 
	{
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
        this.amount = dmgMod;
		this.tag = tag;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		if (!this.tag.equals(Tags.DUMMY_TAG)) { this.description = "#y" + DuelistMod.typeCardMap_NAME.get(this.tag) + DESCRIPTIONS[0] + (this.amount * 10) + DESCRIPTIONS[1]; }
		else { this.description = "Not a good thing to be seeing.."; }
	}

}
