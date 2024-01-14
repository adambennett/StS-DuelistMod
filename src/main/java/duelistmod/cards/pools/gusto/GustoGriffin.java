package duelistmod.cards.pools.gusto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class GustoGriffin extends DuelistCard {
    public static final String ID = DuelistMod.makeID("GustoGriffin");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GustoGriffin.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public GustoGriffin() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GUSTO);
        this.tags.add(Tags.BEAST);
        this.baseSummons = this.summons = 1;
        this.baseMagicNumber = this.magicNumber = 2;    // Number of attacks
        this.baseSecondMagic = this.secondMagic = 1;    // Strength gain
        this.baseDamage = this.damage = 3;
        this.originalName = this.name;
    }

    @Override
    public void upgrade() {
        if (upgraded) return;
        this.upgradeName();
        this.upgradeSecondMagic(2);
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.fixUpgradeDesc();
        this.initializeDescription();
        this.upgraded = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        if (targets.size() > 0) {
            for(int i = 0; i < this.magicNumber; i++) {
                attack(targets.get(0), this.baseAFX, this.damage);
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void customOnTribute(DuelistCard tc) {
        super.customOnTribute(tc);
        if(tc == null || !tc.hasTag(Tags.SPELLCASTER)) return;

        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.applyPowerToSelf(new StrengthPower(duelist.creature(), this.secondMagic));
    }
}
