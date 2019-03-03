package defaultmod.patches;

import java.util.*;
import java.util.Map.Entry;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.actions.common.*;
import defaultmod.cards.*;
import defaultmod.interfaces.*;
import defaultmod.powers.*;
import defaultmod.relics.MillenniumKey;

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
	public boolean isSummon = false;
	public boolean isTribute = false;
	public boolean isCastle = false;
	public int summons = 0;
	public int tributes = 0;
	public int poisonAmt;
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
		this.misc = 0;
	}
	
	public DuelistCard(DuelistCard c, int cost)
	{
		super(c.cardID, c.name, c.textureImg, cost, c.rawDescription, c.type, c.color, c.rarity, c.target);
		this.originalName = c.name;
		this.misc = 0;
	}

	public DuelistCard getCard()
	{
		return this;
	}
	
	public abstract String getID();
	public abstract void onTribute(DuelistCard tributingCard);
	public abstract void onResummon(int summons);
	public abstract void summonThis(int summons, DuelistCard c, int var);
	public abstract void summonThis(int summons, DuelistCard c, int var, AbstractMonster m);

	public void becomeEthereal()
	{
		this.isEthereal = true;
		this.rawDescription = "Ethereal. NL " + this.rawDescription;
		this.initializeDescription();
	} 
	
	public static AbstractMonster getRandomMonster()
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		return m;
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

	public static void applyPower(AbstractPower power, AbstractCreature target) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player(), power, power.amount));
		
	}

	protected void applyPower(AbstractPower power, AbstractCreature target, int amount) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player(), power, amount));
		
	}

	public static void removePower(AbstractPower power, AbstractCreature target) {
		
		if (target.hasPower(power.ID))
		{
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, player(), power, power.amount));
		}
	}

	public static void reducePower(AbstractPower power, AbstractCreature target, int reduction) {
		
		if (target.hasPower(power.ID))
		{
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, player(), power, reduction));
		}		
	}

	public static void heal(AbstractPlayer p, int amount)
	{
		AbstractDungeon.actionManager.addToTop(new HealAction(p, p, amount));
	}

	protected void healMonster(AbstractMonster p, int amount)
	{
		AbstractDungeon.actionManager.addToTop(new HealAction(p, p, amount));
	}

	protected void block() 
	{
		block(block);
	}

	public void block(int amount) 
	{
		if (this.hasTag(DefaultMod.DRAGON) && player().hasPower(MountainPower.POWER_ID)) { amount = (int) Math.floor(amount * 1.5); }
		if (this.hasTag(DefaultMod.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID)) { amount = (int) Math.floor(amount * 1.5); }
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(player(), player(), amount));
	}
	
	public static void staticBlock(int amount) 
	{
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(player(), player(), amount));
	}

	public static void applyPowerToSelf(AbstractPower power) {
		applyPower(power, player());
		
	}

	public static void draw(int cards) {
		AbstractDungeon.actionManager.addToTop(new DrawCardAction(player(), cards));
	}

	public void drawBottom(int cards) {
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player(), cards));
	}

	public static void discard(int amount, boolean isRandom)
	{
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(player(), player(), amount, isRandom));
	}

	public void discardTop(int amount, boolean isRandom)
	{
		AbstractDungeon.actionManager.addToTop(new DiscardAction(player(), player(), amount, isRandom));
	}

	public static void gainEnergy(int energy) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energy));
	}

	protected void attack(AbstractMonster m, AttackEffect effect) 
	{
		attack(m, effect, damage);
	}

	protected void attack(AbstractMonster m, AttackEffect effect, int damageAmount) {
		if (this.hasTag(DefaultMod.DRAGON) && player().hasPower(MountainPower.POWER_ID)) {  damageAmount = (int) Math.floor(damageAmount * 1.5);  }
		if (this.hasTag(DefaultMod.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID)) {  damageAmount = (int) Math.floor(damageAmount * 1.5);  }
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
	
	public static void damageAllEnemies(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.POISON, damageArray, DamageType.NORMAL);
	}
	
	public static void damageAllEnemiesFire(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.FIRE, damageArray, DamageType.NORMAL);
	}
	
	public void damageThroughBlock(AbstractCreature m, AbstractPlayer p, int damage, AttackEffect effect)
	{
		if (this.hasTag(DefaultMod.DRAGON) && player().hasPower(MountainPower.POWER_ID)) { damage = (int) Math.floor(damage * 1.5); }
		if (this.hasTag(DefaultMod.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID)) { damage = (int) Math.floor(damage * 1.5); }
		
		// Record target block and remove all of it
		int targetArmor = m.currentBlock;
		if (targetArmor > 0) { AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(m, m)); }

		// Deal direct damage to target HP
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), effect));

		// Restore original target block
		if (targetArmor > 0) { AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, m, targetArmor)); }
	}
	
	public void damageThroughBlockAllEnemies(AbstractPlayer p, int damage, AttackEffect effect)
	{
		ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
		for (AbstractMonster m : monsters)
		{
			if (!m.isDead) 
			{ 
				damageThroughBlock(m, p, damage, effect); 
			}
		}
	}
	
	public void damageThroughBlockAllEnemies(AbstractPlayer p, int[] damageArray, AttackEffect effect)
	{
		ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
		int damageArrayIndex = 0;
		int damageThisCycle = damageArray[damageArrayIndex];
		for (AbstractMonster m : monsters)
		{
			if (!m.isDead) 
			{ 
				damageThroughBlock(m, p, damageThisCycle, effect); 
			}
			damageArrayIndex++;
			if (!((damageArrayIndex + 1) > damageArray.length)) { damageThisCycle = damageArray[damageArrayIndex]; }
			else { damageArrayIndex = 0; }
		}
	}
	
	public static void damageAllEnemiesThorns(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.POISON, damageArray, DamageType.THORNS);
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
	
	public static void summon(AbstractPlayer p, int SUMMONS, DuelistCard c)
	{
		// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
		if (!p.hasPower(SummonPower.POWER_ID))
		{
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS, c.originalName, "#b" + SUMMONS + " monsters summoned. Maximum of 5 Summons.", c), SUMMONS));
			int startSummons = SUMMONS;
			// Check for Pot of Generosity
			if (p.hasPower(PotGenerosityPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new GainEnergyAction(startSummons)); }
	
			// Check for Summoning Sickness
			if (p.hasPower(SummonSicknessPower.POWER_ID)) { damageSelf(startSummons * p.getPower(SummonSicknessPower.POWER_ID).amount); }
	
			// Check for Slifer
			if (p.hasPower(SliferSkyPower.POWER_ID)) 
			{ 
				channelRandom();
			} 
		}
		
		else
		{
			// Setup Pot of Generosity
			int potSummons = 0;
			int startSummons = p.getPower(SummonPower.POWER_ID).amount;
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			int maxSummons = summonsInstance.MAX_SUMMONS;
			if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
			else { potSummons = SUMMONS; }
	
			// Add SUMMONS
			summonsInstance.amount += potSummons;
			
			if (potSummons > 0) 
			{ 
				for (int i = 0; i < potSummons; i++) 
				{ 
					summonsInstance.summonList.add(c.originalName);
				} 
			}
	
			// Check for Pot of Generosity
			if (p.hasPower(PotGenerosityPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new GainEnergyAction(potSummons)); }
	
			// Check for Summoning Sickness
			if (p.hasPower(SummonSicknessPower.POWER_ID)) { damageSelf(potSummons * p.getPower(SummonSicknessPower.POWER_ID).amount); }
	
			// Check for Slifer
			if (p.hasPower(SliferSkyPower.POWER_ID)) 
			{ 
				channelRandom();
			} 
		
			
			// Update UI
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
			
			// Check for Trap Hole
			if (p.hasPower(TrapHolePower.POWER_ID) && !DefaultMod.checkTrap)
			{
				DefaultMod.checkTrap = true;
				for (int i = 0; i < potSummons; i++)
				{
					TrapHolePower power = (TrapHolePower) p.getPower(TrapHolePower.POWER_ID);
					int randomNum = AbstractDungeon.cardRandomRng.random(1, 10);
					if (randomNum <= power.chance || power.chance > 10)
					{
						power.flash();
						System.out.println("theDuelist:DuelistCard:summon ---> triggered trap hole with roll of: " + randomNum);
						tribute(p, 1, false, new TrapHole());
						DuelistCard cardCopy = DuelistCard.newCopyOfMonster(c.originalName);
						if (cardCopy != null)
						{
							if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
							if (c.upgraded) { cardCopy.upgrade(); }
							cardCopy.freeToPlayOnce = true;
							cardCopy.applyPowers();
							cardCopy.purgeOnUse = true;
							AbstractMonster m = AbstractDungeon.getRandomMonster();
							AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
							cardCopy.onResummon(1);
						}
					}
					else
					{
						System.out.println("theDuelist:DuelistCard:summon ---> did not trigger trap hole with roll of: " + randomNum);
					}
				}
			}
			DefaultMod.checkTrap = false;
			
			// Check for Yami
			if (p.hasPower(YamiPower.POWER_ID) && c.hasTag(DefaultMod.SPELLCASTER))
			{
				spellSummon(p, 1, c);
			}
			
			// Update UI
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
		}
	}
	
	public static void spellSummon(AbstractPlayer p, int SUMMONS, DuelistCard c)
	{
		// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
		if (!p.hasPower(SummonPower.POWER_ID))
		{
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS, c.originalName, "#b" + SUMMONS + " monsters summoned. Maximum of 5 Summons.", c), SUMMONS));
			int startSummons = SUMMONS;
			// Check for Pot of Generosity
			if (p.hasPower(PotGenerosityPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new GainEnergyAction(startSummons)); }
	
			// Check for Summoning Sickness
			if (p.hasPower(SummonSicknessPower.POWER_ID)) { damageSelf(startSummons * p.getPower(SummonSicknessPower.POWER_ID).amount); }
	
			// Check for Slifer
			if (p.hasPower(SliferSkyPower.POWER_ID)) 
			{ 
				channelRandom();
			} 
		}
		
		else
		{
			// Setup Pot of Generosity
			int potSummons = 0;
			int startSummons = p.getPower(SummonPower.POWER_ID).amount;
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			int maxSummons = summonsInstance.MAX_SUMMONS;
			if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
			else { potSummons = SUMMONS; }
	
			// Add SUMMONS
			summonsInstance.amount += potSummons;
			
			if (potSummons > 0) 
			{ 
				for (int i = 0; i < potSummons; i++) 
				{ 
					summonsInstance.summonList.add(c.originalName);
				} 
			}
	
			// Check for Pot of Generosity
			if (p.hasPower(PotGenerosityPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new GainEnergyAction(potSummons)); }
	
			// Check for Summoning Sickness
			if (p.hasPower(SummonSicknessPower.POWER_ID)) { damageSelf(potSummons * p.getPower(SummonSicknessPower.POWER_ID).amount); }
	
			// Check for Slifer
			if (p.hasPower(SliferSkyPower.POWER_ID)) 
			{ 
				channelRandom();
			}
			
			// Update UI
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();

			// Check for Trap Hole
			if (p.hasPower(TrapHolePower.POWER_ID) && !DefaultMod.checkTrap)
			{
				DefaultMod.checkTrap = true;
				for (int i = 0; i < potSummons; i++)
				{
					TrapHolePower power = (TrapHolePower) p.getPower(TrapHolePower.POWER_ID);
					int randomNum = AbstractDungeon.cardRandomRng.random(1, 10);
					if (randomNum <= power.chance || power.chance > 10)
					{
						power.flash();
						System.out.println("theDuelist:DuelistCard:spellSummon ---> triggered trap hole with roll of: " + randomNum);
						tribute(p, 1, false, new TrapHole());
						DuelistCard cardCopy = DuelistCard.newCopyOfMonster(c.originalName);
						if (cardCopy != null)
						{
							if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
							if (c.upgraded) { cardCopy.upgrade(); }
							cardCopy.freeToPlayOnce = true;
							cardCopy.applyPowers();
							cardCopy.purgeOnUse = true;
							AbstractMonster m = AbstractDungeon.getRandomMonster();
							AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
							cardCopy.onResummon(1);
						}
					}
					else
					{
						System.out.println("theDuelist:DuelistCard:spellSummon ---> did not trigger trap hole with roll of: " + randomNum);
					}
				}
			}
			DefaultMod.checkTrap = false;
			
			// Update UI
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
		}
	}
	

	public static void powerSummon(AbstractPlayer p, int SUMMONS, String cardName)
	{
		DuelistCard c = DefaultMod.summonMap.get(cardName);
		// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
		if (!p.hasPower(SummonPower.POWER_ID))
		{
			//DuelistCard newSummonCard = (DuelistCard) DefaultMod.summonMap.get(cardName).makeCopy();
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS, cardName, "#b" + SUMMONS + " monsters summoned. Maximum of 5 Summons."), SUMMONS));
			int startSummons = SUMMONS;
			// Check for Pot of Generosity
			if (p.hasPower(PotGenerosityPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new GainEnergyAction(startSummons)); }
	
			// Check for Summoning Sickness
			if (p.hasPower(SummonSicknessPower.POWER_ID)) { damageSelf(startSummons * p.getPower(SummonSicknessPower.POWER_ID).amount); }
	
			// Check for Slifer
			if (p.hasPower(SliferSkyPower.POWER_ID)) 
			{ 
				channelRandom();
			} 
		}
		else
		{
			// Setup Pot of Generosity
			int potSummons = 0;
			int startSummons = p.getPower(SummonPower.POWER_ID).amount;
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			int maxSummons = summonsInstance.MAX_SUMMONS;
			if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
			else { potSummons = SUMMONS; }
	
			// Add SUMMONS
			summonsInstance.amount += potSummons;
			
			if (potSummons > 0) { for (int i = 0; i < potSummons; i++) { summonsInstance.summonList.add(cardName); } }
	
			// Check for Pot of Generosity
			if (p.hasPower(PotGenerosityPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new GainEnergyAction(potSummons)); }
	
			// Check for Summoning Sickness
			if (p.hasPower(SummonSicknessPower.POWER_ID)) { damageSelf(potSummons * p.getPower(SummonSicknessPower.POWER_ID).amount); }
	
			// Check for Slifer
			if (p.hasPower(SliferSkyPower.POWER_ID)) 
			{
				channelRandom();
			} 
	
			// Update UI
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
			
			// Check for Trap Hole
			if (p.hasPower(TrapHolePower.POWER_ID) && !DefaultMod.checkTrap)
			{
				DefaultMod.checkTrap = true;
				for (int i = 0; i < potSummons; i++)
				{
					TrapHolePower power = (TrapHolePower) p.getPower(TrapHolePower.POWER_ID);
					int randomNum = AbstractDungeon.cardRandomRng.random(1, 10);
					if (randomNum <= power.chance || power.chance > 10)
					{
						power.flash();
						System.out.println("theDuelist:DuelistCard:powerSummon ---> triggered trap hole with roll of: " + randomNum);
						tribute(p, 1, false, new TrapHole());
						DuelistCard cardCopy = DuelistCard.newCopyOfMonster(c.originalName);
						if (cardCopy != null)
						{
							if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
							if (c.upgraded) { cardCopy.upgrade(); }
							cardCopy.freeToPlayOnce = true;
							cardCopy.applyPowers();
							cardCopy.purgeOnUse = true;
							AbstractMonster m = AbstractDungeon.getRandomMonster();
							AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
							cardCopy.onResummon(1);
						}
					}
					else
					{
						System.out.println("theDuelist:DuelistCard:powerSummon ---> did not trigger trap hole with roll of: " + randomNum);
					}
				}
			}
			DefaultMod.checkTrap = false;
			
			// Check for Yami
			if (p.hasPower(YamiPower.POWER_ID) && c.hasTag(DefaultMod.SPELLCASTER))
			{
				spellSummon(p, 1, c);
			}
			
			// Update UI
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
		}
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
			if (!p.hasRelic(MillenniumKey.ID)) { return summonsInstance.MAX_SUMMONS; }
			else 
			{ 
				if (summonsInstance.MAX_SUMMONS > 4) { summonsInstance.MAX_SUMMONS = 4;}
				return summonsInstance.MAX_SUMMONS;
			}
		}
	}

	public static void setMaxSummons(AbstractPlayer p, int amount)
	{
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			summonsInstance.MAX_SUMMONS = amount; DefaultMod.lastMaxSummons = amount;
			if (summonsInstance.MAX_SUMMONS > 4 && p.hasRelic(MillenniumKey.ID)) { summonsInstance.MAX_SUMMONS = 4; DefaultMod.lastMaxSummons = 4;}
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
		}

	}

	public static void incMaxSummons(AbstractPlayer p, int amount)
	{
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			summonsInstance.MAX_SUMMONS += amount; DefaultMod.lastMaxSummons += amount;
			if (summonsInstance.MAX_SUMMONS > 4 && p.hasRelic(MillenniumKey.ID)) { summonsInstance.MAX_SUMMONS = 4; DefaultMod.lastMaxSummons = 4;}
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
		}
	}

	public static void decMaxSummons(AbstractPlayer p, int amount)
	{
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			summonsInstance.MAX_SUMMONS -= amount; DefaultMod.lastMaxSummons -= amount;
			if (summonsInstance.MAX_SUMMONS > 4 && p.hasRelic(MillenniumKey.ID)) { summonsInstance.MAX_SUMMONS = 4; DefaultMod.lastMaxSummons = 4;}
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
		}
	}

	public static ArrayList<DuelistCard> tribute(AbstractPlayer p, int tributes, boolean tributeAll, DuelistCard card)
	{
		ArrayList<DuelistCard> tributeList = new ArrayList<DuelistCard>();
		if (card.misc != 52)
		{
			// If no summons, just skip this so we don't crash
			// This should never be called without summons due to canUse() checking for tributes before use() can be run
			if (!p.hasPower(SummonPower.POWER_ID)) { return tributeList; }
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

						// Check for Pharaoh's Curse
						if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

						// Check for Toon Tribute power
						if (p.hasPower(TributeToonPower.POWER_ID)) { addCardToHand(returnTrulyRandomFromSets(DefaultMod.MONSTER, DefaultMod.TOON)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
						if (p.hasPower(TributeToonPowerB.POWER_ID)) { addCardToHand(returnTrulyRandomFromSet(DefaultMod.TOON)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

						// Look through summonsList and remove #tributes strings
						if (tributes > 0) 
						{
							for (int i = 0; i < tributes; i++)
							{
								if (summonsInstance.summonList.size() > 0)
								{
									int endIndex = summonsInstance.summonList.size() - 1;
									DuelistCard temp = DefaultMod.summonMap.get(summonsInstance.summonList.get(endIndex));
									if (temp != null) { tributeList.add(temp); }
									//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
									summonsInstance.summonList.remove(endIndex);
								}
							}
						}
						
						
						summonsInstance.updateCount(summonsInstance.amount);
						summonsInstance.updateStringColors();
						summonsInstance.updateDescription();
						for (DuelistCard c : tributeList) { c.onTribute(card); System.out.println("theDuelist:DuelistCard:tribute():1 ---> Called " + c.originalName + "'s onTribute()"); }
						return tributeList;
					}
					else
					{
						empInstance.flag = true;
						return tributeList;
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

					// Check for Pharaoh's Curse
					if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

					// Check for Toon Tribute powers
					if (p.hasPower(TributeToonPower.POWER_ID)) { addCardToHand(returnTrulyRandomFromSets(DefaultMod.MONSTER, DefaultMod.TOON)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID)) { addCardToHand(returnTrulyRandomFromSet(DefaultMod.TOON)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

					// Look through summonsList and remove #tributes strings
					if (tributes > 0) 
					{
						for (int i = 0; i < tributes; i++)
						{
							if (summonsInstance.summonList.size() > 0)
							{								
								int endIndex = summonsInstance.summonList.size() - 1;
								DuelistCard temp = DefaultMod.summonMap.get(summonsInstance.summonList.get(endIndex));
								if (temp != null) { tributeList.add(temp); }
								//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
								summonsInstance.summonList.remove(endIndex);								
							}
						}
					}
					
					
					summonsInstance.updateCount(summonsInstance.amount);
					summonsInstance.updateStringColors();
					summonsInstance.updateDescription();
					for (DuelistCard c : tributeList) {c.onTribute(card); System.out.println("theDuelist:DuelistCard:tribute():2 ---> Called " + c.originalName + "'s onTribute()"); }
					return tributeList;
				}
			}
		}
		else
		{
			return tributeList;
		}

	}

	public static int powerTribute(AbstractPlayer p, int tributes, boolean tributeAll)
	{
		ArrayList<DuelistCard> tributeList = new ArrayList<DuelistCard>();
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

					// Check for Pharaoh's Curse
					if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

					// Check for Toon Tribute power
					if (p.hasPower(TributeToonPower.POWER_ID)) { addCardToHand(returnTrulyRandomFromSets(DefaultMod.MONSTER, DefaultMod.TOON)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID)) { addCardToHand(returnTrulyRandomFromSet(DefaultMod.TOON)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

					// Look through summonsList and remove #tributes strings					
					if (tributes > 0) 
					{
						for (int i = 0; i < tributes; i++)
						{
							if (summonsInstance.summonList.size() > 0)
							{
								int endIndex = summonsInstance.summonList.size() - 1;
								DuelistCard temp = DefaultMod.summonMap.get(summonsInstance.summonList.get(endIndex));
								if (temp != null) { tributeList.add(temp); }
								//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
								summonsInstance.summonList.remove(endIndex);
							}
						}
					}
					
					
					summonsInstance.updateCount(summonsInstance.amount);
					summonsInstance.updateStringColors();
					summonsInstance.updateDescription();
					for (DuelistCard c : tributeList) { c.onTribute(new Token()); System.out.println("theDuelist:DuelistCard:powerTribute():1 ---> Called " + c.originalName + "'s onTribute()"); }
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

				// Check for Pharaoh's Curse
				if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

				// Check for Toon Tribute power
				if (p.hasPower(TributeToonPower.POWER_ID)) { addCardToHand(returnTrulyRandomFromSets(DefaultMod.MONSTER, DefaultMod.TOON)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
				if (p.hasPower(TributeToonPowerB.POWER_ID)) { addCardToHand(returnTrulyRandomFromSet(DefaultMod.TOON)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }
				
				// Look through summonsList and remove #tributes strings
				if (tributes > 0) 
				{
					for (int i = 0; i < tributes; i++)
					{
						if (summonsInstance.summonList.size() > 0)
						{
							int endIndex = summonsInstance.summonList.size() - 1;
							DuelistCard temp = DefaultMod.summonMap.get(summonsInstance.summonList.get(endIndex));
							if (temp != null) { tributeList.add(temp); }
							//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
							summonsInstance.summonList.remove(endIndex);
						}
					}
				}
				
				
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				for (DuelistCard c : tributeList) {c.onTribute(new Token()); System.out.println("theDuelist:DuelistCard:powerTribute():2 ---> Called " + c.originalName + "'s onTribute()"); }
				return tributes;
			}
		}
	}
	
	
	public static void tributeChecker(AbstractPlayer p, int tributes)
	{
		// Check for Obelisk after tributing
		if (p.hasPower(ObeliskPower.POWER_ID))
		{
			int[] temp = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
			for (int i : temp) { i = i * tributes; }
			AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
		}
	
		// Check for Pharaoh's Curse
		if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }
	
		// Check for Toon Tribute powers
		if (p.hasPower(TributeToonPower.POWER_ID)) { addCardToHand(returnTrulyRandomFromSets(DefaultMod.MONSTER, DefaultMod.TOON)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
		if (p.hasPower(TributeToonPowerB.POWER_ID)) { addCardToHand(returnTrulyRandomFromSet(DefaultMod.TOON)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }
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
		AbstractPower intan = new IntangiblePlayerPower(p, 1);
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
		AbstractPower orbEvoker = new OrbEvokerPower(p, turnNum);
		AbstractPower tombPilfer = new HealGoldPower(p, turnNum);
		//AbstractPower toonTributeB = new TributeToonPowerB(p, turnNum);
		//AbstractPower magicCylinder = new MagicCylinderPower(p, turnNum, false);
		AbstractPower retainCards = new RetainCardPower(p, 1);
		AbstractPower generosity = new PotGenerosityPower(p, p, 2);
		AbstractPower focus = new FocusPower(p, turnNum);
		AbstractPower reductionist = new ReducerPower(p, turnNum);
		AbstractPower timeWizard = new TimeWizardPower(p, p, turnNum);
		AbstractPower[] buffs = new AbstractPower[] {str, dex, art, plate, intan, regen, energy, thorns, barricade, blur, 
				burst, darkEmb, doubleTap, equal, noPain, fire, jugger, metal, penNib, sadistic, storm, orbHeal, tombLoot,
				orbEvoker, tombPilfer, retainCards, timeWizard,
				generosity, focus, reductionist, creative }; 
		// Get randomized buff
		int randomBuffNum = AbstractDungeon.cardRandomRng.random(buffs.length - 1);
		AbstractPower randomBuff = buffs[randomBuffNum];
		for (int i = 0; i < buffs.length; i++)
		{
			System.out.println("theDuelist:DuelistCard:applyRandomBuff() ---> buffs[" + i + "]: " + buffs[i].name);
		}
		System.out.println("theDuelist:DuelistCard:applyRandomBuff() ---> generated random buff: " + randomBuff.name + " :: index was: " + randomBuffNum);
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
		AbstractPower focus = new FocusPower(p, turnNum);
		AbstractPower[] buffs = new AbstractPower[] { str, dex, art, plate, regen, energy, thorns, focus };

		// Get randomized buff
		int randomBuffNum = AbstractDungeon.cardRandomRng.random(buffs.length - 1);
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

	public static void channelBottom(AbstractOrb orb)
	{
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(orb));
	}
	
	public static void channelRandom()
	{
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperDualMod.channelRandomOrb(); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperCon.channelRandomOrb(); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { RandomOrbHelperRep.channelRandomOrb(); }
		else { RandomOrbHelper.channelRandomOrb(); }
	}

	public static void evokeAll()
	{
		AbstractDungeon.actionManager.addToTop(new EvokeAllOrbsAction());
	}
	
	public static void evoke(int amount)
	{
		AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(amount));
	}
	
	public static void removeOrbs(int amount)
	{
		for (int i = 0; i < amount; i++)
		{
			AbstractDungeon.actionManager.addToTop(new RemoveNextOrbAction());
		}
	}

	public static void gainGold(int amount, AbstractCreature owner, boolean rain)
	{
		AbstractDungeon.actionManager.addToBottom(new ObtainGoldAction(amount, owner, rain));
	}
	
	public static DuelistCard returnRandomFromArray(ArrayList<DuelistCard> tributeList)
	{
		return tributeList.get(AbstractDungeon.cardRandomRng.random(tributeList.size() - 1));
	}

	public static AbstractCard returnTrulyRandomFromSet(CardTags setToFindFrom) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DefaultMod.myCards)
		{
			if (card.hasTag(setToFindFrom)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		return dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
	}

	public static AbstractCard returnTrulyRandomDuelistCard() 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DefaultMod.myCards)
		{
			if (card instanceof DuelistCard) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		return dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
	}

	public static AbstractCard returnTrulyRandomFromSets(CardTags setToFindFrom, CardTags anotherSetToFindFrom) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DefaultMod.myCards)
		{
			if (card.hasTag(setToFindFrom) && card.hasTag(anotherSetToFindFrom)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		return dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
	}

	public static AbstractCard returnTrulyRandomFromEitherSet(CardTags setToFindFrom, CardTags anotherSetToFindFrom) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DefaultMod.myCards)
		{
			if (card.hasTag(setToFindFrom) || card.hasTag(anotherSetToFindFrom)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		return dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
	}

	public static AbstractCard returnTrulyRandomFromOnlyFirstSet(CardTags setToFindFrom, CardTags excludeSet) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DefaultMod.myCards)
		{
			if (card.hasTag(setToFindFrom) && !card.hasTag(excludeSet)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		return dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
	}

	public static AbstractCard returnTrulyRandomFromMultiSet(CardTags[] setsToFindFrom) 
	{
		// Assume card has all tags we want, until we find a missing one
		boolean matchedSet = true;

		// List to randomly select from after checking all cards
		ArrayList<AbstractCard> matchingGroup = new ArrayList<>();

		// Check all cards in library
		for (DuelistCard potentialMatchCard : DefaultMod.myCards)
		{
			// See if check card is missing any match tags
			for (CardTags t : setsToFindFrom) { if (!potentialMatchCard.hasTag(t)) { matchedSet = false; } }

			// If tags match every match set and card has no tags matching exclude sets, add to the list
			if (matchedSet) { matchingGroup.add(potentialMatchCard.makeCopy()); }
		}

		// Return a random card from the final list of cards that have tags from each matched set, and no tags from any of the exclude sets
		return matchingGroup.get(AbstractDungeon.cardRandomRng.random(matchingGroup.size() - 1));
	}


	public static AbstractCard returnTrulyRandomFromMultiSet(CardTags[] setsToFindFrom, CardTags[] excludeSets) 
	{
		// Assume card has all tags we want, until we find a missing one
		boolean matchedSet = true;

		// Assume the card does not have any bad tags, until we find one
		boolean matchedBadSet = false;

		// List to randomly select from after checking all cards
		ArrayList<AbstractCard> matchingGroup = new ArrayList<>();

		for (DuelistCard potentialMatchCard : DefaultMod.myCards)
		{
			// See if check card is missing any match tags
			for (CardTags t : setsToFindFrom) { if (!potentialMatchCard.hasTag(t)) { matchedSet = false; } }

			// If all the necessary tags are present on a card, now we need to make sure it does not have any of the exclude tags
			if (matchedSet)
			{
				// So check against every exclude tag
				for (CardTags s : excludeSets) { if (potentialMatchCard.hasTag(s)) { matchedBadSet = true; } }
			}

			// If tags match every match set and card has no tags matching exclude sets, add to the list
			if (matchedSet && !matchedBadSet) { matchingGroup.add(potentialMatchCard.makeCopy()); }
		}

		// Return a random card from the final list of cards that have tags from each matched set, and no tags from any of the exclude sets
		return matchingGroup.get(AbstractDungeon.cardRandomRng.random(matchingGroup.size() - 1));
	}

	public static boolean isToon(AbstractCard testCard)
	{
		for (DuelistCard card : DefaultMod.myCards)
		{
			if (card.hasTag(DefaultMod.TOON))
			{
				if (testCard.originalName.equals(card.originalName))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isDragon(AbstractCard testCard)
	{
		for (DuelistCard card : DefaultMod.myCards)
		{
			if (card.hasTag(DefaultMod.DRAGON))
			{
				if (testCard.originalName.equals(card.originalName))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static DuelistCard newCopyOfMonster(String name)
	{
		for (DuelistCard card : DefaultMod.myCards)
		{
			if (name.equals(card.originalName))
			{
				return (DuelistCard) card.makeCopy();
			}
		}
		return new SevenColoredFish();
	}

	public static void printSetDetails(CardTags[] setsToFindFrom) 
	{
		// Map that holds set info for printing at end
		Map<CardTags, Integer> tagMap = new HashMap<CardTags, Integer>();
		Map<CardTags, ArrayList<DuelistCard>> tagSet = new HashMap<CardTags, ArrayList<DuelistCard>>();
		for (CardTags t : setsToFindFrom) { tagMap.put(t, 0); tagSet.put(t, new ArrayList<DuelistCard>()); }

		// Check all cards in library
		for (DuelistCard potentialMatchCard : DefaultMod.myCards)
		{
			// See if check card is missing any match tags
			for (CardTags t : setsToFindFrom) 
			{
				if (potentialMatchCard.hasTag(t))
				{
					tagMap.put(t, tagMap.get(t) + 1);
					tagSet.get(t).add(potentialMatchCard);
				}
				
			}
		}

		Set<Entry<CardTags, Integer>> set = tagMap.entrySet();
		for (Entry<CardTags, Integer> t : set)
		{
			System.out.println("theDuelist:DuelistCard:printSetDetails() --- > START OF SET: " + t.getKey() + " --- " + t.getValue());
			for (DuelistCard c : tagSet.get(t.getKey()))
			{
				System.out.println(c.name);
			}
			System.out.println("theDuelist:DuelistCard:printSetDetails() --- > END OF SET: " + t.getKey());
		} 

	}
	

	
	 public static boolean purgeCard(AbstractCard toPurge) {
	        return purgeCard(toPurge.uuid);
	    }

	    private static boolean purgeCard(UUID targetUUID) {
	        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
	            if (c.uuid.equals(targetUUID)) {
	                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, Settings.WIDTH / 2, Settings.HEIGHT / 2));
	                AbstractDungeon.player.masterDeck.removeCard(c);
	                return true;
	            }
	        }
	        return false;
	    }
}
