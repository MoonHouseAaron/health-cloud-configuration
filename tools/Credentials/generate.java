import java.security.MessageDigest;

public class generate {
    
    public static void main(String[] args){
        String clinicKey = args[0];
        String userId = args[1];
        String generatedString = "";
        String shortString = "";

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 32;
        java.util.Random random = new java.util.Random ();

        generatedString = random.ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
    
        shortString = generatedString.substring(0,22);

        String encryptedClinicId = "";
        try {
            MessageDigest md = java.security.MessageDigest.getInstance("SHA-512");
            byte[] b = md.digest((generatedString+clinicKey).getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< b.length ;i++){
                sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedClinicId = generatedString+java.util.Base64.getEncoder().encodeToString(b);
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("");
        System.out.println("Encrypted Clinic Id: ");
        System.out.println(encryptedClinicId);

        String encryptedUserId = "";
        try {
            MessageDigest md = java.security.MessageDigest.getInstance("SHA-512");
            byte[] b = md.digest((userId+shortString+clinicKey).getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< b.length ;i++){
                sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedUserId = java.util.Base64.getEncoder().encodeToString(b);
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("");
        System.out.println("Encrypted User Id: ");
        System.out.println(encryptedUserId);
        System.out.println("");
    }

}