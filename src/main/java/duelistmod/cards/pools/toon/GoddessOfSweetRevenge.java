package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

public class GoddessOfSweetRevenge extends DynamicDamageCard implements RevengeCard {

    public static final String ID = DuelistMod.makeID("GoddessOfSweetRevenge");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GoddessOfSweetRevenge.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public GoddessOfSweetRevenge() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseBlock = this.block = 11;
        this.baseMagicNumber = this.magicNumber = 3;
        this.baseTributes = this.tributes = 1;
    	this.tags.add(Tags.MONSTER);
    	this.misc = 0;
    	this.originalName = this.name;
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return RevengeCard.super.isRevengeActive(card) && this.magicNumber > 0 ;
    }

    @Override
    public void triggerRevenge(AnyDuelist duelist) {
        if (this.magicNumber > 0) {
            AbstractCreature target = null;
            if (duelist.player()) {
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    AbstractMonster random = AbstractDungeon.getMonsters().getRandomMonster(true);
                    if (random != null) {
                        target = random;

                    }
                }
            } else if (duelist.getEnemy() != null) {
                target = AbstractDungeon.player;
            }
            if (target != null) {
                attack(target);
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
        block();
        postDuelistUseCard(owner, targets);
    }

    @Override
    public int damageFunction() {
        int total = this.magicNumber * AnyDuelist.from(this).getRevengeTriggersThisCombat();
        return Math.max(0, total);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new GoddessOfSweetRevenge();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
