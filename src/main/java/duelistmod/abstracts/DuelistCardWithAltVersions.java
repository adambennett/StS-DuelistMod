package duelistmod.abstracts;

import duelistmod.enums.StartingDeck;

public abstract class DuelistCardWithAltVersions extends DuelistCard {

    private final StartingDeck deckVersionKey;
    private final String generalVersionKey;

    public DuelistCardWithAltVersions(StartingDeck deckVersionKey, String generalVersionKey, String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.deckVersionKey = deckVersionKey;
        this.generalVersionKey = generalVersionKey;
    }

    public abstract DuelistCardWithAltVersions getSpecialVersion(StartingDeck deck, String key);

    public StartingDeck getDeckVersionKey() {
        return deckVersionKey;
    }

    public String getGeneralVersionKey() {
        return generalVersionKey;
    }
}
