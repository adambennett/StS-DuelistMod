package defaultmod.patches;

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
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import blackrusemod.powers.*;
import conspire.powers.HoldsTreasurePower;
import defaultmod.actions.common.LoseXEnergyAction;
import defaultmod.powers.*;
import slimebound.powers.SlimedPower;

public abstract class DuelistCard extends CustomCard 
{
	// CARD FIELDS
	public String upgradeType;
	public boolean isOverflow;
	public boolean flag;
	public int summons = 0;
	public int tributes = 0;
	public int upgradeDmg;
	public int upgradeSummons;
	public int counters;
	public int buffs;
	public int debuffs;
	public int heal;
	public int cards;
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
	// CARD FIELDS

	public DuelistCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET)
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
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
		AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, DAMAGE, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
	}

	protected AbstractPlayer player() {
		return AbstractDungeon.player;
	}

	protected boolean hasPower(String power) {
		return player().hasPower(power);
	}

	protected void applyPower(AbstractPower power, AbstractCreature target) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player(), power, power.amount));
	}
	
	protected void applyPower(AbstractPower power, AbstractCreature target, int amount) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player(), power, amount));
	}
	
	protected void heal(AbstractPlayer p, int amount)
	{
		AbstractDungeon.actionManager.addToTop(new HealAction(p, p, amount));
	}

	protected void block() {
		block(block);
	}

	protected void block(int amount) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player(), player(), amount));
	}

	protected void applyPowerToSelf(AbstractPower power) {
		applyPower(power, player());
	}

	public void draw(int cards) {
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player(), cards));
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
			summonsInstance.MAX_SUMMONS = amount;
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateDescription();
		}
	}
	
	public static void incMaxSummons(AbstractPlayer p, int amount)
	{
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			summonsInstance.MAX_SUMMONS += amount;
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateDescription();
		}
	}
	
	public static void decMaxSummons(AbstractPlayer p, int amount)
	{
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			summonsInstance.MAX_SUMMONS -= amount;
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateDescription();
		}
	}
	
	public static int tribute(AbstractPlayer p, int tributes, boolean tributeAll)
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
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), effect));
        
        // Restore original target block
        if (targetArmor > 0) { AbstractDungeon.actionManager.addToTop(new GainBlockAction(m, m, targetArmor)); }
	}

	protected void upgradeName(String newName) 
	{
		this.timesUpgraded += 1;
		this.upgraded = true;
		this.name = newName;
		initializeTitle();
	}
	
	public static AbstractPower getRandomBuff(AbstractPlayer p, int turnNum)
	{
		// Setup powers array for random buff selection
		AbstractPower str = new StrengthPower(p, turnNum);
		AbstractPower dex = new DexterityPower(p, turnNum);
		AbstractPower summons = new SummonPower(p, turnNum);
		AbstractPower art = new ArtifactPower(p, turnNum);
		AbstractPower plate = new PlatedArmorPower(p, turnNum);
		AbstractPower intan = new IntangiblePower(p, 1);
		AbstractPower regen = new RegenPower(p, turnNum);
		AbstractPower energy = new EnergizedPower(p, turnNum);
		AbstractPower thorns = new ThornsPower(p, turnNum);
		AbstractPower barricade = new BarricadePower(p);
		AbstractPower blur = new BlurPower(p, turnNum);
		AbstractPower burst = new BurstPower(p, turnNum);
		//AbstractPower creative = new CreativeAIPower(p, turnNum); probably too good
		AbstractPower darkEmb = new DarkEmbracePower(p, turnNum);
		AbstractPower doubleTap = new DoubleTapPower(p, turnNum);
		AbstractPower equal = new EquilibriumPower(p, turnNum);
		AbstractPower noPain = new FeelNoPainPower(p, turnNum);
		AbstractPower fire = new FireBreathingPower(p, turnNum);
		AbstractPower jugger = new JuggernautPower(p, turnNum);
		AbstractPower metal = new MetallicizePower(p, turnNum);
		AbstractPower penNib = new PenNibPower(p, 1);
		AbstractPower sadistic = new SadisticPower(p, turnNum);
		AbstractPower storm = new StormPower(p, turnNum);
		AbstractPower[] buffs = new AbstractPower[] {str, dex, summons, art, plate, intan, regen, energy, thorns, barricade, blur, burst, darkEmb, doubleTap, equal, noPain, fire, jugger, metal, penNib, sadistic, storm };

		// Get randomized buff
		int randomBuffNum = ThreadLocalRandom.current().nextInt(0, buffs.length);
		AbstractPower randomBuff = buffs[randomBuffNum];
		
		return randomBuff;
	}
	
	public static AbstractPower getRandomDebuff(AbstractPlayer p, AbstractMonster targetMonster, int turnNum)
	{
		// Setup powers array for random debuff selection
		AbstractPower slow = new SlowPower(targetMonster, turnNum);
		AbstractPower vulnerable = new VulnerablePower(targetMonster, turnNum, true);
		AbstractPower poison = new PoisonPower(targetMonster, p, turnNum);
		AbstractPower nPoison = new NecroticPoisonPower(targetMonster, p, turnNum);
		AbstractPower weak = new WeakPower(targetMonster, turnNum, false);
		AbstractPower matrix = new MatrixPower(targetMonster, turnNum);
		AbstractPower goop = new SlimedPower(targetMonster, p, turnNum);
		AbstractPower treasure = new HoldsTreasurePower(targetMonster);
		AbstractPower lifeLink = new LifeLinkPower(targetMonster, turnNum, false);
		AbstractPower aging = new AgingPower(targetMonster, turnNum);
		AbstractPower blighted = new AmplifyDamagePower(targetMonster, turnNum);
		AbstractPower[] debuffs = new AbstractPower[] {slow, vulnerable, poison, nPoison, weak, goop, treasure, aging, blighted, lifeLink, matrix};
		
		// Get randomized debuff
		int randomDebuffNum = ThreadLocalRandom.current().nextInt(0, debuffs.length);
		AbstractPower randomDebuff = debuffs[randomDebuffNum];
		
		return randomDebuff;

	}
	
	public static void addRandomCardToHand()
	{
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy(), false));
	}
	
	public static void addCardToHand(AbstractCard card)
	{
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, false));
	}
	
	public static void channel(AbstractOrb orb)
	{
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orb));
	}
	
	public static void evokeAll()
	{
		AbstractDungeon.actionManager.addToTop(new EvokeAllOrbsAction());
	}
}
