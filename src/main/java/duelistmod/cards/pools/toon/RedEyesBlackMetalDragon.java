package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

public class RedEyesBlackMetalDragon extends DuelistCard {

    public static final String ID = DuelistMod.makeID("RedEyesBlackMetalDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RedEyesBlackMetalDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public RedEyesBlackMetalDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 18;
        this.tributes = this.baseTributes = 3;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.RED_EYES);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.GOOD_TRIB);
        this.misc = 0;
        this.originalName = this.name;
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
            normalMultidmg();
        }
        postDuelistUseCard(owner, targets);
    }

    private boolean isApexAndFreeToPlay() {
        AnyDuelist duelist = AnyDuelist.from(this);
        boolean hasRedEyes = false;
        boolean hasMetalmorph = false;
        for (AbstractCard c : duelist.hand()) {
            if (c.hasTag(Tags.RED_EYES)) {
                hasRedEyes = true;
            }
            if (c instanceof Metalmorph) {
                hasMetalmorph = true;
            }
            if (hasRedEyes && hasMetalmorph) break;
        }
        return (hasRedEyes && hasMetalmorph) || (this.upgraded && (hasRedEyes || hasMetalmorph));
    }

    @Override
    public boolean isApex() {
        return isApexAndFreeToPlay();
    }

    @Override
    public boolean freeToPlay() {
        boolean supe = super.freeToPlay();
        if (AbstractDungeon.currMapNode != null) {
            if (AbstractDungeon.player != null && AbstractDungeon.getCurrRoom().phase.equals(AbstractRoom.RoomPhase.COMBAT)) {
                return isApexAndFreeToPlay() || supe;
            }
        }
        return supe;
    }

    @Override
    public AbstractCard makeCopy() {
        return new RedEyesBlackMetalDragon();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
