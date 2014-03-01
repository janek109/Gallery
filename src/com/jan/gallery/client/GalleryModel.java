package com.jan.gallery.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Image;

public class GalleryModel {
	
	private ArrayList<String> images = new ArrayList<String>();
	private Image showedImage = new Image();
	private Integer current = 0;

	public GalleryModel() {
		images.add("http://i1.kwejk.pl/site_media/obrazki/2014/02/7df6c18fc07f8ce336d6a86608fb9965_original.jpg?1393435308");
		images.add("http://i1.kwejk.pl/site_media/obrazki/2014/02/62e2294110fe320f5ee24619b6374f23_original.png?1393444271");
		images.add("http://i1.kwejk.pl/site_media/obrazki/2014/02/44b160ccd54a746162db9388a59f3ee9_original.jpg?1393430513");
		images.add("http://i1.kwejk.pl/site_media/obrazki/2014/02/bcf06018007b9ac5b5ce725aab91fe50_original.jpg?1393438357");
		images.add("http://i1.kwejk.pl/site_media/obrazki/2014/02/bb7c935e1c04fd070d962c305cf6a5c4_original.jpg?1393431344");
		
		showedImage.setUrl(images.get(0));
	}

	public Image getImagesWidget() {
		return showedImage;
	}
	
	public void nextImg() {
			current++;
			current = current%images.size();
			showedImage.setUrl(images.get(current));
	}
	
	public void prevImg() {
			current--;
			if (current < 0) {
				current = images.size()-1;
			}
			current = current%images.size();
			showedImage.setUrl(images.get(current));
	}
	
	public void randImg() {
		Integer rand = Random.nextInt();
		current = rand%(images.size()-1);
		if (current < 0) {
			current = images.size()-1;
		}
		
		showedImage.setUrl(images.get(current));
	}
	
	public void firstImg() {
		showedImage.setUrl(images.get(0));
	}
	
	public void lastImg() {
		showedImage.setUrl(images.get(images.size()-1));
	}
}
