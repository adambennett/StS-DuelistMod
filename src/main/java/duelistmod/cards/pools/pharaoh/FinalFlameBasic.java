package duelistmod.cards.pools.pharaoh;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.List;

public class FinalFlameBasic extends DuelistCard {

    public static final String ID = DuelistMod.makeID("FinalFlameBasic");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.FINAL_FLAME);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final AttackEffect AFX = AttackEffect.FIRE;

    public FinalFlameBasic() {
    	super(ID, NAME, IMG,  0, EXTENDED_DESCRIPTIONS[0], TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.SPELL);
    	this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.PHARAOH_TWO_DECK);
        this.p2DeckCopies = 1;
    	this.misc = 0;
		this.originalName = this.name;
        this.baseDamage = this.damage = 3;
		this.tributes = this.baseTributes = 0;
		this.baseMagicNumber = this.magicNumber = 1;
		this.setupStartingCopies();
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
            AnyDuelist duelist = AnyDuelist.from(this);
            AbstractPower power = new WeakPower(targets.get(0), this.magicNumber, !duelist.player());
            this.addToBot(new ApplyPowerAction(targets.get(0), owner, power, power.amount));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new FinalFlameBasic();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.upgradeMagicNumber(1);
            this.rawDescription = EXTENDED_DESCRIPTIONS[0];
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
