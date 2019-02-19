package defaultmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class Raigeki extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("Raigeki");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.RAIGEKI);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final AttackEffect AFX = AttackEffect.NONE;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public Raigeki() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.SPELL);
        this.tags.add(DefaultMod.LEGEND_BLUE_EYES);
        this.isMultiDamage = true;
		this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Get target block and remove all of it
    	int monsterBlock = m.currentBlock;
    	if (monsterBlock > 0) { AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(m, m)); }
    	this.multiDamage = new int[] { monsterBlock, monsterBlock, monsterBlock, monsterBlock, monsterBlock, monsterBlock, monsterBlock, monsterBlock, monsterBlock, monsterBlock, monsterBlock, monsterBlock};
    	AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(0.1F, 0.0F, 0.2F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
    	attackAllEnemies(AFX, this.multiDamage);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Raigeki();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}