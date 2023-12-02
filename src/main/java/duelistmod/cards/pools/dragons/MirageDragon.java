package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCardWithAltVersions;
import duelistmod.dto.AnyDuelist;
import duelistmod.enums.StartingDeck;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class MirageDragon extends DuelistCardWithAltVersions {

    public static final String ID = DuelistMod.makeID("MirageDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MirageDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;

    public MirageDragon() {
        this(null, null);
    }

    public MirageDragon(StartingDeck deck, String generalKey) {
        super(deck, generalKey, ID, NAME, IMG, deck == StartingDeck.PHARAOH_I ? 2 : 1, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.PHARAOH_ONE_DECK);
        this.originalName = this.name;
        this.enemyIntent = AbstractMonster.Intent.ATTACK_DEBUFF;
        this.p1DeckCopies = 1;
        this.summons = this.baseSummons = deck == StartingDeck.PHARAOH_I ? 1 : 2;
        this.baseDamage = this.damage = deck == StartingDeck.PHARAOH_I ? 8 : 2;
        this.baseMagicNumber = this.magicNumber = deck == StartingDeck.PHARAOH_I ? 2 : 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        summon();
        if (targets.size() > 0) {
            attack(targets.get(0), this.baseAFX, this.damage);
            AnyDuelist duelist = AnyDuelist.from(this);
            AbstractPower power = new VulnerablePower(targets.get(0), this.magicNumber, !duelist.player());
            this.addToBot(new ApplyPowerAction(targets.get(0), owner, power, power.amount));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MirageDragon(this.getDeckVersionKey(), this.getGeneralVersionKey());
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (this.getDeckVersionKey() != null && this.getDeckVersionKey() == StartingDeck.PHARAOH_I) {
                this.upgradeDamage(2);
                this.upgradeMagicNumber(1);
            } else {
                this.upgradeMagicNumber(2);
            }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public MirageDragon getSpecialVersion(StartingDeck deck, String key) {
        return new MirageDragon(deck, key);
    }
}
