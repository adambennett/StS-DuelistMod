package duelistmod.cards.pools.toon;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SwiftBirdmanJoe extends DuelistCard {

    public static final String ID = DuelistMod.makeID("SwiftBirdmanJoe");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("SwiftBirdmanJoe.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public SwiftBirdmanJoe() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 18;
        this.tributes = this.baseTributes = 5;
        this.tags.add(Tags.MONSTER);
		this.originalName = this.name;
        this.misc = 0;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        ArrayList<DuelistCard> tributes = tribute();
        if (targets.size() > 0) {
            if (duelist.player()) {
                normalMultidmg();
            } else if (duelist.getEnemy() != null) {
                attack(targets.get(0));
            }
        }
        if (tributes.stream().anyMatch(c -> c.hasTag(Tags.BEAST))) {
            List<AbstractCard> spellsAndTraps = duelist.getCardsPlayedThisTurn().stream().filter(c -> c.hasTag(Tags.SPELL) || c.hasTag(Tags.TRAP)).collect(Collectors.toList());
            for (AbstractCard c : spellsAndTraps) {
                if (duelist.hand().size() >= BaseMod.DEFAULT_MAX_HAND_SIZE) break;
                duelist.addCardToHand(c.makeStatEquivalentCopy());
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new SwiftBirdmanJoe();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeTributes(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
