package defaultmod.interfaces;

import java.util.*;
import java.util.regex.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.TooltipInfo;

public class DuelistModalChoice
{
	public interface Callback
	{
		void optionSelected(AbstractPlayer p, AbstractMonster m, int index);
	}

	private String title;
	private ArrayList<AbstractCard> cards;

	DuelistModalChoice(String title, ArrayList<AbstractCard> options)
	{
		this.title = title;
		this.cards = options;
	}

	public AbstractCard getCard(int index)
	{
		return cards.get(index);
	}

	public void open()
	{
		List<AbstractCard> cards_copy = new ArrayList<>(cards.size());
		for (AbstractCard c : cards) {
			AbstractCard copy = c.makeStatEquivalentCopy();
			copy.freeToPlayOnce = true;
			if (copy.type != AbstractCard.CardType.POWER) {
				copy.purgeOnUse = true;
			} else {
				copy.purgeOnUse = false;
			}
			cards_copy.add(copy);
		}

		AbstractDungeon.actionManager.addToBottom(new AbstractGameAction()
		{
			@Override
			public void update()
			{
				BaseMod.modalChoiceScreen.open(cards_copy, title);
				isDone = true;
			}
		});
	}

	public List<TooltipInfo> generateTooltips()
	{
		List<TooltipInfo> ret = new ArrayList<>();

		for (AbstractCard card : cards) {
			TooltipInfo tooltipInfo = new TooltipInfo(card.name, card.rawDescription);
			doDynamicVariables(card, tooltipInfo);
			ret.add(tooltipInfo);
		}

		return ret;
	}

	@SuppressWarnings("resource")
	private static void doDynamicVariables(AbstractCard card, TooltipInfo tooltipInfo)
	{
		Pattern pattern = Pattern.compile("!(.+)!(.*)");

		StringBuilder stringBuilder = new StringBuilder();
		Scanner scanner = new Scanner(tooltipInfo.description);
		while (scanner.hasNext()) {
			String tmp = scanner.next();
			Matcher matcher = pattern.matcher(tmp);
			if (matcher.find()) {
				String key = matcher.group(1);
				int num = 0;
				DynamicVariable dv = BaseMod.cardDynamicVariableMap.get(key);
				if (dv != null) {
					if (dv.isModified(card)) {
						num = dv.value(card);
					} else {
						num = dv.baseValue(card);
					}
				}
				stringBuilder.append(num);
				stringBuilder.append(matcher.group(2));
			} else {
				stringBuilder.append(tmp);
			}
			stringBuilder.append(' ');
		}
		tooltipInfo.description = stringBuilder.toString();
	}
}
