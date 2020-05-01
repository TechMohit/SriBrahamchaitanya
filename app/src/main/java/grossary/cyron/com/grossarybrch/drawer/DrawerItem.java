package grossary.cyron.com.grossarybrch.drawer;

public class DrawerItem {

    private String text;
    private int icon;
    private String tag;
    private int iconUnSelect;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setIconUnSelect(int iconUnSelect) {
        this.iconUnSelect = iconUnSelect;
    }

    public int getIconUnSelect() {
        return iconUnSelect;
    }
}
