package duelistmod.helpers;

import java.util.*;
import java.util.Map.Entry;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.powers.enemyPowers.*;
import duelistmod.powers.incomplete.*;

public class BuffHelper {

	
	// RANDOM BUFF HELPERS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static AbstractPower randomBuffEnemy(AbstractMonster mon, int roll, boolean naturia)
	{
		if (roll < 1) { roll = AbstractDungeon.cardRandomRng.random(1, 6); }
		int intangibleRoll = AbstractDungeon.cardRandomRng.random(1, 3);
		int timeWarpCardsRoll = AbstractDungeon.cardRandomRng.random(10, 16);
		int timeWarpStrRoll = AbstractDungeon.cardRandomRng.random(1, 3);
		ArrayList<AbstractPower> pows = new ArrayList<>();
		pows.add(new StrengthPower(mon, roll));
		pows.add(new RegenPower(mon, roll));
		pows.add(new IntangiblePower(mon, intangibleRoll));
		pows.add(new ThornsPower(mon, roll));
		pows.add(new MetallicizePower(mon, roll));
		pows.add(new AngerPower(mon, roll));
		pows.add(new AngryPower(mon, roll));
		pows.add(new ArtifactPower(mon, roll));
		pows.add(new MalleablePower(mon, roll));
		pows.add(new DemonFormPower(mon, roll));
		pows.add(new RitualPower(mon, roll, false));
		pows.add(new ThieveryPower(mon, roll));
		pows.add(new DuelistTimeWarpPower(mon, timeWarpCardsRoll, timeWarpStrRoll));
		if (naturia) {	pows.add(new ResistNatureEnemyPower(mon, AbstractDungeon.player, roll)); }
		AbstractPower powReturn = pows.get(AbstractDungeon.cardRandomRng.random(pows.size() - 1));
		if (powReturn instanceof DuelistTimeWarpPower) { powReturn = pows.get(AbstractDungeon.cardRandomRng.random(pows.size() - 1)); }
		return powReturn;
	}

	public static AbstractPower randomBuffEnemyChallenge(AbstractMonster mon, int roll, boolean naturia)
	{
		if (roll < 1) { roll = AbstractDungeon.cardRandomRng.random(1, 2); }
		int intangibleRoll = AbstractDungeon.cardRandomRng.random(1, 2);
		int timeWarpCardsRoll = AbstractDungeon.cardRandomRng.random(18, 24);
		int timeWarpStrRoll = AbstractDungeon.cardRandomRng.random(1, 2);
		ArrayList<AbstractPower> pows = new ArrayList<>();
		pows.add(new StrengthPower(mon, roll));
		pows.add(new RegenPower(mon, roll));
		pows.add(new IntangiblePower(mon, intangibleRoll));
		pows.add(new ThornsPower(mon, roll));
		pows.add(new MetallicizePower(mon, roll));
		pows.add(new ArtifactPower(mon, roll));
		pows.add(new MalleablePower(mon, roll));
		pows.add(new DemonFormPower(mon, roll));
		pows.add(new RitualPower(mon, roll, false));
		pows.add(new ThieveryPower(mon, roll));
		pows.add(new DuelistTimeWarpPower(mon, timeWarpCardsRoll, timeWarpStrRoll));
		if (naturia) {	pows.add(new ResistNatureEnemyPower(mon, AbstractDungeon.player, roll)); }
		AbstractPower powReturn = pows.get(AbstractDungeon.cardRandomRng.random(pows.size() - 1));
		if (powReturn instanceof DuelistTimeWarpPower) { powReturn = pows.get(AbstractDungeon.cardRandomRng.random(pows.size() - 1)); }
		return powReturn;
	}
	
	public static AbstractPower createRandomBuff()
	{
		BuffHelper.initBuffMap(AbstractDungeon.player);
		Set<Entry<String, AbstractPower>> set = DuelistMod.buffMap.entrySet();
		ArrayList<AbstractPower> localBuffs = new ArrayList<>();
		for (Entry<String, AbstractPower> e : set) { localBuffs.add(e.getValue()); }
		int roll = AbstractDungeon.cardRandomRng.random(localBuffs.size() - 1);
		return localBuffs.get(roll);
	}

	public static void resetBuffPool()
	{
		int noBuffs = AbstractDungeon.cardRandomRng.random(DuelistMod.lowNoBuffs, DuelistMod.highNoBuffs);
		DuelistMod.randomBuffs = new ArrayList<AbstractPower>();
		DuelistMod.randomBuffStrings = new ArrayList<String>();
		for (int i = 0; i < noBuffs; i++)
		{
			AbstractPower randomBuff = createRandomBuff();
			while (DuelistMod.randomBuffStrings.contains(randomBuff.name)) { randomBuff = createRandomBuff(); }
			DuelistMod.randomBuffs.add(randomBuff);
			DuelistMod.randomBuffStrings.add(randomBuff.name);
		}
	}

	public static void resetRandomBuffs(int turnNum)
	{
		BuffHelper.initBuffMap(AbstractDungeon.player, turnNum);
		for (int i = 0; i < DuelistMod.randomBuffs.size(); i++) { DuelistMod.randomBuffs.set(i, DuelistMod.buffMap.get(DuelistMod.randomBuffs.get(i).name)); }
	}
	
	public static String trapVortexBuffName(int powerID)
	{
		if (powerID == 1)
		{
			return "#yStrength";
		}
		else if (powerID == 2)
		{
			return "#yThorns";
		}
		else if (powerID == 3)
		{
			return "#yArtifacts";
		}
		else if (powerID == 4)
		{
			return "#yBlur";
		}
		else if (powerID == 5)
		{
			return "#yTomb #yLooter";
		}
		else
		{
			return "#yBlur";
		}
	}

	public static AbstractPower trapVortex(int powerID, int traps)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (powerID > 7 || powerID < 1) { return new BlurPower(p, traps); }
		else
		{
			if (powerID == 1)
			{
				return new StrengthPower(p, traps);
			}
			else if (powerID == 2)
			{
				return new ThornsPower(p, traps);
			}
			else if (powerID == 3)
			{
				return new ArtifactPower(p, traps);
			}
			else if (powerID == 4)
			{
				return new BlurPower(p, traps);
			}
			else if (powerID == 5)
			{
				return new TombLooterPower(p, traps);
			}
			else
			{
				return new BlurPower(p, traps);
			}
		}
	}
	
	public static String trapVortexBuffNameB(int powerID)
	{
		if (powerID == 1)
		{
			return "#yStrength";
		}
		else if (powerID == 2)
		{
			return "#yThorns";
		}
		else if (powerID == 3)
		{
			return "#yArtifacts";
		}
		else if (powerID == 4)
		{
			return "#yJuggernaut";
		}
		else if (powerID == 5)
		{
			return "#yBlur";
		}
		else
		{
			return "#yBlur";
		}
	}

	public static AbstractPower trapVortexB(int powerID, int traps)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (powerID > 7 || powerID < 1) { return new BlurPower(p, traps); }
		else
		{
			if (powerID == 1)
			{
				return new StrengthPower(p, traps);
			}
			else if (powerID == 2)
			{
				return new ThornsPower(p, traps);
			}
			else if (powerID == 3)
			{
				return new ArtifactPower(p, traps);
			}
			else if (powerID == 4)
			{
				return new JuggernautPower(p, traps);
			}
			else if (powerID == 5)
			{
				return new BlurPower(p, traps);
			}
			else
			{
				return new BlurPower(p, traps);
			}
		}
	}
	
	public static void initBuffMap(AbstractPlayer p)
	{
		int turnNum = AbstractDungeon.cardRandomRng.random(1, 4);
		initBuffMap(p, turnNum);
	}
	
	public static void initBuffMap(AbstractPlayer p, int turnNum)
	{
		int secondRoll = AbstractDungeon.cardRandomRng.random(1, 3);
		initBuffMap(p, turnNum, secondRoll);
	}

	public static void setupPowerKeywords() {
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ArcanaPower.POWER_ID, "Increases #ySpell damage.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ArtifactPower.POWER_ID, "Negates debuffs.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(AmplifyPower.POWER_ID, "Your next #yPower card is played twice this turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(BarricadePower.POWER_ID, "#yBlock is not removed at the start of your turn for the rest of combat.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(BloodPower.POWER_ID, "Decreases the #yTribute cost of cards.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(BloodUpPower.POWER_ID, "Decreases the #yTribute cost of cards temporarily.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(BlurPower.POWER_ID, "#yBlock is not removed at the start of your turn temporarily.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(BurstPower.POWER_ID, "Your next #ySkill is played twice this turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(DexterityPower.POWER_ID, "Increases #yBlock gained from cards.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(DoublePlayFirstCardPower.POWER_ID, "The next card you play is played twice.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(DoubleTapPower.POWER_ID, "Your next #yAttack is played twice this turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(DragonTreasurePower.POWER_ID, "At the end of combat, gain some Gold.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ElectricityPower.POWER_ID, "Increases the magic number of cards.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ElectricityUpPower.POWER_ID, "Increases the magic number of cards temporarily.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(EnergizedPower.POWER_ID, "Gain additional energy at the start of next turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(EnvenomPower.POWER_ID, "Whenever you deal unblocked attack damage, apply #yPoison.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(EquilibriumPower.POWER_ID, "#yRetain your hand temporarily.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ExodiaPower.POWER_ID, "Assemble all five pieces of Exodia to unleash a powerful damage effect on ALL enemies.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(FeelNoPainPower.POWER_ID, "Whenever a card is #yExhausted, gain some #yBlock.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(FireBreathingPower.POWER_ID, "Whenever you draw a #yStatus or #yCurse card, deal damage to ALL enemies.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(FishscalesPower.POWER_ID, "Increases the #ySummons of cards.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(FutureFusionPower.POWER_ID, "#ySpecial #ySummon the next monster you draw.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(HelloPower.POWER_ID, "At the start of your turn, add some random Common cards into your hand.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ImperialPower.POWER_ID, "At the start of turn, set the cost of a random #ySpell or #yTrap card in your hand to 0 for this turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(JuggernautPower.POWER_ID, "Whenever you gain #yBlock, deal damage to a random enemy.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(MayhemPower.POWER_ID, "At the start of your turn, play the top card of your draw pile.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(MetallicizePower.POWER_ID, "At the end of your turn, gain #yBlock.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ObeliskPower.POWER_ID, "Each time you #yTribute, deal damage to ALL enemies.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(OniPower.POWER_ID, "At the start of turn, add a random base game card to your hand.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(PlatedArmorPower.POWER_ID, "At the end of your turn, gain #yBlock. Receiving unblocked attack damage reduces #yPlated #yArmor by #b1.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(PotGenerosityPower.POWER_ID, "Each time you #ySummon, gain [E]");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ReducerPower.POWER_ID, "At the start of turn, reduce the cost of a card in your hand by 1.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(RegenPower.POWER_ID, "At the end of your turn, heal some HP.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(SeafaringPower.POWER_ID, "Your next Attack deals additional damage, or your next Skill grants additional #yBlock.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(SadisticPower.POWER_ID, "Whenever you apply a debuff to an enemy, deal damage.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(StrengthPower.POWER_ID, "Increases attack damage.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(StrengthUpPower.POWER_ID, "Increases attack damage temporarily.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ThornsPower.POWER_ID, "When receiving #yAttack damage, deals damage back.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(TombLooterPower.POWER_ID, "Each time you attack with a max stack of #ySummons, gain some Gold.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(TricksPower.POWER_ID, "Increases #yBlock gained from #yTraps.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(TwoJamPower.POWER_ID, "At the start of turn, #ySummon some Jam Tokens and deal damage to a random enemy.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(UnicornBeaconPower.POWER_ID, "The next card played this turn ignores #yEnergy and #yTribute costs.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(TurretWarriorPower.POWER_ID, "Whenever you play a Token, deal damage to ALL enemies.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(FluxPower.POWER_ID, "When you #ySolder, improve the magic number of the targeted card by the original amount plus the amount of your Flux.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(GhostrickMansionPower.POWER_ID, "At the start of each turn, gain a random #ySpecial #ySummoning buff that lasts for the turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(OverpoweringEyePower.POWER_ID, "#ySpecial #ySummon cards are always #yUpgraded.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(VampireRetainerPower.POWER_ID, "#yVampires are always #yRetained.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ResummonBonusPower.POWER_ID, "Whenever you #ySpecial #ySummon, #ySpecial #ySummon additional copies of the same monster.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(GravediggerPower.POWER_ID, "Increases the #yEntomb amount of cards.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(SeaDwellerPower.POWER_ID, "#yOverflow cards will not reduce their #yOverflow counters when their effects are triggered.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(MakoBlessingPower.POWER_ID, "#yOverflow cards trigger their effects additional times at the end of turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(BeastFrenzyPower.POWER_ID, "Ignore energy cost on the first #yBeast you play each turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(OrbEvokerPower.POWER_ID, "Each time you #yEvoke, gain a random buff.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(FocusUpPower.POWER_ID, "Increases the effectiveness of Orbs temporarily.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(FocusPower.POWER_ID, "Increases the effectiveness of Orbs.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(FlameTigerPower.POWER_ID, "#yFire orbs now damage ALL enemies.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(OrbHealerPower.POWER_ID, "Each time you #yEvoke, heal.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(AerodynamicsPower.POWER_ID, "#yAir orbs will always #yChannel randomly at the start of turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(MagickaPower.POWER_ID, "The next time you trigger the #ySpellcaster block-on-attack effect, gain extra #yBlock. At the end of each turn, your #yMagicka is set to the number of #ySpellcasters you currently have summoned.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(StormPower.POWER_ID, "Whenever you play a #yPower card, #yChannel #yLightning.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(HealGoldPower.POWER_ID, "Each time you gain HP, gain some Gold.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(IntangiblePlayerPower.POWER_ID, "Reduce ALL damage taken and HP loss to #b1.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(VinesPower.POWER_ID, "At the end of turn, deal damage to ALL enemies equal to #b30% of #yVines times the number of #yNaturias summoned.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(LeavesPower.POWER_ID, "At the start of turn, if you have at least 5 #yLeaves, you may choose to consume your #yLeaves and gain #yBlock.");

		DuelistMod.buffCardPowerKeywordsByPowerId.put(SlowPower.POWER_ID, "Whenever you play a card, receives #b10% more damage from #yAttacks this turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(FrozenDebuff.POWER_ID, "Receives #b50% more damage and deals #b90% less damage for this turn.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(BurningDebuff.POWER_ID, "At the start of its #yeven turns, takes damage equal to #yBurning amount. At the start of #yodd turns, increase the #yBurning amount by #b1 and have a #b25% chance to spread to other enemies.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(PoisonPower.POWER_ID, "At the start of its turn, loses HP, then reduce #yPoison by #b1.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(WeakPower.POWER_ID, "#yAttacks deal less damage temporarily.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(ConstrictedPower.POWER_ID, "At the end of its turn, take damage.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(LoseStrengthPower.POWER_ID, "At the end of this turn, lose #yStrength.");
		DuelistMod.buffCardPowerKeywordsByPowerId.put(VulnerablePower.POWER_ID, "Receive more damage from #yAttacks temporarily.");
	}

	public static String getPowerDescriptionByPowerId(String powerId) {
		if (DuelistMod.buffCardPowerKeywordsByPowerId.isEmpty()) {
			setupPowerKeywords();
		}
		return DuelistMod.buffCardPowerKeywordsByPowerId.get(powerId);
	}
	
	public static ArrayList<AbstractPower> returnBuffList(AbstractPlayer p, int turnNum, int secondRoll)
	{
		ArrayList<AbstractPower> pows = new ArrayList<>();
		pows.add(new ArcanaPower(turnNum));
		pows.add(new ArtifactPower(p, turnNum));
		pows.add(new AmplifyPower(p, 1));
		pows.add(new BarricadePower(p));
		pows.add(new BloodPower(1));
		pows.add(new BlurPower(p, turnNum));
		pows.add(new BurstPower(p, turnNum));
		pows.add(new DexterityPower(p, turnNum));
		pows.add(new DoublePlayFirstCardPower(p, p, turnNum));
		pows.add(new DoubleTapPower(p, turnNum));
		pows.add(new DragonTreasurePower(25));
		pows.add(new ElectricityPower(1));
		pows.add(new ElectricityUpPower(p, p, turnNum, secondRoll));
		pows.add(new EnergizedPower(p, turnNum));
		pows.add(new EnvenomPower(p, turnNum));
		pows.add(new EquilibriumPower(p, turnNum));
		pows.add(new ExodiaPower());
		pows.add(new FeelNoPainPower(p, turnNum));
		pows.add(new FireBreathingPower(p, turnNum));
		pows.add(new FishscalesPower(1));
		pows.add(new FutureFusionPower(p, p, turnNum));
		pows.add(new HelloPower(p, turnNum));
		pows.add(new ImperialPower(p, turnNum));
		pows.add(new JuggernautPower(p, turnNum));
		pows.add(new MayhemPower(p, turnNum));
		pows.add(new MetallicizePower(p, turnNum));
		pows.add(new ObeliskPower(p, p, turnNum));
		pows.add(new OniPower(p, p, 1));
		pows.add(new PlatedArmorPower(p, turnNum));
		pows.add(new PotGenerosityPower(secondRoll));
		pows.add(new ReducerPower(p, turnNum));
		pows.add(new RegenPower(p, turnNum));		
		pows.add(new SeafaringPower(p, p, turnNum));	
		pows.add(new SadisticPower(p, turnNum));	
		pows.add(new StrengthPower(p, turnNum));
		pows.add(new StrengthUpPower(p, p, turnNum, secondRoll));
		pows.add(new ThornsPower(p, turnNum));
		pows.add(new TombLooterPower(p, turnNum));
		pows.add(new TricksPower(turnNum));
		pows.add(new TwoJamPower(p, 1, turnNum, secondRoll));
		pows.add(new RetainCardPower(p, 1));
		pows.add(new UnicornBeaconPower(p, p, 1));
		
		if (Util.deckIs("Machine Deck")) { 
			pows.add(new TurretWarriorPower(p, p, turnNum)); 
			pows.add(new FluxPower(turnNum));
		}
		if (Util.deckIs("Zombie Deck")) { 
			pows.add(new GhostrickMansionPower()); 
			pows.add(new OverpoweringEyePower()); 
			pows.add(new VampireRetainerPower()); 
			pows.add(new ResummonBonusPower(p, p, turnNum));
			pows.add(new GravediggerPower(turnNum));
		}
		if (Util.deckIs("Aqua Deck")) { 
			pows.add(new SeaDwellerPower());
			pows.add(new MakoBlessingPower(p, p, turnNum));
		}
		if (Util.deckIs("Beast Deck")) {
			pows.add(new BeastFrenzyPower(p, p));
		}
		
		// Orb-ish Decks
		if (Util.deckIs("Spellcaster Deck") || Util.deckIs("Standard Deck")|| Util.deckIs("Dragon Deck")|| Util.deckIs("Plant Deck")|| Util.deckIs("Fiend Deck")|| Util.deckIs("Zombie Deck") || Util.deckIs("Machine Deck")) { 
			pows.add(new OrbEvokerPower(p, turnNum));
			pows.add(new FocusUpPower(p, p, turnNum, secondRoll));
			pows.add(new FocusPower(p, secondRoll)); 
			pows.add(new FlameTigerPower(p, p));
			pows.add(new OrbHealerPower(p, turnNum));
			pows.add(new AerodynamicsPower(p, p));
			if (Util.deckIs("Spellcaster Deck")){
				pows.add(new MagickaPower(p, p, turnNum));
			}
		}
		if (Util.deckIs("Naturia Deck")) {
			pows.add(Util.vinesPower(turnNum, AnyDuelist.from(p)));
			pows.add(Util.leavesPower(turnNum, AnyDuelist.from(p)));
			pows.add(new StormPower(p, turnNum));
		}
		if (Util.getChallengeLevel() < 0) { 
			pows.add(new HealGoldPower(p, turnNum)); 
			pows.add(new IntangiblePlayerPower(p, 1));
		}
		
		return pows;
	}
	
	public static void initBuffMap(AbstractPlayer p, int turnNum, int secondRoll)
	{
		DuelistMod.buffMap = new HashMap<>();
		ArrayList<AbstractPower> pows = returnBuffList(p, turnNum, secondRoll);
		for (AbstractPower a : pows)
		{
			DuelistMod.buffMap.put(a.name, a);
		}
	}

}
