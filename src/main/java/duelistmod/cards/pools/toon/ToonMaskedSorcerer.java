package duelistmod.cards.pools.toon;

import java.util.List;

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
import duelistmod.powers.duelistPowers.ArcanaPower;
import duelistmod.variables.Tags;

public class ToonMaskedSorcerer extends DuelistCard {
    public static final String ID = duelistmod.DuelistMod.makeID("ToonMaskedSorcerer");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ToonMaskedSorcerer.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public ToonMaskedSorcerer() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 9;
        this.misc = 0;
        this.originalName = this.name;
        this.baseSummons = this.summons = 2;
        this.baseMagicNumber = this.magicNumber = 2;
        this.toon = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.TOON_WORLD);
        this.tags.add(Tags.TOON_POOL);
        this.tags.add(Tags.SPELLCASTER);
		this.originalName = this.name;
        this.isSummon = true;
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
            attack(targets.get(0), this.baseAFX, this.damage);
        }
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.applyPowerToSelf(new ArcanaPower(duelist.creature(), duelist.creature(), this.magicNumber));
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ToonMaskedSorcerer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
