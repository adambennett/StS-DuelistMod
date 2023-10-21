package duelistmod.cards.other.tempCards;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.TokenCard;
import duelistmod.dto.CardPoolSaveSlotData;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.CardPoolRelic;
import duelistmod.variables.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardPoolOptionSaveSlot extends TokenCard {
    public static final String ID = DuelistMod.makeID("CardPoolOptionSaveSlot");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GENERIC_TOKEN);
    public static final String NAME = cardStrings.NAME;
    public static final String[] EXTENDED = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public 	static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private ArrayList<AbstractCard> savedPool = new ArrayList<>();
	private final int slot;

    public CardPoolOptionSaveSlot(int slot) {
    	super(ID + slot, NAME + slot, IMG, COST, EXTENDED[0], TYPE, COLOR, RARITY, TARGET);
    	this.dontTriggerOnUseCard = true;
		this.slot = slot;
    	this.savedPool = setupPool();
    	this.magicNumber = this.baseMagicNumber = this.savedPool.size();
    }
    
    public void loadCorrectDesc(boolean saving) {
    	if (saving) { this.rawDescription = EXTENDED[0]; }
    	else { this.rawDescription = EXTENDED[1]; }
    	this.initializeDescription();
    }
    
    public void resetPool() {
    	this.savedPool.clear();
    	this.baseMagicNumber = this.magicNumber = 0;
		DuelistMod.persistentDuelistData.cardPoolSaveSlotMap.remove(this.cardID);
		DuelistMod.configSettingsLoader.save();
    }
    
    // Call this when player selects save slot card from Options Relic
    public void loadPool() {
    	DuelistMod.coloredCards.clear();
		DuelistMod.toReplacePoolWith.clear();
		DuelistMod.toReplacePoolWith.addAll(this.savedPool);
		if (this.savedPool.size() > 0) {
			DuelistMod.poolIsCustomized = true;
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				config.setBool("poolIsCustomized", DuelistMod.poolIsCustomized);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		DuelistMod.shouldReplacePool = true;
		DuelistMod.relicReplacement = true;
		if (AbstractDungeon.player.hasRelic(CardPoolRelic.ID)) {
			((CardPoolRelic)AbstractDungeon.player.getRelic(CardPoolRelic.ID)).setDescription();
		}
		CardCrawlGame.dungeon.initializeCardPools();
    }
    
    // Call this when the player selects the save slot card from Save Relic
    public void setPool(ArrayList<AbstractCard> newPool) {
    	this.savedPool = newPool;
    	this.magicNumber = this.baseMagicNumber = this.savedPool.size();
		List<String> cardList = new ArrayList<>();
    	for (AbstractCard c : this.savedPool) {
			cardList.add(c.cardID);
		}
		CardPoolSaveSlotData slotData = new CardPoolSaveSlotData(cardList, this.slot);
		DuelistMod.persistentDuelistData.cardPoolSaveSlotMap.put(this.cardID, slotData);
    	DuelistMod.configSettingsLoader.save();
    }

	public List<String> getSavedPool() {
		List<String> output = DuelistMod.persistentDuelistData.cardPoolSaveSlotMap.getOrDefault(this.cardID, new CardPoolSaveSlotData()).getCardPool();
		return output != null ? output : new ArrayList<>();
	}
    
    private ArrayList<AbstractCard> setupPool() {
		ArrayList<AbstractCard> newPool = new ArrayList<>();
		for (String s : getSavedPool()) {
			if (DuelistMod.mapForCardPoolSave.containsKey(s)) {
				newPool.add(DuelistMod.mapForCardPoolSave.get(s).makeStatEquivalentCopy());
			} else if (DuelistMod.mapForCardPoolSave.containsKey("theDuelist:" + s)) {
				newPool.add(DuelistMod.mapForCardPoolSave.get("theDuelist:" + s).makeStatEquivalentCopy());
			}
		}
		return newPool;
	}

    @Override public AbstractCard makeCopy() {
		return new CardPoolOptionSaveSlot(this.slot);
	}

	public int getSlot() {
		return slot;
	}

	public static int compareSaveSlots(AbstractCard a, AbstractCard b) {
		boolean aIsLegacy = false;
		boolean bIsLegacy = false;
		boolean aIsNewFormat = false;
		boolean bIsNewFormat = false;
		boolean aIsResetFormat = false;
		boolean bIsResetFormat = false;
		if (a instanceof CardPoolOptionSaveA || a instanceof CardPoolOptionSaveB || a instanceof CardPoolOptionSaveC) {
			aIsLegacy = true;
		}
		if (b instanceof CardPoolOptionSaveA || b instanceof CardPoolOptionSaveB || b instanceof CardPoolOptionSaveC) {
			bIsLegacy = true;
		}
		if (a instanceof CardPoolOptionSaveSlot) {
			aIsNewFormat = true;
		}
		if (b instanceof CardPoolOptionSaveSlot) {
			bIsNewFormat = true;
		}
		if (a instanceof CardPoolOptionResetSaveSlot) {
			aIsResetFormat = true;
		}
		if (b instanceof CardPoolOptionResetSaveSlot) {
			bIsResetFormat = true;
		}
		if (aIsLegacy && bIsLegacy) {
			return a.compareTo(b);
		}
		if (aIsLegacy) {
			return -1;
		}
		if (bIsLegacy) {
			return 1;
		}
		if (aIsNewFormat && bIsNewFormat) {
			return Integer.compare(((CardPoolOptionSaveSlot) a).getSlot(), ((CardPoolOptionSaveSlot) b).getSlot());
		}
		if (aIsResetFormat && bIsResetFormat) {
			return Integer.compare(((CardPoolOptionResetSaveSlot) a).getSlot(), ((CardPoolOptionResetSaveSlot) b).getSlot());
		}
		else if (aIsNewFormat) {
			return -1;
		}
		else if (bIsNewFormat) {
			return 1;
		}
		else if (aIsResetFormat) {
			return -1;
		}
		else if (bIsResetFormat) {
			return 1;
		}
		return 0;
	}
}
