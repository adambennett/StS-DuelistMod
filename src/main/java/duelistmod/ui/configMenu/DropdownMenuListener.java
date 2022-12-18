package duelistmod.ui.configMenu;

@FunctionalInterface
public interface DropdownMenuListener {

    void change(String selectedText, int index);
}
