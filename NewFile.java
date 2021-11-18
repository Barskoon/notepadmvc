import java.io.File;

public class NewFile implements Task {

	private Viewer viewer;
	private SaveFile saveFile;

	public NewFile(Viewer viewer) {
		this.viewer = viewer;
		saveFile = new SaveFile(viewer);
	}

	public void doTask() {
		if (viewer.getBool() == true) {
			if (viewer.getFileName() == null) {
				if (viewer.getInputText().equals("")) {
					viewer.updateText("");
					viewer.setFrameTitle(new File("New"));
					viewer.setFileName(null);
					viewer.setBool(false);
				} else {
					startSaveOptionDialog();
				}
			} else {
				startSaveOptionDialog();
			}
		} else {
			viewer.updateText("");
			viewer.setFrameTitle(new File("New"));
			viewer.setFileName(null);
			viewer.setBool(false);
		}
	}

	private void startSaveOptionDialog() {
		int n = viewer.getAnswer();
		if (n == 1) {
			viewer.updateText("");
			viewer.setFrameTitle(new File("New"));
			viewer.setFileName(null);
			viewer.setBool(false);
		} else if (n == 0) {
			if (viewer.getFileName() == null) {
				saveFile.doTask();
				if(saveFile.getCheck() == true){
					viewer.updateText("");
					viewer.setFrameTitle(new File("New"));
					viewer.setFileName(null);
					viewer.setBool(false);
				} else {
					return;
				}
			} else{
				saveFile.doTask();
				viewer.updateText("");
				viewer.setFrameTitle(new File("New"));
				viewer.setFileName(null);
				viewer.setBool(false);
			}
		} else {
			return;
		}
	}
}
