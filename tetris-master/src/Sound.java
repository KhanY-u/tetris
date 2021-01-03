import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	public static Board board;
	public static  void playMusic() {
	File musicPath = new File("tetris.wav");	
		try 
			{
			
			if(musicPath.exists()) {
				
				AudioInputStream audioInput= AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				
				
					clip.open(audioInput);
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(-30.0f);
					clip.start();
					clip.loop(LOOP_CONTINUOUSLY);
					
				
			}
			else {
				System.out.println("can't find file music");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	
}
	
	public void playSoundEffect(String soundfile)
	{
		try 
		{
		File musicPath = new File(soundfile);
		
		if(musicPath.exists()) {
			
			AudioInputStream audioinput= AudioSystem.getAudioInputStream(musicPath);
			Clip clip = AudioSystem.getClip();
			clip.open(audioinput);
			clip.start();
					
		}
		else {
			System.out.println("can't find file music");
		}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	
	}
}
