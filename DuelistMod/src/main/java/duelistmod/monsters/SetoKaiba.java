package duelistmod.monsters;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.KaibaDrawHandAction;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.*;
import duelistmod.powers.enemyPowers.*;
// Hello, welcome to the default monster. For now, It'll show you how to add the Jaw Worm, as it was kindly PR'd to me.
// Later on, I might add any additional/unique things, if requested.
import duelistmod.variables.Tags;

public class SetoKaiba extends AbstractMonster {
	public static final String ID = DuelistMod.makeID("SetoKaiba"); // Monster ID (remember the prefix - yourModID:DefaultMonster)
	private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grab the string

	public static final String NAME = monsterstrings.NAME; // The name of the monster
	public static final String[] MOVES = monsterstrings.MOVES; // The names of the moves
	public static final String[] DIALOG = monsterstrings.DIALOG; // The dialogue (if any)

	private static final int HP_MIN = 71; // 1. The minimum and maximum amount of HP.
	private static final int HP_MAX = 85; // 2. Every monsters hp is "slightly random", falling between these values.

	private static final int A7_HP_MIN = 86; // HP moves up at Ascension 7.
	private static final int A7_HP_MAX = 101;
	
	private static final int DRAGON_HP_BONUS = 10;
	private static final int DRAGON_HP_BONUS_MAX = 20;

	private static final float HB_X = 0.0F;     // The hitbox X coordinate/position (relative to the monster)
	private static final float HB_Y = -25.0F;   // The Y position
	private static final float HB_W = 200.0F;   // Hitbox width
	private static final float HB_H = 330.0F;   // Hitbox Height

	private int energy = 3;
	private int handIndex = -1;
	private int preventRatOverflow = 2;
	private int backgroundOverflow = 1;
	private int summonsThisCombat = 0;
	private int tributesThisCombat = 0;
	private int summonsThisTurn = 0;
	private int armageddon = 11;
	private int earthGiant = 7;
	private int rollingMessage = 0;
	
	private ArrayList<ArrayList<String>> possibleHands = new ArrayList<ArrayList<String>>();
	private ArrayList<AbstractCard> cardsForNextMove = new ArrayList<AbstractCard>();
	private ArrayList<AbstractCard> overflowCards = new ArrayList<AbstractCard>();

	private boolean firstTurn = true;

	public SetoKaiba(float x, float y) 
	{		
		// Monster Setup
		super(NAME, "SetoKaiba", 44, HB_X, HB_Y, HB_W, HB_H, "duelistModResources/images/char/enemies/KaibaModel2.png", x, y); // Initializes the monster.
		setupHands();
		
		// HP Setup
		if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Dragon Deck"))
		{
			int roll = AbstractDungeon.aiRng.random(DRAGON_HP_BONUS, DRAGON_HP_BONUS_MAX);
			if (AbstractDungeon.ascensionLevel >= 7) { this.setHp(A7_HP_MIN + roll, A7_HP_MAX + roll); } 		
			else { this.setHp(HP_MIN + roll, HP_MAX + roll); }
		}
		else
		{
			if (AbstractDungeon.ascensionLevel >= 7) { this.setHp(A7_HP_MIN, A7_HP_MAX); } 		
			else { this.setHp(HP_MIN, HP_MAX); }
		}		
	}

	
	private int dragonsTributed(ArrayList<String> cards)
	{
		int total = 0;
		for (String s : cards)
		{
			if (s.equals("Baby Dragon"))
			{
				total++;
			}
			
			if (s.equals("Cave Dragon"))
			{
				total++;
			}
			
			if (s.equals("Background Dragon"))
			{
				total++;
			}
			
			if (s.equals("Dragon Token"))
			{
				total++;
			}
		}
		return total;
	}
	
	private int lambsTributed(ArrayList<String> cards)
	{
		int total = 0;
		for (String s : cards)
		{
			if (s.equals("Kuriboh Token"))
			{
				total++;
			}
		}
		return total;
	}
	
	private int localApplyBlkPowers(int baseBlock)
	{
		int blk = baseBlock;
		if (this.hasPower(DexterityPower.POWER_ID)) { blk+=this.getPower(DexterityPower.POWER_ID).amount; }
		return blk;
	}
	
	private int localApplyDmgPowers(int baseDamage)
	{
		int dmg = baseDamage;
		AbstractPlayer p = AbstractDungeon.player;
		if (this.hasPower(StrengthPower.POWER_ID)) { dmg+=this.getPower(StrengthPower.POWER_ID).amount; }
		if (this.hasPower(WeakPower.POWER_ID)) 
		{ 
			if (p.hasRelic("Paper Crane"))
			{
				dmg = (int) (dmg * 0.6F); 
			}
			else
			{
				dmg = (int) (dmg * 0.75F); 
			}
			
		}
		
		if (p.hasPower(SlowPower.POWER_ID))
		{
			int amt = p.getPower(SlowPower.POWER_ID).amount;
			float temp = dmg * (1.0F + amt * 0.1F);
			dmg = (int)temp;
		}
		
		if (p.hasPower(VulnerablePower.POWER_ID)) 
		{ 
			if (p.hasRelic("Odd Mushroom"))
			{
				dmg = (int) (dmg * 1.25F);
			}
			else if (p.hasRelic("Paper Frog"))
			{
				dmg = (int) (dmg * 1.75F);
			}
			else
			{
				dmg = (int) (dmg * 1.5F);
			}
		}
		
		if (p.hasPower(IntangiblePlayerPower.POWER_ID))
		{
			dmg = 1;
		}
		
		return dmg;
	}
	
	private boolean playMessage()
	{
		int roll = AbstractDungeon.miscRng.random(1, 35);
		roll += this.rollingMessage;
		if (roll > 34) { this.rollingMessage = 0; return true; }
		else { this.rollingMessage++; return false; }
	}
	
	private boolean playMessage(int bonus)
	{
		int roll = AbstractDungeon.miscRng.random(1, 35);
		roll += this.rollingMessage;
		roll += bonus;
		if (roll > 34) { this.rollingMessage = 0; return true; }
		else { this.rollingMessage++; return false; }
	}
	
	private void takeCardAction(AbstractCard c)
	{
		if (c instanceof GoldenApples)
		{
			int blk = localApplyBlkPowers(this.summonsThisCombat);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}
		
		if (c instanceof SilverApples)
		{
			int blk = localApplyBlkPowers(this.tributesThisCombat);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}
		
		if (c instanceof BabyDragon)
		{
			summon("Baby Dragon");
			summon("Baby Dragon");
			int blk = localApplyBlkPowers(3);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}
		
		if (c instanceof PreventRat)
		{
			summon("Prevent Rat");
			int blk = localApplyBlkPowers(5);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}
		
		if (c instanceof StrayLambs)
		{
			EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);		
			pow.incMaxSummons(1);
			summon("Kuriboh Token");
			summon("Kuriboh Token");				
		}
		
		if (c instanceof YamataDragon)
		{
			tribute(1, true);
			int dmg = localApplyDmgPowers(11);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx)); 
			if (playMessage()) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[8], 0.5F, 2.0F)); }
		}
		
		if (c instanceof FiveHeaded)
		{
			tribute(4, true);
			int dmg = localApplyDmgPowers(11);
			int times = 5;
			AttackEffect afx = AttackEffect.FIRE;
			if (playMessage(4)) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[5], 0.5F, 2.0F)); }
			for (int i = 0; i < times; i++)
			{
				AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx)); 
			}
		}
		
		if (c instanceof BlueEyes)
		{
			tribute(2, true);
			int dmg = localApplyDmgPowers(25);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx)); 
			if (playMessage(7)) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[4], 0.5F, 2.0F)); }
		}
		
		if (c instanceof ArmageddonDragonEmp)
		{
			tribute(this.armageddon, true);			
			this.armageddon = 11;
			int dmg = localApplyDmgPowers(85);
			AttackEffect afx = AttackEffect.FIRE;
			if (playMessage(20)) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[5], 0.5F, 2.0F)); }
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx)); 			
		}
		
		if (c instanceof BackgroundDragon)
		{
			summon("Background Dragon");
			int blk = localApplyBlkPowers(12);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}
		
		if (c instanceof CaveDragon)
		{
			summon("Cave Dragon");
			int blk = localApplyBlkPowers(8);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}
		
		if (c instanceof EarthGiant)
		{
			tribute(this.earthGiant, false);
			int blk = localApplyBlkPowers(50);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}
		
		if (c instanceof BerserkerCrush)
		{
			int dmg = localApplyDmgPowers(11);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx)); 
			DuelistCard.applyPower(new StrengthPower(AbstractDungeon.player, -2), AbstractDungeon.player);
		}
		
		if (c instanceof Reinforcements)
		{
			DuelistCard.applyPower(new StrengthPower(this, 2), this);
		}
		
		if (c instanceof Hinotama)
		{
			int timesRoll = 0;
			if (c.upgraded)
			{
				timesRoll = AbstractDungeon.aiRng.random(2, 4);
			}
			else
			{
				timesRoll = AbstractDungeon.aiRng.random(2, 3);
			}
			int dmg = localApplyDmgPowers(4);
			AttackEffect afx = AttackEffect.FIRE;
			for (int i = 0; i < timesRoll; i++)
			{
				AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx)); 
			}
		}
		
		if (c instanceof TotemDragon)
		{
			summon("Totem Dragon");
			DuelistCard.applyPower(new EnemyTotemPower(this, this, 3, this), this);
		}
		
		if (c instanceof ScrapFactory)
		{
			tribute(2, false);			
		}
		
		if (c instanceof BlueEyesUltimate)
		{
			tribute(3, true);
			int dmg = localApplyDmgPowers(45);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx)); 
			if (playMessage(5)) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[3], 0.5F, 2.0F)); }
		}
		
		if (c instanceof StardustDragon)
		{
			tribute(3, true);
			if (this.hasPower(StrengthPower.POWER_ID)) { DuelistCard.applyPower(new StrengthPower(this, this.getPower(StrengthPower.POWER_ID).amount), this); }
			int dmg = localApplyDmgPowers(22);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx)); 
			DuelistCard.applyPower(new SlowPower(AbstractDungeon.player, 6), AbstractDungeon.player);
			if (playMessage()) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[7], 0.5F, 2.0F)); }
		}
		
		if (c instanceof BusterBlader)
		{
			ArrayList<String> tribs = tribute(3, false);
			int dragsTributed = dragonsTributed(tribs);
			int dmg = localApplyDmgPowers(16 + (dragsTributed * 5));
			AttackEffect afx = AttackEffect.SLASH_DIAGONAL;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
		}
		
		if (c instanceof RedEyes)
		{
			tribute(3, true);
			int dmg = localApplyDmgPowers(14);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx)); 
			DuelistCard.applyPower(new WeakPower(AbstractDungeon.player, 3, true), AbstractDungeon.player);
		}
		
		if (c instanceof FiendSkull)
		{
			tribute(3, true);
			int dmg = localApplyDmgPowers(15);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx)); 
			DuelistCard.applyPower(new VulnerablePower(AbstractDungeon.player, 3, true), AbstractDungeon.player);
			if (playMessage()) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[6], 0.5F, 2.0F)); }
		}
		
		if (c instanceof BoosterDragon)
		{
			tribute(2, true);
			DuelistCard.applyPower(new EnemyBoosterDragonPower(this, this, 6), this);
		}
		
		if (c instanceof PotAvarice)
		{
			ArrayList<String> sizeRef = tributeAll(false);
			if (this.hasPower(EnemySummonsPower.POWER_ID))
			{
				EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
				pow.incMaxSummons(sizeRef.size());
			}
		}
		
		if (c instanceof MiraculousDescentEnemy)
		{
			DuelistCard.applyPower(new EnemyMiraclePower(this, this, AbstractDungeon.aiRng.random(5, 10)), this);
		}
		
		if (c instanceof LabyrinthWall)
		{
			tribute(2, false);
			int blk = localApplyBlkPowers(20);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}
		
		if (c instanceof EarthGiant)
		{
			tribute(this.earthGiant, false);
			int blk = localApplyBlkPowers(50);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}
		
		if (c instanceof RedMedicine)
		{
			int roll = AbstractDungeon.aiRng.random(1, 9);
			int turns = AbstractDungeon.aiRng.random(1, 3);
			switch (roll)
			{
				case 1:
					DuelistCard.applyPower(new StrengthPower(this, turns), this);
					break;
				case 2:
					DuelistCard.applyPower(new DexterityPower(this, turns), this);
					break;
				case 3:
					DuelistCard.applyPower(new RegenPower(this, turns), this);
					break;
				case 4:
					DuelistCard.applyPower(new IntangiblePower(this, 1), this);
					break;
				case 5:
					DuelistCard.applyPower(new ThornsPower(this, turns), this);
					break;
				case 6:
					DuelistCard.applyPower(new BarricadePower(this), this);
					break;
				case 7:
					DuelistCard.applyPower(new MetallicizePower(this, turns), this);
					break;
				case 8:
					DuelistCard.applyPower(new EnvenomPower(this, turns), this);
					break;
				case 9:
					DuelistCard.applyPower(new AngerPower(this, turns), this);
					break;
				default:
					DuelistCard.applyPower(new StrengthPower(this, turns), this);
					break;
			}
		}
		
		if (c.hasTag(Tags.DRAGON) && this.armageddon > 0)
		{
			this.armageddon--;
		}
		
		if (c.type.equals(CardType.SKILL) && this.earthGiant > 0)
		{
			this.earthGiant--;
		}
	}

	private ArrayList<String> generateNewHand()
	{
		int handRoll = AbstractDungeon.aiRng.random(this.possibleHands.size() - 1);
		ArrayList<String> newHand = this.possibleHands.get(handRoll);
		Collections.shuffle(newHand);
		this.handIndex = handRoll;
		Util.log("Kaiba new hand index:" + this.handIndex);
		return newHand;
	}

	public void drawNewHand()
	{
		if (this.hasPower(EnemyMiraclePower.POWER_ID))
		{
			EnemyMiraclePower pow = (EnemyMiraclePower)this.getPower(EnemyMiraclePower.POWER_ID);
			pow.trigger(this);
		}
		if (this.summonsThisTurn > 1)
		{
			int roll = AbstractDungeon.aiRng.random(1, 100);
			if (DuelistMod.addTokens) { roll = 1; }
			if (roll == 1)
			{
				AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Did you just summon a whole bunch of monsters in one turn? Isn't that against the rules?", 3.5F, 3.0F));
				AbstractDungeon.actionManager.addToBottom(new TalkAction(this, "Screw the rules, I have money!", 1.0F, 2.0F));
			}
		}
		this.summonsThisTurn = 0;
		if (this.hasPower(EnemyHandPower.POWER_ID))
		{
			this.overflowCards.clear();
			ArrayList<String> newHand = generateNewHand();
			EnemyHandPower pow = (EnemyHandPower)this.getPower(EnemyHandPower.POWER_ID);
			pow.fillHand(newHand);
			pow.flash();
			if (this.hasPower(EnemyEnergyPower.POWER_ID))
			{
				this.getPower(EnemyEnergyPower.POWER_ID).amount = energy;
				this.getPower(EnemyEnergyPower.POWER_ID).updateDescription();
			}
			else
			{
				DuelistCard.applyPower(new EnemyEnergyPower(this, this, this.energy, this.name), this);
			}
		}
		else
		{
			DuelistCard.applyPower(new EnemyHandPower(this, generateNewHand()), this);
		}
	}
	
	
	public void playCards(ArrayList<AbstractCard> cards)
	{	
		ArrayList<AbstractCard> reversed = new ArrayList<AbstractCard>();
		for (AbstractCard c : cards) { reversed.add(c); }
		Collections.reverse(reversed);
		for (AbstractCard card : reversed)
		{
			AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(card));
		}
		for (AbstractCard card : cards)
		{
			Util.log("Kaiba played " + card.name);
			//AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(card));
			takeCardAction(card);
		}	
	}
	
	public ArrayList<String> tribute(int amount, boolean dragon)
	{
		ArrayList<String> tribs = new ArrayList<String>();
		if (this.hasPower(EnemySummonsPower.POWER_ID))
		{
			EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
			tribs = pow.tribute(amount);
			this.tributesThisCombat += tribs.size();
		}
		if (dragon)
		{
			int dragsTributed = dragonsTributed(tribs);
			if (dragsTributed > 0) { DuelistCard.applyPower(new StrengthPower(this, dragsTributed), this); }
			if (this.hasPower(EnemyBoosterDragonPower.POWER_ID)) { EnemyBoosterDragonPower pow = (EnemyBoosterDragonPower)this.getPower(EnemyBoosterDragonPower.POWER_ID); for (int i = 0; i < dragsTributed; i++) { pow.trigger(this); }}
		}
		else
		{
			int dragsTributed = lambsTributed(tribs);
			if (dragsTributed > 0) { DuelistCard.applyPower(new IntangiblePlayerPower(this, dragsTributed), this); }
		}
		return tribs;
	}
	
	public ArrayList<String> tributeAll(boolean dragon)
	{
		ArrayList<String> tribs = new ArrayList<String>();
		if (this.hasPower(EnemySummonsPower.POWER_ID))
		{
			EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
			tribs = pow.tributeAll();
			this.tributesThisCombat += tribs.size();
		}
		if (dragon)
		{
			int dragsTributed = dragonsTributed(tribs);
			if (dragsTributed > 0) { DuelistCard.applyPower(new StrengthPower(this, dragsTributed), this); }
			if (this.hasPower(EnemyBoosterDragonPower.POWER_ID)) { EnemyBoosterDragonPower pow = (EnemyBoosterDragonPower)this.getPower(EnemyBoosterDragonPower.POWER_ID); for (int i = 0; i < dragsTributed; i++) { pow.trigger(this); }}
		}
		else
		{
			int dragsTributed = lambsTributed(tribs);
			if (dragsTributed > 0) { DuelistCard.applyPower(new IntangiblePlayerPower(this, dragsTributed), this); }
		}
		return tribs;
	}

	public void summon(String card)
	{
		if (this.hasPower(EnemySummonsPower.POWER_ID))
		{
			EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
			if (pow.addCardToSummons(card)) { this.summonsThisCombat++; this.summonsThisTurn++; }
		}
		else
		{
			DuelistCard.applyPower(new EnemySummonsPower(this, 1, card), this);
		}
	}
	
	public void summon(String card, int amount)
	{
		for (int i = 0; i < amount; i++) { summon(card); }
	}

	public void usePreBattleAction() 
	{
		ArrayList<String> startingHand = this.possibleHands.get(0);	
		handIndex = 0;
		this.cardsForNextMove = getHandMove();
		DuelistCard.applyPower(new EnemyEnergyPower(this, this, this.energy, this.name), this);
		DuelistCard.applyPower(new EnemyHandPower(this, startingHand), this);
		if (DuelistMod.forcePuzzleSummons) { DuelistCard.applyPower(new EnemySummonsPower(this, 2, "Dragon Token"), this); }
		else { DuelistCard.applyPower(new EnemySummonsPower(this, 1, "Dragon Token"), this); }
	}
	
	private void handleOverflows(AbstractCard c)
	{
		if (c instanceof PreventRat && this.preventRatOverflow > 0)
		{
			this.preventRatOverflow--;
			AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 0.5F, 2.0F));
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 3));
		}
		
		if (c instanceof BackgroundDragon && this.backgroundOverflow > 0)
		{
			this.backgroundOverflow--;
			AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 0.5F, 2.0F));
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 8));
			
		}
	}

	public void takeTurn() 
	{
		if (this.firstTurn) 
		{ 
			if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Dragon Deck")) {  AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 0.5F, 2.0F)); }
			else
			{
				AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 2.5F, 3.0F)); // Speak the stuff in DIALOG[0],
				this.firstTurn = false; // Then ensure it's no longer the first turn.
			}
		}
		if (this.cardsForNextMove.size() > 0)
		{
			playCards(this.cardsForNextMove);     
			for (AbstractCard c : overflowCards) { handleOverflows(c); }
			AbstractDungeon.actionManager.addToBottom(new KaibaDrawHandAction(this));
		}
		AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
	}

	// Gets a number for movement. This is set once at the beginning of combat and never changed. It's seeded. You can use this to make a few "archetype AI"s for enemies.
	protected void getMove(int num) 
	{ 
		this.cardsForNextMove = getHandMove();
		//this.setMove(MOVES[0], (byte)2, YourCustomIntent.PLAY_CARDS);
		//this.setMove(MOVES[0], (byte)2, Intent.MAGIC);
	}
	
	public void resetHand()
	{
		this.cardsForNextMove = getHandMove();
	}
	
	public void triggerHandReset()
	{
		//AbstractDungeon.actionManager.addToBottom(new KaibaResetHandAction(this));
		AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
	}

	public void die() { // When this monster dies...
		super.die(); // It, uh, dies...
		CardCrawlGame.sound.play("JAW_WORM_DEATH"); // And it croaks too.
	}
	
	@SuppressWarnings("unused")
	private ArrayList<AbstractCard> getHandMove()
	{
		ArrayList<AbstractCard> moveCards = new ArrayList<AbstractCard>();
		int moveRoll = AbstractDungeon.aiRng.random(1, 5);
		int summons = 0;
		boolean hasLambs = false;
		switch (this.handIndex)
		{
			case 0:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				if (summons > 3)
				{
					moveCards.add(new ScrapFactory());
					moveCards.add(new BabyDragon());
					moveCards.add(new FiveHeaded());
					this.setMove((byte)2, Intent.ATTACK, 55);
				}
				else if (summons > 1)
				{
					moveCards.add(new ScrapFactory());
					AbstractCard mir = new MiraculousDescentEnemy();
					mir.upgrade();
					moveCards.add(mir);
					moveCards.add(new BabyDragon());
					moveCards.add(new Reinforcements());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 3);
					if (roll == 1)
					{
						AbstractCard mir = new MiraculousDescentEnemy();
						mir.upgrade();
						moveCards.add(mir);
						moveCards.add(new BabyDragon());
						this.setMove((byte)2, Intent.DEFEND);
					}
					else if (roll == 2)
					{
						AbstractCard mir = new MiraculousDescentEnemy();
						mir.upgrade();
						moveCards.add(mir);
						moveCards.add(new Reinforcements());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
					}
					else
					{
						moveCards.add(new Reinforcements());
						moveCards.add(new BabyDragon());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
					}
				}
				break;
			case 1:
				switch (moveRoll)
				{
					case 1:
						moveCards.add(new BabyDragon());
						moveCards.add(new BabyDragon());
						moveCards.add(new YamataDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
						break;
					case 2:
						moveCards.add(new StrayLambs());
						moveCards.add(new ScrapFactory());
						moveCards.add(new BabyDragon());
						moveCards.add(new BabyDragon());
						moveCards.add(new YamataDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
						break;
					case 3:
						moveCards.add(new BabyDragon());
						moveCards.add(new YamataDragon());
						moveCards.add(new BabyDragon());				
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
						break;
					case 4:
						moveCards.add(new BabyDragon());
						moveCards.add(new StrayLambs());
						moveCards.add(new ScrapFactory());
						moveCards.add(new BabyDragon());
						this.setMove((byte)2, Intent.DEFEND);
						break;
					case 5:
						moveCards.add(new BabyDragon());
						moveCards.add(new BabyDragon());
						this.setMove((byte)2, Intent.DEFEND);
						break;
				}
				break;
			case 2:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 1)
				{
					moveCards.add(new BlueEyes());
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 25);
				}
				else if (summons == 1)
				{					
					moveCards.add(new CaveDragon());
					moveCards.add(new BlueEyes());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 25);
				}
				else
				{
					switch (moveRoll)
					{
						case 1:
							moveCards.add(new CaveDragon());
							moveCards.add(new BerserkerCrush());
							this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
							break;
						case 2:
							moveCards.add(new CaveDragon());
							moveCards.add(new StrayLambs());
							this.setMove((byte)2, Intent.DEFEND);
							break;
						case 3:
							moveCards.add(new BabyDragon());
							moveCards.add(new BerserkerCrush());
							this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
							break;
						case 4:
							moveCards.add(new CaveDragon());
							moveCards.add(new BerserkerCrush());
							this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
							break;
						case 5:
							moveCards.add(new CaveDragon());
							moveCards.add(new StrayLambs());
							this.setMove((byte)2, Intent.DEFEND);
							break;
					}
				}
				break;
			case 3:
				switch (moveRoll)
				{
					case 1:
						AbstractCard totem = new TotemDragon();
						totem.upgrade();
						moveCards.add(totem);
						moveCards.add(new Hinotama());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 4, true);
						break;
					case 2:
						moveCards.add(new PreventRat());
						moveCards.add(new CaveDragon());
						moveCards.add(new BabyDragon());
						this.setMove((byte)2, Intent.DEFEND);
						break;
					case 3:
						AbstractCard totemB = new TotemDragon();
						totemB.upgrade();
						moveCards.add(totemB);
						moveCards.add(new CaveDragon());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
						break;
					case 4:
						moveCards.add(new Hinotama());
						moveCards.add(new CaveDragon());
						moveCards.add(new BabyDragon());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 3, true);
						break;
					case 5:
						AbstractCard totemC = new TotemDragon();
						totemC.upgrade();
						moveCards.add(totemC);
						moveCards.add(new PreventRat());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
						break;
				}
				break;
			case 4:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 1)
				{
					AbstractCard totem = new Hinotama();
					totem.upgrade();
					moveCards.add(totem);
					moveCards.add(new BlueEyes());
					this.setMove((byte)2, Intent.ATTACK, 41);
				}
				else
				{
					switch (moveRoll)
					{
						case 1:
							AbstractCard totem = new Hinotama();
							totem.upgrade();
							moveCards.add(totem);
							moveCards.add(new Hinotama());
							moveCards.add(new BabyDragon());
							overflowCards.add(new BackgroundDragon());
							this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 7, true);
							break;
						case 2:
							moveCards.add(new BabyDragon());
							moveCards.add(new BlueEyes());
							overflowCards.add(new BackgroundDragon());
							this.setMove((byte)2, Intent.ATTACK_DEFEND, 25);
							break;
						case 3:
							moveCards.add(new BabyDragon());
							moveCards.add(new BackgroundDragon());
							this.setMove((byte)2, Intent.DEFEND);
							break;
						case 4:							
							AbstractCard totemB = new Hinotama();
							totemB.upgrade();
							moveCards.add(totemB);
							moveCards.add(new BackgroundDragon());
							this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
							break;
						case 5:
							AbstractCard totemC = new Hinotama();
							totemC.upgrade();
							moveCards.add(totemC);
							moveCards.add(new Hinotama());
							moveCards.add(new BabyDragon());
							overflowCards.add(new BackgroundDragon());
							this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 7, true);
							break;
					}
				}
				break;
			case 5:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons >= armageddon)
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						moveCards.add(new ArmageddonDragonEmp());
						moveCards.add(new CaveDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 85);
					}
					else
					{
						moveCards.add(new ArmageddonDragonEmp());
						moveCards.add(new Hinotama());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 97);
					}
				}
				else
				{
					switch (moveRoll)
					{
						case 1:
							moveCards.add(new CaveDragon());
							moveCards.add(new Reinforcements());
							moveCards.add(new Hinotama());
							this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 3, true);
							break;
						case 2:
							moveCards.add(new Hinotama());
							moveCards.add(new StrayLambs());
							this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 3, true);
							break;
						case 3:
							moveCards.add(new Reinforcements());
							moveCards.add(new Hinotama());
							moveCards.add(new CaveDragon());
							this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 3, true);
							break;
						case 4:
							moveCards.add(new CaveDragon());
							moveCards.add(new StrayLambs());
							this.setMove((byte)2, Intent.DEFEND_BUFF);
							break;
						case 5:
							moveCards.add(new Reinforcements());
							moveCards.add(new StrayLambs());
							this.setMove((byte)2, Intent.BUFF);
							break;
					}
				}
				break;
			case 6:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 3)
				{
					moveCards.add(new BlueEyesUltimate());
					moveCards.add(new YamataDragon());
					this.setMove((byte)2, Intent.ATTACK, 56);
				}
				else if (summons > 2)
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						moveCards.add(new BlueEyesUltimate());
						moveCards.add(new CaveDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 56);
					}
					else
					{
						moveCards.add(new BlueEyesUltimate());
						moveCards.add(new Hinotama());
						this.setMove((byte)2, Intent.ATTACK, 57);
					}					
				}
				else if (summons > 1)
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new BlueEyesUltimate());	
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 45);
					}
					else
					{
						moveCards.add(new BoosterDragon());
						moveCards.add(new Hinotama());
						this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 3, true);
					}			
				}
				else if (summons > 0)
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						moveCards.add(new YamataDragon());
						moveCards.add(new Hinotama());	
						this.setMove((byte)2, Intent.ATTACK, 23);
					}
					else
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new BoosterDragon());	
						this.setMove((byte)2, Intent.DEFEND_BUFF);
					}		
				}
				else
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new YamataDragon());	
					moveCards.add(new Hinotama());	
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 23);
				}
				break;
			case 7:
				switch (moveRoll)
				{
					case 1:
						AbstractCard hion = new Hinotama();
						hion.upgrade();
						moveCards.add(hion);
						moveCards.add(new CaveDragon());
						moveCards.add(new CaveDragon());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
						break;
					case 2:
						moveCards.add(new CaveDragon());
						moveCards.add(new CaveDragon());
						moveCards.add(new GoldenApples());
						overflowCards.add(new PreventRat());	
						this.setMove((byte)2, Intent.DEFEND);
						break;
					case 3:
						moveCards.add(new CaveDragon());
						moveCards.add(new PreventRat());
						moveCards.add(new GoldenApples());	
						this.setMove((byte)2, Intent.DEFEND);
						break;
					case 4:						
						moveCards.add(new PreventRat());
						AbstractCard hionB = new Hinotama();
						hionB.upgrade();
						moveCards.add(hionB);
						moveCards.add(new GoldenApples());	
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
						break;
					case 5:
						moveCards.add(new CaveDragon());
						moveCards.add(new CaveDragon());
						AbstractCard hionC = new Hinotama();
						hionC.upgrade();
						moveCards.add(hionC);	
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
						break;
				}
				break;
			case 8:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 2)
				{
					moveCards.add(new Reinforcements());
					moveCards.add(new StardustDragon());
					this.setMove((byte)2, Intent.ATTACK_BUFF, 22);
				}
				
				else if (summons > 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new StardustDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
				}
				
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 3);
					switch (roll)
					{
						case 1:
							moveCards.add(new CaveDragon());
							moveCards.add(new StrayLambs());
							this.setMove((byte)2, Intent.DEFEND_BUFF);
							break;
						case 2:
							moveCards.add(new CaveDragon());
							moveCards.add(new Reinforcements());
							moveCards.add(new SilverApples());
							this.setMove((byte)2, Intent.DEFEND_BUFF);
							break;
						case 3:
							moveCards.add(new StrayLambs());
							moveCards.add(new Reinforcements());
							this.setMove((byte)2, Intent.BUFF);
							break;
								
					}
				}
				break;
			case 9:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 2)
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						AbstractCard hino = new Hinotama();
						hino.upgrade();
						moveCards.add(new StardustDragon());
						moveCards.add(hino);
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK, 38);
					}
					else
					{
						moveCards.add(new StardustDragon());
						moveCards.add(new GoldenApples());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
					}
				}
				
				else if (summons > 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new StardustDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
				}
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new GoldenApples());
						AbstractCard hino = new Hinotama();
						hino.upgrade();
						moveCards.add(hino);
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
					}
					else
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new PreventRat());
						AbstractCard hino = new Hinotama();
						hino.upgrade();
						moveCards.add(hino);
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
					}
				}
				break;
			case 10:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 5)
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						moveCards.add(new BusterBlader());
						moveCards.add(new StardustDragon());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK, 53);
					}
					else
					{
						moveCards.add(new StardustDragon());
						moveCards.add(new BusterBlader());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK, 53);
					}
				}
				
				else if (summons > 1)
				{
					moveCards.add(new BackgroundDragon());
					moveCards.add(new BusterBlader());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 31);
				}
				
				else if (summons > 0)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new StardustDragon());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
				}
				
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new BabyDragon());
						moveCards.add(new BusterBlader());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 31);
					}
					else
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.DEFEND);
					}					
				}
				
				break;
			case 11:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
		
				if (summons > 5)
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						moveCards.add(new ScrapFactory());
						moveCards.add(new CaveDragon());
						moveCards.add(new FiendSkull());
						moveCards.add(new BlueEyes());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, 40);
					}
					else
					{
						moveCards.add(new ScrapFactory());
						moveCards.add(new CaveDragon());
						moveCards.add(new RedEyes());
						moveCards.add(new BlueEyes());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, 39);
					}
				}
				
				else if (summons > 0)
				{
					int roll = AbstractDungeon.aiRng.random(1, 3);
					if (roll == 1)
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new RedEyes());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, 14);
					}
					else if (roll == 2)
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new FiendSkull());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, 15);
					}
					else
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new BlueEyes());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 25);
					}
				}
				
				else
				{
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.DEFEND);
				}
				
				
				break;
			case 12:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 2)
				{
					moveCards.add(new RedEyes());
					moveCards.add(new Hinotama());
					this.setMove((byte)2, Intent.ATTACK, 26);
				}
				else if (summons > 0)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new RedEyes());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
				}
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 3);
					if (roll == 1)
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new BabyDragon());
						moveCards.add(new Hinotama());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 3, true);
					}
					else if (roll == 2)
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new StrayLambs());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
					}
					else
					{
						moveCards.add(new Hinotama());
						moveCards.add(new StrayLambs());
						this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 3, true);
					}
				}
				break;
			case 13:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 2)
				{
					int roll = AbstractDungeon.aiRng.random(1, 3);
					if (roll == 1)
					{
						moveCards.add(new Hinotama());
						moveCards.add(new FiendSkull());
						this.setMove((byte)2, Intent.ATTACK, 27);
					}
					else if (roll == 2)
					{
						moveCards.add(new FiendSkull());
						moveCards.add(new CaveDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 15);
					}
					else
					{
						moveCards.add(new SilverApples());
						moveCards.add(new FiendSkull());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 15);
					}
				}
				
				else if (summons > 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new FiendSkull());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 15);
				}
				
				else 
				{					
					moveCards.add(new CaveDragon());
					moveCards.add(new Hinotama());
					moveCards.add(new SilverApples());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 3, true);
				}
				
				break;
			case 14:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 2)
				{
					moveCards.add(new Reinforcements());
					moveCards.add(new FiendSkull());
					this.setMove((byte)2, Intent.ATTACK_BUFF, 15);
				}
				
				else if (summons > 1)
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new FiendSkull());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 15);
					}
					else
					{
						moveCards.add(new BoosterDragon());
						moveCards.add(new SilverApples());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
					}
				}
				else if (summons > 0)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BoosterDragon());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				
				else
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new Reinforcements());
					moveCards.add(new SilverApples());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				
				break;
			case 15:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 0)
				{
					int roll = AbstractDungeon.aiRng.random(1, 2);
					if (roll == 1)
					{
						moveCards.add(new BabyDragon());
						moveCards.add(new Reinforcements());
						moveCards.add(new BusterBlader());
						this.setMove((byte)2, Intent.ATTACK_BUFF, 31);
					}
					else
					{
						moveCards.add(new StrayLambs());
						moveCards.add(new BusterBlader());
						this.setMove((byte)2, Intent.ATTACK_BUFF, 31);
					}
				}
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 3);
					if (roll == 1)
					{
						moveCards.add(new BabyDragon());
						moveCards.add(new Reinforcements());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
					}
					else if (roll == 2)
					{
						moveCards.add(new BabyDragon());
						moveCards.add(new StrayLambs());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
					}
					else
					{
						moveCards.add(new StrayLambs());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
					}
				}
				break;
			case 16:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 3)
				{
					moveCards.add(new ScrapFactory());
					moveCards.add(new BabyDragon());
					moveCards.add(new BlueEyes());
					moveCards.add(new BerserkerCrush());
					this.setMove((byte)2, Intent.ATTACK_DEBUFF, 36);
				}
				
				else if (summons > 2)
				{
					moveCards.add(new StardustDragon());
					moveCards.add(new BabyDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
				}
				
				else if (summons > 0)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new StardustDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
				}
				else
				{
					int roll = AbstractDungeon.aiRng.random(1,2);
					if (roll == 1)
					{
						moveCards.add(new BabyDragon());
						moveCards.add(new BerserkerCrush());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
					}
					else
					{
						moveCards.add(new BabyDragon());
						moveCards.add(new BlueEyes());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 25);
					}
				}
				break;
			case 17:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 2)
				{
					int roll = AbstractDungeon.aiRng.random(1,2);
					if (roll == 1)
					{
						moveCards.add(new Hinotama());
						moveCards.add(new BlueEyesUltimate());
						overflowCards.add(new BackgroundDragon());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK, 57);
					}
					else
					{
						AbstractCard hino = new Hinotama();
						hino.upgrade();
						moveCards.add(hino);
						moveCards.add(new BlueEyesUltimate());
						overflowCards.add(new BackgroundDragon());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK, 61);
					}
				}
				
				else if (summons > 1)
				{
					int roll = AbstractDungeon.aiRng.random(1,2);
					if (roll == 1)
					{
						moveCards.add(new PreventRat());
						moveCards.add(new BlueEyesUltimate());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 45);
					}
					else
					{
						moveCards.add(new BabyDragon());
						moveCards.add(new BlueEyesUltimate());
						overflowCards.add(new BackgroundDragon());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 45);
					}
				}
				
				else
				{
					int roll = AbstractDungeon.aiRng.random(1,2);
					if (roll == 1)
					{
						moveCards.add(new BabyDragon());
						moveCards.add(new BackgroundDragon());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.DEFEND);
					}
					else
					{
						AbstractCard hino = new Hinotama();
						hino.upgrade();
						moveCards.add(hino);
						moveCards.add(new Hinotama());
						moveCards.add(new PreventRat());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 7, true);
					}
				}
				
				break;
			case 18:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 2)
				{
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(hino);
					moveCards.add(new BlueEyesUltimate());
					overflowCards.add(new BackgroundDragon());
					overflowCards.add(new PreventRat());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK, 61);
				}
				
				else if (summons > 0)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new BlueEyesUltimate());
					overflowCards.add(new BackgroundDragon());
					overflowCards.add(new PreventRat());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 45);
				}
				
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 3);
					if (roll == 1)
					{
						moveCards.add(new PreventRat());
						AbstractCard hino = new Hinotama();
						hino.upgrade();
						moveCards.add(hino);
						moveCards.add(new BabyDragon());
						overflowCards.add(new BackgroundDragon());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
					}
					else if (roll == 2)
					{
						moveCards.add(new BabyDragon());
						moveCards.add(new BackgroundDragon());
						overflowCards.add(new PreventRat());
						overflowCards.add(new PreventRat());
						this.setMove((byte)2, Intent.DEFEND);
					}
					else
					{
						AbstractCard hino = new Hinotama();
						hino.upgrade();
						moveCards.add(hino);
						moveCards.add(new PreventRat());
						moveCards.add(new PreventRat());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
					}
				}
				
				break;
			case 19:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 1)
				{
					moveCards.add(new RedEyes());
					moveCards.add(new CaveDragon());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
				}
				else if (summons > 0)
				{					
					moveCards.add(new CaveDragon());
					moveCards.add(new RedEyes());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
				}
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 5);
					if (roll == 1)
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new BabyDragon());
						moveCards.add(new Hinotama());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 3, true);
					}
					else if (roll == 2)
					{
						moveCards.add(new CaveDragon());
						moveCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.DEFEND);
					}
					else if (roll == 3)
					{
						moveCards.add(new BabyDragon());
						moveCards.add(new RedEyes());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
					}
					else if (roll == 4)
					{
						moveCards.add(new Hinotama());
						moveCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 3, true);
					}
					else
					{
						moveCards.add(new BabyDragon());
						moveCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.DEFEND);
					}
				}
				
				break;
			case 20:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > 2)
				{
					moveCards.add(new StardustDragon());
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(hino);
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK, 38);
				}
				
				else if (summons > 0)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new StardustDragon());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
				}
				
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 3);
					if (roll == 1)
					{
						moveCards.add(new BabyDragon());
						AbstractCard hino = new Hinotama();
						hino.upgrade();
						moveCards.add(hino);
						moveCards.add(new SilverApples());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
					}
					else if (roll == 2)
					{
						moveCards.add(new SilverApples());
						moveCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.DEFEND);
					}
					else
					{
						moveCards.add(new BackgroundDragon());
						AbstractCard hino = new Hinotama();
						hino.upgrade();
						moveCards.add(hino);
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
					}
				}
				break;
			case 21:
				if (this.hasPower(EnemySummonsPower.POWER_ID))
				{
					EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
					summons = pow.handCards.size();
					if (summons > 1)
					{
						for (String s : pow.handCards)
						{
							if (s.equals("Kuriboh Token"))
							{
								hasLambs = true;
							}
						}
					}
				}
				
				if (summons > this.earthGiant + 2)
				{
					moveCards.add(new EarthGiant());
					moveCards.add(new RedEyes());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
				}
				else if (summons > this.earthGiant + 1)
				{
					moveCards.add(new EarthGiant());
					moveCards.add(new LabyrinthWall());
					this.setMove((byte)2, Intent.DEFEND);
				}
				
				else if (summons >= this.earthGiant)
				{
					moveCards.add(new EarthGiant());
					AbstractCard red = new RedMedicine();
					red.upgrade();
					moveCards.add(red);
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				
				else if (summons > 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new RedEyes());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
				}
				
				else
				{
					moveCards.add(new CaveDragon());
					AbstractCard red = new RedMedicine();
					red.upgrade();
					moveCards.add(red);
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				
				break;
			default:
				switch (moveRoll)
				{
					case 1:
						moveCards.add(new BabyDragon());
						moveCards.add(new GoldenApples());
						moveCards.add(new SilverApples());
						this.setMove((byte)2, Intent.MAGIC);
						break;
					case 2:
						moveCards.add(new BabyDragon());
						moveCards.add(new GoldenApples());
						moveCards.add(new SilverApples());
						this.setMove((byte)2, Intent.MAGIC);
						break;
					case 3:
						moveCards.add(new BabyDragon());
						moveCards.add(new GoldenApples());
						moveCards.add(new SilverApples());
						this.setMove((byte)2, Intent.MAGIC);
						break;
					case 4:
						moveCards.add(new BabyDragon());
						moveCards.add(new GoldenApples());
						moveCards.add(new SilverApples());
						this.setMove((byte)2, Intent.MAGIC);
						break;
					case 5:
						moveCards.add(new BabyDragon());
						moveCards.add(new GoldenApples());
						moveCards.add(new SilverApples());
						this.setMove((byte)2, Intent.MAGIC);
						break;
				}
				break;
		}
		return moveCards;
	}
	
	private void setupHands()
	{
		this.possibleHands = new ArrayList<ArrayList<String>>();
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
		hand0.add("Miraculous Descent+");
		hand0.add("Reinforcements");
		hand0.add("Five Headed Dragon");
		hand0.add("Scrap Factory");
		hand0.add("Baby Dragon");    	

		hand1.add("Scrap Factory");
		hand1.add("Baby Dragon");
		hand1.add("Baby Dragon");
		hand1.add("Stray Lambs"); 
		hand1.add("Yamata Dragon");

		hand2.add("Cave Dragon"); 
		hand2.add("Blue Eyes");
		hand2.add("Beserker Crush");
		hand2.add("Stray Lambs");
		hand2.add("Baby Dragon");

		hand3.add("Totem Dragon+");
		hand3.add("Prevent Rat");
		hand3.add("Hinotama");
		hand3.add("Cave Dragon"); 
		hand3.add("Baby Dragon+");

		hand4.add("Hinotama");    
		hand4.add("Blue Eyes");
		hand4.add("Background Dragon");
		hand4.add("Hinotama+");
		hand4.add("Baby Dragon"); 

		hand5.add("Reinforcements");
		hand5.add("Hinotama");
		hand5.add("Stray Lambs");
		hand5.add("Cave Dragon");    
		hand5.add("Armageddon Dragon"); 

		hand6.add("Blue Eyes Ultimate");
		hand6.add("Yamata Dragon");
		hand6.add("Booster Dragon"); 
		hand6.add("Cave Dragon");
		hand6.add("Hinotama");

		hand7.add("Cave Dragon");
		hand7.add("Cave Dragon");    	
		hand7.add("Hinotama+");
		hand7.add("Prevent Rat"); 
		hand7.add("Golden Apples");

		hand8.add("Stardust Dragon");
		hand8.add("Cave Dragon");
		hand8.add("Silver Apples");
		hand8.add("Reinforcements");
		hand8.add("Stray Lambs"); 

		hand9.add("Stardust Dragon");
		hand9.add("Hinotama+"); 	
		hand9.add("Cave Dragon"); 
		hand9.add("Golden Apples");
		hand9.add("Prevent Rat");

		hand10.add("Stardust Dragon");
		hand10.add("Cave Dragon"); 	
		hand10.add("Baby Dragon"); 
		hand10.add("Background Dragon");
		hand10.add("Buster Blader");
		
		hand11.add("Red Eyes");
		hand11.add("Fiend Skull");
		hand11.add("Cave Dragon");     	
		hand11.add("Blue Eyes"); 
		hand11.add("Scrap Factory");
		
		hand12.add("Red Eyes");
		hand12.add("Baby Dragon");
		hand12.add("Cave Dragon");     	
		hand12.add("Hinotama"); 
		hand12.add("Stray Lambs");
		
		hand13.add("Fiend Skull");
		hand13.add("Cave Dragon");
		hand13.add("Hinotama+");     	
		hand13.add("Five Headed Dragon"); 
		hand13.add("Silver Apples");
		
		hand14.add("Fiend Skull");
		hand14.add("Cave Dragon");
		hand14.add("Reinforcements");     	
		hand14.add("Booster Dragon"); 
		hand14.add("Silver Apples");
		
		hand15.add("Buster Blader");
		hand15.add("Baby Dragon");
		hand15.add("Reinforcements");     	
		hand15.add("Golden Apples"); 
		hand15.add("Stray Lambs");
		
		hand16.add("Baby Dragon");
		hand16.add("Stardust Dragon");
		hand16.add("Beserker Crush");     	
		hand16.add("Blue Eyes"); 
		hand16.add("Scrap Factory");
		
		hand17.add("Hinotama");
		hand17.add("Hinotama+");
		hand17.add("Blue Eyes Ultimate");     	
		hand17.add("Background Dragon"); 
		hand17.add("Prevent Rat");
		
		hand18.add("Hinotama+");
		hand18.add("Blue Eyes Ultimate");
		hand18.add("Background Dragon");     	
		hand18.add("Prevent Rat"); 
		hand18.add("Prevent Rat");

		hand19.add("Red Eyes");
		hand19.add("Baby Dragon");
		hand19.add("Cave Dragon");     	
		hand19.add("Hinotama"); 
		hand19.add("Background Dragon");
		
		hand20.add("Stardust Dragon");
		hand20.add("Hinotama+");
		hand20.add("Baby Dragon");     	
		hand20.add("Silver Apples"); 
		hand20.add("Background Dragon");
		
		hand21.add("Red Medicine+");
		hand21.add("Earth Giant");
		hand21.add("Labyrinth Wall");     	
		hand21.add("Cave Dragon"); 
		hand21.add("Red Eyes");


		this.possibleHands.add(hand0);
		this.possibleHands.add(hand1);
		this.possibleHands.add(hand2);
		this.possibleHands.add(hand3);
		this.possibleHands.add(hand4);
		this.possibleHands.add(hand5);
		this.possibleHands.add(hand6);
		this.possibleHands.add(hand7);
		this.possibleHands.add(hand8);
		this.possibleHands.add(hand9);
		this.possibleHands.add(hand10);
		this.possibleHands.add(hand11);
		this.possibleHands.add(hand12);
		this.possibleHands.add(hand13);
		this.possibleHands.add(hand14);
		this.possibleHands.add(hand15);
		this.possibleHands.add(hand16);
		this.possibleHands.add(hand17);
		this.possibleHands.add(hand18);
		this.possibleHands.add(hand19);
		this.possibleHands.add(hand20);
		this.possibleHands.add(hand21);
	}
	

}// You made it! End of monster.
