package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.helpers.Util;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class RandomTributeMonsterRelic extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("RandomTributeMonsterRelic");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
	public boolean screenOpen = false;

	public RandomTributeMonsterRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}

	@Override
	public void onEquip() {
		CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		List<List<? extends AbstractCard>> cardGroupsToSearch = new ArrayList<>();
		cardGroupsToSearch.add(TheDuelist.cardPool.group);
		cardGroupsToSearch.add(DuelistMod.duelColorlessCards);
		cardGroupsToSearch.add(DuelistMod.myCards);
		ArrayList<DuelistCard> newList = CardFinderHelper.findAsDuelist(5, cardGroupsToSearch, (card) ->
			card instanceof DuelistCard &&
			((DuelistCard)card).tributes >= 1 &&
			!card.hasTag(Tags.NEVER_GENERATE) &&
			!card.hasTag(Tags.GIANT) &&
			card.hasTag(Tags.MONSTER) &&
			card.rarity != CardRarity.BASIC &&
			card.rarity != CardRarity.SPECIAL
		);

		if (newList.size() > 0) {
			for (DuelistCard c : newList) {
				if (c.tributes != 1) {
					c.modifyTributesPerm(-c.tributes + 1);
				}
			}

			for (DuelistCard c : newList) {
				group.addToBottom(c);
			}
			group.sortAlphabetically(true);
			screenOpen = true;
			AbstractDungeon.gridSelectScreen.open(group, 1, "Select a Tribute monster to add to your deck.", false);
		}
	}


	@Override
	public void update() {
		super.update();
		if (this.screenOpen && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
			this.screenOpen = false;
			AbstractDungeon.gridSelectScreen.selectedCards.get(0).unhover();
			AbstractDungeon.gridSelectScreen.selectedCards.get(0).stopGlowing();
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f));
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new RandomTributeMonsterRelic();
	}
}
