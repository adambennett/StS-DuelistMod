package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Gandora extends DynamicDamageCard {
    public static final String ID = DuelistMod.makeID("Gandora");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GANDORA);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.FIRE;
    private static final int COST = 3;

    public Gandora() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.GOOD_TRIB);
        this.tags.add(Tags.NO_CARD_FOR_RANDOM_DECK_POOLS);
        this.tags.add(Tags.NO_CREATOR);
        this.tags.add(Tags.FULL);
        this.tags.add(Tags.X_COST);
        this.tags.add(Tags.FORCE_TRIB_FOR_RESUMMONS);
        this.originalName = this.name;
        this.tributes = this.baseTributes = 0;
        this.baseMagicNumber = this.magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        int x = xCostTribute();
        ArrayList<UUID> exhausted = new ArrayList<>();
        ArrayList<AbstractCard> drawCards = new ArrayList<AbstractCard>(duelist.drawPile());

        if (drawCards.size() > x) {
            for (int i = 0; i < x; i++) {
                AbstractCard c = drawCards.get(AbstractDungeon.cardRandomRng.random(drawCards.size() - 1));
                while (exhausted.contains(c.uuid)) {
                    c = drawCards.get(AbstractDungeon.cardRandomRng.random(drawCards.size() - 1));
                }
                this.addToTop(new ExhaustSpecificCardAction(c, duelist.drawPileGroup()));
                exhausted.add(c.uuid);
            }
        } else if (drawCards.size() > 0) {
            for (AbstractCard c : drawCards) {
                exhausted.add(c.uuid);
                this.addToTop(new ExhaustSpecificCardAction(c, duelist.drawPileGroup()));
            }
        }

        if (targets.size() > 0) {
            attack(targets.get(0), AFX, this.damage);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public int damageFunction() {
        AnyDuelist duelist = AnyDuelist.from(this);
        int x = getSummons(duelist.creature());
        if (duelist.drawPile().size() < x) {
            x = duelist.drawPile().size();
        }
        return this.magicNumber * x;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Gandora();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
