package duelistmod.relics;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.cards.other.tempCards.DynamicRelicTagCard;
import duelistmod.cards.other.tempCards.DynamicRelicTypeCard;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.RelicConfigData;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.GridSort;
import duelistmod.interfaces.MillenniumItem;
import duelistmod.interfaces.MillenniumPrayerPage;
import duelistmod.interfaces.VisitFromAnubisRemovalFilter;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.List;

public class MillenniumPrayerbook extends DuelistRelic implements VisitFromAnubisRemovalFilter, MillenniumItem {
	public static final String ID = DuelistMod.makeID("MillenniumPrayerbook");
    public static final String IMG = DuelistMod.makeRelicPath("MillenniumPrayerbook.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MillenniumPrayerbook_Outline.png");
    private CardTags tag;
    private CardType type;
    private boolean run = false;
    private int cardsToHand = 1;
	private final int turnsActive;
	private final int cardsToAdd;
    private static ArrayList<AbstractCard> pool = new ArrayList<>();

	public MillenniumPrayerbook() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
		this.tag = Tags.ALL;
		this.type = CardType.CURSE;
		this.turnsActive = this.getActiveConfig().getMagic();
		this.cardsToAdd = this.getActiveConfig().getEffect();
		this.counter = turnsActive;
		pool = new ArrayList<>();
	}

	private String getTypeString() {
		if (this.tag == null || this.type == null) {
			return "";
		}

		if (this.tag.equals(Tags.ALL) && this.type.equals(CardType.CURSE)) {
			return "";
		} else if (this.tag.equals(Tags.ALL)) {
			if (this.type.equals(CardType.ATTACK)) { return "Attack"; }
			else if (this.type.equals(CardType.SKILL)) { return "Skill"; }
			else { return "Power"; }
		} else if (this.type.equals(CardType.CURSE)) {
			String tagString = this.tag.toString().toLowerCase();
			String temp = tagString.substring(0, 1).toUpperCase();
			tagString = temp + tagString.substring(1);
			return tagString;
		} else {
			return "";
		}
	}

	@Override
	public boolean canRemove() {
		boolean hasPages = false;
		for (AbstractRelic rel : AbstractDungeon.player.relics) {
			if (rel instanceof MillenniumPrayerPage) {
				hasPages = true;
				break;
			}
		}
		return !hasPages;
	}

	@Override
	public void atBattleStart() {
		pool = new ArrayList<>();
		run = true;
		CardGroup availableCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		AbstractPlayer p = AbstractDungeon.player;
		boolean page1 = p.hasRelic(PrayerPageA.ID);
		boolean page2 = p.hasRelic(PrayerPageB.ID);
		boolean page3 = p.hasRelic(PrayerPageC.ID);
		boolean page4 = p.hasRelic(PrayerPageD.ID);
		boolean page5 = p.hasRelic(PrayerPageE.ID);
		ArrayList<CardTags> tags = new ArrayList<>();
		ArrayList<CardType> types = new ArrayList<>();
		
		// Special extra types for shop page
		if (page5) {
			ArrayList<CardTags> extraTags = new ArrayList<>();
			int halfRoll = AbstractDungeon.cardRandomRng.random(1, 4);
			if (halfRoll < 4) {
				extraTags.add(Tags.ARCANE);
				extraTags.add(Tags.GIANT);
				extraTags.add(Tags.MAGNET);
			} else {
				extraTags.add(Tags.MEGATYPED);
				extraTags.add(Tags.ROSE);
				extraTags.add(Tags.OJAMA);	
			}
			tags.addAll(extraTags);
		}
		
		// Monster types - Page A/C or Default
		if (page3) {
			tags.addAll(DuelistMod.monsterTypes);
		} else if (page1) {
            ArrayList<CardTags> tagsLoc = new ArrayList<>(DuelistMod.monsterTypes);
			while (tagsLoc.size() > 5) {
				tagsLoc.remove(AbstractDungeon.cardRandomRng.random(tagsLoc.size() - 1));
			}
			tags.addAll(tagsLoc);
		} else {
			if (StartingDeck.currentDeck.getPrimaryType() != null) {
				tags.add(StartingDeck.currentDeck.getPrimaryType());
				CardTags rand = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1));
				while (rand == StartingDeck.currentDeck.getPrimaryType()) {
					rand = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1));
				}
				tags.add(rand);
			} else {
				CardTags rand = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1));
				CardTags randB = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1));
				while (rand == randB) {
					randB = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1));
				}
				tags.add(rand);
				tags.add(randB);
			}
		}
		
		// Duelist card types - Page B/D or Default
		if (page4) {
			ArrayList<CardTags> tagsLoc = new ArrayList<>();
			ArrayList<CardType> typeLoc = new ArrayList<>();
			tagsLoc.add(Tags.SPELL);
			tagsLoc.add(Tags.TRAP);
			tagsLoc.add(Tags.MONSTER);
			typeLoc.add(CardType.ATTACK);
			typeLoc.add(CardType.SKILL);
			typeLoc.add(CardType.POWER);
			types.addAll(typeLoc);
			tags.addAll(tagsLoc);
		} else if (page2) {
			ArrayList<CardTags> tagsLoc = new ArrayList<>();
			ArrayList<CardType> typeLoc = new ArrayList<>();
			int roll = AbstractDungeon.cardRandomRng.random(1, 3);
			int rollB = AbstractDungeon.cardRandomRng.random(1, 6);

			if (roll == 1) { tagsLoc.add(Tags.SPELL); }
			else if (roll == 2) { tagsLoc.add(Tags.TRAP); }
			else { tagsLoc.add(Tags.MONSTER); }

			if (rollB < 4) { typeLoc.add(CardType.ATTACK); }
			else if (rollB < 6) { typeLoc.add(CardType.SKILL); }
			else { typeLoc.add(CardType.POWER); }
			tags.addAll(tagsLoc);
			types.addAll(typeLoc);
		} else {
			ArrayList<CardType> typeLoc = new ArrayList<>();
			int roll = AbstractDungeon.cardRandomRng.random(1, 4);
			if (roll < 4) {
				typeLoc.add(CardType.ATTACK);
			}
			else {
				typeLoc.add(CardType.SKILL);
			}
			types.addAll(typeLoc);
			
		}
		ArrayList<DuelistCard> choices = DuelistCard.generateTypeCardsForPrayerbook(this, tags, types, this.cardsToHand);
		for (AbstractCard c : choices) {
			availableCards.addToTop(c);
		}
		availableCards.group.sort(GridSort.getComparator());
		availableCards.addToTop(new CancelCard());
		this.flash();
        AbstractDungeon.gridSelectScreen.open(availableCards, 1, "Choose a Card Type for Millennium Prayerbook", false);
	}
	
	@Override
	public void update() {
		super.update();
		if (run && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
			if (AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof DynamicRelicTagCard) {
				DynamicRelicTagCard ref = (DynamicRelicTagCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
				this.tag = ref.getTypeTag();
				this.cardsToHand = ref.baseMagicNumber;
				for (AbstractCard c : TheDuelist.cardPool.group) { if (c.hasTag(this.tag)) { pool.add(c.makeStatEquivalentCopy()); }}
				if (pool.size() < this.getActiveConfig().getMagic()) {
					for (AbstractCard c : DuelistMod.myCards) {
						if (c.hasTag(this.tag)) {
							pool.add(c.makeStatEquivalentCopy());
						}
					}
				}
				setDescription();
				runEffect();
			} else if (AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof DynamicRelicTypeCard) {
				DynamicRelicTypeCard ref = (DynamicRelicTypeCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
				this.type = ref.getTypeTag();
				this.cardsToHand = ref.baseMagicNumber;
				for (AbstractCard c : TheDuelist.cardPool.group) { if (c.type.equals(this.type)) { pool.add(c.makeStatEquivalentCopy()); }}
				if (pool.size() < this.getActiveConfig().getMagic()) {
					for (AbstractCard c : DuelistMod.myCards) {
						if (c.type.equals(this.type)) {
							pool.add(c.makeStatEquivalentCopy());
						}
					}
				}
				setDescription();
				runEffect();
			}
			run = false;
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			AbstractDungeon.closeCurrentScreen();
		}
	}

	public void setDescription() {
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	private void runEffect() {
		if (pool.size() > 0) {
			for (int i = 0; i < this.cardsToAdd; i++) {
				AbstractCard randomCard = pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1));
				this.addToBot(new RandomizedHandAction(randomCard, false, true, true, false, false, false, false, false, 1, 3, 0, 2, 0, 2));
			}
			this.counter--;
		}
	}
	
	@Override
	public void atTurnStart() {
		if (this.counter > 0) {
			runEffect();
			if (this.counter == 0) {
				this.grayscale = true;
			}
		}
	}
	
	@Override
	public void onVictory() {
		this.type = CardType.CURSE;
		this.tag = Tags.ALL;
		this.counter = this.turnsActive;
		this.grayscale = false;
		setDescription();
	}

	@Override
	public String getUpdatedDescription() {
		int turns = this.getActiveConfig().getMagic();
		int cards = this.getActiveConfig().getEffect();

		String d = turns == 1 ? DESCRIPTIONS[3] : DESCRIPTIONS[0];
		if (turns != 1) {
			d += turns + DESCRIPTIONS[1];
		}

		d += cards;

		if (cards == 1) {
			d += DESCRIPTIONS[2];
		} else {
			d += DESCRIPTIONS[4];
		}

		return this.getTypeString().equals("")
				? d
				: d + " NL NL #yCombat #yType: " + this.getTypeString();
	}

	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumPrayerbook();
	}

	@Override
	public RelicConfigData getDefaultConfig() {
		RelicConfigData config = new RelicConfigData();
		config.setMagic(3);
		config.setEffect(1);
		return config;
	}

	@Override
	protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) {
		List<DuelistDropdown> dropdowns = new ArrayList<>();
		RelicConfigData onLoad = this.getActiveConfig();

		settingElements.add(new ModLabel("Turns Active", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> effectOptions = new ArrayList<>();
		for (int i = 0; i < 1000; i++) { effectOptions.add(String.valueOf(i)); }
		String tooltip = "Modify the number of turns the random card effect remains active. Set to #b" + this.getDefaultConfig().getMagic() + " by default.";
		DuelistDropdown effectSelector = new DuelistDropdown(tooltip, effectOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.setMagic(i);
			this.updateConfigSettings(data);
		});
		effectSelector.setSelectedIndex(onLoad.getMagic());

		LINEBREAK(15);

		settingElements.add(new ModLabel("Cards to Hand", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		ArrayList<String> magicOptions = new ArrayList<>();
		for (int i = 0; i < 11; i++) { magicOptions.add(String.valueOf(i)); }
		tooltip = "Modify the number of #yRandomized cards to add to your hand when the effect is triggered. Set to #b" + this.getDefaultConfig().getEffect() + " by default.";
		DuelistDropdown magicSelector = new DuelistDropdown(tooltip, magicOptions, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
			RelicConfigData data = this.getActiveConfig();
			data.setEffect(i);
			this.updateConfigSettings(data);
		});
		magicSelector.setSelectedIndex(onLoad.getEffect());

		dropdowns.add(magicSelector);
		dropdowns.add(effectSelector);
		LINEBREAK(25);
		return dropdowns;
	}
}
