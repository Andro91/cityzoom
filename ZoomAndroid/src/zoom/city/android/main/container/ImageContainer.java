package zoom.city.android.main.container;

import zoom.city.android.main.parser.ImageDownloader;


public class ImageContainer  {
	
	//variable
	private ImageDownloader imageDownloader = new ImageDownloader();
	
	//Getters and setters

	public ImageDownloader getImageDownloader() {
		return imageDownloader;
	}

	public void setImageDownloader(ImageDownloader imageDownloader) {
		this.imageDownloader = imageDownloader;
	}

	// CONSTUCTOR
	private static ImageContainer instance = null;

	public ImageContainer() {

	}

	public static ImageContainer getInstance() {
		if (instance == null) {
			instance = new ImageContainer();
		}
		return instance;
	}

}