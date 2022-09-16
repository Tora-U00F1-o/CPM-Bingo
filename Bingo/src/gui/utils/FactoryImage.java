package gui.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import logic.game.Box;
import logic.rewards.Reward;

public class FactoryImage {
	public static final String APP_ICON = "/media/img/logos/logoSmall.png";
	public static final String LOGO_MED = "/media/img/logos/logoMedium.png";
	public static final String LOGO_BIG = "/media/img/logos/logoBig.png";

	public static final String TUTORIAL_ICON = "/media/img/tutorial.png";
	public static final String WALLET_ICON = "/media/img/wallet.png";
	public static final String HELP_ICON = "/media/img/help.png";
	public static final String LANG_ICON = "/media/img/langImg/lang.png";
	public static final String TUTORIAL = "/media/img/tutorial/";
	
	public static final String IMAGE_BALL = "/media/img/board/ballImg/n";
	

	public static final String IMAGE_RABBIT_PATH = "/media/img/board/";
	public static final String IMAGE_RABBIT = "/media/img/board/rabbit.png"; 
	public static final String IMAGE_RABBIT_BINGO = "/media/img/board/rabbitBingo.png";
	
	public static final String IMAGE_NUMBER_DEF = "/media/img/board/boxImg/n";
	public static final String IMAGE_NUMBER_MRK = "/media/img/board/boxImg/marked.png";
	public static final String IMAGE_NUMBER_MAG = "/media/img/board/boxImg/magic.png";
	
	public final static String IMAGE_REWARD = "/media/img/rewardsImg/";

	
	public static ImageIcon loadImagen(String imgPath) {
		return new ImageIcon(FactoryImage.class.getResource(imgPath));
	}
	
	public static ImageIcon getImageReward(Reward reward) {
		return loadImagen(IMAGE_REWARD +reward.getCode()+".PNG");
	}
	
	public static void setPageTuto(JLabel page, String name) {
		Image img = loadImagen(TUTORIAL +name).getImage();
		Image imgEscaled = img.getScaledInstance(page.getWidth(), page.getHeight(), Image.SCALE_FAST);
		page.setIcon(new ImageIcon(imgEscaled));
	}
	
	public static ImageIcon getImageBall(int n) {
		return loadImagen(IMAGE_BALL +n +".png");
	}	
		
	public static ImageIcon getImageBox(Box box) {
		ImageIcon icon = loadImagen(IMAGE_NUMBER_DEF +box.getNumber() +".png");
		
		if (box.isMagicNumber()) {
			ImageIcon numMagic = loadImagen(IMAGE_NUMBER_MAG);
			icon = mixIcons(icon, numMagic);
		}
		
		if(box.isMarked()) {
			ImageIcon marked = loadImagen(IMAGE_NUMBER_MRK);
			icon = mixIcons(icon, marked);
		}

		return icon;
	}

	private static ImageIcon mixIcons(ImageIcon backGround, ImageIcon foreGround) {
		Image imageB = backGround.getImage();
        Image imageF = foreGround.getImage();
        
        int width = backGround.getIconWidth();
        int height = backGround.getIconHeight();
          
        Image mix = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) mix.getGraphics();
        
        graphics2D.drawImage(imageB, 0, 0, null);
        graphics2D.drawImage(imageF, 0, 0, null);
        graphics2D.dispose();

        return new ImageIcon(mix);
	}

	public static void setAdaptedImgButton(JButton button, Image image) {
		int width = button.getBounds().width;
	    int height = button.getBounds().height;
		int minorSide = width < height ? width-8 : height-8;
		
		Image imgEscaled = image.getScaledInstance(minorSide, minorSide, Image.SCALE_FAST);
		ImageIcon iconEscaled = new ImageIcon(imgEscaled);
		button.setIcon(iconEscaled);
		button.setDisabledIcon(iconEscaled);
	}
	
	public static void setAdaptedImgLbl(JLabel lbl, Image image) {
		int minorSide = lbl.getWidth() < lbl.getHeight() ? lbl.getWidth() : lbl.getHeight();
		Image imgEscaled = image.getScaledInstance(minorSide, minorSide, Image.SCALE_FAST);
		ImageIcon iconEscaled = new ImageIcon(imgEscaled);
		lbl.setIcon(iconEscaled);
	}	
	
	public static ImageIcon scaleImg(Image img, int width, int height) {
		Image imgEscaled =img.getScaledInstance(width, height, Image.SCALE_FAST);
		return new ImageIcon(imgEscaled);
	}
}
