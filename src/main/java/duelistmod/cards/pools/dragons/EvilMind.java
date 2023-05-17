package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.enemyDuelist.EnemyMakeTempCardInDrawPileAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class EvilMind extends DuelistCard {
    public static final String ID = DuelistMod.makeID("EvilMind");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("EvilMind.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;

    public EvilMind() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.MALICIOUS);
        this.misc = 0;
        this.originalName = this.name;
        this.exhaust = true;
        this.enemyIntent = AbstractMonster.Intent.MAGIC;
        this.cardDraw = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.draw(1);
        if (duelist.player()) {
            this.addToBot(new MakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(), 1, true, true));
        } else if (duelist.getEnemy() != null) {
            this.addToBot(new EnemyMakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(), 1, true, true));
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new EvilMind();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
    }
}
