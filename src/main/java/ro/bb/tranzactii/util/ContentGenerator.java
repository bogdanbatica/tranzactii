package ro.bb.tranzactii.util;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ContentGenerator {

    private static ThreadLocal<StringBuilder> thrdSb = ThreadLocal.withInitial(StringBuilder::new);

    public static long randomLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    public static int randomInt(int limInfIncl, int limSupExcl) {
        return ThreadLocalRandom.current().nextInt(limInfIncl, limSupExcl);
    }

    public static int randomInt(int limSupExcl) {
        return ThreadLocalRandom.current().nextInt(limSupExcl);
    }


    public static String generateFixedLengthText(int length, String charset) {
        int charsetLength = charset.length();
        StringBuilder sb = thrdSb.get();
        sb.setLength(0);
        for (int i = 0; i < length; i++) {
            sb.append(charset.charAt(randomInt(charsetLength)));
        }
        return sb.toString();
    }

    public static String generateVariableLengthText(int minLength, int maxLength, String charset) {
        int length /* of the string to generate */ = randomInt(minLength, maxLength+1);
        return generateFixedLengthText(length, charset);
    }


}
