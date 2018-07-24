package mms.ui;


public class UIGrid extends UIContainer {

	private final String[] gridCols = new String[]{"solo", "a", "b", "c", "d", "e"};
	private final String[] gridBlocks = new String[]{"a", "b", "c", "d", "e"};
	
	protected int columns = 0;
	
	public UIGrid(String nid, int cols) {
		super(nid);
		columns = cols;
	}

	public UIGrid(int cols) {
		super();
		columns = cols;
	}
	
	@Override
	public void add(UIControl ctl) {
		if(ctl instanceof UIGridCol) {
			UIGridCol grdC = (UIGridCol)ctl;
			grdC.block = gridBlocks[getCount() % columns];
			super.add(ctl);
		}
	}

	public int getColumns() {
		return columns;
	}
	
	@Override
	protected void createUI() {
		String shtml = "<div class=\"ui-grid-" + gridCols[columns - 1] + "\" id=\"" + getId() + "\"></div>";
		uiScreen.executeJSView("createGrid('" + shtml + "','" + getId() + "','" + getIdParent() + "')");
		super.createUI();
	}
}
