public class CloseProgram implements Task {

	private Viewer viewer;
	private SaveFile saveFile;

	public CloseProgram(Viewer viewer) {
		this.viewer = viewer;
		saveFile = new SaveFile(viewer);
	}

	public void doTask() {
		if (viewer.getBool() == true) {
			if (viewer.getFileName() == null) {
				if (viewer.getInputText().equals("")) {
					System.exit(0);
				} else {
					startSaveOptionDialog();
				}
			} else {
				startSaveOptionDialog();
			}
		} else {
			System.exit(0);
		}
	}

	private void startSaveOptionDialog() {
		int n = viewer.getAnswer();
		if (n == 1) {
			System.exit(0);
		} else if (n == 0) {
			if (viewer.getFileName() == null) {
				saveFile.doTask();
				if(saveFile.getCheck() == true){
					System.exit(0);
				} else {
					return;
				}
			} else{
				System.exit(0);
			}
		} else {
			return;
		}
	}
}