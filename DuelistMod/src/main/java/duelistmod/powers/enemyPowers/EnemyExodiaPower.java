package duelistmod.powers.enemyPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.helpers.Util;
import duelistmod.variables.Strings;


public class EnemyExodiaPower extends AbstractPower 
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("EnemyExodiaPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.EXODIA_POWER);
	public int effectDmg = 0;
	public ArrayList<DuelistCard> pieces = new ArrayList<DuelistCard>();
	
	public EnemyExodiaPower(final AbstractCreature owner, final AbstractCreature source, DuelistCard piece) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		addPiece(piece);
		this.updateDescription();		
	}
	
	public EnemyExodiaPower(final AbstractCreature owner, final AbstractCreature source)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		addPiece(new ExodiaLA());
		addPiece(new ExodiaLL());
		addPiece(new ExodiaRA());
		addPiece(new ExodiaRL());
		addPiece(new ExodiaHead());
		this.amount = 5;
		this.effectDmg = 25;
		this.updateDescription();	
	}
	
	public void headDamage(int dmg)
	{
		this.effectDmg += dmg;
		updateDescription();
	}

	@Override
    public void atStartOfTurn() 
    {
		if (this.amount != this.pieces.size()) { this.amount = this.pieces.size(); }
    }

	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		// If monster has all 5 pieces
		if (checkForAllPieces())
		{
			// Setup copy of damage
			int newDmg = this.effectDmg;

			// Attack player for the damage value, animation/sfx/afx are handled inside attack function
			DuelistMonster.staticAttack(newDmg, this.owner, true);
			
			// Remove this power
			DuelistCard.removePower(this, this.owner); 
        	updateDescription();
		}
		
		// Otherwise just update the description
		else { updateDescription(); }
	}
	
	public void addNewPiece(DuelistCard piece)
	{
		if (!addPiece(piece)) { this.amount++; Util.log("Exodia Power incremented amount by 1"); }
		this.updateDescription();
	}
	
	public boolean addPiece(DuelistCard piece)
	{
		boolean found = false;
		for (DuelistCard c : pieces) { if (c.exodiaName.equals(piece.exodiaName)) { found = true; } }
		if (!found) { pieces.add(piece); this.amount++; }
		updateDescription();
		Util.log("Exodia Power attempted to add piece. Did we add=" + !found + ", addedPiece=" + piece.exodiaName);
		return found;
	}
	
	public boolean checkForAllPieces()
	{
		if (pieces.size() < 5) { return false; }
		else
		{
			if (!checkForPiece("Head"))   { 	return false; 	}
			if (!checkForPiece("Left Arm")) { 	return false; 	}
			if (!checkForPiece("Left Leg")) { 	return false;	}
			if (!checkForPiece("Right Arm")) { 	return false; 	}
			if (!checkForPiece("Right Leg")) {  return false;	}
		}
		return true;
	}
	
	public boolean checkForAllPiecesButHead()
	{
		if (pieces.size() < 4) { return false; }
		else
		{
			if (!checkForPiece("Left Arm")) { return false; 	}
			if (!checkForPiece("Left Leg")) { return false;		}
			if (!checkForPiece("Right Arm")) { return false; 	}
			if (!checkForPiece("Right Leg")) { return false;	}
		}
		return true;
	}
	
	public boolean checkForPiece(String pieceName)
	{
		boolean found = false;
		for (DuelistCard c : pieces) { if (c.exodiaName.equals(pieceName)) { found = true; } }
		return found;
	}
	
	public boolean checkForLegs()
	{
		if (checkForPiece("Left Leg") && checkForPiece("Right Leg")) { return true; }
		return false;
	}
	
	public boolean checkForArms()
	{
		if (checkForPiece("Left Arm") && checkForPiece("Right Arm")) { return true; }
		return false;
	}
	
	@Override
	public void updateDescription() 
	{
		if (this.amount != this.pieces.size()) { this.amount = this.pieces.size(); }
		if (this.amount == 0) 
		{ 
			this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + pieces.size(); 
		}
		else if (checkForAllPieces())
		{
			this.description = DESCRIPTIONS[2] + this.effectDmg;
		}
		else if (checkForLegs() && checkForArms())
		{
			this.description = DESCRIPTIONS[0] + DuelistMod.exodiaAlmostAllString;
		}
		else if (checkForLegs() && !checkForArms())
		{
			String descr = DuelistMod.exodiaBothLegsString;
			if (checkForPiece("Left Arm")) { descr += DuelistMod.exodiaLeftArmString; }
			if (checkForPiece("Right Arm")) { descr += DuelistMod.exodiaRightArmString; }
			int endingIndex = descr.lastIndexOf(",");
	        String finalPiece = descr.substring(0, endingIndex) + ".";
			this.description = DESCRIPTIONS[0] + finalPiece;
		}
		else if (checkForArms() && !checkForLegs())
		{
			String descr = DuelistMod.exodiaBothArmsString;
			if (checkForPiece("Left Leg")) { descr += DuelistMod.exodiaLeftLegString; }
			if (checkForPiece("Right Leg")) { descr += DuelistMod.exodiaRightLegString; }
			int endingIndex = descr.lastIndexOf(",");
	        String finalPiece = descr.substring(0, endingIndex) + ".";
			this.description = DESCRIPTIONS[0] + finalPiece;
		}
		else
		{
			String pieceString = "";
			for (DuelistCard c : pieces) { pieceString += c.exodiaName + ", "; }
			int endingIndex = pieceString.lastIndexOf(",");
	        String finalPiece = pieceString.substring(0, endingIndex) + ".";
			this.description = DESCRIPTIONS[0] + finalPiece;
		}
		
	}
}
