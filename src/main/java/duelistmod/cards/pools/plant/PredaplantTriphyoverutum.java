package duelistmod.cards.pools.plant;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class PredaplantTriphyoverutum extends DuelistCard implements RevengeCard {
    public static final String ID = DuelistMod.makeID("PredaplantTriphyoverutum");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("PredaplantTriphyoverutum.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AbstractGameAction.AttackEffect AFX = AbstractGameAction.AttackEffect.POISON;
    private static final int COST = 3;

    public PredaplantTriphyoverutum() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 30;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.PREDAPLANT);
        this.tags.add(Tags.DINOSAUR);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.PLANT);
        this.tags.add(Tags.GOOD_TRIB);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.baseTributes = this.tributes = 1;
    	this.setupStartingCopies();
    }

    @Override
    public void triggerRevenge(AnyDuelist duelist) {
        ArrayList<AbstractCard> list = findAllOfTypeForResummon(Tags.PREDAPLANT, 1);
        for (AbstractCard c : list) {
            if (duelist.player()) {
                AbstractMonster mon = AbstractDungeon.getRandomMonster();
                if (mon != null && !mon.isDying && !mon.isDead) {
                    resummon(c, mon, false, true);
                }
            } else if (duelist.getEnemy() != null) {
                DuelistCard.anyDuelistResummon(c, duelist, AbstractDungeon.player, true);
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        if (targets.size() > 0) {
            attack(targets.get(0), AFX, this.damage);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new PredaplantTriphyoverutum();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
