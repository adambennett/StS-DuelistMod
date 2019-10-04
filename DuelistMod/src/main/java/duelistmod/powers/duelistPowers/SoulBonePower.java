package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.variables.Tags;

public class SoulBonePower extends NoStackDuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("SoulBonePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("SoulBonePower.png");
	
	public SoulBonePower() 
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
	public void atStartOfTurn()
	{
		ArrayList<DuelistCard> avail = new ArrayList<DuelistCard>();
		for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) { if (c instanceof DuelistCard && !c.hasTag(Tags.EXEMPT)) { avail.add((DuelistCard) c.makeStatEquivalentCopy()); }}
		if (avail.size() > 0) { while (avail.size() > 2) { avail.remove(AbstractDungeon.cardRandomRng.random(avail.size() - 1)); }}
		if (avail.size() == 2) { this.addToBot(new CardSelectScreenResummonAction(avail, 1, false, false, true, true)); }
	}
	
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
}
