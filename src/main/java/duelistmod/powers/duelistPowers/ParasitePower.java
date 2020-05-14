package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.DebuffHelper;
import duelistmod.variables.Strings;

// Passive no-effect power, just lets Toon Monsters check for playability

public class ParasitePower extends DuelistPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("ParasitePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.PARASITE_POWER);

    public ParasitePower(int turns) 
    {
    	this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
        this.amount = turns;
        updateDescription();
    }

    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount > 0)
    	{
    		ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
    		if (mons.size() > 0) { for (AbstractMonster m : mons) { DuelistCard.applyPower(DebuffHelper.getRandomDebuff(AbstractDungeon.player, m, this.amount), m); }}
    	}
	}

    @Override
	public void updateDescription() 
	{
    	if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
       
    }
}
