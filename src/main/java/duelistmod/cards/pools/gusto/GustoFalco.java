package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GustoFalco extends DuelistCard {

    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GustoFalco");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoFalco.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public GustoFalco() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.BEAST);
        this.baseSummons = this.summons = 1;
        this.baseDamage = this.damage = 6;
    }

    @Override
    public void upgrade() {
        if (upgraded) return;
        this.upgradeName();
        this.upgradeSummons(1);
        this.upgradeDamage(5);
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        attack(p);
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        block(1);
        CardGroup cardsToChooseFrom = new CardGroup(CardGroup.CardGroupType.DISCARD_PILE);
        cardsToChooseFrom.group = new ArrayList<>(player().discardPile.group.stream()
                .filter(c -> c.hasTag(Tags.MONSTER) && allowResummons(c) && c.hasTag(Tags.SPELLCASTER))
                .collect(Collectors.toList()));
        Consumer<ArrayList<AbstractCard>> resummon = selectedCards -> selectedCards.forEach(DuelistCard::resummon);
        SelectScreenHelper.open(cardsToChooseFrom, 1, "Resummon a Spellcaster", true, resummon);
    }
}
