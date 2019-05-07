package duelistmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.interfaces.*;


@SuppressWarnings("unused")
public class RainbowCapturePower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("RainbowCapturePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.PLACEHOLDER_POWER);
	private boolean finished = false;
	public CardTags chosenType;
	private String typeString;
	public ArrayList<AbstractCard> pieces = new ArrayList<AbstractCard>();
	
	public RainbowCapturePower(final AbstractCreature owner, final AbstractCreature source, CardTags type) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 0;
		this.chosenType = type;
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
				this.description = DESCRIPTIONS[0] + this.typeString + DESCRIPTIONS[1] + this.typeString + DESCRIPTIONS[2] + "None."; 
			}
			
			else
			{
				String pieceString = "";
				for (AbstractCard c : pieces) { pieceString += c.name + ", "; }
				int endingIndex = pieceString.lastIndexOf(",");
		        String finalPiece = pieceString.substring(0, endingIndex) + ".";
				this.description = DESCRIPTIONS[0] + this.typeString + DESCRIPTIONS[1] + this.typeString + DESCRIPTIONS[2] + finalPiece;
			}
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer) 
	{
		if (this.amount != pieces.size()) { this.amount = pieces.size(); }
		updateDescription();		
	}
	
	@Override
	public void atStartOfTurnPostDraw()
	{
		for (AbstractCard c : pieces)
		{
			DuelistCard.addCardToHand(c);
		}
		
		//DuelistCard.removePower(this, this.owner);
		pieces = new ArrayList<AbstractCard>();
		this.amount = 0;
		if (this.amount != pieces.size()) { this.amount = pieces.size(); }
		updateDescription();		
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
