package net.marioosh.gwt.client;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import net.marioosh.gwt.shared.model.entities.Link;
import net.marioosh.gwt.shared.model.helper.Criteria;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.validation.client.Validation;
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

	public class Input extends Composite {
		private TextBox textBox;
		public Input(String label) {
			VerticalPanel  p = new VerticalPanel();
			p.add(new Label(label));
			textBox = new TextBox();
			p.add(textBox);
			initWidget(p);
		}
	}
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		final Validator validator = factory.getValidator();
		
		final Button sendButton = new Button("Add link");
		final Input address = new Input("address");
		final Input name = new Input("name");
		final TextArea description = new TextArea();
		final Label errorLabel = new Label();
		errorLabel.setStyleName("serverResponseLabelError");

		VerticalPanel desc = new VerticalPanel();
		desc.setWidth("100%");		
		desc.add(new Label("description"));
		desc.add(description);
		
		HorizontalPanel h = new HorizontalPanel();
		h.add(address);
		h.add(name);
		VerticalPanel p = new VerticalPanel();
		p.add(errorLabel);		
		p.add(h);
		p.add(desc);
		p.add(sendButton);
		RootPanel.get().add(p);

		// Focus the cursor on the name field when the app loads
		address.textBox.setFocus(true);

		// grid
		final CellTable<Link> table = new CellTable<Link>();
		initColumns(table);

		final SingleSelectionModel<Link> selectionModel = new SingleSelectionModel<Link>();
		table.setSelectionModel(selectionModel);
		refreshGrid(table);
		RootPanel.get().add(table);

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

				Link l = new Link(address.textBox.getText(), name.textBox.getText(), description.getText());
				Set<ConstraintViolation<Link>> violations = validator.validate(l);
				if(!violations.isEmpty()) {
					String error = "";
					for(ConstraintViolation<Link> c: violations) {
						error += c.getPropertyPath() + ":" + c.getMessage()+", ";
					}
					errorLabel.setText(error);
				} else {
					sendButton.setEnabled(false);
					greetingService.addLink(l, new AsyncCallback<Void>() {
	
						public void onFailure(Throwable caught) {
							sendButton.setEnabled(true);
							errorLabel.setText(caught.getMessage());
							caught.printStackTrace();
						}
	
						public void onSuccess(Void result) {
							sendButton.setEnabled(true);
							address.textBox.setFocus(true);
							address.textBox.selectAll();
							description.setText("");
							name.textBox.setText("");
							refreshGrid(table);
						}
					});
				}

			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		address.textBox.addKeyUpHandler(handler);
		description.addKeyUpHandler(handler);
		name.textBox.addKeyUpHandler(handler);
	}

	private void initColumns(CellTable table) {

		final SafeHtmlCell progressCell = new SafeHtmlCell();
		Column<Link, SafeHtml> cAddress2 = new Column<Link, SafeHtml>(progressCell){

			@Override
			public SafeHtml getValue(Link l) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				String href = l.getAddress().startsWith("http://") || l.getAddress().startsWith("https://") ? l.getAddress() : "http://" + l.getAddress();
				sb.appendHtmlConstant("<a href=\""+href+"\" target=\"_blank\">"+l.getName()+"</a>");
				return sb.toSafeHtml();
			}
		};
		TextColumn<Link> cId = new TextColumn<Link>() {
			@Override
			public String getValue(Link object) {
				return object.getId() + "";
			}
		};

		TextColumn<Link> cAddress = new TextColumn<Link>() {
			@Override
			public String getValue(Link object) {
				return object.getAddress() + "";
			}
		};
		
		TextColumn<Link> cName = new TextColumn<Link>() {
			@Override
			public String getValue(Link object) {
				return object.getName() + "";
			}
		};
		TextColumn<Link> cDescription = new TextColumn<Link>() {
			@Override
			public String getValue(Link object) {
				return object.getDescription() + "";
			}
		};
		TextColumn<Link> cDate = new TextColumn<Link>() {
			@Override
			public String getValue(Link object) {
				return object.getDate() + "";
			}
		};
		
		table.addColumn(cId,"id");
		table.addColumn(cName,"name");
		table.addColumn(cAddress2,"address");
		table.addColumn(cDate,"date");
		table.addColumn(cDescription,"description");

	}

	private void refreshGrid(final CellTable table) {
		greetingService.allLinks(new Criteria(), new AsyncCallback<List>() {
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			
			@Override
			public void onSuccess(List result) {
				table.setRowData(result);
			}
		});
	}
}
