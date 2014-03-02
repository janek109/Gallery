package com.jan.gallery.client;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;
import gwtupload.client.IUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gallery implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private HorizontalPanel mainPanel = new HorizontalPanel();
	private VerticalPanel mainPanelVertical = new VerticalPanel();
	private HorizontalPanel controlPanel = new HorizontalPanel();

	private Button nextButton = new Button("Dalej");
	private Button prevButton = new Button("Wstecz");
	private Button rand = new Button("Losowy");

	private Button first = new Button("Pierwszy");
	private Button last = new Button("Ostatni");

	private GalleryModel galleryModel = new GalleryModel();
	
	private FlowPanel panelImages = new FlowPanel();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		controlPanel.add(rand);

		mainPanelVertical.add(galleryModel.getImagesWidget());
		mainPanelVertical.add(controlPanel);

		mainPanel.add(first);
		mainPanel.add(prevButton);
		mainPanel.add(mainPanelVertical);

		mainPanel.add(nextButton);
		mainPanel.add(last);

		RootPanel.get("main").add(mainPanel);

		nextButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				galleryModel.nextImg();
			}
		});

		prevButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				galleryModel.prevImg();
			}
		});

		rand.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				galleryModel.randImg();
			}
		});

		first.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				galleryModel.firstImg();
			}
		});

		last.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				galleryModel.lastImg();
			}
		});
		
		// Attach the image viewer to the document
		RootPanel.get("thumbnails").add(panelImages);

		// Create a new uploader panel and attach it to the document
		MultiUploader defaultUploader = new MultiUploader();
		RootPanel.get("default").add(defaultUploader);

		// Add a finish handler which will load the image once the upload
		// finishes
		defaultUploader.addOnFinishUploadHandler(onFinishUploaderHandler);

	}

	// Load the image in the document and in the case of success attach it to
	// the viewer
	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {

				new PreloadedImage(uploader.fileUrl(), showImage);

				// The server sends useful information to the client by default
				UploadedInfo info = uploader.getServerInfo();
				System.out.println("File name " + info.name);
				System.out.println("File content-type " + info.ctype);
				System.out.println("File size " + info.size);

				// You can send any customized message and parse it
				System.out.println("Server message " + info.message);
			}
		}
	};

	// Attach an image to the pictures viewer
	private OnLoadPreloadedImageHandler showImage = new OnLoadPreloadedImageHandler() {
		public void onLoad(PreloadedImage image) {
			image.setWidth("75px");
			panelImages.add(image);
		}
	};
}
