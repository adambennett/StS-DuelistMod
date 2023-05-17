package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.Frost;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.enemy.EnemyFrost;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

public class BlizzardDragonPower extends DuelistPower {
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("BlizzardDragonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.BLIZZARD_DRAGON_POWER);
    private final AnyDuelist duelist;
    
    public BlizzardDragonPower(final AbstractCreature owner, final AbstractCreature source) {
    	this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.duelist = AnyDuelist.from(this);
        this.updateDescription();
    }
    
	@Override
	public void onSummon(DuelistCard c, int amt) {
		if (c.hasTag(Tags.DRAGON) && amt > 0) {
            this.duelist.channel(this.duelist.player() ? new Frost() : new EnemyFrost());
		}
	}
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
    	//if (isPlayer)
    	//{
    		DuelistCard.removePower(this, this.owner);
    	//}
    }    
  
    @Override
	public void updateDescription() {
    	this.description = DESCRIPTIONS[0];
    }
}
