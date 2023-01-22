package duelistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.relics.FrozenEye;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.ExplosiveToken;
import duelistmod.cards.other.tokens.SuperExplodingToken;
import duelistmod.cards.other.tokens.Token;
import duelistmod.helpers.PowHelper;
import duelistmod.interfaces.ImmutableList;
import duelistmod.powers.duelistPowers.CanyonPower;
import duelistmod.relics.MillenniumKey;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.HashMap;

public class SummonPower extends TwoAmountPower
{
	public static final String POWER_ID = DuelistMod.makeID("SummonPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.SUMMON_POWER);

	private int maxSummons = DuelistMod.defaultMaxSummons;
	private final ArrayList<String> cardsSummonedNames = new ArrayList<>();
	private final ArrayList<String> coloredSummonList = new ArrayList<>();
	private ArrayList<DuelistCard> cardsSummoned = new ArrayList<>();
	private ImmutableList<DuelistCard> immutableCardsSummoned;
	private ImmutableList<String> immutableCardsSummonedNames;
	private final HashMap<CardTags, Integer> tagAmountsSummoned = new HashMap<>();
	private final HashMap<String, Integer> cardsSummonedNamesCount = new HashMap<>();
	private int allExplosiveTokens = 0;
	private int tokensSummoned = 0;

	// Constructor for summon() in DuelistCard
	public SummonPower(AbstractCreature owner, int newAmount, String desc, DuelistCard c) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = newAmount;
		this.amount2 = DuelistMod.defaultMaxSummons;
		this.img = new Texture(IMG);
		this.description = desc;
		this.canGoNegative = false;
		this.type = PowerType.BUFF;
		
		// Check the last max summon value in case the player lost the summon power somehow during battle after changing their max summons
		if (DuelistMod.lastMaxSummons != getMaxSummons()) { 
			setMaxSummons(DuelistMod.lastMaxSummons);
		}
		
		// Force max summons of 5 when player has Millennium Key
		if (AbstractDungeon.player.hasRelic(MillenniumKey.ID)) {
			setMaxSummons(5); 
		}
		
		// Add the new summon(s) to the list
		ArrayList<DuelistCard> newList = new ArrayList<>();
		for (int i = 0; i < newAmount; i++) {
			if (i < getMaxSummons()) {
				newList.add((DuelistCard) c.makeStatEquivalentCopy());
			}
		}
		this.setCardsSummoned(newList);
	}

	
	// Constructor for powerSummon() in DuelistCard
	public SummonPower(AbstractCreature owner, int newAmount, String newSummon, String desc) {
		// Set power fields
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = newAmount;
		this.amount2 = DuelistMod.defaultMaxSummons;
		this.img = new Texture(IMG);
		this.description = desc;
		this.canGoNegative = false;
		this.type = PowerType.BUFF;
		
		// Check the last max summon value in case the player lost the summon power somehow during battle after changing their max summons
		if (DuelistMod.lastMaxSummons != getMaxSummons()) { 
			setMaxSummons(DuelistMod.lastMaxSummons); 
		}
				
		// Force max summons of 5 when player has Millennium Key
		if (AbstractDungeon.player.hasRelic(MillenniumKey.ID)) {
			setMaxSummons(5); 
		}

		// Add the new summon(s) to the list
		ArrayList<DuelistCard> newList = new ArrayList<>();
		for (int i = 0; i < newAmount; i++) {
			if (i < getMaxSummons()) {
				newList.add((DuelistCard) DuelistMod.summonMap.get(newSummon).makeStatEquivalentCopy());
			}
		}
		this.setCardsSummoned(newList);
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) {
		// Rock-type blocking effect
		CanyonPower canyonPow = PowHelper.getPower(CanyonPower.POWER_ID);
		int canyonBonus = canyonPow != null ? canyonPow.amount : 0;
		int rocks = this.tagAmountsSummoned.getOrDefault(Tags.ROCK, 0);
		int amt = (rocks * (DuelistMod.rockBlock + canyonBonus));
		if (amt > 0) {
			DuelistCard.staticBlock(amount);
		}
		
		// Remove Spirits
		ArrayList<DuelistCard> newList = new ArrayList<>();
		for (DuelistCard c : getCardsSummoned()) {
			if (!c.hasTag(Tags.SPIRIT)) {
				newList.add(c);
			}
		}
		this.setCardsSummoned(newList);
	}
	
	@Override
	public void atStartOfTurn() {
		updateCount();
		updateStringColors();
		updateDescription();
	}
	
	public boolean hasExplosiveTokens() {
		return this.allExplosiveTokens > 0;
	}

	public int numExplosiveTokens() {
		return this.allExplosiveTokens;
	}
	
	public boolean isEveryMonsterCheck(CardTags tag, boolean tokensAreChecked) {
		if (getNumberOfTypeSummoned(tag) == this.amount && this.amount > 0) {
			return true;
		} else if (getNumberOfTypeSummoned(tag) - getNumberOfTokens() == this.amount && this.amount > 0) {
			return !tokensAreChecked;
		} 
		return false;
	}
	
	public int getNumberOfTokens() {
		return this.tokensSummoned;
	}
	
	public int getNumberOfTypeSummoned(CardTags type) {
		return this.tagAmountsSummoned.getOrDefault(type, 0);
	}

	public int getNumberOfTypeSummonedForTributes(CardTags type, int tributes) {
		int numSummoned = this.tagAmountsSummoned.getOrDefault(type, 0);
		return Math.min(numSummoned, tributes);
	}
	
	public boolean typeSummonsMatchMax(CardTags type) {
		return this.tagAmountsSummoned.getOrDefault(type, 0) == this.getMaxSummons();
	}
	
	public boolean isMonsterSummoned(String name) {
		return this.cardsSummonedNamesCount.getOrDefault(name, 0) > 0;
	}
	
	public void updateStringColors() {
		this.coloredSummonList.clear();
		ArrayList<CardTags> goodTags = new ArrayList<>();
		goodTags.add(Tags.AQUA);
		goodTags.add(Tags.FIEND);
		goodTags.add(Tags.DRAGON);
		goodTags.add(Tags.SUPERHEAVY);
		goodTags.add(Tags.MACHINE);
		goodTags.add(Tags.INSECT);
		goodTags.add(Tags.PLANT);
		goodTags.add(Tags.TOON_POOL);
		if (!DuelistMod.warriorTribThisCombat) { 
			goodTags.add(Tags.WARRIOR); 
		}
		
		for (DuelistCard s : getCardsSummoned()) {
			if (s == null) s = new Token();
			String coloredString;
			if (s.hasTag(Tags.MEGATYPED)) {
				coloredString = "[#BD61FF]" + s.originalName;
				coloredString = coloredString.replaceAll("\\s", " [#BD61FF]");
			} else if (s.hasTag(Tags.NATURIA)) {
				coloredString = "[#008000]" + s.originalName;
				coloredString = coloredString.replaceAll("\\s", " [#008000]");
			} else if (s.hasTag(Tags.BAD_TRIB)) {
				coloredString = "[#FF5252]" + s.originalName;
				coloredString = coloredString.replaceAll("\\s", " [#FF5252]");
			} else if (s.hasTag(Tags.TOKEN)) {
				coloredString = "[#C0B0C0]" + s.originalName;
				coloredString = coloredString.replaceAll("\\s", " [#C0B0C0]");
			} else if ((s.hasTag(Tags.GOOD_TRIB)) || (hasTags(s, goodTags))) {
				coloredString = "#b" + s.originalName;
				coloredString = coloredString.replaceAll("\\s", " #b");
			} else {
				coloredString = s.originalName;
			}
			this.coloredSummonList.add(coloredString);
		}
	}

	private static boolean hasTags(AbstractCard c, ArrayList<CardTags> tags) {
		boolean hasAnyTag = false;
		for (CardTags t : tags) { if (c.hasTag(t)) { hasAnyTag = true; }}
		return hasAnyTag;
	}

	@Override
	public void updateDescription() {
		if (this.amount != getCardsSummonedNames().size()) {
			this.amount = getCardsSummonedNames().size();
		}
		if (this.amount > 0) {
			StringBuilder summonsString = new StringBuilder();
			for (String s : this.coloredSummonList) {
				summonsString.append(s).append(", ");
			}
			int endingIndex = summonsString.lastIndexOf(",");
			String amtStr = endingIndex > -1 ? this.amount + "" : "0";
			String finalSummonsString = endingIndex > -1 ? summonsString.substring(0, endingIndex) + "." : "#bNone.";
			this.description = DESCRIPTIONS[0] + amtStr + DESCRIPTIONS[1] + getMaxSummons() + DESCRIPTIONS[2] + finalSummonsString;
		} else {
			this.cardsSummoned.clear();
			this.emptySummons();
			this.description = DESCRIPTIONS[0] + "0" + DESCRIPTIONS[1] + getMaxSummons() + DESCRIPTIONS[2] + "#bNone.";
		} 
		
		boolean foundBigEye = isMonsterSummoned("Big Eye");
		if (!foundBigEye && DuelistMod.gotFrozenEyeFromBigEye) {
			AbstractDungeon.player.loseRelic(FrozenEye.ID);
		}
	}

	public void updateCount() {
		if (this.amount > getMaxSummons()) {
			DuelistCard.powerTribute(AbstractDungeon.player, this.amount - getMaxSummons(), false);
			this.amount = getMaxSummons(); 
		}
		if (this.amount < 0) {
			DuelistCard.powerTribute(AbstractDungeon.player, 0, true);
			this.amount = 0; 
		}
	}

	public void setCardsSummoned(ArrayList<DuelistCard> cardsSummoned) {
		this.emptySummons();
		this.cardsSummoned = cardsSummoned;
		for (DuelistCard c : cardsSummoned) {
			this.cardsSummonedNames.add(c.originalName);
			this.cardsSummonedNamesCount.compute(c.originalName, (k, v) -> v == null ? 1 : v + 1);
			for (CardTags tag : c.tags) {
				this.tagAmountsSummoned.compute(tag, (k, v) -> v == null ? 1 : v + 1);
			}
			if (c instanceof ExplosiveToken || c instanceof SuperExplodingToken) {
				this.allExplosiveTokens++;
			} else if (c.hasTag(Tags.TOKEN)) {
				this.tokensSummoned++;
			}
		}
		this.amount = this.cardsSummoned.size();
		this.immutableCardsSummoned = new ImmutableList<>(this.cardsSummoned);
		this.immutableCardsSummonedNames = new ImmutableList<>(this.cardsSummonedNames);
		this.updateCount();
		this.updateStringColors();
		this.updateDescription();
	}
	
	private void emptySummons() {		
		this.allExplosiveTokens = 0;
		this.cardsSummonedNames.clear();
		this.cardsSummonedNamesCount.clear();
		this.tagAmountsSummoned.clear();
		this.immutableCardsSummoned = new ImmutableList<>(this.cardsSummoned);
		this.immutableCardsSummonedNames = new ImmutableList<>(this.cardsSummonedNames);
	}

	public void addSummon(DuelistCard card) {
		this.cardsSummoned.add(card);
		this.setCardsSummoned(this.cardsSummoned);
	}

	public DuelistCard tribute(DuelistCard card) {
		if (this.getCardsSummoned().size() > 0) {
			int endIndex = this.getCardsSummoned().size() - 1;
			DuelistCard temp = this.getCardsSummoned().get(endIndex);
			if (temp != null) {
				DuelistCard.handleOnTributeForAllAbstracts(temp, card);
				this.removeSummonAt(endIndex);
				return temp;
			}
		}
		return null;
	}

	public void removeSummonAt(int index) {
		this.cardsSummoned.remove(index);
		this.setCardsSummoned(this.cardsSummoned);
	}

	public ImmutableList<DuelistCard> getCardsSummoned() {
		return this.immutableCardsSummoned == null
				? new ImmutableList<>(this.cardsSummoned)
				: this.immutableCardsSummoned;
	}

	public ImmutableList<String> getCardsSummonedNames() {
		return this.immutableCardsSummonedNames == null
				? new ImmutableList<>(this.cardsSummonedNames)
				: this.immutableCardsSummonedNames;
	}

	public int getMaxSummons() {
		return maxSummons;
	}

	public void setMaxSummons(int maxSummons) {
		this.maxSummons = maxSummons;
		this.amount2 = this.maxSummons;
		if (getMaxSummons() > DuelistMod.highestMaxSummonsObtained) {
			DuelistMod.highestMaxSummonsObtained = getMaxSummons();
		}
	}
}
