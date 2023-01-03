package duelistmod.abstracts;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.cards.other.tokens.Token;

public class NamelessTombCard extends DuelistCard {

    public NamelessTombCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public String getID() { return this.cardID; }

    @Override
    public void onTribute(DuelistCard tributingCard) {}

    @Override
    public void onResummon(int summons) {}

    @Override
    public void summonThis(int summons, DuelistCard c, int var) {}

    @Override
    public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}

    @Override
    public void upgrade() {}

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    public DuelistCard getStandardVersion() { return new Token(); }
}
