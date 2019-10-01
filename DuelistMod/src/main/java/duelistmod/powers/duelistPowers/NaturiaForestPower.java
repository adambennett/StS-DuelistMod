package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.NoStackDuelistPower;


public class NaturiaForestPower extends NoStackDuelistPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("NaturiaForestPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    //public static final String IMG = DuelistMod.makePowerPath("NaturiaForestPower.png");
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    ArrayList<AbstractCard> modCards = new ArrayList<AbstractCard>();
    
    public NaturiaForestPower() 
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
}
