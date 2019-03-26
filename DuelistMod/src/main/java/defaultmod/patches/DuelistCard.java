package defaultmod.patches;

import java.util.*;
import java.util.Map.Entry;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
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
import basemod.helpers.*;
import defaultmod.DefaultMod;
import defaultmod.actions.common.*;
import defaultmod.cards.Token;
import defaultmod.characters.FakePlayer;
import defaultmod.interfaces.*;
import defaultmod.powers.*;
import defaultmod.relics.MillenniumKey;

public abstract class DuelistCard extends CustomCard implements ModalChoice.Callback
{
	// CARD FIELDS
	public ArrayList<Integer> startCopies = new ArrayList<Integer>();
	public String upgradeType;
	public String exodiaName = "None";
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
	public int startingDeckCopies = 1;
	public int startingDragDeckCopies = 1;
	public int startingSpellcasterDeckCopies = 1;
	public int startingNatureDeckCopies = 1;
	public int startingCreatorDeckCopies = 1;
	public int startingToonDeckCopies = 1;
	public int startingOrbDeckCopies = 1;
	public int startingResummonDeckCopies = 1;
	public int startingGenDeckCopies = 1;
	public int startingOjamaDeckCopies = 1;
	public int startingHealDeckCopies = 1;
	public int startingIncDeckCopies = 1;
	public int startingExodiaDeckCopies = 1;
	public int startingMagnetDeckCopies = 1;
	public int startingAquaDeckCopies = 1;
	public int startingMachineDeckCopies = 1;
	public static final String UPGRADE_DESCRIPTION = "";
	public AttackEffect baseAFX = AttackEffect.SLASH_HORIZONTAL;
	private static ArrayList<AbstractOrb> allowedOrbs = new ArrayList<AbstractOrb>();
	private static ArrayList<AbstractOrb> allOrbs = new ArrayList<AbstractOrb>();
	private static Map<String, AbstractOrb> orbMap = new HashMap<String, AbstractOrb>();
	private ModalChoice orbModal;
	
	public static ArrayList<DuelistCard> allowedCardChoices = new ArrayList<DuelistCard>();
	private ModalChoice cardModal;
	
	private DuelistModalChoice duelistCardModal;
	// CARD FIELDS

	public DuelistCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET)
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.originalName = NAME;
		this.misc = 0;
		this.baseDamage = this.damage = 0;
		setupStartingCopies();
		ModalChoiceBuilder builder = new ModalChoiceBuilder().setCallback(this).setColor(COLOR).setType(CardType.SKILL).setTitle("Choose an Orb to Channel");
		for (AbstractOrb orb : allowedOrbs) { if (DefaultMod.orbCardMap.get(orb.name) != null) { builder.addOption(DefaultMod.orbCardMap.get(orb.name)); }}
		orbModal = builder.create();
		
		ModalChoiceBuilder cardBuilder = new ModalChoiceBuilder().setCallback(this).setColor(COLOR).setType(CardType.SKILL).setTitle("Choose a Card to Play");
		for (DuelistCard c : allowedCardChoices) { cardBuilder.addOption(c); }
		cardModal = cardBuilder.create();
		
		DuelistModalChoiceBuilder duelistCardBuilder = new DuelistModalChoiceBuilder().setCallback(this).setColor(COLOR).setType(CardType.SKILL).setTitle("Choose a Card to Play");
		for (DuelistCard c : allowedCardChoices) { duelistCardBuilder.addOption(c); }
		duelistCardModal = duelistCardBuilder.create();
	}

	public DuelistCard getCard()
	{
		return this;
	}
	
	public void downgradeCardForChallenge(int roll)
	{
		if (roll <= 3) { this.cost = this.cost + 1; }
		this.baseDamage = this.damage -= 2;
		this.baseBlock = this.block -= 1;
	}
	
	public void setupStartingCopies()
	{
		this.startCopies = new ArrayList<Integer>();
		this.startCopies.add(this.startingDeckCopies);				// 0 - Default Copies
		this.startCopies.add(this.startingDragDeckCopies); 			// 1 - Dragon Copies
		this.startCopies.add(this.startingSpellcasterDeckCopies); 	// 2 - Spellcaster Copies
		this.startCopies.add(this.startingNatureDeckCopies); 		// 3 - Nature Copies
		this.startCopies.add(this.startingCreatorDeckCopies); 		// 4 - Creator Copies
		this.startCopies.add(this.startingToonDeckCopies); 			// 5 - Toon Copies
		this.startCopies.add(this.startingOrbDeckCopies); 			// 6 - Orb Copies
		this.startCopies.add(this.startingResummonDeckCopies); 		// 7 - Resumon Copies
		this.startCopies.add(this.startingGenDeckCopies); 			// 8 - Gen Copies
		this.startCopies.add(this.startingOjamaDeckCopies); 		// 9 - Ojama Copies
		this.startCopies.add(this.startingHealDeckCopies); 			// 10 - Heal Copies
		this.startCopies.add(this.startingIncDeckCopies);		 	// 11 - Increment Copies
		this.startCopies.add(this.startingExodiaDeckCopies);		// 12 - Exodia Copies
		this.startCopies.add(this.startingMagnetDeckCopies);		// 13 - Magnet Copies
		this.startCopies.add(this.startingAquaDeckCopies);			// 14 - Aqua Copies
		this.startCopies.add(this.startingMachineDeckCopies);		// 15 - Machine Copies
	}
	
	static
    {
        AbstractPlayer realPlayer = AbstractDungeon.player;
        AbstractDungeon.player = new FakePlayer();
        allOrbs.addAll(returnRandomOrbList());
        allowedOrbs.addAll(allOrbs);
        for (AbstractOrb o : allOrbs) { orbMap.put(o.name, o); }
        AbstractDungeon.player = realPlayer;
    }

	public abstract String getID();
	public abstract void onTribute(DuelistCard tributingCard);
	public abstract void onResummon(int summons);
	public abstract void summonThis(int summons, DuelistCard c, int var);
	public abstract void summonThis(int summons, DuelistCard c, int var, AbstractMonster m);

	public void checkResummon()
	{
		if (this.hasTag(DefaultMod.ZOMBIE))
		{
			block(10);
		}
		
		if (AbstractDungeon.player.hasPower(CardSafePower.POWER_ID))
		{
			draw(AbstractDungeon.player.getPower(CardSafePower.POWER_ID).amount);
		}
		
		if (AbstractDungeon.player.hasPower(CallGravePower.POWER_ID))
		{
			ArrayList<DuelistCard> zombies = new ArrayList<DuelistCard>();
			for (int i = 0; i < 4; i++)
			{
				DuelistCard newZomb = (DuelistCard) returnTrulyRandomFromSet(DefaultMod.ZOMBIE);
				while (zombies.contains(newZomb) || newZomb.originalName.equals("Soul Bone Tower"))
				{
					newZomb = (DuelistCard) returnTrulyRandomFromSet(DefaultMod.ZOMBIE);
				}
				zombies.add(newZomb);
			}
			openRandomCardChoiceDuelist(3, zombies, true);
		}
	}
	
	public static void fullResummon(DuelistCard cardCopy, boolean upgradeResummon, AbstractMonster target)
	{
		cardCopy = (DuelistCard) cardCopy.makeCopy();
		if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
		if (upgradeResummon) { cardCopy.upgrade(); }
		cardCopy.freeToPlayOnce = true;
		cardCopy.applyPowers();
		cardCopy.purgeOnUse = true;
		AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, target));
		cardCopy.onResummon(1);
		cardCopy.checkResummon();
	}
	
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) 
	{
		if (DefaultMod.debug) 
		{
			AbstractCard temp = duelistCardModal.getCard(arg2);
			AbstractCard tempB = cardModal.getCard(arg2);
			AbstractCard tempC = orbModal.getCard(arg2);
			if (temp != null && tempB != null && tempC != null)
			{
				System.out.println("theDuelist:DuelistCard:optionSelected() ---> can I see the card we picked? the card should be one of these three:: [Duelist Modal]: " + temp.originalName + ", [CardModal]: " + tempB.originalName + ", [OrbModal]: " + tempC.originalName);
			}
			else
			{
				System.out.println("theDuelist:DuelistCard:optionSelected() ---> one of the modal cards was null, so we printed this unhelpful statement. sorry.");
			}
		}
	}

	public void openRandomOrbChoice(int orbsToSelectFrom, String title)
	{
		resetOrbChoiceList(orbsToSelectFrom, title);
		orbModal.open();
	}
	
	public void openRandomOrbChoiceNoGlass(int orbsToSelectFrom)
	{
		resetOrbChoiceListNoGlass(orbsToSelectFrom);
		if (DefaultMod.debug)
		{
			for (AbstractOrb orb : allowedOrbs)
			{
				System.out.println("theDuelist:DuelistCard:openRandomOrbChoiceNoGlass(int) ---> found " + orb.name + " in allowed orbs");
			}
		}
		orbModal.open();
	}

	public void openRandomOrbChoice(String title)
	{
		resetOrbChoiceList(5, title);
		orbModal.open();
	}
	
	public void openRandomOrbChoiceNoGlass()
	{
		resetOrbChoiceListNoGlass(5);
		orbModal.open();
	}
	
	
	public void openRandomCardChoice(int cards)
	{
		resetCardChoiceList(cards);
		cardModal.open();
	}
	
	public void openRandomCardChoiceDuelist(int cards, boolean resummon)
	{
		resetCardChoiceListDuelist(cards);
		duelistCardModal.open(resummon);
	}
	
	public void openRandomCardChoice()
	{
		resetCardChoiceList(3);
		cardModal.open();
	}
	
	public void openRandomCardChoiceDuelist(boolean resummon)
	{
		resetCardChoiceListDuelist(3);
		duelistCardModal.open(resummon);
	}
	
	public void openRandomCardChoice(int cards, ArrayList<DuelistCard> cardsToChooseFrom)
	{
		resetCardChoiceList(cards, cardsToChooseFrom);
		cardModal.open();
	}
	
	public void openRandomCardChoiceAbstract(int cards, ArrayList<AbstractCard> cardsToChooseFrom)
	{
		resetCardChoiceListAbstract(cards, cardsToChooseFrom);
		cardModal.open();
	}
	
	public void openRandomCardChoiceDuelist(int cards, ArrayList<DuelistCard> cardsToChooseFrom, boolean resummon)
	{
		resetCardChoiceListDuelist(cards, cardsToChooseFrom);
		duelistCardModal.open(resummon);
	}
	
	public void resetOrbChoiceList(int numberOfOrbs, String title)
	{
		allowedOrbs = new ArrayList<AbstractOrb>();
		for (int i = 0; i < numberOfOrbs; i++)
        {
        	AbstractOrb newOrb = allOrbs.get(AbstractDungeon.cardRandomRng.random(allOrbs.size() - 1));
        	while (allowedOrbs.contains(newOrb))
        	{
        		newOrb = allOrbs.get(AbstractDungeon.cardRandomRng.random(allOrbs.size() - 1));
        	}
        	allowedOrbs.add(newOrb);
        }
		
		ModalChoiceBuilder builder = new ModalChoiceBuilder().setCallback(this).setColor(this.color).setType(CardType.SKILL).setTitle(title);
		for (AbstractOrb orb : allowedOrbs) 
		{ 
			if (DefaultMod.orbCardMap.get(orb.name) != null) 
			{ builder.addOption(DefaultMod.orbCardMap.get(orb.name)); }
			
			if (DefaultMod.debug)
			{
				System.out.println("theDuelist:DuelistCard:resetOrbChoiceList(int) ---> added " + orb.name + " to builder options");
			}
		}
		orbModal = builder.create();
	}
	
	public void resetOrbChoiceListNoGlass(int numberOfOrbs)
	{
		allowedOrbs = new ArrayList<AbstractOrb>();
		for (int i = 0; i < numberOfOrbs; i++)
        {
        	AbstractOrb newOrb = allOrbs.get(AbstractDungeon.cardRandomRng.random(allOrbs.size() - 1));
        	while (allowedOrbs.contains(newOrb) || newOrb.name.equals("Glass"))
        	{
        		newOrb = allOrbs.get(AbstractDungeon.cardRandomRng.random(allOrbs.size() - 1));
        	}
        	allowedOrbs.add(newOrb);
        }
		
		ModalChoiceBuilder builder = new ModalChoiceBuilder().setCallback(this).setColor(this.color).setType(CardType.SKILL).setTitle("Channel an Orb");
		for (AbstractOrb orb : allowedOrbs) { if (DefaultMod.orbCardMap.get(orb.name) != null) { builder.addOption(DefaultMod.orbCardMap.get(orb.name)); }}
		orbModal = builder.create();
	}

	public void resetCardChoiceList(int numberOfCards)
	{
		allowedCardChoices = new ArrayList<DuelistCard>();
		for (int i = 0; i < numberOfCards; i++)
        {
        	DuelistCard newCard = DefaultMod.myCards.get(AbstractDungeon.cardRandomRng.random(DefaultMod.myCards.size() - 1));
        	while (allowedCardChoices.contains(newCard))
        	{
        		newCard = DefaultMod.myCards.get(AbstractDungeon.cardRandomRng.random(DefaultMod.myCards.size() - 1));
        	}
        	allowedCardChoices.add(newCard);
        }
		
		ModalChoiceBuilder builder = new ModalChoiceBuilder().setCallback(this).setColor(this.color).setType(CardType.SKILL).setTitle("Choose a card to play");
		for (DuelistCard c : allowedCardChoices) { builder.addOption(c); }
		cardModal = builder.create();
	}
	
	public void resetCardChoiceListDuelist(int numberOfCards)
	{
		allowedCardChoices = new ArrayList<DuelistCard>();
		for (int i = 0; i < numberOfCards; i++)
        {
        	DuelistCard newCard = DefaultMod.myCards.get(AbstractDungeon.cardRandomRng.random(DefaultMod.myCards.size() - 1));
        	while (allowedCardChoices.contains(newCard))
        	{
        		newCard = DefaultMod.myCards.get(AbstractDungeon.cardRandomRng.random(DefaultMod.myCards.size() - 1));
        	}
        	allowedCardChoices.add(newCard);
        }
		
		DuelistModalChoiceBuilder duelistCardBuilder = new DuelistModalChoiceBuilder().setCallback(this).setColor(this.color).setType(CardType.SKILL).setTitle("Choose a Card to Play");
		for (DuelistCard c : allowedCardChoices) { duelistCardBuilder.addOption(c); }
		duelistCardModal = duelistCardBuilder.create();
	}
	
	public void resetCardChoiceList(int numberOfCards, ArrayList<DuelistCard> cardsToChooseFrom)
	{
		allowedCardChoices = new ArrayList<DuelistCard>();
		for (int i = 0; i < numberOfCards; i++)
        {
        	DuelistCard newCard = cardsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(cardsToChooseFrom.size() - 1));
        	while (allowedCardChoices.contains(newCard))
        	{
        		newCard = cardsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(cardsToChooseFrom.size() - 1));
        	}
        	allowedCardChoices.add(newCard);
        }
		
		ModalChoiceBuilder builder = new ModalChoiceBuilder().setCallback(this).setColor(this.color).setType(CardType.SKILL).setTitle("Choose a card to play");
		for (DuelistCard c : allowedCardChoices) { builder.addOption(c); }
		cardModal = builder.create();
	}
	
	public void resetCardChoiceListAbstract(int numberOfCards, ArrayList<AbstractCard> cardsToChooseFrom)
	{
		ArrayList<AbstractCard> allowedCardChoicesAbstract = new ArrayList<AbstractCard>();
		for (int i = 0; i < numberOfCards; i++)
        {
        	AbstractCard newCard = cardsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(cardsToChooseFrom.size() - 1));
        	while (allowedCardChoicesAbstract.contains(newCard))
        	{
        		newCard = cardsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(cardsToChooseFrom.size() - 1));
        	}
        	allowedCardChoicesAbstract.add(newCard);
        }
		
		ModalChoiceBuilder builder = new ModalChoiceBuilder().setCallback(this).setColor(this.color).setType(CardType.SKILL).setTitle("Choose a card to play");
		for (AbstractCard c : allowedCardChoicesAbstract) { builder.addOption(c); }
		cardModal = builder.create();
	}
	
	public void resetCardChoiceListDuelist(int numberOfCards, ArrayList<DuelistCard> cardsToChooseFrom)
	{
		allowedCardChoices = new ArrayList<DuelistCard>();
		for (int i = 0; i < numberOfCards; i++)
        {
        	DuelistCard newCard = cardsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(cardsToChooseFrom.size() - 1));
        	while (allowedCardChoices.contains(newCard))
        	{
        		newCard = cardsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(cardsToChooseFrom.size() - 1));
        	}
        	allowedCardChoices.add(newCard);
        }
		
		DuelistModalChoiceBuilder duelistCardBuilder = new DuelistModalChoiceBuilder().setCallback(this).setColor(this.color).setType(CardType.SKILL).setTitle("Choose a Card to Play");
		for (DuelistCard c : allowedCardChoices) { duelistCardBuilder.addOption(c); }
		duelistCardModal = duelistCardBuilder.create();
	}
	
	public void playRandomFromSet(int numberOfCards, CardTags set)
	{
		ArrayList<DuelistCard> options = new ArrayList<DuelistCard>();
		for (int i = 0; i < numberOfCards; i++)
		{
			DuelistCard ref = (DuelistCard) returnTrulyRandomFromSet(set);
			if (!ref.hasTag(DefaultMod.TRIBUTE)) { ref.misc = 52; }
			while (options.contains(ref))
			{
				ref = (DuelistCard) returnTrulyRandomFromSet(set);
				if (!ref.hasTag(DefaultMod.TRIBUTE)) { ref.misc = 52; }
			}
			options.add(ref);
		}
		openRandomCardChoice(numberOfCards, options);
	}
	
	public void playRandomCards(int numberOfCards)
	{
		ArrayList<DuelistCard> options = new ArrayList<DuelistCard>();
		for (int i = 0; i < numberOfCards; i++)
		{
			DuelistCard ref = (DuelistCard) returnTrulyRandomDuelistCard();
			if (!ref.hasTag(DefaultMod.TRIBUTE)) { ref.misc = 52; }
			while (options.contains(ref))
			{
				ref = (DuelistCard) returnTrulyRandomDuelistCard();
				if (!ref.hasTag(DefaultMod.TRIBUTE)) { ref.misc = 52; }
			}
			options.add(ref);
		}
		openRandomCardChoice(numberOfCards, options);
	}
	
	public void playRandomFromSetDuelist(int numberOfCards, CardTags set, boolean resummon)
	{
		ArrayList<DuelistCard> options = new ArrayList<DuelistCard>();
		for (int i = 0; i < numberOfCards; i++)
		{
			DuelistCard ref = (DuelistCard) returnTrulyRandomFromSet(set);
			if (!ref.hasTag(DefaultMod.TRIBUTE)) { ref.misc = 52; }
			while (options.contains(ref))
			{
				ref = (DuelistCard) returnTrulyRandomFromSet(set);
				if (!ref.hasTag(DefaultMod.TRIBUTE)) { ref.misc = 52; }
			}
			options.add(ref);
		}
		openRandomCardChoiceDuelist(numberOfCards, options, resummon);
	}
	
	public void playRandomCardsDuelist(int numberOfCards, boolean resummon)
	{
		ArrayList<DuelistCard> options = new ArrayList<DuelistCard>();
		for (int i = 0; i < numberOfCards; i++)
		{
			DuelistCard ref = (DuelistCard) returnTrulyRandomDuelistCard();
			if (!ref.hasTag(DefaultMod.TRIBUTE)) { ref.misc = 52; }
			while (options.contains(ref))
			{
				ref = (DuelistCard) returnTrulyRandomDuelistCard();
				if (!ref.hasTag(DefaultMod.TRIBUTE)) { ref.misc = 52; }
			}
			options.add(ref);
		}
		openRandomCardChoiceDuelist(numberOfCards, options, resummon);
	}
	
	
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
	
	public static void applyPowerTop(AbstractPower power, AbstractCreature target) {
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, player(), power, power.amount));

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
		if (this.hasTag(DefaultMod.INSECT) && player().hasPower(VioletCrystalPower.POWER_ID)) { amount = (int) Math.floor(amount * 1.5); }
		if (this.hasTag(DefaultMod.ZOMBIE) || this.hasTag(DefaultMod.FIEND)) { if (player().hasPower(GatesDarkPower.POWER_ID)) { amount = (int) Math.floor(amount * 2); }}
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(player(), player(), amount));
	}

	public static void staticBlock(int amount) 
	{
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(player(), player(), amount));
	}

	public static void applyPowerToSelf(AbstractPower power) {
		applyPower(power, player());

	}
	
	public static void applyPowerToSelfTop(AbstractPower power) {
		applyPowerTop(power, player());

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
		AbstractDungeon.actionManager.addToTop(new DiscardAction(player(), player(), amount, isRandom, false));
	}

	public static void gainEnergy(int energy) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energy));
	}

	protected void attack(AbstractMonster m, AttackEffect effect, int damageAmount) {
		if (this.hasTag(DefaultMod.DRAGON) && player().hasPower(MountainPower.POWER_ID)) {  damageAmount = (int) Math.floor(damageAmount * 1.5);  }
		if (this.hasTag(DefaultMod.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID)) {  damageAmount = (int) Math.floor(damageAmount * 1.5);  }
		if (this.hasTag(DefaultMod.INSECT) && player().hasPower(VioletCrystalPower.POWER_ID)) { damageAmount = (int) Math.floor(damageAmount * 1.5); }
		if (player().hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower) player().getPower(SummonPower.POWER_ID);
			boolean isOnlySpellcasters = instance.isOnlyTypeSummoned(DefaultMod.SPELLCASTER);
			if (isOnlySpellcasters)
			{
				block(5);
			}
		}
		if (this.hasTag(DefaultMod.ZOMBIE) || this.hasTag(DefaultMod.FIEND)) { if (player().hasPower(GatesDarkPower.POWER_ID)) { damageAmount = (int) Math.floor(damageAmount * 2); }}
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, damageTypeForTurn), effect));
	}
	
	protected void attackFast(AbstractMonster m, AttackEffect effect, int damageAmount) {
		if (this.hasTag(DefaultMod.DRAGON) && player().hasPower(MountainPower.POWER_ID)) {  damageAmount = (int) Math.floor(damageAmount * 1.5);  }
		if (this.hasTag(DefaultMod.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID)) {  damageAmount = (int) Math.floor(damageAmount * 1.5);  }
		if (this.hasTag(DefaultMod.INSECT) && player().hasPower(VioletCrystalPower.POWER_ID)) { damageAmount = (int) Math.floor(damageAmount * 1.5); }
		if (player().hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower) player().getPower(SummonPower.POWER_ID);
			boolean isOnlySpellcasters = instance.isOnlyTypeSummoned(DefaultMod.SPELLCASTER);
			if (isOnlySpellcasters)
			{
				block(5);
			}
		}
		if (this.hasTag(DefaultMod.ZOMBIE) || this.hasTag(DefaultMod.FIEND)) { if (player().hasPower(GatesDarkPower.POWER_ID)) { damageAmount = (int) Math.floor(damageAmount * 2); }}
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, damageTypeForTurn), effect, true));
	}

	protected void attackAllEnemies(AttackEffect effect, int[] damageAmounts) 
	{
		if (this.hasTag(DefaultMod.DRAGON) && player().hasPower(MountainPower.POWER_ID)) { for (int i = 0; i < damageAmounts.length; i++) { damageAmounts[i] = (int) Math.floor(damageAmounts[i] * 1.5); }}
		if (this.hasTag(DefaultMod.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID))  { for (int i = 0; i < damageAmounts.length; i++) { damageAmounts[i] = (int) Math.floor(damageAmounts[i] * 1.5); }}
		if (this.hasTag(DefaultMod.INSECT) && player().hasPower(VioletCrystalPower.POWER_ID))  { for (int i = 0; i < damageAmounts.length; i++) { damageAmounts[i] = (int) Math.floor(damageAmounts[i] * 1.5); }}
		if (player().hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower) player().getPower(SummonPower.POWER_ID);
			boolean isOnlySpellcasters = instance.isOnlyTypeSummoned(DefaultMod.SPELLCASTER);
			if (isOnlySpellcasters)
			{
				block(5);
			}
		}
		if (this.hasTag(DefaultMod.ZOMBIE) || this.hasTag(DefaultMod.FIEND)) { if (player().hasPower(GatesDarkPower.POWER_ID)) { for (int i = 0; i < damageAmounts.length; i++) { damageAmounts[i] = (int) Math.floor(damageAmounts[i] * 2); }}}
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player(), damageAmounts, damageTypeForTurn, effect));
	}

	public static void attackAll(AttackEffect effect, int[] damageAmounts, DamageType dmgForTurn)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player(), damageAmounts, dmgForTurn, effect));
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
		if (this.hasTag(DefaultMod.INSECT) && player().hasPower(VioletCrystalPower.POWER_ID)) { damage = (int) Math.floor(damage * 1.5); }
		if (player().hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower) player().getPower(SummonPower.POWER_ID);
			boolean isOnlySpellcasters = instance.isOnlyTypeSummoned(DefaultMod.SPELLCASTER);
			if (isOnlySpellcasters)
			{
				block(5);
			}
		}
		if (this.hasTag(DefaultMod.ZOMBIE) || this.hasTag(DefaultMod.FIEND)) { if (player().hasPower(GatesDarkPower.POWER_ID)) { damage = (int) Math.floor(damage * 2); }}
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
		if (this.hasTag(DefaultMod.DRAGON) && player().hasPower(MountainPower.POWER_ID)) { damage = (int) Math.floor(damage * 1.5); }
		if (this.hasTag(DefaultMod.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID)) { damage = (int) Math.floor(damage * 1.5); }
		if (this.hasTag(DefaultMod.INSECT) && player().hasPower(VioletCrystalPower.POWER_ID)) { damage = (int) Math.floor(damage * 1.5); }
		if (player().hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower) player().getPower(SummonPower.POWER_ID);
			boolean isOnlySpellcasters = instance.isOnlyTypeSummoned(DefaultMod.SPELLCASTER);
			if (isOnlySpellcasters)
			{
				block(5);
			}
		}
		if (this.hasTag(DefaultMod.ZOMBIE) || this.hasTag(DefaultMod.FIEND)) { if (player().hasPower(GatesDarkPower.POWER_ID)) { damage = (int) Math.floor(damage * 2); }}
		ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
		for (AbstractMonster m : monsters)
		{
			if (!m.isDead) 
			{ 
				damageThroughBlock(m, p, damage, effect); 
			}
		}
	}

	public void damageThroughBlockAllEnemies(AbstractPlayer p, int[] damageAmounts, AttackEffect effect)
	{
		if (this.hasTag(DefaultMod.DRAGON) && player().hasPower(MountainPower.POWER_ID)) { for (int i = 0; i < damageAmounts.length; i++) { damageAmounts[i] = (int) Math.floor(damageAmounts[i] * 1.5); }}
		if (this.hasTag(DefaultMod.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID))  { for (int i = 0; i < damageAmounts.length; i++) { damageAmounts[i] = (int) Math.floor(damageAmounts[i] * 1.5); }}
		if (this.hasTag(DefaultMod.INSECT) && player().hasPower(VioletCrystalPower.POWER_ID))  { for (int i = 0; i < damageAmounts.length; i++) { damageAmounts[i] = (int) Math.floor(damageAmounts[i] * 1.5); }}
		if (player().hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower) player().getPower(SummonPower.POWER_ID);
			boolean isOnlySpellcasters = instance.isOnlyTypeSummoned(DefaultMod.SPELLCASTER);
			if (isOnlySpellcasters)
			{
				block(5);
			}
		}
		if (this.hasTag(DefaultMod.ZOMBIE) || this.hasTag(DefaultMod.FIEND)) { if (player().hasPower(GatesDarkPower.POWER_ID)) { for (int i = 0; i < damageAmounts.length; i++) { damageAmounts[i] = (int) Math.floor(damageAmounts[i] * 2); }}}
		ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
		int damageArrayIndex = 0;
		int damageThisCycle = damageAmounts[damageArrayIndex];
		for (AbstractMonster m : monsters)
		{
			if (!m.isDead) 
			{ 
				damageThroughBlock(m, p, damageThisCycle, effect); 
			}
			damageArrayIndex++;
			if (!((damageArrayIndex + 1) > damageAmounts.length)) { damageThisCycle = damageAmounts[damageArrayIndex]; }
			else { damageArrayIndex = 0; }
		}
	}

	public static void damageAllEnemiesThorns(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.POISON, damageArray, DamageType.THORNS);
	}

	public static void poisonAllEnemies(AbstractPlayer p, int amount)
	{
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			//flash();
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
		if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:summon() ---> called summon()"); }
		if (!DefaultMod.checkTrap)
		{
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:summon() ---> no check trap, SUMMONS: " + SUMMONS); }
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
					SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
					if (instance.triggerAllowed && instance.turnTriggers > 0)
					{
						channelRandom();
						instance.turnTriggers--;
						instance.updateDescription();
					}
				} 
				
				DefaultMod.summonsThisCombat += startSummons;
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
				if (p.hasPower(SliferSkyPower.POWER_ID) && potSummons > 0) 
				{ 
					SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
					if (instance.triggerAllowed && instance.turnTriggers > 0)
					{
						channelRandom();
						instance.turnTriggers--;
						instance.updateDescription();
					}
				} 

				// Check for Ultimate Offering
				if (p.hasPower(UltimateOfferingPower.POWER_ID) && potSummons == 0 && SUMMONS != 0)
				{
					if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:summon() ---> hit Ultimate Offering: " + SUMMONS); }
					int amountToSummon = p.getPower(UltimateOfferingPower.POWER_ID).amount;
					damageSelf(3);
					incMaxSummons(p, amountToSummon);
					uoSummon(p, amountToSummon, new Token("Blood Token"));
				}

				// Update UI
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				DefaultMod.summonsThisCombat += potSummons;

				// Check for Trap Hole
				if (p.hasPower(TrapHolePower.POWER_ID) && !DefaultMod.checkTrap)
				{
					for (int i = 0; i < potSummons; i++)
					{
						TrapHolePower power = (TrapHolePower) p.getPower(TrapHolePower.POWER_ID);
						int randomNum = AbstractDungeon.cardRandomRng.random(1, 10);
						if (randomNum <= power.chance || power.chance > 10)
						{
							DefaultMod.checkTrap = true;
							power.flash();
							if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:summon ---> triggered trap hole with roll of: " + randomNum); }
							powerTribute(p, 1, false);
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
								cardCopy.checkResummon();
								if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:summon ---> trap hole resummoned properly"); }
							}
						}
						else
						{
							if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:summon ---> did not trigger trap hole with roll of: " + randomNum); }
						}
					}
				}

				// Check for Yami
				if (p.hasPower(YamiPower.POWER_ID) && c.hasTag(DefaultMod.SPELLCASTER))
				{
					spellSummon(p, 1, c);
				}

				// Update UI
				if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:summon() ---> updating summons instance"); }
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:summon() ---> summons instance amount: " + summonsInstance.amount); }
			}
		}
		else
		{			
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:summon() ---> check trap, SUMMONS: " + SUMMONS); }
			trapHoleSummon(p, SUMMONS, c);			
		}
	}

	public static void spellSummon(AbstractPlayer p, int SUMMONS, DuelistCard c)
	{
		if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:spellSummon() ---> called spellSummon()"); }
		if (!DefaultMod.checkTrap)
		{
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:spellSummon() ---> no check trap, SUMMONS: " + SUMMONS); }
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
					SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
					if (instance.triggerAllowed && instance.turnTriggers > 0)
					{
						channelRandom();
						instance.turnTriggers--;
						instance.updateDescription();
					}
				} 
				
				DefaultMod.summonsThisCombat += startSummons;
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
				if (p.hasPower(SliferSkyPower.POWER_ID) && potSummons > 0) 
				{ 
					SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
					if (instance.triggerAllowed && instance.turnTriggers > 0)
					{
						channelRandom();
						instance.turnTriggers--;
						instance.updateDescription();
					}
				}

				// Update UI
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				DefaultMod.summonsThisCombat += potSummons;

			}
		}

		else
		{			
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:spellSummon() ---> check trap, SUMMONS: " + SUMMONS); }
			trapHoleSummon(p, SUMMONS, c);		
		}
	}


	public static void powerSummon(AbstractPlayer p, int SUMMONS, String cardName, boolean fromUO)
	{
		if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> called powerSummon()"); }
		if (!DefaultMod.checkTrap)
		{
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> no check trap, SUMMONS: " + SUMMONS); }
			DuelistCard c = DefaultMod.summonMap.get(cardName);
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> c: " + c.originalName); }
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
					SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
					if (instance.triggerAllowed && instance.turnTriggers > 0)
					{
						channelRandom();
						instance.turnTriggers--;
						instance.updateDescription();
					}
				} 
				
				DefaultMod.summonsThisCombat += startSummons;
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
				if (p.hasPower(SliferSkyPower.POWER_ID) && potSummons > 0) 
				{
					SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
					if (instance.triggerAllowed && instance.turnTriggers > 0)
					{
						channelRandom();
						instance.turnTriggers--;
						instance.updateDescription();
					}
				} 

				// Check for Ultimate Offering
				if (p.hasPower(UltimateOfferingPower.POWER_ID) && potSummons == 0 && SUMMONS != 0 && !fromUO)
				{
					if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> hit Ultimate Offering, SUMMONS: " + SUMMONS); }
					int amountToSummon = p.getPower(UltimateOfferingPower.POWER_ID).amount;
					damageSelf(3);
					incMaxSummons(p, amountToSummon);
					uoSummon(p, amountToSummon, new Token("Blood Token"));
				}

				// Update UI
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				DefaultMod.summonsThisCombat += potSummons;

				// Check for Trap Hole
				if (p.hasPower(TrapHolePower.POWER_ID) && !DefaultMod.checkTrap)
				{
					for (int i = 0; i < potSummons; i++)
					{
						TrapHolePower power = (TrapHolePower) p.getPower(TrapHolePower.POWER_ID);
						int randomNum = AbstractDungeon.cardRandomRng.random(1, 10);
						if (randomNum <= power.chance || power.chance > 10)
						{
							DefaultMod.checkTrap = true;
							power.flash();
							if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon ---> triggered trap hole with roll of: " + randomNum); }
							powerTribute(p, 1, false);
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
								cardCopy.checkResummon();
								if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon ---> trap hole resummoned properly"); }
							}
						}
						else
						{
							if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon ---> did not trigger trap hole with roll of: " + randomNum); }
						}
					}
				}

				// Check for Yami
				if (p.hasPower(YamiPower.POWER_ID) && c.hasTag(DefaultMod.SPELLCASTER))
				{
					spellSummon(p, 1, c);
				}

				// Update UI
				if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> updating summons instance amount"); }
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> summons instance amount: " + summonsInstance.amount); }
			}
		}

		else
		{
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> check trap, SUMMONS: " + SUMMONS); }
			DuelistCard c = DefaultMod.summonMap.get(cardName);
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> check trap, c: " + c.originalName); }
			trapHoleSummon(p, SUMMONS, c);
		}
	}

	public static void trapHoleSummon(AbstractPlayer p, int SUMMONS, DuelistCard c)
	{		
		if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:trapHoleSummon() ---> called trapHoleSummon()"); }
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
				SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
				if (instance.triggerAllowed && instance.turnTriggers > 0)
				{
					channelRandom();
					instance.turnTriggers--;
					instance.updateDescription();
				}
			} 
			
			DefaultMod.summonsThisCombat += startSummons;
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
			if (p.hasPower(SliferSkyPower.POWER_ID) && potSummons > 0) 
			{ 
				SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
				if (instance.triggerAllowed && instance.turnTriggers > 0)
				{
					channelRandom();
					instance.turnTriggers--;
					instance.updateDescription();
				}
			} 

			// Check for Ultimate Offering
			if (p.hasPower(UltimateOfferingPower.POWER_ID) && !DefaultMod.checkUO)
			{
				DefaultMod.checkUO = true;
				if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:trapHoleSummon() ---> hit Ultimate Offering, SUMMONS: " + SUMMONS); }
				int amountToSummon = p.getPower(UltimateOfferingPower.POWER_ID).amount;
				//damageSelf(3);
				//incMaxSummons(p, amountToSummon);
				if (potSummons == 0) { uoSummon(p, amountToSummon, new Token("Blood Token")); }
			}


			// Update UI
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
			DefaultMod.summonsThisCombat += potSummons;

			// Update UI
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:trapHoleSummon() ---> updating summons instance"); }
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:trapHoleSummon() ---> summons instance amount: " + summonsInstance.amount); }

			DefaultMod.checkUO = false;
			DefaultMod.checkTrap = false;
		}
	}

	public static void uoSummon(AbstractPlayer p, int SUMMONS, DuelistCard c)
	{		
		if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:uoSummon() ---> called uoSummon()"); }
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
				SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
				if (instance.triggerAllowed && instance.turnTriggers > 0)
				{
					channelRandom();
					instance.turnTriggers--;
					instance.updateDescription();
				}
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
			if (p.hasPower(SliferSkyPower.POWER_ID) && potSummons > 0) 
			{ 
				SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
				if (instance.triggerAllowed && instance.turnTriggers > 0)
				{
					channelRandom();
					instance.turnTriggers--;
					instance.updateDescription();
				}
			} 

			// Update UI
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:uoSummon() ---> updating summons instance"); }
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:uoSummon() ---> summons instance amount: " + summonsInstance.amount); }
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

		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DefaultMod.duelistDefaults);
			config.setInt(DefaultMod.PROP_MAX_SUMMONS, DefaultMod.lastMaxSummons);
			config.save();
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:setMaxSummons() ---> ran try block, lastMaxSummons: " + DefaultMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
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
		
		if (p.hasPower(SphereKuribohPower.POWER_ID))
		{
			DuelistCard randomCard = (DuelistCard) returnTrulyRandomDuelistCard();
			AbstractDungeon.actionManager.addToTop(new RandomizedAction(randomCard, false, true, true, false, 1, 4));
		}

		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DefaultMod.duelistDefaults);
			config.setInt(DefaultMod.PROP_MAX_SUMMONS, DefaultMod.lastMaxSummons);
			config.save();
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:incMaxSummons() ---> ran try block, lastMaxSummons: " + DefaultMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
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

		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DefaultMod.duelistDefaults);
			config.setInt(DefaultMod.PROP_MAX_SUMMONS, DefaultMod.lastMaxSummons);
			config.save();
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:decMaxSummons() ---> ran try block, lastMaxSummons: " + DefaultMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
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

						// Check for Tomb Looter
						if (p.hasPower(EnergyTreasurePower.POWER_ID) && card.type.equals(CardType.ATTACK))
						{
							if (getSummons(p) == getMaxSummons(p))
							{
								gainGold(p.getPower(EnergyTreasurePower.POWER_ID).amount, p, true);
							}
						}

						if (tributeAll) { tributes = summonsInstance.amount; }
						if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
						else { summonsInstance.amount -= tributes; }

						// Check for Obelisk after tributing
						if (p.hasPower(ObeliskPower.POWER_ID))
						{
							ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
							int damageObelisk = instance.DAMAGE;
							int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
							for (int i : temp) { i = i * tributes; }
							AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
						}

						// Check for Pharaoh's Curse
						if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

						// Check for Toon Tribute power
						if (p.hasPower(TributeToonPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(returnTrulyRandomFromSets(DefaultMod.MONSTER, DefaultMod.TOON), true, true, true, true, 1, 4)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
						if (p.hasPower(TributeToonPowerB.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(returnTrulyRandomFromSet(DefaultMod.TOON), true, true, true, true, 1, 4)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
						for (DuelistCard c : tributeList) 
						{
							c.onTribute(card); 
							if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:tribute():1 ---> Called " + c.originalName + "'s onTribute()"); }
						}
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

					// Check for Tomb Looter
					if (p.hasPower(EnergyTreasurePower.POWER_ID) && card.type.equals(CardType.ATTACK))
					{
						if (getSummons(p) == getMaxSummons(p))
						{
							gainGold(p.getPower(EnergyTreasurePower.POWER_ID).amount, p, true);
						}
					}

					if (tributeAll) { tributes = summonsInstance.amount; }
					if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
					else { summonsInstance.amount -= tributes; }

					// Check for Obelisk after tributing
					if (p.hasPower(ObeliskPower.POWER_ID))
					{
						ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
						int damageObelisk = instance.DAMAGE;
						int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
						
						for (int i : temp) { i = i * tributes; }
						AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
					}

					// Check for Pharaoh's Curse
					if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

					// Check for Toon Tribute power
					if (p.hasPower(TributeToonPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(returnTrulyRandomFromSets(DefaultMod.MONSTER, DefaultMod.TOON), true, true, true, true, 1, 4)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(returnTrulyRandomFromSet(DefaultMod.TOON), true, true, true, true, 1, 4)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
					for (DuelistCard c : tributeList) 
					{
						c.onTribute(card); 
						if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:tribute():2 ---> Called " + c.originalName + "'s onTribute()"); }
					}
					return tributeList;
				}
			}
		}
		else
		{
			//card.misc = 0;
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
						ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
						int damageObelisk = instance.DAMAGE;
						int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
						
						for (int i : temp) { i = i * tributes; }
						AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
					}

					// Check for Pharaoh's Curse
					if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

					// Check for Toon Tribute power
					if (p.hasPower(TributeToonPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(returnTrulyRandomFromSets(DefaultMod.MONSTER, DefaultMod.TOON), true, true, true, true, 1, 4)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(returnTrulyRandomFromSet(DefaultMod.TOON), true, true, true, true, 1, 4)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
					for (DuelistCard c : tributeList)
					{ 
						c.onTribute(new Token());
						if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerTribute():1 ---> Called " + c.originalName + "'s onTribute()"); }
					}
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
					ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
					int damageObelisk = instance.DAMAGE;
					int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
					
					for (int i : temp) { i = i * tributes; }
					AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
				}

				// Check for Pharaoh's Curse
				if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

				// Check for Toon Tribute power
				if (p.hasPower(TributeToonPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(returnTrulyRandomFromSets(DefaultMod.MONSTER, DefaultMod.TOON), true, true, true, true, 1, 4)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
				if (p.hasPower(TributeToonPowerB.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(returnTrulyRandomFromSet(DefaultMod.TOON), true, true, true, true, 1, 4)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
				for (DuelistCard c : tributeList) 
				{
					c.onTribute(new Token()); 
					if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:powerTribute():2 ---> Called " + c.originalName + "'s onTribute()"); }
				}
				return tributes;
			}
		}
	}


	public static void tributeChecker(AbstractPlayer p, int tributes)
	{
		// Check for Obelisk after tributing
		if (p.hasPower(ObeliskPower.POWER_ID))
		{
			ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
			int damageObelisk = instance.DAMAGE;
			int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
			
			for (int i : temp) { i = i * tributes; }
			AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
		}

		// Check for Pharaoh's Curse
		if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelf(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

		// Check for Toon Tribute power
		if (p.hasPower(TributeToonPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(returnTrulyRandomFromSets(DefaultMod.MONSTER, DefaultMod.TOON), true, true, true, true, 1, 4)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
		if (p.hasPower(TributeToonPowerB.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(returnTrulyRandomFromSet(DefaultMod.TOON), true, true, true, true, 1, 4)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }
	}

	protected void upgradeName(String newName) 
	{
		this.timesUpgraded += 1;
		this.upgraded = true;
		this.name = newName;
		initializeTitle();
	}

	// turnNum arg does not work here, random buffs are generated globally now but I don't feel like fixing all the calls to this function
	public static AbstractPower applyRandomBuff(AbstractCreature p, int turnNum)
	{
		DefaultMod.resetRandomBuffs();

		// Get randomized buff
		int randomBuffNum = AbstractDungeon.cardRandomRng.random(DefaultMod.randomBuffs.size() - 1);
		AbstractPower randomBuff = DefaultMod.randomBuffs.get(randomBuffNum);
		for (int i = 0; i < DefaultMod.randomBuffs.size(); i++)
		{
			if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:applyRandomBuff() ---> buffs[" + i + "]: " + DefaultMod.randomBuffs.get(i).name + " :: amount: " + DefaultMod.randomBuffs.get(i).amount); }
		}
		if (DefaultMod.debug) { System.out.println("theDuelist:DuelistCard:applyRandomBuff() ---> generated random buff: " + randomBuff.name + " :: index was: " + randomBuffNum + " :: turnNum or amount was: " + randomBuff.amount); }
		ArrayList<AbstractPower> powers = p.powers;
		//boolean found = false;
		applyPower(randomBuff, p);
		for (AbstractPower a : powers)
		{
			//if (!a.name.equals("Time Wizard")) { a.updateDescription(); }
			a.updateDescription();
		}		
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
		//boolean found = false;
		applyPower(randomBuff, p);
		for (AbstractPower a : powers)
		{
			//if (!a.name.equals("Time Wizard")) { a.updateDescription(); }
			a.updateDescription();
		}
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
	
	public static ArrayList<AbstractOrb> returnRandomOrbList()
	{
		ArrayList<AbstractOrb> returnOrbs = new ArrayList<AbstractOrb>();
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ returnOrbs.addAll(RandomOrbHelperDualMod.returnOrbList()); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ returnOrbs.addAll(RandomOrbHelperCon.returnOrbList()); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { returnOrbs.addAll(RandomOrbHelperRep.returnOrbList()); }
		else { returnOrbs.addAll(RandomOrbHelper.returnOrbList()); }
		return returnOrbs;
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

	public static AbstractCard returnRandomFromArrayAbstract(ArrayList<AbstractCard> tributeList)
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
		if (dragonGroup.size() > 0)
		{
			return dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
		}
		else
		{
			return new Token();
		}
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
		if (dragonGroup.size() > 0)
		{
			return dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
		}
		else
		{
			return new Token();
		}
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
		if (dragonGroup.size() > 0)
		{
			return dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
		}
		else
		{
			return new Token();
		}
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
		if (dragonGroup.size() > 0)
		{
			return dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
		}
		else
		{
			return new Token();
		}
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
		if (dragonGroup.size() > 0)
		{
			return dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
		}
		else
		{
			return new Token();
		}
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
		if (matchingGroup.size() > 0)
		{
			return matchingGroup.get(AbstractDungeon.cardRandomRng.random(matchingGroup.size() - 1));
		}
		else
		{
			return new Token();
		}
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
		if (matchingGroup.size() > 0)
		{
			return matchingGroup.get(AbstractDungeon.cardRandomRng.random(matchingGroup.size() - 1));
		}
		else
		{
			return new Token();
		}
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
		DuelistCard find = DefaultMod.summonMap.get(name);
		if (find != null)
		{
			return find;
		}
		else
		{
			/*
			for (DuelistCard card : DefaultMod.myCards)
			{
				if (name.equals(card.originalName))
				{
					return (DuelistCard) card.makeCopy();
				}
			}
			*/
			return new Token();
		}
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
