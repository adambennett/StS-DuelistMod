package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.BaseMod;
import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.*;


public class ToonBriefcasePower extends DuelistPower 
{
	public AbstractCreature source;
	public static final String POWER_ID = duelistmod.DuelistMod.makeID("ToonBriefcasePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.TOON_BRIEFCASE_POWER);
	public ArrayList<AbstractCard> pieces = new ArrayList<AbstractCard>();

	public ToonBriefcasePower(final AbstractCreature owner, final AbstractCreature source) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 0;
		this.updateDescription();
	}

	@Override
	public void onDrawOrDiscard() 
	{
		if (this.amount != pieces.size()) { this.amount = pieces.size(); }
		updateDescription();
	}

	@Override
	public void onPlayCard(AbstractCard c, AbstractMonster m) 
	{
		if (this.amount != pieces.size()) { this.amount = pieces.size(); }
		if(c.hasTag(Tags.TOON_POOL)) { pieces.add(c.makeStatEquivalentCopy()); }
		updateDescription();
	}
	
    @Override
    public void onEnemyUseCard(AbstractCard card)
    {
    	onPlayCard(card, null);
    }

	@Override
	public void atStartOfTurnPostDraw() 
	{
		for (AbstractCard c : pieces)
		{
			if (AbstractDungeon.player.hand.group.size() < BaseMod.MAX_HAND_SIZE) {	DuelistCard.addCardToHand(c); }
		}
		pieces = new ArrayList<AbstractCard>();
		if (this.amount != pieces.size()) { this.amount = pieces.size(); }
		updateDescription();
	}

	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		if (this.amount != pieces.size()) { this.amount = pieces.size(); }
		updateDescription();
	}

	@Override
	public void updateDescription() 
	{
		if (pieces.size() == 0) 
		{ 
			this.description = DESCRIPTIONS[0] + "None.";
		}

		else
		{
			String pieceString = "";
			for (AbstractCard c : pieces) { pieceString += c.name + ", "; }
			int endingIndex = pieceString.lastIndexOf(",");
			String finalPiece = pieceString.substring(0, endingIndex) + ".";
			this.description = DESCRIPTIONS[0] + finalPiece;
		}

	}
}
