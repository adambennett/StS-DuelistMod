package duelistmod.abstracts;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import duelistmod.DuelistMod;
import duelistmod.actions.unique.PurgeSpecificCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.relics.MillenniumToken;
import duelistmod.relics.TokenUpgradeRelic;

public abstract class TokenCard extends DuelistCard {
	public TokenCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR,CardRarity RARITY, CardTarget TARGET) {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		CommonKeywordIconsField.useIcons.set(this, false);
	}

	@Override
	public void triggerOnEndOfPlayerTurn() {
		if (DuelistMod.persistentDuelistData.CardConfigurations.getTokensPurgeAtEndOfTurn()) {
			AnyDuelist duelist = AnyDuelist.from(this);
			AbstractDungeon.effectList.add(new ExhaustCardEffect(this));
			AbstractDungeon.actionManager.addToTop(new PurgeSpecificCard(this, duelist.handGroup()));
		}
	}
	
	@Override
	public boolean canUpgrade() {
		return AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(TokenUpgradeRelic.ID) && super.canUpgrade();
	}
	
	@Override
	public void update() {
		super.update();
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(MillenniumToken.ID) && this.cost > 0) {
			this.setCostForTurn(-this.cost);
			this.isCostModifiedForTurn = true;
			DuelistCard.glowCheck();
		}
	}

	@Override
	public void upgrade() {

	}

	@Override
	public void use(AbstractPlayer arg0, AbstractMonster arg1) {

	}

}
