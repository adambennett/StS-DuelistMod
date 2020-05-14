package duelistmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import basemod.BaseMod;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;


@SuppressWarnings("unused")
public class RainbowCapturePower extends TwoAmountPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("RainbowCapturePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("RainbowCapturePower.png");
	private boolean finished = false;
	public CardTags chosenType;
	private String typeString;
	public ArrayList<AbstractCard> pieces = new ArrayList<AbstractCard>();
	
	public RainbowCapturePower(final AbstractCreature owner, final AbstractCreature source, CardTags type, int turns) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 0;
		this.amount2 = turns;
		this.chosenType = type;
		this.canGoNegative = true;
		this.canGoNegative2 = false;
		this.typeString = chosenType.toString().toLowerCase();
		String temp = this.typeString.substring(0, 1).toUpperCase();
		this.typeString = temp + this.typeString.substring(1);
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		 if (this.amount != pieces.size()) { this.amount = pieces.size(); }
			if (pieces.size() == 0) 
			{ 
				this.description = DESCRIPTIONS[0] + this.typeString + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2] + this.typeString + DESCRIPTIONS[3] + "None."; 
			}
			
			else
			{
				String pieceString = "";
				for (AbstractCard c : pieces) { pieceString += c.name + ", "; }
				int endingIndex = pieceString.lastIndexOf(",");
		        String finalPiece = pieceString.substring(0, endingIndex) + ".";
		        this.description = DESCRIPTIONS[0] + this.typeString + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2] + this.typeString + DESCRIPTIONS[3] + finalPiece;
			}
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer) 
	{
		if (this.amount != pieces.size()) { this.amount = pieces.size(); }
		if (this.amount2 > 0) { this.amount2--; }
		updateDescription();		
	}
	
	@Override
	public void atStartOfTurnPostDraw()
	{
		for (AbstractCard c : pieces)
		{
			if (AbstractDungeon.player.hand.group.size() < BaseMod.MAX_HAND_SIZE) {	DuelistCard.addCardToHand(c.makeStatEquivalentCopy()); }
		}

		if (this.amount2 < 1) { DuelistCard.removePower(this, this.owner); }
		else
		{
			pieces = new ArrayList<AbstractCard>();
			this.amount = 0;
			if (this.amount != pieces.size()) { this.amount = pieces.size(); }
			updateDescription();	
		}	
	}
	
	@Override
	public void onPlayCard(AbstractCard card, AbstractMonster m) 
	{
		if (this.amount != pieces.size()) { this.amount = pieces.size(); }
		if(card.hasTag(chosenType)) { pieces.add(card); }
		updateDescription();
	}
	
	@Override
	public void onDrawOrDiscard() 
	{
		if (this.amount != pieces.size()) { this.amount = pieces.size(); }
		updateDescription();
	}
}
