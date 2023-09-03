package duelistmod.cards.pools.beast;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NinjitsuArtOfTransformation extends DuelistCard {
    public static final String ID = DuelistMod.makeID("NinjitsuArtOfTransformation");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("NinjitsuArtOfTransformation.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;

    public NinjitsuArtOfTransformation() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 8;
    	this.tags.add(Tags.TRAP);
    	this.misc = 0;
    	this.originalName = this.name;
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = 6;
        this.tributes = this.baseTributes = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        tribute();
        AnyDuelist duelist = AnyDuelist.from(this);
        List<AbstractCard> toTransform = duelist.hand().stream().filter(c -> c.hasTag(Tags.BEAST)).collect(Collectors.toList());
        ArrayList<AbstractCard> addToHand = new ArrayList<>();
        toTransform.forEach(c -> {
            int randomCardIndex = AbstractDungeon.cardRandomRng.random(0, DuelistMod.myCards.size() - 1);
            DuelistCard random = DuelistMod.myCards.get(randomCardIndex);
            if (random.type == CardType.SKILL) {
                duelist.applyPowerToSelf(new DexterityPower(duelist.creature(), this.magicNumber));
            }
            duelist.handGroup().removeCard(c);
            addToHand.add(random);
        });
        duelist.addCardsToHand(addToHand);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new NinjitsuArtOfTransformation();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
