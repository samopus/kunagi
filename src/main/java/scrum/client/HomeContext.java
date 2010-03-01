package scrum.client;

import ilarkesto.gwt.client.SwitchingNavigatorWidget;
import scrum.client.admin.ProjectSelectorWidget;
import scrum.client.admin.SystemMessageManagerWidget;
import scrum.client.admin.SystemMessageWidget;
import scrum.client.admin.UserConfigWidget;
import scrum.client.admin.UserListWidget;
import scrum.client.common.AScrumComponent;
import scrum.client.workspace.PagePanel;

public class HomeContext extends AScrumComponent {

	private PagePanel sidebar;
	private SwitchingNavigatorWidget navigator;
	private ProjectSelectorWidget projectSelector;
	private UserConfigWidget userConfig;
	private UserListWidget userList;
	private SystemMessageManagerWidget messageManager;

	@Override
	protected void onInitialization() {
		super.onInitialization();
		projectSelector = new ProjectSelectorWidget();
		userConfig = new UserConfigWidget();
		messageManager = new SystemMessageManagerWidget();

		navigator = new SwitchingNavigatorWidget(cm.getUi().getWorkspace().getWorkarea());
		navigator.addItem("Projects", projectSelector);
		navigator.addItem("Personal Preferences", userConfig);
		if (getCurrentUser().isAdmin()) {
			userList = new UserListWidget();
			navigator.addItem("User Management", userList);
			navigator.addItem("System Message Management", messageManager);
		}

		sidebar = new PagePanel();
		sidebar.add(new SystemMessageWidget());
		sidebar.addSection(navigator);
	}

	public void activate() {
		cm.getUi().show(sidebar, projectSelector);
	}

	public UserListWidget getUserList() {
		return userList;
	}

}
