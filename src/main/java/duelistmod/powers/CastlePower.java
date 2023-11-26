package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.CastleToken;
import duelistmod.interfaces.OnShufflePower;
import duelistmod.variables.Strings;

// 

public class CastlePower extends AbstractPower implements OnShufflePower
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("CastlePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.CASTLE_POWER);
    
    public static boolean UPGRADE = false;
    public static int SUMMONS = 5;
    public static int INC_SUMMONS = 5;

    public CastlePower(final AbstractCreature owner, final AbstractCreature source, int amount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public void onShuffle() 
    {
    	DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new CastleToken());
		DuelistCard.summon(AbstractDungeon.player, this.amount, tok);
    }

    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + SUMMONS + DESCRIPTIONS[1]; 
    }
}
