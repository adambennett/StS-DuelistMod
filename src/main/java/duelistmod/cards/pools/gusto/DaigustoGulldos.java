package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;
import java.util.function.Predicate;

public class DaigustoGulldos extends DuelistCard {
    public static final String ID = DuelistMod.makeID("DaigustoGulldos");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DaigustoGulldos.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;

    public DaigustoGulldos() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.SPELLCASTER);
        this.baseSummons = this.summons = 1;
        this.baseTributes = this.tributes = 2;
        this.baseDamage = this.damage = 14;
        this.originalName = this.name;
        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (this.upgraded) return;
        this.upgradeName();
        this.upgradeDamage(6);
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
        tribute();
        summon();
        if (targets.size() > 0 && duelist.getEnemy() != null) {
            attack(targets.get(0), this.baseAFX, this.damage);
        } else if (duelist.player()) {
            attackAllEnemies();
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        super.customOnTribute(tc);
        Predicate<AbstractCard> spellcasterAttack = card ->
                card.hasTag(Tags.SPELLCASTER) &&
                card.hasTag(Tags.MONSTER) &&
                card.type == CardType.ATTACK &&
                allowResummons(card) &&
                card != this;

        CardFinderHelper.find(1, TheDuelist.cardPool.group, DuelistMod.myCards, spellcasterAttack)
                .forEach(card -> DuelistCard.resummonOnAllEnemies(card, upgraded));
    }
}
