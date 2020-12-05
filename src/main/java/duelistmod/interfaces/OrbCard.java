package duelistmod.interfaces;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import duelistmod.abstracts.*;

public class OrbCard extends DuelistCard {

    public OrbCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public void onTribute(DuelistCard tributingCard) {

    }

    @Override
    public void onResummon(int summons) {

    }

    @Override
    public void summonThis(int summons, DuelistCard c, int var) {

    }

    @Override
    public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
