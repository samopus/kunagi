/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.tasks;

import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.undo.AUndoOperation;
import scrum.client.admin.Auth;
import scrum.client.admin.User;
import scrum.client.dnd.BlockListDropAction;
import scrum.client.project.Requirement;
import scrum.client.sprint.Task;
import scrum.client.workspace.VisibleDataChangedEvent;

public class CloseTaskDropAction implements BlockListDropAction<Task> {

	private Requirement requirement;

	public CloseTaskDropAction(Requirement requirement) {
		this.requirement = requirement;
	}

	@Override
	public boolean isDroppable(Task task) {
		if (!task.getProject().isTeamMember(Scope.get().getComponent(User.class))) return false;
		return true;
	}

	@Override
	public boolean onDrop(Task task) {
		Requirement requirement = task.getRequirement();
		task.setRequirement(this.requirement);
		if (!task.isClosed()) task.setDone(Scope.get().getComponent(Auth.class).getUser());
		Scope.get().getComponent(scrum.client.undo.Undo.class).getManager().add(new Undo(task, requirement));
		return true;
	}

	class Undo extends AUndoOperation {

		private Task task;
		private Requirement requirement;

		public Undo(Task task, Requirement requirement) {
			this.task = task;
			this.requirement = requirement;
		}

		@Override
		public String getLabel() {
			return "Undo Close/Change Story for " + task.getReference() + " " + task.getLabel();
		}

		@Override
		protected void onUndo() {
			task.setRequirement(requirement);
			task.setUnDone(Scope.get().getComponent(Auth.class).getUser());
			new VisibleDataChangedEvent().fireInCurrentScope();
		}

	}

}
