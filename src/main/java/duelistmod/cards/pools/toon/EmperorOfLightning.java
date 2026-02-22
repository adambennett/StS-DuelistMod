package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.RevengeDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.orbs.enemy.EnemyLightning;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class EmperorOfLightning extends RevengeDuelistCard {

    public static final String ID = DuelistMod.makeID("EmperorOfLightning");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("EmperorOfLightning.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public EmperorOfLightning() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 18;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.THUNDER);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.baseTributes = this.tributes = 2;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return super.isRevengeActive(card) && this.magicNumber > 0;
    }

    @Override
    public void onRevengeTriggered(AnyDuelist duelist) {
        if (this.magicNumber > 0) {
            if (duelist.player()) {
                int count = 0;
                for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                    if (mon != null && !mon.isDeadOrEscaped()) {
                        count++;
                    }
                }
                if (count > 0) {
                    duelist.channel(new Lightning(), count);
                }
            } else if (duelist.getEnemy() != null) {
                duelist.channel(new EnemyLightning());
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
            attack(targets.get(0));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new EmperorOfLightning();
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
