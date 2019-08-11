package duelistmod.monsters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistMonster;
import duelistmod.powers.enemyPowers.EnemySummonsPower;

public class Pegasus extends DuelistMonster
{
	
	public static final String ID = DuelistMod.makeID("Pegasus"); // Monster ID (remember the prefix - yourModID:DefaultMonster)
	private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grab the string

	public static final String NAME = monsterstrings.NAME; // The name of the monster
	public static final String[] MOVES = monsterstrings.MOVES; // The names of the moves
	public static final String[] DIALOG = monsterstrings.DIALOG; // The dialogue (if any)
	
	private static final float HB_X = 0.0F;     // The hitbox X coordinate/position (relative to the monster)
	private static final float HB_Y = -25.0F;   // The Y position
	private static final float HB_W = 200.0F;   // Hitbox width
	private static final float HB_H = 330.0F;   // Hitbox Height
	
	private static final int HP_MIN = 71; 
	private static final int HP_MAX = 85; 
	private static final int A7_HP_MIN = 86; 
	private static final int A7_HP_MAX = 101;

	public Pegasus() 
	{
		super(NAME, ID, 50, HB_X, HB_Y, HB_W, HB_H, "Pegasus", HP_MIN, HP_MAX, A7_HP_MIN, A7_HP_MAX);
		this.setHands(initHands());
		ArrayList<String> dialog = new ArrayList<String>();
		for (String s : DIALOG) { dialog.add(s); }
		this.setupDialog(dialog);
		this.cardDialogMin = 3;
		this.cardDialogMax = 8;
		this.startingToken = "Toon Token";
		this.overflowDialog = this.dialog.get(2);
		//this.deathSound = "coolKaibaSound";
	}
	
	@Override
	protected ArrayList<AbstractCard> getHandMove()
	{
		ArrayList<AbstractCard> moveCards = new ArrayList<AbstractCard>();
		int summons = getSummons();
		boolean hasLambs = hasLambs();
		switch (this.handIndex)
		{
			
		}
		return moveCards;
	}
	
	private ArrayList<ArrayList<String>> initHands()
	{
		ArrayList<ArrayList<String>> newHands = new ArrayList<ArrayList<String>>();
		ArrayList<String> hand0 = new ArrayList<String>();
		ArrayList<String> hand1 = new ArrayList<String>();
		ArrayList<String> hand2 = new ArrayList<String>();
		ArrayList<String> hand3 = new ArrayList<String>();
		ArrayList<String> hand4 = new ArrayList<String>();
		ArrayList<String> hand5 = new ArrayList<String>();
		ArrayList<String> hand6 = new ArrayList<String>();
		ArrayList<String> hand7 = new ArrayList<String>();
		ArrayList<String> hand8 = new ArrayList<String>();
		ArrayList<String> hand9 = new ArrayList<String>();
		ArrayList<String> hand10 = new ArrayList<String>();
		ArrayList<String> hand11 = new ArrayList<String>();
		ArrayList<String> hand12 = new ArrayList<String>();
		ArrayList<String> hand13 = new ArrayList<String>();
		ArrayList<String> hand14 = new ArrayList<String>();
		ArrayList<String> hand15 = new ArrayList<String>();
		ArrayList<String> hand16 = new ArrayList<String>();
		ArrayList<String> hand17 = new ArrayList<String>();
		ArrayList<String> hand18 = new ArrayList<String>();
		ArrayList<String> hand19 = new ArrayList<String>();
		ArrayList<String> hand20 = new ArrayList<String>();
		ArrayList<String> hand21 = new ArrayList<String>();
		
		
		newHands.add(hand0);
		newHands.add(hand1);
		newHands.add(hand2);
		newHands.add(hand3);
		newHands.add(hand4);
		newHands.add(hand5);
		newHands.add(hand6);
		newHands.add(hand7);
		newHands.add(hand8);
		newHands.add(hand9);
		newHands.add(hand10);
		newHands.add(hand11);
		newHands.add(hand12);
		newHands.add(hand13);
		newHands.add(hand14);
		newHands.add(hand15);
		newHands.add(hand16);
		newHands.add(hand17);
		newHands.add(hand18);
		newHands.add(hand19);
		newHands.add(hand20);
		newHands.add(hand21);
		return newHands;
	}

	@Override
	public void onUseDestructPotion() {
		// TODO Auto-generated method stub
		
	}

}
