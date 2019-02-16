package defaultmod.patches;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import blackrusemod.powers.AmplifyDamagePower;
import conspire.actions.ObtainGoldAction;
import conspire.orbs.Water;
import defaultmod.actions.common.*;
import defaultmod.cards.*;
import defaultmod.orbs.*;
import defaultmod.powers.*;
import defaultmod.relics.MillenniumKey;
import slimebound.powers.SlimedPower;

public abstract class DuelistCard extends CustomCard 
{
	// CARD FIELDS
	public String upgradeType;
	public String exodiaName;
	public String originalName;
	public boolean isMonster = false;
	public boolean isOverflow;
	public boolean flag;
	public boolean toon = false;
	public int summons = 0;
	public int tributes = 0;
	public int upgradeDmg;
	public int upgradeBlk;
	public int upgradeSummons;
	public int counters;
	public int buffs;
	public int debuffs;
	public int heal;
	public int cards;
	public int power;
	public int overflowAmt;
	public int playCount;
	public int damageTimes;
	public int incSummons;
	public int decSummons;
	public int energyGain;
	public int turnCounter;
	public int dex;
	public int str;
	public int magnets;
	public int chanceMod;
	public int damageA;
	public int damageB;
	public int damageC;
	public int damageD;
	// CARD FIELDS

	public DuelistCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET)
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.originalName = NAME;
	}

	public DuelistCard getCard()
	{
		return this;
	}

	protected int getXEffect() {
		if (energyOnUse < EnergyPanel.totalCount) {
			energyOnUse = EnergyPanel.totalCount;
		}

		int effect = EnergyPanel.totalCount;
		if (energyOnUse != -1) {
			effect = energyOnUse;
		}
		if (player().hasRelic(ChemicalX.ID)) {
			effect += ChemicalX.BOOST;
			player().getRelic(ChemicalX.ID).flash();
		}
		return effect;
	}

	protected void useXEnergy() {
		AbstractDungeon.actionManager.addToTop(new LoseXEnergyAction(player(), freeToPlayOnce));
	}

	public static void damageSelf(int DAMAGE)
	{
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, DAMAGE, AbstractGameAction.AttackEffect.POISON));
	}
	
	public static void damageSelfFire(int DAMAGE)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, DAMAGE, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
	}

	protected static AbstractPlayer player() {
		return AbstractDungeon.player;
	}

	protected boolean hasPower(String power) {
		return player().hasPower(power);
	}

	protected static void applyPower(AbstractPower power, AbstractCreature target) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player(), power, power.amount));
	}

	protected void applyPower(AbstractPower power, AbstractCreature target, int amount) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player(), power, amount));
	}

	public static void removePower(AbstractPower power, AbstractCreature target) {
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, player(), power, power.amount));
	}

	public static void reducePower(AbstractPower power, AbstractCreature target, int reduction) {
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, player(), power, reduction));
	}

	public static void heal(AbstractPlayer p, int amount)
	{
		AbstractDungeon.actionManager.addToTop(new HealAction(p, p, amount));
	}

	protected void healMonster(AbstractMonster p, int amount)
	{
		AbstractDungeon.actionManager.addToTop(new HealAction(p, p, amount));
	}

	protected void block() {
		block(block);
	}

	public static void block(int amount) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player(), player(), amount));
	}

	public static void applyPowerToSelf(AbstractPower power) {
		applyPower(power, player());
	}

	public void draw(int cards) {
		AbstractDungeon.actionManager.addToTop(new DrawCardAction(player(), cards));
	}

	public void drawBottom(int cards) {
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player(), cards));
	}

	public void discard(int amount, boolean isRandom)
	{
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(player(), player(), amount, isRandom));
	}

	public void discardTop(int amount, boolean isRandom)
	{
		AbstractDungeon.actionManager.addToTop(new DiscardAction(player(), player(), amount, isRandom));
	}

	protected void gainEnergy(int energy) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energy));
	}

	protected void attack(AbstractMonster m, AttackEffect effect) {
		attack(m, effect, damage);
	}

	protected void attack(AbstractMonster m, AttackEffect effect, int damageAmount) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, damageTypeForTurn), effect));
	}

	protected void attackAllEnemies(AttackEffect effect) {
		attackAllEnemies(effect, multiDamage);
	}


	protected void attackAllEnemies(AttackEffect effect, int[] damageAmounts) {
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player(), damageAmounts, damageTypeForTurn, effect));
	}

	public static void attackAll(AttackEffect effect, int[] damageAmounts, DamageType dmgForTurn)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player(), damageAmounts, dmgForTurn, effect));
	}

	protected void poisonAllEnemies(AbstractPlayer p, int amount)
	{
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			flash();
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if ((!monster.isDead) && (!monster.isDying)) 
				{
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new PoisonPower(monster, p, amount), amount));
				}
			}
		}

	}

	public void initializeNumberedCard() {
		playCount = 0;
	}

	public void addPlayCount() {
		for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
			DuelistCard nc = (DuelistCard) c;
			nc.playCount++;
		}
	}

	public static void summon(AbstractPlayer p, int SUMMONS)
	{
		// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
		if (!p.hasPower(SummonPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p, 0), 0)); }

		// Setup Pot of Generosity
		int potSummons = 0;
		int startSummons = p.getPower(SummonPower.POWER_ID).amount;
		SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
		int maxSummons = summonsInstance.MAX_SUMMONS;
		if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
		else { potSummons = SUMMONS; }

		// Add SUMMONS
		summonsInstance.amount += SUMMONS;

		// Check for Pot of Generosity
		if (p.hasPower(PotGenerosityPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new GainEnergyAction(potSummons)); }

		// Check for Summoning Sickness
		if (p.hasPower(SummonSicknessPower.POWER_ID)) { damageSelf(SUMMONS * p.getPower(SummonSicknessPower.POWER_ID).amount); }

		// Check for Slifer
		if (p.hasPower(SliferSkyPower.POWER_ID)) { channelRandomOrb(); } 

		// Update UI
		summonsInstance.updateCount(summonsInstance.amount);
		summonsInstance.updateDescription();
	}

	public static void summonLite(AbstractPlayer p, int SUMMONS)
	{
		// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
		if (!p.hasPower(SummonPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p, 0), 0)); }

		// Get summon power instance
		SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);

		// Add SUMMONS
		summonsInstance.amount += SUMMONS;

		// Update UI
		summonsInstance.updateCount(summonsInstance.amount);
		summonsInstance.updateDescription();
	}

	public static int getSummons(AbstractPlayer p)
	{
		if (!p.hasPower(SummonPower.POWER_ID)) { return 0; }
		else
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			return summonsInstance.amount;
		}
	}

	public static int getMaxSummons(AbstractPlayer p)
	{
		if (!p.hasPower(SummonPower.POWER_ID)) { return 0; }
		else
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			return summonsInstance.MAX_SUMMONS;
		}
	}

	public static void setMaxSummons(AbstractPlayer p, int amount)
	{
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			if (!p.hasRelic(MillenniumKey.ID)) { summonsInstance.MAX_SUMMONS = amount; }
			else { summonsInstance.MAX_SUMMONS = 4; }
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateDescription();
		}

	}

	public static void incMaxSummons(AbstractPlayer p, int amount)
	{
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			if (!p.hasRelic(MillenniumKey.ID)) { summonsInstance.MAX_SUMMONS += amount; }
			else { summonsInstance.MAX_SUMMONS = 4; }
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateDescription();
		}
	}

	public static void decMaxSummons(AbstractPlayer p, int amount)
	{
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			if (!p.hasRelic(MillenniumKey.ID)) { summonsInstance.MAX_SUMMONS -= amount; }
			else { summonsInstance.MAX_SUMMONS = 4; }
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateDescription();
		}
	}

	public static int tribute(AbstractPlayer p, int tributes, boolean tributeAll, DuelistCard card)
	{
		if (card.misc != 52)
		{
			// If no summons, just skip this so we don't crash
			// This should never be called without summons due to canUse() checking for tributes before use() can be run
			if (!p.hasPower(SummonPower.POWER_ID)) { return 0; }
			else
			{
				//	Check for Mausoleum of the Emperor
				if (p.hasPower(EmperorPower.POWER_ID))
				{
					EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
					if (empInstance.flag)
					{
						SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
						if (tributeAll) { tributes = summonsInstance.amount; }
						if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
						else { summonsInstance.amount -= tributes; }

						// Check for Obelisk after tributing
						if (p.hasPower(ObeliskPower.POWER_ID))
						{
							int[] temp = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
							for (int i : temp) { i = i * tributes; }
							AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
						}

						summonsInstance.updateCount(summonsInstance.amount);
						summonsInstance.updateDescription();

						// Check for Pharaoh's Curse
						if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

						// Check for Toon Tribute power
						if (p.hasPower(TributeToonPower.POWER_ID)) { addCardToHand(randomToonMonster("random")); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
						if (p.hasPower(TributeToonPowerB.POWER_ID)) { addCardToHand(randomToonCard("random")); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }
						
						return tributes;
					}
					else
					{
						empInstance.flag = true;
						return 0;
					}
				}
				else
				{

					SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
					if (tributeAll) { tributes = summonsInstance.amount; }
					if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
					else { summonsInstance.amount -= tributes; }

					// Check for Obelisk after tributing
					if (p.hasPower(ObeliskPower.POWER_ID))
					{
						int[] temp = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
						for (int i : temp) { i = i * tributes; }
						AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
					}

					summonsInstance.updateCount(summonsInstance.amount);
					summonsInstance.updateDescription();

					// Check for Pharaoh's Curse
					if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

					// Check for Toon Tribute powers
					if (p.hasPower(TributeToonPower.POWER_ID)) { addCardToHand(randomToonMonster("random")); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID)) { addCardToHand(randomToonCard("random")); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

					return tributes;
				}
			}
		}
		else
		{
			return 0;
		}

	}

	public static int powerTribute(AbstractPlayer p, int tributes, boolean tributeAll)
	{
		// If no summons, just skip this so we don't crash
		// This should never be called without summons due to canUse() checking for tributes before use() can be run
		if (!p.hasPower(SummonPower.POWER_ID)) { return 0; }
		else
		{
			//	Check for Mausoleum of the Emperor
			if (p.hasPower(EmperorPower.POWER_ID))
			{
				EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
				if (empInstance.flag)
				{
					SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
					if (tributeAll) { tributes = summonsInstance.amount; }
					if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
					else { summonsInstance.amount -= tributes; }

					// Check for Obelisk after tributing
					if (p.hasPower(ObeliskPower.POWER_ID))
					{
						int[] temp = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
						for (int i : temp) { i = i * tributes; }
						AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
					}

					summonsInstance.updateCount(summonsInstance.amount);
					summonsInstance.updateDescription();

					// Check for Pharaoh's Curse
					if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

					// Check for Toon Tribute power
					if (p.hasPower(TributeToonPower.POWER_ID)) { addCardToHand(randomToonMonster("random")); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID)) { addCardToHand(randomToonCard("random")); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }
					
					return tributes;
				}
				else
				{
					empInstance.flag = true;
					return 0;
				}
			}
			else
			{

				SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
				if (tributeAll) { tributes = summonsInstance.amount; }
				if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
				else { summonsInstance.amount -= tributes; }

				// Check for Obelisk after tributing
				if (p.hasPower(ObeliskPower.POWER_ID))
				{
					int[] temp = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
					for (int i : temp) { i = i * tributes; }
					AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
				}

				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateDescription();

				// Check for Pharaoh's Curse
				if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

				// Check for Toon Tribute power
				if (p.hasPower(TributeToonPower.POWER_ID)) { addCardToHand(randomToonMonster("random")); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
				if (p.hasPower(TributeToonPowerB.POWER_ID)) { addCardToHand(randomToonCard("random")); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }
				
				return tributes;
			}
		}
	}

	public void damageThroughBlock(AbstractCreature m, AbstractPlayer p, int damage, AttackEffect effect)
	{
		// Record target block and remove all of it
		int targetArmor = m.currentBlock;
		if (targetArmor > 0) { AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(m, m)); }

		// Deal direct damage to target HP
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), effect));

		// Restore original target block
		if (targetArmor > 0) { AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, m, targetArmor)); }
	}

	protected void upgradeName(String newName) 
	{
		this.timesUpgraded += 1;
		this.upgraded = true;
		this.name = newName;
		initializeTitle();
	}

	public static AbstractPower applyRandomBuff(AbstractCreature p, int turnNum)
	{
		// Setup powers array for random buff selection
		AbstractPower str = new StrengthPower(p, turnNum);
		AbstractPower dex = new DexterityPower(p, 1);
		AbstractPower art = new ArtifactPower(p, turnNum);
		AbstractPower plate = new PlatedArmorPower(p, turnNum);
		AbstractPower intan = new IntangiblePower(p, 1);
		AbstractPower regen = new RegenPower(p, turnNum);
		AbstractPower energy = new EnergizedPower(p, 1);
		AbstractPower thorns = new ThornsPower(p, turnNum);
		AbstractPower barricade = new BarricadePower(p);
		AbstractPower blur = new BlurPower(p, turnNum);
		AbstractPower burst = new BurstPower(p, turnNum);
		AbstractPower creative = new CreativeAIPower(p, 1); //probably too good
		AbstractPower darkEmb = new DarkEmbracePower(p, turnNum);
		AbstractPower doubleTap = new DoubleTapPower(p, turnNum);
		AbstractPower equal = new EquilibriumPower(p, 2);
		AbstractPower noPain = new FeelNoPainPower(p, turnNum);
		AbstractPower fire = new FireBreathingPower(p, 3);
		AbstractPower jugger = new JuggernautPower(p, turnNum);
		AbstractPower metal = new MetallicizePower(p, turnNum);
		AbstractPower penNib = new PenNibPower(p, 1);
		AbstractPower sadistic = new SadisticPower(p, turnNum);
		AbstractPower storm = new StormPower(p, 1);
		AbstractPower orbHeal = new OrbHealerPower(p, turnNum);
		AbstractPower tombLoot = new EnergyTreasurePower(p, turnNum);
		AbstractPower swordsBurn = new SwordsBurnPower(p, p);
		AbstractPower swordsConceal = new SwordsConcealPower(p, p, 1, false);
		AbstractPower orbEvoker = new OrbEvokerPower(p, turnNum);
		AbstractPower tombPilfer = new HealGoldPower(p, turnNum);
		AbstractPower toonTributeB = new TributeToonPowerB(p, turnNum);
		AbstractPower magicCylinder = new MagicCylinderPower(p, turnNum, false);
		AbstractPower retainCards = new RetainCardPower(p, 1);
		AbstractPower generosity = new PotGenerosityPower(p, p, 2);
		AbstractPower focus = new FocusPower(p, turnNum);
		AbstractPower[] buffs = new AbstractPower[] {str, dex, art, plate, intan, regen, energy, thorns, barricade, blur, 
				burst, darkEmb, doubleTap, equal, noPain, fire, jugger, metal, penNib, sadistic, storm, orbHeal, tombLoot,
				swordsBurn, orbEvoker, tombPilfer, swordsConceal, toonTributeB, magicCylinder, retainCards, 
				generosity, focus, creative };
		// Get randomized buff
		int randomBuffNum = ThreadLocalRandom.current().nextInt(0, buffs.length);
		AbstractPower randomBuff = buffs[randomBuffNum];
		
		ArrayList<AbstractPower> powers = p.powers;
		boolean found = false;
		for (AbstractPower a : powers)
		{
			if (a.name.equals(randomBuff.name))
			{
				found = true;
				a.amount += turnNum;
				a.updateDescription();
			}
		}
		
		if (!found) { randomBuff.updateDescription(); applyPower(randomBuff, p); }
		
		return randomBuff;
	}
	
	public static AbstractPower applyRandomBuffSmall(AbstractCreature p, int turnNum)
	{
		// Setup powers array for random buff selection
		AbstractPower str = new StrengthPower(p, turnNum);
		AbstractPower dex = new DexterityPower(p, 1);
		AbstractPower art = new ArtifactPower(p, turnNum);
		AbstractPower plate = new PlatedArmorPower(p, turnNum);
		AbstractPower regen = new RegenPower(p, turnNum);
		AbstractPower energy = new EnergizedPower(p, 1);
		AbstractPower thorns = new ThornsPower(p, turnNum);
		/*
		AbstractPower blur = new BlurPower(p, turnNum);
		AbstractPower burst = new BurstPower(p, 1);
		AbstractPower sadistic = new SadisticPower(p, turnNum);
		AbstractPower retainCards = new RetainCardPower(p, 1);
		AbstractPower toonTributeB = new TributeToonPowerB(p, turnNum);
		*/
		AbstractPower focus = new FocusPower(p, turnNum);
		/*
		AbstractPower[] buffs = new AbstractPower[] { str, dex, art, plate, regen, energy, thorns,
				blur, burst, sadistic, focus, retainCards, toonTributeB };
		*/
		AbstractPower[] buffs = new AbstractPower[] { str, dex, art, plate, regen, energy, thorns, focus };

		// Get randomized buff
		int randomBuffNum = ThreadLocalRandom.current().nextInt(0, buffs.length);
		AbstractPower randomBuff = buffs[randomBuffNum];
		
		ArrayList<AbstractPower> powers = p.powers;
		boolean found = false;
		for (AbstractPower a : powers)
		{
			if (a.name.equals(randomBuff.name))
			{
				found = true;
				a.amount += turnNum;
				a.updateDescription();
			}
		}
		
		if (!found) { randomBuff.updateDescription(); applyPower(randomBuff, p); }
		
		return randomBuff;
	}
	
	public static AbstractPower applyRandomBuffPlayer(AbstractPlayer p, int turnNum, boolean smallSet)
	{
		if (smallSet) { return applyRandomBuffSmall(p, turnNum); }
		else { return applyRandomBuff(p, turnNum); }
	}

	public static AbstractPower getRandomDebuff(AbstractPlayer p, AbstractMonster targetMonster, int turnNum)
	{
		// Setup powers array for random debuff selection
		AbstractPower slow = new SlowPower(targetMonster, turnNum);
		AbstractPower vulnerable = new VulnerablePower(targetMonster, turnNum, true);
		AbstractPower poison = new PoisonPower(targetMonster, p, turnNum);
		AbstractPower nPoison = new NecroticPoisonPower(targetMonster, p, turnNum);
		AbstractPower weak = new WeakPower(targetMonster, turnNum, false);
		AbstractPower goop = new SlimedPower(targetMonster, p, turnNum);
		AbstractPower blighted = new AmplifyDamagePower(targetMonster, turnNum);
		AbstractPower constricted = new ConstrictedPower(targetMonster, p, turnNum);
		AbstractPower[] debuffs = new AbstractPower[] {slow, vulnerable, poison, nPoison, weak, goop, blighted, constricted};

		// Get randomized debuff
		int randomDebuffNum = ThreadLocalRandom.current().nextInt(0, debuffs.length);
		AbstractPower randomDebuff = debuffs[randomDebuffNum];

		return randomDebuff;

	}

	public static AbstractPower getRandomPlayerDebuff(AbstractPlayer p, int turnNum)
	{
		// Setup powers array for random debuff selection
		AbstractPower slow = new SlowPower(p, turnNum);
		AbstractPower vulnerable = new VulnerablePower(p, turnNum, true);
		AbstractPower poison = new PoisonPower(p, p, turnNum);
		AbstractPower nPoison = new NecroticPoisonPower(p, p, turnNum);
		AbstractPower weak = new WeakPower(p, turnNum, false);
		AbstractPower entangled = new EntanglePower(p);
		AbstractPower hexed = new HexPower(p, turnNum);
		AbstractPower summonSick = new SummonSicknessPower(p, turnNum);
		AbstractPower tributeSick = new TributeSicknessPower(p, turnNum);
		AbstractPower evokeSick = new EvokeSicknessPower(p, turnNum);
		AbstractPower[] debuffs = new AbstractPower[] {slow, vulnerable, poison, nPoison, weak, entangled, hexed, summonSick, tributeSick, evokeSick};

		// Get randomized debuff
		int randomDebuffNum = ThreadLocalRandom.current().nextInt(0, debuffs.length);
		AbstractPower randomDebuff = debuffs[randomDebuffNum];

		return randomDebuff;

	}

	public static void addRandomCardToHand()
	{
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy(), false));
	}

	public static void addRandomCardToHand0Cost()
	{
		AbstractDungeon.actionManager.addToBottom(new Make0CostHandCardAction(AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy(), false));
	}

	public static void addCardToHand(AbstractCard card)
	{
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, false));
	}

	public static void channel(AbstractOrb orb)
	{
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orb));
	}

	public static void channelRandomOrb()
	{
		AbstractOrb[] orbs = new AbstractOrb[] { new Water(), new Lightning(), new Plasma(), new Dark(), new HellFireOrb(), new Frost(), new CrystalOrb(), new GlassOrb(), new Gate(), new Buffer(), new Summoner() };
		int randomOrb = ThreadLocalRandom.current().nextInt(0, orbs.length);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs[randomOrb]));
	}

	public static void evokeAll()
	{
		AbstractDungeon.actionManager.addToTop(new EvokeAllOrbsAction());
	}

	public static void gainGold(int amount, AbstractCreature owner, boolean rain)
	{
		AbstractDungeon.actionManager.addToBottom(new ObtainGoldAction(amount, owner, rain));
	}

	public static DuelistCard randomToonMonster(String name)
	{
		switch(name)
		{
		case "B.E. Toon Dragon":
			return new BlueEyesToon();
		case "Red Eyes Toon":
			return new RedEyesToon();
		case "Toon Barrel Dragon":
			return new ToonBarrelDragon();
		case "Toon Dark Magician":
			return new ToonDarkMagician();
		case "Toon D.M. Girl":
			return new ToonDarkMagicianGirl();
		case "Toon Gemini Elf":
			return new ToonGeminiElf();
		case "Toon Mermaid":
			return new ToonMermaid();
		case "Toon S. Skull":
			return new ToonSummonedSkull();
		default:
			String[] monsters = new String[] 
					{		
							"B. E. Toon Dragon", "Red Eyes Toon", "Toon Barrel Dragon", "Toon Dark Magician", 
							"Toon Gemini Elf","Toon Mermaid", "Toon S. Skull"
					};
			int randomCard = ThreadLocalRandom.current().nextInt(0, monsters.length);
			return randomToonMonster(monsters[randomCard]);
		}
	}

	public static DuelistCard randomToonMonster(AbstractCard c)
	{
		String name = c.name;
		switch(name)
		{
		case "B.E. Toon Dragon":
			return new BlueEyesToon();
		case "Red Eyes Toon":
			return new RedEyesToon();
		case "Toon Barrel Dragon":
			return new ToonBarrelDragon();
		case "Toon Dark Magician":
			return new ToonDarkMagician();
		case "Toon D.M. Girl":
			return new ToonDarkMagicianGirl();
		case "Toon Gemini Elf":
			return new ToonGeminiElf();
		case "Toon Mermaid":
			return new ToonMermaid();
		case "Toon S. Skull":
			return new ToonSummonedSkull();
		default:
			String[] monsters = new String[] 
				{		
						"B. E. Toon Dragon", "Red Eyes Toon", "Toon Barrel Dragon", "Toon Dark Magician", 
						"Toon Gemini Elf","Toon Mermaid", "Toon S. Skull"
				};
			int randomCard = ThreadLocalRandom.current().nextInt(0, monsters.length);
			return randomToonMonster(monsters[randomCard]);
		}
	}

	public static DuelistCard randomToonCard(String name)
	{
		switch(name)
		{
			case "B.E. Toon Dragon":
				return new BlueEyesToon();
			case "Red Eyes Toon":
				return new RedEyesToon();
			case "Toon Barrel Dragon":
				return new ToonBarrelDragon();
			case "Toon Dark Magician":
				return new ToonDarkMagician();
			case "Toon D.M. Girl":
				return new ToonDarkMagicianGirl();
			case "Toon Gemini Elf":
				return new ToonGeminiElf();
			case "Toon Mermaid":
				return new ToonMermaid();
			case "Toon S. Skull":
				return new ToonSummonedSkull();
			case "Toon Briefcase":
				return new ToonBriefcase();
			case "Toon World":
				return new ToonWorld();
			case "Toon Kingdom":
				return new ToonKingdom();
			case "Toon Magic":
				return new ToonMagic();
			case "Toon Mask":
				return new ToonMask();
			case "Toon Rollback":
				return new ToonRollback();
			default:
				String[] monsters = new String[] 
				{		
						"B. E. Toon Dragon", "Red Eyes Toon", "Toon Barrel Dragon", "Toon Dark Magician", 
						"Toon Gemini Elf","Toon Mermaid", "Toon S. Skull", "Toon Briefcase", "Toon World",
						"Toon Kingdom", "Toon Magic", "Toon Mask", "Toon Rolback"
				};
				int randomCard = ThreadLocalRandom.current().nextInt(0, monsters.length);
				return randomToonMonster(monsters[randomCard]);
		}
	}

	public static DuelistCard randomToonCard(AbstractCard c)
	{
		String name = c.name;
		switch(name)
		{
			case "B.E. Toon Dragon":
				return new BlueEyesToon();
			case "Red Eyes Toon":
				return new RedEyesToon();
			case "Toon Barrel Dragon":
				return new ToonBarrelDragon();
			case "Toon Dark Magician":
				return new ToonDarkMagician();
			case "Toon D.M. Girl":
				return new ToonDarkMagicianGirl();
			case "Toon Gemini Elf":
				return new ToonGeminiElf();
			case "Toon Mermaid":
				return new ToonMermaid();
			case "Toon S. Skull":
				return new ToonSummonedSkull();
			case "Toon Briefcase":
				return new ToonBriefcase();
			case "Toon World":
				return new ToonWorld();
			case "Toon Kingdom":
				return new ToonKingdom();
			case "Toon Magic":
				return new ToonMagic();
			case "Toon Mask":
				return new ToonMask();
			case "Toon Rollback":
				return new ToonRollback();
			default:
				String[] monsters = new String[] 
				{		
						"B. E. Toon Dragon", "Red Eyes Toon", "Toon Barrel Dragon", "Toon Dark Magician", 
						"Toon Gemini Elf","Toon Mermaid", "Toon S. Skull", "Toon Briefcase", "Toon World",
						"Toon Kingdom", "Toon Magic", "Toon Mask", "Toon Rolback"
				};
				int randomCard = ThreadLocalRandom.current().nextInt(0, monsters.length);
				return randomToonMonster(monsters[randomCard]);
		}
	}

	public static boolean isToon(String name)
	{
		switch(name)
		{
		case "B.E. Toon Dragon":
			return true;
		case "Red Eyes Toon":
			return true;
		case "Toon Barrel Dragon":
			return true;
		case "Toon Dark Magician":
			return true;
		case "Toon D.M. Girl":
			return true;
		case "Toon Gemini Elf":
			return true;
		case "Toon Mermaid":
			return true;
		case "Toon S. Skull":
			return true;
		case "Toon Kingdom":
			return true;
		case "Toon Magic":
			return true;
		case "Toon Mask":
			return true;
		case "Toon Rollback":
			return true;
		default:
			return false;
		}
	}

	// Returns a new copy of the monster passed (by its name)
	// Or if the name does not match the set of monsters, returns a new random monster from the set
	public DuelistCard newCopyOfMonster(String name)
	{
		switch(name)
		{
		case "A. Magnet Warrior":
			return new AlphaMagnet();
		case "Barrel Dragon":
			return new BarrelDragon();
		case "B. Magnet Warrior":
			return new BetaMagnet();
		case "B.E. White Dragon":
			return new BlueEyes();
		case "B.E. Toon Dragon":
			return new BlueEyesToon();
		case "B. Eyes Ultimate":
			return new BlueEyesUltimate();
		case "Buster Blader":
			return new BusterBlader();
		case "Cannon Soldier":
			return new CannonSoldier();
		case "Castle Dark Illusion":
			return new CastleDarkIllusions();
		case "Catapult Turtle":
			return new CatapultTurtle();
		case "Celtic Guardian":
			return new CelticGuardian();
		case "Darklord Marie":
			return new DarklordMarie();
		case "Dark Magician":
			return new DarkMagician();
		case "D.M. Girl":
			return new DarkMagicianGirl();
		case "Exodia Head":
			return new ExodiaHead();
		case "Exodia L. Arm":
			return new ExodiaLA();
		case "Exodia L. Leg":
			return new ExodiaLL();
		case "Exodia R. Arm":
			return new ExodiaRA();
		case "Exodia R. Leg":
			return new ExodiaRL();
		case "Fiend Megacyber":
			return new FiendMegacyber();
		case "Flame Swordsman":
			return new FlameSwordsman();
		case "G. Fierce Knight":
			return new GaiaFierce();
		case "G. Magnet Warrior":
			return new GammaMagnet();
		case "Gate Guardian":
			return new GateGuardian();
		case "Giant Stone Soldier":
			return new GiantSoldier();
		case "Injection Fairly Lily":
			return new InjectionFairy();
		case "Insect Queen":
			return new InsectQueen();
		case "Judge Man":
			return new JudgeMan();
		case "Kuriboh":
			return new Kuriboh();
		case "Labyrinth Wall":
			return new LabyrinthWall();
		case "Legendary Fisherman":
			return new LegendaryFisherman();
		case "Millennium Shield":
			return new MillenniumShield();
		case "Obelisk":
			return new ObeliskTormentor();
		case "Ojama Black":
			return new OjamaBlack();
		case "Ojama Green":
			return new OjamaGreen();
		case "Ojama King":
			return new OjamaKing();
		case "Ojama Knight":
			return new OjamaKnight();
		case "Ojama Yellow":
			return new OjamaYellow();
		case "Parasite Paracide":
			return new Parasite();
		case "Pumpking":
			return new Pumpking();
		case "Pumprincess":
			return new Pumprincess();
		case "R. Eyes Black Dragon":
			return new RedEyes();
		case "Relinquished":
			return new Relinquished();
		case "Kazejin":
			return new SangaEarth();
		case "Sanga of Thunder":
			return new SangaThunder();
		case "Suijin":
			return new SangaWater();
		case "7-Colored Fish":
			return new SevenColoredFish();
		case "Slifer Sky Dragon":
			return new SliferSky();
		case "Summoned Skull":
			return new SummonedSkull();
		case "Time Wizard":
			return new TimeWizard();
		case "Valkyrion":
			return new ValkMagnet();
		case "Red Eyes Toon":
			return new RedEyesToon();
		case "Superheavy Benkei":
			return new SuperheavyBenkei();
		case "Superheavy Scales":
			return new SuperheavyScales();
		case "Superheavy Swordsman":
			return new SuperheavySwordsman();
		case "Superheavy Waraji":
			return new SuperheavyWaraji();
		case "Toon Barrel Dragon":
			return new ToonBarrelDragon();
		case "Toon Dark Magician":
			return new ToonDarkMagician();
		case "Toon D.M. Girl":
			return new ToonDarkMagicianGirl();
		case "Toon Gemini Elf":
			return new ToonGeminiElf();
		case "Toon Mermaid":
			return new ToonMermaid();
		case "Toon S. Skull":
			return new ToonSummonedSkull();
		case "Gemini Elf":
			return new GeminiElf();
		case "Winged Dragon Ra":
			return new WingedDragonRa();
		case "Mini L. Wall":
			return new SmallLabyrinthWall();
		case "Baby Dragon":								
			return new BabyDragon();					
		case "Blizzard Dragon":							
			return new BlizzardDragon();
		case "Cave Dragon":
			return new CaveDragon();
		case "Fortress Warrior":
			return new FortressWarrior();
		case "Island Turtle":
			return new IslandTurtle();
		case "Mystical Elf":
			return new MysticalElf();
		case "Prevent Rat":
			return new PreventRat();
		case "Snowdust Dragon":
			return new SnowdustDragon();
		case "Snow Dragon":
			return new SnowDragon();
		case "Spirit of the Harp":
			return new SpiritHarp();
		case "Superheavy Brawler":
			return new SuperheavyBlueBrawler();
		case "Superheavy Daihachi":
			return new SuperheavyDaihachi();
		case "Superheavy Flutist":
			return new SuperheavyFlutist();
		case "Superheavy General":
			return new SuperheavyGeneral();
		case "Superheavy Magnet":
			return new SuperheavyMagnet();
		case "Superheavy Ogre":
			return new SuperheavyOgre();
		default:
			/*
				String[] monsters = new String[] 
				{		
					"A. Magnet Warrior", "Barrel Dragon", "B. Magnet Warrior", "B.E. White Dragon",  "B. E. Toon Dragon", "B. Eyes Ultimate",
					"Buster Blader", "Cannon Soldier", "Castle Dark Illusion", "Catapult Turtle", "Celtic Guardian", "Darklord Marie", "Dark Magician",
					"D.M. Girl", "Exodia Head", "Exodia L. Arm", "Exodia L. Leg", "Exodia R. Arm", "Exodia R. Leg", "Fiend Megacyber", "Flame Swordsman",
					"G. Fierce Knight", "G. Magnet Warrior", "Gate Guardian", "Giant Soldier", "Injection Fairly Lily", "Insect Queen", "Judge Man",
					"Kuriboh", "Labyrinth Wall", "Legendary Fisherman", "Millennium Shield", "Obelisk", "Ojama Black", "Ojama Green", "Ojama King",
					"Ojama Knight", "Ojama Yellow", "Parasite Paracide", "Pumpking", "Pumprincess", "R. Eyes Black Dragon", "Relinquished", "Kazejin",
					"Sanga of Thunder", "Suijin", "7-Colored Fish", "Slifer Sky Dragon", "Summoned Skull", "Time Wizard", "Valkyrion", "Red Eyes Toon",
					"Superheavy Benkei", "Superheavy Scales", "Superheavy Swordsman", "Superheavy Waraji", "Toon Barrel Dragon", "Toon Dark Magician", 
					"Toon D.M. Girl", "Toon Gemini Elf","Toon Mermaid", "Toon S. Skull", "Gemini Elf", "Winged Dragon Ra", "Mini L. Wall",
					"Baby Dragon", "Blizzard Dragon", "Cave Dragon", "Fortress Warrior", "Island Turtle", "Mystical Elf",
					"Prevent Rat", "Snowdust Dragon", "Snow Dragon", "Spirit of the Harp", "Superheavy Brawler", 
					"Superheavy Daihachi", "Superheavy Flutist", "Superheavy General", "Superheavy Magnet", "Superheavy Ogre"
				};
			 */
			String[] monsters = new String[] 
					{		
							"A. Magnet Warrior", "Barrel Dragon", "B. Magnet Warrior", "B.E. White Dragon",  "B. E. Toon Dragon", "B. Eyes Ultimate",
							"Buster Blader", "Cannon Soldier", "Castle Dark Illusion", "Catapult Turtle", "Celtic Guardian", "Darklord Marie", "Dark Magician",
							"D.M. Girl", "Exodia Head", "Exodia L. Arm", "Exodia L. Leg", "Exodia R. Arm", "Exodia R. Leg", "Fiend Megacyber", "Flame Swordsman",
							"G. Fierce Knight", "G. Magnet Warrior", "Gate Guardian", "Giant Soldier", "Injection Fairly Lily", "Insect Queen", "Judge Man",
							"Kuriboh", "Labyrinth Wall", "Legendary Fisherman", "Millennium Shield", "Obelisk", "Ojama Black", "Ojama Green", "Ojama King",
							"Ojama Knight", "Ojama Yellow", "Parasite Paracide", "Pumpking", "Pumprincess", "R. Eyes Black Dragon", "Relinquished", "Kazejin",
							"Sanga of Thunder", "Suijin", "7-Colored Fish", "Slifer Sky Dragon", "Summoned Skull", "Valkyrion", "Red Eyes Toon",
							"Superheavy Benkei", "Superheavy Scales", "Superheavy Swordsman", "Superheavy Waraji", "Toon Barrel Dragon", "Toon Dark Magician", 
							"Toon Gemini Elf","Toon Mermaid", "Toon S. Skull", "Gemini Elf", "Winged Dragon Ra", "Mini L. Wall",
							"Baby Dragon", "Blizzard Dragon", "Cave Dragon", "Fortress Warrior", "Island Turtle", "Mystical Elf",
							"Prevent Rat", "Snowdust Dragon", "Snow Dragon", "Spirit of the Harp", "Superheavy Brawler", 
							"Superheavy Daihachi", "Superheavy Flutist", "Superheavy General", "Superheavy Magnet", "Superheavy Ogre"
					};
			int randomCard = ThreadLocalRandom.current().nextInt(0, monsters.length);
			return newCopyOfMonster(monsters[randomCard]);
		}
	}

	// Same as above but defaults instead to just return 7-colored fish. Makes it much less likely the player will ever notice a bug
	public DuelistCard newCopyOfMonsterPumpkin(String name)
	{
		switch(name)
		{
		case "A. Magnet Warrior":
			return new AlphaMagnet();
		case "Barrel Dragon":
			return new BarrelDragon();
		case "B. Magnet Warrior":
			return new BetaMagnet();
		case "B.E. White Dragon":
			return new BlueEyes();
		case "B.E. Toon Dragon":
			return new BlueEyesToon();
		case "B. Eyes Ultimate":
			return new BlueEyesUltimate();
		case "Buster Blader":
			return new BusterBlader();
		case "Cannon Soldier":
			return new CannonSoldier();
		case "Castle Dark Illusion":
			return new CastleDarkIllusions();
		case "Catapult Turtle":
			return new CatapultTurtle();
		case "Celtic Guardian":
			return new CelticGuardian();
		case "Darklord Marie":
			return new DarklordMarie();
		case "Dark Magician":
			return new DarkMagician();
		case "D.M. Girl":
			return new DarkMagicianGirl();
		case "Exodia Head":
			return new ExodiaHead();
		case "Exodia L. Arm":
			return new ExodiaLA();
		case "Exodia L. Leg":
			return new ExodiaLL();
		case "Exodia R. Arm":
			return new ExodiaRA();
		case "Exodia R. Leg":
			return new ExodiaRL();
		case "Fiend Megacyber":
			return new FiendMegacyber();
		case "Flame Swordsman":
			return new FlameSwordsman();
		case "G. Fierce Knight":
			return new GaiaFierce();
		case "G. Magnet Warrior":
			return new GammaMagnet();
		case "Gate Guardian":
			return new GateGuardian();
		case "Giant Stone Soldier":
			return new GiantSoldier();
		case "Injection Fairy Lily":
			return new InjectionFairy();
		case "Insect Queen":
			return new InsectQueen();
		case "Judge Man":
			return new JudgeMan();
		case "Kuriboh":
			return new Kuriboh();
		case "Labyrinth Wall":
			return new LabyrinthWall();
		case "Legendary Fisherman":
			return new LegendaryFisherman();
		case "Millennium Shield":
			return new MillenniumShield();
		case "Obelisk":
			return new ObeliskTormentor();
		case "Ojama Black":
			return new OjamaBlack();
		case "Ojama Green":
			return new OjamaGreen();
		case "Ojama King":
			return new OjamaKing();
		case "Ojama Knight":
			return new OjamaKnight();
		case "Ojama Yellow":
			return new OjamaYellow();
		case "Parasite Paracide":
			return new Parasite();
		case "Pumpking":
			return new Pumpking();
		case "Pumprincess":
			return new Pumprincess();
		case "R. Eyes Black Dragon":
			return new RedEyes();
		case "Relinquished":
			return new Relinquished();
		case "Kazejin":
			return new SangaEarth();
		case "Sanga of Thunder":
			return new SangaThunder();
		case "Suijin":
			return new SangaWater();
		case "7-Colored Fish":
			return new SevenColoredFish();
		case "Slifer Sky Dragon":
			return new SliferSky();
		case "Summoned Skull":
			return new SummonedSkull();
		case "Time Wizard":
			return new TimeWizard();
		case "Valkyrion":
			return new ValkMagnet();
		case "Red Eyes Toon":
			return new RedEyesToon();
		case "Superheavy Benkei":
			return new SuperheavyBenkei();
		case "Superheavy Scales":
			return new SuperheavyScales();
		case "Superheavy Swordsman":
			return new SuperheavySwordsman();
		case "Superheavy Waraji":
			return new SuperheavyWaraji();
		case "Toon Barrel Dragon":
			return new ToonBarrelDragon();
		case "Toon Dark Magician":
			return new ToonDarkMagician();
		case "Toon D.M. Girl":
			return new ToonDarkMagicianGirl();
		case "Toon Gemini Elf":
			return new ToonGeminiElf();
		case "Toon Mermaid":
			return new ToonMermaid();
		case "Toon S. Skull":
			return new ToonSummonedSkull();
		case "Gemini Elf":
			return new GeminiElf();
		case "Winged Dragon Ra":
			return new WingedDragonRa();
		case "Mini L. Wall":
			return new SmallLabyrinthWall();
		case "Baby Dragon":
			return new BabyDragon();
		case "Blizzard Dragon":
			return new BlizzardDragon();
		case "Cave Dragon":
			return new CaveDragon();
		case "Fortress Warrior":
			return new FortressWarrior();
		case "Island Turtle":
			return new IslandTurtle();
		case "Mystical Elf":
			return new MysticalElf();
		case "Prevent Rat":
			return new PreventRat();
		case "Snowdust Dragon":
			return new SnowdustDragon();
		case "Snow Dragon":
			return new SnowDragon();
		case "Spirit of the Harp":
			return new SpiritHarp();
		case "Superheavy Brawler":
			return new SuperheavyBlueBrawler();
		case "Superheavy Daihachi":
			return new SuperheavyDaihachi();
		case "Superheavy Flutist":
			return new SuperheavyFlutist();
		case "Superheavy General":
			return new SuperheavyGeneral();
		case "Superheavy Magnet":
			return new SuperheavyMagnet();
		case "Superheavy Ogre":
			return new SuperheavyOgre();
		default:
			return newCopyOfMonster("7-Colored Fish");
		}
	}

	/*
	public static DuelistCard randomDuelistCard()
	{
		String[] monsters = new String[] 
		{		
					"A. Magnet Warrior", "Barrel Dragon", "B. Magnet Warrior", "B.E. White Dragon",  "B. E. Toon Dragon", "B. Eyes Ultimate",
					"Buster Blader", "Cannon Soldier", "Castle Dark Illusion", "Catapult Turtle", "Celtic Guardian", "Darklord Marie", "Dark Magician",
					"D.M. Girl", "Exodia Head", "Exodia L. Arm", "Exodia L. Leg", "Exodia R. Arm", "Exodia R. Leg", "Fiend Megacyber", "Flame Swordsman",
					"G. Fierce Knight", "G. Magnet Warrior", "Gate Guardian", "Giant Soldier", "Injection Fairly Lily", "Insect Queen", "Judge Man",
					"Kuriboh", "Labyrinth Wall", "Legendary Fisherman", "Millennium Shield", "Obelisk", "Ojama Black", "Ojama Green", "Ojama King",
					"Ojama Knight", "Ojama Yellow", "Parasite Paracide", "Pumpking", "Pumprincess", "R. Eyes Black Dragon", "Relinquished", "Kazejin",
					"Sanga of Thunder", "Suijin", "7-Colored Fish", "Slifer Sky Dragon", "Summoned Skull", "Time Wizard", "Valkyrion", "Red Eyes Toon",
					"Superheavy Benkei", "Superheavy Scales", "Superheavy Swordsman", "Superheavy Waraji", "Toon Barrel Dragon", "Toon Dark Magician", 
					"Toon D.M. Girl", "Toon Gemini Elf","Toon Mermaid", "Toon S. Skull", "Gemini Elf", "Winged Dragon Ra", "Mini L. Wall"
		};
		int randomCard = ThreadLocalRandom.current().nextInt(0, monsters.length);
		return newCopyOfCard(monsters[randomCard]);
	}
	 */
}
