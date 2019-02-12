package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.actions.common.SpecificCardDiscardToDeckAction;
import defaultmod.cards.AxeDespair;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class DespairPower extends AbstractPower 
{
	public AbstractCreature source;

	public static final String POWER_ID = defaultmod.DefaultMod.makeID("DespairPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DefaultMod.makePath(DefaultMod.DESPAIR_POWER);
	public static CustomCard ATTACHED_AXE = null;
	
	private static int TRIBUTES = 2;
	private static int STR_LOSS = 9;
	private static int DAMAGE = 30;

	public DespairPower(final AbstractCreature owner, final AbstractCreature source, final CustomCard card, int strLoss) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.updateDescription();
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 1;
		ATTACHED_AXE = card;
		STR_LOSS = strLoss;
	}
	
	@Override
    public void atStartOfTurn() 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
	// At the end of the turn, remove gained Strength.
	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		if (this.amount > 0) { this.amount = 0; }
		int tribs = AxeDespair.tribute(AbstractDungeon.player, TRIBUTES, false);
		if (tribs < TRIBUTES)
		{
			AxeDespair.damageSelf(DAMAGE);
		}

		// Lose 10 or 12 Strength
		AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, "Strength", STR_LOSS));
		
		// Take the Axe of Despair we just played out of the discard and put on top of the deck
		AbstractDungeon.actionManager.addToTop(new SpecificCardDiscardToDeckAction(this.owner, ATTACHED_AXE));
		AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, DespairPower.POWER_ID, 1));
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + STR_LOSS + DESCRIPTIONS[1];
	}
}
