package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.cards.ImperialOrder;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class ImperialPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("ImperialPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.IMPERIAL_POWER);
    private static int GOLD = 25;
    private static int DAMAGE = 3;
    

    public ImperialPower(AbstractCreature owner, int gold, int dmg) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        GOLD = gold;
        DAMAGE = dmg;
    }

    @Override
    public void atStartOfTurn() 
    {
    	ImperialOrder.gainGold(GOLD, this.owner, true);
    	ImperialOrder.damageSelf(DAMAGE);
    	if (this.amount > 0) { this.amount = 0; }
    }

    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount > 0) { this.amount = 0; }
	}
    
    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + GOLD + DESCRIPTIONS[1] + DAMAGE + DESCRIPTIONS[2];
    }
}
