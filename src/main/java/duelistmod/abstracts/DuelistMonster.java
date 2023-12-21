package duelistmod.abstracts;

import java.util.*;

import basemod.*;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;

import duelistmod.DuelistMod;
import duelistmod.actions.common.DuelistMonsterDrawHandAction;
import duelistmod.actions.unique.DetonationAction;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.fiend.DarkCubicLord;
import duelistmod.cards.pools.fiend.SummonedSkull;
import duelistmod.cards.pools.machine.*;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.*;
import duelistmod.powers.SwordsRevealPower;
import duelistmod.powers.enemyPowers.*;
import duelistmod.variables.Tags;

public abstract class DuelistMonster extends AbstractMonster
{
	protected int energy = 3;
	protected int handIndex = -1;
	protected int summonsThisCombat = 0;
	protected int tributesThisCombat = 0;
	protected int summonsThisTurn = 0;
	protected int tributesThisTurn = 0;
	protected int giantCounter = 0;
	protected int rollMessage = 0;
	protected int turns = 0;
	protected int armageddon = 11;
	protected int earthGiant = 7;
	protected int preventRatOverflow = 2;
	protected int backgroundOverflow = 1;
	protected int cardDialogMin = 0;
	protected int cardDialogMax = 1;
	protected int lowWeightHandIndex = -1;
	protected String overflowDialog = "";
	protected String startingToken = "Token";
	protected String deathSound = "JAW_WORM_DEATH";

	protected ArrayList<ArrayList<String>> possibleHands = new ArrayList<ArrayList<String>>();
	protected ArrayList<AbstractCard> cardsForNextMove = new ArrayList<AbstractCard>();
	protected ArrayList<AbstractCard> overflowCards = new ArrayList<AbstractCard>();
	protected ArrayList<String> dialog = new ArrayList<String>();

	protected boolean firstTurn = true;
	protected boolean destructUsed = false;

	public DuelistMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, int lowHP, int maxHP, int a7lowHP, int a7maxHP) {
		super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, "duelistModResources/images/char/enemies/" + imgUrl + ".png");
		if (AbstractDungeon.ascensionLevel >= 7) { this.setHp(a7lowHP, a7maxHP); } else { this.setHp(lowHP, maxHP); }
	}

	public abstract void onUseDestructPotion();

	public ArrayList<AbstractCard> getCardsForNextMove() {
		return cardsForNextMove;
	}

	public void gainEnergy(int add) {
		this.setEnergy(this.getEnergy() + add);
		if (this.energy < 0) {
			this.setEnergy(0);
		}
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getEnergy() {
		return this.energy;
	}

	@Override
	public void die() {
		super.die();
		CardCrawlGame.sound.play(this.deathSound); // And it croaks too.
	}

	@Override
	protected void getMove(int seed) {
		this.cardsForNextMove = getHandMove();
	}

	@Override
	public void takeTurn() {
		if (this.firstTurn) {
			AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.dialog.get(0), 2.5F, 3.0F)); // Speak the stuff in DIALOG[0],
			this.firstTurn = false; // Then ensure it's no longer the first turn.
		}

		if (this.cardsForNextMove.size() > 0) {
			playCards(this.cardsForNextMove);
			for (AbstractCard c : overflowCards) { handleOverflows(c); }
			AbstractDungeon.actionManager.addToBottom(new DuelistMonsterDrawHandAction(this));
		}
		AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));

	}

	@Override
	public void usePreBattleAction() {
		ArrayList<String> startingHand = this.possibleHands.get(0);
		handIndex = 0;
		this.cardsForNextMove = getHandMove();
		DuelistCard.applyPower(new EnemyEnergyPower(this, this, this.energy, this.name), this);
		DuelistCard.applyPower(new EnemyHandPower(this, startingHand), this);
		Integer tokens = StartingDeck.currentDeck.getActiveConfig().getTokensToSummon();
		if (tokens == null) {
			tokens = 1;
		}
		DuelistCard.applyPower(new EnemySummonsPower(this, tokens, this.startingToken), this);
	}

	private ArrayList<String> generateNewHand() {
		int handRoll = AbstractDungeon.aiRng.random(this.possibleHands.size() - 1);
		if (handRoll == this.lowWeightHandIndex) { handRoll = AbstractDungeon.aiRng.random(this.possibleHands.size() - 1); }
		ArrayList<String> newHand = this.possibleHands.get(handRoll);
		Collections.shuffle(newHand, new Random(AbstractDungeon.cardRandomRng.randomLong()));
		this.handIndex = handRoll;
		Util.log(this.name + " new hand index:" + this.handIndex);
		return newHand;
	}

	public void drawNewHand() {
		if (this.hasPower(EnemyMiraclePower.POWER_ID)) {
			EnemyMiraclePower pow = (EnemyMiraclePower)this.getPower(EnemyMiraclePower.POWER_ID);
			//pow.trigger(this);
		}
		this.summonsThisTurn = 0;
		if (this.hasPower(EnemyHandPower.POWER_ID)) {
			this.overflowCards.clear();
			ArrayList<String> newHand = generateNewHand();
			EnemyHandPower pow = (EnemyHandPower)this.getPower(EnemyHandPower.POWER_ID);
			pow.fillHand(newHand);
			pow.flash();
			if (!this.hasPower(EnemyEnergyPower.POWER_ID)) { DuelistCard.applyPower(new EnemyEnergyPower(this, this, this.energy, this.name), this); }
		} else { DuelistCard.applyPower(new EnemyHandPower(this, generateNewHand()), this); }
	}

	public void setHands(ArrayList<ArrayList<String>> hands) {
		this.possibleHands = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < hands.size(); i++) { this.possibleHands.add(hands.get(i)); }
	}

	protected ArrayList<AbstractCard> getHandMove() {
		ArrayList<AbstractCard> moveCards = new ArrayList<AbstractCard>();
		return moveCards;
	}

	protected int getSummons() {
		int summons = 0;
		if (this.hasPower(EnemySummonsPower.POWER_ID)) { EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID); summons = pow.handCards.size(); }
		return summons;
	}

	protected boolean hasLambs() {
		int summons = 0;
		boolean hasLambs = false;
		if (this.hasPower(EnemySummonsPower.POWER_ID)) {
			EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
			summons = pow.handCards.size();
			if (summons > 1) { for (String s : pow.handCards) { if (s.equals("Kuriboh Token")) { hasLambs = true; }}}
		}
		return hasLambs;
	}

	private void handleOverflows(AbstractCard c) {
		if (c instanceof PreventRat && this.preventRatOverflow > 0) {
			this.preventRatOverflow--;
			if (!this.firstTurn) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.overflowDialog, 0.5F, 2.0F)); }
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 3));
		}

		if (c instanceof BackgroundDragon && this.backgroundOverflow > 0) {
			this.backgroundOverflow--;
			if (!this.firstTurn) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.overflowDialog, 0.5F, 2.0F)); }
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 8));

		}
	}

	public void playCards(ArrayList<AbstractCard> cards) {
		ArrayList<AbstractCard> reversed = new ArrayList<>(cards);
		Collections.reverse(reversed);
		for (AbstractCard card : reversed) {
			AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(card));
		}
		for (AbstractCard card : cards) {
			Util.log(this.name + " played " + card.name, true);
			if (!(DuelistMod.debug)) {
				DevConsole.log(this.name + " played " + card.name);
			}
			takeCardAction(card);
		}
	}

	public ArrayList<String> tribute(int amount, boolean dragon, boolean toon, boolean machine) {
		ArrayList<String> tribs = new ArrayList<String>();

		if (this.hasPower(EnemySummonsPower.POWER_ID)) {
			EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
			tribs = pow.tribute(amount);
			this.tributesThisCombat += tribs.size();
		}

		if (dragon) {
			int dragsTributed = dragonsTributed(tribs);
			if (dragsTributed > 0) { DuelistCard.applyPower(new StrengthPower(this, dragsTributed), this); }
			if (this.hasPower(EnemyBoosterDragonPower.POWER_ID)) { EnemyBoosterDragonPower pow = (EnemyBoosterDragonPower)this.getPower(EnemyBoosterDragonPower.POWER_ID); for (int i = 0; i < dragsTributed; i++) { pow.trigger(this); }}
		} else {
			int dragsTributed = lambsTributed(tribs);
			if (dragsTributed > 0) { DuelistCard.applyPower(new IntangiblePlayerPower(this, dragsTributed), this); }
		}

		if (toon) {
			int toonsTributed = toonsTributes(tribs);
			if (toonsTributed > 0) { DuelistCard.applyPowerToSelf(new VulnerablePower(AbstractDungeon.player, toonsTributed, true)); }
		}

		if (machine) {
			int machinesTributed = machineTributes(tribs);
			if (machinesTributed > 0) { DuelistCard.applyPower(new ArtifactPower(this, machinesTributed), this); }
		}

		int explodes = explodesTributed(tribs);
		if (explodes > 0) {
			this.addToBot(new DetonationAction(explodes, false));
		}

		int supExplodes = superExplodesTributed(tribs);
		if (supExplodes > 0) {
			this.addToBot(new DetonationAction(supExplodes, true));
		}
		return tribs;
	}

	public ArrayList<String> tributeAll(boolean dragon, boolean toon, boolean machine) {
		ArrayList<String> tribs = new ArrayList<String>();
		if (this.hasPower(EnemySummonsPower.POWER_ID)) {
			EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
			tribs = pow.tributeAll();
			this.tributesThisCombat += tribs.size();
		}
		if (dragon) {
			int dragsTributed = dragonsTributed(tribs);
			if (dragsTributed > 0) { DuelistCard.applyPower(new StrengthPower(this, dragsTributed), this); }
			if (this.hasPower(EnemyBoosterDragonPower.POWER_ID)) { EnemyBoosterDragonPower pow = (EnemyBoosterDragonPower)this.getPower(EnemyBoosterDragonPower.POWER_ID); for (int i = 0; i < dragsTributed; i++) { pow.trigger(this); }}
		} else {
			int dragsTributed = lambsTributed(tribs);
			if (dragsTributed > 0) { DuelistCard.applyPower(new IntangiblePlayerPower(this, dragsTributed), this); }
		}

		if (toon) {
			int toonsTributed = toonsTributes(tribs);
			if (toonsTributed > 0) { DuelistCard.applyPowerToSelf(new VulnerablePower(AbstractDungeon.player, toonsTributed, true)); }
		}

		if (machine) {
			int machinesTributed = machineTributes(tribs);
			if (machinesTributed > 0) { DuelistCard.applyPower(new ArtifactPower(this, machinesTributed), this); }
		}

		int explodes = explodesTributed(tribs);
		if (explodes > 0) {
			this.addToBot(new DetonationAction(explodes, false));
		}

		int supExplodes = superExplodesTributed(tribs);
		if (supExplodes > 0) {
			this.addToBot(new DetonationAction(supExplodes, true));
		}
		return tribs;
	}

	public void summon(String card) {
		if (this.hasPower(EnemySummonsPower.POWER_ID)) {
			EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
			if (pow.addCardToSummons(card)) { this.summonsThisCombat++; this.summonsThisTurn++; }
		} else {
			DuelistCard.applyPower(new EnemySummonsPower(this, 1, card), this);
		}
	}

	public void summon(String card, int amount) {
		for (int i = 0; i < amount; i++) { summon(card); }
	}

	private void attackThroughBlock(int amount) {

		int playerBlock = AbstractDungeon.player.currentBlock;
		if (playerBlock > 0) { AbstractDungeon.player.loseBlock(playerBlock); }
		attack(amount);
		if (playerBlock > 0) { AbstractDungeon.player.addBlock(playerBlock); }
	}

	public static void staticAttack(int amt, AbstractCreature owner, boolean exodia) {
		int dmg = applyDmgPowers(amt, owner);
		if (exodia) {
			AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new LaserBeamEffect(owner.hb.cX, owner.hb.cY + 60.0f * Settings.scale), 1.5f));
		}
		AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(owner, dmg), AttackEffect.SLASH_HEAVY));
	}

	public static void staticAttack(int amt, AbstractCreature owner) {
		staticAttack(amt, owner, false);
	}

	private void attack(int amount) {
		int dmg = localApplyDmgPowers(amount);
		AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), AttackEffect.SLASH_HEAVY));
	}

	private void attack(int amount, int times) {
		int dmg = localApplyDmgPowers(amount);
		for (int i = 0; i < times; i++) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), AttackEffect.SLASH_HEAVY));
		}
	}

	private void attack(int amount, AttackEffect afx) {
		int dmg = localApplyDmgPowers(amount);
		AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
	}

	private void attack(int amount, int times, AttackEffect afx) {
		int dmg = localApplyDmgPowers(amount);
		for (int i = 0; i < times; i++) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
		}
	}

	private void block(int amount) {
		int blk = localApplyBlkPowers(amount);
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
	}

	private void block(int amount, int times) {
		int blk = localApplyBlkPowers(amount);
		for (int i = 0; i < times; i++) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}
	}

	public int getExodiaDamage() {
		if (this.hasPower(EnemyExodiaPower.POWER_ID)) {
			EnemyExodiaPower pow = (EnemyExodiaPower)this.getPower(EnemyExodiaPower.POWER_ID);
			return pow.effectDmg;
		} else { return 0; }
	}

	private boolean exodiaHeadDmgCheck() {
		if (this.hasPower(EnemyExodiaPower.POWER_ID)) {
			EnemyExodiaPower pow = (EnemyExodiaPower)this.getPower(EnemyExodiaPower.POWER_ID);
			return pow.checkForAllPiecesButHead();
		} else { return false; }
	}

	public int exodiaHeadDmg(AbstractCard head) {
		if (exodiaHeadDmgCheck()) {
			return head.magicNumber;
		} else { return 0; }
	}

	public void incMaxSummons(int amount) {
		if (this.hasPower(EnemySummonsPower.POWER_ID)) {
			EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
			pow.incMaxSummons(amount);
		}
	}

	private int localApplyBlkPowers(int baseBlock) {
		int blk = baseBlock;
		if (this.hasPower(DexterityPower.POWER_ID)) { blk+=this.getPower(DexterityPower.POWER_ID).amount; }
		return blk;
	}

	private int localApplyDmgPowers(int baseDamage) {
		float dmg = baseDamage;
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPower pow : p.powers) { dmg = pow.atDamageReceive(dmg, DamageType.NORMAL); }
		for (AbstractPower pow : this.powers) { dmg = pow.atDamageGive(dmg, DamageType.NORMAL); }
		dmg = p.stance.atDamageReceive(dmg, DamageType.NORMAL);
		return (int) dmg;
	}

	public static int applyDmgPowers(int baseDamage, AbstractCreature owner) {
		float dmg = baseDamage;
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPower pow : p.powers) { dmg = pow.atDamageReceive(dmg, DamageType.NORMAL); }
		for (AbstractPower pow : owner.powers) { dmg = pow.atDamageGive(dmg, DamageType.NORMAL); }
		dmg = p.stance.atDamageReceive(dmg, DamageType.NORMAL);
		return (int) dmg;
	}

	public void resetHand() {
		this.cardsForNextMove = getHandMove();
	}

	public void triggerHandReset() {
		//AbstractDungeon.actionManager.addToBottom(new KaibaResetHandAction(this));
		AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
	}

	private boolean playMessage() {
		if (this.firstTurn) { return false; }
		int roll = AbstractDungeon.miscRng.random(1, 35);
		roll += this.rollMessage;
		if (roll > 34) { this.rollMessage = 0; return true; } else { this.rollMessage++; return false; }
	}

	private boolean playMessage(int bonus) {
		if (this.firstTurn) { return false; }
		int roll = AbstractDungeon.miscRng.random(1, 35);
		roll += this.rollMessage;
		roll += bonus;
		if (roll > 34) { this.rollMessage = 0; return true; } else { this.rollMessage++; return false; }
	}

	private void exodiaAction(DuelistCard card) {
		if (this.hasPower(EnemyExodiaPower.POWER_ID)) {
			if (this.getPower(EnemyExodiaPower.POWER_ID).amount > 0) {
				EnemyExodiaPower power = (EnemyExodiaPower)this.getPower(EnemyExodiaPower.POWER_ID);
				power.addNewPiece(card);
			} else {
				DuelistCard.applyPower(new EnemyExodiaPower(this, this, card), this);
			}
		} else { DuelistCard.applyPower(new EnemyExodiaPower(this, this, card), this); }
	}

	private void takeCardAction(AbstractCard c) {
		//DuelistCard.handleOnEnemyPlayCardForAllAbstracts(c);
		if (c instanceof DarkCubicLord) {
			tribute(7, false, false, false);
			DuelistCard ref = new DarkCubicLord();
			DuelistCard.applyPower(new StrengthPower(this, ref.magicNumber), this);
		}

		if (c instanceof BlueEyesToon) {
			tribute(2, true, true, false);
			attackThroughBlock(30);
		}

		if (c instanceof CombinationAttack) {
			int upgrades = c.timesUpgraded;
			int dmg = 10;
			int blk = 6;
			dmg += (upgrades * 2);
			blk += upgrades;
			attack(dmg);
			block(blk);
		}

		if (c instanceof ExploderDragonwing) {
			DuelistCard ref = new ExploderDragonwing();
			summon("[#FF5252]Explosive [#FF5252]Token", ref.summons);
			attack(ref.damage);
		}

		if (c instanceof MirageDragon) {
			DuelistCard ref = new MirageDragon();
			summon("Mirage Dragon", ref.summons);
			attack(ref.damage);
			DuelistCard.applyPower(new WeakPower(AbstractDungeon.player, ref.magicNumber, true), AbstractDungeon.player);
		}

		if (c instanceof DestructPotion) {
			ArrayList<String> tribs = tributeAll(false, false, false);
			if (tribs.size() > 0) { int num = tribs.size() * new DestructPotion().magicNumber; this.increaseMaxHp(num, true); this.heal(num); }
			this.destructUsed = true;
			this.onUseDestructPotion();
		}

		if (c instanceof FeatherShot) {
			int upgrades = c.timesUpgraded;
			upgrades++;
			DuelistCard ref = new FeatherShot();
			for (int i = 0; i < upgrades; i++) { attack(ref.damage); }
		}

		if (c instanceof GiantSoldier) {
			DuelistCard ref = new GiantSoldier();
			summon("Giant Soldier", ref.summons);
			block(ref.block);
		}

		if (c instanceof GiantSoldierSteel) {
			DuelistCard ref = new GiantSoldierSteel();
			summon("Giant Steel Soldier", ref.summons);
			block(ref.block);
			AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(this, this, ref.magicNumber));
		}

		if (c instanceof Illusionist) {
			tribute(1, false, false, false);
			block(new Illusionist().block);
		}

		if (c instanceof MillenniumShield) {
			DuelistCard ms = new MillenniumShield();
			tribute(ms.tributes, false, false, false);
			DuelistCard.applyPower(new DexterityPower(this, ms.magicNumber), this);
		}

		if (c instanceof Kuriboh) {
			summon("Kuriboh Token");
		}

		if (c instanceof ForbiddenLance) {
			DuelistCard ref = new ForbiddenLance();
			attack(ref.damage);
			DuelistCard.applyPower(new VulnerablePower(AbstractDungeon.player, ref.magicNumber, true), AbstractDungeon.player);
			DuelistCard.applyPower(new VulnerablePower(AbstractDungeon.player, ref.magicNumber, true), AbstractDungeon.player);
		}

		if (c instanceof SummonedSkull) {
			DuelistCard ms = new SummonedSkull();
			tribute(ms.tributes, false, false, false);
			attack(ms.damage);
		}

		if (c instanceof MysticalElf) {
			summon("Mystical Elf");
			block(new MysticalElf().block);
		}

		if (c instanceof NeoMagic) {
			summon("Neo the Magic Swordsman");
			attack(new NeoMagic().damage);
		}

		if (c instanceof DarkMagician) {
			DuelistCard ms = new DarkMagician();
			tribute(ms.tributes, false, false, false);
			attack(ms.damage);
		}

		if (c instanceof CelticGuardian) {
			summon("Celtic Guardian");
			attack(new CelticGuardian().damage);
		}

		if (c instanceof SwordsRevealing) {
			DuelistCard sword = new SwordsRevealing();
			tribute(9999, false, false, false);
			DuelistCard.applyPower(new SwordsRevealPower(this, this, sword.magicNumber), this);
		}

		if (c instanceof ExodiaHead) {
			if (this.hasPower(EnemyExodiaPower.POWER_ID)) {
				EnemyExodiaPower powerInstance = (EnemyExodiaPower)this.getPower(EnemyExodiaPower.POWER_ID);
				if (powerInstance.checkForAllPiecesButHead()) {
					powerInstance.addNewPiece(new ExodiaHead());
					powerInstance.headDamage(c.magicNumber);
				}
			}
		}

		if (c instanceof ExodiaRA) {
			summon("#yRight #yArm");
			attack(new ExodiaRA().damage);
			exodiaAction(new ExodiaRA());
		}

		if (c instanceof ExodiaLA) {
			summon("#yLeft #yArm");
			attack(new ExodiaLA().damage);
			exodiaAction(new ExodiaLA());
		}

		if (c instanceof ExodiaLL) {
			summon("#yLeft #yLeg");
			block(new ExodiaLL().block);
			exodiaAction(new ExodiaLL());
		}

		if (c instanceof ExodiaRL) {
			summon("#yRight #yLeg");
			block(new ExodiaRL().block);
			exodiaAction(new ExodiaRL());
		}

		if (c instanceof GoldenApples) {
			int blk = localApplyBlkPowers(this.summonsThisCombat);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}

		if (c instanceof SilverApples) {
			int blk = localApplyBlkPowers(this.tributesThisCombat);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}

		if (c instanceof BabyDragon) {
			summon("Baby Dragon");
			summon("Baby Dragon");
			int blk = localApplyBlkPowers(3);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}

		if (c instanceof PreventRat) {
			summon("Prevent Rat");
			int blk = localApplyBlkPowers(5);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}

		if (c instanceof StrayLambs) {
			EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
			pow.incMaxSummons(1);
			summon("Kuriboh Token");
			summon("Kuriboh Token");
		}

		if (c instanceof YamataDragon) {
			tribute(1, true, false, false);
			int dmg = localApplyDmgPowers(11);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
			//if (playMessage()) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[8], 0.5F, 2.0F)); }
		}

		if (c instanceof FiveHeaded) {
			tribute(4, true, false, false);
			int dmg = localApplyDmgPowers(11);
			int times = 5;
			AttackEffect afx = AttackEffect.FIRE;
			//if (playMessage(4)) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[5], 0.5F, 2.0F)); }
			for (int i = 0; i < times; i++) {
				AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
			}
		}

		if (c instanceof BlueEyes) {
			tribute(2, true, false, false);
			int dmg = localApplyDmgPowers(25);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
			//if (playMessage(7)) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[4], 0.5F, 2.0F)); }
		}

		if (c instanceof ArmageddonDragonEmp) {
			tribute(this.armageddon, true, false, false);
			this.armageddon = 11;
			int dmg = localApplyDmgPowers(85);
			AttackEffect afx = AttackEffect.FIRE;
			//if (playMessage(20)) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[5], 0.5F, 2.0F)); }
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
		}

		if (c instanceof BackgroundDragon) {
			summon("Background Dragon");
			int blk = localApplyBlkPowers(12);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}

		if (c instanceof CaveDragon) {
			summon("Cave Dragon");
			int blk = localApplyBlkPowers(8);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}

		if (c instanceof EarthGiant) {
			tribute(this.earthGiant, false, false, false);
			int blk = localApplyBlkPowers(50);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}

		if (c instanceof BerserkerCrush) {
			int dmg = localApplyDmgPowers(11);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
			DuelistCard.applyPower(new StrengthPower(AbstractDungeon.player, -2), AbstractDungeon.player);
		}

		if (c instanceof Reinforcements) {
			DuelistCard.applyPower(new StrengthPower(this, 2), this);
		}

		if (c instanceof Hinotama) {
			int timesRoll = 0;
			if (c.upgraded) {
				timesRoll = AbstractDungeon.aiRng.random(2, 4);
			} else {
				timesRoll = AbstractDungeon.aiRng.random(2, 3);
			}
			int dmg = localApplyDmgPowers(4);
			AttackEffect afx = AttackEffect.FIRE;
			for (int i = 0; i < timesRoll; i++) {
				AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
			}
		}

		if (c instanceof TotemDragon) {
			summon("Totem Dragon");
			DuelistCard.applyPower(new EnemyTotemPower(this, this, 3, this), this);
		}

		if (c instanceof ScrapFactory) {
			tribute(2, false, false, true);
		}

		if (c instanceof BlueEyesUltimate) {
			tribute(3, true, false, false);
			int dmg = localApplyDmgPowers(45);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
			//if (playMessage(5)) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[3], 0.5F, 2.0F)); }
		}

		if (c instanceof StardustDragon) {
			tribute(3, true, false, false);
			if (this.hasPower(StrengthPower.POWER_ID)) { DuelistCard.applyPower(new StrengthPower(this, this.getPower(StrengthPower.POWER_ID).amount), this); }
			int dmg = localApplyDmgPowers(22);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
			DuelistCard.applyPower(new SlowPower(AbstractDungeon.player, 6), AbstractDungeon.player);
			//if (playMessage()) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[7], 0.5F, 2.0F)); }
		}

		if (c instanceof BusterBlader) {
			ArrayList<String> tribs = tribute(3, false, false, false);
			int dragsTributed = dragonsTributed(tribs);
			int dmg = localApplyDmgPowers(16 + (dragsTributed * 5));
			AttackEffect afx = AttackEffect.SLASH_DIAGONAL;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
		}

		if (c instanceof RedEyes) {
			tribute(3, true, false, false);
			int dmg = localApplyDmgPowers(14);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
			DuelistCard.applyPower(new WeakPower(AbstractDungeon.player, 3, true), AbstractDungeon.player);
		}

		if (c instanceof FiendSkull) {
			tribute(3, true, false, false);
			int dmg = localApplyDmgPowers(15);
			AttackEffect afx = AttackEffect.FIRE;
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, dmg), afx));
			DuelistCard.applyPower(new VulnerablePower(AbstractDungeon.player, 3, true), AbstractDungeon.player);
			//if (playMessage()) { AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[6], 0.5F, 2.0F)); }
		}

		if (c instanceof BoosterDragon) {
			tribute(2, true, false, false);
			DuelistCard.applyPower(new EnemyBoosterDragonPower(this, this, 6), this);
		}

		if (c instanceof PotAvarice) {
			ArrayList<String> sizeRef = tributeAll(false, false, false);
			if (this.hasPower(EnemySummonsPower.POWER_ID)) {
				EnemySummonsPower pow = (EnemySummonsPower)this.getPower(EnemySummonsPower.POWER_ID);
				pow.incMaxSummons(sizeRef.size());
			}
		}

		if (c instanceof MiraculousDescentEnemy) {
			if (!this.hasPower(EnemyMiraclePower.POWER_ID)) { DuelistCard.applyPower(new EnemyMiraclePower(this, this, AbstractDungeon.aiRng.random(5, 10)), this); }
		}

		if (c instanceof LabyrinthWall) {
			tribute(2, false, false, false);
			int blk = localApplyBlkPowers(20);
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blk));
		}

		if (c instanceof Deskbot004) {
			summon("Deskbot004");
			this.attack(6);
		}

		if (c instanceof Deskbot005) {
			summon("Deskbot005");
			this.block(6);
		}

		if (c instanceof JudgeMan) {
			tribute(1, false, false, false);
			summon("Judge Man");
			attack(10);
		}

		if (c instanceof Sparks) {
			if (c.upgraded) { attack(9); } else { attack(6); }
		}

		if (c instanceof PowerWall) {
			block(10);
		}

		if (c instanceof LesserDragon) {
			summon("Lesser Dragon");
			attack(new LesserDragon().damage);
		}

		if (c instanceof WingedKuriboh) {
			summon("Winged Kuriboh");
			incMaxSummons(1);
			attack(6);
		}

		if (c instanceof SpiralSpearStrike) {
			attack(12);
			DuelistCard.applyPowerToSelf(DebuffHelper.getRandomPlayerDebuff(AbstractDungeon.player, 3));
		}

		if (c instanceof RedMedicine) {
			int roll = AbstractDungeon.aiRng.random(1, 8);
			int turns = AbstractDungeon.aiRng.random(1, 3);
			switch (roll) {
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
					DuelistCard.applyPower(new MetallicizePower(this, turns), this);
					break;
				case 7:
					DuelistCard.applyPower(new EnvenomPower(this, turns), this);
					break;
				case 8:
					DuelistCard.applyPower(new AngerPower(this, turns), this);
					break;
				default:
					DuelistCard.applyPower(new StrengthPower(this, turns), this);
					break;
			}
		}

		if (c.hasTag(Tags.DRAGON) && this.armageddon > 0) {
			this.armageddon--;
		}

		if (c.type.equals(CardType.SKILL) && this.earthGiant > 0) {
			this.earthGiant--;
		}

		if (playMessage()) {
			if (this.dialog.size() > 0) {
				int dialogRoll = AbstractDungeon.aiRng.random(this.dialog.size() - 1);
				AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.dialog.get(dialogRoll), 0.5F, 2.0F));
			}
		}
	}

	public void setupDialog(ArrayList<String> strings) {
		this.dialog.addAll(strings);
	}

	private int explodesTributed(ArrayList<String> cards) {
		int total = 0;
		for (String s : cards) {
			if (s.equals("[#FF5252]Explosive [#FF5252]Token")) {
				total++;
			}
		}
		return total;
	}

	private int superExplodesTributed(ArrayList<String> cards) {
		int total = 0;
		for (String s : cards) {
			if (s.equals("[#FF5252]Super [#FF5252]Explosive [#FF5252]Token")) {
				total++;
			}
		}
		return total;
	}

	private int machineTributes(ArrayList<String> cards) {
		int total = 0;
		for (String s : cards) {
			if (s.equals("Toon Ancient Gear")) {
				total++;
			}
			if (s.equals("Toon Cannon Soldier")) {
				total++;
			}

			if (s.equals("Deskbot004")) {
				total++;
			}

			if (s.equals("Deskbot005")) {
				total++;
			}

			if (s.equals("Junk Kuriboh")) {
				total++;
			}

			if (s.equals("Machine Token")) {
				total++;
			}
		}
		return total;
	}

	private int toonsTributes(ArrayList<String> cards) {
		int total = 0;
		for (String s : cards) {
			if (s.equals("Toon Mermaid")) {
				total++;
			}

			if (s.equals("Toon Ancient Gear")) {
				total++;
			}

			if (s.equals("Toon D.M. Girl")) {
				total++;
			}

			if (s.equals("Toon Gemini Elf")) {
				total++;
			}

			if (s.equals("Toon Goblin")) {
				total++;
			}

			if (s.equals("Toon Masked Sorcerer")) {
				total++;
			}

			if (s.equals("Toon Token")) {
				total++;
			}
		}
		return total;
	}

	private int dragonsTributed(ArrayList<String> cards) {
		int total = 0;
		for (String s : cards) {
			if (s.equals("Baby Dragon")) {
				total++;
			}

			if (s.equals("Cave Dragon")) {
				total++;
			}

			if (s.equals("Background Dragon")) {
				total++;
			}

			if (s.equals("Dragon Token")) {
				total++;
			}

			if (s.equals("Lesser Dragon")) {
				total++;
			}

			if (s.equals("Mirage Dragon")) {
				total++;
			}
		}
		return total;
	}

	private int lambsTributed(ArrayList<String> cards) {
		int total = 0;
		for (String s : cards) {
			if (s.equals("Kuriboh Token")) {
				total++;
			}
		}
		return total;
	}

}
