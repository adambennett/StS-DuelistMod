package duelistmod.speedster.SpeedsterUtil;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardInfo {
    public String cardName;
    public int cardCost;
    public AbstractCard.CardType cardType;
    public AbstractCard.CardTarget cardTarget;
    public AbstractCard.CardRarity cardRarity;

    public CardInfo(String cardName, int cardCost, AbstractCard.CardType cardType, AbstractCard.CardTarget cardTarget, AbstractCard.CardRarity cardRarity)
    {
        this.cardName = cardName;
        this.cardCost = cardCost;
        this.cardType = cardType;
        this.cardTarget = cardTarget;
        this.cardRarity = cardRarity;
    }

    public CardInfo(String cardName, int cardCost, AbstractCard.CardType cardType, AbstractCard.CardTarget cardTarget)
    {
        this(cardName, cardCost, cardType, cardTarget, AbstractCard.CardRarity.BASIC);
    }
}