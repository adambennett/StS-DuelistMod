package duelistmod.relics;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.poolhelpers.GlobalPoolHelper;
import duelistmod.interfaces.*;

public class CardPoolOptionsRelic extends DuelistRelic implements ClickableRelic, VisitFromAnubisRemovalFilter
{
	public static final String ID = DuelistMod.makeID("CardPoolOptionsRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("CardPoolOptionsRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolOptionsRelic_Outline.png");
	public CardGroup pool;

	public CardPoolOptionsRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.MASTER_DECK);
		refreshPool();
	}
	
	public void refreshPool()
	{
		pool.clear();
		pool.group.addAll(generateAddableCards());
	}
	
	private ArrayList<AbstractCard> generateAddableCards()
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		ArrayList<CardPoolOptionTypeCard> types = new ArrayList<>();
		cards.add(new CardPoolOptionSaveA());
		cards.add(new CardPoolOptionSaveB());
		cards.add(new CardPoolOptionSaveC());
		cards.add(new CardPoolOptionResetSave());
		
		types.add(new CardPoolOptionAqua());
		types.add(new CardPoolOptionArcane());
		types.add(new CardPoolOptionDino());
		types.add(new CardPoolOptionDragon());
		types.add(new CardPoolOptionFiend());
		types.add(new CardPoolOptionIncrement());
		types.add(new CardPoolOptionInsect());
		types.add(new CardPoolOptionMachine());
		types.add(new CardPoolOptionNaturia());
		types.add(new CardPoolOptionOjama());
		types.add(new CardPoolOptionPlant());
		types.add(new CardPoolOptionRock());
		types.add(new CardPoolOptionSpellcaster());
		types.add(new CardPoolOptionStandard());
		types.add(new CardPoolOptionToon());
		types.add(new CardPoolOptionWarrior());
		types.add(new CardPoolOptionZombie());
		
		if (DuelistMod.baseGameCards)
		{
			types.add(new CardPoolOptionRed());
			types.add(new CardPoolOptionBlue());
			types.add(new CardPoolOptionGreen());
			types.add(new CardPoolOptionPurple());
			types.add(new CardPoolOptionColorless());
		}
		
		if (DuelistMod.isInfiniteSpire) { types.add(new CardPoolOptionBlack()); }
		if (DuelistMod.isAnimator) { types.add(new CardPoolOptionAnimator()); }
		if (DuelistMod.isClockwork) { types.add(new CardPoolOptionClockwork()); }	
		if (DuelistMod.isConspire) { types.add(new CardPoolOptionConspire()); }
		if (DuelistMod.isDisciple) { types.add(new CardPoolOptionDisciple()); }
		if (DuelistMod.isGatherer) { types.add(new CardPoolOptionGatherer()); }
		if (DuelistMod.isHubris && DuelistMod.isInfiniteSpire) { types.add(new CardPoolOptionHubris()); }
		if (DuelistMod.isReplay) { types.add(new CardPoolOptionReplay()); }
		Collections.sort(types);
		for (CardPoolOptionTypeCard c : types) { if (c.canAdd) { cards.add(c); }}
		
		return cards;
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new CardPoolOptionsRelic();
	}
	
	private void setupSaveSlots()
	{
		for (AbstractCard c : this.pool.group)
		{
			if (c instanceof CardPoolOptionSaveA) { 
				CardPoolOptionSaveA ca = (CardPoolOptionSaveA)c;
				ca.loadCorrectDesc(false);
			}
			else if (c instanceof CardPoolOptionSaveB) { 
				CardPoolOptionSaveB ca = (CardPoolOptionSaveB)c;
				ca.loadCorrectDesc(false);
			}
			else if (c instanceof CardPoolOptionSaveC) { 
				CardPoolOptionSaveC ca = (CardPoolOptionSaveC)c;
				ca.loadCorrectDesc(false);
			}
		}
	}

	@Override
	public void onRightClick() 
	{
		refreshPool();
		setupSaveSlots();
		DuelistMod.duelistCardSelectScreen.open(true, this.pool, 1, "Select an Option", this::confirmLogic);
	}

	private void confirmLogic(List<AbstractCard> selectedCards) {
		for (AbstractCard c : selectedCards) {
			if (c instanceof CardPoolOptionSaveA) {
				CardPoolOptionSaveA ca = (CardPoolOptionSaveA)c;
				ca.loadPool();
				GlobalPoolHelper.resetGlobalDeckFlags();
			} else if (c instanceof CardPoolOptionSaveB) {
				CardPoolOptionSaveB ca = (CardPoolOptionSaveB)c;
				ca.loadPool();
				GlobalPoolHelper.resetGlobalDeckFlags();
			} else if (c instanceof CardPoolOptionSaveC) {
				CardPoolOptionSaveC ca = (CardPoolOptionSaveC)c;
				ca.loadPool();
				GlobalPoolHelper.resetGlobalDeckFlags();
			} else if (c instanceof CardPoolOptionTypeCard) {
				CardPoolOptionTypeCard ca = (CardPoolOptionTypeCard)c;
				ca.loadPool();
			} else if (c instanceof CardPoolOptionResetSave) {
				CardPoolOptionResetSave ca = (CardPoolOptionResetSave)c;
				ca.loadPool();
			}
		}
		if (AbstractDungeon.player.hasRelic(CardPoolRelic.ID)) {
			((CardPoolRelic)AbstractDungeon.player.getRelic(CardPoolRelic.ID)).setDescription();
		}
	}
}
