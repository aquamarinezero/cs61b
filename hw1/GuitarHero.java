/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
import synthesizer.GuitarString;
import java.util.Map;
import java.util.HashMap;
public class GuitarHero {
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        Map<Character, GuitarString> map = new HashMap<>();
        for (int i = 0; i < keyboard.length(); i++) {
            double frequency = Math.pow(2, ((double) (i - 24)) / 12) * 440;
            map.put(keyboard.charAt(i), new GuitarString(frequency));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */

            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (map.containsKey(key)) {
                    map.get(key).pluck();

                }
            }
            double sample = 0;
            for (int i = 0; i < keyboard.length(); i++) {
                /* compute the superposition of samples */
                sample = sample + map.get(keyboard.charAt(i)).sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);

                /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < keyboard.length(); i++) {
                /* compute the superposition of samples */
                map.get(keyboard.charAt(i)).tic();
            }
        }
    }
}
