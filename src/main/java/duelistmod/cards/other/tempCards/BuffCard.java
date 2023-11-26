package duelistmod.cards.other.tempCards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.BuffHelper;

import java.util.ArrayList;
import java.util.List;

public class BuffCard extends DuelistCard {
	private AbstractPower powerToApply;
	private String keyword;

	public BuffCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR,CardRarity RARITY, CardTarget TARGET, AbstractPower power) {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.dontTriggerOnUseCard = true;
		this.setPowerToApply(power);
		CommonKeywordIconsField.useIcons.set(this, false);
	}

	@Override
	public BuffCard makeStatEquivalentCopy() {
		BuffCard out = (BuffCard) super.makeStatEquivalentCopy();
		out.rawDescription = this.rawDescription;
		out.dontTriggerOnUseCard = true;
		out.setPowerToApply(this.getPowerToApply());
		out.setKeyword(this.getKeyword());
		return out;
	}

	@Override
	public List<TooltipInfo> getCustomTooltips() {
		List<TooltipInfo> output = new ArrayList<>();
		if (this.getPowerToApply() != null && this.getKeyword() != null) {
			this.keywords.clear();
			output.add(new TooltipInfo(this.getPowerToApply().name, this.getKeyword()));
		}
		return output;
	}

	public AbstractPower getPowerToApply() {
		return powerToApply;
	}

	public void setPowerToApply(AbstractPower powerToApply) {
		this.powerToApply = powerToApply;
		this.setKeyword(BuffHelper.getPowerDescriptionByPowerId(this.getPowerToApply().ID));
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public void upgrade() {}

	@Override
	public void use(AbstractPlayer arg0, AbstractMonster arg1) {}

}
