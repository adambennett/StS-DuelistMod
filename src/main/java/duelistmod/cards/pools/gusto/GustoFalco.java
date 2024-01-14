package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GustoFalco extends DuelistCard {
    public static final String ID = DuelistMod.makeID("GustoFalco");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoFalco.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public GustoFalco() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.BEAST);
        this.baseMagicNumber = this.magicNumber = 2;
        this.baseSummons = this.summons = 1;
        this.baseDamage = this.damage = 3;
        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (upgraded) return;
        this.upgradeName();
        this.upgradeSummons(1);
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.fixUpgradeDesc();
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        summon();
        for (int i = 0; i < this.magicNumber; i++) {
            if (targets.size() > 0 && duelist.getEnemy() != null) {
                attack(targets.get(0), this.baseAFX, this.damage);
            } else if (duelist.player()) {
                attackAllEnemies();
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        super.customOnTribute(tc);
        CardGroup cardsToChooseFrom = new CardGroup(CardGroup.CardGroupType.DISCARD_PILE);
        cardsToChooseFrom.group = player().discardPile.group.stream()
                .filter(card -> card.hasTag(Tags.SPELLCASTER) && card.hasTag(Tags.MONSTER))
                .collect(Collectors.toCollection(ArrayList::new));

        Consumer<ArrayList<AbstractCard>> resummon = group -> group.forEach(DuelistCard::resummon);

        if (cardsToChooseFrom.isEmpty()) return;

        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.player()) {
            SelectScreenHelper.open(cardsToChooseFrom, 1, "Resummon a Spellcaster", true, resummon);
        } else if (duelist.getEnemy() != null && cardsToChooseFrom.size() > 0) {
            AbstractCard randomSpellcaster = cardsToChooseFrom.getRandomCard(AbstractDungeon.cardRandomRng);
            DuelistCard.anyDuelistResummon(randomSpellcaster, duelist, AbstractDungeon.player);
        }
    }
}
