package duelistmod.cards.pools.beast;

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
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class AncientCrimsonApe extends DuelistCard {
    public static final String ID = DuelistMod.makeID("AncientCrimsonApe");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AncientCrimsonApe.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public AncientCrimsonApe() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 10;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.tributes = this.baseTributes = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        tribute();
        if (targets.size() > 0) {
            attack(targets.get(0), this.baseAFX, this.damage);
            ArrayList<AbstractCard> list = findAllOfTypeForResummon(Tags.BEAST, 1);
            for (AbstractCard c : list) {
                AnyDuelist duelist = AnyDuelist.from(this);
                if (duelist.player()) {
                    resummon(c, (AbstractMonster) targets.get(0));
                } else if (duelist.getEnemy() != null) {
                    DuelistCard.anyDuelistResummon(c, duelist, AbstractDungeon.player);
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new AncientCrimsonApe();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
