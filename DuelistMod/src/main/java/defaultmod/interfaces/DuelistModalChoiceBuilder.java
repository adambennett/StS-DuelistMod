package defaultmod.interfaces;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.helpers.*;

public class DuelistModalChoiceBuilder
{
    private String title = "Choose an Option";
    private ArrayList<AbstractCard> options = new ArrayList<>();
    private ModalChoice.Callback callback = null;
    private AbstractCard.CardColor color = AbstractCard.CardColor.COLORLESS;
    private AbstractCard.CardType type = AbstractCard.CardType.SKILL;

    public DuelistModalChoiceBuilder()
    {
    }

    public DuelistModalChoice create()
    {
        return new DuelistModalChoice(title, options);
    }

    public DuelistModalChoiceBuilder setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public DuelistModalChoiceBuilder addOption(String description, AbstractCard.CardTarget target)
    {
        return addOption(null, description, target);
    }

    public DuelistModalChoiceBuilder addOption(String title, String description, AbstractCard.CardTarget target)
    {
        if (title == null) {
            title = "Option " + (options.size() + 1);
        }
        return addOption(new DuelistModalChoiceCard(title, description, type, color, target, options.size(), callback));
    }

    public DuelistModalChoiceBuilder addOption(AbstractCard card)
    {
        options.add(card);
        return this;
    }

    // setColor - Sets the color for all following automatically created ModalChoiceCards.
    //            Does not affect previously added cards
    public DuelistModalChoiceBuilder setColor(AbstractCard.CardColor color)
    {
        this.color = color;
        return this;
    }

    // setType - Sets the card type for all following automatically created ModalChoiceCards.
    //           Does not affect previously added cards
    public DuelistModalChoiceBuilder setType(AbstractCard.CardType type)
    {
        this.type = type;
        return this;
    }

    // setCallback - Sets the callback for all following automatically created ModalChoiceCards.
    //               Does not affect previously added cards
    public DuelistModalChoiceBuilder setCallback(ModalChoice.Callback callback)
    {
        this.callback = callback;
        return this;
    }
}
