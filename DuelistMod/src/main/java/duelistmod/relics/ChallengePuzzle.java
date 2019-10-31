package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.campfire.*;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.*;
import duelistmod.powers.MortalityPower;
import duelistmod.powers.duelistPowers.*;
import duelistmod.variables.*;

public class ChallengePuzzle extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("ChallengePuzzle");
	public static final String IMG = DuelistMod.makeRelicPath("MillenniumPuzzleRelic_R.png");
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_PUZZLE_RELIC_OUTLINE);
	
	private final int smithGoldLoss = 35;
	private final float questionMonsterChanceInc = 0.15f;
	private final int randomBlockCap = 15;
	private final int burning = 3;
	private static int restSiteMod = -1;
	

	public ChallengePuzzle() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		this.setCounter(DuelistMod.challengeLevel);
	}
	

	@Override
    public void onEnterRoom(final AbstractRoom room) 
	{
		int decideRoll = AbstractDungeon.cardRandomRng.random(1, 2);
		int incRoll = AbstractDungeon.cardRandomRng.random(1, 3);
		if (decideRoll == 1) { restSiteMod -= incRoll; }
		else { if (incRoll == 3) { incRoll = 2; } restSiteMod += incRoll; }
		if (restSiteMod < -3) { restSiteMod = -3; }
		if (Util.getChallengeLevel() > 12)
		{
			ArrayList<Float> oldChances = EventHelper.getChances();
			ArrayList<Float> newChances = new ArrayList<>();
			newChances.add(oldChances.get(0));
			if (oldChances.get(1) < 2.0f) { newChances.add(oldChances.get(1) + questionMonsterChanceInc); }
			else { newChances.add(oldChances.get(1)); }
			newChances.add(oldChances.get(2));
			newChances.add(oldChances.get(3));
			EventHelper.setChances(newChances);
		}
	}
	
	@Override
	public void onChestOpen(final boolean bossChest) 
	{
		if (Util.getChallengeLevel() > 7 && !bossChest)
		{
			if (AbstractDungeon.player.hasAnyPotions())
			{
				AbstractDungeon.player.potions.clear();
			}
		}
    }
	
	@Override
	public void onSmith() 
	{
		if (Util.getChallengeLevel() > 14)
		{
			AbstractDungeon.player.loseGold(smithGoldLoss);
		}
    }
	
	
	@Override
	public void atTurnStart()
	{
		// At the start of each turn, each enemy has a 10% chance to gain 1-20 (random) Block
		if (DuelistMod.challengeLevel > 5)
		{
			ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
			for (AbstractMonster mon : mons) { if (AbstractDungeon.cardRandomRng.random(1, 10) == 1) { mon.addBlock(AbstractDungeon.cardRandomRng.random(1, randomBlockCap)); }}
		}
		
		// 1% chance to become Frozen each turn
		if (DuelistMod.challengeLevel > 10) { if (AbstractDungeon.cardRandomRng.random(1, 100) == 1) { DuelistCard.applyPowerToSelf(new FrozenDebuff(AbstractDungeon.player, AbstractDungeon.player)); }}
	}
	
	@Override
	public void atBattleStart() 
	{
		// 3 Burning at the start of combat
		if (DuelistMod.challengeLevel > 11) { DuelistCard.applyPowerToSelf(new BurningDebuff(AbstractDungeon.player, AbstractDungeon.player, burning)); }
	
		if (Util.getChallengeLevel() > 17 && AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) 
		{ 
			//AbstractDungeon.player.drawPile.addToRandomSpot(CardLibrary.getCurse()); 
			AbstractDungeon.player.discardPile.addToRandomSpot(CardLibrary.getCurse());
		}
	}
	
	@Override
	public void onCardDraw(final AbstractCard drawnCard) 
	{
		if (drawnCard.hasTag(Tags.SPELL) && Util.getChallengeLevel() > 3 && Util.deckIs("Standard Deck") && drawnCard.cost > -1)
		{
			if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
			{
				int cardCost = drawnCard.costForTurn;
				if (cardCost > 1) { cardCost--; }
				int costCap = 4;
				if (drawnCard.cost > 4) { costCap = drawnCard.cost; }
				int costRoll = AbstractDungeon.cardRandomRng.random(cardCost, costCap);
				drawnCard.setCostForTurn(-drawnCard.costForTurn + costRoll);
			}
		}
    }

	@Override
	public boolean canUseCampfireOption(final AbstractCampfireOption option) 
	{
		if (restSiteMod > 7 || Util.getChallengeLevel() < 17 || option instanceof SmithOption || option instanceof RestOption || option instanceof RecallOption) { return true; }
		return false;
	}

	@Override
	public void atPreBattle()
	{
		if (Util.deckIs("Zombie Deck") && Util.getChallengeLevel() > 3)
		{
			int mRoll = AbstractDungeon.cardRandomRng.random(1, 2);
			DuelistCard.applyPowerToSelf(new MortalityPower(AbstractDungeon.player, AbstractDungeon.player, mRoll));
		}
		// Elite - random buff
		if (DuelistMod.challengeLevel > 2)
		{
			if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) 
	        {
				ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
				for (AbstractMonster mon : mons)
				{
					int turnRoll = AbstractDungeon.cardRandomRng.random(2, AbstractDungeon.actNum + 3);
					boolean naturia = StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Naturia Deck");
					DuelistCard.applyPower(BuffHelper.randomBuffEnemy(mon, turnRoll, naturia), mon);
				}
	        }
		}
		
		// Bosses & Elites - random buff
		if (DuelistMod.challengeLevel > 18)
		{
			if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) 
	        {
				ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
				for (AbstractMonster mon : mons)
				{
					int turnRoll = AbstractDungeon.cardRandomRng.random(2, AbstractDungeon.actNum + 4);
					boolean naturia = StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Naturia Deck");
					DuelistCard.applyPower(BuffHelper.randomBuffEnemy(mon, turnRoll, naturia), mon);
				}
	        }
		}
	}
	
	// Description
	@Override
	public String getUpdatedDescription() {
		getDesc("");
		this.setCounter(DuelistMod.challengeLevel);
		return description;
	}
	
	private void getDesc(String desc)
	{
		description = desc;
		tips.clear();
		//tips.add(new PowerTip(name, description));
		String deckName = StarterDeckSetup.getCurrentDeck().getSimpleName();
		int cL = DuelistMod.challengeLevel;
		for (int i = 0; i < cL + 1; i++)
		{
			String result = "";
			if (i == 4) { result = getC4String(deckName); }
			else { result = DESCRIPTIONS[i+2]; }
			if (!result.equals("")) { tips.add(new PowerTip("Challenge " + i, result)); }
		}
		initializeTips();
	}

	private String getC4String(String deckName)
	{
		String result = "";
		int counter = 0;
		if (deckName.equals("Standard Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Dragon Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Naturia Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Spellcaster Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Toon Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Zombie Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Aqua Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Fiend Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Machine Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Warrior Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Insect Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Plant Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Megatype Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Increment Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Creator Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Exodia Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Ojama Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Giant Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Ascended I")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Ascended II")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Ascended III")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Pharaoh I")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Pharaoh II")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Pharaoh III")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Pharaoh IV")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Pharaoh V")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Random Deck (Small)")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Random Deck (Big)")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Upgrade Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		if (deckName.equals("Metronome Deck")) { result = DESCRIPTIONS[counter+23]; } counter++;
		return result;
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new ChallengePuzzle();
	}
}
