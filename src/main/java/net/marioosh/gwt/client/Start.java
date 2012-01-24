package net.marioosh.gwt.client;

import java.util.List;
import net.marioosh.gwt.shared.FieldVerifier;
import net.marioosh.gwt.shared.model.entities.User;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Start implements EntryPoint {

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("Send");
		final Button deleteButton = new Button("Remove all");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// input + buttons
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(5);
		hPanel.add(nameField);
		hPanel.add(sendButton);
		hPanel.add(deleteButton);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// grid
		final CellTable<User> table = new CellTable<User>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		initColumns(table);

		final SingleSelectionModel<User> selectionModel = new SingleSelectionModel<User>();
		table.setSelectionModel(selectionModel);
		refreshGrid(table);
		RootPanel.get("grid").add(table);

		Button refreshGrid = new Button("Refresh grid");
		hPanel.add(refreshGrid);
		refreshGrid.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				refreshGrid(table);
			}
		});
		
		RootPanel.get("buttons").add(hPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {

			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.addUser(textToServer, new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Remote Procedure Call - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(caught.getMessage());
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(String result) {
						dialogBox.setText("Remote Procedure Call");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(result);
						dialogBox.center();
						closeButton.setFocus(true);
						refreshGrid(table);
					}
				});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);

		deleteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				greetingService.deleteAllUsers(new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Void result) {
						refreshGrid(table);
					}
				});
			}
		});
	}

	private void initColumns(CellTable table) {
		
		Column<User, String> loginColumn = new Column<User, String>(new TextCell()) {

			@Override
			public String getValue(User user) {
				return user.getLogin();
			}
		};
		TextColumn<User> cId = new TextColumn<User>() {

			@Override
			public String getValue(User object) {
				return object.getId() + "";
			}
		};

		TextColumn<User> cEmail = new TextColumn<User>() {

			@Override
			public String getValue(User object) {
				return object.getEmail();
			}
		};

		TextColumn<User> cFirstname = new TextColumn<User>() {

			@Override
			public String getValue(User object) {
				return object.getFirstname();
			}
		};

		TextColumn<User> cLastname = new TextColumn<User>() {

			@Override
			public String getValue(User object) {
				return object.getLastname();
			}
		};

		TextColumn<User> cTelephone = new TextColumn<User>() {

			@Override
			public String getValue(User object) {
				return object.getTelephone();
			}
		};

		TextColumn<User> cDate = new TextColumn<User>() {

			@Override
			public String getValue(User object) {
				return object.getDate() + "";
			}
		};

		table.addColumn(cId,"id");
		table.addColumn(loginColumn,"login");
		table.addColumn(cFirstname,"firstname");
		table.addColumn(cLastname,"lastname");
		table.addColumn(cEmail,"email");
		// table.addColumn(cTelephone,"telephone");
		table.addColumn(cDate,"date");

	}

	private void refreshGrid(final CellTable table) {
		greetingService.allUsers(new AsyncCallback<List>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(List result) {
				GWT.log("SIZE: " + result.size());
				for (Object u : result) {
					GWT.log((User) u + "");
				}
				table.setRowData(result);
			}
		});
	}
}
