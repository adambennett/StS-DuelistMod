package duelistmod.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.enums.MathematicianFormula;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class Mathematician extends DuelistCard {

    public static final String ID = DuelistMod.makeID("Mathematician");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Mathematician.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = -1;
    private MathematicianFormula currentFormula;

    public Mathematician() {
        this(null);
    }

    public Mathematician(MathematicianFormula currentFormula) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tributes = this.baseTributes = 0;
        this.baseMagicNumber = this.magicNumber = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.X_COST);
        this.tags.add(Tags.SPELLCASTER);
        this.originalName = this.name;
        this.exhaust = true;
        this.currentFormula = currentFormula;
        if (this.currentFormula == null) {
            this.resetFormula();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.currentFormula == null) {
            return;
        }

        int x = 0;
        int y = 0;
        if (this.currentFormula.isX()) {
            x = getXEffect();
        }
        if (this.currentFormula.isY()) {
            y = getSummons(p);
        }

        if (y > 0) {
            tribute(p, y, false, this);
        }
        if (x > 0) {
            useXEnergy();
        }

        int newDamage = this.currentFormula.getDamage(x, y, this.magicNumber);
        specialAttack(m, AFX, newDamage);
    }

    @Override
    public void onDraw() {
        this.resetFormula();
    }

    private void resetFormula() {
        int formulaRoll = AbstractDungeon.cardRandomRng.random(0, MathematicianFormula.values().length - 1);
        this.currentFormula = MathematicianFormula.values()[formulaRoll];
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo("Mathematician", "Rolls a random formula to determine card damage each time you draw the card. If both X and Y appear in the formula, Mathematician will use all of your #yEnergy and #yTributes. If only X appears, only your #yEnergy will be removed. If only Y appears, only your #yTributes will be removed."));
        return tooltips;
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase.equals(AbstractRoom.RoomPhase.COMBAT)) {
            if (this.currentFormula == null) {
                this.resetFormula();
            }
            this.rawDescription = this.currentFormula.getDescription(
                    getXEffect(),
                    getSummons(AbstractDungeon.player),
                    this.magicNumber,
                    this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION
            );
        } else if (this.upgraded) {
            this.rawDescription = DESCRIPTION;
        } else {
            this.rawDescription = UPGRADE_DESCRIPTION;
        }
        this.fixUpgradeDesc();
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mathematician();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {}

	@Override
	public void onResummon(int summons) {}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
