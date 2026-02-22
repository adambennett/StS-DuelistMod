package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.NimbleDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class FeedbackWarrior extends NimbleDuelistCard {

    public static final String ID = DuelistMod.makeID("FeedbackWarrior");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("FeedbackWarrior.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public FeedbackWarrior() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 15;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WARRIOR);
        this.baseTributes = this.tributes = 1;
        this.originalName = this.name;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        if (!targets.isEmpty()) {
            attack(targets.get(0));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onNimbleTriggered(AnyDuelist duelist, List<AbstractCreature> targets) {
        ArrayList<AbstractCard> warriors = new ArrayList<>();
        for (AbstractCard c : duelist.hand()) {
            if (c != null && c.hasTag(Tags.WARRIOR) && c.canUpgrade()) {
                warriors.add(c);
            }
        }
        if (!warriors.isEmpty()) {
            AbstractCard chosen = warriors.get(AbstractDungeon.cardRandomRng.random(warriors.size() - 1));
            chosen.upgrade();
            chosen.superFlash();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FeedbackWarrior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
