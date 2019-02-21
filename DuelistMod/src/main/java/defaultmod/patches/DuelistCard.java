package defaultmod.patches;

import java.util.*;
import java.util.Map.Entry;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import blackrusemod.powers.AmplifyDamagePower;
import conspire.actions.ObtainGoldAction;
import conspire.orbs.Water;
import defaultmod.DefaultMod;
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
	public boolean isSummon = false;
	public boolean isTribute = false;
	public boolean isCastle = false;
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
	
	public abstract void onTribute(DuelistCard tributingCard);
	public abstract void onSummon(int summons);
	public abstract void summonThis(int summons, DuelistCard c, int var);
	public abstract void summonThis(int summons, DuelistCard c, int var, AbstractMonster m);

	public void becomeEthereal()
	{
		this.isEthereal = true;
		this.rawDescription = "Ethereal. NL " + this.rawDescription;
		this.initializeDescription();
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
	
	public static void damageAllEnemies(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.POISON, damageArray, DamageType.NORMAL);
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
		if (!p.hasPower(SummonPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p), 0)); }

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
				summonsInstance.summonList.add(c.originalName); summonsInstance.summonMap.put(c.originalName, c); 
			} 
			
			c.onSummon(potSummons);
			System.out.println("theDuelist:DuelistCard:summon() ---> Called " + c.originalName + "'s onSummon()");
		}

		// Check for Pot of Generosity
		if (p.hasPower(PotGenerosityPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new GainEnergyAction(potSummons)); }

		// Check for Summoning Sickness
		if (p.hasPower(SummonSicknessPower.POWER_ID)) { damageSelf(potSummons * p.getPower(SummonSicknessPower.POWER_ID).amount); }

		// Check for Slifer
		if (p.hasPower(SliferSkyPower.POWER_ID)) { channelRandomOrb(); } 
	
		
		// Update UI
		summonsInstance.updateCount(summonsInstance.amount);
		summonsInstance.updateDescription();
	}
	
	public static void powerSummon(AbstractPlayer p, int SUMMONS, String cardName)
	{
		// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
		if (!p.hasPower(SummonPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p), 0)); }

		// Setup Pot of Generosity
		int potSummons = 0;
		int startSummons = p.getPower(SummonPower.POWER_ID).amount;
		SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
		int maxSummons = summonsInstance.MAX_SUMMONS;
		if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
		else { potSummons = SUMMONS; }

		// Add SUMMONS
		summonsInstance.amount += potSummons;
		
		if (potSummons > 0) { for (int i = 0; i < potSummons; i++) { summonsInstance.summonList.add(cardName); summonsInstance.summonMap.put(cardName, new Token()); } }

		// Check for Pot of Generosity
		if (p.hasPower(PotGenerosityPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new GainEnergyAction(potSummons)); }

		// Check for Summoning Sickness
		if (p.hasPower(SummonSicknessPower.POWER_ID)) { damageSelf(potSummons * p.getPower(SummonSicknessPower.POWER_ID).amount); }

		// Check for Slifer
		if (p.hasPower(SliferSkyPower.POWER_ID)) { channelRandomOrb(); } 

		// Update UI
		summonsInstance.updateCount(summonsInstance.amount);
		summonsInstance.updateDescription();
	}

	public static void summonLite(AbstractPlayer p, int SUMMONS, String cardName)
	{
		// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
		if (!p.hasPower(SummonPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p), 0)); }

		// Get summon power instance
		SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);

		// Add SUMMONS
		summonsInstance.amount += SUMMONS;
		
		if (SUMMONS > 0) { for (int i = 0; i < SUMMONS; i++) { summonsInstance.summonList.add(cardName); summonsInstance.summonMap.put(cardName, new Token()); } }
		
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

	public static ArrayList<DuelistCard> tribute(AbstractPlayer p, int tributes, boolean tributeAll, DuelistCard card)
	{
		ArrayList<DuelistCard> tributeList = new ArrayList<DuelistCard>();
		if (card.misc != 52)
		{
			// If no summons, just skip this so we don't crash
			// This should never be called without summons due to canUse() checking for tributes before use() can be run
			if (!p.hasPower(SummonPower.POWER_ID)) { return null; }
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
									DuelistCard temp = summonsInstance.summonMap.get(summonsInstance.summonList.get(endIndex));
									if (temp != null) { tributeList.add(temp); }
									//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
									summonsInstance.summonList.remove(summonsInstance.summonList.get(endIndex));
								}
							}
						}
						
						
						summonsInstance.updateCount(summonsInstance.amount);
						summonsInstance.updateDescription();
						for (DuelistCard c : tributeList) { c.onTribute(card); System.out.println("theDuelist:DuelistCard:tribute():1 ---> Called " + c.originalName + "'s onTribute()"); }
						return tributeList;
					}
					else
					{
						empInstance.flag = true;
						return null;
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
								DuelistCard temp = summonsInstance.summonMap.get(summonsInstance.summonList.get(endIndex));
								if (temp != null) { tributeList.add(temp); }
								//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
								summonsInstance.summonList.remove(summonsInstance.summonList.get(endIndex));
							}
						}
					}
					
					
					summonsInstance.updateCount(summonsInstance.amount);
					summonsInstance.updateDescription();
					for (DuelistCard c : tributeList) {c.onTribute(card); System.out.println("theDuelist:DuelistCard:tribute():2 ---> Called " + c.originalName + "'s onTribute()"); }
					return tributeList;
				}
			}
		}
		else
		{
			return null;
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
								DuelistCard temp = summonsInstance.summonMap.get(summonsInstance.summonList.get(endIndex));
								if (temp != null) { tributeList.add(temp); }
								//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
								summonsInstance.summonList.remove(summonsInstance.summonList.get(endIndex));
							}
						}
					}
					
					
					summonsInstance.updateCount(summonsInstance.amount);
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
							DuelistCard temp = summonsInstance.summonMap.get(summonsInstance.summonList.get(endIndex));
							if (temp != null) { tributeList.add(temp); }
							//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
							summonsInstance.summonList.remove(summonsInstance.summonList.get(endIndex));
						}
					}
				}
				
				
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateDescription();
				for (DuelistCard c : tributeList) {c.onTribute(new Token()); System.out.println("theDuelist:DuelistCard:powerTribute():2 ---> Called " + c.originalName + "'s onTribute()"); }
				return tributes;
			}
		}
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
		//AbstractPower swordsBurn = new SwordsBurnPower(p, p);
		//AbstractPower swordsConceal = new SwordsConcealPower(p, p, 1, false);
		AbstractPower orbEvoker = new OrbEvokerPower(p, turnNum);
		AbstractPower tombPilfer = new HealGoldPower(p, turnNum);
		AbstractPower toonTributeB = new TributeToonPowerB(p, turnNum);
		AbstractPower magicCylinder = new MagicCylinderPower(p, turnNum, false);
		AbstractPower retainCards = new RetainCardPower(p, 1);
		AbstractPower generosity = new PotGenerosityPower(p, p, 2);
		AbstractPower focus = new FocusPower(p, turnNum);
		AbstractPower reductionist = new ReducerPower(p, turnNum);
		AbstractPower[] buffs = new AbstractPower[] {str, dex, art, plate, intan, regen, energy, thorns, barricade, blur, 
				burst, darkEmb, doubleTap, equal, noPain, fire, jugger, metal, penNib, sadistic, storm, orbHeal, tombLoot,
				orbEvoker, tombPilfer, toonTributeB, magicCylinder, retainCards, 
				generosity, focus, reductionist, creative }; 
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
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.length - 1);
		AbstractPower randomDebuff = debuffs[randomDebuffNum];

		return randomDebuff;

	}
	
	public static AbstractPower getRandomDebuffPotion(AbstractPlayer p, AbstractMonster targetMonster, int turnNum)
	{
		// Setup powers array for random debuff selection
		//AbstractPower slow = new SlowPower(targetMonster, turnNum );
		AbstractPower vulnerable = new VulnerablePower(targetMonster, turnNum, true);
		AbstractPower poison = new PoisonPower(targetMonster, p, turnNum);
		AbstractPower nPoison = new NecroticPoisonPower(targetMonster, p, turnNum);
		AbstractPower weak = new WeakPower(targetMonster, turnNum, true);
		AbstractPower goop = new SlimedPower(targetMonster, p, turnNum);
		AbstractPower blighted = new AmplifyDamagePower(targetMonster, turnNum);
		AbstractPower constricted = new ConstrictedPower(targetMonster, p, turnNum);
		AbstractPower[] debuffs = new AbstractPower[] {vulnerable, poison, nPoison, weak, goop, blighted, constricted};

		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.length - 1);
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
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.length - 1);
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
		AbstractOrb[] orbs = new AbstractOrb[] 
				{	
						new Water(), new Lightning(), new Plasma(), new Dark(), 
						new HellFireOrb(),new Frost(), new CrystalOrb(), new GlassOrb(), 
						new Gate(), new Buffer(), new Summoner(), new MonsterOrb(),
						new DragonOrb(), new ReducerOrb()
				};
		int randomOrb = AbstractDungeon.cardRandomRng.random(orbs.length - 1);
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orbs[randomOrb]));
	}

	public static void evokeAll()
	{
		AbstractDungeon.actionManager.addToTop(new EvokeAllOrbsAction());
	}
	
	public static void evoke(int amount)
	{
		AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(amount));
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

	public DuelistCard newCopyOfMonster(String name)
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
}
