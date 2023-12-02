package duelistmod.persistence;

@SuppressWarnings("unused")
public class DeckUnlockProgressDTO {

    public boolean isAscendedDeckOneUnlocked = false;
    public boolean isAscendedDeckTwoUnlocked = false;
    public boolean isAscendedDeckThreeUnlocked = false;
    public boolean isPharaohDeckOneUnlocked = false;
    public boolean isPharaohDeckTwoUnlocked = false;
    public boolean isPharaohDeckThreeUnlocked = false;
    public boolean isPharaohDeckFourUnlocked = false;
    public boolean isPharaohDeckFiveUnlocked = false;
    public boolean isExtraRandomDecksUnlocked = false;

    public DeckUnlockProgressDTO() {}

    public DeckUnlockProgressDTO(boolean isAscendedDeckOneUnlocked, boolean isAscendedDeckTwoUnlocked, boolean isAscendedDeckThreeUnlocked, boolean isPharaohDeckOneUnlocked, boolean isPharaohDeckTwoUnlocked, boolean isPharaohDeckThreeUnlocked, boolean isPharaohDeckFourUnlocked, boolean isPharaohDeckFiveUnlocked, boolean isExtraRandomDecksUnlocked) {
        this.isAscendedDeckOneUnlocked = isAscendedDeckOneUnlocked;
        this.isAscendedDeckTwoUnlocked = isAscendedDeckTwoUnlocked;
        this.isAscendedDeckThreeUnlocked = isAscendedDeckThreeUnlocked;
        this.isPharaohDeckOneUnlocked = isPharaohDeckOneUnlocked;
        this.isPharaohDeckTwoUnlocked = isPharaohDeckTwoUnlocked;
        this.isPharaohDeckThreeUnlocked = isPharaohDeckThreeUnlocked;
        this.isPharaohDeckFourUnlocked = isPharaohDeckFourUnlocked;
        this.isPharaohDeckFiveUnlocked = isPharaohDeckFiveUnlocked;
        this.isExtraRandomDecksUnlocked = isExtraRandomDecksUnlocked;
    }

    public boolean isAscendedDeckOneUnlocked() {
        return isAscendedDeckOneUnlocked;
    }

    public void setAscendedDeckOneUnlocked(boolean ascendedDeckOneUnlocked) {
        isAscendedDeckOneUnlocked = ascendedDeckOneUnlocked;
    }

    public boolean isAscendedDeckTwoUnlocked() {
        return isAscendedDeckTwoUnlocked;
    }

    public void setAscendedDeckTwoUnlocked(boolean ascendedDeckTwoUnlocked) {
        isAscendedDeckTwoUnlocked = ascendedDeckTwoUnlocked;
    }

    public boolean isAscendedDeckThreeUnlocked() {
        return isAscendedDeckThreeUnlocked;
    }

    public void setAscendedDeckThreeUnlocked(boolean ascendedDeckThreeUnlocked) {
        isAscendedDeckThreeUnlocked = ascendedDeckThreeUnlocked;
    }

    public boolean isPharaohDeckOneUnlocked() {
        return isPharaohDeckOneUnlocked;
    }

    public void setPharaohDeckOneUnlocked(boolean pharaohDeckOneUnlocked) {
        isPharaohDeckOneUnlocked = pharaohDeckOneUnlocked;
    }

    public boolean isPharaohDeckTwoUnlocked() {
        return isPharaohDeckTwoUnlocked;
    }

    public void setPharaohDeckTwoUnlocked(boolean pharaohDeckTwoUnlocked) {
        isPharaohDeckTwoUnlocked = pharaohDeckTwoUnlocked;
    }

    public boolean isPharaohDeckThreeUnlocked() {
        return isPharaohDeckThreeUnlocked;
    }

    public void setPharaohDeckThreeUnlocked(boolean pharaohDeckThreeUnlocked) {
        isPharaohDeckThreeUnlocked = pharaohDeckThreeUnlocked;
    }

    public boolean isPharaohDeckFourUnlocked() {
        return isPharaohDeckFourUnlocked;
    }

    public void setPharaohDeckFourUnlocked(boolean pharaohDeckFourUnlocked) {
        isPharaohDeckFourUnlocked = pharaohDeckFourUnlocked;
    }

    public boolean isPharaohDeckFiveUnlocked() {
        return isPharaohDeckFiveUnlocked;
    }

    public void setPharaohDeckFiveUnlocked(boolean pharaohDeckFiveUnlocked) {
        isPharaohDeckFiveUnlocked = pharaohDeckFiveUnlocked;
    }

    public boolean isExtraRandomDecksUnlocked() {
        return isExtraRandomDecksUnlocked;
    }

    public void setExtraRandomDecksUnlocked(boolean extraRandomDecksUnlocked) {
        isExtraRandomDecksUnlocked = extraRandomDecksUnlocked;
    }
}
