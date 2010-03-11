package scrum.client.workspace;

import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.SwitchingNavigatorWidget;
import scrum.client.admin.ProjectSelectorWidget;
import scrum.client.admin.SystemMessageManagerWidget;
import scrum.client.admin.SystemMessageWidget;
import scrum.client.admin.UserConfigWidget;
import scrum.client.admin.UserListWidget;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;

public class UsersWorkspaceWidgets extends GUsersWorkspaceWidgets {

	private FlowPanel sidebar;
	private SwitchingNavigatorWidget navigator;
	private ProjectSelectorWidget projectSelector;
	private UserConfigWidget userConfig;
	private UserListWidget userList;
	private SystemMessageManagerWidget messageManager;

	public void activate() {
		projectSelector = new ProjectSelectorWidget();
		userConfig = new UserConfigWidget();
		messageManager = new SystemMessageManagerWidget();

		navigator = new SwitchingNavigatorWidget(ui.getWorkspace().getWorkarea());
		navigator.addItem("Projects", projectSelector);
		navigator.addItem("Personal Preferences", userConfig);
		if (auth.getUser().isAdmin()) {
			userList = new UserListWidget();
			navigator.addItem("User Management", userList);
			navigator.addItem("System Message Management", messageManager);
		}

		sidebar = new FlowPanel();
		sidebar.getElement().getStyle().setMarginTop(10, Unit.PX);
		sidebar.getElement().getStyle().setMarginLeft(10, Unit.PX);
		sidebar.add(new SystemMessageWidget());
		sidebar.add(Gwt.createSpacer(1, 10));
		sidebar.add(navigator);
		ui.show(sidebar, projectSelector);
	}

	public UserListWidget getUserList() {
		return userList;
	}

}