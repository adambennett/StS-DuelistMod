package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.campfire.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.enums.StartingDecks;
import duelistmod.helpers.*;
import duelistmod.interfaces.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.variables.*;

public class ChallengePuzzle extends DuelistRelic implements VisitFromAnubisRemovalFilter {

	public static final String ID = DuelistMod.makeID("ChallengePuzzle");
	public static final String IMG = DuelistMod.makeRelicPath("MillenniumPuzzleRelic_R.png");
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_PUZZLE_RELIC_OUTLINE);
	
	private final int smithGoldLoss = 25;
	private final float questionMonsterChanceInc = 0.11f;
	private final int randomBlockCap = 15;
	private static int restSiteMod = -1;
	

	public ChallengePuzzle() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		this.setCounter(Util.getChallengeLevel());
	}
	

	@Override
    public void onEnterRoom(final AbstractRoom room) 
	{
		int decideRoll = AbstractDungeon.cardRandomRng.random(1, 2);
		if (decideRoll == 1) { restSiteMod = 1; }
		else { restSiteMod = 2; }
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
				// 'Some or all of your potions' [UNTESTED]
				/*int amt = 0;
				for (AbstractPotion pot : AbstractDungeon.player.potions) {
					if (!(pot instanceof PotionSlot)) {
						amt++;
					}
				}
				int amtToLose = AbstractDungeon.cardRandomRng.random(1, amt);
				int counter = 0;
				ArrayList<Integer> potionIndices = new ArrayList<>();
				for (AbstractPotion pot : AbstractDungeon.player.potions) {
					if (!(pot instanceof PotionSlot)) {
						potionIndices.add(counter);
					}
					counter++;
				}
				while (amtToLose > 0) {
					int index = potionIndices.remove(ThreadLocalRandom.current().nextInt(0, potionIndices.size() - 1));
					AbstractDungeon.player.potions.set(index, new PotionSlot(index));
					amtToLose--;
				}*/

				// 'All of your potions'
				for (int i = 0; i < AbstractDungeon.player.potions.size(); i++) {
					AbstractDungeon.player.potions.set(i, new PotionSlot(i));
				}
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
		if (Util.getChallengeLevel() > 5)
		{
			ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
			for (AbstractMonster mon : mons) { if (AbstractDungeon.cardRandomRng.random(1, 10) == 1) { mon.addBlock(AbstractDungeon.cardRandomRng.random(1, randomBlockCap)); }}
		}
		
		// 1% chance to become Frozen each turn
		if (Util.getChallengeLevel() > 10) { if (AbstractDungeon.cardRandomRng.random(1, 100) == 1) { DuelistCard.applyPowerToSelf(new FrozenDebuff(AbstractDungeon.player, AbstractDungeon.player)); }}
	}
	
	@Override
	public void atBattleStart() 
	{
		// Random debuff at the start of combat
		/*if (Util.getChallengeLevel() > 4) {
			int turnNum = 1;
			AbstractPower debuff = DebuffHelper.getRandomPlayerDebuff(AbstractDungeon.player, turnNum, false, true);
			DuelistCard.applyPowerToSelf(debuff);
		}*/
		
		// 3 Burning at the start of combat
		if (Util.getChallengeLevel() > 11) {
			int burnRoll = AbstractDungeon.cardRandomRng.random(1, 3);
			DuelistCard.applyPowerToSelf(new BurningDebuff(AbstractDungeon.player, AbstractDungeon.player, burnRoll));
		}
	
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
				AbstractDungeon.player.hand.glowCheck();
			}
		}
    }

	@Override
	public boolean canUseCampfireOption(final AbstractCampfireOption option) 
	{
		if (restSiteMod == 1 && option instanceof SmithOption && Util.getChallengeLevel() > 16) {
			return false;
		}
		else if (restSiteMod == 2 && option instanceof RestOption && Util.getChallengeLevel() > 16) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void atPreBattle()
	{
		/*if (Util.deckIs("Zombie Deck") && Util.getChallengeLevel() > 3)
		{
			int mRoll = AbstractDungeon.cardRandomRng.random(1, 2);
			DuelistCard.applyPowerToSelf(new MortalityPower(AbstractDungeon.player, AbstractDungeon.player, mRoll));
		}*/
		// Elite - random buff
		/*if (Util.getChallengeLevel() > 2)
		{
			if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) 
	        {
				ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
				for (AbstractMonster mon : mons)
				{
					int turnRoll = AbstractDungeon.cardRandomRng.random(2, AbstractDungeon.actNum + 3);
					boolean naturia = Util.deckIs("Naturia Deck");
					DuelistCard.applyPower(BuffHelper.randomBuffEnemyChallenge(mon, turnRoll, naturia), mon);
				}
	        }
		}*/
		
		// Bosses & Elites - random buff
		if (Util.getChallengeLevel() > 18)
		{
			if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) 
	        {
				ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
				for (AbstractMonster mon : mons)
				{
					int turnRoll = AbstractDungeon.cardRandomRng.random(2, AbstractDungeon.actNum + 4);
					boolean naturia = Util.deckIs("Naturia Deck");
					DuelistCard.applyPower(BuffHelper.randomBuffEnemyChallenge(mon, turnRoll, naturia), mon);
				}
	        }
		}
	}
	
	// Description
	@Override
	public String getUpdatedDescription() {
		getDesc("");
		this.setCounter(Util.getChallengeLevel());
		return description;
	}
	
	public void getDesc(String desc)
	{
		description = desc;
		tips.clear();
		//tips.add(new PowerTip(name, description));
		int cL = Util.getChallengeLevel();
		for (int i = 0; i < cL + 1; i++)
		{
			String result;
			if (i == 4) { result = StartingDecks.currentDeck.getChallengeDescription(); }
			else { result = DESCRIPTIONS[i+2]; }
			if (!result.equals("")) { tips.add(new PowerTip("Challenge " + i, result)); }
		}
		initializeTips();
	}

	private String getC4String(String deckName)
	{
		String result = "";
		ArrayList<String> results = new ArrayList<>();
		int counter = 0;
		if (Util.deckIs("Standard Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Dragon Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Naturia Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Spellcaster Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Toon Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Zombie Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Aqua Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Fiend Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Machine Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Warrior Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Insect Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Plant Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Megatype Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Increment Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Creator Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Exodia Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Ojama Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Giant Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Ascended I")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Ascended II")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Ascended III")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Pharaoh I")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Pharaoh II")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Pharaoh III")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Pharaoh IV")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Pharaoh V")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Random Deck (Small)")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Random Deck (Big)")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Upgrade Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		if (Util.deckIs("Metronome Deck")) { results.add(DESCRIPTIONS[counter+23]); } counter++;
		
		if (results.size() == 1) { return results.get(0); }
		else
		{
			for (int i = 0; i < results.size(); i++)
			{
				if (i + 1 >= results.size()) { result += results.get(i); }
				else { result += results.get(i) + " NL NL "; }
			}
			return result;
		}
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new ChallengePuzzle();
	}
}
