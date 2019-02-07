package defaultmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class LoseXEnergyAction extends AbstractGameAction {

	private AbstractPlayer player;
	private boolean freeToPlayOnce;

	public LoseXEnergyAction(AbstractPlayer player, boolean freeToPlayOnce) {
		super();
		this.player = player;
		this.freeToPlayOnce = freeToPlayOnce;
		duration = Settings.ACTION_DUR_XFAST;
		actionType = AbstractGameAction.ActionType.SPECIAL;
	}

	@Override
	public void update() {
		if (!freeToPlayOnce) {
			player.energy.use(EnergyPanel.totalCount);
		}
		isDone = true;
	}

}