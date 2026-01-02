package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.RetainForTurnsPower;
import duelistmod.variables.Tags;

public class LegendarySword extends DuelistCard {

    public static final String ID = DuelistMod.makeID("LegendarySword");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("LegendarySword.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public LegendarySword() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.SPELL);
        this.baseDamage = this.damage = 3;
        this.baseMagicNumber = this.magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int hit = 0; hit < this.magicNumber; hit++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        int dmg = this.damage != 0 ? (int) Math.floor((double) m.currentBlock / this.damage) : 0;
        int unblockedHits = this.magicNumber - dmg;
        if (unblockedHits > 0 && p.hand.group.size() > 0) {
            applyPowerToSelf(new RetainForTurnsPower(p, unblockedHits, 1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new LegendarySword();
    }

    @Override
    public void upgrade() {
        if (canUpgrade()) {
            if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
