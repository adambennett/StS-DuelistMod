package duelistmod.abstracts;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;
import java.util.List;

public class TreatMagicianCard extends DuelistCard {

    private final TreatCard treat;

    public TreatMagicianCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET, TreatCard treat) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.treat = treat;
        this.cardsToPreview = treat;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.TOON);
        this.tags.add(Tags.REQUIRES_TOON_WORLD);
        this.misc = 0;
        this.originalName = this.name;
        this.magicNumber = this.baseMagicNumber = 1;
        this.summons = this.baseSummons = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        AnyDuelist duelist = AnyDuelist.from(this);
        AbstractCard treatCopy = this.treat.makeStatEquivalentCopy();
        if (this.upgraded) {
            treatCopy.upgrade();
        }
        duelist.addCardToHand(treatCopy);
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
