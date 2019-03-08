package defaultmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;


public class ExodiaPower extends AbstractPower 
{
	public AbstractCreature source;
	public static final String POWER_ID = defaultmod.DefaultMod.makeID("ExodiaPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DefaultMod.makePath(DefaultMod.EXODIA_POWER);
	private static int DAMAGE = 20;
	public ArrayList<DuelistCard> pieces = new ArrayList<DuelistCard>();
	
	public ExodiaPower(final AbstractCreature owner, final AbstractCreature source, DuelistCard piece) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		addPiece(piece);
		this.amount++; 
		this.updateDescription();
	}
	
	@Override
    public void onDrawOrDiscard() 
    {
		if (this.amount != this.pieces.size()) { this.amount = this.pieces.size(); }
    }
	
	@Override
    public void atStartOfTurn() 
    {
		if (this.amount != this.pieces.size()) { this.amount = this.pieces.size(); }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount != this.pieces.size()) { this.amount = this.pieces.size(); }
    }

	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		updateDescription();
		if (checkForAllPieces())
		{
			int playerSummons = 1;
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID)) 
			{ 
				playerSummons = DuelistCard.getSummons(AbstractDungeon.player); 
				playerSummons += DuelistCard.getMaxSummons(AbstractDungeon.player); 
			}
			int[] catapultDmg = new int[] {DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE, DAMAGE};
			for (int i = 0; i < catapultDmg.length; i++) { catapultDmg[i] = DAMAGE * playerSummons; }
        	AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(this.owner, catapultDmg, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH));
        	amount = 0;
        	pieces = new ArrayList<DuelistCard>();
        	updateDescription();
		}
	}
	
	public void addNewPiece(DuelistCard piece)
	{
		if (!addPiece(piece)) { this.amount++; }
		this.updateDescription();
	}
	
	public boolean addPiece(DuelistCard piece)
	{
		boolean found = false;
		for (DuelistCard c : pieces) { if (c.exodiaName.equals(piece.exodiaName)) { found = true; } }
		if (!found) { pieces.add(piece); }
		updateDescription();
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
			int playerSummons = 1;
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID)) 
			{ 
				playerSummons = DuelistCard.getSummons(AbstractDungeon.player); 
				playerSummons += DuelistCard.getMaxSummons(AbstractDungeon.player); 
			}
			int damageDescr = DAMAGE * playerSummons;
			this.description = DESCRIPTIONS[2] + damageDescr;
		}
		else if (checkForLegs() && checkForArms())
		{
			this.description = DESCRIPTIONS[0] + "Both arms and legs.";
		}
		else if (checkForLegs() && !checkForArms())
		{
			String descr = "Both legs, ";
			if (checkForPiece("Left Arm")) { descr += "Left Arm, "; }
			if (checkForPiece("Right Arm")) { descr += "Right Arm, "; }
			int endingIndex = descr.lastIndexOf(",");
	        String finalPiece = descr.substring(0, endingIndex) + ".";
			this.description = DESCRIPTIONS[0] + finalPiece;
		}
		else if (checkForArms() && !checkForLegs())
		{
			String descr = "Both arms, ";
			if (checkForPiece("Left Leg")) { descr += "Left Leg, "; }
			if (checkForPiece("Right Leg")) { descr += "Right Leg, "; }
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
