package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.RevengeDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.orbs.DuelistLight;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

public class SpiritHarpBasic extends RevengeDuelistCard {

    public static final String ID = DuelistMod.makeID("SpiritHarpBasic");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SPIRIT_HARP);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public SpiritHarpBasic() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 4;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.PHARAOH_THREE_DECK);
        this.p3DeckCopies = 1;
        this.summons = this.baseSummons = 1;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
		this.originalName = this.name;
		this.isSummon = true;
		this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	summon();
    	block();
    	channel(new DuelistLight());
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return super.isRevengeActive(card);
    }

    @Override
    public void onRevengeTriggered(AnyDuelist duelist) {
        duelist.channel(new DuelistLight());
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpiritHarpBasic();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
