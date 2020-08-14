package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.cards.other.tempCards.*;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.GridSort;
import duelistmod.interfaces.*;
import duelistmod.variables.Tags;

public class MillenniumPrayerbook extends DuelistRelic implements VisitFromAnubisRemovalFilter
{

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumPrayerbook");
    public static final String IMG = DuelistMod.makeRelicPath("MillenniumPrayerbook.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MillenniumPrayerbook_Outline.png");
    private CardTags tag = Tags.ALL;
    private CardType type = CardType.CURSE;
    private boolean run = false;
    private int cardsToHand = 1;
    private static ArrayList<AbstractCard> pool = new ArrayList<>();

	public MillenniumPrayerbook() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
		this.tag = Tags.ALL;
		this.type = CardType.CURSE;
		this.setCounter(1);
		pool = new ArrayList<>();
	}

	private String getTypeString()
	{
		if (this.tag == null || this.type == null) { return ""; }
		if (this.tag.equals(Tags.ALL) && this.type.equals(CardType.CURSE)) { return ""; }
		else if (this.tag.equals(Tags.ALL))
		{
			if (this.type.equals(CardType.ATTACK)) { return "Attack"; }
			else if (this.type.equals(CardType.SKILL)) { return "Skill"; }
			else { return "Power"; }
		}
		else if (this.type.equals(CardType.CURSE))
		{
			String tagString = this.tag.toString().toLowerCase();
			String temp = tagString.substring(0, 1).toUpperCase();
			tagString = temp + tagString.substring(1);
			return tagString;
		}
		else { return ""; }
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
	
	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
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
		if (page5)
		{
			ArrayList<CardTags> extraTags = new ArrayList<>();
			int halfRoll = AbstractDungeon.cardRandomRng.random(1, 4);
			if (halfRoll < 4)
			{
				extraTags.add(Tags.ARCANE);
				extraTags.add(Tags.GIANT);
				extraTags.add(Tags.MAGNET);
			}
			else
			{
				extraTags.add(Tags.MEGATYPED);
				extraTags.add(Tags.ROSE);
				extraTags.add(Tags.OJAMA);	
			}
			tags.addAll(extraTags);
		}
		
		// Monster types - Page A/C or Default
		if (page3) { tags.addAll(DuelistMod.monsterTypes);  }
		else if (page1)
		{
			ArrayList<CardTags> tagsLoc = new ArrayList<>();
			tagsLoc.addAll(DuelistMod.monsterTypes);
			while (tagsLoc.size() > 6) { tagsLoc.remove(AbstractDungeon.cardRandomRng.random(tagsLoc.size() - 1)); }
			tags.addAll(tagsLoc);
		}
		else
		{
			ArrayList<CardTags> tagsLoc = new ArrayList<>();
			tagsLoc.addAll(DuelistMod.monsterTypes);
			while (tagsLoc.size() > 3) { tagsLoc.remove(AbstractDungeon.cardRandomRng.random(tagsLoc.size() - 1)); }
			tags.addAll(tagsLoc);
		}
		
		// Duelist card types - Page B/D or Default
		if (page4)
		{
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
		}
		else if (page2)
		{
			ArrayList<CardTags> tagsLoc = new ArrayList<>();
			ArrayList<CardType> typeLoc = new ArrayList<>();
			int roll = AbstractDungeon.cardRandomRng.random(1, 3);
			int rollB = AbstractDungeon.cardRandomRng.random(1, 3);
			if (roll == 1) { tagsLoc.add(Tags.SPELL); }
			else if (roll == 2) { tagsLoc.add(Tags.TRAP); }
			else { tagsLoc.add(Tags.MONSTER); }
			if (rollB == 1) { typeLoc.add(CardType.ATTACK); }
			else if (rollB == 2) { typeLoc.add(CardType.SKILL); }
			else { typeLoc.add(CardType.POWER); }
			tags.addAll(tagsLoc);
			types.addAll(typeLoc);
		}
		else
		{
			ArrayList<CardType> typeLoc = new ArrayList<>();
			int roll = AbstractDungeon.cardRandomRng.random(1, 2);
			if (roll == 1) { typeLoc.add(CardType.ATTACK); }
			else if (roll == 2) { typeLoc.add(CardType.SKILL); }
			types.addAll(typeLoc);
			
		}
		ArrayList<DuelistCard> choices = DuelistCard.generateTypeCardsForPrayerbook(this, tags, types, this.cardsToHand);
		for (AbstractCard c : choices) { availableCards.addToTop(c); }
		Collections.sort(availableCards.group, GridSort.getComparator());
		availableCards.addToTop(new CancelCard());
		this.flash();
        AbstractDungeon.gridSelectScreen.open(availableCards, 1, "Choose a Card Type for Millennium Prayerbook", false);
	}
	
	@Override
	public void update()
	{
		super.update();
		if (run && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1)
		{
			if (AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof DynamicRelicTagCard)
			{
				DynamicRelicTagCard ref = (DynamicRelicTagCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
				this.tag = ref.getTypeTag();
				this.cardsToHand = ref.baseMagicNumber;
				for (AbstractCard c : TheDuelist.cardPool.group) { if (c.hasTag(this.tag)) { pool.add(c.makeStatEquivalentCopy()); }}
				if (pool.size() < 1) { for (AbstractCard c : DuelistMod.myCards) { if (c.hasTag(this.tag)) { pool.add(c.makeStatEquivalentCopy()); }} }
				setDescription();
				runEffect();
			}
			else if (AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof DynamicRelicTypeCard)
			{
				DynamicRelicTypeCard ref = (DynamicRelicTypeCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
				this.type = ref.getTypeTag();
				this.cardsToHand = ref.baseMagicNumber;
				for (AbstractCard c : TheDuelist.cardPool.group) { if (c.type.equals(this.type)) { pool.add(c.makeStatEquivalentCopy()); }}
				if (pool.size() < 1) { for (AbstractCard c : DuelistMod.myCards) { if (c.type.equals(this.type)) { pool.add(c.makeStatEquivalentCopy()); }} }
				setDescription();
				runEffect();
			}
			run = false;
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			AbstractDungeon.closeCurrentScreen();
		}
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        String header = name;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(MillenniumSymbol.ID)) { header = "Millennium Puzzle (S)"; }
        tips.add(new PowerTip(header, description));
        initializeTips();
	}
	
	private void runEffect()
	{
		if (pool.size() > 0)
		{
			for (int i = 0; i < this.counter; i++)
			{
				AbstractCard randomCard = pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1));
				this.addToBot(new RandomizedHandAction(randomCard, false, true, true, false, false, false, false, false, 1, 3, 0, 2, 0, 2));
			}
		}
	}
	
	@Override
	public void atTurnStart()
	{
		runEffect();
	}
	
	@Override
	public void onVictory()
	{
		this.type = CardType.CURSE;
		this.tag = Tags.ALL;
		setDescription();
	}
	
	@Override
	public void onRightClick()
	{
		if (AbstractDungeon.player.gold >= 150 && this.counter < 5)
		{
			AbstractDungeon.player.loseGold(150);
			this.setCounter(counter + 1);
		}
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		if (this.getTypeString().equals("")) { return DESCRIPTIONS[0]; }
		else { return DESCRIPTIONS[0] + " NL NL #yCombat #yType: " + this.getTypeString(); }
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumPrayerbook();
	}
}
