package duelistmod.cards.incomplete;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.*;
import duelistmod.variables.*;

public class CrystalRaigeki extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CrystalRaigeki");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CrystalRaigeki.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public CrystalRaigeki() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
        this.isMultiDamage = true;
		this.originalName = this.name;
		this.baseDamage = this.damage = 20;
		this.magicNumber = this.baseMagicNumber = 10;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(0.1F, 0.0F, 0.2F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
    	AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05F));
    	attackAllEnemies(AttackEffect.SLASH_HEAVY);
    	for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters)
    	{
            int roll = AbstractDungeon.cardRandomRng.random(1, 100);
            int check = 100 - (this.magicNumber);
            if (check < 1 || roll > check)
            {
    			AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(monster, p));
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CrystalRaigeki();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
